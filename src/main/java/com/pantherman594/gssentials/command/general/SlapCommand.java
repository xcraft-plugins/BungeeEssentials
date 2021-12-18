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
/*    */ public class SlapCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public SlapCommand() {
/* 33 */     super("slap", "");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 38 */     if (sender.hasPermission("gssentials.slap")) {
/* 39 */       ProxiedPlayer player = null;
/* 40 */       if (sender instanceof ProxiedPlayer) {
/* 41 */         player = (ProxiedPlayer)sender;
/*    */       }
/* 43 */       if (args.length > 0) {
/* 44 */         ProxiedPlayer enemy = ProxyServer.getInstance().getPlayer(args[0]);
/* 45 */         if (enemy != null && !this.pD.isHidden(enemy.getUniqueId().toString())) {
/* 46 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.SLAPPER_MSG, new String[] { "SLAPPED", enemy.getName() }));
/* 47 */           enemy.sendMessage((BaseComponent)Dictionary.format(Dictionary.SLAPPED_MSG, new String[] { "SLAPPER", (player == null) ? "GOD" : player.getName() }));
/*    */         } else {
/* 49 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */         } 
/*    */       } else {
/* 52 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player>" }));
/*    */       } 
/*    */     } else {
/* 55 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_UNWORTHY_OF_SLAP, new String[0]));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 62 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\SlapCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */