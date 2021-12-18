/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ public class CSpyCommand
/*    */   extends ServerSpecificCommand
/*    */ {
/*    */   public CSpyCommand() {
/* 30 */     super("commandspy", "gssentials.admin.spy.command");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/*    */     String uuid;
/* 36 */     if (sender instanceof ProxiedPlayer) {
/* 37 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 38 */       uuid = player.getUniqueId().toString();
/*    */     } else {
/* 40 */       uuid = "CONSOLE";
/*    */     } 
/* 42 */     if (args != null && args.length == 1) {
/* 43 */       switch (args[0]) {
/*    */         case "on":
/* 45 */           this.pD.setCSpy(uuid, true);
/* 46 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.CSPY_ENABLED, new String[0]));
/*    */           return;
/*    */         case "off":
/* 49 */           this.pD.setCSpy(uuid, false);
/* 50 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.CSPY_DISABLED, new String[0]));
/*    */           return;
/*    */       } 
/* 53 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [on|off]" }));
/*    */ 
/*    */     
/*    */     }
/* 57 */     else if (this.pD.toggleCSpy(uuid)) {
/* 58 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.CSPY_ENABLED, new String[0]));
/*    */     } else {
/* 60 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.CSPY_DISABLED, new String[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\CSpyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */