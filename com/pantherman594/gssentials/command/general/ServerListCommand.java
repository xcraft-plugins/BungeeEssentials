/*     */ package com.pantherman594.gssentials.command.general;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import com.pantherman594.gssentials.command.BECommand;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.util.Collection;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.config.ServerInfo;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*     */ public class ServerListCommand
/*     */   extends BECommand
/*     */ {
/*     */   public ServerListCommand() {
/*  40 */     super("list", "gssentials.list");
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(CommandSender sender, String[] args) {
/*  45 */     boolean canSeeHidden = sender.hasPermission("gssentials.admin.hide.bypass");
/*  46 */     int online = BungeeEssentials.getInstance().getMessenger().getVisiblePlayers(canSeeHidden).size();
/*     */     
/*  48 */     String current = "CONSOLE";
/*  49 */     if (sender instanceof ProxiedPlayer) {
/*  50 */       current = ((ProxiedPlayer)sender).getServer().getInfo().getName();
/*     */     }
/*     */     
/*  53 */     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LIST_HEADER, new String[] { "COUNT", String.valueOf(online), "CURRENT", current }));
/*  54 */     for (ServerInfo info : ProxyServer.getInstance().getServers().values()) {
/*  55 */       try (Socket s = new Socket()) {
/*  56 */         s.connect(info.getAddress());
/*  57 */         print(sender, canSeeHidden, info, false);
/*  58 */       } catch (IOException e) {
/*  59 */         if (sender.hasPermission("gssentials.list.offline")) {
/*  60 */           print(sender, canSeeHidden, info, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<ProxiedPlayer> getPlayers(boolean canSeeHidden, ServerInfo info) {
/*  74 */     if (canSeeHidden && !info.getPlayers().isEmpty()) {
/*  75 */       return info.getPlayers();
/*     */     }
/*  77 */     return (Collection<ProxiedPlayer>)info.getPlayers().stream().filter(player -> !this.pD.isHidden(player.getUniqueId().toString())).collect(Collectors.toCollection(java.util.ArrayList::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDensity(boolean canSeeHidden, int players) {
/*  88 */     return String.valueOf(getColor(canSeeHidden, players)) + players;
/*     */   }
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
/*     */   private ChatColor getColor(boolean canSeeHidden, int players) {
/* 102 */     if (players == 0 || players < 0) {
/* 103 */       return ChatColor.RED;
/*     */     }
/*     */     
/* 106 */     int total = BungeeEssentials.getInstance().getMessenger().getVisiblePlayers(canSeeHidden).size();
/* 107 */     double percent = (players * 100.0F / total);
/* 108 */     if (percent <= 33.0D)
/* 109 */       return ChatColor.RED; 
/* 110 */     if (percent > 33.0D && percent <= 66.0D) {
/* 111 */       return ChatColor.GOLD;
/*     */     }
/* 113 */     return ChatColor.GREEN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPlayerList(Collection<ProxiedPlayer> players) {
/* 124 */     StringBuilder pList = new StringBuilder();
/* 125 */     for (ProxiedPlayer p : players) {
/* 126 */       if (pList.length() > 0) {
/* 127 */         pList.append(", ");
/*     */       }
/* 129 */       pList.append(p.getName());
/*     */     } 
/* 131 */     return pList.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void print(CommandSender sender, boolean canSeeHidden, ServerInfo info, boolean offline) {
/* 143 */     if (info.canAccess(sender) || sender.hasPermission("gssentials.list.restricted"))
/* 144 */       if (offline) {
/* 145 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LIST_BODY, new String[] { "SERVER", info.getName(), "MOTD", info.getMotd(), "DENSITY", "Offline", "COUNT", "Offline", "PLAYERS", "" }));
/*     */       } else {
/*     */         
/* 148 */         Collection<ProxiedPlayer> online = getPlayers(canSeeHidden, info);
/* 149 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LIST_BODY, new String[] { "SERVER", info.getName(), "MOTD", info.getMotd(), "DENSITY", getDensity(canSeeHidden, online.size()), "COUNT", String.valueOf(online.size()), "PLAYERS", getPlayerList(online) }));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\ServerListCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */