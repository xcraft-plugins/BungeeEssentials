/*    */ package com.pantherman594.gssentials.integration;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public abstract class IntegrationProvider
/*    */ {
/* 30 */   private static Map<String, Class<? extends IntegrationProvider>> providers = new HashMap<>();
/* 31 */   private static Map<Class<? extends IntegrationProvider>, IntegrationProvider> instances = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 38 */     providers.put("BungeeAdminTools", AdminToolsProvider.class);
/* 39 */     providers.put("BungeeSuite", BungeeSuiteProvider.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntegrationProvider get(String name) {
/* 49 */     Class<? extends IntegrationProvider> clazz = providers.get(name);
/* 50 */     if (instances.get(clazz) == null) {
/*    */       try {
/* 52 */         instances.put(clazz, clazz.newInstance());
/* 53 */       } catch (InstantiationException|IllegalAccessException e) {
/*    */         try {
/* 55 */           Constructor<? extends IntegrationProvider> providerConstructor = clazz.getDeclaredConstructor(new Class[0]);
/* 56 */           if (!providerConstructor.isAccessible()) {
/* 57 */             providerConstructor.setAccessible(true);
/*    */           }
/* 59 */           IntegrationProvider provider = providerConstructor.newInstance(new Object[0]);
/* 60 */           instances.put(clazz, provider);
/* 61 */         } catch (NoSuchMethodException|java.lang.reflect.InvocationTargetException|InstantiationException|IllegalAccessException noSuchMethodException) {}
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 66 */     return instances.get(clazz);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Set<String> getPlugins() {
/* 73 */     return providers.keySet();
/*    */   }
/*    */   
/*    */   public abstract boolean isMuted(ProxiedPlayer paramProxiedPlayer);
/*    */   
/*    */   public abstract boolean isBanned(ProxiedPlayer paramProxiedPlayer);
/*    */   
/*    */   public abstract boolean isEnabled();
/*    */   
/*    */   public abstract String getName();
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\integration\IntegrationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */