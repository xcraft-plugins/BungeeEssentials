/*     */ package com.pantherman594.gssentials.regex;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ public class Rule
/*     */ {
/*  30 */   private Pattern pattern = null;
/*  31 */   private Handle handle = null;
/*     */   
/*     */   private String replacement;
/*     */   
/*     */   private String command;
/*     */   
/*     */   private List<String> matches;
/*     */   
/*     */   private Rule() {
/*  40 */     this.matches = Lists.newArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Rule deserialize(Map<String, String> serialized) {
/*  50 */     Preconditions.checkNotNull(serialized);
/*  51 */     Preconditions.checkArgument(!serialized.isEmpty());
/*  52 */     Preconditions.checkNotNull(serialized.get("pattern"), "invalid pattern");
/*  53 */     Preconditions.checkNotNull(serialized.get("handle"), "invalid handler");
/*     */     
/*  55 */     Rule rule = new Rule();
/*  56 */     rule.pattern(String.valueOf(serialized.get("pattern")));
/*  57 */     rule.handle(Handle.valueOf(String.valueOf(serialized.get("handle")).toUpperCase()));
/*  58 */     if (serialized.get("replacement") != null) {
/*  59 */       rule.replacement = serialized.get("replacement");
/*     */     }
/*  61 */     if (serialized.get("command") != null) {
/*  62 */       rule.command = serialized.get("command");
/*     */     }
/*  64 */     return rule;
/*     */   }
/*     */   
/*     */   private Rule pattern(String pattern) {
/*  68 */     return pattern(pattern, false);
/*     */   }
/*     */   
/*     */   private Rule pattern(String pattern, boolean sensitive) {
/*  72 */     if (sensitive) {
/*  73 */       this.pattern = Pattern.compile(pattern);
/*     */     } else {
/*  75 */       this.pattern = Pattern.compile(pattern, 2);
/*     */     } 
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   private Rule handle(Handle handle) {
/*  81 */     this.handle = handle;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public Handle getHandle() {
/*  86 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReplacement() {
/*  93 */     if (this.handle != Handle.REPLACE) {
/*  94 */       return null;
/*     */     }
/*  96 */     return this.replacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommand() {
/* 103 */     if (this.handle != Handle.COMMAND) {
/* 104 */       return null;
/*     */     }
/* 106 */     return this.command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean matches(String input) {
/* 117 */     Preconditions.checkNotNull(input);
/* 118 */     this.matches.clear();
/* 119 */     Matcher matcher = this.pattern.matcher(input);
/* 120 */     boolean found = false;
/* 121 */     while (matcher.find()) {
/* 122 */       if (!found) {
/* 123 */         found = true;
/*     */       }
/* 125 */       this.matches.add(input.substring(matcher.start(), matcher.end()));
/*     */     } 
/* 127 */     return found;
/*     */   }
/*     */   
/*     */   public Pattern getPattern() {
/* 131 */     return this.pattern;
/*     */   }
/*     */   
/*     */   public List<String> getMatches() {
/* 135 */     return this.matches;
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\regex\Rule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */