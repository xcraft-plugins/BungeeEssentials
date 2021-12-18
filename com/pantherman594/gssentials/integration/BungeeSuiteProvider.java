/*    */ package com.pantherman594.gssentials.integration;
/*    */ 
/*    */ import com.minecraftdimensions.bungeesuite.managers.BansManager;
/*    */ import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
/*    */ import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
/*    */ import managers.BansManager;
/*    */ import managers.PlayerManager;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*    */ import objects.BSPlayer;
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
/*    */ class BungeeSuiteProvider
/*    */   extends IntegrationProvider
/*    */ {
/*    */   public boolean isMuted(ProxiedPlayer player) {
/*    */     try {
/* 36 */       Class.forName("com.minecraftdimensions.bungeesuite.managers.PlayerManager");
/* 37 */       BSPlayer suitePlayer = PlayerManager.getPlayer((CommandSender)player);
/* 38 */       return (suitePlayer != null && suitePlayer.isMuted());
/* 39 */     } catch (ClassNotFoundException classNotFoundException) {
/*    */ 
/*    */ 
/*    */       
/* 43 */       BSPlayer suitePlayer = PlayerManager.getPlayer((CommandSender)player);
/* 44 */       return (suitePlayer != null && suitePlayer.isMuted());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBanned(ProxiedPlayer player) {
/*    */     try {
/* 57 */       Class.forName("com.minecraftdimensions.bungeesuite.managers.PlayerManager");
/* 58 */       return BansManager.isPlayerBanned(player.getName());
/*    */     }
/* 60 */     catch (ClassNotFoundException classNotFoundException) {
/*    */ 
/*    */ 
/*    */       
/* 64 */       return BansManager.isPlayerBanned(player.getName());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 74 */     return ProxyServer.getInstance().getChannels().contains("BSChat");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 82 */     return "BungeeSuite";
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\integration\BungeeSuiteProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */