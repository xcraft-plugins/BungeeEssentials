/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.pantherman594.gssentials.database.PlayerData;
/*     */ import com.pantherman594.gssentials.regex.Handle;
/*     */ import com.pantherman594.gssentials.regex.RuleManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.stream.Collectors;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.event.ChatEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Messenger
/*     */ {
/*  38 */   public Map<UUID, UUID> messages = new HashMap<>();
/*  39 */   private Map<UUID, String> sentMessages = new HashMap<>();
/*  40 */   private Map<UUID, String> chatMessages = new HashMap<>();
/*     */   
/*  42 */   private PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*     */   
/*     */   public void chat(ProxiedPlayer player, ChatEvent event) {
/*  45 */     Preconditions.checkNotNull(player, "player null");
/*  46 */     Preconditions.checkNotNull(event, "event null");
/*  47 */     String message = filter(player, event.getMessage(), ChatType.PUBLIC);
/*     */     
/*  49 */     if (message == null) {
/*  50 */       event.setCancelled(true);
/*     */     } else {
/*  52 */       event.setMessage(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String filter(ProxiedPlayer player, String msg, ChatType ct) {
/*  57 */     if (msg == null || player == null) {
/*  58 */       return msg;
/*     */     }
/*  60 */     String message = msg;
/*     */     
/*  62 */     if (isMutedF(player, msg)) {
/*  63 */       return null;
/*     */     }
/*  65 */     if (!player.hasPermission("gssentials.admin.bypass-filter")) {
/*  66 */       if (BungeeEssentials.getInstance().contains(new String[] { ct.getRule() })) {
/*  67 */         List<RuleManager.MatchResult> results = BungeeEssentials.getInstance().getRuleManager().matches(msg);
/*  68 */         for (RuleManager.MatchResult result : results) {
/*  69 */           if (result.matched()) {
/*  70 */             CommandSender console; String command; Log.log(player, result.getRule(), ct);
/*  71 */             switch (result.getRule().getHandle()) {
/*     */               case ADVERTISEMENT:
/*  73 */                 player.sendMessage((BaseComponent)Dictionary.format(Dictionary.WARNINGS_ADVERTISING, new String[0]));
/*  74 */                 message = null;
/*  75 */                 ruleNotify(Dictionary.NOTIFY_ADVERTISEMENT, player, msg);
/*     */               
/*     */               case CURSING:
/*  78 */                 player.sendMessage((BaseComponent)Dictionary.format(Dictionary.WARNING_HANDLE_CURSING, new String[0]));
/*  79 */                 message = null;
/*  80 */                 ruleNotify(Dictionary.NOTIFY_CURSING, player, msg);
/*     */               
/*     */               case REPLACE:
/*  83 */                 if (result.getRule().getReplacement() != null && message != null) {
/*  84 */                   Matcher matcher = result.getRule().getPattern().matcher(message);
/*  85 */                   message = matcher.replaceAll(result.getRule().getReplacement());
/*     */                 } 
/*  87 */                 ruleNotify(Dictionary.NOTIFY_REPLACE, player, msg);
/*     */               
/*     */               case COMMAND:
/*  90 */                 console = ProxyServer.getInstance().getConsole();
/*  91 */                 command = result.getRule().getCommand();
/*  92 */                 if (command != null) {
/*  93 */                   ProxyServer.getInstance().getPluginManager().dispatchCommand(console, command.replace("{{ SENDER }}", player.getName()));
/*     */                 }
/*  95 */                 ruleNotify(Dictionary.NOTIFY_COMMAND, player, msg);
/*  96 */                 message = null;
/*     */             } 
/*     */ 
/*     */ 
/*     */           
/*     */           } 
/*     */         } 
/* 103 */         message = filterBannedWords(player, message, msg);
/*     */       } 
/* 105 */       if (BungeeEssentials.getInstance().contains(new String[] { ct.getSpam() }) && player.getServer().getInfo().getPlayers().size() > 1) {
/*     */         Map<UUID, String> msgs;
/* 107 */         if (ct.getSpam().equals("spam")) {
/* 108 */           msgs = this.sentMessages;
/*     */         } else {
/* 110 */           msgs = this.chatMessages;
/*     */         } 
/* 112 */         if (msgs.get(player.getUniqueId()) != null && compare(msg, msgs.get(player.getUniqueId())) > 0.85D) {
/* 113 */           player.sendMessage((BaseComponent)Dictionary.format(Dictionary.WARNING_LEVENSHTEIN_DISTANCE, new String[0]));
/* 114 */           return null;
/*     */         } 
/* 116 */         msgs.put(player.getUniqueId(), msg);
/*     */       } 
/*     */     } 
/* 119 */     return message;
/*     */   }
/*     */   
/*     */   private String filterBannedWords(ProxiedPlayer player, String message, String msg) {
/* 123 */     if (message != null) {
/* 124 */       for (String word : BungeeEssentials.getInstance().getMessages().getStringList("bannedwords.list")) {
/* 125 */         String finalReg = "\\b(";
/* 126 */         char[] chars = word.toLowerCase().toCharArray();
/* 127 */         char[] chars2 = word.toUpperCase().toCharArray();
/* 128 */         for (int i = 0; i < chars.length; i++) {
/* 129 */           finalReg = finalReg + "[" + chars[i] + chars2[i] + "]+[\\W\\d_]*";
/*     */         }
/* 131 */         finalReg = finalReg + ")";
/* 132 */         if (!finalReg.equals("\\b()")) {
/* 133 */           String replacement = Dictionary.BANNED_REPLACE;
/* 134 */           if (replacement.length() == 1) {
/* 135 */             String origRepl = replacement;
/* 136 */             while (replacement.length() < word.length()) {
/* 137 */               replacement = replacement + origRepl;
/*     */             }
/*     */           } 
/* 140 */           replacement = replacement + " ";
/* 141 */           String message2 = message.replaceAll(finalReg, replacement);
/* 142 */           if (!message2.equals(message)) {
/* 143 */             ruleNotify(Dictionary.NOTIFY_REPLACE, player, msg);
/* 144 */             message = message2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 149 */     return message;
/*     */   }
/*     */   
/*     */   private void ruleNotify(String notification, ProxiedPlayer player, String sentMessage) {
/* 153 */     ProxyServer.getInstance().getPlayers().stream().filter(p -> p.hasPermission("gssentials.admin.notify")).forEach(p -> {
/*     */           p.sendMessage((BaseComponent)Dictionary.format(notification, new String[] { "PLAYER", player.getName() }));
/*     */           p.sendMessage(ChatColor.GRAY + "Original Message: " + sentMessage);
/*     */         });
/*     */   }
/*     */   
/*     */   public UUID reply(ProxiedPlayer player) {
/* 160 */     return this.messages.get(player.getUniqueId());
/*     */   }
/*     */   
/*     */   public List<ProxiedPlayer> getVisiblePlayers(boolean seeHidden) {
/* 164 */     return (List<ProxiedPlayer>)BungeeEssentials.getInstance().getProxy().getPlayers().stream().filter(p -> (seeHidden || !this.pD.isHidden(p.getUniqueId().toString()))).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public Integer hiddenNum() {
/* 168 */     int hiddenNum = 0;
/* 169 */     for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
/* 170 */       if (this.pD.isHidden(p.getUniqueId().toString())) {
/* 171 */         hiddenNum++;
/*     */       }
/*     */     } 
/* 174 */     return Integer.valueOf(hiddenNum);
/*     */   }
/*     */   
/*     */   boolean isMutedF(ProxiedPlayer player, String msg) {
/* 178 */     Preconditions.checkNotNull(player, "Invalid player specified");
/* 179 */     BungeeEssentials bInst = BungeeEssentials.getInstance();
/* 180 */     if (!player.hasPermission("gssentials.admin.mute.exempt") && (this.pD.isMuted(player.getUniqueId().toString()) || (bInst.isIntegrated() && bInst.getIntegrationProvider().isMuted(player)))) {
/* 181 */       player.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_ERROR, new String[0]));
/* 182 */       ruleNotify(Dictionary.MUTE_ERRORN, player, msg);
/* 183 */       return true;
/*     */     } 
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   private double compare(String first, String second) {
/* 189 */     String longer = first, shorter = second;
/* 190 */     if (first.length() < second.length()) {
/* 191 */       longer = second;
/* 192 */       shorter = first;
/*     */     } 
/* 194 */     int longerLength = longer.length();
/* 195 */     if (longerLength == 0) {
/* 196 */       return 1.0D;
/*     */     }
/* 198 */     return (longerLength - getDistance(longer, shorter)) / longerLength;
/*     */   }
/*     */   
/*     */   private int getDistance(String s1, String s2) {
/* 202 */     s1 = s1.toLowerCase();
/* 203 */     s2 = s2.toLowerCase();
/*     */     
/* 205 */     int[] costs = new int[s2.length() + 1];
/* 206 */     for (int i = 0; i <= s1.length(); i++) {
/* 207 */       int lastValue = i;
/* 208 */       for (int j = 0; j <= s2.length(); j++) {
/* 209 */         if (i == 0) {
/* 210 */           costs[j] = j;
/*     */         }
/* 212 */         else if (j > 0) {
/* 213 */           int newValue = costs[j - 1];
/* 214 */           if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
/* 215 */             newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
/*     */           }
/* 217 */           costs[j - 1] = lastValue;
/* 218 */           lastValue = newValue;
/*     */         } 
/*     */       } 
/*     */       
/* 222 */       if (i > 0)
/* 223 */         costs[s2.length()] = lastValue; 
/*     */     } 
/* 225 */     return costs[s2.length()];
/*     */   }
/*     */   
/*     */   public enum ChatType {
/* 229 */     PUBLIC("rules-chat", "spam-chat"),
/* 230 */     PRIVATE("rules", "spam"),
/* 231 */     STAFF("rules", "spam"),
/* 232 */     GLOBAL("rules-chat", "spam-chat");
/*     */     
/*     */     private String rule;
/*     */     private String spam;
/*     */     
/*     */     ChatType(String rule, String spam) {
/* 238 */       this.rule = rule;
/* 239 */       this.spam = spam;
/*     */     }
/*     */     
/*     */     public String getRule() {
/* 243 */       return this.rule;
/*     */     }
/*     */     
/*     */     public String getSpam() {
/* 247 */       return this.spam;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\Messenger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */