/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

import com.n1t3slay3r.empirecraft.Commands.MainConversions;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.configFile;
import static com.n1t3slay3r.empirecraft.main.Main.pluginFolder;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.IRON_DOOR;
import static org.bukkit.Material.LAVA;
import static org.bukkit.Material.LEVER;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.STATIONARY_LAVA;
import static org.bukkit.Material.STATIONARY_WATER;
import static org.bukkit.Material.WATER;
import static org.bukkit.Material.WOOD;
import static org.bukkit.Material.WOODEN_DOOR;
import static org.bukkit.Material.WOOL;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.WEST;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
import org.bukkit.util.Vector;

/**
 *
 * @author Dylan Malec
 */
public class Listeners implements Listener {

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntityType().toString().equals("ARROW") && event.getEntity().getShooter() == null) {
            Entity e = event.getEntity();
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()), e.getLocation().getChunk().getX(), e.getLocation().getChunk().getZ(), "str")) {
                if (Config.getString("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()).get(e.getLocation().getChunk().getX())).get(e.getLocation().getChunk().getZ())).get("str").toString() + ".Type").equals("Archer")) {
                    Vector velocity = e.getVelocity();
                    Location loc = e.getLocation().add(velocity.normalize());
                    Vector temp = new Vector(0, abs(e.getFallDistance()) + 1, 0);
                    Bukkit.getWorld(e.getLocation().getWorld().getUID()).spawnArrow(loc, velocity.subtract(temp), (float) (Config.getDouble("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()).get(e.getLocation().getChunk().getX())).get(e.getLocation().getChunk().getZ())).get("str").toString() + ".Arrow Speed")), 0);
                    e.remove();
                }
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Config.getStringList("Global Settings.Uncraftable Items").stream().filter((s) -> (event.getCurrentItem().getType().equals(Material.getMaterial(s)))).forEach((_item) -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onSmeltItem(FurnaceSmeltEvent event) {
        Config.getStringList("Global Settings.Unsmeltable Items").stream().filter((s) -> (event.getResult().getType().equals(Material.getMaterial(s)))).forEach((_item) -> {
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (tempHashMap.get("chc").containsKey(player.getUniqueId().toString())) {
            event.getRecipients().clear();
            if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("loc")) {
                Location sloc = player.getLocation();
                player.getWorld().getPlayers().stream().filter((p) -> (Math.abs(sloc.getChunk().getX() - p.getLocation().getChunk().getX()) <= Config.getInt("Global Settings.Local Chat Range") && Math.abs(sloc.getChunk().getZ() - p.getLocation().getChunk().getZ()) <= Config.getInt("Global Settings.Local Chat Range"))).forEach((p) -> {
                    event.getRecipients().add(p);
                });
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("wor")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "World" + ChatColor.YELLOW + ") " + event.getFormat());
                event.getRecipients().addAll(Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers());
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("val")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "Village" + ChatColor.YELLOW + ") " + event.getFormat());
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("mem")) {
                    ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                }
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("man")) {
                    ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                }
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("vma")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "Village Managers" + ChatColor.YELLOW + ") " + event.getFormat());
                ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                    event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                });
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("valy")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "Ally Villages" + ChatColor.YELLOW + ") " + event.getFormat());
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("mem")) {
                    ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                }
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("man")) {
                    ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                }
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("all")) {
                    ((ArrayList<String>) serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("all")).stream().map((v) -> {
                        if (serverdata.get("villages").get(v).containsKey("mem")) {
                            ((ArrayList<String>) serverdata.get("villages").get(v).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                                event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                            });
                        }
                        return v;
                    }).map((v) -> {
                        if (serverdata.get("villages").get(v).containsKey("man")) {
                            ((ArrayList<String>) serverdata.get("villages").get(v).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                                event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                            });
                        }
                        return v;
                    }).filter((v) -> (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).isOnline())).forEach((v) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())));
                    });
                }
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("eal")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "Empire" + ChatColor.YELLOW + ") " + event.getFormat());
                ((ArrayList<String>) serverdata.get("empires").get(serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp").toString()).get("vils")).stream().map((v) -> {
                    if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).isOnline()) {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())));
                    }
                    return v;
                }).map((v) -> {
                    if (serverdata.get("villages").get(v).containsKey("mem")) {
                        ((ArrayList<String>) serverdata.get("villages").get(v).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                            event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                        });
                    }
                    return v;
                }).filter((v) -> (serverdata.get("villages").get(v).containsKey("man"))).forEach((v) -> {
                    ((ArrayList<String>) serverdata.get("villages").get(v).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                });
            } else if (tempHashMap.get("chc").get(player.getUniqueId().toString()).equals("ealy")) {
                event.setFormat(ChatColor.YELLOW + "(" + ChatColor.GOLD + "Empire Allies" + ChatColor.YELLOW + ") " + event.getFormat());
                ((ArrayList<String>) serverdata.get("empires").get(serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp").toString()).get("vils")).stream().map((v) -> {
                    if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).isOnline()) {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())));
                    }
                    return v;
                }).map((v) -> {
                    if (serverdata.get("villages").get(v).containsKey("mem")) {
                        ((ArrayList<String>) serverdata.get("villages").get(v).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                            event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                        });
                    }
                    return v;
                }).filter((v) -> (serverdata.get("villages").get(v).containsKey("man"))).forEach((v) -> {
                    ((ArrayList<String>) serverdata.get("villages").get(v).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                        event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                    });
                });
                if (serverdata.get("empires").get(serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp").toString()).containsKey("all")) {
                    ((ArrayList<String>) serverdata.get("empires").get(serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp").toString()).get("all")).stream().forEach((e) -> {
                        ((ArrayList<String>) serverdata.get("empires").get(e).get("vils")).stream().map((v) -> {
                            if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())).isOnline()) {
                                event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(v).get("own").toString())));
                            }
                            return v;
                        }).map((v) -> {
                            if (serverdata.get("villages").get(v).containsKey("mem")) {
                                ((ArrayList<String>) serverdata.get("villages").get(v).get("mem")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                                    event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                                });
                            }
                            return v;
                        }).filter((v) -> (serverdata.get("villages").get(v).containsKey("man"))).forEach((v) -> {
                            ((ArrayList<String>) serverdata.get("villages").get(v).get("man")).stream().filter((s) -> (Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())).forEach((s) -> {
                                event.getRecipients().add(Bukkit.getPlayer(UUID.fromString(s)));
                            });
                        });
                    });
                }
            }
            if (!event.getRecipients().contains(player)) {
                event.getRecipients().add(player);
            }
        }
        /*for (Player p : event.getRecipients()) {
         if (tempHashMap.get("chc").containsKey(p.getUniqueId())) {
         switch (tempHashMap.get("chc").get(p.getUniqueId()).toString()) {
         case "local":
         Location loc = p.getLocation();
         if (Math.abs(loc.getChunk().getX() - player.getLocation().getChunk().getX()) <= Config.getInt("Global Settings.Local Chat Range") && Math.abs(loc.getChunk().getZ() - player.getLocation().getChunk().getZ()) > Config.getInt("Global Settings.Local Chat Range")) {
         event.getRecipients().remove(p);
         }
         break;
         case "world":
         if (!player.getWorld().equals(p.getWorld())) {
         event.getRecipients().remove(p);
         }
         break;
         case "val":
         if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village"))) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "vmal":
         if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village"))) {
         event.getRecipients().remove(p);
         } else if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()), "man", player.getUniqueId().toString())) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "vally":
         if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village")) && !MainConversions.isPlayerInArrayList(serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()), "all", serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString())) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "eal":
         if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("emp")) {
         event.getRecipients().remove(p);
         } else if (!serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp").equals(serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()).get("emp"))) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "ealy":
         if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
         if (!MainConversions.isVillageAlliedOrYoursEmpireWise(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString(), serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()).get("emp").toString())) {
         event.getRecipients().remove(p);
         }
         }
         break;
         }
         }
         }*/
        if (serverdata.get("playerdata").containsKey(player.getUniqueId().toString())) {
            if (serverdata.get("playerdata").get(player.getUniqueId().toString()).containsKey("village")) {
                event.setFormat(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village") + ChatColor.DARK_GREEN + "] " + event.getFormat());
                if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("emp")) {
                    event.setFormat(ChatColor.DARK_GRAY + "{" + ChatColor.GRAY + serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).get("emp") + ChatColor.DARK_GRAY + "} " + event.getFormat());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && Config.getString("Global Settings.Auto Update Notifier").equals("on")) {
            //Update updateCheck = new Update(80075, "ed2919ef1dcca33b92ac5571e73d53ba1e474a4e", player.getUniqueId().toString());
        }
    }

    //LIQUIDS such as water or lava or dragon egg tps
    @EventHandler
    public void onBlockMove(BlockFromToEvent event) {
        Block block = event.getBlock(), to = event.getToBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = to.getLocation().getChunk().getX(), z = to.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                if (block.getType().equals(WATER)) {
                    block.setType(STATIONARY_WATER);
                } else if (block.getType().equals(LAVA)) {
                    block.setType(STATIONARY_LAVA);
                }
                event.setCancelled(true);
            }
        }
    }

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
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(w), ((Integer) tempHashMap.get("lcx").get(playerid)), ((Integer) tempHashMap.get("lcz").get(playerid)), "cla")) {
                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                    if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla").equals(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla"))) {
                        player.sendMessage(ChatColor.YELLOW + "*You are now leaving the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla") + ChatColor.YELLOW + " and entering " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla") + ChatColor.YELLOW + "*");
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + "*You are now leaving the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(tempHashMap.get("lcx").get(playerid))).get(tempHashMap.get("lcz").get(playerid))).get("cla") + ChatColor.YELLOW + "*");
                }
            } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                player.sendMessage(ChatColor.YELLOW + "*You are now entering the village " + ChatColor.GOLD + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla") + ChatColor.YELLOW + "*");
            }
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(w), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "str")) {
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
                    if (!MainConversions.isMultiType(structure)) {
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

    @EventHandler
    public void onBlockBurn(BlockFadeEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                event.setCancelled(true);
            }
        }
    }

    /* TOOOOOO LAGGYYYYYYYYYYY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     @EventHandler
     public void onBlockPhysics(BlockPhysicsEvent event) {
     Integer x = event.getBlock().getLocation().getChunk().getX(), z = event.getBlock().getLocation().getChunk().getZ();
     if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(event.getBlock().getWorld().getUID().toString()), x, z, "cla")) {
     if (((HashMap) ((HashMap) serverdata.get("worldmap").get(event.getBlock().getWorld().getUID().toString()).get(x)).get(z)).containsKey("str")) {
     event.setCancelled(true);
     }
     }
     }*/
    @EventHandler
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            String pvillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
            if (serverdata.get("villages").get(pvillage).containsKey("fire")) {
                if (serverdata.get("villages").get(pvillage).get("fire").equals("off")) {
                    event.setCancelled(true);
                }
            } else if (Config.getString("Village Settings.Toggle Settings.Fire Enabled").equals("off")) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();
        String playername = player.getUniqueId().toString();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (pvillage.equals(evillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)));
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("mom")) {
                            if (serverdata.get("playerdata").get(tplayer).get("mom").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                    if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                    } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moa")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moa").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
            } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                    if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
    
    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();
        String playername = player.getUniqueId().toString();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (pvillage.equals(evillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)));
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("mom")) {
                            if (serverdata.get("playerdata").get(tplayer).get("mom").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                    if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                    } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moa")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moa").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
            } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                    if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        String world = entity.getWorld().getUID().toString();
        Integer x = entity.getLocation().getChunk().getX(), z = entity.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            String pvillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
            if (serverdata.get("villages").get(pvillage).containsKey("mobs")) {
                if (serverdata.get("villages").get(pvillage).get("mobs").equals("off")) {
                    event.setCancelled(true);
                }
            } else if (Config.getString("Village Settings.Toggle Settings.Mobs Enabled").equals("off")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        String world = event.getEntity().getWorld().getUID().toString();
        Integer x, z;
        ArrayList<Block> temparraylist = new ArrayList<>();
        temparraylist.addAll(event.blockList());
        for (Block b : temparraylist) {
            x = b.getLocation().getChunk().getX();
            z = b.getLocation().getChunk().getZ();
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
                String pvillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (serverdata.get("villages").get(pvillage).containsKey("expl")) {
                    if (serverdata.get("villages").get(pvillage).get("expl").equals("off") || ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                        event.blockList().remove(b);
                    }
                } else if (Config.getString("Village Settings.Toggle Settings.Explosions Enabled").equals("off") || ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                    event.blockList().remove(b);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material mat = block.getType();
        Player player = event.getPlayer();
        String playername = player.getUniqueId().toString();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (pvillage.equals(evillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)));
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("mom")) {
                            if (serverdata.get("playerdata").get(tplayer).get("mom").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    } else if (Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                        //Do nothing/let event occur
                    } else {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                    if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null || ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                                event.setCancelled(true);
                            }
                        } else if (Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())).toString()) >= Integer.parseInt(((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage).toString())) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                                event.setCancelled(true);
                            }
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                    }
                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moa")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moa").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
            } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                    if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material mat = block.getType();
        Player player = event.getPlayer();
        String playername = player.getUniqueId().toString();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (pvillage.equals(evillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)));
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("mom")) {
                            if (serverdata.get("playerdata").get(tplayer).get("mom").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    } else if (Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                        //Do nothing/let event occur
                    } else {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                    if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null || ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                                if (MainConversions.listenerBlockBreak(world, x, z, player, mat, evillage, playername, block)) {
                                    event.setCancelled(true);
                                }
                            }
                        } else if (Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())).toString()) >= Integer.parseInt(((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage).toString())) {
                            if (MainConversions.listenerBlockBreak(world, x, z, player, mat, evillage, playername, block)) {
                                event.setCancelled(true);
                            }
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                        } else {
                            if (MainConversions.listenerBlockBreak(world, x, z, player, mat, evillage, playername, block)) {
                                event.setCancelled(true);
                            }
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
                        if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) == null) {
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                                if (MainConversions.listenerBlockBreak(world, x, z, player, mat, evillage, playername, block)) {
                                    event.setCancelled(true);
                                }
                            }
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).get(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString())) + ChatColor.DARK_RED + " seconds");
                        }
                    } else if (((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) == null) {
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str") && !Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
                            if (MainConversions.listenerBlockBreak(world, x, z, player, mat, evillage, playername, block)) {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.DARK_RED + "The war doesn't start for " + ChatColor.RED + ((HashMap) serverdata.get("villages").get(evillage).get("ene")).get(pvillage) + ChatColor.DARK_RED + " seconds");
                    }
                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("moa")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moa").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                        String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                        if (tplayer.equals(playername)) {
                            //Let Run
                        } else if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                            if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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
            } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "playerplot")) {
                String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("playerplot").toString();
                if (tplayer.equals(playername)) {
                    //Let Run
                } else if (serverdata.get("playerdata").get(tplayer).containsKey("moo")) {
                    if (serverdata.get("playerdata").get(tplayer).get("moo").equals("off")) {
                        event.setCancelled(true);
                    }
                } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "modify", playername)) {
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

    @EventHandler
    public void onPlayerOpenChest(InventoryOpenEvent event) {
        InventoryType inv = event.getInventory().getType();
        if (inv.equals(InventoryType.CHEST) || inv.equals(InventoryType.ANVIL) || inv.equals(InventoryType.BEACON) || inv.equals(InventoryType.BREWING) || inv.equals(InventoryType.DISPENSER) || inv.equals(InventoryType.DROPPER) || inv.equals(InventoryType.FURNACE) || inv.equals(InventoryType.HOPPER)) {
            HashSet<Byte> t = new HashSet();
            Block targetblock = ((Block) event.getPlayer().getTargetBlock(t, 9));
            String playername = event.getPlayer().getUniqueId().toString();
            String world = targetblock.getWorld().getUID().toString();
            Integer cx = targetblock.getLocation().getChunk().getX(), cz = targetblock.getLocation().getChunk().getZ();
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                    String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                    if (pvillage.equals(evillage)) {
                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            System.out.println(tplayer + " " + playername + " " + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)));
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("com")) {
                                if (serverdata.get("playerdata").get(tplayer).get("com").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
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
                    } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                        } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                    } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("coa")) {
                                if (serverdata.get("playerdata").get(tplayer).get("coa").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
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
                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("coo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("coo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
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
                } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                    if (tplayer.equals(playername)) {
                        //You own it so your good
                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("coo")) {
                        if (serverdata.get("playerdata").get(tplayer).get("coo").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "containers", playername)) {
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

    @EventHandler
    public void onPvP(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(PLAYER)) {
            String playername = ((Player) entity).getName();
            String world = entity.getWorld().getUID().toString();
            Integer cx = entity.getLocation().getChunk().getX(), cz = entity.getLocation().getChunk().getZ();
            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                String evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                    String pvillage = serverdata.get("playerdata").get(playername).get("village").toString();
                    if (pvillage.equals(evillage)) {
                        if (serverdata.get("villages").get(pvillage).containsKey("pvp")) {
                            if (serverdata.get("villages").get(pvillage).get("pvp").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        //Always allow damage
                    } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
                        //Always allow damage
                    } else {
                        if (serverdata.get("villages").get(pvillage).containsKey("pvp")) {
                            if (serverdata.get("villages").get(pvillage).get("pvp").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                            event.setCancelled(true);
                        }
                    }
                } else {
                    if (serverdata.get("villages").get(evillage).containsKey("pvp")) {
                        if (serverdata.get("villages").get(evillage).get("pvp").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("dom")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("dom").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("doors")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Doors").equals("off")) {
                                        event.setCancelled(true);
                                    }
                                }
                            } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                                if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                                } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                            } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("doa")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("doa").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
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
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("doo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("doo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
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
                        } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("doo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("doo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "doors", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("bum")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("bum").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("buttons")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Buttons").equals("off")) {
                                        event.setCancelled(true);
                                    }
                                }
                            } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                                if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                                } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                            } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("bua")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("bua").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
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
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("buo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("buo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
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
                        } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("buo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("buo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "buttons", playername)) {
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
                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                        if (MainConversions.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                            String pvillage = serverdata.get("playerdata").get(playername).get("village").toString(), evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                            if (pvillage.equals(evillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("lem")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("lem").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
                                        if (((HashMap) serverdata.get("playerdata").get(tplayer).get("levers")).get(playername).equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (Config.getString("Player Plots.Members.Levers").equals("off")) {
                                        event.setCancelled(true);
                                    }
                                }
                            } else if (MainConversions.enemyEmpire(pvillage, evillage) || MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                                if (MainConversions.enemyEmpire(pvillage, evillage) && MainConversions.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
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
                                } else if (MainConversions.enemyEmpire(pvillage, evillage)) {
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
                            } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(evillage), "all", pvillage)) {
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("lea")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("lea").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
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
                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                                    String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                                    if (tplayer.equals(playername)) {
                                        //You own it so your good
                                    } else if (serverdata.get("playerdata").get(tplayer).containsKey("leo")) {
                                        if (serverdata.get("playerdata").get(tplayer).get("leo").equals("off")) {
                                            event.setCancelled(true);
                                        }
                                    } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
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
                        } else if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "playerplot")) {
                            String tplayer = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("playerplot").toString();
                            if (tplayer.equals(playername)) {
                                //You own it so your good
                            } else if (serverdata.get("playerdata").get(tplayer).containsKey("leo")) {
                                if (serverdata.get("playerdata").get(tplayer).get("leo").equals("off")) {
                                    event.setCancelled(true);
                                }
                            } else if (MainConversions.isPartInHashMap(serverdata.get("playerdata").get(tplayer), "levers", playername)) {
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
