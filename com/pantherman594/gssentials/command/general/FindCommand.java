/*    */ package com.pantherman594.gssentials.command.general;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
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
/*    */ public class FindCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public FindCommand() {
/* 33 */     super("find", "gssentials.find");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 38 */     if (args.length > 0) {
/* 39 */       ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
/* 40 */       if (player == null) {
/* 41 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */       } else {
/* 43 */         String uuid = player.getUniqueId().toString();
/*    */         
/* 45 */         if (!this.pD.isHidden(uuid)) {
/* 46 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.FORMAT_FIND_PLAYER, new String[] { "SERVER", player.getServer().getInfo().getName(), "PLAYER", player.getName() }));
/*    */         } else {
/* 48 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */         } 
/*    */       } 
/*    */     } else {
/* 52 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player>" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 58 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\FindCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */