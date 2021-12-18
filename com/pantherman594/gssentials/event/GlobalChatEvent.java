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
/*    */ 
/*    */ public class GlobalChatEvent
/*    */   extends BEChatEvent
/*    */   implements Cancellable
/*    */ {
/*    */   public GlobalChatEvent(String server, String sender, String msgPre) {
/* 37 */     super(server, sender, msgPre);
/*    */   }
/*    */   
/*    */   public void execute() {
/* 41 */     String msgPre = getMessage();
/* 42 */     if (msgPre != null) {
/* 43 */       msgPre = BungeeEssentials.getInstance().getMessenger().filter(ProxyServer.getInstance().getPlayer(getSender()), msgPre, Messenger.ChatType.GLOBAL);
/* 44 */       if (msgPre != null) {
/* 45 */         TextComponent msg = Dictionary.formatMsg(Dictionary.FORMAT_GCHAT, new String[] { "SERVER", getServer(), "SENDER", getSender(), "MESSAGE", msgPre });
/* 46 */         ProxiedPlayer senderP = ProxyServer.getInstance().getPlayer(getSender());
/* 47 */         ProxyServer.getInstance().getPlayers().stream().filter(player -> ((player.hasPermission("gssentials.chat." + getServer()) || player.hasPermission("gssentials.chat")) && (senderP == null || !BungeeEssentials.getInstance().contains(new String[] { "ignore" }) || !this.pD.isIgnored(player.getUniqueId().toString(), senderP.getUniqueId().toString())))).forEach(player -> player.sendMessage((BaseComponent)msg));
/* 48 */         if (msg != null) {
/* 49 */           ProxyServer.getInstance().getConsole().sendMessage((BaseComponent)msg);
/* 50 */           Log.log("[GCHAT] " + msg.toLegacyText(), new Object[0]);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return "ChatEvent(cancelled=" + isCancelled() + ", server=" + getServer() + ", sender=" + getSender() + ", message=" + getMessage() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\event\GlobalChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */