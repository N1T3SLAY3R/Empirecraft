/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 *
 * @author dylan
 */
public class OnPlayerOpenChest implements Listener {
    @EventHandler
    public void onPlayerOpenChest(InventoryOpenEvent event) {
        InventoryType inv = event.getInventory().getType();
        if (inv.equals(InventoryType.CHEST) || inv.equals(InventoryType.ANVIL) || inv.equals(InventoryType.BEACON) || inv.equals(InventoryType.BREWING) || inv.equals(InventoryType.DISPENSER) || inv.equals(InventoryType.DROPPER) || inv.equals(InventoryType.FURNACE) || inv.equals(InventoryType.HOPPER)) {
            HashSet<Byte> t = new HashSet();
            Block targetblock = ((Block) event.getPlayer().getTargetBlock(t, 9));
            String playername = event.getPlayer().getUniqueId().toString();
            String world = targetblock.getWorld().getUID().toString();
            Integer cx = targetblock.getLocation().getChunk().getX(), cz = targetblock.getLocation().getChunk().getZ();
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                    String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                    if (pvillage.equals(evillage)) {
                        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)));
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("com")) {
                                if (serverdata.get("playerdata").get(tplayer).get("com").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("containers")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Members.Containers").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (serverdata.get("villages").get(pvillage).containsKey("man")) {
                            if (!((ArrayList) serverdata.get("villages").get(pvillage).get("man")).contains(playername) && !playername.equals(serverdata.get("villages").get(pvillage).get("own")) && !((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).containsKey("str")) {
                                event.setCancelled(true);
                            }
                        } else if (playername.equals(serverdata.get("villages").get(pvillage).get("own")) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).containsKey("str")) {
                            //Do nothing/let event occur
                        } else {
                            event.setCancelled(true);
                        }
                    } else if (QuickChecks.isEnemyEmpire(pvillage, evillage) || QuickChecks.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        if (QuickChecks.isEnemyEmpire(pvillage, evillage) && QuickChecks.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                            if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null || ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).containsKey("str")) {
                                    event.setCancelled(true);
                                }
                            } else if (Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())).toString()) >= Integer.parseInt(((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage).toString())) {
                                event.setCancelled(true);
                                Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                            } else {
                                event.setCancelled(true);
                                Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                            }
                        } else if (QuickChecks.isEnemyEmpire(pvillage, evillage)) {
                            if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null) {
                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).containsKey("str")) {
                                    event.setCancelled(true);
                                }
                            } else {
                                event.setCancelled(true);
                                Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                            }
                        } else if (((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).containsKey("str")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                            Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (QuickChecks.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("coa")) {
                                if (serverdata.get("playerdata").get(tplayer).get("coa").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("containers")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Allys.Containers").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    } else {
                        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("coo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("coo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("containers")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Outsiders.Containers").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                    if (tplayer.equals(playername)) {
                        //You own it so your good
                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("coo")) {
                        if (serverdata.get("playerdata").get(tplayer).get("coo").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("containers")).get(playername).equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (Config.getString("Player Plots.Outsiders.Containers").equals("off")) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
