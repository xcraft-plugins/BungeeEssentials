/*     */ package com.pantherman594.gssentials.database;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*     */   extends Database
/*     */ {
/*     */   private static final String SETUP_SQL = "(`uuid` VARCHAR(36) NOT NULL,`lastname` VARCHAR(32) NOT NULL,`ip` VARCHAR(32) NOT NULL,`friends` TEXT NOT NULL,`outRequests` TEXT NOT NULL,`inRequests` TEXT NOT NULL,`ignores` TEXT NOT NULL,`hidden` INT(1) NOT NULL,`spy` INT(1) NOT NULL,`cSpy` INT(1) NOT NULL,`globalChat` INT(1) NOT NULL,`staffChat` INT(1) NOT NULL,`muted` INT(1) NOT NULL,`msging` INT(1) NOT NULL";
/*  53 */   private Map<String, String> lastname = new HashMap<>();
/*  54 */   private Map<String, String> ip = new HashMap<>();
/*  55 */   private Map<String, String> friends = new HashMap<>();
/*  56 */   private Map<String, String> outRequests = new HashMap<>();
/*  57 */   private Map<String, String> inRequests = new HashMap<>();
/*  58 */   private Map<String, String> ignores = new HashMap<>();
/*  59 */   private Map<String, Integer> hidden = new HashMap<>();
/*  60 */   private Map<String, Integer> spy = new HashMap<>();
/*  61 */   private Map<String, Integer> cSpy = new HashMap<>();
/*  62 */   private Map<String, Integer> globalChat = new HashMap<>();
/*  63 */   private Map<String, Integer> staffChat = new HashMap<>();
/*  64 */   private Map<String, Integer> muted = new HashMap<>();
/*  65 */   private Map<String, Integer> msging = new HashMap<>();
/*     */   
/*     */   public PlayerData() {
/*  68 */     super("playerdata", "(`uuid` VARCHAR(36) NOT NULL,`lastname` VARCHAR(32) NOT NULL,`ip` VARCHAR(32) NOT NULL,`friends` TEXT NOT NULL,`outRequests` TEXT NOT NULL,`inRequests` TEXT NOT NULL,`ignores` TEXT NOT NULL,`hidden` INT(1) NOT NULL,`spy` INT(1) NOT NULL,`cSpy` INT(1) NOT NULL,`globalChat` INT(1) NOT NULL,`staffChat` INT(1) NOT NULL,`muted` INT(1) NOT NULL,`msging` INT(1) NOT NULL", "uuid");
/*     */   }
/*     */   
/*     */   public PlayerData(String url, String username, String password, String prefix) {
/*  72 */     super(prefix + "playerdata", "(`uuid` VARCHAR(36) NOT NULL,`lastname` VARCHAR(32) NOT NULL,`ip` VARCHAR(32) NOT NULL,`friends` TEXT NOT NULL,`outRequests` TEXT NOT NULL,`inRequests` TEXT NOT NULL,`ignores` TEXT NOT NULL,`hidden` INT(1) NOT NULL,`spy` INT(1) NOT NULL,`cSpy` INT(1) NOT NULL,`globalChat` INT(1) NOT NULL,`staffChat` INT(1) NOT NULL,`muted` INT(1) NOT NULL,`msging` INT(1) NOT NULL", "uuid", url, username, password);
/*     */   }
/*     */   
/*     */   public void convert() {
/*  76 */     if (this.isNewMySql) {
/*  77 */       PlayerData oldPD = new PlayerData();
/*  78 */       List<Object> uuids = oldPD.listAllData("uuid");
/*  79 */       if (uuids != null && !uuids.isEmpty()) {
/*  80 */         BungeeEssentials.getInstance().getLogger().info("New MySQL configuration found. Converting " + uuids.size() + " PlayerDatas...");
/*  81 */         for (Object uuidO : uuids) {
/*  82 */           String uuid = (String)uuidO;
/*  83 */           setName(uuid, oldPD.getName(uuid));
/*  84 */           setIp(uuid, oldPD.getIp(uuid));
/*  85 */           setFriends(uuid, oldPD.getFriends(uuid));
/*  86 */           setOutRequests(uuid, oldPD.getOutRequests(uuid));
/*  87 */           setInRequests(uuid, oldPD.getInRequests(uuid));
/*  88 */           setIgnores(uuid, oldPD.getIgnores(uuid));
/*  89 */           setHidden(uuid, oldPD.isHidden(uuid));
/*  90 */           setSpy(uuid, oldPD.isSpy(uuid));
/*  91 */           setCSpy(uuid, oldPD.isCSpy(uuid));
/*  92 */           setGlobalChat(uuid, oldPD.isGlobalChat(uuid));
/*  93 */           setStaffChat(uuid, oldPD.isStaffChat(uuid));
/*  94 */           setMuted(uuid, oldPD.isMuted(uuid));
/*  95 */           setMsging(uuid, oldPD.isMsging(uuid));
/*     */         } 
/*  97 */         BungeeEssentials.getInstance().getLogger().info("PlayerData conversion complete!");
/*     */       } 
/*     */     } else {
/* 100 */       BungeeEssentials.getInstance().getLogger().info("A database conversion was requested, but no empty database was found. If you want to convert, please delete the existing MySQL database.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createDataNotExist(String uuid) {
/* 106 */     if (getData("uuid", uuid, "uuid") != null) {
/* 107 */       return true;
/*     */     }
/*     */     
/* 110 */     ProxiedPlayer p = null;
/* 111 */     if (!uuid.equals("CONSOLE")) {
/* 112 */       p = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
/*     */     }
/*     */     
/* 115 */     Connection conn = getSQLConnection();
/*     */     
/* 117 */     try (PreparedStatement ps = conn.prepareStatement("INSERT INTO " + this.tableName + " (uuid, lastname, ip, friends, outRequests, inRequests, ignores, hidden, spy, cSpy, globalChat, staffChat, muted, msging) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);")) {
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (uuid.equals("CONSOLE")) {
/* 122 */         setValues(ps, new Object[] { uuid, "Console", "127.0.0.1" });
/* 123 */       } else if (p != null) {
/* 124 */         setValues(ps, new Object[] { uuid, p.getName(), p.getAddress().getAddress().getHostAddress() });
/*     */       } else {
/* 126 */         setValues(ps, new Object[] { uuid, "", "" });
/*     */       } 
/* 128 */       insertDefaults(ps);
/* 129 */       ps.executeUpdate();
/* 130 */       return true;
/* 131 */     } catch (SQLException e) {
/* 132 */       e.printStackTrace();
/*     */       
/* 134 */       return false;
/*     */     } 
/*     */   }
/*     */   private void insertDefaults(PreparedStatement ps) throws SQLException {
/* 138 */     setValues(4, ps, new Object[] { "", "", "", "", Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true) });
/*     */   }
/*     */   
/*     */   private Map<String, Map> getData() {
/* 142 */     Map<String, Map> data = new HashMap<>();
/* 143 */     data.put("lastname", this.lastname);
/* 144 */     data.put("ip", this.ip);
/* 145 */     data.put("friends", this.friends);
/* 146 */     data.put("outRequests", this.outRequests);
/* 147 */     data.put("inRequests", this.inRequests);
/* 148 */     data.put("ignores", this.ignores);
/* 149 */     data.put("hidden", this.hidden);
/* 150 */     data.put("spy", this.spy);
/* 151 */     data.put("cSpy", this.cSpy);
/* 152 */     data.put("globalChat", this.globalChat);
/* 153 */     data.put("staffChat", this.staffChat);
/* 154 */     data.put("muted", this.muted);
/* 155 */     data.put("msging", this.msging);
/* 156 */     return data;
/*     */   }
/*     */   
/*     */   private Object getData(String uuid, String label) {
/* 160 */     if (getData().containsKey(label)) {
/* 161 */       if (!((Map)getData().get(label)).containsKey(uuid)) {
/* 162 */         ((Map<String, Object>)getData().get(label)).put(uuid, getData("uuid", uuid, label));
/*     */       }
/* 164 */       return ((Map)getData().get(label)).get(uuid);
/*     */     } 
/* 166 */     return getData("uuid", uuid, label);
/*     */   }
/*     */   
/*     */   private void setData(String uuid, String label, Object labelVal) {
/* 170 */     if (getData().containsKey(label)) {
/* 171 */       ((Map<String, Object>)getData().get(label)).put(uuid, labelVal);
/*     */     }
/* 173 */     setData("uuid", uuid, label, labelVal);
/*     */   }
/*     */   
/*     */   public String getIp(String uuid) {
/* 177 */     return (String)getData(uuid, "ip");
/*     */   }
/*     */   
/*     */   public void setIp(String uuid, String ip) {
/* 181 */     setData(uuid, "ip", ip);
/*     */   }
/*     */   
/*     */   public Set<String> getFriends(String uuid) {
/* 185 */     return setFromString((String)getData(uuid, "friends"));
/*     */   }
/*     */   
/*     */   public void addFriend(String uuid, String friend) {
/* 189 */     Set<String> friends = getFriends(uuid);
/* 190 */     friends.add(friend);
/* 191 */     setFriends(uuid, friends);
/*     */   }
/*     */   
/*     */   public void removeFriend(String uuid, String friend) {
/* 195 */     Set<String> friends = getFriends(uuid);
/* 196 */     friends.remove(friend);
/* 197 */     setFriends(uuid, friends);
/*     */   }
/*     */   
/*     */   public void setFriends(String uuid, Set<String> friends) {
/* 201 */     setData(uuid, "friends", Dictionary.combine(";", friends));
/*     */   }
/*     */   
/*     */   public Set<String> getOutRequests(String uuid) {
/* 205 */     return setFromString((String)getData(uuid, "outRequests"));
/*     */   }
/*     */   
/*     */   public void addOutRequest(String uuid, String outRequest) {
/* 209 */     Set<String> outRequests = getOutRequests(uuid);
/* 210 */     outRequests.add(outRequest);
/* 211 */     setOutRequests(uuid, outRequests);
/*     */   }
/*     */   
/*     */   public void removeOutRequest(String uuid, String outRequest) {
/* 215 */     Set<String> outRequests = getOutRequests(uuid);
/* 216 */     outRequests.remove(outRequest);
/* 217 */     setOutRequests(uuid, outRequests);
/*     */   }
/*     */   
/*     */   public void setOutRequests(String uuid, Set<String> outRequests) {
/* 221 */     setData(uuid, "outRequests", Dictionary.combine(";", outRequests));
/*     */   }
/*     */   
/*     */   public Set<String> getInRequests(String uuid) {
/* 225 */     return setFromString((String)getData(uuid, "inRequests"));
/*     */   }
/*     */   
/*     */   public void addInRequest(String uuid, String inRequest) {
/* 229 */     Set<String> inRequests = getInRequests(uuid);
/* 230 */     inRequests.add(inRequest);
/* 231 */     setInRequests(uuid, inRequests);
/*     */   }
/*     */   
/*     */   public void removeInRequest(String uuid, String inRequest) {
/* 235 */     Set<String> inRequests = getInRequests(uuid);
/* 236 */     inRequests.remove(inRequest);
/* 237 */     setInRequests(uuid, inRequests);
/*     */   }
/*     */   
/*     */   public void setInRequests(String uuid, Set<String> inRequests) {
/* 241 */     setData(uuid, "inRequests", Dictionary.combine(";", inRequests));
/*     */   }
/*     */   
/*     */   public boolean isMuted(String uuid) {
/* 245 */     return (((Integer)getData(uuid, "muted")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setMuted(String uuid, boolean muted) {
/* 249 */     setData(uuid, "muted", Integer.valueOf(muted ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleMuted(String uuid) {
/* 253 */     boolean status = !isMuted(uuid);
/* 254 */     setMuted(uuid, status);
/* 255 */     return status;
/*     */   }
/*     */   
/*     */   public Set<String> getIgnores(String uuid) {
/* 259 */     return setFromString((String)getData(uuid, "ignores"));
/*     */   }
/*     */   
/*     */   public boolean isIgnored(String uuid, String ignoreUuid) {
/* 263 */     return getIgnores(uuid).contains(ignoreUuid);
/*     */   }
/*     */   
/*     */   public void setIgnored(String uuid, String ignoreUuid, boolean status) {
/* 267 */     Set<String> ignoreSet = setFromString((String)getData(uuid, "ignores"));
/* 268 */     if (status) {
/* 269 */       ignoreSet.add(ignoreUuid);
/*     */     } else {
/* 271 */       ignoreSet.remove(ignoreUuid);
/*     */     } 
/* 273 */     setIgnores(uuid, ignoreSet);
/*     */   }
/*     */   
/*     */   public void setIgnores(String uuid, Set<String> ignoreSet) {
/* 277 */     setData(uuid, "ignores", Dictionary.combine(";", ignoreSet));
/*     */   }
/*     */   
/*     */   public boolean toggleIgnore(String uuid, String ignoreUuid) {
/* 281 */     boolean status = !isIgnored(uuid, ignoreUuid);
/* 282 */     setIgnored(uuid, ignoreUuid, status);
/* 283 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isHidden(String uuid) {
/* 287 */     return (((Integer)getData(uuid, "hidden")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setHidden(String uuid, boolean hidden) {
/* 291 */     setData(uuid, "hidden", Integer.valueOf(hidden ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleHidden(String uuid) {
/* 295 */     boolean status = !isHidden(uuid);
/* 296 */     setHidden(uuid, status);
/* 297 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isSpy(String uuid) {
/* 301 */     return (((Integer)getData(uuid, "spy")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setSpy(String uuid, boolean spy) {
/* 305 */     setData(uuid, "spy", Integer.valueOf(spy ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleSpy(String uuid) {
/* 309 */     boolean status = !isSpy(uuid);
/* 310 */     setSpy(uuid, status);
/* 311 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isCSpy(String uuid) {
/* 315 */     return (((Integer)getData(uuid, "cSpy")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setCSpy(String uuid, boolean cSpy) {
/* 319 */     setData(uuid, "cSpy", Integer.valueOf(cSpy ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleCSpy(String uuid) {
/* 323 */     boolean status = !isCSpy(uuid);
/* 324 */     setCSpy(uuid, status);
/* 325 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isGlobalChat(String uuid) {
/* 329 */     return (((Integer)getData(uuid, "globalChat")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setGlobalChat(String uuid, boolean globalChat) {
/* 333 */     setData(uuid, "globalChat", Integer.valueOf(globalChat ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleGlobalChat(String uuid) {
/* 337 */     boolean status = !isGlobalChat(uuid);
/* 338 */     setGlobalChat(uuid, status);
/* 339 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isStaffChat(String uuid) {
/* 343 */     return (((Integer)getData(uuid, "staffChat")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setStaffChat(String uuid, boolean staffChat) {
/* 347 */     setData(uuid, "staffChat", Integer.valueOf(staffChat ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleStaffChat(String uuid) {
/* 351 */     boolean status = !isStaffChat(uuid);
/* 352 */     setStaffChat(uuid, status);
/* 353 */     return status;
/*     */   }
/*     */   
/*     */   public boolean isMsging(String uuid) {
/* 357 */     return (((Integer)getData(uuid, "msging")).intValue() == 1);
/*     */   }
/*     */   
/*     */   public void setMsging(String uuid, boolean msging) {
/* 361 */     setData(uuid, "msging", Integer.valueOf(msging ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean toggleMsging(String uuid) {
/* 365 */     boolean status = !isMsging(uuid);
/* 366 */     setMsging(uuid, status);
/* 367 */     return status;
/*     */   }
/*     */   
/*     */   public String getName(String uuid) {
/* 371 */     return (String)getData(uuid, "lastname");
/*     */   }
/*     */   
/*     */   public void setName(String uuid, String name) {
/* 375 */     setData(uuid, "lastname", name);
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\database\PlayerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */