/*     */ package com.pantherman594.gssentials.event;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import com.pantherman594.gssentials.Messenger;
/*     */ import com.pantherman594.gssentials.database.PlayerData;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.Cancellable;
/*     */ import net.md_5.bungee.api.plugin.Event;
/*     */ import net.md_5.bungee.api.plugin.Plugin;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageEvent
/*     */   extends Event
/*     */   implements Cancellable
/*     */ {
/*     */   private CommandSender sender;
/*     */   private CommandSender recipient;
/*     */   private String msg;
/*     */   private boolean cancelled;
/*     */   
/*     */   public MessageEvent(CommandSender sender, CommandSender recipient, String msg) {
/*  50 */     this.sender = sender;
/*  51 */     this.recipient = recipient;
/*  52 */     this.msg = msg;
/*  53 */     String message = msg;
/*  54 */     PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*  55 */     if (recipient != null && recipient instanceof ProxiedPlayer && !pD.isHidden(((ProxiedPlayer)recipient).getUniqueId().toString())) {
/*  56 */       ProxiedPlayer player = null;
/*  57 */       if (sender instanceof ProxiedPlayer) {
/*  58 */         player = (ProxiedPlayer)sender;
/*  59 */         message = BungeeEssentials.getInstance().getMessenger().filter(player, msg, Messenger.ChatType.PRIVATE);
/*     */       } 
/*  61 */       String server = (player != null) ? player.getServer().getInfo().getName() : "CONSOLE";
/*  62 */       if (player != null) {
/*     */         
/*  64 */         if (!sender.hasPermission("gssentials.admin.spy.exempt")) {
/*  65 */           TextComponent spyMessage = Dictionary.format(Dictionary.SPY_MESSAGE, new String[] { "SERVER", server, "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message });
/*  66 */           for (ProxiedPlayer onlinePlayer : ProxyServer.getInstance().getPlayers()) {
/*  67 */             if (player.getUniqueId() != onlinePlayer.getUniqueId() && ((ProxiedPlayer)recipient).getUniqueId() != onlinePlayer.getUniqueId() && 
/*  68 */               onlinePlayer.hasPermission("gssentials.admin.spy") && pD.isSpy(onlinePlayer.getUniqueId().toString())) {
/*  69 */               onlinePlayer.sendMessage((BaseComponent)spyMessage);
/*     */             }
/*     */           } 
/*     */           
/*  73 */           if (pD.isSpy("CONSOLE") && spyMessage != null) {
/*  74 */             ProxyServer.getInstance().getConsole().sendMessage((BaseComponent)spyMessage);
/*     */           }
/*     */         } 
/*  77 */         ProxiedPlayer recp = (ProxiedPlayer)recipient;
/*  78 */         ProxiedPlayer play = player;
/*  79 */         (BungeeEssentials.getInstance().getMessenger()).messages.put(play.getUniqueId(), recp.getUniqueId());
/*  80 */         if (pD.isMsging(recp.getUniqueId().toString())) {
/*  81 */           ProxyServer.getInstance().getScheduler().schedule((Plugin)BungeeEssentials.getInstance(), () -> (UUID)(BungeeEssentials.getInstance().getMessenger()).messages.put(recp.getUniqueId(), play.getUniqueId()), 3L, TimeUnit.SECONDS);
/*     */         }
/*     */       } 
/*  84 */       String uuidR = ((ProxiedPlayer)recipient).getUniqueId().toString();
/*  85 */       if (sender != ProxyServer.getInstance().getConsole() && BungeeEssentials.getInstance().contains(new String[] { "ignore" })) {
/*  86 */         String uuidS = ((ProxiedPlayer)sender).getUniqueId().toString();
/*  87 */         if (message != null) {
/*  88 */           if (!pD.isIgnored(uuidS, uuidR)) {
/*  89 */             if (!pD.isIgnored(uuidR, uuidS) && (pD.isMsging(uuidR) || sender.hasPermission("gssentials.message.bypass"))) {
/*  90 */               recipient.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_RECEIVE, new String[] { "SERVER", server, "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/*     */             }
/*  92 */             sender.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_SEND, new String[] { "SERVER", ((ProxiedPlayer)recipient).getServer().getInfo().getName(), "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/*     */           } else {
/*  94 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_IGNORING, new String[0]));
/*     */           } 
/*     */         }
/*     */       } else {
/*  98 */         sender.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_SEND, new String[] { "SERVER", ((ProxiedPlayer)recipient).getServer().getInfo().getName(), "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/*  99 */         if ((message != null && pD.isMsging(uuidR)) || sender.hasPermission("gssentials.message.bypass")) {
/* 100 */           recipient.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_RECEIVE, new String[] { "SERVER", server, "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/*     */         }
/*     */       } 
/* 103 */     } else if (recipient == ProxyServer.getInstance().getConsole()) {
/* 104 */       String server = (sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)sender).getServer().getInfo().getName() : "CONSOLE";
/* 105 */       String serverC = (recipient instanceof ProxiedPlayer) ? ((ProxiedPlayer)recipient).getServer().getInfo().getName() : "CONSOLE";
/* 106 */       if (message != null) {
/* 107 */         recipient.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_RECEIVE, new String[] { "SERVER", server, "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/* 108 */         sender.sendMessage((BaseComponent)Dictionary.formatMsg(Dictionary.MESSAGE_FORMAT_SEND, new String[] { "SERVER", serverC, "SENDER", sender.getName(), "RECIPIENT", recipient.getName(), "MESSAGE", message }));
/*     */       } 
/*     */     } else {
/* 111 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */     } 
/*     */   }
/*     */   
/*     */   public CommandSender getSender() {
/* 116 */     return this.sender;
/*     */   }
/*     */   
/*     */   public void setSender(CommandSender sender) {
/* 120 */     this.sender = sender;
/*     */   }
/*     */   
/*     */   public CommandSender getRecipient() {
/* 124 */     return this.recipient;
/*     */   }
/*     */   
/*     */   public void setRecipient(ProxiedPlayer recipient) {
/* 128 */     this.recipient = (CommandSender)recipient;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 132 */     return this.msg;
/*     */   }
/*     */   
/*     */   public void setMessage(String msg) {
/* 136 */     this.msg = msg;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 140 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancelled) {
/* 144 */     this.cancelled = cancelled;
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\event\MessageEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */