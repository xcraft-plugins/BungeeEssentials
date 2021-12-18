/*    */ package com.pantherman594.gssentials.event;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.Dictionary;
/*    */ import com.pantherman594.gssentials.Log;
/*    */ import com.pantherman594.gssentials.Messenger;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Cancellable;
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
/*    */ 
/*    */ public class StaffChatEvent
/*    */   extends BEChatEvent
/*    */   implements Cancellable
/*    */ {
/*    */   public StaffChatEvent(String server, String sender, String msgPre) {
/* 36 */     super(server, sender, msgPre);
/*    */   }
/*    */   
/*    */   public void execute() {
/* 40 */     String msgPre = getMessage();
/* 41 */     if (msgPre != null) {
/* 42 */       msgPre = BungeeEssentials.getInstance().getMessenger().filter(ProxyServer.getInstance().getPlayer(getSender()), msgPre, Messenger.ChatType.STAFF);
/* 43 */       if (msgPre != null) {
/* 44 */         TextComponent msg = Dictionary.formatMsg(Dictionary.FORMAT_STAFF_CHAT, new String[] { "SERVER", getServer(), "SENDER", getSender(), "MESSAGE", msgPre });
/* 45 */         ProxyServer.getInstance().getPlayers().stream().filter(player -> (player.hasPermission("gssentials.admin.chat." + getServer()) || player.hasPermission("gssentials.admin.chat"))).forEach(player -> player.sendMessage((BaseComponent)msg));
/* 46 */         if (msg != null) {
/* 47 */           ProxyServer.getInstance().getConsole().sendMessage((BaseComponent)msg);
/* 48 */           Log.log("[SCHAT] " + msg.toLegacyText(), new Object[0]);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     return "StaffChatEvent(cancelled=" + isCancelled() + ", server=" + getServer() + ", sender=" + getSender() + ", message=" + getMessage() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\event\StaffChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */