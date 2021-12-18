/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.file.Files;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.config.Configuration;
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
/*     */ class Updater
/*     */ {
/*     */   private static final String VERSION_LINK = "https://raw.githubusercontent.com/PantherMan594/BungeeEssentials/master/version.txt";
/*  39 */   private BungeeEssentials plugin = BungeeEssentials.getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean update(boolean beta) {
/*     */     URLConnection con;
/*  48 */     String newVerDec, oldVerDec = this.plugin.getDescription().getVersion();
/*  49 */     int oldVersion = getVersionFromString(oldVerDec);
/*  50 */     File path = new File(ProxyServer.getInstance().getPluginsFolder(), "BungeeEssentials.jar");
/*     */ 
/*     */     
/*     */     try {
/*  54 */       URL url = new URL("https://raw.githubusercontent.com/PantherMan594/BungeeEssentials/master/version.txt");
/*  55 */       con = url.openConnection();
/*  56 */     } catch (IOException e) {
/*  57 */       this.plugin.getLogger().log(Level.WARNING, "Invalid version link. Please contact plugin author.");
/*  58 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  63 */     try(InputStreamReader isr = new InputStreamReader(con.getInputStream()); 
/*  64 */         BufferedReader reader = new BufferedReader(isr)) {
/*     */ 
/*     */       
/*  67 */       String newVer = reader.readLine();
/*  68 */       String newBVer = reader.readLine();
/*  69 */       newVerDec = beta ? newBVer : newVer;
/*  70 */     } catch (IOException e) {
/*  71 */       this.plugin.getLogger().log(Level.WARNING, "Unable to read version from link. Please contact plugin author.");
/*  72 */       return false;
/*     */     } 
/*     */     
/*  75 */     int newVersion = getVersionFromString(newVerDec);
/*     */     
/*  77 */     if (newVersion > oldVersion) {
/*  78 */       this.plugin.getLogger().log(Level.INFO, "Update found, downloading...");
/*  79 */       String dlLink = "https://github.com/PantherMan594/BungeeEssentials/releases/download/" + newVerDec + "/BungeeEssentials.jar";
/*     */       try {
/*  81 */         URL url = new URL(dlLink);
/*  82 */         con = url.openConnection();
/*  83 */       } catch (IOException e) {
/*  84 */         this.plugin.getLogger().log(Level.WARNING, "Invalid download link. Please contact plugin author.");
/*  85 */         return false;
/*     */       } 
/*     */ 
/*     */       
/*  89 */       try(InputStream in = con.getInputStream(); 
/*  90 */           FileOutputStream out = new FileOutputStream(path)) {
/*     */         
/*  92 */         byte[] buffer = new byte[1024];
/*     */         int size;
/*  94 */         while ((size = in.read(buffer)) != -1) {
/*  95 */           out.write(buffer, 0, size);
/*     */         }
/*  97 */       } catch (IOException e) {
/*  98 */         this.plugin.getLogger().log(Level.WARNING, "Failed to download update, please update manually from http://www.spigotmc.org/resources/bungeeessentials.1488/");
/*  99 */         this.plugin.getLogger().log(Level.WARNING, "Error message: ");
/* 100 */         e.printStackTrace();
/* 101 */         return false;
/*     */       } 
/*     */       
/* 104 */       this.plugin.getLogger().log(Level.INFO, "Succesfully updated plugin to v" + newVerDec);
/* 105 */       this.plugin.getLogger().log(Level.INFO, "Plugin disabling, restart the server to enable changes!");
/* 106 */       return true;
/*     */     } 
/* 108 */     this.plugin.getLogger().log(Level.INFO, "You are running the latest version of BungeeEssentials (v" + oldVerDec + ")!");
/* 109 */     updateConfig();
/*     */     
/* 111 */     return false;
/*     */   }
/*     */   private void updateConfig() {
/*     */     List<String> enabledList;
/*     */     String muteEnabled, muteDisabled, muteError, muterExemptError;
/*     */     List<Map<String, String>> section, section2;
/*     */     String name;
/*     */     int num;
/*     */     String msgFormat;
/* 120 */     Configuration config = this.plugin.getConfig();
/* 121 */     Configuration messages = this.plugin.getMessages();
/* 122 */     int oldVersion = getVersionFromString(config.getString("configversion"));
/* 123 */     int newVersion = getVersionFromString(this.plugin.getDescription().getVersion());
/* 124 */     if (oldVersion == newVersion) {
/*     */       return;
/*     */     }
/* 127 */     if (oldVersion == 0) {
/* 128 */       if (!messages.getString("mute.muter.error").equals("")) {
/* 129 */         oldVersion = 244;
/*     */       } else {
/* 131 */         oldVersion = 243;
/*     */       } 
/*     */     }
/*     */     try {
/* 135 */       File oldDir = new File(this.plugin.getDataFolder(), "old");
/* 136 */       if (!oldDir.exists()) {
/* 137 */         oldDir.mkdir();
/*     */       }
/* 139 */       File newConf = new File(this.plugin.getDataFolder(), "config.yml");
/* 140 */       File oldConf = new File(oldDir, "config_v" + oldVersion + ".yml");
/* 141 */       File newMess = new File(this.plugin.getDataFolder(), "messages.yml");
/* 142 */       File oldMess = new File(oldDir, "messages_v" + oldVersion + ".yml");
/* 143 */       if (!oldConf.exists()) {
/* 144 */         Files.copy(newConf.toPath(), oldConf.toPath(), new java.nio.file.CopyOption[0]);
/*     */       }
/* 146 */       if (!oldMess.exists()) {
/* 147 */         Files.copy(newMess.toPath(), oldMess.toPath(), new java.nio.file.CopyOption[0]);
/*     */       }
/* 149 */     } catch (Exception e) {
/* 150 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 153 */     switch (oldVersion) {
/*     */       case 243:
/* 155 */         muteEnabled = messages.getString("mute.enabled");
/* 156 */         muteDisabled = messages.getString("mute.disabled");
/* 157 */         muteError = messages.getString("mute.error");
/* 158 */         messages.set("mute.enabled", null);
/* 159 */         messages.set("mute.disabled", null);
/* 160 */         messages.set("mute.error", null);
/* 161 */         messages.set("mute.muted.enabled", muteEnabled);
/* 162 */         messages.set("mute.muted.disabled", muteDisabled);
/* 163 */         messages.set("mute.muted.error", muteError);
/* 164 */         messages.set("mute.muter.enabled", "&c{{ PLAYER }} is now muted!");
/* 165 */         messages.set("mute.muter.disabled", "&a{{ PLAYER }} is no longer muted!");
/* 166 */         messages.set("mute.muter.error", "&cHey, you can't mute that player!");
/*     */       case 244:
/* 168 */         muterExemptError = messages.getString("mute.muter.error");
/* 169 */         messages.set("mute.muter.exempt", muterExemptError);
/* 170 */         messages.set("mute.muter.error", "&7{{ PLAYER }} tried to chat while muted!");
/*     */       case 245:
/* 172 */         messages.set("bannedwords.replace", "****");
/* 173 */         messages.set("bannedwords.list", Arrays.asList(new String[] { "anal", "anus", "aroused", "asshole", "bastard", "bitch", "boob", "bugger", "cock", "cum", "cunt", "dafuq", "dick", "ffs", "fuck", "gay", "hentai", "homo", "homosexual", "horny", "intercourse", "jerk", "lesbian", "milf", "nigga", "nigger", "pedo", "penis", "piss", "prostitute", "pussy", "rape", "rapist", "retard", "sex", "shit", "slag", "slut", "sperm", "spunk", "testicle", "titt", "tosser", "twat", "vagina", "wanker", "whore", "wtf" }));
/*     */       case 246:
/*     */       case 247:
/* 176 */         enabledList = config.getStringList("enable");
/* 177 */         enabledList.remove("multirelog");
/* 178 */         enabledList.add("autoredirect");
/* 179 */         enabledList.add("fastrelog");
/* 180 */         enabledList.add("friend");
/* 181 */         enabledList.add("spam-command");
/* 182 */         config.set("enable", enabledList);
/* 183 */         config.set("commands.friend", Arrays.asList(new String[] { "friend", "f" }));
/* 184 */         section = config.getList("aliases");
/* 185 */         config.set("aliases", null);
/* 186 */         for (Map<String, String> map : section) {
/* 187 */           Preconditions.checkNotNull(map);
/* 188 */           Preconditions.checkArgument(!map.isEmpty());
/* 189 */           Preconditions.checkNotNull(map.get("alias"), "invalid alias");
/* 190 */           Preconditions.checkNotNull(map.get("commands"), "invalid commands");
/* 191 */           config.set("aliases." + (String)map.get("alias"), map.get("commands"));
/*     */         } 
/* 193 */         messages.set("message.format", messages.get("format.message"));
/* 194 */         messages.set("message.enabled", "&aMessaging is now enabled!");
/* 195 */         messages.set("message.disabled", "&cMessaging is now disabled!");
/* 196 */         messages.set("format.message", null);
/* 197 */         if (messages.get("bannedwords.replace").equals("****")) {
/* 198 */           messages.set("bannedwords.replace", "*");
/*     */         }
/* 200 */         messages.set("multilog", null);
/* 201 */         messages.set("friend.header", "&2Current Friends:");
/* 202 */         messages.set("friend.body", "- {{ NAME }} ({{ SERVER }})");
/* 203 */         messages.set("friend.new", "&aYou are now friends with {{ NAME }}!");
/* 204 */         messages.set("friend.old", "&aYou are already friends with {{ NAME }}!");
/* 205 */         messages.set("friend.remove", "&cYou are no longer friends with {{ NAME }}.");
/* 206 */         messages.set("friend.outrequests.header", "&2Outgoing Friend Requests:");
/* 207 */         messages.set("friend.outrequests.body", "- {{ NAME }}");
/* 208 */         messages.set("friend.outrequests.new", "&a{{ NAME }} has received your friend request.");
/* 209 */         messages.set("friend.outrequests.old", "&cYou already requested to be friends with {{ NAME }}. Please wait for a response!");
/* 210 */         messages.set("friend.outrequests.remove", "&cThe friend request to {{ NAME }} was removed.");
/* 211 */         messages.set("friend.inrequests.header", "&2Incoming Friend Requests:");
/* 212 */         messages.set("friend.inrequests.body", "- {{ NAME }}");
/* 213 */         messages.set("friend.inrequests.new", "&a{{ NAME }} would like to be your friend. /friend <add|remove> {{ NAME }} to accept or decline the request.");
/* 214 */         messages.set("friend.inrequests.remove", "&cThe friend request from {{ NAME }} was removed.");
/* 215 */         messages.set("errors.fastrelog", "&cPlease wait before reconnecting!");
/* 216 */         section2 = messages.getList("announcements");
/* 217 */         messages.set("announcements", null);
/* 218 */         name = "annc";
/* 219 */         num = 0;
/* 220 */         for (Map<String, String> map : section2) {
/* 221 */           Preconditions.checkNotNull(map);
/* 222 */           Preconditions.checkArgument(!map.isEmpty());
/* 223 */           Preconditions.checkNotNull(map.get("delay"), "invalid delay");
/* 224 */           Preconditions.checkNotNull(map.get("interval"), "invalid interval");
/* 225 */           Preconditions.checkNotNull(map.get("message"), "invalid message");
/* 226 */           messages.set("announcements." + name + num + ".delay", map.get("delay"));
/* 227 */           messages.set("announcements." + name + num + ".interval", map.get("interval"));
/* 228 */           messages.set("announcements." + name + num + ".message", map.get("message"));
/* 229 */           num++;
/*     */         } 
/*     */       case 250:
/* 232 */         messages.set("list.body", messages.getString("list.body").replace("{{ DENSITY }}", "({{ DENSITY }})"));
/* 233 */         messages.set("friend.body", messages.getString("friend.body") + "{{ HOVER: Click to join your friend! }}{{ CLICK: /server {{ SERVER }} }}");
/* 234 */         messages.set("friend.removeerror", "&cYou can't remove a friend you don't have!");
/* 235 */         messages.set("friend.inrequests.body", messages.getString("friend.inrequests.body") + "{{ HOVER: Click to accept friend request! }}{{ CLICK: /friend add {{ NAME }} }}");
/* 236 */         messages.set("friend.inrequests.new", messages.getString("friend.inrequests.new") + "{{ HOVER: Click to accept friend request! }}{{ CLICK: /friend add {{ NAME }} }}");
/*     */       case 251:
/*     */       case 252:
/*     */       case 253:
/* 240 */         enabledList = config.getStringList("enable");
/* 241 */         enabledList.add("server");
/* 242 */         config.set("enable", enabledList);
/* 243 */         if (messages.getString("list.header").equals("&aServers:")) {
/* 244 */           messages.set("list.header", "You are on {{ CURRENT }}\n&aServers:");
/*     */         }
/*     */       case 254:
/* 247 */         msgFormat = messages.getString("message.format");
/* 248 */         messages.set("message.format", null);
/* 249 */         messages.set("message.format.send", msgFormat.replace("{{ SENDER }}", "me"));
/* 250 */         messages.set("message.format.receive", msgFormat.replace("{{ RECIPIENT }}", "me"));
/* 251 */         messages.set("friend.removeerror", messages.get("friend.inrequests.removeerror"));
/* 252 */         messages.set("friend.inrequests.removeerror", null);
/*     */       case 255:
/*     */       case 256:
/*     */       case 257:
/*     */       case 258:
/* 257 */         PlayerData.convertPlayerData();
/* 258 */         enabledList = config.getStringList("enable");
/* 259 */         enabledList.add("hoverlist");
/* 260 */         enabledList.add("msggroup");
/* 261 */         enabledList.remove("clean");
/* 262 */         config.set("commands.msggroup", Arrays.asList(new String[] { "msggroup", "mg" }));
/* 263 */         config.set("capspam.enabled", "true");
/* 264 */         config.set("capspam.percent", Integer.valueOf(50));
/* 265 */         config.set("aliases.mga", Collections.singletonList("msggroup admin {*}"));
/* 266 */         config.set("enable", enabledList);
/* 267 */         if (messages.getString("message.format.receive").equals("&7[{{ BREAK }}&7{{ SENDER }}{{ HOVER: On the {{ SERVER }} server. }}{{ BREAK }}&7 » me] &f{{ MESSAGE }}")) {
/* 268 */           messages.set("message.format.receive", "&7[{{ BREAK }}&7{{ SENDER }}{{ HOVER: On the {{ SERVER }} server. }}{{ CLICK: SUG: /msg {{ SENDER }} }}{{ BREAK }}&7 » me] &f{{ MESSAGE }}{{ CLICK: SUG: /msg {{ SENDER }} }}");
/*     */         }
/* 270 */         messages.set("msggroup.format", "&9{{ NAME }} - {{ SENDER }} » &7{{ MESSAGE }}");
/* 271 */         messages.set("msggroup.create", "&aMessage group &f{{ NAME }} &asuccessfuly created! Invite players with /msggroup invite <username> {{ NAME }}!{{ HOVER: Click to prepare command. }}{{ CLICK: SUG: /msggroup invite <username> {{ NAME }} }}");
/* 272 */         messages.set("msggroup.join", "&aSuccessfully joined the &f{{ NAME }} &amessage group.");
/* 273 */         messages.set("msggroup.leave", "&aSuccessfully left the &f{{ NAME }} &amessage group.");
/* 274 */         messages.set("msggroup.invite.send", "&aSuccessfully invited &f{{ PLAYER }} &ato the &f{{ NAME }} &amessage group.");
/* 275 */         messages.set("msggroup.invite.receive", "&aYou've been invited to join the &f{{ NAME }} &amessage group. Click to accept!{{ CLICK: /msggroup join {{ NAME }} }}");
/* 276 */         messages.set("msggroup.kick.send", "&aSuccessfully kicked &f{{ PLAYER }} &afrom the &f{{ NAME }} &amessage group.");
/* 277 */         messages.set("msggroup.kick.receive", "&cYou've been kicked from the &f{{ NAME }} &cmessage group.");
/* 278 */         messages.set("msggroup.disband", "&aSuccessfully disbanded the &f{{ NAME }} &amessage group.");
/* 279 */         messages.set("msggroup.error.invalidname", "&cMessage group names must contain lowercase letters only, and must be at least 3 letters long.");
/* 280 */         messages.set("msggroup.error.nametaken", "&cSorry, that name has already been taken.");
/* 281 */         messages.set("msggroup.error.notinvited", "&cSorry, you can only join message groups with an invite.");
/* 282 */         messages.set("msggroup.error.notingroup", "&cSorry, you're not in that message group.");
/* 283 */         messages.set("msggroup.error.notexist", "&cSorry, that message group doesn't exist.");
/* 284 */         messages.set("msggroup.error.alreadyingroup", "&cWhoops, I think you're already in that group!");
/* 285 */         messages.set("msggroup.admin.listgroups.header", "&6Message Groups:");
/* 286 */         messages.set("msggroup.admin.listgroups.body", "&f- {{ NAME }}: {{ MEMBERS }}");
/* 287 */         messages.set("msggroup.admin.owner", "&a{{ PLAYER }} is now the owner of the {{ NAME }} message group.");
/* 288 */         messages.set("hoverlist.friend.order", "1");
/* 289 */         messages.set("hoverlist.friend.header", "&aFriends Online:");
/* 290 */         messages.set("hoverlist.staff.order", "2");
/* 291 */         messages.set("hoverlist.staff.header", "&6Staff Online:");
/* 292 */         messages.set("hoverlist.other.order", "0");
/* 293 */         messages.set("hoverlist.other.header", "&7Other Players:");
/* 294 */         if (messages.get("lookup.body").equals("&f - {{ PLAYER }}")) {
/* 295 */           messages.set("lookup.body", "&f - {{ PLAYER }}{{ HOVER: Click to view player info }}{{ CLICK: /" + (String)config.getStringList("commands.lookup").get(0) + " {{ PLAYER }} }}");
/*     */         }
/* 297 */         messages.set("lookup.player.header", "&6=====&l{{ PLAYER }}&6=====");
/* 298 */         messages.set("lookup.player.format", "&6{{ TYPE }}: &f{{ INFO }}{{ HOVER: Click to copy }}{{ CLICK: SUG: {{ INFO }} }}");
/* 299 */         if (messages.getString("errors.invalid").equals("&cInvalid arguments provided. Usage: {{ HELP }}")) {
/* 300 */           messages.set("errors.invalid", "&cInvalid arguments provided. Usage: {{ HELP }}{{ HOVER: Click to fill in command }}{{ CLICK: SUG: {{ HELP }} }}");
/*     */         }
/* 302 */         if (messages.getString("errors.offline").equals("&cSorry, that player is offline.")) {
/* 303 */           messages.set("errors.notfound", "&cSorry, no player was found.");
/*     */         } else {
/* 305 */           messages.set("errors.notfound", messages.getString("errors.offline"));
/*     */         } 
/* 307 */         messages.set("errors.offline", null);
/* 308 */         (new File(this.plugin.getDataFolder(), "players.yml")).delete();
/*     */       case 260:
/* 310 */         messages.set("msggroup.rename", "&aMessage group &f{{ OLDNAME }} &arenamed to &f{{ NAME }}&a.");
/*     */       case 261:
/* 312 */         if (messages.getString("message.format.receive").equals("&7[{{ BREAK }}&7{{ SENDER }}{{ HOVER: On the {{ SERVER }} server. }}{{ CLICK: SUG: /msg {{ SENDER }} }}{{ BREAK }}&7 » me] &f{{ MESSAGE }}{{ CLICK: SUG: /msg {{ SENDER }} }}")) {
/* 313 */           messages.set("message.format.receive", "&7[{{ SENDER }} » me]{{ HOVER: On the {{ SERVER }} server. Click to respond. }}{{ CLICK: SUG: /msg {{ SENDER }}  }} &f{{ MESSAGE }}");
/*     */         }
/*     */       case 262:
/*     */       case 263:
/* 317 */         config.set("database.format", "sqlite");
/* 318 */         config.set("configversion", null);
/* 319 */         config.set("configversion", "2.6.4"); break;
/*     */     } 
/* 321 */     this.plugin.saveMainConfig();
/* 322 */     this.plugin.saveMessagesConfig();
/* 323 */     this.plugin.getLogger().log(Level.INFO, "Config updated. You may edit new values to your liking.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getVersionFromString(String from) {
/* 333 */     String result = from.replace(".", "");
/*     */     
/* 335 */     return result.isEmpty() ? 0 : Integer.parseInt(result);
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\Updater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */