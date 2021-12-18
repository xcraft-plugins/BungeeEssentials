/*     */ package com.pantherman594.gssentials;
/*     */ 
/*     */ import com.pantherman594.gssentials.database.PlayerData;
/*     */ import com.pantherman594.gssentials.event.GlobalChatEvent;
/*     */ import com.pantherman594.gssentials.event.StaffChatEvent;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Collectors;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.ServerPing;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.config.ServerInfo;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.event.ChatEvent;
/*     */ import net.md_5.bungee.api.event.LoginEvent;
/*     */ import net.md_5.bungee.api.event.PlayerDisconnectEvent;
/*     */ import net.md_5.bungee.api.event.PostLoginEvent;
/*     */ import net.md_5.bungee.api.event.ProxyPingEvent;
/*     */ import net.md_5.bungee.api.event.ServerConnectedEvent;
/*     */ import net.md_5.bungee.api.event.ServerKickEvent;
/*     */ import net.md_5.bungee.api.event.TabCompleteResponseEvent;
/*     */ import net.md_5.bungee.api.plugin.Event;
/*     */ import net.md_5.bungee.api.plugin.Listener;
/*     */ import net.md_5.bungee.event.EventHandler;
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
/*     */ public class PlayerListener
/*     */   implements Listener
/*     */ {
/*  50 */   private final HashSet<InetAddress> connections = new HashSet<>();
/*  51 */   private final Map<InetAddress, ServerInfo> redirServer = new HashMap<>();
/*  52 */   private Map<UUID, String> cmds = new HashMap<>();
/*  53 */   private Map<UUID, String> cmdLog = new HashMap<>();
/*  54 */   private Messenger mess = BungeeEssentials.getInstance().getMessenger();
/*  55 */   private PlayerData pD = BungeeEssentials.getInstance().getPlayerData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void chat(ChatEvent event) {
/*  66 */     ProxiedPlayer player = (ProxiedPlayer)event.getSender();
/*  67 */     String uuid = player.getUniqueId().toString();
/*  68 */     String sender = player.getName();
/*  69 */     if (event.isCommand()) {
/*  70 */       if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/*  71 */         Log.log(Dictionary.format("[COMMAND] " + Dictionary.FORMAT_CHAT, new String[] { "PLAYER", sender, "MESSAGE", event.getMessage() }).toLegacyText(), new Object[0]);
/*     */       }
/*  73 */       String cmd = event.getMessage().substring(1);
/*  74 */       if (BungeeEssentials.getInstance().contains(new String[] { "spam-command" }) && !player.hasPermission("gssentials.admin.bypass-filter")) {
/*  75 */         if (this.cmds.get(player.getUniqueId()) != null && (cmd.equals(this.cmds.get(player.getUniqueId())) & this.cmdLog.containsKey(player.getUniqueId())) != 0) {
/*  76 */           player.sendMessage((BaseComponent)Dictionary.format(Dictionary.WARNING_LEVENSHTEIN_DISTANCE, new String[0]));
/*  77 */           event.setCancelled(true);
/*     */         } 
/*  79 */         this.cmds.put(player.getUniqueId(), cmd);
/*  80 */         String time = Dictionary.getTime();
/*  81 */         this.cmdLog.put(player.getUniqueId(), time);
/*  82 */         ProxyServer.getInstance().getScheduler().schedule(BungeeEssentials.getInstance(), () -> { if (this.cmdLog.containsKey(player.getUniqueId()) && ((String)this.cmdLog.get(player.getUniqueId())).equals(time)) this.cmdLog.remove(player.getUniqueId());  }5L, TimeUnit.SECONDS);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       if (!event.isCancelled() && !player.hasPermission("gssentials.admin.spy.exempt") && BungeeEssentials.getInstance().contains(new String[] { "commandSpy" })) {
/*  89 */         ProxyServer.getInstance().getPlayers().stream().filter(onlinePlayer -> (onlinePlayer.getUniqueId() != player.getUniqueId() && onlinePlayer.hasPermission("gssentials.admin.spy.command") && this.pD.isCSpy(onlinePlayer.getUniqueId().toString()))).forEach(onlinePlayer -> onlinePlayer.sendMessage((BaseComponent)Dictionary.format(Dictionary.CSPY_COMMAND, new String[] { "SENDER", sender, "COMMAND", event.getMessage() })));
/*  90 */         if (this.pD.isCSpy("CONSOLE")) {
/*  91 */           ProxyServer.getInstance().getConsole().sendMessage(Dictionary.format(Dictionary.CSPY_COMMAND, new String[] { "SENDER", sender, "COMMAND", event.getMessage() }).toLegacyText());
/*     */         }
/*     */       } 
/*  94 */       if (BungeeEssentials.getInstance().contains(new String[] { "server" }) && player.hasPermission("gssentials.list") && cmd.split(" ")[0].startsWith("server") && (cmd.split(" ")).length == 1) {
/*  95 */         event.setCancelled(true);
/*  96 */         ProxyServer.getInstance().getPluginManager().dispatchCommand((CommandSender)player, BungeeEssentials.getInstance().getMain("list"));
/*     */       } 
/*     */     } else {
/*     */       
/* 100 */       if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/* 101 */         Log.log(Dictionary.format("[CHAT] " + Dictionary.FORMAT_CHAT, new String[] { "PLAYER", sender, "MESSAGE", event.getMessage() }).toLegacyText(), new Object[0]);
/*     */       }
/* 103 */       if (this.mess.isMutedF(player, event.getMessage())) {
/* 104 */         event.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/* 108 */       if (BungeeEssentials.getInstance().getConfig().getBoolean("capspam.enabled", true) && !player.hasPermission("gssentials.admin.bypass-filter") && event.getMessage().length() >= 5) {
/* 109 */         String msg = event.getMessage();
/* 110 */         int upperC = msg.replaceAll("[^A-Z]", "").length();
/*     */         
/* 112 */         if ((upperC * 100 / msg.length()) >= BungeeEssentials.getInstance().getConfig().getDouble("capspam.percent", 50.0D))
/* 113 */           event.setMessage(event.getMessage().toLowerCase()); 
/*     */       } 
/* 115 */       if (!event.isCancelled() && !event.isCommand()) {
/* 116 */         if (player.hasPermission("gssentials.admin.chat") && this.pD.isStaffChat(uuid)) {
/* 117 */           String server = player.getServer().getInfo().getName();
/* 118 */           ProxyServer.getInstance().getPluginManager().callEvent((Event)new StaffChatEvent(server, sender, event.getMessage()));
/* 119 */           event.setCancelled(true);
/* 120 */         } else if (player.hasPermission("gssentials.chat") && this.pD.isGlobalChat(uuid)) {
/* 121 */           String server = player.getServer().getInfo().getName();
/* 122 */           ProxyServer.getInstance().getPluginManager().callEvent((Event)new GlobalChatEvent(server, sender, event.getMessage()));
/* 123 */           event.setCancelled(true);
/*     */         } 
/*     */       }
/* 126 */       if (BungeeEssentials.getInstance().contains(new String[] { "spam-chat", "rules-chat" })) {
/* 127 */         if (event.isCommand() || event.isCancelled()) {
/*     */           return;
/*     */         }
/* 130 */         this.mess.chat(player, event);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = -65)
/*     */   public void login(LoginEvent event) {
/* 143 */     if (BungeeEssentials.getInstance().contains(new String[] { "fastRelog" })) {
/* 144 */       if (this.connections.contains(event.getConnection().getAddress().getAddress())) {
/* 145 */         event.setCancelled(true);
/* 146 */         event.setCancelReason(Dictionary.format(Dictionary.FAST_RELOG_KICK, new String[0]).toLegacyText());
/*     */         return;
/*     */       } 
/* 149 */       this.connections.add(event.getConnection().getAddress().getAddress());
/* 150 */       ProxyServer.getInstance().getScheduler().schedule(BungeeEssentials.getInstance(), () -> this.connections.remove(event.getConnection().getAddress().getAddress()), 5L, TimeUnit.SECONDS);
/*     */     } 
/* 152 */     if (BungeeEssentials.getInstance().contains(new String[] { "autoredirect" })) {
/* 153 */       String[] ip = event.getConnection().getVirtualHost().getHostName().split("\\.");
/* 154 */       for (ServerInfo info : ProxyServer.getInstance().getServers().values()) {
/* 155 */         if (info.getName().equalsIgnoreCase(ip[0])) {
/* 156 */           this.redirServer.put(event.getConnection().getAddress().getAddress(), info);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void postLogin(PostLoginEvent event) {
/* 172 */     this.pD.setName(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName());
/* 173 */     this.pD.setIp(event.getPlayer().getUniqueId().toString(), event.getPlayer().getAddress().getAddress().getHostAddress());
/* 174 */     if (BungeeEssentials.getInstance().contains(new String[] { "joinAnnounce" }) && !this.pD.isHidden(event.getPlayer().getUniqueId().toString()) && !Dictionary.FORMAT_JOIN.equals("") && event.getPlayer().hasPermission("gssentials.announce.join") && (!BungeeEssentials.getInstance().isIntegrated() || !BungeeEssentials.getInstance().getIntegrationProvider().isBanned(event.getPlayer()))) {
/* 175 */       ProxyServer.getInstance().broadcast((BaseComponent)Dictionary.format(Dictionary.FORMAT_JOIN, new String[] { "PLAYER", event.getPlayer().getName() }));
/*     */     }
/* 177 */     if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/* 178 */       Log.log(Dictionary.format("[JOIN] " + Dictionary.FORMAT_JOIN, new String[] { "PLAYER", event.getPlayer().getName() }).toLegacyText(), new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void connect(ServerConnectedEvent event) {
/* 190 */     if (this.redirServer.containsKey(event.getPlayer().getAddress().getAddress())) {
/* 191 */       ServerInfo info = this.redirServer.get(event.getPlayer().getAddress().getAddress());
/* 192 */       if (info.canAccess((CommandSender)event.getPlayer())) {
/* 193 */         event.getPlayer().connect(info);
/*     */       }
/* 195 */       this.redirServer.remove(event.getPlayer().getAddress().getAddress());
/*     */     } 
/* 197 */     if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/* 198 */       Log.log(Dictionary.format("[CONNECT] {{ PLAYER }} connected to {{ SERVER }}.", new String[] { "PLAYER", event.getPlayer().getName(), "SERVER", event.getServer().getInfo().getName() }).toLegacyText(), new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void logout(PlayerDisconnectEvent event) {
/* 211 */     if (BungeeEssentials.getInstance().contains(new String[] { "fastRelog"
/* 212 */         }) && !this.connections.contains(event.getPlayer().getAddress().getAddress())) {
/* 213 */       this.connections.add(event.getPlayer().getAddress().getAddress());
/* 214 */       ProxyServer.getInstance().getScheduler().schedule(BungeeEssentials.getInstance(), () -> this.connections.remove(event.getPlayer().getAddress().getAddress()), 3L, TimeUnit.SECONDS);
/*     */     } 
/*     */     
/* 217 */     if (BungeeEssentials.getInstance().contains(new String[] { "joinAnnounce" }) && !Dictionary.FORMAT_QUIT.equals("") && event.getPlayer().hasPermission("gssentials.announce.quit") && !this.pD.isHidden(event.getPlayer().getUniqueId().toString())) {
/* 218 */       ProxyServer.getInstance().broadcast((BaseComponent)Dictionary.format(Dictionary.FORMAT_QUIT, new String[] { "PLAYER", event.getPlayer().getName() }));
/*     */     }
/* 220 */     if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/* 221 */       Log.log(Dictionary.format("[QUIT] " + Dictionary.FORMAT_QUIT, new String[] { "PLAYER", event.getPlayer().getName() }).toLegacyText(), new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void kick(ServerKickEvent event) {
/* 234 */     if (BungeeEssentials.getInstance().contains(new String[] { "fulllog" })) {
/* 235 */       Log.log(Dictionary.format("[KICK] " + Dictionary.FORMAT_KICK, new String[] { "PLAYER", event.getPlayer().getName(), "REASON", event.getKickReason() }).toLegacyText(), new Object[0]);
/*     */     }
/* 237 */     if (event.getKickReasonComponent()[0].toPlainText().equals("Server closed")) {
/* 238 */       sendFallback(event);
/*     */     } else {
/* 240 */       try (Socket s = new Socket()) {
/* 241 */         s.connect(event.getKickedFrom().getAddress());
/* 242 */       } catch (IOException e) {
/* 243 */         sendFallback(event);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void ping(ProxyPingEvent event) {
/* 256 */     ServerPing response = event.getResponse();
/* 257 */     ServerPing.Players players = response.getPlayers();
/*     */     
/* 259 */     if (BungeeEssentials.getInstance().contains(new String[] { "hoverlist" }) && !ProxyServer.getInstance().getPlayers().isEmpty()) {
/*     */       
/* 261 */       String uuid = (String)this.pD.getData("ip", event.getConnection().getAddress().getAddress().getHostAddress(), "uuid");
/*     */       
/* 263 */       UUID EMPTY_UUID = UUID.fromString("0-0-0-0-0");
/* 264 */       List<ServerPing.PlayerInfo> infos = new ArrayList<>();
/* 265 */       List<ServerPing.PlayerInfo> friends = new ArrayList<>();
/*     */       
/* 267 */       if (uuid != null) {
/* 268 */         for (String fUuid : this.pD.getFriends(uuid)) {
/*     */           ProxiedPlayer fP;
/* 270 */           if ((fP = ProxyServer.getInstance().getPlayer(UUID.fromString(fUuid))) != null && 
/* 271 */             !this.pD.isHidden(fUuid)) {
/* 272 */             friends.add(new ServerPing.PlayerInfo(fP.getName(), fUuid));
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 277 */         if (!friends.isEmpty()) {
/* 278 */           friends.add(0, new ServerPing.PlayerInfo(Dictionary.color(Dictionary.HOVER_FRIEND_HEADER), EMPTY_UUID));
/*     */         }
/*     */       } 
/*     */       
/* 282 */       List<ServerPing.PlayerInfo> staff = new ArrayList<>();
/* 283 */       List<ServerPing.PlayerInfo> other = new ArrayList<>();
/*     */       
/* 285 */       for (ProxiedPlayer p : this.mess.getVisiblePlayers(false)) {
/* 286 */         ServerPing.PlayerInfo info = new ServerPing.PlayerInfo(p.getName(), p.getUniqueId());
/* 287 */         if (!friends.contains(info)) {
/* 288 */           if (p.hasPermission("gssentials.admin.hover-list")) {
/* 289 */             staff.add(info); continue;
/*     */           } 
/* 291 */           other.add(info);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 296 */       if (!staff.isEmpty()) {
/* 297 */         staff.add(0, new ServerPing.PlayerInfo(Dictionary.color(Dictionary.HOVER_STAFF_HEADER), EMPTY_UUID));
/*     */       }
/*     */       
/* 300 */       if (!other.isEmpty()) {
/* 301 */         other.add(0, new ServerPing.PlayerInfo(Dictionary.color(Dictionary.HOVER_OTHER_HEADER), EMPTY_UUID));
/*     */       }
/*     */       
/* 304 */       Map<String, List<ServerPing.PlayerInfo>> orders = new TreeMap<>();
/* 305 */       orders.put(Dictionary.HOVER_FRIEND_ORDER, friends);
/* 306 */       orders.put(Dictionary.HOVER_STAFF_ORDER, staff);
/* 307 */       orders.put(Dictionary.HOVER_OTHER_ORDER, other);
/*     */       
/* 309 */       orders.keySet().stream().filter(order -> (Integer.valueOf(order).intValue() > 0)).forEach(order -> infos.addAll((Collection)orders.get(order)));
/*     */       
/* 311 */       players.setSample(infos.<ServerPing.PlayerInfo>toArray(new ServerPing.PlayerInfo[(infos.size() > 12) ? 12 : infos.size()]));
/* 312 */       players.setOnline(this.mess.getVisiblePlayers(false).size());
/* 313 */       response.setPlayers(players);
/* 314 */       event.setResponse(response);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 127)
/*     */   public void tab(TabCompleteResponseEvent event) {
/* 326 */     List<String> suggestions = event.getSuggestions();
/* 327 */     List<String> remove = (List<String>)suggestions.stream().filter(suggestion -> (ProxyServer.getInstance().getPlayer(suggestion) instanceof ProxiedPlayer && this.pD.isHidden(ProxyServer.getInstance().getPlayer(suggestion).getUniqueId().toString()))).collect(Collectors.toList());
/* 328 */     remove.forEach(suggestions::remove);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendFallback(ServerKickEvent e) {
/* 338 */     e.setCancelled(true);
/* 339 */     for (String server : e.getPlayer().getPendingConnection().getListener().getServerPriority()) {
/* 340 */       ServerInfo info = ProxyServer.getInstance().getServerInfo(server); try {
/* 341 */         try (Socket s = new Socket()) {
/* 342 */           s.connect(info.getAddress());
/* 343 */           e.getPlayer().connect(info);
/*     */         }  break;
/* 345 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\PlayerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */