/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Commands;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Dylan Malec
 */
@SuppressWarnings("unchecked")
public class MainCommands {
    
    public static void Create(CommandSender sender, Player player, String[] args) {
        switch (args[1]) {
            case "village":
                if (Config.isConfigurationSection("Village Ranks." + Config.get("Village Settings.Default Rank"))) {
                    if (player.hasPermission("empirecraft.createvillage")) {
                        if (!MainConversions.isPlayerInVillage(player.getUniqueId())) {
                            tempstring = "";
                            for (int i = 2; i < args.length; i++) {
                                tempstring += args[i] + " ";
                            }
                            tempstring = tempstring.trim();
                            if (serverdata.get("villages").containsKey(tempstring)) {
                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", already exists!");
                            } else if (Pattern.matches("[a-zA-Z][a-zA-Z ]+", tempstring)) {
                                if (tempstring.length() <= Config.getInt("Village Settings.Name Max Length")) {
                                    if (econ.has(player, Config.getInt("Village Settings.Creation Cost"))) {
                                        econ.withdrawPlayer(player, Config.getInt("Village Settings.Creation Cost"));
                                        serverdata.get("playerdata").put(player.getUniqueId().toString(), new HashMap<>());
                                        serverdata.get("playerdata").get(player.getUniqueId().toString()).put("village", tempstring);
                                        serverdata.get("villages").put(tempstring, new HashMap<>());
                                        serverdata.get("villages").get(tempstring).put("own", player.getUniqueId().toString());
                                        serverdata.get("villages").get(tempstring).put("vir", Config.get("Village Settings.Default Rank"));
                                        serverdata.get("villages").get(tempstring).put("plc", 0);
                                        serverdata.get("villages").get(tempstring).put("vau", Config.getInt("Village Settings.Initial Cash In Village Vault"));
                                        sender.sendMessage(ChatColor.BLUE + "You have successfully created the village " + ChatColor.AQUA + tempstring);
                                        Bukkit.getOnlinePlayers().stream().filter((p) -> (!p.equals(player))).forEach((p) -> {
                                            p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " has created the village " + ChatColor.LIGHT_PURPLE + tempstring);
                                        });
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "It costs $" + Config.getInt("Village Settings.Creation Cost") + " to create a village");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "The village name can only be a maximum of " + ChatColor.RED + Config.getInt("Village Settings.Name Max Length") + ChatColor.DARK_RED + " letters long");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "The village name can only contain letters");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You already belong to a village");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "The default rank " + ChatColor.RED + Config.get("Village Settings.Default Rank") + ChatColor.DARK_RED + " does not exsist, if you do NOT know what this rank is then look at the HELP GUIDE:" + ChatColor.RED + "http://dev.bukkit.org/bukkit-plugins/empirecraft/pages/help-guide-tutorial/");
                }
                break;
            case "empire":
                if (player.hasPermission("empirecraft.createempire")) {
                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                        String playervillage = serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString();
                        if (serverdata.get("villages").get(playervillage).get("own").equals(player.getUniqueId().toString())) {
                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                tempstring = "";
                                for (int i = 3; i < args.length; i++) {
                                    tempstring += args[i] + " ";
                                }
                                tempstring = tempstring.trim();
                                String[] names = tempstring.split(":");
                                if (!serverdata.get("villages").containsKey(names[0])) {
                                    if (Pattern.matches("[a-zA-Z][a-zA-Z ]+", names[0])) {
                                        if (serverdata.get("empires").containsKey(names[1])) {
                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + names[1] + ChatColor.DARK_RED + ", already exists!");
                                        } else if (Pattern.matches("[a-zA-Z][a-zA-Z ]+", names[1])) {
                                            if (names[0].length() <= Config.getInt("Village Settings.Name Max Length")) {
                                                if (names[1].length() <= Config.getInt("Empire Settings.Name Max Length")) {
                                                    if (econ.has(player, Config.getInt("Empire Settings.Creation Cost"))) {
                                                        econ.withdrawPlayer(player, Config.getInt("Empire Settings.Creation Cost"));
                                                        serverdata.get("empires").put(names[1], new HashMap<>());
                                                        //Main Village
                                                        serverdata.get("empires").get(names[1]).put("mav", playervillage);
                                                        serverdata.get("empires").get(names[1]).put("emr", Config.get("Empire Settings.Default Rank"));
                                                        //Number of villages in empire
                                                        serverdata.get("empires").get(names[1]).put("vils", new ArrayList<>());
                                                        ((ArrayList) serverdata.get("empires").get(names[1]).get("vils")).add(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village"));
                                                        ((ArrayList) serverdata.get("empires").get(names[1]).get("vils")).add(names[0]);
                                                        serverdata.get("empires").get(names[1]).put("vau", Config.getInt("Empire Settings.Initial Cash In Village Vault"));
                                                        serverdata.get("villages").get(playervillage).put("emp", names[1]);
                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).remove(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString());
                                                        if (((ArrayList) serverdata.get("villages").get(playervillage).get("man")).isEmpty()) {
                                                            serverdata.get("villages").get(playervillage).remove("man");
                                                        }
                                                        serverdata.get("playerdata").remove(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString());
                                                        serverdata.get("playerdata").put(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString(), new HashMap<>());
                                                        serverdata.get("playerdata").get(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString()).put("village", names[0]);
                                                        serverdata.get("villages").put(names[0], new HashMap<>());
                                                        serverdata.get("villages").get(names[0]).put("own", Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString());
                                                        serverdata.get("villages").get(names[0]).put("vir", Config.get("Village Settings.Default Rank"));
                                                        serverdata.get("villages").get(names[0]).put("plc", 0);
                                                        serverdata.get("villages").get(names[0]).put("emp", names[1]);
                                                        serverdata.get("villages").get(names[0]).put("vau", Config.getInt("Village Settings.Initial Cash In Village Vault"));
                                                        sender.sendMessage(ChatColor.BLUE + "You have successfully created the empire " + ChatColor.AQUA + names[1]);
                                                        Bukkit.getOnlinePlayers().stream().filter((p) -> (!p.equals(player))).forEach((p) -> {
                                                            p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " has created the empire " + ChatColor.LIGHT_PURPLE + names[1] + ChatColor.DARK_PURPLE + ", by promoting " + ChatColor.LIGHT_PURPLE + args[2] + ChatColor.DARK_PURPLE + " to the owner of the newly found village " + ChatColor.LIGHT_PURPLE + names[0]);
                                                        });
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "It costs $" + Config.getInt("Empire Settings.Creation Cost") + " to create an empire");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "The empire's name can only be a maximum of " + ChatColor.RED + Config.getInt("Empire Settings.Name Max Length") + ChatColor.DARK_RED + " letters long");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "The village's name can only be a maximum of " + ChatColor.RED + Config.getInt("Village Settings.Name Max Length") + ChatColor.DARK_RED + " letters long");
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "The empire's name can only contain letters");
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "The village's name can only contain letters");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + names[0] + ChatColor.DARK_RED + ", already exists!");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Upon creating an empire, you must select a previous village manager to become the owner of the new village created in the process of becoming an empire");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "Only the owner of the village can start up an empire");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You need to belong to a village inorder to create an empire");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                }
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "You can only create a village or empire");
                break;
        }
    }
    
    public static void Apply(String targetvillage, String playername) {
        if (serverdata.get("villages").get(targetvillage).get("app") == null) {
            serverdata.get("villages").get(targetvillage).put("app", new ArrayList<>());
        }
        ((ArrayList) serverdata.get("villages").get(targetvillage).get("app")).add(playername);
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.BLUE + "Your application to " + ChatColor.AQUA + targetvillage + ChatColor.BLUE + " has been sent successfully");
        if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(targetvillage).get("own").toString())) != null) {
            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(targetvillage).get("own").toString())).sendMessage(ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + " has sent an application to join the village, to accept use /vil manage accept " + Bukkit.getPlayer(UUID.fromString(playername)).getName());
        }
        if (serverdata.get("villages").get(targetvillage) != null) {
            if (serverdata.get("villages").get(targetvillage).get("man") != null) {
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(targetvillage).get("man"));
                temparraylist.stream().filter((p) -> (Bukkit.getPlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + " has sent an application to join the village, to accept use /vil manage accept " + Bukkit.getPlayer(UUID.fromString(playername)).getName());
                });
            }
        }
    }
}
