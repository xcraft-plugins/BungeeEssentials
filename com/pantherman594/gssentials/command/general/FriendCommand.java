/*     */ package com.pantherman594.gssentials.command.general;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import com.pantherman594.gssentials.command.BECommand;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.TabExecutor;
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
/*     */ public class FriendCommand
/*     */   extends BECommand
/*     */   implements TabExecutor
/*     */ {
/*     */   public FriendCommand() {
/*  42 */     super("friend", "gssentials.friend");
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(CommandSender sender, String[] args) {
/*  47 */     if (sender instanceof ProxiedPlayer) {
/*  48 */       String uuid = ((ProxiedPlayer)sender).getUniqueId().toString();
/*  49 */       Set<String> friends = this.pD.getFriends(uuid);
/*  50 */       if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("list"))) {
/*  51 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_HEADER, new String[] { "COUNT", String.valueOf(this.pD.getFriends(uuid).size()) }));
/*     */         
/*  53 */         for (String friend : friends) {
/*  54 */           String name, server = "Offline";
/*     */ 
/*     */           
/*  57 */           if (ProxyServer.getInstance().getPlayer(UUID.fromString(friend)) != null) {
/*  58 */             server = ProxyServer.getInstance().getPlayer(UUID.fromString(friend)).getServer().getInfo().getName();
/*  59 */             name = ProxyServer.getInstance().getPlayer(UUID.fromString(friend)).getName();
/*     */           } else {
/*  61 */             name = this.pD.getName(friend);
/*     */           } 
/*     */           
/*  64 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_BODY, new String[] { "NAME", name, "SERVER", server }));
/*     */         } 
/*     */         
/*  67 */         boolean headerSent = false;
/*     */         
/*  69 */         Set<String> outRequests = this.pD.getOutRequests(uuid);
/*  70 */         for (String outRequest : outRequests) {
/*  71 */           String name; if (!headerSent) {
/*  72 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_HEADER, new String[] { "COUNT", String.valueOf(outRequests.size()) }));
/*  73 */             headerSent = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  78 */           if (ProxyServer.getInstance().getPlayer(UUID.fromString(outRequest)) != null) {
/*  79 */             name = ProxyServer.getInstance().getPlayer(UUID.fromString(outRequest)).getName();
/*     */           } else {
/*  81 */             name = this.pD.getName(outRequest);
/*     */           } 
/*     */           
/*  84 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_BODY, new String[] { "NAME", name }));
/*     */         } 
/*     */         
/*  87 */         headerSent = false;
/*     */         
/*  89 */         Set<String> inRequests = this.pD.getInRequests(uuid);
/*  90 */         for (String inRequest : inRequests) {
/*  91 */           String name; if (!headerSent) {
/*  92 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.INREQUESTS_HEADER, new String[] { "COUNT", String.valueOf(inRequests.size()) }));
/*  93 */             headerSent = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  98 */           if (ProxyServer.getInstance().getPlayer(UUID.fromString(inRequest)) != null) {
/*  99 */             name = ProxyServer.getInstance().getPlayer(UUID.fromString(inRequest)).getName();
/*     */           } else {
/* 101 */             name = this.pD.getName(inRequest);
/*     */           } 
/*     */           
/* 104 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.INREQUESTS_BODY, new String[] { "NAME", name }));
/*     */         } 
/* 106 */       } else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("deny"))) {
/* 107 */         ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
/*     */ 
/*     */         
/* 110 */         if (p == sender) {
/* 111 */           sender.sendMessage((BaseComponent)Dictionary.format("&cYou can't be friends with yourself!.", new String[0]));
/*     */         } else {
/*     */           String friendUuid;
/* 114 */           if (p != null) {
/* 115 */             friendUuid = p.getUniqueId().toString();
/*     */           } else {
/* 117 */             friendUuid = BungeeEssentials.getInstance().getOfflineUUID(args[1]);
/* 118 */             if (friendUuid == null) {
/* 119 */               sender.sendMessage((BaseComponent)Dictionary.format("&cError: Could not find player.", new String[0]));
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/* 124 */           String friendName = this.pD.getName(friendUuid);
/*     */           
/* 126 */           if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("accept")) {
/* 127 */             if (this.pD.getFriends(uuid).contains(friendUuid)) {
/* 128 */               sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_OLD, new String[] { "NAME", args[1] }));
/*     */             }
/* 130 */             else if (p != null && !this.pD.isHidden(friendUuid)) {
/*     */               
/* 132 */               if (this.pD.getInRequests(uuid).contains(friendUuid)) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 137 */                 this.pD.removeInRequest(uuid, friendUuid);
/* 138 */                 this.pD.addFriend(uuid, friendUuid);
/*     */                 
/* 140 */                 this.pD.removeOutRequest(friendUuid, uuid);
/* 141 */                 this.pD.addFriend(friendUuid, uuid);
/*     */                 
/* 143 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_NEW, new String[] { "NAME", sender.getName() }));
/* 144 */                 sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_NEW, new String[] { "NAME", p.getName() }));
/*     */               }
/* 146 */               else if (!this.pD.getOutRequests(uuid).contains(friendUuid)) {
/*     */                 
/* 148 */                 this.pD.addOutRequest(uuid, friendUuid);
/* 149 */                 this.pD.addInRequest(friendUuid, uuid);
/*     */                 
/* 151 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.INREQUESTS_NEW, new String[] { "NAME", sender.getName() }));
/* 152 */                 sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_NEW, new String[] { "NAME", p.getName() }));
/*     */               } else {
/*     */                 
/* 155 */                 sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_OLD, new String[] { "NAME", p.getName() }));
/*     */               } 
/*     */             } else {
/* 158 */               sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */             }
/*     */           
/*     */           }
/* 162 */           else if (this.pD.getFriends(uuid).contains(friendUuid)) {
/* 163 */             this.pD.removeFriend(uuid, friendUuid);
/* 164 */             this.pD.removeFriend(friendUuid, uuid);
/*     */             
/* 166 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_REMOVE, new String[] { "NAME", friendName }));
/* 167 */             if (p != null) {
/* 168 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.FRIEND_REMOVE, new String[] { "NAME", sender.getName() }));
/*     */             }
/*     */           }
/* 171 */           else if (this.pD.getOutRequests(uuid).contains(friendUuid)) {
/* 172 */             this.pD.removeOutRequest(uuid, friendUuid);
/* 173 */             this.pD.removeInRequest(friendUuid, uuid);
/*     */             
/* 175 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, new String[] { "NAME", friendName }));
/* 176 */             if (p != null) {
/* 177 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.INREQUESTS_REMOVE, new String[] { "NAME", sender.getName() }));
/*     */             }
/*     */           }
/* 180 */           else if (this.pD.getInRequests(uuid).contains(friendUuid)) {
/* 181 */             this.pD.removeInRequest(uuid, friendUuid);
/* 182 */             this.pD.removeOutRequest(friendUuid, uuid);
/*     */             
/* 184 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.INREQUESTS_REMOVE, new String[] { "NAME", friendName }));
/* 185 */             if (p != null) {
/* 186 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, new String[] { "NAME", sender.getName() }));
/*     */             }
/*     */           } else {
/*     */             
/* 190 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.CANNOT_REMOVE_FRIEND, new String[] { "NAME", friendName }));
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 195 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [list|add <player>|remove <player>]" }));
/*     */       } 
/*     */     } else {
/* 198 */       sender.sendMessage((BaseComponent)Dictionary.format("&cConsole does not have any friends.", new String[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 204 */     switch (args.length) {
/*     */       case 2:
/* 206 */         return tabPlayers(sender, args[1]);
/*     */       case 1:
/* 208 */         return tabStrings(args[0], new String[] { "list", "add", "remove" });
/*     */     } 
/* 210 */     return (Iterable<String>)ImmutableSet.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\FriendCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */