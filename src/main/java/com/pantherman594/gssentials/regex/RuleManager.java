/*    */ package com.pantherman594.gssentials.regex;
/*    */ 
/*    */ import com.pantherman594.gssentials.BungeeEssentials;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class RuleManager
/*    */ {
/* 29 */   private List<Rule> rules = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuleManager() {
/* 36 */     this.rules.clear();
/* 37 */     List<Map<String, String>> section = (List<Map<String, String>>) BungeeEssentials.getInstance().getMessages().getList("rules");
/* 38 */     for (Map<String, String> map : section) {
/* 39 */       Rule rule = Rule.deserialize(map);
/* 40 */       if (rule != null) {
/* 41 */         this.rules.add(rule);
/*    */       }
/*    */     } 
/* 44 */     if (this.rules.size() > 0) {
/* 45 */       BungeeEssentials.getInstance().getLogger().log(Level.INFO, "Loaded {0} rules from config", Integer.valueOf(this.rules.size()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<MatchResult> matches(String input) {
/* 56 */     List<MatchResult> results = new ArrayList<>();
/* 57 */     Boolean contains = Boolean.valueOf(false);
/* 58 */     for (Rule rule : this.rules) {
/* 59 */       if (rule.matches(input)) {
/* 60 */         results.add(new MatchResult(true, rule));
/* 61 */         contains = Boolean.valueOf(true); continue;
/*    */       } 
/* 63 */       if (input.contains(" ")) {
/* 64 */         for (String string : input.split(" ")) {
/* 65 */           if (rule.matches(string)) {
/* 66 */             results.add(new MatchResult(true, rule));
/* 67 */             contains = Boolean.valueOf(true);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 73 */     if (!contains.booleanValue()) {
/* 74 */       results.add(new MatchResult(false, null));
/*    */     }
/* 76 */     return results;
/*    */   }
/*    */   
/*    */   public List<Rule> getRules() {
/* 80 */     return this.rules;
/*    */   }
/*    */   
/*    */   public class MatchResult {
/*    */     private final boolean success;
/*    */     private final Rule rule;
/*    */     
/*    */     MatchResult(boolean success, Rule rule) {
/* 88 */       this.success = success;
/* 89 */       this.rule = rule;
/*    */     }
/*    */     
/*    */     public boolean matched() {
/* 93 */       return this.success;
/*    */     }
/*    */     
/*    */     public Rule getRule() {
/* 97 */       return this.rule;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\regex\RuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */