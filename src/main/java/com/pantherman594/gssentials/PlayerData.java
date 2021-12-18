/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.pantherman594.gssentials.database.PlayerData;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.config.Configuration;
/*     */ import net.md_5.bungee.config.ConfigurationProvider;
/*     */ import net.md_5.bungee.config.YamlConfiguration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerData
/*     */ {
/*  41 */   private static PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*     */ 
/*     */   
/*     */   private String uuid;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlayerData(String uuid, String name) {
/*  50 */     this.uuid = uuid;
/*  51 */     BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Please update any plugin that uses BungeeEssentials' old PlayerData. This will NOT be supported in future versions, and the plugin MAY NOT work correctly.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void convertPlayerData() {
/*  59 */     PlayerData pD = new PlayerData();
/*  60 */     pD.createDataNotExist("CONSOLE");
/*  61 */     File playerDir = new File(BungeeEssentials.getInstance().getDataFolder() + File.separator + "playerdata");
/*  62 */     if (playerDir.exists()) {
/*  63 */       for (File playerFile : playerDir.listFiles()) {
/*  64 */         if (playerFile.getName().endsWith(".yml")) {
/*     */           Configuration config;
/*     */           
/*  67 */           String uuid = playerFile.getName().substring(0, playerFile.getName().length() - 4);
/*     */           
/*     */           try {
/*  70 */             config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerFile);
/*  71 */           } catch (IOException e) {
/*  72 */             BungeeEssentials.getInstance().getLogger().warning("Unable to load " + uuid + "'s data.");
/*     */             return;
/*     */           } 
/*  75 */           for (String friend : config.getStringList("friends")) {
/*  76 */             pD.addFriend(uuid, friend);
/*     */           }
/*  78 */           for (String request : config.getStringList("requests.out")) {
/*  79 */             pD.addOutRequest(uuid, request);
/*     */           }
/*  81 */           for (String request : config.getStringList("requests.in")) {
/*  82 */             pD.addInRequest(uuid, request);
/*     */           }
/*  84 */           for (String ignore : config.getStringList("ignorelist")) {
/*  85 */             pD.setIgnored(uuid, ignore, true);
/*     */           }
/*  87 */           pD.setName(uuid, config.getString("lastname"));
/*  88 */           pD.setHidden(uuid, config.getBoolean("hidden"));
/*  89 */           pD.setSpy(uuid, config.getBoolean("spy"));
/*  90 */           pD.setCSpy(uuid, config.getBoolean("cspy"));
/*  91 */           pD.setGlobalChat(uuid, config.getBoolean("globalchat"));
/*  92 */           pD.setStaffChat(uuid, config.getBoolean("staffchat"));
/*  93 */           pD.setMuted(uuid, config.getBoolean("muted"));
/*  94 */           pD.setMsging(uuid, config.getBoolean("msging"));
/*     */         } 
/*  96 */       }  File playerDirArchive = new File(BungeeEssentials.getInstance().getDataFolder() + File.separator + "playerdata_old");
/*     */       try {
/*  98 */         Files.move(playerDir.toPath(), playerDirArchive.toPath(), new java.nio.file.CopyOption[0]);
/*  99 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Map<String, PlayerData> getDatas() {
/* 109 */     Map<String, PlayerData> playerDataList = new HashMap<>();
/* 110 */     for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
/* 111 */       playerDataList.put(p.getUniqueId().toString(), new PlayerData(p.getUniqueId().toString(), null));
/*     */     }
/* 113 */     return playerDataList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PlayerData getData(String uuid) {
/* 121 */     return new PlayerData(uuid, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PlayerData getData(UUID uuid) {
/* 129 */     return getData(uuid.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void setData(String uuid, PlayerData playerData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void clearData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean save() {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<String> getFriends() {
/* 162 */     Set<String> friends = pD.getFriends(this.uuid);
/* 163 */     return Arrays.asList(friends.toArray(new String[friends.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<String> getOutRequests() {
/* 171 */     Set<String> outRequests = pD.getOutRequests(this.uuid);
/* 172 */     return Arrays.asList(outRequests.toArray(new String[outRequests.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<String> getInRequests() {
/* 180 */     Set<String> inRequests = pD.getInRequests(this.uuid);
/* 181 */     return Arrays.asList(inRequests.toArray(new String[inRequests.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isMuted() {
/* 189 */     return pD.isMuted(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setMuted(boolean muted) {
/* 197 */     pD.setMuted(this.uuid, muted);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleMuted() {
/* 205 */     return pD.toggleMuted(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isIgnored(String uuid) {
/* 213 */     return pD.isIgnored(this.uuid, uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setIgnored(String uuid, boolean status) {
/* 221 */     pD.setIgnored(this.uuid, uuid, status);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleIgnore(String uuid) {
/* 229 */     return pD.toggleIgnore(this.uuid, uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isHidden() {
/* 237 */     return pD.isHidden(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setHidden(boolean hidden) {
/* 245 */     pD.setHidden(this.uuid, hidden);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleHidden() {
/* 253 */     return pD.toggleHidden(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isSpy() {
/* 261 */     return pD.isSpy(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setSpy(boolean spy) {
/* 269 */     pD.setSpy(this.uuid, spy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleSpy() {
/* 277 */     return pD.toggleSpy(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isCSpy() {
/* 285 */     return pD.isCSpy(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setCSpy(boolean cSpy) {
/* 293 */     pD.setCSpy(this.uuid, cSpy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleCSpy() {
/* 301 */     return pD.toggleCSpy(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isGlobalChat() {
/* 309 */     return pD.isGlobalChat(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setGlobalChat(boolean globalChat) {
/* 317 */     pD.setGlobalChat(this.uuid, globalChat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleGlobalChat() {
/* 325 */     return pD.toggleGlobalChat(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isStaffChat() {
/* 333 */     return pD.isStaffChat(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setStaffChat(boolean staffChat) {
/* 341 */     pD.setStaffChat(this.uuid, staffChat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean toggleStaffChat() {
/* 349 */     return pD.toggleStaffChat(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isMsging() {
/* 357 */     return pD.isMsging(this.uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setMsging(boolean msging) {
/* 365 */     pD.setMsging(this.uuid, msging);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getName() {
/* 373 */     return pD.getName(this.uuid);
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\PlayerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */