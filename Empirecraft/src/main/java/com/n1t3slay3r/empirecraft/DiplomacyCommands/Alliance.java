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
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author dylan
 */
public class Alliance {
    public static void Alliance(String section, String playersection, String targetsection, String playername) {
        ((ArrayList) serverdata.get(section).get(playersection).get("alr")).remove(targetsection);
        if (((ArrayList) serverdata.get(section).get(playersection).get("alr")).isEmpty()) {
            serverdata.get(section).get(playersection).remove("alr");
        }
        if (serverdata.get(section).get(targetsection).get("all") == null) {
            serverdata.get(section).get(targetsection).put("all", new ArrayList<>());
        }
        if (serverdata.get(section).get(playersection).get("all") == null) {
            serverdata.get(section).get(playersection).put("all", new ArrayList<>());
        }
        ((ArrayList) serverdata.get(section).get(targetsection).get("all")).add(playersection);
        ((ArrayList) serverdata.get(section).get(playersection).get("all")).add(targetsection);
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_PURPLE + "You have successfully created an alliance with " + ChatColor.LIGHT_PURPLE + targetsection);
        if (section.equals("villages")) {
            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).isOnline()) {
                Bukkit.getPlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the village" + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has accepted the alliance request");
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
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + " has created an alliance with you!");
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
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + targetsection + ChatColor.DARK_PURPLE + " has created an alliance with you!");
            });
        } else {
            ((ArrayList<String>) serverdata.get("empires").get(targetsection).get("vils")).stream().map((v) -> {
                if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString()))).isOnline()) {
                    Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the empire " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has accepted the alliance request");
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + " has created an alliance with you!");
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + targetsection + ChatColor.DARK_PURPLE + " has created an alliance with you!");
                });
            });
        }
    }
}
