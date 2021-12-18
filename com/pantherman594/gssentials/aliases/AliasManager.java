/*    */ package com.pantherman594.gssentials.aliases;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.plugin.Plugin;
/*    */ import net.md_5.bungee.config.Configuration;
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
/*    */ public class AliasManager
/*    */ {
/* 31 */   private Map<String, List<String>> aliases = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AliasManager() {
/* 37 */     this.aliases.clear();
/* 38 */     Configuration aliasSection = BungeeEssentials.getInstance().getConfig().getSection("aliases");
/* 39 */     for (String alias : aliasSection.getKeys()) {
/* 40 */       List<String> commands = aliasSection.getStringList(alias);
/* 41 */       register(alias, commands);
/*    */     } 
/* 43 */     if (this.aliases.size() > 0) {
/* 44 */       BungeeEssentials.getInstance().getLogger().log(Level.INFO, "Loaded {0} aliases from config", Integer.valueOf(this.aliases.size()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void register(String alias, List<String> commands) {
/* 55 */     if (!this.aliases.containsKey(alias)) {
/* 56 */       this.aliases.put(alias, commands);
/* 57 */       ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)BungeeEssentials.getInstance(), new LoadCmds(alias, commands));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, List<String>> getAliases() {
/* 65 */     return this.aliases;
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\aliases\AliasManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */