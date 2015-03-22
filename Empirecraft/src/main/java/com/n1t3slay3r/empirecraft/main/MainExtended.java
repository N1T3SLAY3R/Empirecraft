/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

import com.n1t3slay3r.empirecraft.Commands.DiplomacyCommands;
import com.n1t3slay3r.empirecraft.Commands.MainConversions;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Dylan Malec
 */
public class MainExtended {

    public static void Empires(CommandSender sender, Player player, String[] args) {
        String playerid = player.getUniqueId().toString();
        if (serverdata.get("playerdata").containsKey(playerid)) {
            if (serverdata.get("playerdata").get(playerid).containsKey("village")) {
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).containsKey("emp")) {
                    if (args.length > 0) {
                        String playerempire = serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("emp").toString();
                        switch (args[0]) {
                            case "leader":
                                if (args.length > 1) {
                                    switch (args[1]) {
                                        case "retire":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.retire")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "vils", tempstring)) {
                                                            serverdata.get("empires").get(playerempire).replace("mav", tempstring);
                                                            ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                temparraylist.clear();
                                                                return v;
                                                            }).map((v) -> {
                                                                if (serverdata.get("villages").get(v) != null) {
                                                                    if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                    }
                                                                    if (serverdata.get("villages").get(v).get("man") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                    }
                                                                }
                                                                return v;
                                                            }).map((v) -> {
                                                                if (!tempstring.equals(v) && !v.equals(serverdata.get("playerdata").get(playerid).get("village").toString())) {
                                                                    temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                }
                                                                return v;
                                                            }).forEach((_item) -> {
                                                                temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.DARK_PURPLE + ", has just left the throne and has passed it off to " + ChatColor.LIGHT_PURPLE + Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).getName() + ChatColor.DARK_PURPLE + ", owner of the village: " + ChatColor.LIGHT_PURPLE + tempstring);
                                                                });
                                                            });
                                                            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).isOnline()) {
                                                                Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.BLUE + "You have been successfully given the leadership of your empire from " + ChatColor.AQUA + player.getName() + ChatColor.BLUE + ", the previous leader of the empire");
                                                            }
                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully given " + ChatColor.AQUA + Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).getName() + ChatColor.BLUE + " leadership to the village, and you have been set to a village manager");
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "The village " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist within the empire");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader retire <village>" + ChatColor.GREEN + " Changes the leader of the empire to the owner of the selected village, and their village becomes the central village");
                                            }
                                            break;
                                        case "settax":
                                            if (args.length == 3) {
                                                if (player.hasPermission("empirecraft.empire.leader.settax")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (MainConversions.isInteger(args[2])) {
                                                            serverdata.get("empires").get(playerempire).put("tax", args[2]);
                                                            sender.sendMessage(ChatColor.BLUE + "Daily tax has been set to $" + ChatColor.AQUA + args[2] + ChatColor.BLUE + " for all the village's vaults to pay at tax time");
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You can only set number values as tax");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else if (args.length > 3) {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader settax <$$$$>");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader settax <$$$$>" + ChatColor.GREEN + " Sets the daily tax that all villages have to pay from their vault to yours");
                                            }
                                            break;
                                        case "description":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.description")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        serverdata.get("empires").get(playerempire).put("des", tempstring);
                                                        sender.sendMessage(ChatColor.BLUE + "Description set to: " + ChatColor.AQUA + tempstring);
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader description <text>" + ChatColor.GREEN + " Sets the empires description for all to see");
                                            }
                                            break;
                                        case "withdraw":
                                            if (args.length == 3) {
                                                if (player.hasPermission("empirecraft.empire.leader.withdraw")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (MainConversions.isInteger(args[2])) {
                                                            if (((Integer) serverdata.get("empires").get(playerempire).get("vau")) >= Integer.parseInt(args[2])) {
                                                                serverdata.get("empires").get(playerempire).put("vau", ((Integer) serverdata.get("empires").get(playerempire).get("vau")) - Integer.parseInt(args[2]));
                                                                econ.depositPlayer(player, Integer.parseInt(args[2]));
                                                                sender.sendMessage(ChatColor.BLUE + "You succesfully deposited $" + ChatColor.AQUA + args[2] + ChatColor.BLUE + " into the empires vault");
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot withdraw $" + ChatColor.RED + args[2] + ChatColor.DARK_RED + " when the empires vault only has $" + ChatColor.RED + serverdata.get("empires").get(playerempire).get("vau"));
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You can only withdraw $ as a whole number");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else if (args.length > 3) {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader withdraw <$$$$>");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader withdraw <$$$$>" + ChatColor.GREEN + " Withdraws cash from the empire's vault");
                                            }
                                            break;
                                        case "settp":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.settp")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                            if (MainConversions.isVillageAlliedOrYoursEmpireWise(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").toString(), playerempire)) {
                                                                if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                                                    tempstring = "";
                                                                    for (int i = 2; i < args.length; i++) {
                                                                        tempstring += args[i] + " ";
                                                                    }
                                                                    tempstring = tempstring.trim();
                                                                    if (((HashMap) serverdata.get("empires").get(playerempire).get("tp")).keySet().size() < Config.getInt("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Number of creatable teleport locations")) {
                                                                        ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).put(tempstring, new HashMap<>());
                                                                        ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("x", player.getLocation().getBlockX());
                                                                        ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("y", player.getLocation().getBlockY());
                                                                        ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("z", player.getLocation().getBlockZ());
                                                                        ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("w", player.getLocation().getWorld().getUID().toString());
                                                                        sender.sendMessage(ChatColor.BLUE + "You have successfully set the spawn point " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " to X " + ChatColor.AQUA + player.getLocation().getBlockX() + ChatColor.BLUE + ", Y " + ChatColor.AQUA + player.getLocation().getBlockY() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getBlockZ());
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You currently have the maximum teleportation spots set, which is " + ChatColor.RED + Config.getInt("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Number of creatable teleport locations"));
                                                                    }
                                                                } else if (Config.getInt("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Number of creatable teleport locations") != 0) {
                                                                    tempstring = "";
                                                                    for (int i = 2; i < args.length; i++) {
                                                                        tempstring += args[i] + " ";
                                                                    }
                                                                    tempstring = tempstring.trim();
                                                                    serverdata.get("empires").get(playerempire).put("tp", new HashMap<>());
                                                                    ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).put(tempstring, new HashMap<>());
                                                                    ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("x", player.getLocation().getBlockX());
                                                                    ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("y", player.getLocation().getBlockY());
                                                                    ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("z", player.getLocation().getBlockZ());
                                                                    ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).put("w", player.getLocation().getWorld().getUID().toString());
                                                                    sender.sendMessage(ChatColor.BLUE + "You have successfully set the teleporation point " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " to X " + ChatColor.AQUA + player.getLocation().getBlockX() + ChatColor.BLUE + ", Y " + ChatColor.AQUA + player.getLocation().getBlockY() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getBlockZ());
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot create any teleportation locations because your limit is currently 0");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You can only set teleportation points to positions within a village that is part of your empire, or an allied empires village");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You can only set teleportation points to positions within a village that is part of your empire, or an allied empires village");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader settp <name>" + ChatColor.GREEN + " Sets a teleportation point (must be positioned inside an empires claimed territory)");
                                            }
                                            break;
                                        case "removetp":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.removetp")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                                            tempstring = "";
                                                            for (int i = 2; i < args.length; i++) {
                                                                tempstring += args[i] + " ";
                                                            }
                                                            tempstring = tempstring.trim();
                                                            if (((HashMap) serverdata.get("empires").get(playerempire).get("tp")).containsKey(tempstring)) {
                                                                ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).remove(tempstring);
                                                                sender.sendMessage(ChatColor.BLUE + "You have successfully removed the teleportation point " + ChatColor.AQUA + tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "The teleportation point " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no teleportation points set");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader removetp <name>" + ChatColor.GREEN + " Removes the selected teleportation point");
                                            }
                                            break;
                                        case "invitevillage":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.invitevillage")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        if (serverdata.get("villages").containsKey(tempstring)) {
                                                            if (!serverdata.get("villages").get(tempstring).containsKey("emp")) {
                                                                if (serverdata.get("villages").get(tempstring).containsKey("emi")) {
                                                                    if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "emi", playerempire)) {
                                                                        if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "app", tempstring)) {
                                                                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() < Config.getInt("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Maximum villages allowed in empire")) {
                                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("app")).remove(tempstring);
                                                                                if (((ArrayList) serverdata.get("empires").get(playerempire).get("app")).isEmpty()) {
                                                                                    serverdata.get("empires").get(playerempire).remove("app");
                                                                                }
                                                                                serverdata.get("villages").get(tempstring).put("emp", playerempire);
                                                                                ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                                    temparraylist.clear();
                                                                                    return v;
                                                                                }).map((v) -> {
                                                                                    if (serverdata.get("villages").get(v) != null) {
                                                                                        if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                                        }
                                                                                        if (serverdata.get("villages").get(v).get("man") != null) {
                                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                                        }
                                                                                    }
                                                                                    return v;
                                                                                }).map((v) -> {
                                                                                    temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                                    return v;
                                                                                }).forEach((_item) -> {
                                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + ", has successfully joined the empire ");
                                                                                    });
                                                                                });
                                                                                temparraylist.clear();
                                                                                if (serverdata.get("villages").get(tempstring) != null) {
                                                                                    if (serverdata.get("villages").get(tempstring).get("mem") != null) {
                                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("mem"));
                                                                                    }
                                                                                    if (serverdata.get("villages").get(tempstring).get("man") != null) {
                                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("man"));
                                                                                    }
                                                                                }
                                                                                temparraylist.add(serverdata.get("villages").get(tempstring).get("own").toString());
                                                                                temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.BLUE + "Your village has been successfully joined into the empire " + ChatColor.AQUA + playerempire);
                                                                                });
                                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).add(tempstring);
                                                                                sender.sendMessage(ChatColor.AQUA + tempstring + ChatColor.BLUE + " has already applied so they have been successfully added into the empire");
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You currently have the maximum number of villages in your empire which is " + ChatColor.RED + Config.get("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Maximum villages allowed in empire"));
                                                                            }
                                                                        } else {
                                                                            serverdata.get("villages").get(tempstring).put("emi", new ArrayList<>());
                                                                            ((ArrayList) serverdata.get("villages").get(tempstring).get("emi")).add(playerempire);
                                                                            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).isOnline()) {
                                                                                Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.DARK_PURPLE + "Your village have been invited to join the empire " + ChatColor.LIGHT_PURPLE + playerempire);
                                                                            }
                                                                            sender.sendMessage(ChatColor.BLUE + "You have been successfully invited the village " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " to join the empire");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " has already been invited to join the empire");
                                                                    }
                                                                } else {
                                                                    serverdata.get("villages").get(tempstring).put("emi", new ArrayList<>());
                                                                    ((ArrayList) serverdata.get("villages").get(tempstring).get("emi")).add(playerempire);
                                                                    if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).isOnline()) {
                                                                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.DARK_PURPLE + "Your village have been invited to join the empire " + ChatColor.LIGHT_PURPLE + playerempire);
                                                                    }
                                                                    sender.sendMessage(ChatColor.BLUE + "You have been successfully invited the village " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " to join the empire");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + ", already belongs to an empire");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "The village " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader invitevillage <village>" + ChatColor.GREEN + " Invites the selected village to join your empire");
                                            }
                                            break;
                                        case "removevillage":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.removevillage")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() > 2) {
                                                            tempstring = "";
                                                            for (int i = 2; i < args.length; i++) {
                                                                tempstring += args[i] + " ";
                                                            }
                                                            tempstring = tempstring.trim();
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "vils", tempstring)) {
                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(tempstring);
                                                                serverdata.get("villages").get(tempstring).remove("emp");
                                                                ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                    temparraylist.clear();
                                                                    return v;
                                                                }).map((v) -> {
                                                                    if (serverdata.get("villages").get(v) != null) {
                                                                        if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                        }
                                                                        if (serverdata.get("villages").get(v).get("man") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                        }
                                                                    }
                                                                    return v;
                                                                }).map((v) -> {
                                                                    if (!serverdata.get("villages").get(v).get("own").toString().equals(playerid)) {
                                                                        temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                    }
                                                                    return v;
                                                                }).forEach((_item) -> {
                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + ", has successfully joined the empire ");
                                                                    });
                                                                });
                                                                temparraylist.clear();
                                                                if (serverdata.get("villages").get(tempstring) != null) {
                                                                    if (serverdata.get("villages").get(tempstring).get("mem") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("mem"));
                                                                    }
                                                                    if (serverdata.get("villages").get(tempstring).get("man") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("man"));
                                                                    }
                                                                }
                                                                temparraylist.add(serverdata.get("villages").get(tempstring).get("own").toString());
                                                                temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.DARK_RED + "You have been removed from the empire " + ChatColor.RED + playerempire + ChatColor.DARK_RED + " by " + ChatColor.RED + player.getName());
                                                                });
                                                                sender.sendMessage(ChatColor.AQUA + tempstring + ChatColor.BLUE + " has been successfully removed from the empire");
                                                            } else {
                                                                sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist in the empires database");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot remove a village from your empire when there is only 2 villages total in it. Otherwise you'd lose the empire altogether");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader removevillage <village>" + ChatColor.GREEN + " Removes the selected village from your empire");
                                            }
                                            break;
                                        case "applications":
                                            if (args.length == 3) {
                                                if (player.hasPermission("empirecraft.empire.leader.applications")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (serverdata.get("empires").get(playerempire).get("app") != null) {
                                                            tempstring = ChatColor.BLUE + "";
                                                            temparraylist.clear();
                                                            temparraylist.addAll((ArrayList) serverdata.get("empires").get(playerempire).get("app"));
                                                            ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("app")).stream().map((s) -> {
                                                                tempstring += Bukkit.getOfflinePlayer(UUID.fromString(s)).getName();
                                                                return s;
                                                            }).map((s) -> {
                                                                temparraylist.remove(s);
                                                                return s;
                                                            }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                            });
                                                            sender.sendMessage(tempstring);
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the empire");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else if (args.length > 3) {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader applications");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader applications" + ChatColor.GREEN + " Views a list of villages who have requested to join your empire");
                                            }
                                            break;
                                        case "accept":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.accept")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        if (serverdata.get("empires").get(playerempire).containsKey("app")) {
                                                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("app")).contains(tempstring)) {
                                                                if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() < Config.getInt("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Maximum villages allowed in empire")) {
                                                                    ((ArrayList) serverdata.get("empires").get(playerempire).get("app")).remove(tempstring);
                                                                    if (((ArrayList) serverdata.get("empires").get(playerempire).get("app")).isEmpty()) {
                                                                        serverdata.get("empires").get(playerempire).remove("app");
                                                                    }
                                                                    serverdata.get("villages").get(tempstring).put("emp", playerempire);
                                                                    ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                        temparraylist.clear();
                                                                        return v;
                                                                    }).map((v) -> {
                                                                        if (serverdata.get("villages").get(v) != null) {
                                                                            if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                            }
                                                                            if (serverdata.get("villages").get(v).get("man") != null) {
                                                                                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                            }
                                                                        }
                                                                        return v;
                                                                    }).map((v) -> {
                                                                        if (!serverdata.get("villages").get(v).get("own").toString().equals(playerid)) {
                                                                            temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                        }
                                                                        return v;
                                                                    }).forEach((_item) -> {
                                                                        temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                            Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + ", has successfully joined the empire ");
                                                                        });
                                                                    });
                                                                    temparraylist.clear();
                                                                    if (serverdata.get("villages").get(tempstring) != null) {
                                                                        if (serverdata.get("villages").get(tempstring).get("mem") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("mem"));
                                                                        }
                                                                        if (serverdata.get("villages").get(tempstring).get("man") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("man"));
                                                                        }
                                                                    }
                                                                    temparraylist.add(serverdata.get("villages").get(tempstring).get("own").toString());
                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.BLUE + "Your village have been successfully joined into the empire " + ChatColor.AQUA + playerempire);
                                                                    });
                                                                    ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).add(tempstring);
                                                                    sender.sendMessage(ChatColor.AQUA + tempstring + ChatColor.BLUE + " has been successfully added into the empire");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You currently have the maximum number of villages in your empire which is " + ChatColor.RED + Config.get("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Maximum villages allowed in empire"));
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "This village was not found in the empires application database");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the village");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader accept <village>" + ChatColor.GREEN + " Accepts a village's request to join the empire");
                                            }
                                            break;
                                        case "deny":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.deny")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        if (serverdata.get("empires").get(playerempire).containsKey("app")) {
                                                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("app")).contains(tempstring)) {
                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("app")).remove(tempstring);
                                                                if (((ArrayList) serverdata.get("empires").get(playerempire).get("app")).isEmpty()) {
                                                                    serverdata.get("empires").get(playerempire).remove("app");
                                                                }
                                                                sender.sendMessage(ChatColor.AQUA + tempstring + ChatColor.BLUE + " has been sucessfully removed from the applications list");
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "The village " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist in empires the applications list");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the village");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader deny <village>" + ChatColor.GREEN + " Denys a village's request to join the empire");
                                            }
                                            break;
                                        case "abandon":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.abandon")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        temparraylist.clear();
                                                        ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                            serverdata.get("villages").get(v).remove("emp");
                                                            return v;
                                                        }).map((v) -> {
                                                            if (serverdata.get("villages").get(v) != null) {
                                                                if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                }
                                                                if (serverdata.get("villages").get(v).get("man") != null) {
                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                }
                                                            }
                                                            return v;
                                                        }).map((v) -> {
                                                            if (!serverdata.get("villages").get(v).get("own").toString().equals(playerid)) {
                                                                temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                            }
                                                            return v;
                                                        }).forEach((_item) -> {
                                                            temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", has just abandoned the entire empire, so you are now *free*");
                                                                if (tempHashMap.get("chc").containsKey(p)) {
                                                                    if (tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                                                                        tempHashMap.get("chc").remove(p);
                                                                    }
                                                                }
                                                            });
                                                        });
                                                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                                            if (!temparraylist.contains(p.getUniqueId().toString())) {
                                                                p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", has just abandoned the empire " + ChatColor.LIGHT_PURPLE + playerempire);
                                                            }
                                                        }
                                                        if (serverdata.get("empires").get(playerempire).get("ene") != null) {
                                                            ((HashMap) serverdata.get("empires").get(playerempire).get("ene")).keySet().stream().forEach((o) -> {
                                                                ((HashMap) serverdata.get("empires").get(o.toString()).get("ene")).remove(playerempire);
                                                                if (((HashMap) serverdata.get("empires").get(o.toString()).get("ene")).isEmpty()) {
                                                                    serverdata.get("empires").get(o.toString()).remove("ene");
                                                                }
                                                                if (serverdata.get("empires").get(o.toString()).get("trr") != null) {
                                                                    if (((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).contains(playerempire)) {
                                                                        ((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).remove(playerempire);
                                                                        if (((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).isEmpty()) {
                                                                            serverdata.get("empires").get(o.toString()).remove("trr");
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        if (serverdata.get("empires").get(playerempire).get("all") != null) {
                                                            ((ArrayList) serverdata.get("empires").get(playerempire).get("all")).stream().forEach((o) -> {
                                                                ((ArrayList) serverdata.get("empires").get(o.toString()).get("all")).remove(playerempire);
                                                                if (((ArrayList) serverdata.get("empires").get(o.toString()).get("all")).isEmpty()) {
                                                                    serverdata.get("empires").get(o.toString()).remove("all");
                                                                }
                                                            });
                                                        }
                                                        serverdata.get("empires").remove(playerempire);
                                                        serverdata.get("empires").keySet().stream().filter((s) -> (serverdata.get("villages").get(s).get("alr") != null)).filter((s) -> (((ArrayList) serverdata.get("empires").get(s).get("alr")).contains(playerempire))).forEach((s) -> {
                                                            ((ArrayList) serverdata.get("empires").get(s).get("alr")).remove(playerempire);
                                                        });
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader abandon" + ChatColor.GREEN + "Removes the empire, and all villages become *free*");
                                            }
                                            break;
                                        case "viewdebt":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.viewdebt")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        if (serverdata.get("empires").get(playerempire).containsKey("debt")) {
                                                            tempstring = "";
                                                            for (String v : ((HashMap<String, Integer>) serverdata.get("empires").get(playerempire).get("debt")).keySet()) {
                                                                tempstring += ChatColor.BLUE + v;
                                                                if (Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(v).toString()) > 0) {
                                                                    tempstring += ChatColor.AQUA + " +$" + ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(v) + "\n";
                                                                } else {
                                                                    tempstring += ChatColor.RED + " -$" + ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(v) + "\n";
                                                                }
                                                            }
                                                            String trim = tempstring.trim();
                                                            sender.sendMessage(trim);
                                                        } else {
                                                            sender.sendMessage(ChatColor.BLUE + "Currently every village is neutral with their payments (no village is above or below the natural payment level)");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader viewdebt");
                                            }
                                            break;
                                        case "upgraderank":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.upgraderank")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        if (Config.contains("Empire Ranks." + tempstring)) {
                                                            if (Config.contains("Empire Ranks." + tempstring + ".Upgraded From")) {
                                                                if (Config.getString("Empire Ranks." + tempstring + ".Upgraded From").equals(serverdata.get("empires").get(playerempire).get("emr").toString())) {
                                                                    if (econ.has(player, Config.getInt("Empire Ranks." + tempstring + ".Upgrade Cost"))) {
                                                                        econ.withdrawPlayer(player, Config.getInt("Empire Ranks." + tempstring + ".Upgrade Cost"));
                                                                        serverdata.get("empires").get(playerempire).replace("emr", tempstring);
                                                                        ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                            temparraylist.clear();
                                                                            return v;
                                                                        }).map((v) -> {
                                                                            if (serverdata.get("villages").get(v) != null) {
                                                                                if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                                }
                                                                                if (serverdata.get("villages").get(v).get("man") != null) {
                                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                                }
                                                                            }
                                                                            return v;
                                                                        }).map((v) -> {
                                                                            if (!serverdata.get("villages").get(v).get("own").toString().equals(playerid)) {
                                                                                temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                            }
                                                                            return v;
                                                                        }).forEach((_item) -> {
                                                                            temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " has just upgraded the empires rank to " + tempstring);
                                                                            });
                                                                        });
                                                                        sender.sendMessage(ChatColor.BLUE + "You have succesfully upgraded the rempires rank to " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " for only $" + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Upgrade Cost"));
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "The upgrade costs $" + ChatColor.RED + Config.get("Empire Ranks." + tempstring + ".Upgrade Cost") + ChatColor.DARK_RED + ", and you only have $" + ChatColor.RED + econ.getBalance(player));
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "The village rank " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " is only upgraded from " + ChatColor.RED + Config.get("Empire Ranks." + tempstring + ".Upgraded From").equals(serverdata.get("empires").get(playerempire).get("emr").toString()));
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "The empire rank " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " is not upgradeable from any empire rank");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire rank " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist in the server database");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of the empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader upgraderank <rank>" + ChatColor.GREEN + " Changes the empires rank to the specified rank, potentially increasing revenue/decreasing upkeep, increasing the maximum number of villages, or increasing the maximum number of teleportation points");
                                            }
                                            break;
                                        case "ranklist":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.ranklist")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = ChatColor.BLUE + "Empire Ranks\n" + ChatColor.AQUA + "";
                                                        temparraylist.clear();
                                                        temparraylist.addAll(Config.getConfigurationSection("Empire Ranks").getKeys(false));
                                                        Config.getConfigurationSection("Empire Ranks").getKeys(false).stream().map((s) -> {
                                                            tempstring += s;
                                                            return s;
                                                        }).map((s) -> {
                                                            temparraylist.remove(s);
                                                            return s;
                                                        }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                            tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                        });
                                                        sender.sendMessage(tempstring);
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader ranklist");
                                            }
                                            break;
                                        case "rankinfo":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.leader.ranklist")) {
                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                        tempstring = "";
                                                        for (int i = 2; i < args.length; i++) {
                                                            tempstring += args[i] + " ";
                                                        }
                                                        tempstring = tempstring.trim();
                                                        String tempstring2 = "";
                                                        if (Config.contains("Empire Ranks." + tempstring)) {
                                                            tempstring2 += ChatColor.BLUE + "                                        " + ChatColor.AQUA + tempstring
                                                                    + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Upkeep")
                                                                    + ChatColor.BLUE + "\nRevenue: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".nRevenue")
                                                                    + ChatColor.BLUE + "\nMaximum villages allowed in empire: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Maximum villages allowed in empire")
                                                                    + ChatColor.BLUE + "\nNumber of creatable teleport locations: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Number of creatable teleport locations");
                                                            if (Config.contains("Empire Ranks." + tempstring + ".Upgraded From")) {
                                                                tempstring2 += ChatColor.BLUE + "\nUpgraded From: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Upgraded From")
                                                                        + ChatColor.BLUE + "\nUpgrade Cost: " + ChatColor.AQUA + Config.get("Empire Ranks." + tempstring + ".Upgrade Cost");
                                                            }
                                                            sender.sendMessage(tempstring2);
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "The village rank " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this empire!");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader rankinfo <rank>");
                                            }
                                            break;
                                        case "diplomacy":
                                            if (player.hasPermission("empirecraft.empire.leader.diplomacy")) {
                                                if (args.length > 2) {
                                                    switch (args[2]) {
                                                        case "war":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.war")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (serverdata.get("empires").get(playerempire).get("ene") != null) {
                                                                                    if (!((HashMap) serverdata.get("empires").get(playerempire).get("ene")).containsKey(tempstring)) {
                                                                                        DiplomacyCommands.War("empires", playerempire, tempstring, playerid);
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are already at war with " + ChatColor.RED + tempstring);
                                                                                    }
                                                                                } else {
                                                                                    DiplomacyCommands.War("empires", playerempire, tempstring, playerid);
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot declare war on yourself!");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader diplomacy war <empire>" + ChatColor.GREEN + " Declare war on the enemy empire");
                                                            }
                                                            break;
                                                        case "truce":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.truce")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (MainConversions.isPartInHashMap(serverdata.get("empires").get(tempstring), "ene", playerempire)) {
                                                                                    if (!MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "trr", tempstring)) {
                                                                                        if (!MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "trr", playerempire)) {
                                                                                            if (serverdata.get("empires").get(tempstring).get("trr") == null) {
                                                                                                serverdata.get("empires").get(tempstring).put("trr", new ArrayList<>());
                                                                                            }
                                                                                            ((ArrayList) serverdata.get("empires").get(tempstring).get("trr")).add(playerempire);
                                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully sent a truce request to " + ChatColor.AQUA + tempstring);
                                                                                            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).isOnline()) {
                                                                                                Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.BLUE + ", has requested a truce with you, type /emp leader acceptrequest " + ChatColor.AQUA + playerempire + ChatColor.BLUE + " to end this war");
                                                                                            }
                                                                                        } else {
                                                                                            sender.sendMessage(ChatColor.DARK_RED + "You have already requested a truce with " + ChatColor.RED + tempstring);
                                                                                        }
                                                                                    } else {
                                                                                        DiplomacyCommands.Truce("empires", playerempire, tempstring, playerid);
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot request to have a truce when your not at war with " + ChatColor.RED + tempstring);
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot request a truce with yourself!");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader diplomacy truce <empire>" + ChatColor.GREEN + " Send a request for the current war to end");
                                                            }
                                                            break;
                                                        case "alliance":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.alliance")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (!MainConversions.isPartInHashMap(serverdata.get("empires").get(tempstring), "ene", playerempire)) {
                                                                                    if (!MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "all", playerempire)) {
                                                                                        if (!MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "alr", tempstring)) {
                                                                                            if (!MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "alr", playerempire)) {
                                                                                                if (serverdata.get("empires").get(tempstring).get("alr") == null) {
                                                                                                    serverdata.get("empires").get(tempstring).put("alr", new ArrayList<>());
                                                                                                }
                                                                                                ((ArrayList) serverdata.get("empires").get(tempstring).get("alr")).add(playerempire);
                                                                                                sender.sendMessage(ChatColor.BLUE + "You have successfully sent an alliance request to " + ChatColor.AQUA + tempstring);
                                                                                                if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).isOnline()) {
                                                                                                    Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.BLUE + ", has requested an alliance with you, type /emp leader acceptrequest " + ChatColor.AQUA + playerempire + ChatColor.BLUE + " to form the alliance");
                                                                                                }
                                                                                            } else {
                                                                                                sender.sendMessage(ChatColor.DARK_RED + "You have already requested an alliance with " + ChatColor.RED + tempstring);
                                                                                            }
                                                                                        } else {
                                                                                            DiplomacyCommands.Alliance("empires", playerempire, tempstring, playerid);
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You already have an alliance with " + ChatColor.RED + tempstring);
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot request to have an alliance with your enemy");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot have an alliance with yourself!");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else if (args.length > 4) {
                                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader diplomacy alliance <empire>");
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader diplomacy alliance <empire>" + ChatColor.GREEN + " Send a request for an alliance");
                                                            }
                                                            break;
                                                        case "neutralize":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.neutralize")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "all", playerempire)) {
                                                                                    if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "all", playerempire)) {
                                                                                        DiplomacyCommands.Neutralize("empires", playerempire, tempstring, playerid);
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You can only neutralize allys");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You can only neutralize allys, use /emp leader diplomacy truce <empire> to end wars");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot have an alliance with yourself!");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp leader diplomacy neutralize <empire>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral");
                                                            }
                                                            break;
                                                        case "acceptrequest":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.acceptrequest")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "alr", tempstring)) {
                                                                                    DiplomacyCommands.Alliance("empires", playerempire, tempstring, playerid);
                                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "trr", tempstring)) {
                                                                                    DiplomacyCommands.Truce("empires", playerempire, tempstring, playerid);
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " currently has no requests of you");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot accept requests from yourself!");
                                                                            }
                                                                        } else {
                                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "alr", tempstring)) {
                                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("alr")).remove(tempstring);
                                                                                if (((ArrayList) serverdata.get("empires").get(playerempire).get("alr")).isEmpty()) {
                                                                                    serverdata.get("empires").get(playerempire).remove("alr");
                                                                                }
                                                                            }
                                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(playerempire), "trr", tempstring)) {
                                                                                ((ArrayList) serverdata.get("empires").get(playerempire).get("trr")).remove(tempstring);
                                                                                if (((ArrayList) serverdata.get("empires").get(playerempire).get("trr")).isEmpty()) {
                                                                                    serverdata.get("empires").get(playerempire).remove("trr");
                                                                                }
                                                                            }
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader diplomacy acceptrequest <empire>");
                                                            }
                                                            break;
                                                        case "denyrequest":
                                                            if (args.length > 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.denyrequest")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        tempstring = "";
                                                                        for (int i = 3; i < args.length; i++) {
                                                                            tempstring += args[i] + " ";
                                                                        }
                                                                        tempstring = tempstring.trim();
                                                                        if (serverdata.get("empires").containsKey(tempstring)) {
                                                                            if (!playerempire.equals(tempstring)) {
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "alr", tempstring)) {
                                                                                    ((ArrayList) serverdata.get("empires").get(playerempire).get("alr")).remove(tempstring);
                                                                                    if (((ArrayList) serverdata.get("empires").get(playerempire).get("alr")).isEmpty()) {
                                                                                        serverdata.get("empires").get(playerempire).remove("alr");
                                                                                    }
                                                                                    sender.sendMessage(ChatColor.DARK_PURPLE + "You have successfully denied " + ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + "'s alliance request");
                                                                                    if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).isOnline()) {
                                                                                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", leader of " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + ", has denied your alliance request");
                                                                                    }
                                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("empires").get(tempstring), "trr", tempstring)) {
                                                                                    ((ArrayList) serverdata.get("empires").get(playerempire).get("trr")).remove(tempstring);
                                                                                    if (((ArrayList) serverdata.get("empires").get(playerempire).get("trr")).isEmpty()) {
                                                                                        serverdata.get("empires").get(playerempire).remove("trr");
                                                                                    }
                                                                                    sender.sendMessage(ChatColor.DARK_PURPLE + "You have successfully denied " + ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + "'s truce request");
                                                                                    if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).isOnline()) {
                                                                                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(tempstring).get("mav").toString()).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", leader of " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + ", has denied your truce request");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " currently has no requests of you");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot accept requests from yourself!");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The empire name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader diplomacy denyrequest <empire>");
                                                            }
                                                            break;
                                                        case "requestlist":
                                                            if (args.length == 3) {
                                                                if (player.hasPermission("empirecraft.empire.leader.diplomacy.requestlist")) {
                                                                    if (serverdata.get("empires").get(playerempire).get("mav").equals(serverdata.get("playerdata").get(playerid).get("village")) && serverdata.get("villages").get(serverdata.get("playerdata").get(playerid).get("village").toString()).get("own").equals(playerid)) {
                                                                        if (serverdata.get("empires").get(playerempire) != null) {
                                                                            if (serverdata.get("empires").get(playerempire).get("alr") != null || serverdata.get("empires").get(playerempire).get("trr") != null) {
                                                                                DiplomacyCommands.RequestList("empires", playerempire, sender);
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "There are currently no diplomacy requests for the empire");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no diplomacy requests for the empire");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You are not the leader of this empire!");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader diplomacy requestlist");
                                                            }
                                                            break;
                                                        case "1":
                                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader diplomacy war <name>" + ChatColor.GREEN + " Declare war on the target empire\n"
                                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy truce <name>" + ChatColor.GREEN + " Send a request for the current war to end\n"
                                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy alliance <name>" + ChatColor.GREEN + " Send a request for an alliance\n"
                                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy neutralize <name>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral\n"
                                                                    + ChatColor.DARK_GREEN + "page <1/2>");
                                                            break;
                                                        case "2":
                                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader diplomacy acceptrequest <empire>" + ChatColor.GREEN + " Creates an alliance/truce with the target empire\n"
                                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy denyrequest <empire>" + ChatColor.GREEN + " Denys the allaince/truce request of the target empire\n"
                                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy requestlist" + ChatColor.GREEN + " Views a list of all alliance/truce requests\n"
                                                                    + ChatColor.DARK_GREEN + "page <2/2>");
                                                            break;
                                                        default:
                                                            sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader diplomacy");
                                                            break;
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader diplomacy war <name>" + ChatColor.GREEN + " Declare war on the target empire\n"
                                                            + ChatColor.DARK_GREEN + "/emp leader diplomacy truce <name>" + ChatColor.GREEN + " Send a request for the current war to end\n"
                                                            + ChatColor.DARK_GREEN + "/emp leader diplomacy alliance <name>" + ChatColor.GREEN + " Send a request for an alliance\n"
                                                            + ChatColor.DARK_GREEN + "/emp leader diplomacy neutralize <name>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral\n"
                                                            + ChatColor.DARK_GREEN + "page <1/2>");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                            }
                                            break;
                                        case "1":
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader retire <village>" + ChatColor.GREEN + " Changes the leader of the empire to the owner of the selected village, and their village becomes the central village\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader settax <$$$$>" + ChatColor.GREEN + " Sets the daily tax that all villages have to pay from their vault to yours\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader description <text>" + ChatColor.GREEN + " Sets the empires description for all to see\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader withdraw <$$$$>" + ChatColor.GREEN + " Withdraws cash from the empire's vault\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader settp <name>" + ChatColor.GREEN + " Sets a teleportation point (must be positioned inside an empires claimed territory)\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader removetp <name>" + ChatColor.GREEN + " Removes the selected teleportation point\n"
                                                    + ChatColor.DARK_GREEN + "page <1/2>");
                                            break;
                                        case "2":
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader invitevillage <village>" + ChatColor.GREEN + " Invites the selected village to join your empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader removevillage <village>" + ChatColor.GREEN + " Removes the selected village from your empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader applications" + ChatColor.GREEN + " Views a list of villages who have requested to join your empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader accept <village>" + ChatColor.GREEN + " Accepts a village's request to join the empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader deny <village>" + ChatColor.GREEN + " Denys a village's request to join the empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader abandon" + ChatColor.GREEN + " Removes the empire, and all villages become *free*\n"
                                                    + ChatColor.DARK_GREEN + "page <2/3>");
                                            break;
                                        case "3":
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader viewdebt" + ChatColor.GREEN + " Names every village's tax payments, whether they missed some and are negative or donated and have gone positive\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader upgraderank <rank>" + ChatColor.GREEN + " View a list of different commands regarding other villages\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader ranklist" + ChatColor.GREEN + " Lists the names of all possible ranks and what they are upgraded from\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader rankinfo" + ChatColor.GREEN + " Gives a finite description about the specified rank\n"
                                                    + ChatColor.DARK_GREEN + "/emp leader diplomacy" + ChatColor.GREEN + " View a list of different commands regarding other villages\n"
                                                    + ChatColor.DARK_GREEN + "page <3/3>");
                                            break;
                                        default:
                                            sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp leader <page>");
                                            break;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader retire <village>" + ChatColor.GREEN + " Changes the leader of the empire to the owner of the selected village, and their village becomes the central village\n"
                                            + ChatColor.DARK_GREEN + "/emp leader settax <$$$$>" + ChatColor.GREEN + " Sets the daily tax that all villages have to pay from their vault to yours\n"
                                            + ChatColor.DARK_GREEN + "/emp leader description <text>" + ChatColor.GREEN + " Sets the empires description for all to see\n"
                                            + ChatColor.DARK_GREEN + "/emp leader withdraw <$$$$>" + ChatColor.GREEN + " Withdraws cash from the empire's vault\n"
                                            + ChatColor.DARK_GREEN + "/emp leader settp <name>" + ChatColor.GREEN + " Sets a teleportation point (must be positioned inside an empires claimed territory)\n"
                                            + ChatColor.DARK_GREEN + "/emp leader removetp <name>" + ChatColor.GREEN + " Removes the selected teleportation point\n"
                                            + ChatColor.DARK_GREEN + "page <1/3>");
                                }
                                break;
                            case "follower":
                                if (args.length > 1) {
                                    switch (args[1]) {
                                        case "deposit":
                                            if (args.length == 3) {
                                                if (player.hasPermission("empirecraft.empire.follower.deposit")) {
                                                    if (econ.has(player, Integer.parseInt(args[2]))) {
                                                        serverdata.get("empires").get(playerempire).put("vau", ((Integer) serverdata.get("empires").get(playerempire).get("vau")) + Integer.parseInt(args[2]));
                                                        econ.withdrawPlayer(player, Integer.parseInt(args[2]));
                                                        if (serverdata.get("empires").get(playerempire).containsKey("debt")) {
                                                            if (((HashMap) serverdata.get("empires").get(playerempire).get("debt")).containsKey(serverdata.get("playerdata").get(playerid).get("village"))) {
                                                                ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).put(serverdata.get("playerdata").get(playerid).get("village"), ((Integer) ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(serverdata.get("playerdata").get(playerid).get("village"))) - Integer.parseInt(args[2]));
                                                                if (((Integer) ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(player)) == 0) {
                                                                    ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).remove(serverdata.get("playerdata").get(playerid).get("village"));
                                                                    if (((HashMap) serverdata.get("empires").get(playerempire).get("debt")).isEmpty()) {
                                                                        serverdata.get("empires").get(playerempire).remove("debt");
                                                                    }
                                                                }
                                                            } else {
                                                                ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).put(serverdata.get("playerdata").get(playerid).get("village"), Integer.parseInt(args[2]));
                                                            }
                                                        } else {
                                                            serverdata.get("empires").get(playerempire).put("debt", new HashMap<>());
                                                            ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).put(serverdata.get("playerdata").get(playerid).get("village"), Integer.parseInt(args[2]));
                                                        }
                                                        sender.sendMessage(ChatColor.BLUE + "You succesfully deposited $" + ChatColor.LIGHT_PURPLE + args[2] + ChatColor.BLUE + " into the empires vault");
                                                        ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                            temparraylist.clear();
                                                            return v;
                                                        }).map((v) -> {
                                                            if (serverdata.get("villages").get(v) != null) {
                                                                if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                }
                                                                if (serverdata.get("villages").get(v).get("man") != null) {
                                                                    temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                }
                                                            }
                                                            return v;
                                                        }).forEach((_item) -> {
                                                            temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                if (!p.equals(playerid)) {
                                                                    Bukkit.getPlayer(UUID.fromString(serverdata.get("empires").get(playerempire).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", Has just donated $" + ChatColor.LIGHT_PURPLE + args[2] + ChatColor.DARK_PURPLE + " to the empire!");
                                                                }
                                                            });
                                                        });
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot deposit $" + ChatColor.RED + args[2] + ChatColor.DARK_RED + " when you only have $" + ChatColor.RED + econ.getBalance(player));
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else if (args.length > 3) {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower deposit <$$$$>");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp follower deposit <$$$$>" + ChatColor.GREEN + " Deposits the selected amount of cash from your pocket into the empires vault");
                                            }
                                            break;
                                        case "tp":
                                            if (args.length > 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.tp")) {
                                                    tempstring = "";
                                                    for (int i = 3; i < args.length; i++) {
                                                        tempstring += args[i] + " ";
                                                    }
                                                    tempstring = tempstring.trim();
                                                    if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                                        if (((HashMap) serverdata.get("empires").get(playerempire).get("tp")).containsKey(tempstring)) {
                                                            if (Config.getLong("Empire Settings.Teleport Delay") != 0) {
                                                                tempHashMap.get("tpx").put(playerid, player.getLocation().getBlockX());
                                                                tempHashMap.get("tpy").put(playerid, player.getLocation().getBlockY());
                                                                tempHashMap.get("tpz").put(playerid, player.getLocation().getBlockZ());
                                                                sender.sendMessage(ChatColor.BLUE + "You will teleport to " + tempstring + " in " + Config.getInt("Empire Settings.Teleport Delay") + " seconds, do not move");
                                                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Empirecraft"), () -> {
                                                                    if (tempHashMap.get("tpx").containsKey(playerid) && player.isOnline()) {
                                                                        if (Bukkit.getPlayer(UUID.fromString(playerid)) != null && ((Integer) tempHashMap.get("tpx").get(playerid)) == player.getLocation().getBlockX() && ((Integer) tempHashMap.get("tpy").get(playerid)) == player.getLocation().getBlockY() && ((Integer) tempHashMap.get("tpz").get(playerid)) == player.getLocation().getBlockZ()) {
                                                                            Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("w").toString())), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("x"), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("y"), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("z"));
                                                                            player.teleport(loc);
                                                                            tempHashMap.get("tpx").remove(playerid);
                                                                            tempHashMap.get("tpy").remove(playerid);
                                                                            tempHashMap.get("tpz").remove(playerid);
                                                                        }
                                                                    }
                                                                }, Config.getLong("Village Settings.Home Teleport Delay") * 20);
                                                            } else {
                                                                Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("w").toString())), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("x"), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("y"), (Integer) ((HashMap) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get(tempstring)).get("z"));
                                                                player.teleport(loc);
                                                                sender.sendMessage(ChatColor.BLUE + "Teleporting now");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "The teleportation point " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "There are currently no teleportation points set");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else if (args.length > 3) {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower tp <name>");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/emp follower tp <name>" + ChatColor.GREEN + " Teleports to the specificied tp location");
                                            }
                                            break;
                                        case "tplist":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.tplist")) {
                                                    if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                                        tempstring = ChatColor.BLUE + "Teleportation Locatations\n" + ChatColor.AQUA + "";
                                                        temparraylist.clear();
                                                        temparraylist.addAll(((HashMap<String, String>) serverdata.get("empires").get(playerempire).get("tp")).keySet());
                                                        ((HashMap<String, String>) serverdata.get("empires").get(playerempire).get("tp")).keySet().stream().map((s) -> {
                                                            tempstring += s;
                                                            return s;
                                                        }).map((s) -> {
                                                            temparraylist.remove(s);
                                                            return s;
                                                        }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                            tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                        });
                                                        tempstring += ChatColor.BLUE + "\nTotal Teleportation Locations: " + ChatColor.AQUA + ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).keySet().size();
                                                        sender.sendMessage(tempstring);
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "There are currently no teleportation points within the empire");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower tplist");
                                            }
                                            break;
                                        case "leave":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.leave")) {
                                                    String playervillage = serverdata.get("playerdata").get(playerid).get("village").toString();
                                                    if (serverdata.get("empires").get(playerempire).get("mav") != playervillage) {
                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playerid)) {
                                                            ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(playervillage);
                                                            player.sendMessage(ChatColor.BLUE + "You have successfully left the empire " + ChatColor.AQUA + playerempire);
                                                            if (tempHashMap.get("chc").containsKey(player.getUniqueId())) {
                                                                if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("eal") || tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("ealy")) {
                                                                    tempHashMap.get("chc").remove(player.getUniqueId());
                                                                }
                                                            }
                                                            serverdata.get("villages").get(playervillage).remove("emp");
                                                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() > 1) {
                                                                ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                    temparraylist.clear();
                                                                    return v;
                                                                }).map((v) -> {
                                                                    if (serverdata.get("villages").get(v) != null) {
                                                                        if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                        }
                                                                        if (serverdata.get("villages").get(v).get("man") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                        }
                                                                    }
                                                                    return v;
                                                                }).map((v) -> {
                                                                    temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                    return v;
                                                                }).forEach((_item) -> {
                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playervillage + ChatColor.DARK_PURPLE + " has left the empire");
                                                                    });
                                                                });
                                                                temparraylist.clear();
                                                                if (serverdata.get("villages").get(playervillage) != null) {
                                                                    if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("mem"));
                                                                    }
                                                                    if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("man"));
                                                                    }
                                                                }
                                                                temparraylist.stream().filter((p) -> (Bukkit.getOfflinePlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                                                                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " has issued to command for the village to leave the empire " + ChatColor.LIGHT_PURPLE + playerempire);
                                                                    if (tempHashMap.get("chc").containsKey(p)) {
                                                                        if (tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                                                                            tempHashMap.get("chc").remove(p);
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                temparraylist.clear();
                                                                ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((v) -> {
                                                                    serverdata.get("villages").get(v).remove("emp");
                                                                    return v;
                                                                }).map((v) -> {
                                                                    if (serverdata.get("villages").get(v) != null) {
                                                                        if (serverdata.get("villages").get(v).get("mem") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                                                                        }
                                                                        if (serverdata.get("villages").get(v).get("man") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                                                                        }
                                                                    }
                                                                    return v;
                                                                }).map((v) -> {
                                                                    if (!serverdata.get("villages").get(v).get("own").toString().equals(playerid)) {
                                                                        temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                                                                    }
                                                                    return v;
                                                                }).forEach((_item) -> {
                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.DARK_PURPLE + "Your empire " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + " has been removed since " + ChatColor.LIGHT_PURPLE + playervillage + ChatColor.LIGHT_PURPLE + " left and your village is the last one left in it, so it has been eleminated/removed");
                                                                        if (tempHashMap.get("chc").containsKey(p)) {
                                                                            if (tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                                                                                tempHashMap.get("chc").remove(p);
                                                                            }
                                                                        }
                                                                    });
                                                                });
                                                                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                                                    if (!temparraylist.contains(p.getUniqueId().toString())) {
                                                                        p.sendMessage(ChatColor.DARK_PURPLE + "The village " + ChatColor.LIGHT_PURPLE + playervillage + ChatColor.DARK_PURPLE + ", has just left the empire " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.LIGHT_PURPLE + ", and since there is only one village left in it, it has been eleminated/removed");
                                                                    }
                                                                }
                                                                if (serverdata.get("empires").get(playerempire).get("ene") != null) {
                                                                    ((HashMap) serverdata.get("empires").get(playerempire).get("ene")).keySet().stream().forEach((o) -> {
                                                                        ((HashMap) serverdata.get("empires").get(o.toString()).get("ene")).remove(playerempire);
                                                                        if (((HashMap) serverdata.get("empires").get(o.toString()).get("ene")).isEmpty()) {
                                                                            serverdata.get("empires").get(o.toString()).remove("ene");
                                                                        }
                                                                        if (serverdata.get("empires").get(o.toString()).get("trr") != null) {
                                                                            if (((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).contains(playerempire)) {
                                                                                ((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).remove(playerempire);
                                                                                if (((ArrayList) serverdata.get("empires").get(o.toString()).get("trr")).isEmpty()) {
                                                                                    serverdata.get("empires").get(o.toString()).remove("trr");
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                                if (serverdata.get("empires").get(playerempire).get("all") != null) {
                                                                    ((ArrayList) serverdata.get("empires").get(playerempire).get("all")).stream().forEach((o) -> {
                                                                        ((ArrayList) serverdata.get("empires").get(o.toString()).get("all")).remove(playerempire);
                                                                        if (((ArrayList) serverdata.get("empires").get(o.toString()).get("all")).isEmpty()) {
                                                                            serverdata.get("empires").get(o.toString()).remove("all");
                                                                        }
                                                                    });
                                                                }
                                                                serverdata.get("empires").remove(playerempire);
                                                                serverdata.get("empires").keySet().stream().filter((s) -> (serverdata.get("villages").get(s).get("alr") != null)).filter((s) -> (((ArrayList) serverdata.get("empires").get(s).get("alr")).contains(playerempire))).forEach((s) -> {
                                                                    ((ArrayList) serverdata.get("empires").get(s).get("alr")).remove(playerempire);
                                                                });
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "Only the owner of your village can issue this command to leave the empire");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot leave the empire as its leader, you must either abandon it or retire another person to take your place");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower leave");
                                            }
                                            break;
                                        case "info":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.info")) {
                                                    tempstring = ChatColor.BLUE + "                                        " + playerempire + "\nLeader: " + ChatColor.AQUA + Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(serverdata.get("empires").get(playerempire).get("mav").toString()).get("own").toString())).getName()
                                                            + ChatColor.BLUE + "\nLeaders/Main Village: " + ChatColor.AQUA + serverdata.get("empires").get(playerempire).get("mav")
                                                            + ChatColor.BLUE + "\nVillages: " + ChatColor.AQUA + ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() + ChatColor.BLUE + "/" + ChatColor.AQUA + Config.getString("Empire Ranks." + serverdata.get("empires").get(playerempire).get("emr") + ".Maximum villages allowed in empire")
                                                            + ChatColor.BLUE + "\nMoney In Vault: " + ChatColor.AQUA + serverdata.get("empires").get(playerempire).get("vau");
                                                    if (serverdata.get("empires").get(playerempire).containsKey("des")) {
                                                        tempstring += ChatColor.BLUE + "\nDescription: " + ChatColor.AQUA + serverdata.get("empires").get(playerempire).get("des");
                                                    }
                                                    sender.sendMessage(tempstring);
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower info");
                                            }
                                            break;
                                        case "relations":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.relations")) {
                                                    sender.sendMessage(ChatColor.BLUE + "Allies");
                                                    if (serverdata.get("empires").get(playerempire).get("all") != null) {
                                                        tempstring = ChatColor.AQUA + "";
                                                        temparraylist.clear();
                                                        temparraylist.addAll((ArrayList) serverdata.get("empires").get(playerempire).get("all"));
                                                        ((ArrayList) serverdata.get("empires").get(playerempire).get("all")).stream().map((s) -> {
                                                            tempstring += s;
                                                            return s;
                                                        }).map((s) -> {
                                                            temparraylist.remove((String) s);
                                                            return s;
                                                        }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                            tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                        });
                                                        tempstring += ChatColor.BLUE + "\nTotal Allies: " + ChatColor.AQUA + ((ArrayList) serverdata.get("empires").get(playerempire).get("all")).size();
                                                        sender.sendMessage(tempstring);
                                                    } else {
                                                        sender.sendMessage(ChatColor.AQUA + "None");
                                                    }
                                                    sender.sendMessage(ChatColor.BLUE + "Enemies");
                                                    if (serverdata.get("empires").get(playerempire).get("ene") != null) {
                                                        tempstring = ChatColor.AQUA + "";
                                                        temparraylist.clear();
                                                        temparraylist.addAll(((HashMap) serverdata.get("empires").get(playerempire).get("ene")).keySet());
                                                        ((HashMap) serverdata.get("empires").get(playerempire).get("ene")).keySet().stream().map((s) -> {
                                                            tempstring += s;
                                                            return s;
                                                        }).map((s) -> {
                                                            temparraylist.remove((String) s);
                                                            return s;
                                                        }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                            tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                        });
                                                        tempstring += ChatColor.BLUE + "\nTotal Enemies: " + ChatColor.AQUA + ((HashMap) serverdata.get("empires").get(playerempire).get("ene")).keySet().size();
                                                        sender.sendMessage(tempstring);
                                                    } else {
                                                        sender.sendMessage(ChatColor.AQUA + "None");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower relations");
                                            }
                                            break;
                                        case "vessels":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.vessels")) {
                                                    tempstring = ChatColor.BLUE + "Villages within" + playerempire + ChatColor.AQUA + "\n";
                                                    temparraylist.clear();
                                                    temparraylist.addAll((ArrayList) serverdata.get("empires").get(playerempire).get("vils"));
                                                    ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils")).stream().map((s) -> {
                                                        tempstring += s;
                                                        return s;
                                                    }).map((s) -> {
                                                        temparraylist.remove(s);
                                                        return s;
                                                    }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                        tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                    });
                                                    tempstring += ChatColor.BLUE + "\nTotal Villages: " + ChatColor.AQUA + ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size();
                                                    sender.sendMessage(tempstring);
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower vessels");
                                            }
                                            break;
                                        case "tax":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.empire.follower.tax")) {
                                                    tempstring = ChatColor.BLUE + "Server time: " + ChatColor.AQUA;
                                                    Calendar cal = Calendar.getInstance();
                                                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                                                    int min = cal.get(Calendar.MINUTE);
                                                    int sec = cal.get(Calendar.SECOND);
                                                    tempstring += String.format("%02d:%02d:%02d", hour, min, sec);
                                                    tempstring += ChatColor.BLUE + "\nTime left until next village tax: " + ChatColor.AQUA;
                                                    Long tax = Config.getLong("Empire Settings.Tax Delay");
                                                    Long dif = Math.abs(tax - hour * 3600 + min * 60 + sec);
                                                    hour = 0;
                                                    min = 0;
                                                    sec = 0;
                                                    while (dif >= 0) {
                                                        dif -= 3600;
                                                        hour += 1;
                                                    }
                                                    dif += 3600;
                                                    while (dif >= 0) {
                                                        dif -= 60;
                                                        min += 1;
                                                    }
                                                    dif += 60;
                                                    while (dif > 0) {
                                                        dif -= 1;
                                                        sec += 1;
                                                    }
                                                    tempstring += String.format("%02d:%02d:%02d", hour, min, sec);
                                                    tempstring += ChatColor.BLUE + "\nTax Amount: $" + ChatColor.AQUA;
                                                    if (serverdata.get("empires").get(playerempire).containsKey("tax")) {
                                                        tempstring += serverdata.get("empires").get(playerempire).get("tax");
                                                    } else {
                                                        tempstring += "0";
                                                    }
                                                    if (serverdata.get("empires").get(playerempire).containsKey("debt")) {
                                                        if (((HashMap) serverdata.get("empires").get(playerempire).get("debt")).containsKey(serverdata.get("playerdata").get(playerid).get("village"))) {
                                                            if (Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(serverdata.get("playerdata").get(playerid).get("village")).toString()) > 0) {
                                                                tempstring += ChatColor.BLUE + "\nYour Donations: " + ChatColor.AQUA + "+$ " + ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(serverdata.get("playerdata").get(playerid).get("village"));
                                                            } else {
                                                                tempstring += ChatColor.BLUE + "\nYour Debt: " + ChatColor.AQUA + "-$ " + ((HashMap) serverdata.get("empires").get(playerempire).get("debt")).get(serverdata.get("playerdata").get(playerid).get("village"));
                                                            }
                                                        } else {
                                                            tempstring += ChatColor.BLUE + "\nYou currently have no debt";
                                                        }
                                                    } else {
                                                        tempstring += ChatColor.BLUE + "\nYou currently have no debt";
                                                    }
                                                    sender.sendMessage(tempstring);
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower tax");
                                            }
                                            break;
                                        case "1":
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp follower deposit <$$$$>" + ChatColor.GREEN + " Deposits the selected amount of cash from your pocket into the empires vault\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower tp" + ChatColor.GREEN + " Teleports to the specificied tp location\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower tplist" + ChatColor.GREEN + " Lists the names of all the possible teleporations\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower leave" + ChatColor.GREEN + " Your village leaves the empire and becomes independent (only the owner of your village can do this)\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower info" + ChatColor.GREEN + " Displays information about your empire\n"
                                                    + ChatColor.DARK_GREEN + "page <1/2>");
                                            break;
                                        case "2":
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp follower relations" + ChatColor.GREEN + " Displays your empires allies and enmies (villages involved within them are included)\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower vessels" + ChatColor.GREEN + " Displays all the villages within your empire\n"
                                                    + ChatColor.DARK_GREEN + "/emp follower tax" + ChatColor.GREEN + " Tells you the time till the next village tax and what it is\n"
                                                    + ChatColor.DARK_GREEN + "page <2/2>");
                                            break;
                                        default:
                                            sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp follower <page>");
                                            break;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp follower deposit <$$$$>" + ChatColor.GREEN + " Deposits the selected amount of cash from your pocket into the empires vault\n"
                                            + ChatColor.DARK_GREEN + "/emp follower tp" + ChatColor.GREEN + " Teleports to the specificied tp location\n"
                                            + ChatColor.DARK_GREEN + "/emp follower tplist" + ChatColor.GREEN + " Lists the names of all the possible teleporations\n"
                                            + ChatColor.DARK_GREEN + "/emp follower leave" + ChatColor.GREEN + " Your village leaves the empire and becomes independent (only the owner of your village can do this)\n"
                                            + ChatColor.DARK_GREEN + "/emp follower info" + ChatColor.GREEN + " Displays information about your empire\n"
                                            + ChatColor.DARK_GREEN + "page <1/2>");
                                }
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /emp");
                                break;
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/emp leader" + ChatColor.GREEN + " Contains commands for the leader of the empire to use\n"
                                + ChatColor.DARK_GREEN + "/emp follower" + ChatColor.GREEN + " Contains commands that all members of the empire can use\n"
                                + ChatColor.DARK_GREEN + "page <1/1>");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You must belong to an empire to use these commands");
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You must belong to an empire to use these commands");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You must belong to an empire to use these commands");
        }
    }
}
