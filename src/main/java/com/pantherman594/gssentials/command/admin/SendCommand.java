/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.config.ServerInfo;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Command;
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
/*    */ public class SendCommand
/*    */   extends ServerSpecificCommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public SendCommand() {
/* 35 */     super("send", "gssentials.admin.send");
/* 36 */     ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)BungeeEssentials.getInstance(), (Command)new SendAllCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/* 41 */     if (args.length > 1) {
/* 42 */       ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
/* 43 */       if (player != null) {
/* 44 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FORMAT_SEND_PLAYER, new String[] { "PLAYER", args[0], "SERVER", args[1] }));
/* 45 */         ServerInfo info = ProxyServer.getInstance().getServerInfo(args[1]);
/* 46 */         player.connect(info, (success, throwable) -> {
/*    */               if (!success.booleanValue()) {
/*    */                 sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_SENDFAIL, new String[] { "PLAYER", player.getName(), "SERVER", info.getName() }));
/*    */               }
/*    */             });
/*    */       } else {
/* 52 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */       } 
/*    */     } else {
/* 55 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player> <server>" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 61 */     switch (args.length) {
/*    */       case 1:
/* 63 */         return tabPlayers(sender, args[0]);
/*    */       case 2:
/* 65 */         return tabStrings(args[1], (String[])ProxyServer.getInstance().getServers().keySet().toArray((Object[])new String[ProxyServer.getInstance().getServers().keySet().size()]));
/*    */     } 
/* 67 */     return (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\SendCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */