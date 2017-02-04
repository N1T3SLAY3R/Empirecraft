/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.DiplomacyCommands;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author dylan
 */
public class War {
    
    public static void War(String section, String playersection, String targetsection, String playername) {
        if (serverdata.get(section).get(playersection).get("ene") == null) {
            serverdata.get(section).get(playersection).put("ene", new HashMap<>());
        }
        if (section.equals("villages")) {
            ((HashMap) serverdata.get(section).get(playersection).get("ene")).put(targetsection, Config.get("Village Settings.War Time Delay"));
        } else {
            ((HashMap) serverdata.get(section).get(playersection).get("ene")).put(targetsection, Config.get("Empire Settings.War Time Delay"));
        }
        if (serverdata.get(section).get(targetsection).get("ene") == null) {
            serverdata.get(section).get(targetsection).put("ene", new HashMap<>());
        }
        if (section.equals("villages")) {
            ((HashMap) serverdata.get(section).get(targetsection).get("ene")).put(playersection, Config.get("Village Settings.War Time Delay"));
        } else {
            ((HashMap) serverdata.get(section).get(targetsection).get("ene")).put(playersection, Config.get("Empire Settings.War Time Delay"));
        }
        if (serverdata.get(section).get(playersection).get("all") != null) {
            if (((ArrayList) serverdata.get(section).get(playersection).get("all")).contains(targetsection)) {
                ((ArrayList) serverdata.get(section).get(playersection).get("all")).remove(targetsection);
                ((ArrayList) serverdata.get(section).get(targetsection).get("all")).remove(playersection);
            }
        }
        if (serverdata.get(section).get(playersection).get("alr") != null) {
            if (((ArrayList) serverdata.get(section).get(playersection).get("alr")).contains(targetsection)) {
                ((ArrayList) serverdata.get(section).get(playersection).get("alr")).remove(targetsection);
            }
        }
        if (serverdata.get(section).get(targetsection).get("alr") != null) {
            if (((ArrayList) serverdata.get(section).get(targetsection).get("alr")).contains(playersection)) {
                ((ArrayList) serverdata.get(section).get(targetsection).get("alr")).remove(playersection);
            }
        }
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.BLUE + " you have successfully declared war on " + ChatColor.AQUA + targetsection);
        if (section.equals("villages")) {
            if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString()))).isOnline()) {
                Bukkit.getPlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the village " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has declared war on you!");
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
            temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", Has declared war on you!");
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
            temparraylist.stream().filter((p) -> ((Bukkit.getOfflinePlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", has declared war on " + ChatColor.LIGHT_PURPLE + targetsection);
            });
        } else {
            ((ArrayList<String>) serverdata.get("empires").get(targetsection).get("vils")).stream().map((v) -> {
                if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString()))).isOnline()) {
                    Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", leader of the empire " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has declared war on your empire!");
                }
                return v;
            }).map((v) -> {
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", Has declared war on you!");
                });
            });
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", has declared war on " + ChatColor.LIGHT_PURPLE + targetsection);
                });
            });
        }
    }
    
}
