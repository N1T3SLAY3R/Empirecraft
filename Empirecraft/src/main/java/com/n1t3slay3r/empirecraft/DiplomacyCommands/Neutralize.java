/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.DiplomacyCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 *
 * @author dylan
 */
public class Neutralize {
    public static void Neutralize(String section, String playersection, String targetsection, String playername) {
        if (section.equals("empires")) {
            if (serverdata.get("empires").get(playersection).containsKey("tp")||serverdata.get("empires").get(targetsection).containsKey("tp")) {
                serverdata.get("worldmap").keySet().stream().forEach((w) -> {
                    for (Object x : ((HashMap) serverdata.get("worldmap").get(w)).keySet().toArray()) {
                        for (Object z : ((HashMap<String, HashMap>) serverdata.get("worldmap").get(w).get(x)).keySet().toArray()) {
                            if (((ArrayList) serverdata.get("empires").get(targetsection).get("vils")).contains(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla"))) {
                                if (serverdata.get("empires").get(playersection).containsKey("tp")) {
                                    for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(playersection).get("tp")).keySet())) {
                                        Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(playersection).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(playersection).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(playersection).get("tp")).get("z").toString()));
                                        if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                            ((HashMap) serverdata.get("empires").get(playersection).get("tp")).remove(t);
                                        }
                                    }
                                }
                            }
                            if (((ArrayList) serverdata.get("empires").get(playersection).get("vils")).contains(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla"))) {
                                if (serverdata.get("empires").get(targetsection).containsKey("tp")) {
                                    for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(targetsection).get("tp")).keySet())) {
                                        Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(targetsection).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(targetsection).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(targetsection).get("tp")).get("z").toString()));
                                        if (loc.getChunk().getX() == Integer.parseInt(x.toString()) && loc.getChunk().getZ() == Integer.parseInt(z.toString())) {
                                            ((HashMap) serverdata.get("empires").get(targetsection).get("tp")).remove(t);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        ((ArrayList) serverdata.get(section).get(targetsection).get("all")).remove(playersection);
        if (((ArrayList) serverdata.get(section).get(targetsection).get("all")).isEmpty()) {
            serverdata.get(section).get(targetsection).remove("all");
        }
        ((ArrayList) serverdata.get(section).get(playersection).get("all")).remove(targetsection);
        if (((ArrayList) serverdata.get(section).get(playersection).get("all")).isEmpty()) {
            serverdata.get(section).get(playersection).remove("all");
        }
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_PURPLE + "You have successfully removed your alliance with " + ChatColor.LIGHT_PURPLE + targetsection);
        if (section.equals("villages")) {
            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).isOnline()) {
                Bukkit.getPlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has removed their alliance with you");
            }
            temparraylist.clear();
            if (serverdata.get(section).get(playersection) != null) {
                if (serverdata.get(section).get(playersection).get("mem") != null) {
                    temparraylist.addAll((Collection<? extends String>) serverdata.get(section).get(playersection).get("mem"));
                }
                if (serverdata.get(section).get(playersection).get("man") != null) {
                    temparraylist.addAll((Collection<? extends String>) serverdata.get(section).get(playersection).get("man"));
                }
            }
            temparraylist.stream().filter((p) -> (Bukkit.getOfflinePlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", has ended your alliance with " + ChatColor.LIGHT_PURPLE + targetsection);
            });
            temparraylist.clear();
            if (serverdata.get(section).get(targetsection) != null) {
                if (serverdata.get(section).get(targetsection).get("mem") != null) {
                    temparraylist.addAll((Collection<? extends String>) serverdata.get(section).get(targetsection).get("mem"));
                }
                if (serverdata.get(section).get(targetsection).get("man") != null) {
                    temparraylist.addAll((Collection<? extends String>) serverdata.get(section).get(targetsection).get("man"));
                }
            }
            temparraylist.stream().filter((p) -> (Bukkit.getOfflinePlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the village " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + "has ended the alliance");
            });
        } else {
            ((ArrayList<String>) serverdata.get("empires").get(playersection).get("vils")).stream().map((v) -> {
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the empire" + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + " has ended the alliance");
                });
            });
        }
    }
}
