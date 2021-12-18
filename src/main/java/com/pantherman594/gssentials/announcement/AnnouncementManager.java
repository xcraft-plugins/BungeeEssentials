/*     */ package com.pantherman594.gssentials.announcement;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.Plugin;
/*     */ import net.md_5.bungee.config.Configuration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnouncementManager
/*     */ {
/*  35 */   private Map<String, Announcement> anncs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnouncementManager() {
/*  41 */     this.anncs.clear();
/*  42 */     Configuration anncSection = BungeeEssentials.getInstance().getMessages().getSection("announcements");
/*  43 */     for (String annc : anncSection.getKeys()) {
/*  44 */       int delay = anncSection.getInt(annc + ".delay");
/*  45 */       int interval = anncSection.getInt(annc + ".interval");
/*  46 */       String msg = anncSection.getString(annc + ".message");
/*  47 */       String server = "ALL";
/*  48 */       if (!anncSection.getString(annc + ".server").equals("")) {
/*  49 */         server = anncSection.getString(annc + ".server");
/*     */       }
/*  51 */       register(annc, new Announcement(Integer.valueOf(delay), Integer.valueOf(interval), msg, server));
/*     */     } 
/*  53 */     if (this.anncs.size() > 0) {
/*  54 */       BungeeEssentials.getInstance().getLogger().log(Level.INFO, "Loaded {0} announcements from config", Integer.valueOf(this.anncs.size()));
/*  55 */       scheduleAnnc();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void register(String anncName, Announcement annc) {
/*  66 */     this.anncs.put(anncName, annc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scheduleAnnc() {
/*  73 */     for (Iterator<String> iterator = this.anncs.keySet().iterator(); iterator.hasNext(); ) { String anncName = iterator.next();
/*  74 */       Announcement annc = this.anncs.get(anncName);
/*  75 */       ProxyServer.getInstance().getScheduler().schedule((Plugin)BungeeEssentials.getInstance(), () -> { annc(annc.getPlayers(), anncName, new String[] { annc.getMsg() }); scheduleAnnc(anncName, annc); }annc
/*     */ 
/*     */           
/*  78 */           .getDelay().intValue(), TimeUnit.SECONDS); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scheduleAnnc(String anncName, Announcement annc) {
/*  89 */     ProxyServer.getInstance().getScheduler().schedule((Plugin)BungeeEssentials.getInstance(), () -> { annc(annc.getPlayers(), anncName, new String[] { annc.getMsg() }); scheduleAnnc(anncName, annc); }annc
/*     */ 
/*     */         
/*  92 */         .getInterval().intValue(), TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void annc(Collection<ProxiedPlayer> players, String anncName, String... msg) {
/* 103 */     for (String singMsg : msg) {
/* 104 */       if (!players.isEmpty()) {
/* 105 */         players.stream().filter(p -> (p.hasPermission("gssentials.announcement") || p.hasPermission("gssentials.announcement." + anncName))).forEach(p -> p.sendMessage((BaseComponent)Dictionary.format(Dictionary.FORMAT_ALERT, new String[] { "MESSAGE", singMsg })));
/*     */       }
/* 107 */       ProxyServer.getInstance().getConsole().sendMessage((BaseComponent)Dictionary.format(Dictionary.FORMAT_ALERT, new String[] { "MESSAGE", singMsg }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Announcement> getAnncs() {
/* 116 */     return this.anncs;
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\announcement\AnnouncementManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */