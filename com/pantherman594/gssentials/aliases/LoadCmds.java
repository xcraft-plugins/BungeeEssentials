/*     */ package com.pantherman594.gssentials.aliases;
/*     */ 
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.Command;
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
/*     */ class LoadCmds
/*     */   extends Command
/*     */ {
/*     */   private final List<String> commands;
/*     */   private final String main;
/*     */   
/*     */   LoadCmds(String main, List<String> commands) {
/*  35 */     super(main);
/*  36 */     this.main = main;
/*  37 */     this.commands = commands;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(CommandSender sender, String[] args) {
/*  42 */     if (sender.hasPermission("gssentials.alias") || sender.hasPermission("gssentials.alias." + this.main)) {
/*  43 */       for (String command : this.commands) {
/*  44 */         runCommand(command, sender, args);
/*     */       }
/*     */     } else {
/*  47 */       sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */     } 
/*     */   }
/*     */   private void runCommand(String command, CommandSender sender, String[] args) {
/*     */     String server, commToSplit, message, recipient;
/*  52 */     int num = 0;
/*     */     
/*  54 */     if (sender instanceof ProxiedPlayer) {
/*  55 */       server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
/*     */     } else {
/*  57 */       server = "CONSOLE";
/*     */     } 
/*     */     int i;
/*  60 */     for (i = 0; i < args.length; i++) {
/*  61 */       args[i] = args[i].replace("{", "Ƃ");
/*     */     }
/*     */     
/*  64 */     if (command.contains("{*}") && args.length > 0) {
/*  65 */       command = command.replace("{*}", Dictionary.combine(args));
/*     */     }
/*     */     
/*  68 */     while (args.length > num) {
/*  69 */       if (command.contains("{" + num + "}")) {
/*  70 */         if (args[num] != null && !args[num].equals("")) {
/*  71 */           command = command.replace("{" + num + "}", args[num]);
/*     */         } else {
/*  73 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "VARIES" }));
/*     */           return;
/*     */         } 
/*     */       }
/*  77 */       num++;
/*     */     } 
/*     */     
/*  80 */     command = command.replace("{{ PLAYER }}", sender.getName()).replace("{{ SERVER }}", server);
/*     */     
/*  82 */     for (i = 0; i < args.length; i++) {
/*  83 */       args[i] = args[i].replace("Ƃ", "{");
/*     */     }
/*     */     
/*  86 */     switch (command.contains(" ") ? command.split(" ")[0] : command) {
/*     */       case "CONSOLE:":
/*  88 */         ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command.substring(9));
/*     */         return;
/*     */       case "TELL":
/*  91 */         commToSplit = command.replaceFirst(": ", "Ƃ");
/*  92 */         message = commToSplit.split("Ƃ")[1];
/*  93 */         recipient = commToSplit.split("Ƃ")[0].substring(5);
/*  94 */         if (recipient.equals("ALL")) {
/*  95 */           for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
/*  96 */             p.sendMessage((BaseComponent)Dictionary.format(message, new String[0]));
/*     */           }
/*  98 */         } else if (command != null && ProxyServer.getInstance().getPlayer(recipient) != null) {
/*  99 */           ProxyServer.getInstance().getPlayer(recipient).sendMessage((BaseComponent)Dictionary.format(message, new String[0]));
/*     */         } 
/*     */         return;
/*     */       case "TELL:":
/* 103 */         sender.sendMessage((BaseComponent)Dictionary.format(command.substring(6), new String[0]));
/*     */         return;
/*     */     } 
/* 106 */     ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, command);
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\aliases\LoadCmds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */