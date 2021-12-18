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
/*    */ public class HideCommand
/*    */   extends ServerSpecificCommand
/*    */ {
/*    */   public HideCommand() {
/* 30 */     super("hide", "gssentials.admin.hide");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(CommandSender sender, String[] args) {
/* 35 */     if (sender instanceof ProxiedPlayer) {
/* 36 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 37 */       String uuid = player.getUniqueId().toString();
/* 38 */       if (args != null && args.length == 1) {
/* 39 */         switch (args[0]) {
/*    */           case "on":
/* 41 */             this.pD.setHidden(uuid, true);
/* 42 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.HIDE_ENABLED, new String[0]));
/*    */             return;
/*    */           case "off":
/* 45 */             this.pD.setHidden(uuid, false);
/* 46 */             player.sendMessage((BaseComponent)Dictionary.format(Dictionary.HIDE_DISABLED, new String[0]));
/*    */             return;
/*    */         } 
/* 49 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " [on|off]" }));
/*    */ 
/*    */       
/*    */       }
/* 53 */       else if (this.pD.toggleHidden(uuid)) {
/* 54 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.HIDE_ENABLED, new String[0]));
/*    */       } else {
/* 56 */         player.sendMessage((BaseComponent)Dictionary.format(Dictionary.HIDE_DISABLED, new String[0]));
/*    */       } 
/*    */     } else {
/*    */       
/* 60 */       sender.sendMessage((BaseComponent)Dictionary.format("&cConsole cannot hide itself", new String[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\HideCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */