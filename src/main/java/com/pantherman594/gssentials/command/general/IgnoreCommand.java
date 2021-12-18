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
/*    */ public class IgnoreCommand
/*    */   extends BECommand
/*    */   implements TabExecutor
/*    */ {
/*    */   public IgnoreCommand() {
/* 33 */     super("ignore", "gssentials.ignore");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 38 */     if (sender instanceof ProxiedPlayer) {
/* 39 */       if (args.length > 0)
/* 40 */       { ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
/* 41 */         String uuid = ((ProxiedPlayer)sender).getUniqueId().toString();
/* 42 */         if (p != null && !this.pD.isHidden(uuid))
/* 43 */         { if (p != sender)
/* 44 */           { if (this.pD.toggleIgnore(uuid, p.getUniqueId().toString())) {
/* 45 */               sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.IGNORE_ENABLED, new String[] { "PLAYER", p.getName() }));
/*    */             } else {
/* 47 */               sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.IGNORE_DISABLED, new String[] { "PLAYER", p.getName() }));
/*    */             }  }
/* 49 */           else { sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_IGNORE_SELF, new String[0])); }  }
/* 50 */         else { sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0])); }
/*    */          }
/* 52 */       else { sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <player>" })); }
/*    */     
/*    */     } else {
/* 55 */       sender.sendMessage((BaseComponent)Dictionary.format("&cConsole cannot ignore players (how are you seeing messages in the first place?)", new String[0]));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 61 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\IgnoreCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */