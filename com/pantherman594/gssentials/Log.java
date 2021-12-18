/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.pantherman594.gssentials.regex.Handle;
/*     */ import com.pantherman594.gssentials.regex.Rule;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.logging.FileHandler;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Log
/*     */ {
/*  36 */   private static Joiner joiner = Joiner.on(", ");
/*     */ 
/*     */   
/*     */   private static Logger logger;
/*     */ 
/*     */   
/*     */   static void reset() {
/*  43 */     if (logger != null)
/*     */     {
/*  45 */       for (Handler handler : logger.getHandlers()) {
/*  46 */         if (handler instanceof FileHandler) {
/*  47 */           handler.close();
/*  48 */           logger.removeHandler(handler);
/*     */         } 
/*     */       } 
/*     */     }
/*  52 */     logger = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean setup() {
/*  61 */     File logDir = new File(BungeeEssentials.getInstance().getDataFolder(), "chat");
/*  62 */     File logFile = new File(logDir, "chat.log");
/*  63 */     if (!logDir.exists()) {
/*  64 */       logDir.mkdir();
/*     */     }
/*     */     
/*  67 */     if (logFile.exists()) {
/*     */       try {
/*  69 */         if (logFile.length() > 0L) {
/*  70 */           Files.move(logFile.toPath(), (new File(logDir, "chat-" + System.currentTimeMillis() + ".log")).toPath(), new java.nio.file.CopyOption[0]);
/*  71 */           BungeeEssentials.getInstance().getLogger().log(Level.INFO, "Moved old log file to \"chat\" directory");
/*     */         } 
/*  73 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (!logFile.exists()) {
/*     */       try {
/*  80 */         logFile.createNewFile();
/*  81 */       } catch (IOException e) {
/*  82 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  86 */     Logger constructing = Logger.getLogger(BungeeEssentials.class.getSimpleName() + "ChatLogger");
/*  87 */     constructing.setUseParentHandlers(false);
/*     */ 
/*     */     
/*     */     try {
/*  91 */       FileHandler handler = new FileHandler(logFile.getPath(), logFile.exists());
/*  92 */       handler.setFormatter(new LogFormatter());
/*  93 */       constructing.addHandler(handler);
/*  94 */       logger = constructing;
/*  95 */       logger.setLevel(Level.ALL);
/*  96 */     } catch (IOException e) {
/*  97 */       return false;
/*     */     } 
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(ProxiedPlayer sender, Rule rule, Messenger.ChatType type) {
/* 110 */     if (BungeeEssentials.getInstance().contains(new String[] { "log" }) && logger != null) {
/* 111 */       String chatType = "";
/* 112 */       switch (type) {
/*     */         case ADVERTISEMENT:
/* 114 */           chatType = "a PM";
/*     */           break;
/*     */         case CURSING:
/* 117 */           chatType = "public chat";
/*     */           break;
/*     */         case REPLACE:
/* 120 */           chatType = "global chat";
/*     */           break;
/*     */         case COMMAND:
/* 123 */           chatType = "staff chat";
/*     */           break;
/*     */       } 
/*     */       
/* 127 */       String prefix = "[CHAT/";
/* 128 */       switch (rule.getHandle()) {
/*     */         case ADVERTISEMENT:
/* 130 */           prefix = prefix + "ADVERTISEMENT";
/*     */           break;
/*     */         case CURSING:
/* 133 */           prefix = prefix + "CURSING";
/*     */           break;
/*     */         case REPLACE:
/* 136 */           prefix = prefix + "FILTER";
/*     */           break;
/*     */         case COMMAND:
/* 139 */           prefix = prefix + "FILTER";
/*     */           break;
/*     */       } 
/* 142 */       log(prefix + "] {0} broke a chat rule in " + chatType + ": \"{1}\"", new Object[] { sender.getName(), joiner.join(rule.getMatches()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(String message, Object... args) {
/* 153 */     ProxyServer.getInstance().getScheduler().runAsync(BungeeEssentials.getInstance(), () -> {
/*     */           if (logger != null) {
/*     */             logger.log(Level.FINE, message, args);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static class LogFormatter
/*     */     extends Formatter
/*     */   {
/* 163 */     private final Calendar calendar = Calendar.getInstance();
/* 164 */     private final SimpleDateFormat format = new SimpleDateFormat("H:mm:s");
/*     */ 
/*     */ 
/*     */     
/*     */     public String format(LogRecord record) {
/* 169 */       return this.format.format(this.calendar.getTime()) + " " + MessageFormat.format(record.getMessage(), record.getParameters()) + System.lineSeparator();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */