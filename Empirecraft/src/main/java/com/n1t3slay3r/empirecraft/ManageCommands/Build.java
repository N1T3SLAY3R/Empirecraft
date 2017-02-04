/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.ManageCommands;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import com.n1t3slay3r.empirecraft.Uncategorized.StructureBookWriteUp;
import com.n1t3slay3r.empirecraft.main.BuildRotationCheck;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import org.bukkit.block.Block;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.WEST;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.Chest;

/**
 *
 * @author dylan
 */
public class Build {
    public static void Build(String[] args, Player player, FileConfiguration tempyaml, String playervillage, String structure) {
        if (args[2].equalsIgnoreCase("N")) {
            Block block = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(tempyaml.getInt("Main Chest.X") + player.getLocation().getChunk().getX() * 16 - 1, tempyaml.getInt("Main Chest.Y") + player.getLocation().getBlockY() - 2, tempyaml.getInt("Main Chest.Z") + player.getLocation().getChunk().getZ() * 16 - 1);
            block.setType(Material.CHEST);
            Chest main = new Chest(0, block.getData());
            if (!QuickChecks.isMultiType(structure)) {
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
            StructureBookWriteUp.structureBookWriteUp(structure, block, tempyaml);
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
            if (!QuickChecks.isMultiType(structure)) {
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
            StructureBookWriteUp.structureBookWriteUp(structure, block, tempyaml);
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
            if (!QuickChecks.isMultiType(structure)) {
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
            StructureBookWriteUp.structureBookWriteUp(structure, block, tempyaml);
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
            if (!QuickChecks.isMultiType(structure)) {
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
            StructureBookWriteUp.structureBookWriteUp(structure, block, tempyaml);
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
