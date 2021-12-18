/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.ClickEvent;
/*     */ import net.md_5.bungee.api.chat.ComponentBuilder;
/*     */ import net.md_5.bungee.api.chat.HoverEvent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Dictionary
/*     */ {
/*     */   private static final String DEFAULT_CONFIG_VALUE = "INVALID CONFIGURATION VALUE";
/*     */   @Load(key = "errors.messages", def = "&cNobody has messaged you recently.")
/*     */   public static String ERROR_NOBODY_HAS_MESSAGED;
/*     */   @Load(key = "errors.slap", def = "&cYou are unworthy of slapping people.")
/*     */   public static String ERROR_UNWORTHY_OF_SLAP;
/*     */   @Load(key = "errors.notfound", def = "&cSorry, no player was found.")
/*     */   public static String ERROR_PLAYER_NOT_FOUND;
/*     */   @Load(key = "errors.invalid", def = "&cInvalid arguments provided. Usage: {{ HELP }}{{ HOVER: Click to fill in command }}{{ CLICK: SUG: {{ HELP }} }}")
/*     */   public static String ERROR_INVALID_ARGUMENTS;
/*     */   @Load(key = "errors.ignoreself", def = "&cYou can't ignore yourself!")
/*     */   public static String ERROR_IGNORE_SELF;
/*     */   @Load(key = "errors.ignoring", def = "&cYou can't send a message to someone you're ignoring!")
/*     */   public static String ERROR_IGNORING;
/*     */   @Load(key = "errors.sendfail", def = "&cUnable to send {{ PLAYER }} to {{ SERVER }}.")
/*     */   public static String ERROR_SENDFAIL;
/*     */   @Load(key = "format.send", def = "&aSending &e{{ PLAYER }} &ato server &e{{ SERVER }}")
/*     */   public static String FORMAT_SEND_PLAYER;
/*     */   @Load(key = "format.find", def = "&e{{ PLAYER }} &ais playing on &e{{ SERVER }}")
/*     */   public static String FORMAT_FIND_PLAYER;
/*     */   @Load(key = "format.join", def = "&8[&b+&8] &7{{ PLAYER }}")
/*     */   public static String FORMAT_JOIN;
/*     */   @Load(key = "format.quit", def = "&8[&a-&8] &7{{ PLAYER }}")
/*     */   public static String FORMAT_QUIT;
/*     */   @Load(key = "format.quit", def = "{{ PLAYER }} was kicked for {{ REASON }}")
/*     */   public static String FORMAT_KICK;
/*     */   @Load(key = "format.chat", def = "{{ PLAYER }}: {{ MESSAGE }}")
/*     */   public static String FORMAT_CHAT;
/*     */   @Load(key = "format.alert", def = "&8[&c!&8] &7{{ MESSAGE }}")
/*     */   public static String FORMAT_ALERT;
/*     */   @Load(key = "message.format.send", def = "&7[me » {{ BREAK }}&7{{ RECIPIENT }}{{ HOVER: On the {{ SERVER }} server. }}{{ BREAK }}&7] &f{{ MESSAGE }}")
/*     */   public static String MESSAGE_FORMAT_SEND;
/*     */   @Load(key = "message.format.receive", def = "&7[{{ SENDER }} » me]{{ HOVER: On the {{ SERVER }} server. Click to respond. }}{{ CLICK: SUG: /msg {{ SENDER }}  }} &f{{ MESSAGE }}")
/*     */   public static String MESSAGE_FORMAT_RECEIVE;
/*     */   @Load(key = "message.enabled", def = "&aMessaging is now enabled!")
/*     */   public static String MESSAGE_ENABLED;
/*     */   @Load(key = "message.disabled", def = "&cMessaging is now disabled!")
/*     */   public static String MESSAGE_DISABLED;
/*     */   @Load(key = "msggroup.format", def = "&9{{ NAME }} - {{ SENDER }} » &7{{ MESSAGE }}")
/*     */   public static String MG_FORMAT;
/*     */   @Load(key = "msggroup.create", def = "&aMessage group &f{{ NAME }} &asuccessfuly created! Invite players with /msggroup invite <username> {{ NAME }}!{{ HOVER: Click to prepare command. }}{{ CLICK: SUG: /msggroup invite <username> {{ NAME }} }}")
/*     */   public static String MG_CREATE;
/*     */   @Load(key = "msggroup.join", def = "&aSuccessfully joined the &f{{ NAME }} &amessage group.")
/*     */   public static String MG_JOIN;
/*     */   @Load(key = "msggroup.leave", def = "&aSuccessfully left the &f{{ NAME }} &amessage group.")
/*     */   public static String MG_LEAVE;
/*     */   @Load(key = "msggroup.rename", def = "&aMessage group &f{{ OLDNAME }} &arenamed to &f{{ NAME }}&a.")
/*     */   public static String MG_RENAME;
/*     */   @Load(key = "msggroup.invite.send", def = "&aSuccessfully invited &f{{ PLAYER }} &ato the &f{{ NAME }} &amessage group.")
/*     */   public static String MG_INVITE_SEND;
/*     */   @Load(key = "msggroup.invite.receive", def = "&aYou've been invited to join the &f{{ NAME }} &amessage group. Click to accept!{{ CLICK: /msggroup join {{ NAME }} }}")
/*     */   public static String MG_INVITE_RECEIVE;
/*     */   @Load(key = "msggroup.kick.send", def = "&aSuccessfully kicked &f{{ PLAYER }} &afrom the &f{{ NAME }} &amessage group.")
/*     */   public static String MG_KICK_SEND;
/*     */   @Load(key = "msggroup.kick.receive", def = "&cYou've been kicked from the &f{{ NAME }} &cmessage group.")
/*     */   public static String MG_KICK_RECEIVE;
/*     */   @Load(key = "msggroup.disband", def = "&aSuccessfully disbanded the &f{{ NAME }} &amessage group.")
/*     */   public static String MG_DISBAND;
/*     */   @Load(key = "msggroup.error.invalidname", def = "&cMessage group names must contain lowercase letters only, and must be at least 3 letters long.")
/*     */   public static String MG_ERROR_INVALID_NAME;
/*     */   @Load(key = "msggroup.error.nametaken", def = "&cSorry, that name has already been taken.")
/*     */   public static String MG_ERROR_NAME_TAKEN;
/*     */   @Load(key = "msggroup.error.notinvited", def = "&cSorry, you can only join message groups with an invite.")
/*     */   public static String MG_ERROR_NOT_INVITED;
/*     */   @Load(key = "msggroup.error.notingroup", def = "&cSorry, you're not in that message group.")
/*     */   public static String MG_ERROR_NOT_IN_GROUP;
/*     */   @Load(key = "msggroup.error.notexist", def = "&cSorry, that message group doesn't exist.")
/*     */   public static String MG_ERROR_NOT_EXIST;
/*     */   @Load(key = "msggroup.error.alreadyingroup", def = "&cWhoops, I think you're already in that group!")
/*     */   public static String MG_ERROR_ALREADY_IN_GROUP;
/*     */   @Load(key = "msggroup.admin.listgroups.header", def = "&6Message Groups:")
/*     */   public static String MGA_LIST_GROUPS_HEADER;
/*     */   @Load(key = "msggroup.admin.listgroups.body", def = "&f- {{ NAME }}: {{ MEMBERS }}")
/*     */   public static String MGA_LIST_GROUPS_BODY;
/*     */   @Load(key = "msggroup.admin.owner", def = "&a{{ PLAYER }} is now the owner of the {{ NAME }} message group.")
/*     */   public static String MGA_OWNER;
/*     */   @Load(key = "friend.header", def = "&2Current Friends:")
/*     */   public static String FRIEND_HEADER;
/*     */   @Load(key = "friend.body", def = "- {{ NAME }} ({{ SERVER }})")
/*     */   public static String FRIEND_BODY;
/*     */   @Load(key = "friend.new", def = "&aYou are now friends with {{ NAME }}!")
/*     */   public static String FRIEND_NEW;
/*     */   @Load(key = "friend.old", def = "&aYou are already friends with {{ NAME }}!")
/*     */   public static String FRIEND_OLD;
/*     */   @Load(key = "friend.remove", def = "&cYou are no longer friends with {{ NAME }}.")
/*     */   public static String FRIEND_REMOVE;
/*     */   @Load(key = "friend.removeerror", def = "&cYou can't remove a friend you don't have!")
/*     */   public static String CANNOT_REMOVE_FRIEND;
/*     */   @Load(key = "friend.outrequests.header", def = "&2Outgoing Friend Requests:")
/*     */   public static String OUTREQUESTS_HEADER;
/*     */   @Load(key = "friend.outrequests.body", def = "- {{ NAME }}")
/*     */   public static String OUTREQUESTS_BODY;
/*     */   @Load(key = "friend.outrequests.new", def = "&a{{ NAME }} has received your friend request.")
/*     */   public static String OUTREQUESTS_NEW;
/*     */   @Load(key = "friend.outrequests.old", def = "&cYou already requested to be friends with {{ NAME }}. Please wait for a response!")
/*     */   public static String OUTREQUESTS_OLD;
/*     */   @Load(key = "friend.outrequests.remove", def = "&cThe friend request to {{ NAME }} was cancelled.")
/*     */   public static String OUTREQUESTS_REMOVE;
/*     */   @Load(key = "friend.inrequests.header", def = "&2Incoming Friend Requests:")
/*     */   public static String INREQUESTS_HEADER;
/*     */   @Load(key = "friend.inrequests.body", def = "- {{ NAME }}{{ BREAK }}  &a[Y]{{ HOVER: Click to accept the request! }}{{ CLICK: /friend add {{ NAME }} }}{{ BREAK }} {{ BREAK }}&c[N]{{ HOVER: Click to deny the request. }}{{ CLICK: /friend remove {{ NAME }} }}")
/*     */   public static String INREQUESTS_BODY;
/*     */   @Load(key = "friend.inrequests.new", def = "&a{{ NAME }} would like to be your friend! Click one:{{ BREAK }}\n&a[Yes!]{{ HOVER: Click to accept the request! }}{{ CLICK: /friend add {{ NAME }} }}{{ BREAK }}     {{ BREAK }}&c[No]{{ HOVER: Click to deny the request. }}{{ CLICK: /friend remove {{ NAME }} }}")
/*     */   public static String INREQUESTS_NEW;
/*     */   @Load(key = "friend.inrequests.remove", def = "&cThe friend request from {{ NAME }} was cancelled.")
/*     */   public static String INREQUESTS_REMOVE;
/*     */   @Load(key = "list.header", def = "You are on {{ CURRENT }}\n&aServers:")
/*     */   public static String LIST_HEADER;
/*     */   @Load(key = "list.body", def = "&a- {{ SERVER }} ({{ DENSITY }}&a)")
/*     */   public static String LIST_BODY;
/*     */   @Load(key = "hoverlist.friend.order", def = "1")
/*     */   public static String HOVER_FRIEND_ORDER;
/*     */   @Load(key = "hoverlist.friend.header", def = "&aFriends Online:")
/*     */   public static String HOVER_FRIEND_HEADER;
/*     */   @Load(key = "hoverlist.staff.order", def = "2")
/*     */   public static String HOVER_STAFF_ORDER;
/*     */   @Load(key = "hoverlist.staff.header", def = "&6Staff Online:")
/*     */   public static String HOVER_STAFF_HEADER;
/*     */   @Load(key = "hoverlist.other.order", def = "0")
/*     */   public static String HOVER_OTHER_ORDER;
/*     */   @Load(key = "hoverlist.other.header", def = "&fOther Players:")
/*     */   public static String HOVER_OTHER_HEADER;
/*     */   @Load(key = "lookup.header", def = "&6Found {{ SIZE }} player(s):")
/*     */   public static String LOOKUP_HEADER;
/*     */   @Load(key = "lookup.body", def = "&f - {{ PLAYER }}{{ HOVER: Click to view player info }}{{ CLICK: /lookup {{ PLAYER }} }}")
/*     */   public static String LOOKUP_BODY;
/*     */   @Load(key = "lookup.player.header", def = "&6=====&l{{ PLAYER }}&6=====")
/*     */   public static String LOOKUP_PLAYER_HEADER;
/*     */   @Load(key = "lookup.player.format", def = "&6{{ TYPE }}: &f{{ INFO }}{{ HOVER: Click to copy }}{{ CLICK: SUG: {{ INFO }} }}")
/*     */   public static String LOOKUP_PLAYER_FORMAT;
/*     */   @Load(key = "spy.message", def = "&a({{ SERVER }}) &7[{{ SENDER }} » {{ RECIPIENT }}] &f{{{ MESSAGE }}}")
/*     */   public static String SPY_MESSAGE;
/*     */   @Load(key = "spy.enabled", def = "&aSpy has been enabled!")
/*     */   public static String SPY_ENABLED;
/*     */   @Load(key = "spy.disabled", def = "&cSpy has been disabled!")
/*     */   public static String SPY_DISABLED;
/*     */   @Load(key = "commandspy.command", def = "&7[{{ SENDER }}] &b{{ COMMAND }}")
/*     */   public static String CSPY_COMMAND;
/*     */   @Load(key = "commandspy.enabled", def = "&aCommand Spy has been enabled!")
/*     */   public static String CSPY_ENABLED;
/*     */   @Load(key = "commandspy.disabled", def = "&cCommand Spy has been disabled!")
/*     */   public static String CSPY_DISABLED;
/*     */   @Load(key = "hide.enabled", def = "&aYou are now hidden from all users!")
/*     */   public static String HIDE_ENABLED;
/*     */   @Load(key = "hide.disabled", def = "&cYou are no longer hidden!")
/*     */   public static String HIDE_DISABLED;
/*     */   @Load(key = "ignore.enabled", def = "&6Now ignoring &{ PLAYER }}.")
/*     */   public static String IGNORE_ENABLED;
/*     */   @Load(key = "ignore.disabled", def = "&6No longer ignoring {{ PLAYER }}.")
/*     */   public static String IGNORE_DISABLED;
/*     */   @Load(key = "mute.muted.enabled", def = "&cYou are now muted!")
/*     */   public static String MUTE_ENABLED;
/*     */   @Load(key = "mute.muted.disabled", def = "&aYou are no longer muted!")
/*     */   public static String MUTE_DISABLED;
/*     */   @Load(key = "mute.muted.error", def = "&cHey, you can't chat while muted!")
/*     */   public static String MUTE_ERROR;
/*     */   @Load(key = "mute.muter.enabled", def = "&c{{ PLAYER }} is now muted!")
/*     */   public static String MUTE_ENABLEDN;
/*     */   @Load(key = "mute.muter.disabled", def = "&a{{ PLAYER }} is no longer muted!")
/*     */   public static String MUTE_DISABLEDN;
/*     */   @Load(key = "mute.muter.error", def = "&7{{ PLAYER }} tried to chat while muted!")
/*     */   public static String MUTE_ERRORN;
/*     */   @Load(key = "mute.muter.exempt", def = "&cHey, you can't mute that player!")
/*     */   public static String MUTE_EXEMPT;
/*     */   @Load(key = "slap.slapper", def = "&aYou just slapped &e{{ SLAPPED }}&a. I bet that felt good, didn't it?")
/*     */   public static String SLAPPER_MSG;
/*     */   @Load(key = "slap.slapped", def = "&cYou were just slapped by &e{{ SLAPPER }}&c! Ouch! (/slap him back!)")
/*     */   public static String SLAPPED_MSG;
/*     */   @Load(key = "rulenotify.advertisement", def = "&7{{ PLAYER }} just advertised!")
/*     */   public static String NOTIFY_ADVERTISEMENT;
/*     */   @Load(key = "rulenotify.cursing", def = "&7{{ PLAYER }} just swore!")
/*     */   public static String NOTIFY_CURSING;
/*     */   @Load(key = "rulenotify.replace", def = "&7{{ PLAYER }} swore but was replaced!")
/*     */   public static String NOTIFY_REPLACE;
/*     */   @Load(key = "rulenotify.command", def = "&7{{ PLAYER }} swore, triggering a command!")
/*     */   public static String NOTIFY_COMMAND;
/*     */   @Load(key = "bannedwords.replace", def = "*")
/*     */   public static String BANNED_REPLACE;
/*     */   @Load(key = "staffchat.message", def = "&c[{{ SERVER }} - {{ SENDER }}] » &7{{ MESSAGE }}")
/*     */   public static String FORMAT_STAFF_CHAT;
/*     */   @Load(key = "staffchat.enabled", def = "&aYou are now chatting in staff chat!")
/*     */   public static String SCHAT_ENABLED;
/*     */   @Load(key = "staffchat.disabled", def = "&cYou are no longer chatting in staff chat!")
/*     */   public static String SCHAT_DISABLED;
/*     */   @Load(key = "chat.message", def = "&e{{ SERVER }} - {{ SENDER }} » &7{{ MESSAGE }}")
/*     */   public static String FORMAT_GCHAT;
/*     */   @Load(key = "chat.enabled", def = "&aYou are now chatting in global chat!")
/*     */   public static String GCHAT_ENABLED;
/*     */   @Load(key = "chat.disabled", def = "&cYou are no longer chatting in global chat!")
/*     */   public static String GCHAT_DISABLED;
/*     */   @Load(key = "warnings.similarity", def = "&cPlease do not spam other players!")
/*     */   public static String WARNING_LEVENSHTEIN_DISTANCE;
/*     */   @Load(key = "warnings.swearing", def = "&cPlease do not swear at other players!")
/*     */   public static String WARNING_HANDLE_CURSING;
/*     */   @Load(key = "warnings.advertising", def = "&cPlease do not advertise other servers!")
/*     */   public static String WARNINGS_ADVERTISING;
/*     */   @Load(key = "errors.fastrelog", def = "&cPlease wait before reconnecting!")
/*     */   public static String FAST_RELOG_KICK;
/*     */   private static SimpleDateFormat date;
/* 247 */   private static Calendar calendar = Calendar.getInstance(); static {
/* 248 */     date = new SimpleDateFormat("H:mm:ss");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String color(String str) {
/* 258 */     return ChatColor.translateAlternateColorCodes('&', str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String combine(int omit, String delim, String[] array) {
/* 270 */     StringBuilder builder = new StringBuilder();
/* 271 */     for (int i = 0; i < array.length; i++) {
/* 272 */       if (i != omit) {
/* 273 */         builder.append(array[i]);
/* 274 */         if (i != array.length - 1) {
/* 275 */           builder.append(delim);
/*     */         }
/*     */       } 
/*     */     } 
/* 279 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public static String combine(String delim, String[] array) {
/* 283 */     return combine(-1, delim, array);
/*     */   }
/*     */   
/*     */   public static String combine(String[] array) {
/* 287 */     return combine(" ", array);
/*     */   }
/*     */   
/*     */   public static String combine(String delim, List<String> array) {
/* 291 */     return combine(delim, array.<String>toArray(new String[array.size()]));
/*     */   }
/*     */   
/*     */   public static String combine(String delim, Set<String> array) {
/* 295 */     return combine(delim, array.<String>toArray(new String[array.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String combine(int omit, String[] array) {
/* 306 */     StringBuilder builder = new StringBuilder();
/* 307 */     for (String string : array) {
/* 308 */       if (!string.equals(array[omit])) {
/* 309 */         builder.append(string);
/* 310 */         if (!string.equals(array[array.length - 1])) {
/* 311 */           builder.append(" ");
/*     */         }
/*     */       } 
/*     */     } 
/* 315 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String capitalizeFirst(String input) {
/* 325 */     return input.substring(0, 1).toUpperCase() + input.substring(1);
/*     */   }
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
/*     */   public static TextComponent format(String input, boolean color, boolean hover, boolean click, String... args) {
/* 340 */     input = input.replace("{{ TIME }}", getTime());
/*     */     
/* 342 */     input = input.replace("{{ RAQUO }}", "»");
/*     */     
/* 344 */     if (args.length % 2 == 0) {
/* 345 */       for (int i = 0; i < args.length; i += 2) {
/* 346 */         if (args[i] == null || args[i + 1] == null) {
/* 347 */           return null;
/*     */         }
/* 349 */         input = input.replace("{{ " + args[i].toUpperCase() + " }}", args[i + 1]);
/*     */       } 
/*     */     }
/*     */     
/* 353 */     if (!input.contains("{{ BREAK }}")) {
/* 354 */       input = input + "{{ BREAK }}";
/*     */     }
/* 356 */     TextComponent finalText = new TextComponent("");
/*     */     
/* 358 */     for (String segment : input.split("\\{\\{ BREAK }}")) {
/*     */       
/* 360 */       HoverEvent hoverEvent = null;
/* 361 */       ClickEvent clickEvent = null;
/*     */       
/* 363 */       if (hover && 
/* 364 */         segment.contains("{{ HOVER: ")) {
/* 365 */         String[] hoverTextStripped = segment.split("(\\{\\{ HOVER: )([^\\}]+)( \\}\\})");
/* 366 */         String hoverText = segment;
/* 367 */         for (String aHoverTextStripped : hoverTextStripped) {
/* 368 */           hoverText = hoverText.replace(aHoverTextStripped, "");
/*     */         }
/* 370 */         hoverText = hoverText.replace("{{ HOVER: ", "").replace(" }}", "");
/* 371 */         if (hoverText != null) {
/* 372 */           hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(color ? color(hoverText) : hoverText)).retain(ComponentBuilder.FormatRetention.ALL).create());
/* 373 */           segment = Joiner.on("").join((Object[])hoverTextStripped);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 378 */       if (click && 
/* 379 */         segment.contains("{{ CLICK: ")) {
/* 380 */         String[] clickTextStripped = segment.split("(\\{\\{ CLICK: )([^\\}]+)( \\}\\})");
/* 381 */         String clickText = segment;
/* 382 */         for (String aClickTextStripped : clickTextStripped) {
/* 383 */           clickText = clickText.replace(aClickTextStripped, "");
/*     */         }
/* 385 */         clickText = clickText.replace("{{ CLICK: ", "").replace(" }}", "");
/* 386 */         if (clickText.startsWith("SUG: ")) {
/* 387 */           clickText = clickText.substring(5);
/* 388 */           clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, clickText);
/* 389 */           segment = Joiner.on("").join((Object[])clickTextStripped);
/* 390 */         } else if (clickText != null) {
/* 391 */           clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickText);
/* 392 */           segment = Joiner.on("").join((Object[])clickTextStripped);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 397 */       TextComponent textSegment = new TextComponent(TextComponent.fromLegacyText(color ? color(segment) : segment));
/* 398 */       textSegment.setHoverEvent(hoverEvent);
/* 399 */       textSegment.setClickEvent(clickEvent);
/*     */       
/* 401 */       finalText.addExtra((BaseComponent)textSegment);
/*     */     } 
/*     */     
/* 404 */     return finalText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextComponent format(String input, String... args) {
/* 415 */     return format(input, true, true, true, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextComponent formatMsg(String input, String... args) {
/* 426 */     TextComponent finalText = new TextComponent("");
/*     */     
/* 428 */     ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[3]);
/* 429 */     if (player != null) {
/* 430 */       int count = (input.length() - input.replace("{{ MESSAGE }}", "").length()) / 13;
/* 431 */       TextComponent message = format("{{ MESSAGE }}", player.hasPermission("gssentials.message.color"), player.hasPermission("gssentials.message.hover"), player.hasPermission("gssentials.message.click"), args);
/*     */       
/* 433 */       if (input.startsWith("{{ MESSAGE }}")) {
/* 434 */         count--;
/* 435 */         finalText.addExtra((BaseComponent)message);
/*     */       } 
/*     */       
/* 438 */       if (input.endsWith("{{ MESSAGE }}")) {
/* 439 */         count--;
/*     */       }
/*     */       
/* 442 */       for (String part : input.split("\\{\\{ MESSAGE }}")) {
/* 443 */         finalText.addExtra((BaseComponent)format(part, args));
/* 444 */         if (count > 0) {
/* 445 */           count--;
/* 446 */           finalText.addExtra((BaseComponent)message);
/*     */         } 
/*     */       } 
/*     */       
/* 450 */       if (input.endsWith("{{ MESSAGE }}")) {
/* 451 */         finalText.addExtra((BaseComponent)message);
/*     */       }
/*     */       
/* 454 */       return finalText;
/*     */     } 
/*     */     
/* 457 */     return format(color(input), args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void load() throws IllegalAccessException {
/* 467 */     Configuration messages = BungeeEssentials.getInstance().getMessages();
/* 468 */     for (Field field : Dictionary.class.getDeclaredFields()) {
/* 469 */       int mod = field.getModifiers();
/* 470 */       if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && field.isAnnotationPresent((Class)Load.class)) {
/* 471 */         Load load = field.<Load>getAnnotation(Load.class);
/* 472 */         String value = messages.getString(load.key(), "INVALID CONFIGURATION VALUE");
/* 473 */         if (value.equals("INVALID CONFIGURATION VALUE")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 483 */           BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Your configuration is either outdated or invalid!");
/* 484 */           BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Falling back to default value for key \"{0}\"", load.key());
/* 485 */           value = load.def();
/*     */         } 
/* 487 */         field.set((Object)null, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static String getTime() {
/* 493 */     return date.format(calendar.getTime());
/*     */   }
/*     */   
/*     */   @Target({ElementType.FIELD})
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   private static @interface Load {
/*     */     String key();
/*     */     
/*     */     String def();
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\Dictionary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */