/*    */ package com.pantherman594.gssentials.announcement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ public class Announcement
/*    */ {
/*    */   private Integer delay;
/*    */   private Integer interval;
/*    */   private String msg;
/*    */   private String server;
/*    */   
/*    */   public Announcement(Integer delay, Integer interval, String msg, String server) {
/* 42 */     this.delay = delay;
/* 43 */     this.interval = interval;
/* 44 */     this.msg = msg;
/* 45 */     this.server = server;
/*    */   }
/*    */   
/*    */   Integer getDelay() {
/* 49 */     return this.delay;
/*    */   }
/*    */   
/*    */   Integer getInterval() {
/* 53 */     return this.interval;
/*    */   }
/*    */   
/*    */   public String getMsg() {
/* 57 */     return this.msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<ProxiedPlayer> getPlayers() {
/* 64 */     if (this.server.equals("ALL"))
/* 65 */       return ProxyServer.getInstance().getPlayers(); 
/* 66 */     if (ProxyServer.getInstance().getServerInfo(this.server) != null) {
/* 67 */       return ProxyServer.getInstance().getServerInfo(this.server).getPlayers();
/*    */     }
/* 69 */     return new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\announcement\Announcement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */