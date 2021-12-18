/*    */ package com.pantherman594.gssentials.command.general;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.config.ServerInfo;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ public class JoinCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public JoinCommand() {
/* 35 */     super("join", "gssentials.join");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 40 */     if (sender instanceof ProxiedPlayer) {
/* 41 */       if (args == null || args.length < 1) {
/* 42 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player>" }));
/*    */         
/*    */         return;
/*    */       } 
/* 46 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 47 */       ProxiedPlayer join = ProxyServer.getInstance().getPlayer(args[0]);
/* 48 */       if (join == null || this.pD.isHidden(join.getUniqueId().toString())) {
/* 49 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */         
/*    */         return;
/*    */       } 
/* 53 */       if (player.getUniqueId() == join.getUniqueId()) {
/* 54 */         sender.sendMessage(ChatColor.RED + "You cannot join yourself!");
/*    */         
/*    */         return;
/*    */       } 
/* 58 */       ServerInfo info = join.getServer().getInfo();
/* 59 */       if (info.canAccess((CommandSender)player)) {
/* 60 */         sender.sendMessage(ChatColor.LIGHT_PURPLE + "Attempting to join " + join.getName() + "'s server..");
/* 61 */         player.connect(info);
/*    */       } else {
/* 63 */         sender.sendMessage(ProxyServer.getInstance().getTranslation("no_server_permission", new Object[0]));
/*    */       } 
/*    */     } else {
/* 66 */       sender.sendMessage(ChatColor.RED + "Console cannot join servers");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 72 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\JoinCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */