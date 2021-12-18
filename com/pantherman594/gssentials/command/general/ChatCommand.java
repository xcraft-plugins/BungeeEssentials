/*    */ package com.pantherman594.gssentials.command.general;
/*    */ 
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
/*    */ import com.pantherman594.gssentials.event.GlobalChatEvent;
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
/*    */ public class ChatCommand
/*    */   extends ServerSpecificCommand
/*    */ {
/*    */   public ChatCommand() {
/* 32 */     super("chat", "gssentials.chat");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/* 37 */     if (args != null && args.length > 0) {
/* 38 */       String server = "CONSOLE";
/*    */       
/* 40 */       if (sender instanceof ProxiedPlayer) {
/* 41 */         ProxiedPlayer player = (ProxiedPlayer)sender;
/* 42 */         String uuid = player.getUniqueId().toString();
/* 43 */         server = player.getServer().getInfo().getName();
/* 44 */         if (args.length == 1) {
/* 45 */           if (args[0].equals("on")) {
/* 46 */             this.pD.setGlobalChat(uuid, true);
/* 47 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_ENABLED, new String[0])); return;
/*    */           } 
/* 49 */           if (args[0].equals("off")) {
/* 50 */             this.pD.setGlobalChat(uuid, false);
/* 51 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_DISABLED, new String[0]));
/*    */             
/*    */             return;
/*    */           } 
/*    */         } 
/*    */       } 
/* 57 */       ProxyServer.getInstance().getPluginManager().callEvent((Event)new GlobalChatEvent(server, sender.getName(), Dictionary.combine(args)));
/*    */ 
/*    */     
/*    */     }
/* 61 */     else if (sender instanceof ProxiedPlayer) {
/* 62 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 63 */       if (this.pD.toggleGlobalChat(player.getUniqueId().toString())) {
/* 64 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.GCHAT_ENABLED, new String[0]));
/*    */       } else {
/* 66 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.GCHAT_DISABLED, new String[0]));
/*    */       } 
/*    */     } else {
/* 69 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [on|off|msg]" }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\ChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */