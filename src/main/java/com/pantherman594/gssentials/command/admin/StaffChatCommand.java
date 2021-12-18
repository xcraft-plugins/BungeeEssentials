/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
/*    */ import com.pantherman594.gssentials.event.StaffChatEvent;
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
/*    */ public class StaffChatCommand
/*    */   extends ServerSpecificCommand
/*    */ {
/*    */   public StaffChatCommand() {
/* 32 */     super("staffchat", "gssentials.admin.chat");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/* 37 */     if (args != null && args.length > 0) {
/* 38 */       String server = "CONSOLE";
/* 39 */       if (sender instanceof ProxiedPlayer) {
/* 40 */         ProxiedPlayer player = (ProxiedPlayer)sender;
/* 41 */         String uuid = player.getUniqueId().toString();
/* 42 */         server = player.getServer().getInfo().getName();
/* 43 */         if (args.length == 1) {
/* 44 */           if (args[0].equals("on")) {
/* 45 */             this.pD.setStaffChat(uuid, true);
/* 46 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_ENABLED, new String[0])); return;
/*    */           } 
/* 48 */           if (args[0].equals("off")) {
/* 49 */             this.pD.setStaffChat(uuid, false);
/* 50 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_DISABLED, new String[0]));
/*    */             
/*    */             return;
/*    */           } 
/*    */         } 
/*    */       } 
/* 56 */       ProxyServer.getInstance().getPluginManager().callEvent((Event)new StaffChatEvent(server, sender.getName(), Dictionary.combine(args)));
/*    */ 
/*    */     
/*    */     }
/* 60 */     else if (sender instanceof ProxiedPlayer) {
/* 61 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 62 */       if (this.pD.toggleStaffChat(player.getUniqueId().toString())) {
/* 63 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_ENABLED, new String[0]));
/*    */       } else {
/* 65 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.SCHAT_DISABLED, new String[0]));
/*    */       } 
/*    */     } else {
/* 68 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [on|off]" }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\StaffChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */