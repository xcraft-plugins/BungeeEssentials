/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.pantherman594.gssentials.aliases.AliasManager;
/*     */ import com.pantherman594.gssentials.announcement.AnnouncementManager;
/*     */ import com.pantherman594.gssentials.database.MsgGroups;
/*     */ import com.pantherman594.gssentials.database.PlayerData;
/*     */ import com.pantherman594.gssentials.integration.IntegrationProvider;
/*     */ import com.pantherman594.gssentials.integration.IntegrationTest;
/*     */ import com.pantherman594.gssentials.regex.RuleManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.plugin.Command;
/*     */ import net.md_5.bungee.api.plugin.Plugin;
/*     */ import net.md_5.bungee.config.Configuration;
/*     */ import net.md_5.bungee.config.ConfigurationProvider;
/*     */ import net.md_5.bungee.config.YamlConfiguration;
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
/*     */ public class BungeeEssentials
/*     */   extends Plugin
/*     */ {
/*     */   private static BungeeEssentials instance;
/*  51 */   private Map<String, String> mainList = new HashMap<>();
/*  52 */   private Map<String, String[]> aliasList = (Map)new HashMap<>();
/*     */   private RuleManager ruleManager;
/*     */   private Configuration config;
/*  55 */   private Configuration messages = null;
/*     */   
/*     */   private IntegrationProvider helper;
/*     */   
/*     */   private List<String> enabled;
/*     */   
/*     */   private File libDir;
/*     */   private File configFile;
/*     */   private File messageFile;
/*     */   private Messenger messenger;
/*     */   private PlayerData playerData;
/*     */   private MsgGroups msgGroups;
/*     */   private boolean integrated;
/*     */   
/*     */   public static BungeeEssentials getInstance() {
/*  70 */     return instance;
/*     */   }
/*     */   
/*     */   RuleManager getRuleManager() {
/*  74 */     return this.ruleManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMain(String key) {
/*  82 */     return this.mainList.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAlias(String key) {
/*  90 */     return this.aliasList.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  95 */     instance = this;
/*  96 */     this.libDir = new File(getDataFolder(), "lib");
/*  97 */     this.configFile = new File(getDataFolder(), "config.yml");
/*  98 */     this.messageFile = new File(getDataFolder(), "messages.yml");
/*     */     try {
/* 100 */       loadConfig();
/* 101 */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/*     */     } 
/* 104 */     if (getConfig().getStringList("enable").contains("updater") && (
/* 105 */       new Updater()).update(getConfig().getStringList("enable").contains("betaupdates"))) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     reload();
/* 110 */     ProxyServer.getInstance().getScheduler().schedule(this, this::reload, 3L, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 115 */     Log.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOfflineUUID(String name) {
/* 126 */     String uuid, checkUuid = (String)this.playerData.getData("lastname", name, "uuid");
/* 127 */     if (checkUuid != null) {
/* 128 */       return checkUuid;
/*     */     }
/* 130 */     if (ProxyServer.getInstance().getConfig().isOnlineMode()) {
/* 131 */       try (BufferedReader in = new BufferedReader(new InputStreamReader((new URL("https://api.mojang.com/users/profiles/minecraft/" + name)).openStream()))) {
/* 132 */         uuid = ((JsonObject)(new JsonParser()).parse(in)).get("id").toString().replaceAll("\"", "").replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
/* 133 */       } catch (Exception e) {
/* 134 */         return null;
/*     */       } 
/*     */     } else {
/* 137 */       uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)).toString();
/*     */     } 
/*     */     
/* 140 */     return uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveConfig() throws IOException {
/* 149 */     if (!getDataFolder().exists() && 
/* 150 */       !getDataFolder().mkdir()) {
/* 151 */       getLogger().log(Level.WARNING, "Unable to create config folder!");
/*     */     }
/*     */     
/* 154 */     File file = new File(getDataFolder(), "config.yml");
/* 155 */     if (!file.exists()) {
/* 156 */       Files.copy(getResourceAsStream("config.yml"), file.toPath(), new java.nio.file.CopyOption[0]);
/*     */     }
/* 158 */     file = new File(getDataFolder(), "messages.yml");
/* 159 */     if (!file.exists()) {
/* 160 */       Files.copy(getResourceAsStream("messages.yml"), file.toPath(), new java.nio.file.CopyOption[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadConfig() throws IOException {
/* 171 */     if (!this.libDir.exists()) {
/* 172 */       this.libDir.mkdir();
/*     */     }
/* 174 */     if (!this.configFile.exists()) {
/* 175 */       saveConfig();
/*     */     }
/* 177 */     if (!this.messageFile.exists()) {
/* 178 */       saveConfig();
/*     */     }
/* 180 */     this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configFile);
/* 181 */     this.messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.messageFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean reload() {
/*     */     try {
/* 191 */       loadConfig();
/* 192 */       Dictionary.load();
/* 193 */     } catch (IOException|IllegalAccessException e) {
/* 194 */       e.printStackTrace();
/* 195 */       return false;
/*     */     } 
/*     */     
/* 198 */     ProxyServer.getInstance().getScheduler().cancel(this);
/* 199 */     ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
/* 200 */     ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
/*     */     
/* 202 */     if (this.config.getString("database.format", "sqlite").equalsIgnoreCase("mysql")) {
/* 203 */       String host = this.config.getString("database.host", "127.0.0.1");
/* 204 */       int port = this.config.getInt("database.port", 3306);
/* 205 */       String username = this.config.getString("database.username", "user");
/* 206 */       String password = this.config.getString("database.password", "pass");
/* 207 */       String database = this.config.getString("database.database", "bungeecord");
/* 208 */       String prefix = this.config.getString("database.prefix", "BE_");
/*     */       
/* 210 */       this.playerData = new PlayerData(String.format("%s:%d/%s", new Object[] { host, Integer.valueOf(port), database }), username, password, prefix);
/* 211 */       this.msgGroups = new MsgGroups(String.format("%s:%d/%s", new Object[] { host, Integer.valueOf(port), database }), username, password, prefix);
/*     */       
/* 213 */       if (this.config.getBoolean("database.convert", false)) {
/* 214 */         this.playerData.convert();
/* 215 */         this.msgGroups.convert();
/* 216 */         this.config.set("database.convert", Boolean.valueOf(false));
/* 217 */         saveMainConfig();
/*     */       } 
/*     */     } else {
/* 220 */       this.playerData = new PlayerData();
/* 221 */       this.msgGroups = new MsgGroups();
/*     */     } 
/* 223 */     this.playerData.createDataNotExist("CONSOLE");
/*     */     
/* 225 */     this.messenger = new Messenger();
/* 226 */     Log.reset();
/* 227 */     this.enabled = new ArrayList<>();
/*     */     
/* 229 */     ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerListener());
/*     */     
/* 231 */     int commands = 0;
/*     */ 
/*     */     
/* 234 */     List<String> enable = this.config.getStringList("enable");
/* 235 */     for (String comm : Arrays.<String>asList(new String[] { "alert", "commandspy", "hide", "lookup", "mute", "sendall", "send", "spy", "staffchat", "chat", "find", "friend", "ignore", "join", "list", "reply", "message", "msggroup", "slap", "reload" })) {
/* 236 */       if (enable.contains(comm) || comm.equals("reply") || comm.equals("reload")) {
/* 237 */         List<String> BASE = this.config.getStringList("commands." + comm);
/* 238 */         if (BASE.isEmpty()) {
/* 239 */           getLogger().log(Level.WARNING, "Your configuration is either outdated or invalid!");
/* 240 */           getLogger().log(Level.WARNING, "Falling back to main value for key commands." + comm);
/* 241 */           BASE = Collections.singletonList(comm);
/*     */         } 
/* 243 */         this.mainList.put(comm, BASE.get(0));
/* 244 */         String[] TEMP_ALIAS = BASE.<String>toArray(new String[BASE.size()]);
/* 245 */         this.aliasList.put(comm, Arrays.copyOfRange(TEMP_ALIAS, 1, TEMP_ALIAS.length));
/* 246 */         register(comm);
/* 247 */         commands++;
/*     */       } 
/*     */     } 
/* 250 */     addEnabled("rules-chat");
/* 251 */     addEnabled("spam-chat");
/* 252 */     addEnabled("spam-command");
/* 253 */     addEnabled("commandSpy");
/* 254 */     addEnabled("fastRelog");
/* 255 */     addEnabled("friend");
/* 256 */     addEnabled("ignore");
/* 257 */     addEnabled("joinAnnounce");
/* 258 */     addEnabled("fulllog");
/* 259 */     addEnabled("hoverlist");
/* 260 */     addEnabled("mute");
/* 261 */     addEnabled("server");
/* 262 */     addEnabled("autoredirect");
/* 263 */     addEnabled("rules");
/* 264 */     addEnabled("spam");
/* 265 */     addEnabled("useLog", new String[] { "log", "fulllog" });
/* 266 */     if (contains(new String[] { "useLog" })) {
/* 267 */       if (!Log.setup()) {
/* 268 */         getLogger().log(Level.WARNING, "Error enabling the chat logger!");
/*     */       } else {
/* 270 */         getLogger().log(Level.INFO, "Enabled the chat logger");
/*     */       } 
/*     */     }
/* 273 */     if (contains(enable, new String[] { "aliases" })) {
/* 274 */       new AliasManager();
/* 275 */       getLogger().log(Level.INFO, "Enabled aliases");
/*     */     } 
/* 277 */     if (contains(enable, new String[] { "announcement" })) {
/* 278 */       new AnnouncementManager();
/* 279 */       getLogger().log(Level.INFO, "Enabled announcements");
/*     */     } 
/* 281 */     if (contains(new String[] { "rules", "rules-chat" })) {
/* 282 */       this.ruleManager = new RuleManager();
/*     */     }
/* 284 */     getLogger().log(Level.INFO, "Registered {0} commands successfully", Integer.valueOf(commands));
/* 285 */     setupIntegration(new String[0]);
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupIntegration(String... ignore) {
/* 295 */     Preconditions.checkNotNull(ignore);
/* 296 */     this.integrated = false;
/* 297 */     this.helper = null;
/* 298 */     if (ignore.length > 0) {
/* 299 */       getLogger().log(Level.INFO, "*** Rescanning for supported plugins ***");
/*     */     }
/* 301 */     List<String> ignoredPlugins = Arrays.asList(ignore);
/* 302 */     for (String name : IntegrationProvider.getPlugins()) {
/* 303 */       if (ignoredPlugins.contains(name)) {
/*     */         continue;
/*     */       }
/* 306 */       if (ProxyServer.getInstance().getPluginManager().getPlugin(name) != null) {
/* 307 */         this.integrated = true;
/* 308 */         this.helper = IntegrationProvider.get(name);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 313 */     if (isIntegrated()) {
/* 314 */       getLogger().log(Level.INFO, "*** Integrating with \"{0}\" plugin ***", this.helper.getName());
/*     */     }
/* 316 */     else if (ignore.length > 0) {
/* 317 */       getLogger().log(Level.INFO, "*** No supported plugins detected ***");
/*     */     } 
/*     */ 
/*     */     
/* 321 */     ProxyServer.getInstance().getScheduler().schedule(this, (Runnable)new IntegrationTest(), 7L, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void register(String comm) {
/* 330 */     Map<String, String> commands = new HashMap<>();
/* 331 */     commands.put("alert", "com.pantherman594.gssentials.command.admin.AlertCommand");
/* 332 */     commands.put("chat", "com.pantherman594.gssentials.command.general.ChatCommand");
/* 333 */     commands.put("commandspy", "com.pantherman594.gssentials.command.admin.CSpyCommand");
/* 334 */     commands.put("find", "com.pantherman594.gssentials.command.general.FindCommand");
/* 335 */     commands.put("friend", "com.pantherman594.gssentials.command.general.FriendCommand");
/* 336 */     commands.put("hide", "com.pantherman594.gssentials.command.admin.HideCommand");
/* 337 */     commands.put("ignore", "com.pantherman594.gssentials.command.general.IgnoreCommand");
/* 338 */     commands.put("join", "com.pantherman594.gssentials.command.general.JoinCommand");
/* 339 */     commands.put("list", "com.pantherman594.gssentials.command.general.ServerListCommand");
/* 340 */     commands.put("lookup", "com.pantherman594.gssentials.command.admin.LookupCommand");
/* 341 */     commands.put("message", "com.pantherman594.gssentials.command.general.MessageCommand");
/* 342 */     commands.put("msggroup", "com.pantherman594.gssentials.command.general.MsgGroupCommand");
/* 343 */     commands.put("mute", "com.pantherman594.gssentials.command.admin.MuteCommand");
/* 344 */     commands.put("reload", "com.pantherman594.gssentials.command.admin.ReloadCommand");
/* 345 */     commands.put("send", "com.pantherman594.gssentials.command.admin.SendCommand");
/* 346 */     commands.put("slap", "com.pantherman594.gssentials.command.general.SlapCommand");
/* 347 */     commands.put("spy", "com.pantherman594.gssentials.command.admin.SpyCommand");
/* 348 */     commands.put("staffchat", "com.pantherman594.gssentials.command.admin.StaffChatCommand");
/* 349 */     if (commands.containsKey(comm)) {
/*     */       try {
/* 351 */         Class<?> cClass = Class.forName(commands.get(comm));
/* 352 */         ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)cClass.newInstance());
/* 353 */       } catch (Exception e) {
/* 354 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getLibDir() {
/* 363 */     return this.libDir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Configuration getConfig() {
/* 370 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Configuration getMessages() {
/* 377 */     return this.messages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Messenger getMessenger() {
/* 384 */     return this.messenger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerData getPlayerData() {
/* 391 */     return this.playerData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MsgGroups getMsgGroups() {
/* 398 */     return this.msgGroups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void saveMainConfig() {
/*     */     try {
/* 406 */       ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), this.configFile);
/* 407 */     } catch (IOException e) {
/* 408 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void saveMessagesConfig() {
/*     */     try {
/* 417 */       ConfigurationProvider.getProvider(YamlConfiguration.class).save(getMessages(), this.messageFile);
/* 418 */     } catch (IOException e) {
/* 419 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntegrationProvider getIntegrationProvider() {
/* 427 */     return this.helper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEnabled(String name) {
/* 436 */     addEnabled(name, new String[] { name });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEnabled(String name, String... keys) {
/* 446 */     if (contains(this.config.getStringList("enable"), keys)) this.enabled.add(name);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean contains(List<String> list, String... checks) {
/* 457 */     for (String string : list) {
/* 458 */       for (String check : checks) {
/* 459 */         if (string.equalsIgnoreCase(check)) {
/* 460 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 464 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String... checks) {
/* 475 */     return contains(this.enabled, checks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntegrated() {
/* 482 */     return (this.integrated && this.helper != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\BungeeEssentials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */