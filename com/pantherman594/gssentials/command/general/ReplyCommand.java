/*    */ package com.pantherman594.gssentials.command.general;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import com.pantherman594.gssentials.event.MessageEvent;
/*    */ import java.util.UUID;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Event;
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
/*    */ 
/*    */ public class ReplyCommand
/*    */   extends BECommand
/*    */ {
/*    */   public ReplyCommand() {
/* 35 */     super("reply", "gssentials.message");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 40 */     if (sender instanceof ProxiedPlayer) {
/* 41 */       if (args.length > 0) {
/* 42 */         ProxiedPlayer player = (ProxiedPlayer)sender;
/* 43 */         UUID uuid = BungeeEssentials.getInstance().getMessenger().reply(player);
/* 44 */         if (uuid == null) {
/* 45 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_NOBODY_HAS_MESSAGED, new String[0]));
/*    */           return;
/*    */         } 
/* 48 */         ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(uuid);
/* 49 */         if (recipient == null) {
/* 50 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*    */         }
/* 52 */         ProxyServer.getInstance().getPluginManager().callEvent((Event)new MessageEvent(sender, (CommandSender)recipient, Dictionary.combine(args)));
/*    */       } else {
/* 54 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <message>" }));
/*    */       } 
/*    */     } else {
/* 57 */       sender.sendMessage((BaseComponent)Dictionary.format("&cSorry, only players can reply to messages.", new String[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\ReplyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */