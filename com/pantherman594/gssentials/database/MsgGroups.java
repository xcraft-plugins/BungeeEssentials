/*     */ package com.pantherman594.gssentials.database;
/*     */ 
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MsgGroups
/*     */   extends Database
/*     */ {
/*     */   private static final String SETUP_SQL = "(`groupname` VARCHAR(36) NOT NULL,`owner` VARCHAR(36) NOT NULL,`members` TEXT NOT NULL,`invited` TEXT NOT NULL";
/*     */   
/*     */   public MsgGroups() {
/*  24 */     super("msggroups", "(`groupname` VARCHAR(36) NOT NULL,`owner` VARCHAR(36) NOT NULL,`members` TEXT NOT NULL,`invited` TEXT NOT NULL", "groupname");
/*     */   }
/*     */   
/*     */   public MsgGroups(String url, String username, String password, String prefix) {
/*  28 */     super(prefix + "msggroups", "(`groupname` VARCHAR(36) NOT NULL,`owner` VARCHAR(36) NOT NULL,`members` TEXT NOT NULL,`invited` TEXT NOT NULL", "groupname", url, username, password);
/*     */   }
/*     */   
/*     */   public boolean createDataNotExist(String groupName) {
/*  32 */     return (getData(groupName, "groupname") != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object getData(String groupName, String label) {
/*  37 */     return getData("groupname", groupName, label);
/*     */   }
/*     */   
/*     */   private void setData(String groupName, String label, Object labelVal) {
/*  41 */     setData("groupname", groupName, label, labelVal);
/*     */   }
/*     */   
/*     */   public boolean create(String groupName) {
/*  45 */     if (getData("groupname", groupName, "groupname") != null) {
/*  46 */       return true;
/*     */     }
/*     */     
/*  49 */     Connection conn = getSQLConnection();
/*     */     
/*  51 */     try (PreparedStatement ps = conn.prepareStatement("INSERT INTO " + this.tableName + " (groupname, owner, members, invited) VALUES (?,?,?,?);")) {
/*     */       
/*  53 */       setValues(ps, new Object[] { groupName, "", "", "" });
/*  54 */       ps.executeUpdate();
/*  55 */       return true;
/*  56 */     } catch (SQLException e) {
/*  57 */       e.printStackTrace();
/*     */       
/*  59 */       return false;
/*     */     } 
/*     */   }
/*     */   public void remove(String groupName) {
/*  63 */     if (getData("groupname", groupName, "groupname") == null) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     Connection conn = getSQLConnection();
/*     */     
/*  69 */     try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + this.tableName + " WHERE groupname = ?;")) {
/*     */       
/*  71 */       setValues(ps, new Object[] { groupName });
/*  72 */       ps.executeUpdate();
/*  73 */     } catch (SQLException e) {
/*  74 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void convert() {
/*  79 */     if (this.isNewMySql) {
/*  80 */       MsgGroups oldMG = new MsgGroups();
/*  81 */       List<Object> groups = oldMG.listAllData("groupname");
/*  82 */       if (groups != null && !groups.isEmpty()) {
/*  83 */         BungeeEssentials.getInstance().getLogger().info("New MySQL configuration found. Converting " + groups.size() + " MsgGroups...");
/*  84 */         for (Object groupO : groups) {
/*  85 */           String groupName = (String)groupO;
/*  86 */           create(groupName);
/*  87 */           setOwner(groupName, oldMG.getOwner(groupName));
/*  88 */           setMembers(groupName, oldMG.getMembers(groupName));
/*  89 */           setInvited(groupName, oldMG.getInvited(groupName));
/*     */         } 
/*  91 */         BungeeEssentials.getInstance().getLogger().info("MsgGroup conversion complete!");
/*     */       } 
/*     */     } else {
/*  94 */       BungeeEssentials.getInstance().getLogger().info("A database conversion was requested, but no empty database was found. If you want to convert, please delete the existing MySQL database.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setName(String groupName, String newName) {
/*  99 */     setData(groupName, "groupname", newName);
/*     */   }
/*     */   
/*     */   public String getOwner(String groupName) {
/* 103 */     return (String)getData(groupName, "owner");
/*     */   }
/*     */   
/*     */   public void setOwner(String groupName, String uuid) {
/* 107 */     setData(groupName, "owner", uuid);
/* 108 */     addMember(groupName, uuid);
/*     */   }
/*     */   
/*     */   public Set<String> getMembers(String groupName) {
/* 112 */     return setFromString((String)getData(groupName, "members"));
/*     */   }
/*     */   
/*     */   public void addMember(String groupName, String uuid) {
/* 116 */     Set<String> members = getMembers(groupName);
/* 117 */     members.add(uuid);
/* 118 */     setMembers(groupName, members);
/*     */   }
/*     */   
/*     */   public void removeMember(String groupName, String uuid) {
/* 122 */     Set<String> members = getMembers(groupName);
/* 123 */     members.remove(uuid);
/* 124 */     if (members.isEmpty()) {
/* 125 */       remove(groupName);
/*     */     } else {
/* 127 */       setMembers(groupName, members);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMembers(String groupName, Set<String> members) {
/* 132 */     setData(groupName, "members", Dictionary.combine(";", members));
/*     */   }
/*     */   
/*     */   public Set<String> getInvited(String groupName) {
/* 136 */     return setFromString((String)getData(groupName, "invited"));
/*     */   }
/*     */   
/*     */   public void addInvited(String groupName, String uuid) {
/* 140 */     Set<String> invited = getInvited(groupName);
/* 141 */     invited.add(uuid);
/* 142 */     setInvited(groupName, invited);
/*     */   }
/*     */   
/*     */   public void removeInvited(String groupName, String uuid) {
/* 146 */     Set<String> invited = getInvited(groupName);
/* 147 */     invited.remove(uuid);
/* 148 */     setInvited(groupName, invited);
/*     */   }
/*     */   
/*     */   public void setInvited(String groupName, Set<String> invited) {
/* 152 */     setData(groupName, "invited", Dictionary.combine(";", invited));
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\database\MsgGroups.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */