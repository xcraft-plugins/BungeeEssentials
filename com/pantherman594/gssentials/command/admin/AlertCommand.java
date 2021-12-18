/*    */ package com.pantherman594.gssentials.command.admin;
/*    */ 
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.command.BECommand;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
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
/*    */ public class AlertCommand
/*    */   extends BECommand
/*    */ {
/*    */   public AlertCommand() {
/* 31 */     super("alert", "gssentials.admin.alert");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender sender, String[] args) {
/* 36 */     if (args.length > 0) {
/* 37 */       String server = "";
/* 38 */       if (sender instanceof ProxiedPlayer) {
/* 39 */         server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
/*    */       }
/* 41 */       ProxyServer.getInstance().broadcast((BaseComponent)Dictionary.format(Dictionary.FORMAT_ALERT, new String[] { "SENDER", sender.getName(), "SERVER", server, "MESSAGE", Dictionary.combine(args) }));
/*    */     } else {
/* 43 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <msg>" }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\AlertCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */