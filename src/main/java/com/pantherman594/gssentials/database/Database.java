/*     */ package com.pantherman594.gssentials.database;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLConnection;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
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
/*     */ public abstract class Database
/*     */ {
/*     */   String tableName;
/*     */   boolean isNewMySql;
/*     */   private String primary;
/*     */   private Connection connection;
/*     */   private boolean mysql;
/*     */   private String url;
/*     */   private String username;
/*     */   private String password;
/*  49 */   private int uses = 0;
/*     */   
/*     */   public Database(String tableName, String setupSql, String primary) {
/*  52 */     this.tableName = tableName;
/*  53 */     this.primary = primary;
/*  54 */     this.mysql = false;
/*  55 */     load(setupSql, primary);
/*     */   }
/*     */   
/*     */   public Database(String tableName, String setupSql, String primary, String url, String username, String password) {
/*  59 */     this.tableName = tableName;
/*  60 */     this.primary = primary;
/*  61 */     this.mysql = true;
/*  62 */     this.url = url;
/*  63 */     this.username = username;
/*  64 */     this.password = password;
/*  65 */     load(setupSql, primary);
/*     */   }
/*     */   
/*     */   static Set<String> setFromString(String input) {
/*  69 */     Set<String> set = new HashSet<>();
/*  70 */     if (input != null && !input.equals("")) {
/*  71 */       Collections.addAll(set, input.split(";"));
/*     */     }
/*  73 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   Connection getSQLConnection() {
/*  78 */     File dbFile = new File(BungeeEssentials.getInstance().getDataFolder(), this.tableName + ".db");
/*  79 */     if (!dbFile.exists()) {
/*     */       try {
/*  81 */         dbFile.createNewFile();
/*  82 */       } catch (IOException e) {
/*  83 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     try {
/*  87 */       if (this.uses > 250) {
/*  88 */         this.connection.close();
/*     */       }
/*     */       
/*  91 */       if (this.connection != null && !this.connection.isClosed()) {
/*  92 */         this.uses++;
/*  93 */         return this.connection;
/*     */       } 
/*     */       
/*  96 */       if (!this.mysql) {
/*  97 */         File sqliteLib = new File(BungeeEssentials.getInstance().getLibDir(), "sqlite-jdbc-3.8.11.2.jar");
/*     */         
/*  99 */         if (!sqliteLib.exists()) {
/* 100 */           URLConnection con; BungeeEssentials.getInstance().getLogger().log(Level.INFO, "Downloading SQLite JDBC library...");
/* 101 */           String dlLink = "https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.8.11.2.jar";
/*     */           
/*     */           try {
/* 104 */             URL url = new URL(dlLink);
/* 105 */             con = url.openConnection();
/* 106 */           } catch (IOException e) {
/* 107 */             BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Invalid SQLite download link. Please contact plugin author.");
/* 108 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 112 */           try(InputStream in = con.getInputStream(); 
/* 113 */               FileOutputStream out = new FileOutputStream(sqliteLib)) {
/*     */             
/* 115 */             byte[] buffer = new byte[1024];
/*     */             int size;
/* 117 */             while ((size = in.read(buffer)) != -1) {
/* 118 */               out.write(buffer, 0, size);
/*     */             }
/* 120 */           } catch (IOException e) {
/* 121 */             BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Failed to download update, please download it manually from https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.8.11.2.jar and put it in the /plugins/BungeeEssentials/lib folder.");
/* 122 */             BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Error message: ");
/* 123 */             e.printStackTrace();
/* 124 */             return null;
/*     */           } 
/*     */         } 
/*     */         
/* 128 */         URLClassLoader loader = new URLClassLoader(new URL[] { sqliteLib.toURI().toURL() });
/* 129 */         Method m = DriverManager.class.getDeclaredMethod("getConnection", new Class[] { String.class, Properties.class, Class.class });
/* 130 */         m.setAccessible(true);
/*     */         
/* 132 */         this.connection = (Connection)m.invoke(null, new Object[] { "jdbc:sqlite:" + dbFile.getPath(), new Properties(), Class.forName("org.sqlite.JDBC", true, loader) });
/*     */         
/* 134 */         this.uses = 0;
/* 135 */         return this.connection;
/*     */       } 
/* 137 */       Class.forName("com.mysql.jdbc.Driver");
/* 138 */       this.connection = DriverManager.getConnection("jdbc:mysql://" + this.url, this.username, this.password);
/*     */       
/* 140 */       DatabaseMetaData conMeta = this.connection.getMetaData();
/* 141 */       ResultSet findTable = conMeta.getTables(null, null, this.tableName, null);
/* 142 */       this.isNewMySql = !findTable.next();
/*     */       
/* 144 */       this.uses = 0;
/* 145 */       return this.connection;
/*     */     
/*     */     }
/* 148 */     catch (ClassNotFoundException e) {
/* 149 */       BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "You are missing necessary libraries. If using SQLite, download it from https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.8.11.2.jar and put it in the /plugins/BungeeEssentials/lib folder.");
/* 150 */     } catch (Exception e) {
/* 151 */       BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Exception on SQL initialize", e);
/*     */     } 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */   
/*     */   public abstract boolean createDataNotExist(String paramString);
/*     */   
/*     */   public List<Object> listAllData(String label) {
/* 160 */     List<Object> datas = new ArrayList();
/*     */     
/* 162 */     Connection conn = getSQLConnection();
/*     */     
/* 164 */     try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + this.tableName + ";"); 
/* 165 */         ResultSet rs = ps.executeQuery()) {
/*     */ 
/*     */       
/* 168 */       while (rs.next()) {
/* 169 */         datas.add(rs.getObject(label));
/*     */       }
/*     */       
/* 172 */       if (datas.size() > 0) {
/* 173 */         return datas;
/*     */       }
/*     */     }
/* 176 */     catch (SQLException e) {
/* 177 */       BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Couldn't execute SQLite statement: ", e);
/*     */     } 
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Object> getDataMultiple(String key, String keyVal, String label) {
/* 184 */     List<Object> datas = new ArrayList();
/*     */     
/* 186 */     Connection conn = getSQLConnection();
/*     */     
/* 188 */     try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + this.tableName + " WHERE " + key + " = ?;")) {
/*     */       
/* 190 */       ps.setObject(1, keyVal);
/* 191 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 193 */       while (rs.next()) {
/* 194 */         if (rs.getString(key).equals(keyVal)) {
/* 195 */           datas.add(rs.getObject(label));
/*     */         }
/*     */       } 
/*     */       
/* 199 */       if (datas.size() > 0) {
/* 200 */         return datas;
/*     */       }
/*     */     }
/* 203 */     catch (SQLException e) {
/* 204 */       BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Couldn't execute SQLite statement: ", e);
/*     */     } 
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   public Object getData(String key, String keyVal, String label) {
/* 210 */     List<Object> datas = getDataMultiple(key, keyVal, label);
/* 211 */     if (datas != null) {
/* 212 */       return datas.get(0);
/*     */     }
/* 214 */     return null;
/*     */   }
/*     */   
/*     */   public void setData(String key, String keyVal, String label, Object labelVal) {
/* 218 */     if (key.equals(this.primary) && !createDataNotExist(keyVal)) {
/*     */       return;
/*     */     }
/*     */     
/* 222 */     Connection conn = getSQLConnection();
/*     */     
/* 224 */     try (PreparedStatement ps = conn.prepareStatement("UPDATE " + this.tableName + " SET " + label + " = ? WHERE " + key + " = ?;")) {
/*     */       
/* 226 */       ps.setObject(1, labelVal);
/* 227 */       ps.setObject(2, keyVal);
/* 228 */       ps.executeUpdate();
/* 229 */     } catch (SQLException e) {
/* 230 */       BungeeEssentials.getInstance().getLogger().log(Level.SEVERE, "Couldn't execute SQLite statement: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setValues(PreparedStatement ps, Object... values) throws SQLException {
/* 235 */     setValues(1, ps, values);
/*     */   }
/*     */   
/*     */   void setValues(int start, PreparedStatement ps, Object... values) throws SQLException {
/* 239 */     for (int i = 0; i < values.length; i++) {
/* 240 */       ps.setObject(i + start, values[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void load(String setupSql, String primary) {
/* 245 */     this.connection = getSQLConnection();
/*     */     
/* 247 */     try (Statement s = this.connection.createStatement()) {
/* 248 */       s.executeUpdate("CREATE TABLE IF NOT EXISTS " + this.tableName + " " + setupSql + ",PRIMARY KEY (`" + primary + "`));");
/* 249 */     } catch (SQLException e) {
/* 250 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\database\Database.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */