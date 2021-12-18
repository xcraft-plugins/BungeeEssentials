/*    */ package com.pantherman594.gssentials.integration;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import java.util.logging.Level;
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
/*    */ public class IntegrationTest
/*    */   implements Runnable
/*    */ {
/*    */   public void run() {
/* 33 */     if (BungeeEssentials.getInstance().isIntegrated() || BungeeEssentials.getInstance().getIntegrationProvider() != null) {
/* 34 */       IntegrationProvider provider = BungeeEssentials.getInstance().getIntegrationProvider();
/* 35 */       if (!provider.isEnabled()) {
/* 36 */         BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "*** \"{0}\" is not enabled ***", provider.getName());
/* 37 */         BungeeEssentials.getInstance().setupIntegration(new String[] { provider.getName() });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\integration\IntegrationTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */