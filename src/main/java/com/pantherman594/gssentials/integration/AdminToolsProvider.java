/*    */ package com.pantherman594.gssentials.integration;
/*    */ 
/*    */ import fr.Alphart.BAT.BAT;
/*    */ import fr.Alphart.BAT.Modules.InvalidModuleException;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ 
/*    */ class AdminToolsProvider
/*    */   extends IntegrationProvider
/*    */ {
/*    */   public boolean isMuted(ProxiedPlayer player) {
/*    */     try {
/* 36 */       return (BAT.getInstance().getModules().getMuteModule().isMute(player, "(any)") == 1);
/* 37 */     } catch (InvalidModuleException e) {
/* 38 */       return false;
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
/* 51 */       return BAT.getInstance().getModules().getBanModule().isBan(player, "(any)");
/* 52 */     } catch (InvalidModuleException e) {
/* 53 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/*    */     try {
/* 65 */       return (BAT.getInstance() != null && BAT.getInstance().getModules() != null && BAT.getInstance().getModules().getMuteModule() != null);
/* 66 */     } catch (InvalidModuleException ex) {
/* 67 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 76 */     return "BungeeAdminTools";
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\integration\AdminToolsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */