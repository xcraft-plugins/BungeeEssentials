/*    */ package com.pantherman594.gssentials.command.general;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import com.pantherman594.gssentials.event.MessageEvent;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Command;
/*    */ import net.md_5.bungee.api.plugin.Event;
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
/*    */ public class MessageCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public MessageCommand() {
/* 35 */     super("message", "gssentials.message");
/* 36 */     ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)BungeeEssentials.getInstance(), (Command)new ReplyCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 41 */     if (args.length == 1) {
/* 42 */       if (sender instanceof ProxiedPlayer) {
/* 43 */         String uuid = ((ProxiedPlayer)sender).getUniqueId().toString();
/* 44 */         boolean change = true;
/* 45 */         boolean status = false;
/* 46 */         if (args[0].equalsIgnoreCase("toggle")) {
/* 47 */           status = this.pD.toggleMsging(uuid);
/* 48 */         } else if (args[0].equalsIgnoreCase("on")) {
/* 49 */           this.pD.setMsging(uuid, true);
/* 50 */           status = true;
/* 51 */         } else if (args[0].equalsIgnoreCase("off")) {
/* 52 */           this.pD.setMsging(uuid, false);
/*    */         } else {
/* 54 */           change = false;
/* 55 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "\n  /" + 
/* 56 */                   getName() + " <player> <message>\n  /" + 
/* 57 */                   getName() + " <on|off|toggle>" }));
/*    */         } 
/* 59 */         if (change) {
/* 60 */           if (status) {
/* 61 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MESSAGE_ENABLED, new String[0]));
/*    */           } else {
/* 63 */             sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MESSAGE_DISABLED, new String[0]));
/*    */           } 
/*    */         }
/*    */       } else {
/* 67 */         sender.sendMessage((BaseComponent)Dictionary.format("&cSorry, Console cannot toggle messages.", new String[0]));
/*    */       } 
/* 69 */     } else if (args.length > 1) {
/* 70 */       if (!args[0].equalsIgnoreCase("CONSOLE")) {
/* 71 */         ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(args[0]);
/* 72 */         if (recipient == null) {
/* 73 */           for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
/* 74 */             if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
/* 75 */               recipient = p;
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         }
/* 81 */         if (recipient != null && (!(sender instanceof ProxiedPlayer) || ((ProxiedPlayer)sender).getServer().getInfo() == recipient.getServer().getInfo() || sender.hasPermission("gssentials.message.global"))) {
/* 82 */           ProxyServer.getInstance().getPluginManager().callEvent((Event)new MessageEvent(sender, (CommandSender)recipient, Dictionary.combine(0, args)));
/*    */         } else {
/* 84 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */         } 
/*    */       } else {
/* 87 */         ProxyServer.getInstance().getPluginManager().callEvent((Event)new MessageEvent(sender, ProxyServer.getInstance().getConsole(), Dictionary.combine(0, args)));
/*    */       } 
/*    */     } else {
/* 90 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "\n  /" + 
/* 91 */               getName() + " <player> <message>\n  /" + 
/* 92 */               getName() + " <on|off|toggle>" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 98 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\MessageCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */