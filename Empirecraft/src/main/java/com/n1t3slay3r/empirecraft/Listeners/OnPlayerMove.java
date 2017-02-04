/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import com.n1t3slay3r.empirecraft.main.Main;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.Material.AIR;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author dylan
 */
public class OnPlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String playerid = player.getUniqueId().toString();
        if (tempHashMap.get("tpx").containsKey(playerid)) {
            if (((Integer) tempHashMap.get("tpx").get(playerid)) != player.getLocation().getBlockX() || ((Integer) tempHashMap.get("tpy").get(playerid)) != player.getLocation().getBlockY() || ((Integer) tempHashMap.get("tpz").get(playerid)) != player.getLocation().getBlockZ()) {
                tempHashMap.get("tpx").remove(playerid);
                tempHashMap.get("tpy").remove(playerid);
                tempHashMap.get("tpz").remove(playerid);
                player.sendMessage(ChatColor.DARK_RED + "You have canceled your teleport home command because you moved");
            }
        }
        if (!tempHashMap.get("lcx").containsKey(playerid)) {
            tempHashMap.get("lcx").put(playerid, player.getLocation().getChunk().getX());
            tempHashMap.get("lcz").put(playerid, player.getLocation().getChunk().getZ());
        }
        if (!tempHashMap.get("lcx").get(playerid).equals(player.getLocation().getChunk().getX()) || !tempHashMap.get("lcz").get(playerid).equals(player.getLocation().getChunk().getZ())) {
            String w = player.getWorld().getUID().toString();
            Integer x = player.getLocation().getChunk().getX(), z = player.getLocation().getChunk().getZ();
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(w), ((Integer) tempHashMap.get("lcx").get(playerid)), ((Integer) tempHashMap.get("lcz").get(playerid)), "cla")) {
                if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                    if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla").equals(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla"))) {
                        player.sendMessage(ChatColor.YELLOW + "*You are now leaving the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla") + ChatColor.YELLOW + " and entering " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla") + ChatColor.YELLOW + "*");
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + "*You are now leaving the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla") + ChatColor.YELLOW + "*");
                }
            } else if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                player.sendMessage(ChatColor.YELLOW + "*You are now entering the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla") + ChatColor.YELLOW + "*");
            }
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "str")) {
                String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("con") && !((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("cle")) {
                    tempfile = new File(structureFolder, structure + ".yml");
                    FileConfiguration tempyaml = new YamlConfiguration();
                    try {
                        tempyaml.load(tempfile);
                    } catch (IOException | InvalidConfigurationException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Double c = 0., n = 0.;
                    Integer nx, ny, nz;
                    if (!QuickChecks.isMultiType(structure)) {
                        for (String sy : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                            for (String sx : tempyaml.getConfigurationSection("Scematic." + sy).getKeys(false)) {
                                for (String sz : tempyaml.getConfigurationSection("Scematic." + sy + "." + sx).getKeys(false)) {
                                    ny = Integer.parseInt(sy) + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2;
                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                        nx = Integer.parseInt(sx) + x * 16 - 1;
                                        nz = Integer.parseInt(sz) + z * 16 - 1;
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                        nx = Integer.parseInt(sz) + z * 16 - 1;
                                        nz = Math.abs(Integer.parseInt(sx) + x * 16 - 16);
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                        nx = 16 - Integer.parseInt(sx) + x * 16;
                                        nz = 16 - Integer.parseInt(sz) + z * 16;
                                    } else {
                                        nx = Math.abs(Integer.parseInt(sz) + z * 16 - 16);
                                        nz = Integer.parseInt(sx) + x * 16 - 1;
                                    }
                                    if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                        c++;
                                    }
                                    n++;
                                }
                            }
                        }
                    } else {
                        for (String cx : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                            Integer chx = Integer.parseInt(cx);
                            for (String cz : tempyaml.getConfigurationSection("Scematic." + cx).getKeys(false)) {
                                Integer chz = Integer.parseInt(cz);
                                for (String sy : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz).getKeys(false)) {
                                    for (String sx : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy).getKeys(false)) {
                                        for (String sz : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy + "." + sx).getKeys(false)) {
                                            ny = Integer.parseInt(sy) + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2;
                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                nx = Integer.parseInt(sx) + (x - chx) * 16 - 1;
                                                nz = Integer.parseInt(sz) + (z - chz) * 16 - 1;
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                nx = Integer.parseInt(sz) + (z - chz) * 16;
                                                nz = Math.abs(Integer.parseInt(sx) + (x - chx) * 16 - 16);
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                nx = 16 - Integer.parseInt(sx) + (x - chx) * 16;
                                                nz = 16 - Integer.parseInt(sz) + (z - chz) * 16;
                                            } else {
                                                nx = Math.abs(Integer.parseInt(sz) + (z - chz) * 16 - 16);
                                                nz = Integer.parseInt(sx) + (x - chx) * 16;
                                            }
                                            if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                c++;
                                            }
                                            n++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (Math.round(c / n * 100) != 100) {
                        player.sendMessage(ChatColor.YELLOW + "This structure is " + ChatColor.GOLD + Math.round(c / n * 100) + ChatColor.YELLOW + "% complete");
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "This structure is " + ChatColor.GOLD + "99" + ChatColor.YELLOW + "% complete");
                    }
                }
            }
            tempHashMap.get("lcx").replace(playerid, player.getLocation().getChunk().getX());
            tempHashMap.get("lcz").replace(playerid, player.getLocation().getChunk().getZ());
        }
    }
}
