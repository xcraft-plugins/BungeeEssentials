/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import java.util.Collection;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.config.ServerInfo;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Plugin;
/*    */ import net.md_5.bungee.api.plugin.TabExecutor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SendAllCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public SendAllCommand() {
/* 37 */     super("sendall", "gssentials.admin.sendall");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 42 */     if (args.length > 0) {
/* 43 */       ServerInfo sInfo = ProxyServer.getInstance().getServerInfo(args[0]);
/* 44 */       Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
/* 45 */       if (args.length > 1) {
/* 46 */         sInfo = ProxyServer.getInstance().getServerInfo(args[1]);
/* 47 */         players = ProxyServer.getInstance().getServerInfo(args[0]).getPlayers();
/*    */       } 
/* 49 */       ServerInfo info = sInfo;
/* 50 */       for (ProxiedPlayer player : players) {
/* 51 */         ProxyServer.getInstance().getScheduler().runAsync((Plugin)BungeeEssentials.getInstance(), () -> player.connect(info, ()));
/*    */       
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 58 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [fromServer] <toServer>" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 64 */     switch (args.length) {
/*    */       case 1:
/* 66 */         return tabPlayers(sender, args[0]);
/*    */       case 2:
/* 68 */         return tabStrings(args[1], (String[])ProxyServer.getInstance().getServers().keySet().toArray((Object[])new String[ProxyServer.getInstance().getServers().keySet().size()]));
/*    */     } 
/* 70 */     return (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\SendAllCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */