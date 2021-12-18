/*    */ package com.pantherman594.gssentials.command;
/*    */ 
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
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
/*    */ public abstract class ServerSpecificCommand
/*    */   extends BECommand
/*    */ {
/*    */   private final String permission;
/*    */   
/*    */   public ServerSpecificCommand(String name, String permission) {
/* 29 */     super(name, "");
/* 30 */     this.permission = permission;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void execute(CommandSender sender, String[] args) {
/* 35 */     if (sender.hasPermission(this.permission)) {
/* 36 */       run(sender, args);
/*    */     } else {
/* 38 */       ProxiedPlayer player = (ProxiedPlayer)sender;
/* 39 */       String server = player.getServer().getInfo().getName().toLowerCase().replace(" ", "-");
/* 40 */       if (player.hasPermission(this.permission + "." + server)) {
/* 41 */         run(sender, args);
/*    */       } else {
/* 43 */         player.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void run(CommandSender paramCommandSender, String[] paramArrayOfString);
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\ServerSpecificCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */