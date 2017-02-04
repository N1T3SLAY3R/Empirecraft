/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author dylan
 */
public class OnPlayerChat implements Listener{
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
         if (QuickChecks.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village"))) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "vmal":
         if (QuickChecks.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village"))) {
         event.getRecipients().remove(p);
         } else if (!QuickChecks.isPlayerInArrayList(serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()), "man", player.getUniqueId().toString())) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "vally":
         if (QuickChecks.isPlayerInVillage(player.getUniqueId())) {
         if (!serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").equals(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village")) && !QuickChecks.isPlayerInArrayList(serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()), "all", serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString())) {
         event.getRecipients().remove(p);
         }
         } else {
         event.getRecipients().remove(p);
         }
         break;
         case "eal":
         if (QuickChecks.isPlayerInVillage(player.getUniqueId())) {
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
         if (QuickChecks.isPlayerInVillage(player.getUniqueId())) {
         if (!QuickChecks.isVillageAlliedOrYoursEmpireWise(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString(), serverdata.get("villages").get(serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString()).get("emp").toString())) {
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
}
