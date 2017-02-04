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
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

/**
 *
 * @author dylan
 */
public class OnPlayerBucketEmptyEvent implements Listener {
    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();
        String playername = player.getUniqueId().toString();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (pvillage.equals(evillage)) {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)));
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("mom")) {
                            if (serverdata.get("playerdata").get(tplayer).get("mom").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
                            if (((HashMap) serverdata.get("playerdata").get(tplayer).get("modify")).get(playername).equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Player Plots.Members.Modify").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (serverdata.get("villages").get(pvillage).containsKey("man") && !((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                        if (!((ArrayList) serverdata.get("villages").get(pvillage).get("man")).contains(playername) && !playername.equals(serverdata.get("villages").get(pvillage).get("own"))) {
                            event.setCancelled(true);
                        }
                    } else if (playername.equals(serverdata.get("villages").get(pvillage).get("own")) && !((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                        //Do nothing/let event occur
                    } else {
                        event.setCancelled(true);
                    }
                } else if (QuickChecks.isEnemyEmpire(pvillage, evillage) || QuickChecks.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                    if (QuickChecks.isEnemyEmpire(pvillage, evillage) && QuickChecks.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null || ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                                event.setCancelled(true);
                            }
                        } else if (Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())).toString()) >= Integer.parseInt(((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage).toString())) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (QuickChecks.isEnemyEmpire(pvillage, evillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                    }
                } else if (QuickChecks.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moa")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moa").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
                            if (((HashMap) serverdata.get("playerdata").get(tplayer).get("modify")).get(playername).equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Player Plots.Allys.Modify").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
                            if (((HashMap) serverdata.get("playerdata").get(tplayer).get("modify")).get(playername).equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Player Plots.Outsiders.Modify").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else {

                        event.setCancelled(true);
                    }
                }
            } else if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                    if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
                    if (((HashMap) serverdata.get("playerdata").get(tplayer).get("modify")).get(playername).equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (Config.getString("Player Plots.Outsiders.Modify").equals("off")) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
