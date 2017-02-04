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

/**
 *
 * @author dylan
 */
public class Truce {
    public static void Truce(String section, String playersection, String targetsection, String playername) {
        ((ArrayList) serverdata.get(section).get(playersection).get("trr")).remove(targetsection);
        if (((ArrayList) serverdata.get(section).get(playersection).get("trr")).isEmpty()) {
            serverdata.get(section).get(playersection).remove("trr");
        }
        ((HashMap) serverdata.get(section).get(targetsection).get("ene")).remove(playersection);
        if (((HashMap) serverdata.get(section).get(targetsection).get("ene")).isEmpty()) {
            serverdata.get(section).get(targetsection).remove("ene");
        }
        ((HashMap) serverdata.get(section).get(playersection).get("ene")).remove(targetsection);
        if (((HashMap) serverdata.get(section).get(playersection).get("ene")).isEmpty()) {
            serverdata.get(section).get(playersection).remove("ene");
        }
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.LIGHT_PURPLE + targetsection + ChatColor.DARK_PURPLE + " has already requested a truce with you so it has been done");
        if (section.equals("villages")) {
            if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString()))).isOnline()) {
                Bukkit.getPlayer(UUID.fromString(serverdata.get(section).get(targetsection).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of the village " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has accepted the truce request and ended the war!");
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
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + " has agreed upon a truce and ended the war!");
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
                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + targetsection + ChatColor.DARK_PURPLE + " has agreed upon a truce and ended the war!");
            });
        } else {
            ((ArrayList<String>) serverdata.get("empires").get(targetsection).get("vils")).stream().map((v) -> {
                if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString()))).isOnline()) {
                    Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + ", owner of empire " + ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + ", has accepted the truce request and ended the war!");
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + playersection + ChatColor.DARK_PURPLE + " has agreed upon a truce and ended the war!");
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
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + targetsection + ChatColor.DARK_PURPLE + " has agreed upon a truce and ended the war!");
                });
            });
        }
    }
}
