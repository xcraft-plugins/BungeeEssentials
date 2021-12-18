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
/*    */ public class SpyCommand
/*    */   extends ServerSpecificCommand
/*    */ {
/*    */   public SpyCommand() {
/* 30 */     super("spy", "gssentials.admin.spy");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/*    */     String uuid;
/* 36 */     if (sender instanceof ProxiedPlayer) {
/* 37 */       uuid = ((ProxiedPlayer)sender).getUniqueId().toString();
/*    */     } else {
/* 39 */       uuid = "CONSOLE";
/*    */     } 
/* 41 */     if (args != null && args.length == 1) {
/* 42 */       switch (args[0]) {
/*    */         case "on":
/* 44 */           this.pD.setSpy(uuid, true);
/* 45 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.SPY_ENABLED, new String[0]));
/*    */           return;
/*    */         case "off":
/* 48 */           this.pD.setSpy(uuid, false);
/* 49 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.SPY_DISABLED, new String[0]));
/*    */           return;
/*    */       } 
/* 52 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [on|off]" }));
/*    */ 
/*    */     
/*    */     }
/* 56 */     else if (this.pD.toggleSpy(uuid)) {
/* 57 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.SPY_ENABLED, new String[0]));
/*    */     } else {
/* 59 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.SPY_DISABLED, new String[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\SpyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */