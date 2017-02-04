/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.configFile;
import static com.n1t3slay3r.empirecraft.main.Main.pluginFolder;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.IRON_DOOR;
import static org.bukkit.Material.LEVER;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.WOOD;
import static org.bukkit.Material.WOODEN_DOOR;
import static org.bukkit.Material.WOOL;
import org.bukkit.block.Block;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.WEST;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Bed;
import org.bukkit.material.Button;
import org.bukkit.material.Chest;
import org.bukkit.material.Crops;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Furnace;
import org.bukkit.material.Gate;
import org.bukkit.material.Ladder;
import org.bukkit.material.Lever;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Rails;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.material.Torch;
import org.bukkit.material.TrapDoor;
import org.bukkit.material.Tree;
import org.bukkit.material.TripwireHook;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

/**
 *
 * @author dylan
 */
public class OnPlayerInteractBlock implements Listener {
    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) throws IOException, FileNotFoundException, InvalidConfigurationException {
        Block targetblock = event.getClickedBlock();
        if (targetblock != null) {
            Player player = event.getPlayer();
            String playername = player.getUniqueId().toString();
            Action action = event.getAction();
            //DOOR/BUTTON USE CHECK
            if (action == Action.RIGHT_CLICK_BLOCK) {
                Material mat = targetblock.getType();
                String world = targetblock.getWorld().getUID().toString();
                Integer cx = targetblock.getLocation().getChunk().getX(), cz = targetblock.getLocation().getChunk().getZ();
                if (mat.equals(WOODEN_DOOR) || mat.equals(IRON_DOOR)) {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("dom")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("dom").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("doors")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Doors").equals("off")) {
                                        event.setCancelled(true);
                                    }
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("doa")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("doa").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("doors")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Allys.Doors").equals("off")) {
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("doo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("doo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("doors")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Outsiders.Doors").equals("off")) {
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
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("doo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("doo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("doors")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Outsiders.Doors").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else if (mat.equals(Material.WOOD_BUTTON) || mat.equals(Material.STONE_BUTTON)) {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("bum")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("bum").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("buttons")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Buttons").equals("off")) {
                                        event.setCancelled(true);
                                    }
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("bua")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("bua").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("buttons")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Allys.Buttons").equals("off")) {
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("buo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("buo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("buttons")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Outsiders.Buttons").equals("off")) {
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
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("buo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("buo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("buttons")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Outsiders.Buttons").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else if (mat.equals(LEVER)) {
                    if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("lem")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("lem").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("levers")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Levers").equals("off")) {
                                        event.setCancelled(true);
                                    }
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("lea")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("lea").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("levers")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Allys.Levers").equals("off")) {
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
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("leo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("leo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("levers")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Outsiders.Levers").equals("off")) {
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
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("leo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("leo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (QuickChecks.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
                                if (((HashMap) serverdata.get("playerdata").get(tplayer).get("levers")).get(playername).equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (Config.getString("Player Plots.Outsiders.Levers").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }

            //CHECK FOR STRUCTURE CREATION
            if (player.getItemInHand().getType().toString().equals("STICK")) {
                if (tempHashMap.get("mainchest").containsKey(playername)) {
                    if (targetblock.getChunk().equals((Chunk) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("chunk"))) {
                        if (targetblock.getY() >= ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight")) && targetblock.getY() <= (((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("height")) + ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight")))) {
                            if (targetblock.getType() != Material.CHEST) {
                                targetblock.setType(Material.CHEST);
                                Chest temp = new Chest(0, targetblock.getData());
                                int px = player.getLocation().getBlockX();
                                int pz = player.getLocation().getBlockZ();
                                if (pz > targetblock.getZ()) {
                                    if (px > targetblock.getX()) {
                                        if (pz - targetblock.getZ() > px - targetblock.getX()) {
                                            temp.setFacingDirection(SOUTH);
                                        } else {
                                            temp.setFacingDirection(EAST);
                                        }
                                    } else {
                                        if (pz - targetblock.getZ() > targetblock.getX() - px) {
                                            temp.setFacingDirection(SOUTH);
                                        } else {
                                            temp.setFacingDirection(WEST);
                                        }
                                    }
                                } else if (px > targetblock.getX()) {
                                    if (targetblock.getZ() - pz < px - targetblock.getX()) {
                                        temp.setFacingDirection(EAST);
                                    } else {
                                        temp.setFacingDirection(NORTH);
                                    }
                                } else {
                                    if (targetblock.getZ() - pz < targetblock.getX() - px) {
                                        temp.setFacingDirection(WEST);
                                    } else {
                                        temp.setFacingDirection(NORTH);
                                    }
                                }
                                targetblock.setData(temp.getData());
                            }
                            Config.load(configFile);
                            if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("normal")) {
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Type", "Normal");
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Creation Cost", 1000);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Height Offset", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Upkeep", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Revenue", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Income Timer", 3600);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Animals", new ArrayList());
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Animal Spawn Range", 7);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Required Materials", new ArrayList());
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Produced Materials", new ArrayList());
                            } else if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("multi")) {
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Type", "Multi");
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Creation Cost", 1000);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Height Offset", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Upkeep", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Revenue", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Income Timer", 3600);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Required Materials", new ArrayList());
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Productions.Main.Produced Materials", new ArrayList());
                            } else if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("camp")) {
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Type", "Camp");
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Creation Cost", 1000);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Height Offset", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Upkeep", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Revenue", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Income Timer", 3600);
                            } else if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("archer")) {
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Type", "Archer");
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Creation Cost", 1000);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Height Offset", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Upkeep", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Required Materials", new ArrayList());
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Bounce", false);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Critical", false);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Knockback", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Fire Ticks", 0);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Arrow Speed", 2);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Arrow Spread", 12);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Arrows Fired", 1);
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Range", 1);
                            } else if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("rank")) {
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Creation Cost", 1000);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Height Offset", 0);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Max Plots", 9);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Upkeep", 100);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Max Villagers", 9);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Min Villagers", 1);
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Structure Limits", new ArrayList());
                            }
                            File tempfile = new File(structureFolder, ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".yml");
                            try {
                                tempfile.createNewFile();
                            } catch (IOException ex) {
                            }
                            FileConfiguration tempwriteup = new YamlConfiguration();
                            int cy = 0, cx, cz, hp = 0;
                            Chunk mc = ((Chunk) ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).get("chunk"));
                            if (((HashMap) tempHashMap.get("mainchest").get(playername)).get("type").equals("multi")) {
                                for (Chunk c : ((ArrayList<Chunk>) ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).get("chunks"))) {
                                    cy = 0;
                                    for (int y = ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight")); y <= (((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("height")) + ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight"))); y++) {
                                        cy++;
                                        cx = 0;
                                        for (int x = (mc.getX() - c.getX()) * 16; x < (mc.getX() - c.getX()) * 16 + 16; x++) {
                                            cx++;
                                            cz = 0;
                                            for (int z = (c.getZ() + mc.getZ()) * 16; z < (c.getZ() + mc.getZ()) * 16 + 16; z++) {
                                                cz++;
                                                if (!Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType().equals(AIR)) {
                                                    tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".id", Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType().toString());
                                                    Material mat = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType();
                                                    if (mat == Material.ACACIA_STAIRS || mat == Material.BIRCH_WOOD_STAIRS || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Stairs) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".inv", ((Stairs) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                    } else if (mat == Material.CHEST) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Chest) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".cli", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                    } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Dispenser) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.WOOD || mat == Material.LOG) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Tree) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((Tree) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getSpecies().toString());
                                                    } else if (mat == Material.DETECTOR_RAIL) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((DetectorRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".cli", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                    } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((PistonBaseMaterial) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.STEP) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Step) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((Step) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getMaterial().toString());
                                                    } else if (mat == Material.WOOD_STEP) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((WoodenStep) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((WoodenStep) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getSpecies().toString());
                                                    } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Torch) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.LADDER) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Ladder) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Furnace) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.RAILS) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Rails) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".cli", ((Rails) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                    } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Pumpkin) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.FENCE_GATE) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Gate) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Bed) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".bed", ((Bed) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isHeadOfBed());
                                                    } else if (mat == Material.SANDSTONE) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((Sandstone) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getType().toString());
                                                    } else if (mat == Material.WOOL) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((Wool) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getColor().toString());
                                                    } else if (mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Button) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.CROPS) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((Crops) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getState().toString());
                                                    } else if (mat == Material.TRAP_DOOR) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".ope", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOpen());
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".inv", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                    } else if (mat == Material.TRIPWIRE_HOOK) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".typ", ((TripwireHook) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    } else if (mat == Material.LEVER) {
                                                        tempwriteup.set("Scematic." + c.getX() + "." + c.getZ() + "." + cy + "." + cx + "." + cz + ".dat", ((Lever) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing());
                                                    }
                                                    if (Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() != Material.AIR) {
                                                        hp++;
                                                        if (Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOL || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == SANDSTONE || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == LOG || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOD) {
                                                            Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat + "_" + tempwriteup.getString("Scematic." + cy + "." + cx + "." + cz + ".typ"), 5);
                                                        } else {
                                                            Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat, 1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (int y = ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight")); y <= (((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("height")) + ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight"))); y++) {
                                    cy++;
                                    cx = 0;
                                    for (int x = targetblock.getChunk().getX() * 16; x < targetblock.getChunk().getX() * 16 + 16; x++) {
                                        cx++;
                                        cz = 0;
                                        for (int z = targetblock.getChunk().getZ() * 16; z < targetblock.getChunk().getZ() * 16 + 16; z++) {
                                            cz++;
                                            if (!Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType().equals(AIR)) {
                                                tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".id", Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType().toString());
                                                Material mat = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType();
                                                if (mat == Material.ACACIA_STAIRS || mat == Material.BIRCH_WOOD_STAIRS || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Stairs) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".inv", ((Stairs) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                } else if (mat == Material.CHEST) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Chest) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".cli", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Dispenser) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.WOOD || mat == Material.LOG) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Tree) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Tree) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getSpecies().toString());
                                                } else if (mat == Material.DETECTOR_RAIL) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((DetectorRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".cli", ((PoweredRail) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((PistonBaseMaterial) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.STEP) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Step) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Step) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getMaterial().toString());
                                                } else if (mat == Material.WOOD_STEP) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((WoodenStep) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((WoodenStep) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getSpecies().toString());
                                                } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Torch) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.LADDER) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Ladder) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Furnace) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.RAILS) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Rails) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getDirection().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".cli", ((Rails) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOnSlope());
                                                } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Pumpkin) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.FENCE_GATE) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Gate) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Bed) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".bed", ((Bed) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isHeadOfBed());
                                                } else if (mat == Material.SANDSTONE) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Sandstone) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getType().toString());
                                                } else if (mat == Material.WOOL) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Wool) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getColor().toString());
                                                } else if (mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((Button) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.CROPS) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Crops) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getState().toString());
                                                } else if (mat == Material.TRAP_DOOR) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".dat", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".ope", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isOpen());
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".inv", ((TrapDoor) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).isInverted());
                                                } else if (mat == Material.TRIPWIRE_HOOK) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((TripwireHook) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                } else if (mat == Material.LEVER) {
                                                    tempwriteup.set("Scematic." + cy + "." + cx + "." + cz + ".typ", ((Lever) Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getState().getData()).getFacing().toString());
                                                }
                                                if (Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() != Material.AIR) {
                                                    hp++;
                                                    if (!(((HashMap) tempHashMap.get("mainchest").get(playername)).get("type")).equals("rank")) {
                                                        if (Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOL || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == SANDSTONE || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == LOG || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOD) {
                                                            Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat + "_" + tempwriteup.getString("Scematic." + cy + "." + cx + "." + cz + ".typ"), 5);
                                                        } else {
                                                            Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat, 1);
                                                        }
                                                    } else {
                                                        if (Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOL || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == SANDSTONE || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == LOG || Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x, y, z).getType() == WOOD) {
                                                            Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat + "_" + tempwriteup.getString("Scematic." + cy + "." + cx + "." + cz + ".typ"), 5);
                                                        } else {
                                                            Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Block Hp." + mat, 1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!(((HashMap) tempHashMap.get("mainchest").get(playername)).get("type")).equals("rank")) {
                                Config.set("Village Structures." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Total Hp", hp);
                            } else {
                                Config.set("Village Ranks." + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ".Total Hp", hp);
                            }
                            tempwriteup.set("Main Chest.X", targetblock.getLocation().getBlockX() - targetblock.getChunk().getX() * 16 + 1);
                            tempwriteup.set("Main Chest.Y", targetblock.getLocation().getBlockY() - ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("baseheight")) + 1);
                            tempwriteup.set("Main Chest.Z", targetblock.getLocation().getBlockZ() - targetblock.getChunk().getZ() * 16 + 1);
                            tempwriteup.set("Height", ((Integer) ((HashMap) tempHashMap.get("mainchest").get(playername)).get("height")));
                            tempwriteup.save(tempfile);
                            tempfile = new File(pluginFolder, "Config.yml");
                            Config.save(tempfile);
                            player.sendMessage(ChatColor.BLUE + "The structure " + ChatColor.AQUA + ((HashMap) tempHashMap.get("mainchest").get(playername)).get("name") + ChatColor.BLUE + " has been successfully created, now go to the config (reload) and perfect it");
                            tempHashMap.get("mainchest").remove(playername);
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "The main chest block must located inside the structures height");
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "The main chest block must located inside the structures origin chunk");
                    }
                }
            }
        }
    }
}
