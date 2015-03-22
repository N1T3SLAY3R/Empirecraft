/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Commands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import java.util.ArrayList;
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
@SuppressWarnings("unchecked")
public class OwnerCommands {

    public static void Abandon(String village, String playername, Player player) {
        serverdata.get("worldmap").keySet().stream().forEach((w) -> {
            for (Object x : ((HashMap) serverdata.get("worldmap").get(w)).keySet().toArray()) {
                for (Object z : ((HashMap<String, HashMap>) serverdata.get("worldmap").get(w).get(x)).keySet().toArray()) {
                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").equals(village)) {
                        ((HashMap) serverdata.get("worldmap").get(w).get(x)).remove(z);
                        if (((HashMap) serverdata.get("worldmap").get(w).get(x)).isEmpty()) {
                            serverdata.get("worldmap").get(w).remove(x);
                        }
                        if (serverdata.get("villages").get(village).containsKey("emp")) {
                            String playerempire = serverdata.get("villages").get(village).get("emp").toString();
                            ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(village);
                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() > 1) {
                                if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                    for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).keySet())) {
                                        Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("z").toString()));
                                        if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                            ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).remove(t);
                                        }
                                    }
                                }
                            }
                            if (serverdata.get("empires").get(playerempire).containsKey("all")) {
                                for (String e : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("all"))) {
                                    if (serverdata.get("empires").get(e).containsKey("tp")) {
                                        for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(e).get("tp")).keySet())) {
                                            Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(e).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("z").toString()));
                                            if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                                ((HashMap) serverdata.get("empires").get(e).get("tp")).remove(t);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        if (serverdata.get("villages").get(village).containsKey("emp")) {
            String playerempire = serverdata.get("villages").get(village).get("emp").toString();
            ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(village);
            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() < 2) {
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
                    if (!serverdata.get("villages").get(v).get("own").toString().equals(playername)) {
                        temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                    }
                    return v;
                }).forEach((_item) -> {
                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.DARK_PURPLE + "Your empire " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + " has been terminated since " + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.LIGHT_PURPLE + " has abandoned their village, " + village + " and yours is the last one left in it, so your empire has been eleminated/removed");
                        if (tempHashMap.get("chc").containsKey(p)) {
                            if (tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                                tempHashMap.get("chc").remove(p);
                            }
                        }
                    });
                });
                Bukkit.getServer().getOnlinePlayers().stream().filter((p) -> (!temparraylist.contains(p.getUniqueId().toString()))).forEach((p) -> {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", has abandoned the village " + ChatColor.LIGHT_PURPLE + village + ChatColor.DARK_PURPLE + ", and because there is only 1 village left in the empire " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + ", the empire has been eleminated/removed");
                });
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
        }
        temparraylist.clear();
        if (serverdata.get("villages").get(village) != null) {
            if (serverdata.get("villages").get(village).get("mem") != null) {
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(village).get("mem"));
            }
        }
        if (serverdata.get("villages").get(village) != null) {
            if (serverdata.get("villages").get(village).get("man") != null) {
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(village).get("man"));
            }
        }
        temparraylist.stream().forEach((p) -> {
            serverdata.get("playerdata").remove(p);
            if (tempHashMap.get("chc").containsKey(p)) {
                if (tempHashMap.get("chc").get(p).equals("val") || tempHashMap.get("chc").get(p).equals("vmal") || tempHashMap.get("chc").get(p).equals("vally") || tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                    tempHashMap.get("chc").remove(p);
                }
            }
        });
        serverdata.get("playerdata").remove(playername);
        Bukkit.getOnlinePlayers().stream().filter((p) -> (p != player)).forEach((p) -> {
            p.sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + " has abandoned the village " + ChatColor.LIGHT_PURPLE + village);
        });
        if (serverdata.get("villages").get(village).get("ene") != null) {
            temparraylist.clear();
            temparraylist.addAll(((HashMap) serverdata.get("villages").get(village).get("ene")).keySet());
            temparraylist.stream().forEach((o) -> {
                ((HashMap) serverdata.get("villages").get(o).get("ene")).remove(village);
                if (((HashMap) serverdata.get("villages").get(o).get("ene")).isEmpty()) {
                    serverdata.get("villages").get(o).remove("ene");
                }
                if (serverdata.get("villages").get(o).get("trr") != null) {
                    if (((ArrayList) serverdata.get("villages").get(o).get("trr")).contains(village)) {
                        ((ArrayList) serverdata.get("villages").get(o).get("trr")).remove(village);
                        if (((ArrayList) serverdata.get("villages").get(o).get("trr")).isEmpty()) {
                            serverdata.get("villages").get(o).remove("trr");
                        }
                    }
                }
            });
        }
        if (serverdata.get("villages").get(village).get("all") != null) {
            temparraylist.clear();
            temparraylist.addAll(((ArrayList) serverdata.get("villages").get(village).get("all")));
            temparraylist.stream().forEach((o) -> {
                ((ArrayList) serverdata.get("villages").get(o).get("all")).remove(village);
                if (((ArrayList) serverdata.get("villages").get(o).get("all")).isEmpty()) {
                    serverdata.get("villages").get(o).remove("all");
                }
            });
        }
        serverdata.get("villages").remove(village);
        serverdata.get("villages").keySet().stream().filter((s) -> (serverdata.get("villages").get(s).get("alr") != null)).filter((s) -> (((ArrayList) serverdata.get("villages").get(s).get("alr")).contains(village))).forEach((s) -> {
            ((ArrayList) serverdata.get("villages").get(s).get("alr")).remove(village);
        });
        player.sendMessage(ChatColor.BLUE + "You have successfully abandoned the village and everyone in it");
    }

    public static void togglesetting(String playervillage, CommandSender sender, String[] args) {
        switch (args[2]) {
            case "fire":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("fire", "on");
                        sender.sendMessage(ChatColor.BLUE + "Fire has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("fire", "off");
                        sender.sendMessage(ChatColor.BLUE + "Fire has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "pvp":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("pvp", "on");
                        sender.sendMessage(ChatColor.BLUE + "Pvp has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("pvp", "off");
                        sender.sendMessage(ChatColor.BLUE + "Pvp has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "explosions":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("expl", "on");
                        sender.sendMessage(ChatColor.BLUE + "Explosions has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("expl", "off");
                        sender.sendMessage(ChatColor.BLUE + "Explosions has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "mobs":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("mobs", "on");
                        sender.sendMessage(ChatColor.BLUE + "Mob spawning has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("mobs", "off");
                        sender.sendMessage(ChatColor.BLUE + "Mob spawning has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + args[2] + " is not a valid setting to toggle, you can can change fire, pvp, explosions, and mobs");
                break;

        }
    }

    public static void Defeated(String village, String playername) {
        serverdata.get("worldmap").keySet().stream().forEach((w) -> {
            for (Object x : ((HashMap) serverdata.get("worldmap").get(w)).keySet().toArray()) {
                for (Object z : ((HashMap<String, HashMap>) serverdata.get("worldmap").get(w).get(x)).keySet().toArray()) {
                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").equals(village)) {
                        ((HashMap) serverdata.get("worldmap").get(w).get(x)).remove(z);
                        if (((HashMap) serverdata.get("worldmap").get(w).get(x)).isEmpty()) {
                            serverdata.get("worldmap").get(w).remove(x);
                        }
                        if (serverdata.get("villages").get(village).containsKey("emp")) {
                            String playerempire = serverdata.get("villages").get(village).get("emp").toString();
                            ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(village);
                            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() > 1) {
                                if (serverdata.get("empires").get(playerempire).containsKey("tp")) {
                                    for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).keySet())) {
                                        Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(playerempire).get("tp")).get("z").toString()));
                                        if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                            ((HashMap) serverdata.get("empires").get(playerempire).get("tp")).remove(t);
                                        }
                                    }
                                }
                            }
                            if (serverdata.get("empires").get(playerempire).containsKey("all")) {
                                for (String e : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("all"))) {
                                    if (serverdata.get("empires").get(e).containsKey("tp")) {
                                        for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(e).get("tp")).keySet())) {
                                            Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(e).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("z").toString()));
                                            if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                                ((HashMap) serverdata.get("empires").get(e).get("tp")).remove(t);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (serverdata.get("villages").get(village).containsKey("emp")) {
                        if (((ArrayList) serverdata.get("empires").get(serverdata.get("villages").get(village).get("emp").toString()).get("vils")).size() > 1) {
                            String playerempire = serverdata.get("villages").get(village).get("emp").toString();
                            for (String v : ((ArrayList<String>) serverdata.get("empires").get(serverdata.get("villages").get(village).get("emp").toString()).get("vils"))) {
                                if (serverdata.get("empires").get(playerempire).containsKey("all") && ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").equals(v)) {
                                    for (String e : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("all"))) {
                                        if (serverdata.get("empires").get(e).containsKey("tp")) {
                                            for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(e).get("tp")).keySet())) {
                                                Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(e).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("z").toString()));
                                                if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                                    ((HashMap) serverdata.get("empires").get(e).get("tp")).remove(t);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        if (serverdata.get("villages").get(village).containsKey("emp")) {
            String playerempire = serverdata.get("villages").get(village).get("emp").toString();
            ((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).remove(village);
            if (((ArrayList) serverdata.get("empires").get(playerempire).get("vils")).size() < 2) {
                temparraylist.clear();
                for (String v : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils"))) {
                    serverdata.get("villages").get(v).remove("emp");
                    if (serverdata.get("villages").get(v) != null) {
                        if (serverdata.get("villages").get(v).get("mem") != null) {
                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("mem"));
                        }
                        if (serverdata.get("villages").get(v).get("man") != null) {
                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(v).get("man"));
                        }
                    }
                    if (!serverdata.get("villages").get(v).get("own").toString().equals(playername)) {
                        temparraylist.add(serverdata.get("villages").get(v).get("own").toString());
                    }
                    temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                        Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.DARK_PURPLE + "Your empire " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.DARK_PURPLE + " has been terminated since " + ChatColor.LIGHT_PURPLE + village + ChatColor.LIGHT_PURPLE + " has been defeated and your village is the last one left in it, so it has been eleminated/removed");
                        if (tempHashMap.get("chc").containsKey(p)) {
                            if (tempHashMap.get("chc").get(p).equals("val") || tempHashMap.get("chc").get(p).equals("vmal") || tempHashMap.get("chc").get(p).equals("vally") || tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                                tempHashMap.get("chc").remove(p);
                            }
                        }
                    });
                }
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (!temparraylist.contains(p.getUniqueId().toString())) {
                        p.sendMessage(ChatColor.DARK_PURPLE + "The village " + ChatColor.LIGHT_PURPLE + village + ChatColor.DARK_PURPLE + ", has been defeated and since they were the last village in " + ChatColor.LIGHT_PURPLE + playerempire + ChatColor.LIGHT_PURPLE + ", the empire has been eleminated/removed");
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
        }
        temparraylist.clear();
        if (serverdata.get("villages").get(village) != null) {
            if (serverdata.get("villages").get(village).get("mem") != null) {
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(village).get("mem"));
            }
        }
        if (serverdata.get("villages").get(village) != null) {
            if (serverdata.get("villages").get(village).get("man") != null) {
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(village).get("man"));
            }
        }
        serverdata.get("playerdata").remove(serverdata.get("villages").get(village).get("own").toString());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (serverdata.get("playerdata").containsKey(playername)) {
                p.sendMessage(ChatColor.BLACK + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.GRAY + " from the village " + ChatColor.BLACK + serverdata.get("playerdata").get(playername).get("village").toString() + ChatColor.GRAY + " has struck the killing blow against the village " + ChatColor.STRIKETHROUGH + village + ChatColor.GRAY + ", and as a result of their defeat, that village has been lost");
            } else {
                p.sendMessage(ChatColor.BLACK + "Taxes" + ChatColor.GRAY + " has struck the killing blow against the village " + ChatColor.STRIKETHROUGH + village + ChatColor.GRAY + ", and as a result of their defeat, that village has been lost");
            }
        }
        temparraylist.stream().forEach((p) -> {
            serverdata.get("playerdata").remove(p);
            if (tempHashMap.get("chc").containsKey(p)) {
                if (tempHashMap.get("chc").get(p).equals("val") || tempHashMap.get("chc").get(p).equals("vmal") || tempHashMap.get("chc").get(p).equals("vally") || tempHashMap.get("chc").get(p).equals("eal") || tempHashMap.get("chc").get(p).equals("ealy")) {
                    tempHashMap.get("chc").remove(p);
                }
            }
        });
        if (serverdata.get("villages").get(village).get("ene") != null) {
            ((HashMap) serverdata.get("villages").get(village).get("ene")).keySet().stream().forEach((o) -> {
                ((HashMap) serverdata.get("villages").get(o.toString()).get("ene")).remove(village);
                if (((HashMap) serverdata.get("villages").get(o.toString()).get("ene")).isEmpty()) {
                    serverdata.get("villages").get(o.toString()).remove("ene");
                }
                if (serverdata.get("villages").get(o.toString()).get("trr") != null) {
                    if (((ArrayList) serverdata.get("villages").get(o.toString()).get("trr")).contains(village)) {
                        ((ArrayList) serverdata.get("villages").get(o.toString()).get("trr")).remove(village);
                        if (((ArrayList) serverdata.get("villages").get(o.toString()).get("trr")).isEmpty()) {
                            serverdata.get("villages").get(o.toString()).remove("trr");
                        }
                    }
                }
            });
        }
        if (serverdata.get("villages").get(village).get("all") != null) {
            ((ArrayList) serverdata.get("villages").get(village).get("all")).stream().forEach((o) -> {
                ((ArrayList) serverdata.get("villages").get(o.toString()).get("all")).remove(village);
                if (((ArrayList) serverdata.get("villages").get(o.toString()).get("all")).isEmpty()) {
                    serverdata.get("villages").get(o.toString()).remove("all");
                }
            });
        }
        //Simple check if its a defeat by player or taxes
        if (serverdata.get("playerdata").containsKey(playername)) {
            String pvil = serverdata.get("playerdata").get(playername).get("village").toString();
            serverdata.get("villages").get(pvil).replace("vau", ((Integer) serverdata.get("villages").get(pvil).get("vau")) + ((Integer) serverdata.get("villages").get(village).get("vau")));
        }
        serverdata.get("villages").remove(village);
        serverdata.get("villages").keySet().stream().filter((s) -> (serverdata.get("villages").get(s).get("alr") != null)).filter((s) -> (((ArrayList) serverdata.get("villages").get(s).get("alr")).contains(village))).forEach((s) -> {
            ((ArrayList) serverdata.get("villages").get(s).get("alr")).remove(village);
        });
    }
}
