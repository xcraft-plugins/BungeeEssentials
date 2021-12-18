/*    */ package com.pantherman594.gssentials.command;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import com.pantherman594.gssentials.database.PlayerData;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import net.md_5.bungee.api.plugin.Command;
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
/*    */ public abstract class BECommand
/*    */   extends Command
/*    */ {
/* 38 */   public PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*    */   
/*    */   public BECommand(String name, String permission) {
/* 41 */     super(BungeeEssentials.getInstance().getMain(name), permission, BungeeEssentials.getInstance().getAlias(name));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<String> tabPlayers(CommandSender sender, String search) {
/* 52 */     return (Iterable<String>)BungeeEssentials.getInstance().getMessenger().getVisiblePlayers(sender.hasPermission("gssentials.admin.hide.bypass")).stream().filter(player -> (!player.getName().equals(sender.getName()) && player.getName().toLowerCase().startsWith(search.toLowerCase()))).map(CommandSender::getName).collect(Collectors.toSet());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Iterable<String> tabStrings(String search, String[] strings) {
/* 63 */     Set<String> matches = new HashSet<>();
/* 64 */     for (String string : strings) {
/* 65 */       if (string.toLowerCase().startsWith(search.toLowerCase())) {
/* 66 */         matches.add(string);
/*    */       }
/*    */     } 
/* 69 */     return matches;
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\BECommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */