/*     */ package com.pantherman594.gssentials.command.general;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.pantherman594.gssentials.BungeeEssentials;
/*     */ import com.pantherman594.gssentials.Dictionary;
/*     */ import com.pantherman594.gssentials.Messenger;
/*     */ import com.pantherman594.gssentials.command.BECommand;
/*     */ import com.pantherman594.gssentials.database.MsgGroups;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.TabExecutor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MsgGroupCommand
/*     */   extends BECommand
/*     */   implements TabExecutor
/*     */ {
/*     */   private MsgGroups msgGroups;
/*     */   
/*     */   public MsgGroupCommand() {
/*  30 */     super("msggroup", "");
/*  31 */     this.msgGroups = BungeeEssentials.getInstance().getMsgGroups();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(CommandSender sender, String[] args) {
/*  36 */     if (args.length > 0 && args[0].equalsIgnoreCase("admin") && sender.hasPermission("gssentials.admin.gmessage")) {
/*  37 */       String uuid = "CONSOLE";
/*  38 */       if (sender instanceof ProxiedPlayer) {
/*  39 */         uuid = ((ProxiedPlayer)sender).getUniqueId().toString();
/*     */       }
/*     */       
/*  42 */       if (args.length > 1) {
/*  43 */         if (args.length == 2 && args[1].equalsIgnoreCase("listgroups")) {
/*  44 */           sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MGA_LIST_GROUPS_HEADER, new String[0]));
/*  45 */           List<Object> groups = this.msgGroups.listAllData("groupname");
/*  46 */           if (groups != null) {
/*  47 */             for (Object o : groups) {
/*  48 */               String name = (String)o;
/*  49 */               sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MGA_LIST_GROUPS_BODY, new String[] { "NAME", name, "OWNER", this.msgGroups.getOwner(name), "MEMBERS", Dictionary.combine(", ", this.msgGroups.getMembers(name)) }));
/*     */             } 
/*     */           } else {
/*  52 */             sender.sendMessage((BaseComponent)Dictionary.format("None found.", new String[0]));
/*     */           } 
/*  54 */         } else if (args.length == 3) {
/*  55 */           String name = args[2].toLowerCase();
/*  56 */           switch (args[1]) {
/*     */             case "join":
/*  58 */               if (sender.hasPermission("gssentials.admin.gmessage.forcejoin") || sender.hasPermission("gssentials.admin.gmessage.all")) {
/*  59 */                 if (this.msgGroups.createDataNotExist(name)) {
/*  60 */                   if (this.msgGroups.getMembers(name).contains(uuid)) {
/*  61 */                     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_ALREADY_IN_GROUP, new String[] { "NAME", name }));
/*     */                   } else {
/*  63 */                     this.msgGroups.addMember(name, uuid);
/*  64 */                     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_JOIN, new String[] { "NAME", name }));
/*     */                   } 
/*     */                 } else {
/*  67 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_EXIST, new String[] { "NAME", name }));
/*     */                 } 
/*     */               } else {
/*  70 */                 sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */               } 
/*     */               return;
/*     */             case "disband":
/*  74 */               if (sender.hasPermission("gssentials.admin.gmessage.disband") || sender.hasPermission("gssentials.admin.gmessage.all")) {
/*  75 */                 if (this.msgGroups.createDataNotExist(name)) {
/*  76 */                   ProxyServer.getInstance().getPlayers().stream().filter(recipient -> this.msgGroups.getMembers(name).contains(recipient.getUniqueId().toString())).forEach(recipient -> recipient.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_KICK_RECEIVE, new String[] { "NAME", name })));
/*  77 */                   this.msgGroups.remove(name);
/*  78 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_DISBAND, new String[] { "NAME", name }));
/*     */                 } else {
/*  80 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_EXIST, new String[] { "NAME", name }));
/*     */                 } 
/*     */               } else {
/*  83 */                 sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */               } 
/*     */               return;
/*     */           } 
/*  87 */           helpMsg(sender);
/*     */         }
/*  89 */         else if (args.length == 4) {
/*  90 */           switch (args[1]) {
/*     */             case "join":
/*  92 */               if (sender.hasPermission("gssentials.admin.gmessage.forcejoin") || sender.hasPermission("gssentials.admin.gmessage.all")) {
/*  93 */                 String name = args[3].toLowerCase();
/*  94 */                 ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(args[2]);
/*  95 */                 if (recipient != null) {
/*  96 */                   if (this.msgGroups.createDataNotExist(name)) {
/*  97 */                     if (this.msgGroups.getMembers(name).contains(recipient.getUniqueId().toString())) {
/*  98 */                       sender.sendMessage((BaseComponent)Dictionary.format(ChatColor.GREEN + recipient.getName() + Dictionary.MG_ERROR_ALREADY_IN_GROUP, new String[] { "NAME", name }));
/*     */                     } else {
/* 100 */                       this.msgGroups.addMember(name, recipient.getUniqueId().toString());
/* 101 */                       recipient.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_JOIN, new String[] { "NAME", name }));
/* 102 */                       sender.sendMessage((BaseComponent)Dictionary.format(ChatColor.GREEN + recipient.getName() + Dictionary.MG_JOIN, new String[] { "NAME", name }));
/*     */                     } 
/*     */                   } else {
/* 105 */                     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_EXIST, new String[] { "NAME", name }));
/*     */                   } 
/*     */                 } else {
/* 108 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */                 } 
/*     */               } else {
/* 111 */                 sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */               } 
/*     */               return;
/*     */             case "makeowner":
/* 115 */               if (sender.hasPermission("gssentials.admin.gmessage.makeowner") || sender.hasPermission("gssentials.admin.gmessage.all")) {
/* 116 */                 String name = args[2].toLowerCase();
/* 117 */                 ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(args[3]);
/* 118 */                 if (this.msgGroups.createDataNotExist(name)) {
/* 119 */                   if (recipient != null) {
/* 120 */                     this.msgGroups.addMember(name, recipient.getUniqueId().toString());
/* 121 */                     this.msgGroups.setOwner(name, recipient.getUniqueId().toString());
/* 122 */                     ProxyServer.getInstance().getPlayers().stream().filter(p -> (this.msgGroups.getMembers(name).contains(p.getUniqueId().toString()) || p == sender)).forEach(p -> p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MGA_OWNER, new String[] { "PLAYER", recipient.getName(), "NAME", name })));
/*     */                   } else {
/* 124 */                     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */                   } 
/*     */                 } else {
/* 127 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_EXIST, new String[] { "NAME", name }));
/*     */                 } 
/*     */               } else {
/* 130 */                 sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */               } 
/*     */               return;
/*     */             case "kick":
/* 134 */               if (sender.hasPermission("gssentials.admin.gmessage.kick") || sender.hasPermission("gssentials.admin.gmessage.all")) {
/* 135 */                 String name = args[3].toLowerCase();
/* 136 */                 String recipient = ProxyServer.getInstance().getPlayer(args[2]).getUniqueId().toString();
/* 137 */                 boolean online = false;
/*     */                 
/* 139 */                 if (recipient == null) {
/* 140 */                   recipient = (String)this.pD.getData("lastname", args[2], "uuid");
/*     */                 } else {
/* 142 */                   online = true;
/*     */                 } 
/*     */                 
/* 145 */                 if (recipient == null) {
/* 146 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */ 
/*     */                 
/*     */                 }
/* 150 */                 else if (this.msgGroups.getMembers(name).contains(recipient)) {
/* 151 */                   this.msgGroups.removeMember(name, recipient);
/* 152 */                   if (online) {
/* 153 */                     ProxyServer.getInstance().getPlayer(args[2]).sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_KICK_RECEIVE, new String[] { "NAME", name }));
/*     */                   }
/* 155 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_KICK_SEND, new String[] { "NAME", name, "PLAYER", args[2] }));
/*     */                 } else {
/* 157 */                   sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */                 } 
/*     */               } else {
/* 160 */                 sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */               } 
/*     */               return;
/*     */           } 
/* 164 */           helpMsg(sender);
/*     */         } 
/*     */       } else {
/*     */         
/* 168 */         helpMsg(sender);
/*     */       } 
/* 170 */     } else if (args.length > 0) {
/* 171 */       if (sender instanceof ProxiedPlayer) {
/* 172 */         ProxiedPlayer p = (ProxiedPlayer)sender;
/* 173 */         String uuid = p.getUniqueId().toString();
/* 174 */         if (p.hasPermission("gssentials.gmessage")) {
/* 175 */           if (args.length == 2 && (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("leave"))) {
/* 176 */             String name = args[1].toLowerCase();
/* 177 */             switch (args[0].toLowerCase()) {
/*     */               case "create":
/* 179 */                 if (p.hasPermission("gssentials.gmessage.create")) {
/* 180 */                   if (name.length() < 3) {
/* 181 */                     p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_INVALID_NAME, new String[] { "NAME", name }));
/*     */                     return;
/*     */                   } 
/* 184 */                   for (char c : args[1].toCharArray()) {
/* 185 */                     if (!Character.isLetter(c)) {
/* 186 */                       p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_INVALID_NAME, new String[] { "NAME", name }));
/*     */                       return;
/*     */                     } 
/*     */                   } 
/* 190 */                   if (this.msgGroups.createDataNotExist(name)) {
/* 191 */                     sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NAME_TAKEN, new String[] { "NAME", name }));
/*     */                     return;
/*     */                   } 
/* 194 */                   this.msgGroups.create(name);
/* 195 */                   this.msgGroups.setOwner(name, uuid);
/* 196 */                   p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_CREATE, new String[] { "NAME", name })); break;
/*     */                 } 
/* 198 */                 p.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */                 break;
/*     */               
/*     */               case "join":
/* 202 */                 if (this.msgGroups.createDataNotExist(name) && this.msgGroups.getMembers(name).contains(uuid)) {
/* 203 */                   p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_ALREADY_IN_GROUP, new String[] { "NAME", name })); break;
/* 204 */                 }  if (this.msgGroups.createDataNotExist(name) && this.msgGroups.getInvited(name).contains(uuid)) {
/* 205 */                   this.msgGroups.removeInvited(name, uuid);
/* 206 */                   this.msgGroups.addMember(name, uuid);
/* 207 */                   p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_JOIN, new String[] { "NAME", name })); break;
/*     */                 } 
/* 209 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_INVITED, new String[] { "NAME", name }));
/*     */                 break;
/*     */               
/*     */               case "leave":
/* 213 */                 if (this.msgGroups.createDataNotExist(name) && this.msgGroups.getMembers(name).contains(uuid)) {
/* 214 */                   if (this.msgGroups.getOwner(name).equals(uuid)) {
/* 215 */                     this.msgGroups.remove(name);
/* 216 */                     p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_DISBAND, new String[] { "NAME", name })); break;
/*     */                   } 
/* 218 */                   this.msgGroups.removeMember(name, uuid);
/* 219 */                   p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_LEAVE, new String[] { "NAME", name }));
/*     */                   break;
/*     */                 } 
/* 222 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_IN_GROUP, new String[] { "NAME", name }));
/*     */                 break;
/*     */             } 
/*     */           
/* 226 */           } else if (args.length == 3 && (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("kick"))) {
/* 227 */             String name = args[2].toLowerCase();
/* 228 */             if (this.msgGroups.createDataNotExist(name) && this.msgGroups.getOwner(name).equals(uuid)) {
/* 229 */               ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(args[1]);
/* 230 */               if (recipient != null) {
/* 231 */                 switch (args[0].toLowerCase()) {
/*     */                   case "invite":
/* 233 */                     this.msgGroups.addInvited(name, recipient.getUniqueId().toString());
/* 234 */                     recipient.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_INVITE_RECEIVE, new String[] { "NAME", name }));
/* 235 */                     p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_INVITE_SEND, new String[] { "NAME", name, "PLAYER", recipient.getName() }));
/*     */                     break;
/*     */                   case "kick":
/* 238 */                     if (this.msgGroups.getMembers(name).contains(recipient.getUniqueId().toString())) {
/* 239 */                       this.msgGroups.removeMember(name, recipient.getUniqueId().toString());
/* 240 */                       recipient.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_KICK_RECEIVE, new String[] { "NAME", name }));
/* 241 */                       p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_KICK_SEND, new String[] { "NAME", name, "PLAYER", recipient.getName() })); break;
/*     */                     } 
/* 243 */                     p.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */                     break;
/*     */                 } 
/*     */               
/*     */               } else {
/* 248 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_PLAYER_NOT_FOUND, new String[0]));
/*     */               } 
/*     */             } else {
/* 251 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_IN_GROUP, new String[] { "NAME", name }));
/*     */             } 
/* 253 */           } else if (args.length == 3 && args[0].equalsIgnoreCase("rename")) {
/* 254 */             String oldName = args[1].toLowerCase();
/* 255 */             String name = args[2].toLowerCase();
/* 256 */             if (this.msgGroups.createDataNotExist(oldName) && this.msgGroups.getOwner(oldName).equals(uuid)) {
/* 257 */               if (name.length() < 3) {
/* 258 */                 p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_INVALID_NAME, new String[] { "NAME", name }));
/*     */                 return;
/*     */               } 
/* 261 */               for (char c : args[1].toCharArray()) {
/* 262 */                 if (!Character.isLetter(c)) {
/* 263 */                   p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_INVALID_NAME, new String[] { "NAME", name }));
/*     */                   return;
/*     */                 } 
/*     */               } 
/* 267 */               if (this.msgGroups.createDataNotExist(name)) {
/* 268 */                 sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NAME_TAKEN, new String[] { "NAME", name }));
/*     */                 return;
/*     */               } 
/* 271 */               this.msgGroups.setName(oldName, name);
/* 272 */               for (String member : this.msgGroups.getMembers(name)) {
/* 273 */                 ProxiedPlayer memberP = ProxyServer.getInstance().getPlayer(UUID.fromString(member));
/* 274 */                 if (memberP != null) {
/* 275 */                   memberP.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_RENAME, new String[] { "OLDNAME", oldName, "NAME", name }));
/*     */                 }
/*     */               } 
/*     */             } else {
/* 279 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_IN_GROUP, new String[] { "NAME", name }));
/*     */             } 
/* 281 */           } else if (args.length > 1) {
/* 282 */             String name = args[0].toLowerCase();
/* 283 */             if (this.msgGroups.createDataNotExist(name) && this.msgGroups.getMembers(name).contains(uuid)) {
/* 284 */               Set<String> members = this.msgGroups.getMembers(name);
/* 285 */               String messageS = BungeeEssentials.getInstance().getMessenger().filter(p, Dictionary.combine(0, " ", args), Messenger.ChatType.PRIVATE);
/* 286 */               TextComponent msg = Dictionary.format(Dictionary.MG_FORMAT, new String[] { "NAME", Dictionary.capitalizeFirst(name), "SENDER", p.getName(), "MESSAGE", messageS });
/* 287 */               TextComponent spyMsg = Dictionary.format(Dictionary.SPY_MESSAGE, new String[] { "SENDER", p.getName(), "RECIPIENT", ChatColor.BLUE + Dictionary.capitalizeFirst(name), "MESSAGE", messageS });
/*     */               
/* 289 */               for (ProxiedPlayer recip : ProxyServer.getInstance().getPlayers()) {
/* 290 */                 if (members.contains(recip.getUniqueId().toString())) {
/* 291 */                   recip.sendMessage((BaseComponent)msg); continue;
/* 292 */                 }  if (this.pD.isSpy(recip.getUniqueId().toString())) {
/* 293 */                   recip.sendMessage((BaseComponent)spyMsg);
/*     */                 }
/*     */               } 
/*     */             } else {
/* 297 */               p.sendMessage((BaseComponent)Dictionary.format(Dictionary.MG_ERROR_NOT_IN_GROUP, new String[] { "NAME", name }));
/*     */             } 
/*     */           } else {
/* 300 */             helpMsg((CommandSender)p);
/*     */           } 
/*     */         } else {
/* 303 */           p.sendMessage(ProxyServer.getInstance().getTranslation("no_permission", new Object[0]));
/*     */         } 
/*     */       } else {
/* 306 */         sender.sendMessage((BaseComponent)Dictionary.format("&cYou can't do that as console!", new String[0]));
/*     */       } 
/*     */     } else {
/* 309 */       helpMsg(sender);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void helpMsg(CommandSender sender) {
/* 314 */     if (sender.hasPermission("gssentials.gmessage")) {
/* 315 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "\n  /" + 
/* 316 */               getName() + " <group> <message>\n  /" + 
/* 317 */               getName() + " <create|join|leave> <group>\n  /" + 
/* 318 */               getName() + " <invite|kick> <username> <group>\n  /" + 
/* 319 */               getName() + " rename <oldname> <group>" }));
/*     */     }
/* 321 */     if (sender.hasPermission("gssentials.admin.gmessage")) {
/* 322 */       sender.sendMessage((BaseComponent)Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, new String[] { "HELP", "\n  /" + 
/* 323 */               getName() + " admin listgroups\n  /" + 
/* 324 */               getName() + " admin <disband> <group>\n  /" + 
/* 325 */               getName() + " admin <makeowner> <group> <username>\n  /" + 
/* 326 */               getName() + " admin <join|kick> [username] <group>" }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
/* 333 */     return (args.length == 1) ? tabPlayers(sender, args[0]) : (Iterable<String>)ImmutableSet.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\Umbreon Majora\Desktop\BungeeEssentials.jar!\com\pantherman594\gssentials\command\general\MsgGroupCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */