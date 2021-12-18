/*    */ package com.pantherman594.gssentials.event;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.database.PlayerData;
/*    */ import net.md_5.bungee.api.plugin.Cancellable;
/*    */ import net.md_5.bungee.api.plugin.Event;
/*    */ 
/*    */ 
/*    */ public abstract class BEChatEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 13 */   public PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*    */ 
/*    */   
/*    */   private String server;
/*    */ 
/*    */   
/*    */   private String sender;
/*    */   
/*    */   private String msg;
/*    */   
/*    */   private boolean cancelled;
/*    */ 
/*    */   
/*    */   public BEChatEvent(String server, String sender, String msgPre) {
/* 27 */     this.server = server;
/* 28 */     this.sender = sender;
/* 29 */     this.msg = msgPre;
/* 30 */     execute();
/*    */   }
/*    */   
/*    */   public abstract void execute();
/*    */   
/*    */   public String getServer() {
/* 36 */     return this.server;
/*    */   }
/*    */   
/*    */   public void setServer(String server) {
/* 40 */     this.server = server;
/*    */   }
/*    */   
/*    */   public String getSender() {
/* 44 */     return this.sender;
/*    */   }
/*    */   
/*    */   public void setSender(String sender) {
/* 48 */     this.sender = sender;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 52 */     return this.msg;
/*    */   }
/*    */   
/*    */   public void setMessage(String msg) {
/* 56 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 60 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 64 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public abstract String toString();
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\event\BEChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */