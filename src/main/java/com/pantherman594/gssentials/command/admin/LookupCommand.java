/*     */ package com.pantherman594.gssentials.command.admin;
/*     */ 
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import com.pantherman594.gssentials.command.ServerSpecificCommand;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
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
/*     */ public class LookupCommand
/*     */   extends ServerSpecificCommand
/*     */ {
/*     */   public LookupCommand() {
/*  34 */     super("lookup", "gssentials.admin.lookup");
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(CommandSender sender, String[] args) {
/*  39 */     Set<String> matches = new HashSet<>();
/*  40 */     if (args.length == 1 && sender.hasPermission("gssentials.admin.lookup.info")) {
/*  41 */       String uuid = null;
/*  42 */       for (Object nameO : this.pD.listAllData("lastname")) {
/*  43 */         String name = (String)nameO;
/*  44 */         if (name.equalsIgnoreCase(args[0])) {
/*  45 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_HEADER, new String[] { "PLAYER", name }));
/*  46 */           uuid = (String)this.pD.getData("lastname", name, "uuid");
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  51 */       if (uuid == null) {
/*  52 */         ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, getName() + " -a " + args[0]);
/*     */         
/*     */         return;
/*     */       } 
/*  56 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "UUID", "INFO", uuid }));
/*  57 */       if (sender.hasPermission("gssentials.admin.lookup.info.ip") || sender.hasPermission("gssentials.admin.lookup.info.all"))
/*  58 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "IP", "INFO", this.pD.getIp(uuid) })); 
/*  59 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Messaging", "INFO", Dictionary.capitalizeFirst(this.pD.isMsging(uuid) + "") }));
/*  60 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Muted", "INFO", Dictionary.capitalizeFirst(this.pD.isMuted(uuid) + "") }));
/*  61 */       if (sender.hasPermission("gssentials.admin.lookup.info.hidden") || sender.hasPermission("gssentials.admin.lookup.info.all"))
/*  62 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Hidden", "INFO", Dictionary.capitalizeFirst(this.pD.isHidden(uuid) + "") })); 
/*  63 */       if (sender.hasPermission("gssentials.admin.lookup.info.spy") || sender.hasPermission("gssentials.admin.lookup.info.all")) {
/*  64 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Spy", "INFO", Dictionary.capitalizeFirst(this.pD.isSpy(uuid) + "") }));
/*  65 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Command Spy", "INFO", Dictionary.capitalizeFirst(this.pD.isCSpy(uuid) + "") }));
/*     */       } 
/*  67 */       String list = Dictionary.combine(", ", this.pD.getFriends(uuid));
/*  68 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Friends", "INFO", list.equals("") ? "None" : list }));
/*  69 */       list = Dictionary.combine(", ", this.pD.getOutRequests(uuid));
/*  70 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Outgoing Friend Requests", "INFO", list.equals("") ? "None" : list }));
/*  71 */       list = Dictionary.combine(", ", this.pD.getInRequests(uuid));
/*  72 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_PLAYER_FORMAT, new String[] { "TYPE", "Incoming Friend Requests", "INFO", list.equals("") ? "None" : list }));
/*  73 */     } else if (args.length == 2) {
/*  74 */       boolean error = true;
/*  75 */       String partialPlayerName = args[0].toLowerCase();
/*  76 */       int arg = 0;
/*  77 */       String[] possibleArgs = { "b", "m", "e", "a", "ip" };
/*  78 */       for (String a : possibleArgs) {
/*  79 */         if (args[0].equals("-" + a)) {
/*  80 */           partialPlayerName = args[1].toLowerCase();
/*  81 */           error = false;
/*  82 */         } else if (args[1].equals("-" + a)) {
/*  83 */           arg = 1;
/*  84 */           error = false;
/*     */         } 
/*     */       } 
/*  87 */       if (error) {
/*  88 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "/" + getName() + " <part of name> [-b|-m|-e|-a|-ip]" }));
/*  89 */       } else if (args[arg].equals("-i")) {
/*  90 */         matches.addAll((Collection<? extends String>)this.pD.getDataMultiple("ip", partialPlayerName, "lastname").stream().map(name -> (String)name).collect(Collectors.toList()));
/*     */       } else {
/*  92 */         for (Object pO : this.pD.listAllData("lastname")) {
/*  93 */           String p = (String)pO;
/*  94 */           switch (args[arg]) {
/*     */             case "-m":
/*  96 */               if (p.toLowerCase().substring(1, p.length() - 1).contains(partialPlayerName.toLowerCase())) {
/*  97 */                 matches.add(p);
/*     */               }
/*     */             
/*     */             case "-e":
/* 101 */               if (p.toLowerCase().endsWith(partialPlayerName.toLowerCase())) {
/* 102 */                 matches.add(p);
/*     */               }
/*     */             
/*     */             case "-b":
/* 106 */               if (p.toLowerCase().startsWith(partialPlayerName.toLowerCase())) {
/* 107 */                 matches.add(p);
/*     */               }
/*     */             
/*     */             case "-a":
/* 111 */               if (p.toLowerCase().contains(partialPlayerName.toLowerCase())) {
/* 112 */                 matches.add(p);
/*     */               }
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         } 
/* 119 */         sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_HEADER, new String[] { "SIZE", String.valueOf(matches.size()) }));
/* 120 */         for (String match : matches) {
/* 121 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.LOOKUP_BODY, new String[] { "PLAYER", match }));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 125 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "\n  /" + 
/* 126 */               getName() + " <part of name|ip> <-b|-m|-e|-a|-ip>\n  /" + 
/* 127 */               getName() + " <full name>" }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\admin\LookupCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */