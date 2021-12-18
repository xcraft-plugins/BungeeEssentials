/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
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
/*    */ public class ReloadCommand
/*    */   extends BECommand
/*    */ {
/*    */   public ReloadCommand() {
/* 30 */     super("reload", "gssentials.admin.reload");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 35 */     if (BungeeEssentials.getInstance().reload()) {
/* 36 */       sender.sendMessage((BaseComponent)Dictionary.format("&aBungeeEssentials has been reloaded!", new String[0]));
/*    */     } else {
/* 38 */       sender.sendMessage((BaseComponent)Dictionary.format("&cUnable to reload BungeeEssentials! :(", new String[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\ReloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */