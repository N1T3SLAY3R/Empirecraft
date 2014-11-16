/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

import com.n1t3slay3r.empirecraft.Commands.MainConversions;
import com.n1t3slay3r.empirecraft.Commands.OwnerCommands;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.BEDROCK;
import static org.bukkit.Material.LADDER;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.REDSTONE_TORCH_OFF;
import static org.bukkit.Material.REDSTONE_TORCH_ON;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.STEP;
import static org.bukkit.Material.TORCH;
import static org.bukkit.Material.WOOD;
import static org.bukkit.Material.WOOD_STEP;
import static org.bukkit.Material.WOOL;
import org.bukkit.SandstoneType;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 *
 * @author Dylan Malec
 */
public class RepetitiveMethods {

    private static Long time;
    private static Integer taxes;
    private static Block block;

    public static void test(Plugin plugin) {
        //Blocks that might not have the right direction (ex. stairs)
        ArrayList<String> blocks = new ArrayList<>();
        blocks.add("ACACIA_STAIRS");
        blocks.add("BIRCH_WOOD_STAIRS");
        blocks.add("BRICK_STAIRS");
        blocks.add("COBBLESTONE_STAIRS");
        blocks.add("JUNGLE_WOOD_STAIRS");
        blocks.add("NETHER_BRICK_STAIRS");
        blocks.add("QUARTZ_STAIRS");
        blocks.add("SANDSTONE_STAIRS");
        blocks.add("SANDSTONE");
        blocks.add("SMOOTH_STAIRS");
        blocks.add("SPRUCE_WOOD_STAIRS");
        blocks.add("WOOD_STAIRS");
        blocks.add("CHEST");
        blocks.add("ACTIVATOR_RAIL");
        blocks.add("POWERED_RAIL");
        blocks.add("DROPPER");
        blocks.add("DISPENSER");
        blocks.add("WOOD");
        blocks.add("WOOL");
        blocks.add("LOG");
        blocks.add("DETECTOR_RAIL");
        blocks.add("PISTON_BASE");
        blocks.add("PISTON_STICKY_BASE");
        blocks.add("STEP");
        blocks.add("WOOD_STEP");
        blocks.add("TORCH");
        blocks.add("REDSTONE_TORCH_OFF");
        blocks.add("REDSTONE_TORCH_ON");
        blocks.add("LADDER");
        blocks.add("FURNACE");
        blocks.add("BURNING_FURNACE");
        blocks.add("RAILS");
        blocks.add("PUMPKIN");
        blocks.add("JACK_O_LANTERN");
        blocks.add("FENCE_GATE");
        blocks.add("BED");
        blocks.add("BED_BLOCK");
        ArrayList<Player> tempplist = new ArrayList<>();
        Long build = Config.getLong("Village Settings.Build Delay"), destr = Config.getLong("Village Settings.Destruction Delay"), income = Config.getLong("Village Settings.Structure Income Delay"), tax = Config.getLong("Village Settings.Tax Delay"), tax2 = Config.getLong("Empire Settings.Tax Delay"), save = Config.getLong("Global Settings.Auto Save Interval");
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            time = System.currentTimeMillis() / 1000;
            for (String w : serverdata.get("worldmap").keySet()) {
                for (Object x : serverdata.get("worldmap").get(w).keySet()) {
                    for (Object z : ((HashMap) serverdata.get("worldmap").get(w).get(x)).keySet()) {
                        //DESTRUCTION/CLEARING OF LAND SYSTEM
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("cle")) {
                            if (destr != 0) {
                                if (time % destr == 0) {
                                    tempfile = new File(structureFolder, ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString() + ".yml");
                                    FileConfiguration tempyaml = new YamlConfiguration();
                                    try {
                                        tempyaml.load(tempfile);
                                    } catch (IOException | InvalidConfigurationException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    Integer nx = (Integer) x * 16 - 1, ny = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getConfigurationSection("Scematic").getKeys(false).size() - 2, nz = (Integer) z * 16;
                                    Boolean fin = false;
                                    Block chest;
                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.X") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.Z") - 1);
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + Math.abs(tempyaml.getInt("Main Chest.Z") - 16), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.X") - 1);
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + 16 - tempyaml.getInt("Main Chest.X"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + 16 - tempyaml.getInt("Main Chest.Z"));
                                    } else {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.Z") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + Math.abs(tempyaml.getInt("Main Chest.X") - 16));
                                    }
                                    Material mat = AIR;
                                    do {
                                        nx++;
                                        if (nx - 16 == (Integer) x * 16) {
                                            nx -= 16;
                                            nz++;
                                            if (nz - 16 == (Integer) z * 16) {
                                                nz -= 16;
                                                ny--;
                                            }
                                        }
                                        if (ny < ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 1) {
                                            fin = true;
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                            mat = Material.getMaterial(tempyaml.getString("Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nx - (Integer) x * 16 + 1) + "." + (nz - (Integer) z * 16 + 1) + ".id"));
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                            mat = Material.getMaterial(tempyaml.getString("Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nz - (Integer) z * 16 + 1) + "." + Math.abs(nx - 16 - (Integer) x * 16) + ".id"));
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                            mat = Material.getMaterial(tempyaml.getString("Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (16 - (nx - (Integer) x * 16)) + "." + (16 - (nz - (Integer) z * 16)) + ".id"));
                                        } else {
                                            mat = Material.getMaterial(tempyaml.getString("Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + Math.abs(nz - 16 - (Integer) z * 16) + "." + (nx - (Integer) x * 16 + 1) + ".id"));
                                        }
                                    } while (((Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(mat) && !blocks.contains(mat.toString())) || Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType() == AIR || Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).equals(chest)) && !fin);
                                    if (fin) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("cle");
                                        if (Config.getInt("Village Settings.Build Delay") == 0) {
                                            int cy = 0;
                                            for (int y = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")); y < ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getConfigurationSection("Scematic").getKeys(false).size() - 2; y++) {
                                                cy++;
                                                for (nx = 1; nx < 17; nx++) {
                                                    for (nz = 1; nz < 17; nz++) {
                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + nx + "." + nz + ".id")));
                                                                mat = block.getType();
                                                                BuildRotationCheck.Set("n", block, mat, cy, nx, nz, tempyaml);
                                                            } else if (!(tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.Z") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + nz + "." + Math.abs(nx - 17) + ".id")));
                                                                mat = block.getType();
                                                                BuildRotationCheck.Set("e", block, mat, cy, nx, nz, tempyaml);
                                                            } else if (!(tempyaml.getInt("Main Chest.Z") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(17 - tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + (17 - nx) + "." + (17 - nz) + ".id")));
                                                                mat = block.getType();
                                                                BuildRotationCheck.Set("s", block, mat, cy, nx, nz, tempyaml);
                                                            } else if (!(17 - tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + Math.abs(nz - 17) + "." + nx + ".id")));
                                                                mat = block.getType();
                                                                BuildRotationCheck.Set("w", block, mat, cy, nx, nz, tempyaml);
                                                            } else if (!(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("con");
                                            if (Config.isConfigurationSection("Village Ranks." + structure)) {
                                                serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).replace("vir", structure);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            } else {
                                                if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                                                    tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Income Timer"));
                                                }
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            }
                                        }
                                    } else if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType() != BEDROCK) {
                                        Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).breakNaturally();
                                    } else {
                                        Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).setType(AIR);
                                    }
                                }
                            }
                            //IF NOT CLEARING CHECK FOR BUILDING/CONSTRUCTION
                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("con")) {
                            if (build != 0) {
                                if (time % build == 0) {
                                    tempfile = new File(structureFolder, ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString() + ".yml");
                                    FileConfiguration tempyaml = new YamlConfiguration();
                                    try {
                                        tempyaml.load(tempfile);
                                    } catch (IOException | InvalidConfigurationException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    Integer nx = 1, ny = 1, nz = 0;
                                    Boolean fin = false, cont = false, not = false;
                                    Material mat = null;
                                    Block chest;
                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.X") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.Z") - 1);
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + Math.abs(tempyaml.getInt("Main Chest.Z") - 16), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.X") - 1);
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + 16 - tempyaml.getInt("Main Chest.X"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + 16 - tempyaml.getInt("Main Chest.Z"));
                                    } else {
                                        chest = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.Z") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + Math.abs(tempyaml.getInt("Main Chest.X") - 16));
                                    }
                                    do {
                                        nz++;
                                        if (nz > 16) {
                                            nz = 1;
                                            nx++;
                                            if (nx > 16) {
                                                nx = 1;
                                                ny++;
                                            }
                                        }
                                        if (ny > tempyaml.getConfigurationSection("Scematic").getKeys(false).size()) {
                                            fin = true;
                                        } else if (!fin) {
                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                mat = Material.getMaterial(tempyaml.getString("Scematic." + ny + "." + nx + "." + nz + ".id"));
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                mat = Material.getMaterial(tempyaml.getString("Scematic." + ny + "." + nz + "." + Math.abs(nx - 17) + ".id"));
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                mat = Material.getMaterial(tempyaml.getString("Scematic." + ny + "." + (17 - nx) + "." + (17 - nz) + ".id"));
                                            } else {
                                                mat = Material.getMaterial(tempyaml.getString("Scematic." + ny + "." + Math.abs(nz - 17) + "." + nx + ".id"));
                                            }
                                            if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1).getType().equals(mat)) {
                                                if (Config.getString("Village Settings.Require Materials To Build").equals("on")) {
                                                    if (mat == WOOL || mat == SANDSTONE || mat == WOOD || mat == LOG || mat == STEP || mat == WOOD_STEP) {
                                                        ItemStack stack = null;
                                                        int xx = nx, zz = nz;
                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                            xx = nz;
                                                            zz = Math.abs(nx - 17);
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                            xx = (17 - nx);
                                                            zz = (17 - nz);
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("w")) {
                                                            xx = Math.abs(nz - 17);
                                                            zz = nx;
                                                        }
                                                        if (mat == WOOL) {
                                                            switch (tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".typ")) {
                                                                case "BLACK":
                                                                    stack = new ItemStack(mat, 1, DyeColor.BLACK.getWoolData());
                                                                    break;
                                                                case "BLUE":
                                                                    stack = new ItemStack(mat, 1, DyeColor.BLUE.getWoolData());
                                                                    break;
                                                                case "BROWN":
                                                                    stack = new ItemStack(mat, 1, DyeColor.BROWN.getWoolData());
                                                                    break;
                                                                case "CYAN":
                                                                    stack = new ItemStack(mat, 1, DyeColor.CYAN.getWoolData());
                                                                    break;
                                                                case "GRAY":
                                                                    stack = new ItemStack(mat, 1, DyeColor.GRAY.getWoolData());
                                                                    break;
                                                                case "GREEN":
                                                                    stack = new ItemStack(mat, 1, DyeColor.GREEN.getWoolData());
                                                                    break;
                                                                case "LIGHT_BLUE":
                                                                    stack = new ItemStack(mat, 1, DyeColor.LIGHT_BLUE.getWoolData());
                                                                    break;
                                                                case "LIME":
                                                                    stack = new ItemStack(mat, 1, DyeColor.LIME.getWoolData());
                                                                    break;
                                                                case "MAGENTA":
                                                                    stack = new ItemStack(mat, 1, DyeColor.MAGENTA.getWoolData());
                                                                    break;
                                                                case "ORANGE":
                                                                    stack = new ItemStack(mat, 1, DyeColor.ORANGE.getWoolData());
                                                                    break;
                                                                case "PINK":
                                                                    stack = new ItemStack(mat, 1, DyeColor.PINK.getWoolData());
                                                                    break;
                                                                case "PURPLE":
                                                                    stack = new ItemStack(mat, 1, DyeColor.PURPLE.getWoolData());
                                                                    break;
                                                                case "RED":
                                                                    stack = new ItemStack(mat, 1, DyeColor.RED.getWoolData());
                                                                    break;
                                                                case "SILVER":
                                                                    stack = new ItemStack(mat, 1, DyeColor.SILVER.getWoolData());
                                                                    break;
                                                                case "WHITE":
                                                                    stack = new ItemStack(mat, 1, DyeColor.WHITE.getWoolData());
                                                                    break;
                                                                case "YELLOW":
                                                                    stack = new ItemStack(mat, 1, DyeColor.YELLOW.getWoolData());
                                                                    break;
                                                            }
                                                        } else if (mat == SANDSTONE) {
                                                            switch (tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".typ")) {
                                                                case "CRACKED":
                                                                    stack = new ItemStack(mat, 1, SandstoneType.CRACKED.getData());
                                                                    break;
                                                                case "GLYPHED":
                                                                    stack = new ItemStack(mat, 1, SandstoneType.GLYPHED.getData());
                                                                    break;
                                                                case "SMOOTH":
                                                                    stack = new ItemStack(mat, 1, SandstoneType.SMOOTH.getData());
                                                                    break;
                                                            }
                                                        } else if (mat == STEP) {
                                                            switch (tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".typ")) {
                                                                case "COBBLESTONE":
                                                                    stack = new ItemStack(mat, 1, Material.COBBLESTONE.getMaxDurability());
                                                                    break;
                                                                case "STONE":
                                                                    stack = new ItemStack(mat, 1, Material.STONE.getMaxDurability());
                                                                    break;
                                                                case "SANDSTONE":
                                                                    stack = new ItemStack(mat, 1, Material.SANDSTONE.getMaxDurability());
                                                                    break;
                                                                case "BRICK":
                                                                    stack = new ItemStack(mat, 1, Material.BRICK.getMaxDurability());
                                                                    break;
                                                                case "SMOOTH_BRICK":
                                                                    stack = new ItemStack(mat, 1, Material.SMOOTH_BRICK.getMaxDurability());
                                                                    break;
                                                                case "QUARTZ":
                                                                    stack = new ItemStack(mat, 1, Material.QUARTZ.getMaxDurability());
                                                                    break;
                                                                case "NETHER_BRICK":
                                                                    stack = new ItemStack(mat, 1, Material.NETHER_BRICK.getMaxDurability());
                                                                    break;
                                                            }
                                                        } else {
                                                            switch (tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".typ")) {
                                                                case "ACACIA":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.ACACIA.getData());
                                                                    break;
                                                                case "BIRCH":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.BIRCH.getData());
                                                                    break;
                                                                case "DARK_OAK":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.DARK_OAK.getData());
                                                                    break;
                                                                case "GENERIC":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.GENERIC.getData());
                                                                    break;
                                                                case "JUNGLE":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.JUNGLE.getData());
                                                                    break;
                                                                case "REDWOOD":
                                                                    stack = new ItemStack(mat, 1, TreeSpecies.REDWOOD.getData());
                                                                    break;
                                                            }
                                                        }
                                                        if (((InventoryHolder) chest.getState()).getInventory().containsAtLeast(stack, 1)) {
                                                            ((InventoryHolder) chest.getState()).getInventory().removeItem(stack);
                                                            cont = true;
                                                        } else {
                                                            not = true;
                                                        }
                                                    } else {
                                                        Boolean cancel = false;
                                                        if (mat == TORCH || mat == REDSTONE_TORCH_OFF || mat == REDSTONE_TORCH_ON || mat == LADDER) {
                                                            int xx = nx, zz = nz, dir = 0, cx = -1, cz = -1;
                                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                                dir = 1;
                                                                xx = nz;
                                                                zz = Math.abs(nx - 17);
                                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                                dir = 2;
                                                                xx = (17 - nx);
                                                                zz = (17 - nz);
                                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("w")) {
                                                                dir = 3;
                                                                xx = Math.abs(nz - 17);
                                                                zz = nx;
                                                            }
                                                            switch (tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".dat")) {
                                                                case "NORTH":
                                                                    if (dir == 0) {
                                                                        cz = 0;
                                                                    } else if (dir == 1) {
                                                                        cx = -2;
                                                                    } else if (dir == 2) {
                                                                        cz = -2;
                                                                    } else {
                                                                        cx = 0;
                                                                    }
                                                                    if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz + cz).getType().equals(AIR)) {
                                                                        cancel = true;
                                                                    }
                                                                    break;
                                                                case "EAST":
                                                                    if (dir == 0) {
                                                                        cx = -2;
                                                                    } else if (dir == 1) {
                                                                        cz = -2;
                                                                    } else if (dir == 2) {
                                                                        cx = 0;
                                                                    } else {
                                                                        cz = 0;
                                                                    }
                                                                    if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz + cz).getType().equals(AIR)) {
                                                                        cancel = true;
                                                                    }
                                                                    break;
                                                                case "SOUTH":
                                                                    if (dir == 0) {
                                                                        cz = -2;
                                                                    } else if (dir == 1) {
                                                                        cx = 0;
                                                                    } else if (dir == 2) {
                                                                        cz = 0;
                                                                    } else {
                                                                        cx = -2;
                                                                    }
                                                                    if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz + cz).getType().equals(AIR)) {
                                                                        cancel = true;
                                                                    }
                                                                    break;
                                                                case "WEST":
                                                                    if (dir == 0) {
                                                                        cx = 0;
                                                                    } else if (dir == 1) {
                                                                        cz = 0;
                                                                    } else if (dir == 2) {
                                                                        cx = -2;
                                                                    } else {
                                                                        cz = -2;
                                                                    }
                                                                    if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz + cz).getType().equals(AIR)) {
                                                                        cancel = true;
                                                                    }
                                                                    break;
                                                            }
                                                        }
                                                        if (!cancel) {
                                                            if (((InventoryHolder) chest.getState()).getInventory().contains(mat)) {
                                                                ((InventoryHolder) chest.getState()).getInventory().removeItem(new ItemStack(mat, 1));
                                                                cont = true;
                                                            } else {
                                                                for (String s : ((ArrayList<String>) tempHashMap.get("ble").get("a"))) {
                                                                    String[] blo = s.split(":");
                                                                    if (blo[0].equals(mat.toString()) || blo[1].equals(mat.toString())) {
                                                                        if (((InventoryHolder) chest.getState()).getInventory().contains(Material.getMaterial(blo[0]))) {
                                                                            ((InventoryHolder) chest.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(blo[0]), 1));
                                                                            cont = true;
                                                                        } else if (((InventoryHolder) chest.getState()).getInventory().contains(Material.getMaterial(blo[1]))) {
                                                                            ((InventoryHolder) chest.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(blo[1]), 1));
                                                                            cont = true;
                                                                        }
                                                                    }
                                                                }
                                                                if (!cont) {
                                                                    not = true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    cont = true;
                                                }
                                            }
                                        }
                                    } while (!cont && !fin);
                                    if (fin && !not) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("con");
                                        if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"))) {
                                            tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Income Timer"));
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                        } else {
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).put("vir", ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"));
                                        }
                                    } else if (cont) {
                                        if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"))) {
                                            if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + mat) <= Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + mat));
                                            }
                                        } else {
                                            if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + mat) <= Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + mat));
                                            }
                                        }
                                        Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1).setType(mat);
                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                            BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, nx, nz, tempyaml);
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                            BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, nz, Math.abs(nx - 17), tempyaml);
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                            BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, 17 - nx, 17 - nz, tempyaml);
                                        } else {
                                            BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, Math.abs(nz - 17), nx, tempyaml);
                                        }
                                    }
                                }
                            }
                            //CHECK FOR DAMAGED STRUCTURES
                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("hp")) {
                            String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                            tempfile = new File(structureFolder, structure + ".yml");
                            FileConfiguration tempyaml = new YamlConfiguration();
                            try {
                                tempyaml.load(tempfile);
                            } catch (IOException | InvalidConfigurationException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.X"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.Z"));
                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.Z"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + Math.abs(tempyaml.getInt("Main Chest.X") - 17));
                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + 17 - tempyaml.getInt("Main Chest.X"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + 17 - tempyaml.getInt("Main Chest.Z"));
                            } else {
                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + Math.abs(tempyaml.getInt("Main Chest.Z") - 17), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.X"));
                            }
                            Boolean repaired = false;
                            if (Config.isConfigurationSection("Village Ranks." + structure)) {
                                if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp").equals(Config.get("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"))) {
                                    for (String p : Config.getStringList("Village Ranks." + structure + ".Block Hp")) {
                                        if (!repaired) {
                                            String[] req = p.split(":");
                                            if (((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                                if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]) <= Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]));
                                                } else {
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp."));
                                                }
                                                repaired = true;
                                            }
                                        }
                                    }
                                }
                            } else if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp").equals(Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"))) {
                                for (ItemStack i : ((InventoryHolder) block.getState()).getInventory().getContents()) {
                                    if (!repaired) {
                                        for (String p : Config.getStringList("Village Structures." + structure + ".Block Hp")) {
                                            String[] req = p.split(":");
                                            if (((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                                if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]) <= Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]));
                                                } else {
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp."));
                                                }
                                                repaired = true;
                                            }
                                        }
                                    }
                                }
                            }
                            //ARCHER TOWERS
                            if (Config.isConfigurationSection("Village Structures." + structure)) {
                                if (Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                                    String village = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString();
                                    tempplist.clear();
                                    Bukkit.getWorld(UUID.fromString(w)).getPlayers().stream().filter((p) -> (abs(p.getLocation().getChunk().getX() + p.getLocation().getChunk().getZ() - (Integer) x - (Integer) z) <= Config.getInt("Village Structures." + structure + ".Range"))).filter((p) -> (!p.isDead() && MainConversions.isPlayerInVillage(p.getUniqueId()))).forEach((p) -> {
                                        String tempvil = serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString();
                                        if (MainConversions.isPartInHashMap(serverdata.get("villages").get(village), "ene", tempvil) || MainConversions.enemyEmpire(village, tempvil)) {
                                            tempplist.add(p);
                                        }
                                    });
                                    if (!tempplist.isEmpty() && ((Integer) serverdata.get("villages").get(village).get("vau")) >= Config.getInt("Village Structures." + structure + ".Upkeep")) {
                                        Boolean cont = true;
                                        if (Config.isSet("Village Structures." + structure + ".Required Materials")) {
                                            for (String s : Config.getStringList("Village Structures." + structure + ".Required Materials")) {
                                                String[] req = s.split(":");
                                                if (!((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                    cont = false;
                                                }
                                            }
                                        }
                                        if (cont) {
                                            for (String s : Config.getStringList("Village Structures." + structure + ".Required Materials")) {
                                                String[] req = s.split(":");
                                                ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                            }
                                            serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                            Double n = random() * tempplist.size();
                                            Location p = tempplist.get(n.intValue()).getLocation();
                                            Location pla = new Location(Bukkit.getWorld(UUID.fromString(w)), p.getX(), p.getY() + 1, p.getZ());
                                            Location ini = new Location(Bukkit.getWorld(UUID.fromString(w)), block.getX(), block.getY() + 1, block.getZ());
                                            Vector velocity = new Vector(pla.getX() - ini.getX(), pla.getY() - ini.getY(), pla.getZ() - ini.getZ());
                                            velocity = new Vector(pla.getX() - ini.getX(), pla.getY() - ini.getY() + (velocity.normalize().getY() / (pow(Config.getInt("Village Structures." + structure + ".Arrow Speed"), 2) * 100)), pla.getZ() - ini.getZ());
                                            Arrow a;
                                            for (int an = 0; an < Config.getInt("Village Structures." + structure + ".Arrows Fired"); an++) {
                                                a = Bukkit.getWorld(UUID.fromString(w)).spawnArrow(ini, velocity, (float) (Config.getInt("Village Structures." + structure + ".Arrow Speed")), (float) Config.getInt("Village Structures." + structure + ".Arrow Spread"));
                                                a.setBounce(Config.getBoolean("Village Structures." + structure + ".Bounce"));
                                                a.setCritical(Config.getBoolean("Village Structures." + structure + ".Critical"));
                                                a.setKnockbackStrength(Config.getInt("Village Structures." + structure + ".Knockback"));
                                                a.setFireTicks(Config.getInt("Village Structures." + structure + ".Fire Ticks"));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //INCOME TIME FOR STRUCTURES
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("str") && !((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("con")) {
                            String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                            if (Config.isConfigurationSection("Village Structures." + structure)) {
                                if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                                    if (time % ((Integer) tempHashMap.get("incometimer").get(structure)) == 0 && ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) == Config.getInt("Village Structures." + structure + ".Total Hp")) {
                                        tempfile = new File(structureFolder, structure + ".yml");
                                        FileConfiguration tempyaml = new YamlConfiguration();
                                        try {
                                            tempyaml.load(tempfile);
                                        } catch (IOException | InvalidConfigurationException ex) {
                                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                            block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.X") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.Z") - 1);
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                            block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + Math.abs(tempyaml.getInt("Main Chest.Z") - 16), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + tempyaml.getInt("Main Chest.X") - 1);
                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                            block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + 16 - tempyaml.getInt("Main Chest.X"), ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + 16 - tempyaml.getInt("Main Chest.Z"));
                                        } else {
                                            block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + tempyaml.getInt("Main Chest.Z") - 1, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Main Chest.Y") - 2, (Integer) z * 16 + Math.abs(tempyaml.getInt("Main Chest.X") - 16));
                                        }
                                        String village = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString();
                                        if (Config.isSet("Village Structures." + structure + ".Required Materials")) {
                                            Boolean cont = true;
                                            for (String s : Config.getStringList("Village Structures." + structure + ".Required Materials")) {
                                                String[] req = s.split(":");
                                                if (!((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                    cont = false;
                                                }
                                            }
                                            if (cont) {
                                                for (String s : Config.getStringList("Village Structures." + structure + ".Required Materials")) {
                                                    String[] req = s.split(":");
                                                    ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                                }
                                                serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Revenue") - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                                if (Config.isSet("Village Structures." + structure + ".Produced Materials")) {
                                                    for (String p : Config.getStringList("Village Structures." + structure + ".Produced Materials")) {
                                                        String[] pro = p.split(":");
                                                        ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                                    }
                                                }
                                            } else {
                                                serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                            }
                                        } else if (Config.isSet("Village Structures." + structure + ".Produced Materials")) {
                                            for (String p : Config.getStringList("Village Structures." + structure + ".Produced Materials")) {
                                                String[] pro = p.split(":");
                                                ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                            }
                                            serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Revenue") - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                        } else {
                                            serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Revenue") - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //AUTO SAVE
            if (time % save == 0) {
                MainConversions.onPluginSave();
            }
            if (time % tax2 == 0) {
                for (String e : serverdata.get("empires").keySet()) {
                    if (serverdata.get("empires").get(e).containsKey("tax")) {
                        taxes = Integer.parseInt(serverdata.get("empires").get(e).get("vau").toString());
                        for (String v : ((ArrayList<String>) serverdata.get("empires").get(e).get("vils"))) {
                            if (Integer.parseInt(serverdata.get("villages").get(v).get("vau").toString()) >= Integer.parseInt(serverdata.get("empires").get(e).get("tax").toString())) {
                                taxes += Integer.parseInt(serverdata.get("empires").get(e).get("tax").toString());
                                serverdata.get("villages").get(v).put("vau", Integer.parseInt(serverdata.get("villages").get(v).get("vau").toString()) - Integer.parseInt(serverdata.get("empires").get(e).get("tax").toString()));
                            } else {
                                if (!serverdata.get("empires").get(e).containsKey("debt")) {
                                    serverdata.get("empires").get(e).put("debt", new HashMap<>());
                                }
                                if (!((HashMap) serverdata.get("empires").get(e).get("debt")).containsKey(v)) {
                                    ((HashMap) serverdata.get("empires").get(e).get("debt")).put(v, Integer.parseInt(serverdata.get("empires").get(e).get("tax").toString()) * -1);
                                } else {
                                    ((HashMap) serverdata.get("empires").get(e).get("debt")).put(v, Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("debt")).get(v).toString()) - Integer.parseInt(serverdata.get("empires").get(e).get("tax").toString()));
                                }
                            }
                        }
                    }
                }
            }
            for (String e : serverdata.get("empires").keySet()) {
                if (serverdata.get("empires").get(e).containsKey("ene")) {
                    ((HashMap) serverdata.get("empires").get(e).get("ene")).keySet().stream().filter((en) -> (((HashMap) serverdata.get("empires").get(e).get("ene")).get(en) != null)).map((en) -> {
                        ((HashMap) serverdata.get("empires").get(e).get("ene")).put(en, ((Integer) ((HashMap) serverdata.get("empires").get(e).get("ene")).get(en)) - 1);
                        return en;
                    }).filter((en) -> (((Integer) ((HashMap) serverdata.get("empires").get(e).get("ene")).get(en)) == 0)).forEach((en) -> {
                        ((HashMap) serverdata.get("empires").get(e).get("ene")).put(en, null);
                    });
                }
            }
            for (String s : serverdata.get("villages").keySet()) {
                if (serverdata.get("villages").get(s).containsKey("ene")) {
                    ((HashMap) serverdata.get("villages").get(s).get("ene")).keySet().stream().filter((e) -> (((HashMap) serverdata.get("villages").get(s).get("ene")).get(e) != null)).map((e) -> {
                        ((HashMap) serverdata.get("villages").get(s).get("ene")).put(e, ((Integer) ((HashMap) serverdata.get("villages").get(s).get("ene")).get(e)) - 1);
                        return e;
                    }).filter((e) -> (((Integer) ((HashMap) serverdata.get("villages").get(s).get("ene")).get(e)) == 0)).forEach((e) -> {
                        ((HashMap) serverdata.get("villages").get(s).get("ene")).put(e, null);
                    });
                }
                if (time % tax == 0) {
                    String rank = serverdata.get("villages").get(s).get("vir").toString();
                    taxes = Integer.parseInt(serverdata.get("villages").get(s).get("vau").toString()) - Config.getInt("Village Ranks." + rank + ".Upkeep");
                    if (serverdata.get("villages").get(s).containsKey("tax")) {
                        if (serverdata.get("villages").get(s).get("man") != null) {
                            ((ArrayList) serverdata.get("villages").get(s).get("man")).stream().forEach((n) -> {
                                if (econ.has(Bukkit.getServer().getOfflinePlayer(UUID.fromString(n.toString())), Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()))) {
                                    econ.withdrawPlayer(Bukkit.getServer().getOfflinePlayer(UUID.fromString(n.toString())), Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()));
                                    taxes += Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString());
                                } else {
                                    if (!serverdata.get("villages").get(s).containsKey("debt")) {
                                        serverdata.get("villages").get(s).put("debt", new HashMap<>());
                                    }
                                    if (!((HashMap) serverdata.get("villages").get(s).get("debt")).containsKey(n)) {
                                        ((HashMap) serverdata.get("villages").get(s).get("debt")).put(n, Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()) * -1);
                                    } else {
                                        ((HashMap) serverdata.get("villages").get(s).get("debt")).put(n, Integer.parseInt(((HashMap) serverdata.get("villages").get(s).get("debt")).get(n).toString()) - Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()));
                                    }
                                }
                            });
                        }
                        if (serverdata.get("villages").get(s).get("mem") != null) {
                            ((ArrayList) serverdata.get("villages").get(s).get("mem")).stream().forEach((n) -> {
                                if (econ.has(Bukkit.getServer().getOfflinePlayer(UUID.fromString(n.toString())), Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()))) {
                                    econ.withdrawPlayer(Bukkit.getServer().getOfflinePlayer(UUID.fromString(n.toString())), Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()));
                                    taxes += Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString());
                                } else {
                                    if (!serverdata.get("villages").get(s).containsKey("debt")) {
                                        serverdata.get("villages").get(s).put("debt", new HashMap<>());
                                    }
                                    if (!((HashMap) serverdata.get("villages").get(s).get("debt")).containsKey(n)) {
                                        ((HashMap) serverdata.get("villages").get(s).get("debt")).put(n, Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()) * -1);
                                    } else {
                                        ((HashMap) serverdata.get("villages").get(s).get("debt")).put(n, Integer.parseInt(((HashMap) serverdata.get("villages").get(s).get("debt")).get(n).toString()) - Integer.parseInt(serverdata.get("villages").get(s).get("tax").toString()));
                                    }
                                }
                            });
                        }
                    }
                    serverdata.get("villages").get(s).put("vau", taxes);
                    if (((Integer) serverdata.get("villages").get(s).get("vau")) < Config.getInt("Village Settings.Debt Before Village Loss")) {
                        OwnerCommands.Defeated(s, "Taxes");
                    }
                }
            }
        }, 0L, 20L);
    }
}
