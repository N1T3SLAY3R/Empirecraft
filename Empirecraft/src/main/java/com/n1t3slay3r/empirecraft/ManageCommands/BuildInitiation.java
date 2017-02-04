/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.ManageCommands;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import com.n1t3slay3r.empirecraft.main.Main;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author dylan
 */
public class BuildInitiation {
    public static void BuildInitiation(CommandSender sender, Player player, String playervillage, String[] args) {
        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("playerplot")) {
                    tempfile = new File(structureFolder, tempstring + ".yml");
                    if (tempfile.exists()) {
                        if (args[2].equalsIgnoreCase("N") || args[2].equalsIgnoreCase("E") || args[2].equalsIgnoreCase("S") || args[2].equalsIgnoreCase("W")) {
                            if (QuickChecks.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
                                if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("str")) {
                                    if (Config.isConfigurationSection("Village Structures." + tempstring)) {
                                        if (!Config.isList("Village Structures." + tempstring + ".Upgraded From")) {
                                            if (econ.has(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"))) {
                                                FileConfiguration tempyaml = new YamlConfiguration();
                                                try {
                                                    tempyaml.load(tempfile);
                                                } catch (IOException | InvalidConfigurationException ex) {
                                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                if (QuickChecks.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
                                                    econ.withdrawPlayer(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"));
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                                    Build.Build(args, player, tempyaml, playervillage, tempstring);
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "The structure type " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", is a Multi structure type and therefore takes up multiple chunks to build which you currently dont claim or are being used as player plots/other structures");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "It costs $" + ChatColor.RED + Config.get("Village Structures." + tempstring + ".Creation Cost") + ChatColor.RED + " to create a " + ChatColor.DARK_RED + tempstring);
                                            }
                                        } else {
                                            String tempstring2 = ChatColor.RED + "";
                                            temparraylist.clear();
                                            temparraylist.addAll(Config.getStringList("Village Structures." + tempstring + ".Upgraded From"));
                                            for (String s : Config.getStringList("Village Structures." + tempstring + ".Upgraded From")) {
                                                tempstring2 += s;
                                                temparraylist.remove((String) s);
                                                if (!temparraylist.isEmpty()) {
                                                    tempstring2 += (ChatColor.DARK_RED + ", " + ChatColor.RED);
                                                }
                                            }
                                            sender.sendMessage(ChatColor.DARK_RED + "The structure " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " can only be upgraded from " + ChatColor.RED + tempstring2);
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You can only have 1 rank structure building in your village!");
                                    }
                                } else if (Config.isConfigurationSection("Village Ranks." + tempstring + ".Upgraded From")) {
                                    if (Config.getStringList("Village Ranks." + tempstring + ".Upgraded From").contains(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str").toString())) {
                                        int tempint = 1;
                                        if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                            tempint += ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).size();
                                        }
                                        if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                            tempint += ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).size();
                                        }
                                        if (tempint >= Config.getInt("Village Ranks." + tempstring + ".Creation Cost")) {
                                            if (econ.has(player, Config.getInt("Village Ranks." + tempstring + ".Creation Cost"))) {
                                                econ.withdrawPlayer(player, Config.getInt("Village Ranks." + tempstring + ".Creation Cost"));
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                                FileConfiguration tempyaml = new YamlConfiguration();
                                                try {
                                                    tempyaml.load(tempfile);
                                                } catch (IOException | InvalidConfigurationException ex) {
                                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                Build.Build(args, player, tempyaml, playervillage, tempstring);
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "It costs $" + ChatColor.RED + Config.get("Village Structures." + tempstring + ".Creation Cost") + ChatColor.RED + " to create a " + ChatColor.DARK_RED + tempstring);
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "The village rank/building " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " requires atleast " + ChatColor.RED + Config.getInt("Village Ranks." + tempstring + ".Creation Cost") + ChatColor.DARK_RED + " players to be in the village before upgrading, you currently only have " + ChatColor.RED + tempint);
                                        }
                                    } else {
                                        String tempstring2 = ChatColor.RED + "";
                                        temparraylist.clear();
                                        temparraylist.addAll(Config.getStringList("Village Ranks." + tempstring + ".Upgraded From"));
                                        for (String s : Config.getStringList("Village Ranks." + tempstring + ".Upgraded From")) {
                                            tempstring2 += s;
                                            temparraylist.remove((String) s);
                                            if (!temparraylist.isEmpty()) {
                                                tempstring2 += (ChatColor.DARK_RED + ", " + ChatColor.RED);
                                            }
                                        }
                                        sender.sendMessage(ChatColor.DARK_RED + tempstring + " is not upgraded from " + (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str")) + ", it is upgraded from " + tempstring2);
                                    }
                                } else if (Config.isList("Village Structures." + tempstring + ".Upgraded From")) {
                                    if (Config.getStringList("Village Structures." + tempstring + ".Upgraded From").contains(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str").toString())) {
                                        if (econ.has(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"))) {
                                            econ.withdrawPlayer(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"));
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                            if (!Config.getString("Village Structures." + tempstring + ".Type").equals("Multi")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            }
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                            FileConfiguration tempyaml = new YamlConfiguration();
                                            try {
                                                tempyaml.load(tempfile);
                                            } catch (IOException | InvalidConfigurationException ex) {
                                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            Build.Build(args, player, tempyaml, playervillage, tempstring);
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "It costs $" + ChatColor.RED + Config.get("Village Structures." + tempstring + ".Creation Cost") + ChatColor.RED + " to create a " + ChatColor.DARK_RED + tempstring);
                                        }
                                    } else {
                                        String tempstring2 = ChatColor.RED + "";
                                        temparraylist.clear();
                                        temparraylist.addAll(Config.getStringList("Village Structures." + tempstring + ".Upgraded From"));
                                        for (String s : Config.getStringList("Village Structures." + tempstring + ".Upgraded From")) {
                                            tempstring2 += s;
                                            temparraylist.remove((String) s);
                                            if (!temparraylist.isEmpty()) {
                                                tempstring2 += (ChatColor.DARK_RED + ", " + ChatColor.RED);
                                            }
                                        }
                                        sender.sendMessage(ChatColor.DARK_RED + tempstring + " is not upgraded from " + (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str")) + ", it is upgraded from " + tempstring2);
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "You can not build a new structure where an old one already exsists unless it is upgraded from it, which " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " is not");
                                }
                            } else {
                                String[] spl;
                                for (String s : Config.getStringList("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Structure Limits")) {
                                    spl = s.split(":");
                                    if (spl[0].equals(tempstring)) {
                                        sender.sendMessage(ChatColor.DARK_RED + "You are limited to only being able to build " + spl[1] + ChatColor.DARK_RED + tempstring + " structures, and your are currently at that limit");
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You must select a direction (N=North, E=East, S=South, W=West), this is the direction the building will be facing (Use F3 as a refrence/compass)");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "The structure name " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You cannot build a structure in a villager's claimed territory");
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You cannot build a structure in another villages territory");
            }
        } else if (((int) serverdata.get("villages").get(playervillage).get("plc")) == 0) {
            tempfile = new File(structureFolder, tempstring + ".yml");
            if (tempfile.exists()) {
                if (Config.isConfigurationSection("Village Ranks." + tempstring)) {
                    if (!Config.isConfigurationSection("Village Ranks." + tempstring + ".Upgraded From")) {
                        if (args[2].equalsIgnoreCase("N") || args[2].equalsIgnoreCase("E") || args[2].equalsIgnoreCase("S") || args[2].equalsIgnoreCase("W")) {
                            if (econ.has(player, Config.getInt("Village Ranks." + tempstring + ".Creation Cost"))) {
                                if (QuickChecks.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
                                    if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
                                        econ.withdrawPlayer(player, Config.getInt("Village Ranks." + tempstring + ".Creation Cost"));
                                        serverdata.get("villages").get(playervillage).put("world", player.getWorld().getUID().toString());
                                        serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                        if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()) == null) {
                                            serverdata.get("worldmap").put(player.getWorld().getUID().toString(), new HashMap<>());
                                        }
                                        if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                            serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                        }
                                        ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Ranks." + tempstring + ".Height Offset"));
                                        serverdata.get("villages").get(playervillage).put("rcw", player.getWorld().getUID().toString());
                                        serverdata.get("villages").get(playervillage).put("rcx", player.getLocation().getBlockX());
                                        serverdata.get("villages").get(playervillage).put("rcy", player.getLocation().getBlockY());
                                        serverdata.get("villages").get(playervillage).put("rcz", player.getLocation().getBlockZ());
                                        FileConfiguration tempyaml = new YamlConfiguration();
                                        try {
                                            tempyaml.load(tempfile);
                                        } catch (IOException | InvalidConfigurationException ex) {
                                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        Build.Build(args, player, tempyaml, playervillage, tempstring);
                                    } else {
                                        Boolean cont = true;
                                        World world = Bukkit.getWorld(player.getWorld().getUID());
                                        for (int y = 1; y < world.getMaxHeight(); y++) {
                                            for (int x = 0; x < 16; x++) {
                                                for (int z = 0; z < 16; z++) {
                                                    Vector vector = new Vector(x + player.getLocation().getChunk().getX() * 16, y, z + player.getLocation().getChunk().getZ() * 16);
                                                    ApplicableRegionSet set = WGBukkit.getRegionManager(world).getApplicableRegions(vector);
                                                    int tempint = 0;
                                                    if (set.size() > 0) {
                                                        for (ProtectedRegion r : set) {
                                                            if (Config.getStringList("Village Settings.Regions To Ignore").contains(r.getId())) {
                                                                tempint++;
                                                            }
                                                        }
                                                        if (set.size() != tempint) {
                                                            cont = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (cont) {
                                            econ.withdrawPlayer(player, Config.getInt("Village Ranks." + tempstring + ".Creation Cost"));
                                            serverdata.get("villages").get(playervillage).put("world", player.getWorld().getUID().toString());
                                            serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                            if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()) == null) {
                                                serverdata.get("worldmap").put(player.getWorld().getUID().toString(), new HashMap<>());
                                            }
                                            if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                                serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                            }
                                            ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Ranks." + tempstring + ".Height Offset"));
                                            serverdata.get("villages").get(playervillage).put("rcw", player.getWorld().getUID().toString());
                                            serverdata.get("villages").get(playervillage).put("rcx", player.getLocation().getBlockX());
                                            serverdata.get("villages").get(playervillage).put("rcy", player.getLocation().getBlockY());
                                            serverdata.get("villages").get(playervillage).put("rcz", player.getLocation().getBlockZ());
                                            FileConfiguration tempyaml = new YamlConfiguration();
                                            try {
                                                tempyaml.load(tempfile);
                                            } catch (IOException | InvalidConfigurationException ex) {
                                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            Build.Build(args, player, tempyaml, playervillage, tempstring);
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot found your rank structure on top of a world gaurd region");
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "You are restricted to not being able to build the structure " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " as your current village rank");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "It costs $" + ChatColor.RED + Config.get("Village Ranks." + tempstring + ".Creation Cost") + ChatColor.RED + " to create a " + ChatColor.DARK_RED + tempstring);
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You must select a direction (N=North, E=East, S=South, W=West), this is the direction the building will be facing (Use F3 as a refrence/compass)");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "The structure " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " can only be upgraded from " + ChatColor.RED + Config.get("Village Ranks." + tempstring + ".Upgraded From"));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You must create a structure that is a village rank ");
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "The structure name " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
            }
        } else if (Config.isConfigurationSection("Village Structures." + tempstring)) {
            if (Config.getString("Village Structures." + tempstring + ".Type").equals("Camp")) {
                if (args[2].equalsIgnoreCase("N") || args[2].equalsIgnoreCase("E") || args[2].equalsIgnoreCase("S") || args[2].equalsIgnoreCase("W")) {
                    if (Integer.parseInt(serverdata.get("villages").get(playervillage).get("plc").toString()) < Config.getInt("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Max Plots")) {
                        if (econ.has(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"))) {
                            if (QuickChecks.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
                                tempfile = new File(structureFolder, tempstring + ".yml");
                                if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {

                                    FileConfiguration tempyaml = new YamlConfiguration();
                                    try {
                                        tempyaml.load(tempfile);
                                    } catch (IOException | InvalidConfigurationException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (QuickChecks.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
                                        econ.withdrawPlayer(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"));
                                        serverdata.get("villages").get(playervillage).put("world", player.getWorld().getUID().toString());
                                        serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                        if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()) == null) {
                                            serverdata.get("worldmap").put(player.getWorld().getUID().toString(), new HashMap<>());
                                        }
                                        if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                            serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                        }
                                        ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                        Build.Build(args, player, tempyaml, playervillage, tempstring);
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "The structure type " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", is a Multi structure type and therefore takes up multiple chunks to build which you currently dont claim or are being used as player plots/other structures");
                                    }
                                } else {
                                    Boolean cont = true;
                                    World world = Bukkit.getWorld(player.getWorld().getUID());
                                    for (int y = 1; y < world.getMaxHeight(); y++) {
                                        for (int x = 0; x < 16; x++) {
                                            for (int z = 0; z < 16; z++) {
                                                Vector vector = new Vector(x + player.getLocation().getChunk().getX() * 16, y, z + player.getLocation().getChunk().getZ() * 16);
                                                ApplicableRegionSet set = WGBukkit.getRegionManager(world).getApplicableRegions(vector);
                                                int tempint = 0;
                                                if (set.size() > 0) {
                                                    for (ProtectedRegion r : set) {
                                                        if (Config.getStringList("Village Settings.Regions To Ignore").contains(r.getId())) {
                                                            tempint++;
                                                        }
                                                    }
                                                    if (set.size() != tempint) {
                                                        cont = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (cont) {
                                        FileConfiguration tempyaml = new YamlConfiguration();
                                        try {
                                            tempyaml.load(tempfile);
                                        } catch (IOException | InvalidConfigurationException ex) {
                                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (QuickChecks.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
                                            econ.withdrawPlayer(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"));
                                            serverdata.get("villages").get(playervillage).put("world", player.getWorld().getUID().toString());
                                            serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                            if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()) == null) {
                                                serverdata.get("worldmap").put(player.getWorld().getUID().toString(), new HashMap<>());
                                            }
                                            if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                                serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                            }
                                            ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 0);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 0);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                            Build.Build(args, player, tempyaml, playervillage, tempstring);
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "The structure type " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", is a Multi structure type and therefore takes up multiple chunks to build which you currently dont claim or are being used as player plots/other structures");
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot found your camp structure on top of a world gaurd region");
                                    }
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "You are restricted to not being able to build the structure " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " as your current village rank");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "It costs $" + ChatColor.RED + Config.get("Village Structures." + tempstring + ".Creation Cost") + ChatColor.RED + " to create a " + ChatColor.DARK_RED + tempstring);
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You already have the max claimed plots of " + ChatColor.DARK_RED + serverdata.get("villages").get(playervillage).get("plc") + ChatColor.DARK_RED + " claimed, a camp strucutre requires the ability to claim an unclaimed chunk of land");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "You must select a direction (N=North, E=East, S=South, W=West), this is the direction the building will be facing (Use F3 as a refrence/compass)");
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You can only build structures on your village plotland, with the exception to camps");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You can only build structures on your village plotland, with the exception to camps");
        }
    }
}
