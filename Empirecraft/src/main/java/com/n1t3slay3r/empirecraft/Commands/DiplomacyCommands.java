/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Commands;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Dylan Malec
 */
public class DiplomacyCommands {

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

    public static void RequestList(String section, String playersection, CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + "Ally Requests");
        if (serverdata.get(section).get(playersection).get("alr") != null) {
            if (serverdata.get(section).get(playersection).get("alr") != null) {
                tempstring = ChatColor.AQUA + "";
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get(section).get(playersection).get("alr"));
                ((ArrayList) serverdata.get(section).get(playersection).get("alr")).stream().map((s) -> {
                    tempstring += s;
                    return s;
                }).map((s) -> {
                    temparraylist.remove((String) s);
                    return s;
                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                });
                sender.sendMessage(tempstring);
            } else {
                sender.sendMessage(ChatColor.AQUA + "None");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "None");
        }
        sender.sendMessage(ChatColor.BLUE + "Truce Requests");
        if (serverdata.get(section).get(playersection).get("trr") != null) {
            if (serverdata.get(section).get(playersection).get("trr") != null) {
                tempstring = ChatColor.AQUA + "";
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get(section).get(playersection).get("trr"));
                ((ArrayList) serverdata.get(section).get(playersection).get("trr")).stream().map((s) -> {
                    tempstring += s;
                    return s;
                }).map((s) -> {
                    temparraylist.remove((String) s);
                    return s;
                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                });
                sender.sendMessage(tempstring);
            } else {
                sender.sendMessage(ChatColor.AQUA + "None");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "None");
        }
    }
}
