/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.MemberCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author dylan
 */
public class SetPerm {
    public static void SetPerm(String playername, CommandSender sender, String[] args) {
        switch (args[2]) {
            case "modify":
                switch (args[3]) {
                    case "member":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("mom", "on");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can now place and destroy blocks in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("mom", "off");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can no longer place and destroy blocks in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "outsider":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("moo", "on");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can now place and destroy blocks in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("moo", "off");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can no longer place and destroy blocks in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "ally":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("moa", "on");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can now place and destroy blocks in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("moa", "off");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can no longer place and destroy blocks in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    default:
                        if (serverdata.get("playerdata").get(playername).containsKey("modify")) {
                            switch (args[4]) {
                                case "on":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("modify")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "on");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now place and destroy blocks in your plot(s)");
                                    break;
                                case "off":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("modify")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "off");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer place and destroy blocks in your plot(s)");
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                    break;
                            }
                        } else {
                            if (Bukkit.getOfflinePlayer(args[3]).isOnline()) {
                                switch (args[4]) {
                                    case "on":
                                        serverdata.get("playerdata").get(playername).put("modify", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "on");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now place and destroy blocks in your plot(s)");
                                        break;
                                    case "off":
                                        serverdata.get("playerdata").get(playername).put("modify", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "off");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer place and destroy blocks in your plot(s)");
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Either you didnt type a vailid relation or the player " + ChatColor.RED + args[3] + ChatColor.DARK_RED + "is currently not online");
                            }
                        }
                        break;
                }
                break;
            case "doors":
                switch (args[3]) {
                    case "member":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("dom", "on");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can now open and close doors in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("dom", "off");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can no longer open and close doors in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "outsider":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("doo", "on");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can now open and close doors in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("doo", "off");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can no longer open and close doors in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "ally":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("doa", "on");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can now open and close doors in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("doa", "off");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can no longer open and close doors in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    default:
                        if (serverdata.get("playerdata").get(playername).containsKey("doors")) {
                            switch (args[4]) {
                                case "on":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("doors")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "on");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now open and close doors in your plot(s)");
                                    break;
                                case "off":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("doors")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "off");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer open and close doors in your plot(s)");
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                    break;
                            }
                        } else {
                            if (Bukkit.getOfflinePlayer(args[3]).isOnline()) {
                                switch (args[4]) {
                                    case "on":
                                        serverdata.get("playerdata").get(playername).put("doors", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "on");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now open and close doors in your plot(s)");
                                        break;
                                    case "off":
                                        serverdata.get("playerdata").get(playername).put("doors", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "off");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer open and close doors in your plot(s)");
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Either you didnt type a vailid relation or the player " + ChatColor.RED + args[3] + ChatColor.DARK_RED + "is currently not online");
                            }
                        }
                        break;
                }
                break;
            case "buttons":
                switch (args[3]) {
                    case "member":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("bum", "on");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can now push buttons in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("bum", "off");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can no longer push buttons in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "outsider":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("buo", "on");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can now push buttons in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("buo", "off");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can no longer push buttons in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "ally":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("bua", "on");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can now push buttons in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("bua", "off");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can no longer push buttons in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    default:
                        if (serverdata.get("playerdata").get(playername).containsKey("buttons")) {
                            switch (args[4]) {
                                case "on":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("buttons")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "on");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now push buttons in your plot(s)");
                                    break;
                                case "off":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("buttons")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "off");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer push buttons in your plot(s)");
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                    break;
                            }
                        } else {
                            if (Bukkit.getOfflinePlayer(args[3]).isOnline()) {
                                switch (args[4]) {
                                    case "on":
                                        serverdata.get("playerdata").get(playername).put("buttons", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "on");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now push buttons in your plot(s)");
                                        break;
                                    case "off":
                                        serverdata.get("playerdata").get(playername).put("buttons", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "off");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer push buttons in your plot(s)");
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Either you didnt type a vailid relation or the player " + ChatColor.RED + args[3] + ChatColor.DARK_RED + "is currently not online");
                            }
                        }
                        break;
                }
                break;
            case "levers":
                switch (args[3]) {
                    case "member":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("lem", "on");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can now use levers in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("lem", "off");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can no longer use levers in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "outsider":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("leo", "on");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can now use levers in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("leo", "off");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can no longer use levers in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "ally":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("lea", "on");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can now use levers in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("lea", "off");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can no longer use levers in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    default:
                        if (serverdata.get("playerdata").get(playername).containsKey("levers")) {
                            switch (args[4]) {
                                case "on":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("levers")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "on");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now use levers in your plot(s)");
                                    break;
                                case "off":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("levers")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "off");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " no longer use levers in your plot(s)");
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                    break;
                            }
                        } else {
                            if (Bukkit.getOfflinePlayer(args[3]).isOnline()) {
                                switch (args[4]) {
                                    case "on":
                                        serverdata.get("playerdata").get(playername).put("levers", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "on");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now use levers in your plot(s)");
                                        break;
                                    case "off":
                                        serverdata.get("playerdata").get(playername).put("levers", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "off");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer use levers in your plot(s)");
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Either you didnt type a vailid relation or the player " + ChatColor.RED + args[3] + ChatColor.DARK_RED + "is currently not online");
                            }
                        }
                        break;
                }
                break;
            case "containers":
                switch (args[3]) {
                    case "member":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("com", "on");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can now open chests in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("com", "off");
                                sender.sendMessage(ChatColor.BLUE + "Other members of your village can no longer open chests in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "outsider":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("coo", "on");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can now open chests in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("coo", "off");
                                sender.sendMessage(ChatColor.BLUE + "Players who do not live in your village can no longer open chests in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    case "ally":
                        switch (args[4]) {
                            case "on":
                                serverdata.get("playerdata").get(playername).put("coa", "on");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can now open chests in your plot(s)");
                                break;
                            case "off":
                                serverdata.get("playerdata").get(playername).put("coa", "off");
                                sender.sendMessage(ChatColor.BLUE + "Ally village members can no longer open chests in your plot(s)");
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                break;
                        }
                        break;
                    default:
                        if (serverdata.get("playerdata").get(playername).containsKey("containers")) {
                            switch (args[4]) {
                                case "on":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("containers")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "on");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now open chests in your plot(s)");
                                    break;
                                case "off":
                                    ((HashMap) serverdata.get("playerdata").get(playername).get("containers")).put(Bukkit.getOfflinePlayer(args[3]).getUniqueId().toString(), "off");
                                    sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " no can no longer open chests in your plot(s)");
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                    break;
                            }
                        } else {
                            if (Bukkit.getOfflinePlayer(args[3]).isOnline()) {
                                switch (args[4]) {
                                    case "on":
                                        serverdata.get("playerdata").get(playername).put("containers", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "on");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can now open chests in your plot(s)");
                                        break;
                                    case "off":
                                        serverdata.get("playerdata").get(playername).put("containers", new HashMap<>());
                                        serverdata.get("playerdata").get(playername).put(Bukkit.getPlayer(args[3]).getUniqueId().toString(), "off");
                                        sender.sendMessage(ChatColor.AQUA + args[3] + ChatColor.BLUE + " can no longer open chests in your plot(s)");
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Either you didnt type a vailid relation or the player " + ChatColor.RED + args[3] + ChatColor.DARK_RED + "is currently not online");
                            }
                        }
                        break;
                }
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "The perm " + ChatColor.RED + args[2] + ChatColor.DARK_RED + " is not valid, the perms are modify, doors, buttons, levers, and containers");
                break;
        }
    }
}
