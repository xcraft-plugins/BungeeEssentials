/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
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
/*    */ public class MuteCommand
/*    */   extends ServerSpecificCommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public MuteCommand() {
/* 33 */     super("mute", "gssentials.admin.mute");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/* 38 */     if (args != null && args.length > 0) {
/* 39 */       ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
/* 40 */       if (player != null) {
/* 41 */         String uuid = player.getUniqueId().toString();
/* 42 */         if (!player.hasPermission("gssentials.admin.mute.exempt")) {
/* 43 */           if (this.pD.toggleMuted(uuid)) {
/* 44 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_ENABLED, new String[0]));
/* 45 */             ProxyServer.getInstance().getPlayers().stream().filter(p -> p.hasPermission("gssentials.admin.notify")).forEach(p -> p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_ENABLEDN, new String[] { "PLAYER", player.getName() })));
/*    */           } else {
/* 47 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_DISABLED, new String[0]));
/* 48 */             ProxyServer.getInstance().getPlayers().stream().filter(p -> p.hasPermission("gssentials.admin.notify")).forEach(p -> p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_DISABLEDN, new String[] { "PLAYER", player.getName() })));
/*    */           } 
/*    */         } else {
/* 51 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MUTE_EXEMPT, new String[0]));
/*    */         } 
/*    */       } else {
/* 54 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */       } 
/*    */     } else {
/* 57 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player>" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 63 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\MuteCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */