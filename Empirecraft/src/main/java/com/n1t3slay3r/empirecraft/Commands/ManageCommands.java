/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Commands;

import com.n1t3slay3r.empirecraft.main.BuildRotationCheck;
import com.n1t3slay3r.empirecraft.main.Main;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
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
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import org.bukkit.World;
import org.bukkit.block.Block;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.WEST;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.Chest;

/**
 *
 * @author Dylan Malec
 */
public class ManageCommands {

    public static void buildInitiation(CommandSender sender, Player player, String playervillage, String[] args) {
        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("playerplot")) {
                    tempfile = new File(structureFolder, tempstring + ".yml");
                    if (tempfile.exists()) {
                        if (args[2].equalsIgnoreCase("N") || args[2].equalsIgnoreCase("E") || args[2].equalsIgnoreCase("S") || args[2].equalsIgnoreCase("W")) {
                            if (MainConversions.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
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
                                                if (MainConversions.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
                                                    econ.withdrawPlayer(player, Config.getInt("Village Structures." + tempstring + ".Creation Cost"));
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("str", tempstring);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                                    ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
                                                FileConfiguration tempyaml = new YamlConfiguration();
                                                try {
                                                    tempyaml.load(tempfile);
                                                } catch (IOException | InvalidConfigurationException ex) {
                                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                            if (!Config.getString("Village Structures." + tempstring + ".Type").equals("Multi")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            }
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
                                            FileConfiguration tempyaml = new YamlConfiguration();
                                            try {
                                                tempyaml.load(tempfile);
                                            } catch (IOException | InvalidConfigurationException ex) {
                                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                                if (MainConversions.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
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
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
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
                                        ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
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
                                            ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                            if (MainConversions.isNotLimitedStructure(tempstring, serverdata.get("villages").get(playervillage).get("vir").toString())) {
                                tempfile = new File(structureFolder, tempstring + ".yml");
                                if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {

                                    FileConfiguration tempyaml = new YamlConfiguration();
                                    try {
                                        tempyaml.load(tempfile);
                                    } catch (IOException | InvalidConfigurationException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (MainConversions.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
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
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                        ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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
                                        if (MainConversions.isMulti(player.getLocation().getChunk(), tempstring, tempyaml, args[2], playervillage)) {
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
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("con", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("hp", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("dir", args[2]);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cle", 1);
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("base", player.getLocation().getBlockY() + Config.getInt("Village Structures." + tempstring + ".Height Offset"));
                                            ManageCommands.build(args, player, tempyaml, playervillage, tempstring);
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

    public static void build(String[] args, Player player, FileConfiguration tempyaml, String playervillage, String structure) {
        if (args[2].equalsIgnoreCase("N")) {
            Block block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(tempyaml.getInt("Main Chest.X") + player.getLocation().getChunk().getX() * 16 - 1, tempyaml.getInt("Main Chest.Y") + player.getLocation().getBlockY() - 2, tempyaml.getInt("Main Chest.Z") + player.getLocation().getChunk().getZ() * 16 - 1);
            block.setType(Material.CHEST);
            Chest main = new Chest(0, block.getData());
            if (!MainConversions.isMultiType(structure)) {
                switch (tempyaml.getString("Scematic." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                }
            } else {
                switch (tempyaml.getString("Scematic.0.0." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                }
            }
            MainConversions.structureBookWriteUp(structure, block, tempyaml);
            if (Config.getInt("Village Settings.Destruction Delay") == 0) {
                int cy = 0;
                for (int y = player.getLocation().getBlockY(); y < (tempyaml.getConfigurationSection("Scematic").getKeys(false).size() + player.getLocation().getBlockY()); y++) {
                    cy++;
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".id")));
                                Material mat = block.getType();
                                BuildRotationCheck.Set("n", block, mat, cy, x, z, tempyaml);
                            } else if (!(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                if (!(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                    block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                    block.setType(AIR);
                                }
                            }
                        }
                    }
                }
                if (Config.getInt("Village Settings.Build Delay") == 0) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("con");
                    if (Config.isConfigurationSection("Village Ranks." + structure)) {
                        serverdata.get("villages").get(playervillage).replace("vir", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    } else {
                        if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                            if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal")) {
                                tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Income Timer"));
                            } else {
                                String m = Config.getStringList("Village Structures." + structure + ".Productions").get(0);
                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("pro", m);
                            }
                        }
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    }
                }
            }
        } else if (args[2].equalsIgnoreCase("E")) {
            Block block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(Math.abs(tempyaml.getInt("Main Chest.Z") - 16) + player.getLocation().getChunk().getX() * 16, tempyaml.getInt("Main Chest.Y") + player.getLocation().getBlockY() - 2, tempyaml.getInt("Main Chest.X") + player.getLocation().getChunk().getZ() * 16 - 1);
            block.setType(Material.CHEST);
            Chest main = new Chest(0, block.getData());
            if (!MainConversions.isMultiType(structure)) {
                switch (tempyaml.getString("Scematic." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                }
            } else {
                switch (tempyaml.getString("Scematic.0.0." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                }
            }
            MainConversions.structureBookWriteUp(structure, block, tempyaml);
            if (Config.getInt("Village Settings.Destruction Delay") == 0) {
                int cy = 0;
                for (int y = player.getLocation().getBlockY(); y < (tempyaml.getConfigurationSection("Scematic").getKeys(false).size() + player.getLocation().getBlockY()); y++) {
                    cy++;
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.Z") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == z)) {
                                block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + z + "." + Math.abs(x - 17) + ".id")));
                                Material mat = block.getType();
                                BuildRotationCheck.Set("e", block, mat, cy, x, z, tempyaml);
                            } else if (!(tempyaml.getInt("Main Chest.Z") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == z)) {
                                if (!(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                    block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                    block.setType(AIR);
                                }
                            }
                        }
                    }
                }
                if (Config.getInt("Village Settings.Build Delay") == 0) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("con");
                    if (Config.isConfigurationSection("Village Ranks." + structure)) {
                        serverdata.get("villages").get(playervillage).replace("vir", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    } else {
                        if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                            if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal")) {
                                tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Income Timer"));
                            } else {
                                String m = Config.getStringList("Village Structures." + structure + ".Productions").get(0);
                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("pro", m);
                            }
                        }
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    }
                }
            }
        } else if (args[2].equalsIgnoreCase("S")) {
            Block block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(16 - tempyaml.getInt("Main Chest.X") + player.getLocation().getChunk().getX() * 16, tempyaml.getInt("Main Chest.Y") + player.getLocation().getBlockY() - 2, 16 - tempyaml.getInt("Main Chest.Z") + player.getLocation().getChunk().getZ() * 16);
            block.setType(Material.CHEST);
            Chest main = new Chest(0, block.getData());
            if (!MainConversions.isMultiType(structure)) {
                switch (tempyaml.getString("Scematic." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                }
            } else {
                switch (tempyaml.getString("Scematic.0.0." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                }
            }
            MainConversions.structureBookWriteUp(structure, block, tempyaml);
            if (Config.getInt("Village Settings.Destruction Delay") == 0) {
                int cy = 0;
                for (int y = player.getLocation().getBlockY(); y < (tempyaml.getConfigurationSection("Scematic").getKeys(false).size() + player.getLocation().getBlockY()); y++) {
                    cy++;
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(17 - tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == z)) {
                                block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + (17 - x) + "." + (17 - z) + ".id")));
                                Material mat = block.getType();
                                BuildRotationCheck.Set("s", block, mat, cy, x, z, tempyaml);
                            } else if (!(17 - tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == z)) {
                                if (!(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                    block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                    block.setType(AIR);
                                }
                            }
                        }
                    }
                }
                if (Config.getInt("Village Settings.Build Delay") == 0) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("con");
                    if (Config.isConfigurationSection("Village Ranks." + structure)) {
                        serverdata.get("villages").get(playervillage).replace("vir", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    } else {
                        if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                            if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal")) {
                                tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Income Timer"));
                            } else {
                                String m = Config.getStringList("Village Structures." + structure + ".Productions").get(0);
                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("pro", m);
                            }
                        }
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    }
                }
            }
        } else {
            Block block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(tempyaml.getInt("Main Chest.Z") + player.getLocation().getChunk().getX() * 16 - 1, tempyaml.getInt("Main Chest.Y") + player.getLocation().getBlockY() - 2, Math.abs(tempyaml.getInt("Main Chest.X") - 16) + player.getLocation().getChunk().getZ() * 16);
            block.setType(Material.CHEST);
            Chest main = new Chest(0, block.getData());
            if (!MainConversions.isMultiType(structure)) {
                switch (tempyaml.getString("Scematic." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                }
            } else {
                switch (tempyaml.getString("Scematic.0.0." + tempyaml.getInt("Main Chest.Y") + "." + tempyaml.getInt("Main Chest.X") + "." + tempyaml.getInt("Main Chest.Z") + ".dat")) {
                    case "NORTH":
                        main.setFacingDirection(NORTH);
                        block.setData(main.getData());
                        break;
                    case "EAST":
                        main.setFacingDirection(EAST);
                        block.setData(main.getData());
                        break;
                    case "SOUTH":
                        main.setFacingDirection(SOUTH);
                        block.setData(main.getData());
                        break;
                    case "WEST":
                        main.setFacingDirection(WEST);
                        block.setData(main.getData());
                        break;
                }
            }
            MainConversions.structureBookWriteUp(structure, block, tempyaml);
            if (Config.getInt("Village Settings.Destruction Delay") == 0) {
                int cy = 0;
                for (int y = player.getLocation().getBlockY(); y < (tempyaml.getConfigurationSection("Scematic").getKeys(false).size() + player.getLocation().getBlockY()); y++) {
                    cy++;
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == z)) {
                                block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + Math.abs(z - 17) + "." + x + ".id")));
                                Material mat = block.getType();
                                BuildRotationCheck.Set("w", block, mat, cy, x, z, tempyaml);
                            } else if (!(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == z)) {
                                if (!(tempyaml.getInt("Main Chest.X") == x && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == z)) {
                                    block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(x + player.getLocation().getChunk().getX() * 16, y - 1, z + player.getLocation().getChunk().getZ() * 16);
                                    block.setType(AIR);
                                }
                            }
                        }
                    }
                }
                if (Config.getInt("Village Settings.Build Delay") == 0) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("con");
                    if (Config.isConfigurationSection("Village Ranks." + structure)) {
                        serverdata.get("villages").get(playervillage).replace("vir", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    } else {
                        if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                            if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal")) {
                                tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Income Timer"));
                            } else {
                                String m = Config.getStringList("Village Structures." + structure + ".Productions").get(0);
                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("pro", m);
                            }
                        }
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str") + ".Total Hp"));
                    }
                }
            }
        }
        player.sendMessage(ChatColor.BLUE + "The establishment of your structure " + ChatColor.AQUA + structure + ChatColor.BLUE + " has been successfully established");
    }
}
