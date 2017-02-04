/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import com.n1t3slay3r.empirecraft.OwnerCommands.*;
import com.n1t3slay3r.empirecraft.Uncategorized.OnPluginSave;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.econ;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.TORCH;
import static org.bukkit.Material.REDSTONE_TORCH_ON;
import static org.bukkit.Material.REDSTONE_TORCH_OFF;
import static org.bukkit.Material.BEDROCK;
import static org.bukkit.Material.LADDER;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.STEP;
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
        ArrayList<Player> tempplist = new ArrayList<>();
        TreeMap<Integer, TreeMap<Integer, ArrayList<Integer>>> tMap = new TreeMap<>();
        HashMap<String, HashMap<String, TreeMap<String, TreeMap<String, ArrayList<String>>>>> t2Map = new HashMap<>();
        Long build = Config.getLong("Village Settings.Build Delay"), destr = Config.getLong("Village Settings.Destruction Delay"), tax = Config.getLong("Village Settings.Tax Delay"), tax2 = Config.getLong("Empire Settings.Tax Delay"), save = Config.getLong("Global Settings.Auto Save Interval");
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            time = System.currentTimeMillis() / 1000;
            for (String w : serverdata.get("worldmap").keySet()) {
                for (Object x : serverdata.get("worldmap").get(w).keySet()) {
                    for (Object z : ((HashMap) serverdata.get("worldmap").get(w).get(x)).keySet()) {
                        //Fireworks delay so it doesnt get spammed
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("fir");
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
                                    Integer ny = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Height") - 2, nx = (Integer) x * 16 - 1, nz = (Integer) z * 16;
                                    String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                                    Boolean fin = false, cont = false;
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
                                    nx += ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) % 16;
                                    nz += (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) / 16) % 16;
                                    if (!QuickChecks.isMultiType(structure)) {
                                        ny -= ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) / 256;
                                        do {
                                            nx++;
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("cle", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) + 1);
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
                                            } else {
                                                String dir = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString();
                                                String part;
                                                Material mat;
                                                if (dir.equalsIgnoreCase("n")) {
                                                    part = "Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nx - (Integer) x * 16 + 1) + "." + (nz - (Integer) z * 16 + 1);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else if (dir.equalsIgnoreCase("e")) {
                                                    part = "Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nz - (Integer) z * 16 + 1) + "." + Math.abs(nx - 16 - (Integer) x * 16);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else if (dir.equalsIgnoreCase("s")) {
                                                    part = "Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (16 - (nx - (Integer) x * 16)) + "." + (16 - (nz - (Integer) z * 16));
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else {
                                                    part = "Scematic." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + Math.abs(nz - 16 - (Integer) z * 16) + "." + (nx - (Integer) x * 16 + 1);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                }
                                            }
                                        } while ((Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType() == AIR || !cont) && !fin);
                                    } else {
                                        Integer cx = 0, cz = 0, t1 = 0;
                                        Check:
                                        for (String a : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                                            for (String b : tempyaml.getConfigurationSection("Scematic." + a).getKeys(false)) {
                                                if ((256 * tempyaml.getInt("Height")) + t1 < ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle"))) {
                                                    t1 += tempyaml.getInt("Height") * 256;
                                                } else {
                                                    cx = Integer.parseInt(a);
                                                    cz = Integer.parseInt(b);
                                                    fin = true;
                                                    break Check;
                                                }
                                            }
                                        }
                                        fin = !fin;
                                        ny -= (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) / 256) % tempyaml.getInt("Height");
                                        String dir = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString();
                                        String part;
                                        Material mat;
                                        nx -= cx * 16;
                                        nz -= cz * 16;
                                        Loop:
                                        do {
                                            nx++;
                                            if (nx - 16 == ((Integer) x - cx) * 16) {
                                                nx -= 16;
                                                nz++;
                                                if (nz - 16 == ((Integer) z - cz) * 16) {
                                                    nz -= 16;
                                                    ny--;
                                                }
                                            }
                                            if (ny < ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 1) {
                                                break;
                                            } else {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("cle", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cle")) + 1);
                                                if (dir.equalsIgnoreCase("n")) {
                                                    part = "Scematic." + cx + "." + cz + "." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nx - ((Integer) x - cx) * 16 + 1) + "." + (nz - ((Integer) z - cz) * 16 + 1);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else if (dir.equalsIgnoreCase("e")) {
                                                    part = "Scematic." + cz + "." + cx + "." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (nz - ((Integer) z + cz) * 16 + 1) + "." + Math.abs(nx - 16 - ((Integer) x + cx) * 16);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else if (dir.equalsIgnoreCase("s")) {
                                                    part = "Scematic." + (cx * -1) + "." + (cz * -1) + "." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + (16 - (nx - ((Integer) x + cx) * 16)) + "." + (16 - (nz - ((Integer) z + cz) * 16));
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                } else {
                                                    part = "Scematic." + (cz * -1) + "." + (cx * -1) + "." + (ny - ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + 2) + "." + Math.abs(nz - 16 - ((Integer) z + cz) * 16) + "." + (nx - ((Integer) x + cx) * 16 + 1);
                                                    if (tempyaml.contains(part)) {
                                                        mat = Material.getMaterial(tempyaml.getString(part + ".id"));
                                                        cont = BuildRotationCheck.cont(Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz), mat, chest, tempyaml, part, dir);
                                                    } else if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType().equals(AIR)) {
                                                        cont = true;
                                                    }
                                                }
                                            }
                                        } while ((Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType() == AIR || !cont) && !fin);
                                    }
                                    if (fin) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("cle");
                                        if (Config.getInt("Village Settings.Build Delay") == 0) {
                                            int cy = 0;
                                            for (int y = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")); y < ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) + tempyaml.getInt("Height") - 2; y++) {
                                                cy++;
                                                for (nx = 1; nx < 17; nx++) {
                                                    for (nz = 1; nz < 17; nz++) {
                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + nx + "." + nz + ".id")));
                                                                BuildRotationCheck.Set("n", block, block.getType(), cy, nx, nz, tempyaml);
                                                            } else if (!(tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(tempyaml.getInt("Main Chest.Z") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + nz + "." + Math.abs(nx - 17) + ".id")));
                                                                BuildRotationCheck.Set("e", block, block.getType(), cy, nx, nz, tempyaml);
                                                            } else if (!(tempyaml.getInt("Main Chest.Z") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && Math.abs(tempyaml.getInt("Main Chest.X") - 17) == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(17 - tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + (17 - nx) + "." + (17 - nz) + ".id")));
                                                                BuildRotationCheck.Set("s", block, block.getType(), cy, nx, nz, tempyaml);
                                                            } else if (!(17 - tempyaml.getInt("Main Chest.X") == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && 17 - tempyaml.getInt("Main Chest.Z") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        } else {
                                                            if (Config.getInt("Village Settings.Build Delay") == 0 && !(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(Material.getMaterial(tempyaml.getString("Scematic." + cy + "." + Math.abs(nz - 17) + "." + nx + ".id")));
                                                                BuildRotationCheck.Set("w", block, block.getType(), cy, nx, nz, tempyaml);
                                                            } else if (!(Math.abs(tempyaml.getInt("Main Chest.Z") - 17) == nx && tempyaml.getInt("Main Chest.Y") == y - 1 && tempyaml.getInt("Main Chest.X") == nz)) {
                                                                block = Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx + (Integer) x * 16, y - 1, nz + (Integer) z * 16);
                                                                block.setType(AIR);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("con");
                                            if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).get("own").toString()))).isOnline()) {
                                                Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).get("own").toString())).sendMessage(ChatColor.DARK_PURPLE + "The structure " + ChatColor.LIGHT_PURPLE + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ChatColor.DARK_PURPLE + " has finished construction and is now operational");
                                            }
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("con");
                                            if (Config.isConfigurationSection("Village Ranks." + structure)) {
                                                serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).replace("vir", structure);
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            } else {
                                                if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                                                    tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Income Timer"));
                                                } else {
                                                    temparraylist.clear();
                                                    temparraylist.addAll(Config.getConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Productions").getKeys(false));
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("pro", temparraylist.get(0));
                                                }
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            }
                                        }
                                    } else if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).getType() != BEDROCK) {
                                        Bukkit.getWorld(UUID.fromString(w)).getBlockAt(nx, ny, nz).breakNaturally();
                                    } else if (cont) {
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
                                    Integer chx = 0, chz = 0, skip = 0, t1 = 0, nx = 0, ny = 0, nz = 0, icon = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")), xx = 0, zz = 0;
                                    Boolean fin = false, cont = false, not = false;
                                    Material mat = null;
                                    String nmat = null;
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
                                    String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").toString();
                                    tMap.clear();
                                    Boolean rage = false;
                                    if (!QuickChecks.isMultiType(structure)) {
                                        for (String sy : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                                            for (String sx : tempyaml.getConfigurationSection("Scematic." + sy).getKeys(false)) {
                                                if (tempyaml.getConfigurationSection("Scematic." + sy + "." + sx).getKeys(false).size() + t1 < icon) {
                                                    t1 += tempyaml.getConfigurationSection("Scematic." + sy + "." + sx).getKeys(false).size();
                                                } else if (!rage) {
                                                    rage = true;
                                                    Integer i = 0;
                                                    tMap.put(Integer.parseInt(sy), new TreeMap<>());
                                                    tMap.get(Integer.parseInt(sy)).put(Integer.parseInt(sx), new ArrayList<>());
                                                    for (String sz : tempyaml.getConfigurationSection("Scematic." + sy + "." + sx).getKeys(false)) {
                                                        i++;
                                                        if (t1 + i >= icon) {
                                                            tMap.get(Integer.parseInt(sy)).get(Integer.parseInt(sx)).add(Integer.parseInt(sz));
                                                        }
                                                    }
                                                } else {
                                                    tMap.putIfAbsent(Integer.parseInt(sy), new TreeMap<>());
                                                    tMap.get(Integer.parseInt(sy)).put(Integer.parseInt(sx), new ArrayList<>());
                                                    for (String sz : tempyaml.getConfigurationSection("Scematic." + sy + "." + sx).getKeys(false)) {
                                                        tMap.get(Integer.parseInt(sy)).get(Integer.parseInt(sx)).add(Integer.parseInt(sz));
                                                    }
                                                }
                                            }
                                        }
                                        Main:
                                        for (Integer sy : tMap.keySet()) {
                                            ny = sy;
                                            for (Integer sx : tMap.get(sy).keySet()) {
                                                for (Integer sz : tMap.get(sy).get(sx)) {
                                                    nx = sx;
                                                    nz = sz;
                                                    xx = nx;
                                                    zz = nz;
                                                    skip++;
                                                    System.out.println(tMap.keySet() + " " + tMap.get(sy).keySet() + " " + tMap.get(sy).get(sx));
                                                    mat = Material.getMaterial(tempyaml.getString("Scematic." + ny + "." + nx + "." + nz + ".id"));
                                                    System.out.println(mat + " " + Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1).getLocation());
                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                        nx = Math.abs(zz - 17);
                                                        nz = xx;
                                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                        nx = (17 - xx);
                                                        nz = (17 - zz);
                                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("w")) {
                                                        nx = zz;
                                                        nz = Math.abs(xx - 17);
                                                    }
                                                    if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1).getType().equals(mat)) {
                                                        if (Config.getString("Village Settings.Require Materials To Build").equals("on")) {
                                                            boolean cancel = false;
                                                            //Check to see if the required block is there/able to support the specificied block
                                                            if (mat == TORCH || mat == REDSTONE_TORCH_OFF || mat == REDSTONE_TORCH_ON || mat == LADDER) {
                                                                int dir = 0, cx = -1, cz = -1;
                                                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                                    dir = 1;
                                                                } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                                    dir = 2;
                                                                } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("w")) {
                                                                    dir = 3;
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
                                                            if (mat == WOOL || mat == SANDSTONE || mat == WOOD || mat == LOG || mat == STEP || mat == WOOD_STEP) {
                                                                ItemStack stack = null;
                                                                nmat = tempyaml.getString("Scematic." + ny + "." + xx + "." + zz + ".typ");
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
                                                                            stack = new ItemStack(mat, 1, Material.COBBLESTONE.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "STONE":
                                                                            stack = new ItemStack(mat, 1, Material.STONE.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "SANDSTONE":
                                                                            stack = new ItemStack(mat, 1, Material.SANDSTONE.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "BRICK":
                                                                            stack = new ItemStack(mat, 1, Material.BRICK.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "SMOOTH_BRICK":
                                                                            stack = new ItemStack(mat, 1, Material.SMOOTH_BRICK.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "QUARTZ":
                                                                            stack = new ItemStack(mat, 1, Material.QUARTZ.getNewData((byte) 0).getData());
                                                                            break;
                                                                        case "NETHER_BRICK":
                                                                            stack = new ItemStack(mat, 1, Material.NETHER_BRICK.getNewData((byte) 0).getData());
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
                                                            } else if (!cancel) {
                                                                nmat = mat.toString();
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
                                                        } else {
                                                            cont = true;
                                                        }
                                                    } else if (skip + icon == ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1) {
                                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("con", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1);
                                                    }
                                                    if (cont) {
                                                        break Main;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Integer i;
                                        t2Map.clear();
                                        Loop:
                                        for (String cx : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                                            for (String cz : tempyaml.getConfigurationSection("Scematic." + cx).getKeys(false)) {
                                                for (String sy : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz).getKeys(false)) {
                                                    for (String sx : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy).getKeys(false)) {
                                                        if (tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy + "." + sx).getKeys(false).size() + t1 < icon) {
                                                            t1 += tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy + "." + sx).getKeys(false).size();
                                                        } else if (!rage) {
                                                            rage = true;
                                                            i = 0;
                                                            t2Map.put(cx, new HashMap<>());
                                                            t2Map.get(cx).put(cz, new TreeMap<>());
                                                            t2Map.get(cx).get(cz).put(sy, new TreeMap<>());
                                                            t2Map.get(cx).get(cz).get(sy).put(sx, new ArrayList<>());
                                                            for (String sz : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy + "." + sx).getKeys(false)) {
                                                                i++;
                                                                if (t1 + i >= icon) {
                                                                    t2Map.get(cx).get(cz).get(sy).get(sx).add(sz);
                                                                }
                                                            }
                                                        } else {
                                                            if (!t2Map.get(cx).get(cz).containsKey(sy)) {
                                                                t2Map.get(cx).get(cz).put(sy, new TreeMap<>());
                                                            }
                                                            t2Map.get(cx).get(cz).get(sy).put(sx, new ArrayList<>());
                                                            t2Map.get(cx).get(cz).get(sy).get(sx).addAll(tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + sy + "." + sx).getKeys(false));
                                                        }
                                                    }
                                                }
                                                if (rage) {
                                                    chx = Integer.parseInt(cx);
                                                    chz = Integer.parseInt(cz);
                                                    break Loop;
                                                }
                                            }
                                        }
                                        if (!t2Map.isEmpty()) {
                                            Main:
                                            for (String sy : t2Map.get(chx.toString()).get(chz.toString()).keySet()) {
                                                ny = Integer.parseInt(sy);
                                                for (String sx : t2Map.get(chx.toString()).get(chz.toString()).get(sy).keySet()) {
                                                    for (String sz : t2Map.get(chx.toString()).get(chz.toString()).get(sy).get(sx)) {
                                                        nx = Integer.parseInt(sx);
                                                        nz = Integer.parseInt(sz);
                                                        xx = nx;
                                                        zz = nz;
                                                        skip++;
                                                        mat = Material.getMaterial(tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + nx + "." + nz + ".id"));
                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                            nx = Math.abs(zz - 17);
                                                            nz = xx;
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                            nx = (17 - xx);
                                                            nz = (17 - zz);
                                                        } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("w")) {
                                                            nx = zz;
                                                            nz = Math.abs(xx - 17);
                                                        }
                                                        if (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1).getType().equals(mat)) {
                                                            if (Config.getString("Village Settings.Require Materials To Build").equals("on")) {
                                                                boolean cancel = false;
                                                                if (mat == TORCH || mat == REDSTONE_TORCH_OFF || mat == REDSTONE_TORCH_ON || mat == LADDER) {
                                                                    int dir = 0, cx = -1, cz = -1;
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
                                                                    switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".dat")) {
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
                                                                            if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz + cz).getType().equals(AIR)) {
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
                                                                            if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz + cz).getType().equals(AIR)) {
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
                                                                            if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz + cz).getType().equals(AIR)) {
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
                                                                            if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz + cz).getType().equals(AIR)) {
                                                                                cancel = true;
                                                                            }
                                                                            break;
                                                                    }
                                                                }
                                                                if (mat == WOOL || mat == SANDSTONE || mat == WOOD || mat == LOG || mat == STEP || mat == WOOD_STEP) {
                                                                    ItemStack stack = null;
                                                                    nmat = tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".typ");
                                                                    if (mat == WOOL) {
                                                                        switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".typ")) {
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
                                                                        switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".typ")) {
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
                                                                        switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".typ")) {
                                                                            case "COBBLESTONE":
                                                                                stack = new ItemStack(mat, 1, Material.COBBLESTONE.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "STONE":
                                                                                stack = new ItemStack(mat, 1, Material.STONE.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "SANDSTONE":
                                                                                stack = new ItemStack(mat, 1, Material.SANDSTONE.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "BRICK":
                                                                                stack = new ItemStack(mat, 1, Material.BRICK.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "SMOOTH_BRICK":
                                                                                stack = new ItemStack(mat, 1, Material.SMOOTH_BRICK.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "QUARTZ":
                                                                                stack = new ItemStack(mat, 1, Material.QUARTZ.getNewData((byte) 0).getData());
                                                                                break;
                                                                            case "NETHER_BRICK":
                                                                                stack = new ItemStack(mat, 1, Material.NETHER_BRICK.getNewData((byte) 0).getData());
                                                                                break;
                                                                        }
                                                                    } else {
                                                                        switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".typ")) {
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
                                                                } else if (!cancel) {
                                                                    nmat = mat.toString();
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
                                                            } else {
                                                                cont = true;
                                                            }
                                                        } else if (skip + icon == ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1) {
                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("con", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1);
                                                        }
                                                        if (cont) {
                                                            break Main;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (skip == 0 && !not) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).remove("con");
                                        if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).get("own").toString()))).isOnline()) {
                                            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).get("own").toString())).sendMessage(ChatColor.DARK_PURPLE + "The structure " + ChatColor.LIGHT_PURPLE + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ChatColor.DARK_PURPLE + " has finished construction and is now operational");
                                        }
                                        if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"))) {
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            if (!Config.getString("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Type").equals("Archer")) {
                                                if (!Config.getString("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Type").equals("Normal") && !Config.getString("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Type").equals("Multi")) {
                                                    tempHashMap.get("incometimer").put(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"), Config.get("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Income Timer"));
                                                } else {
                                                    temparraylist.clear();
                                                    temparraylist.addAll(Config.getConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Productions").getKeys(false));
                                                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("pro", temparraylist.get(0));
                                                }
                                            }
                                        } else {
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"));
                                            serverdata.get("villages").get(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString()).put("vir", ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"));
                                        }
                                    } else if (cont) {
                                        if (skip + icon == ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1) {
                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).put("con", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("con")) + 1);
                                        }
                                        if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str"))) {
                                            if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + nmat) <= Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + nmat));
                                            }
                                        } else {
                                            if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + nmat) <= Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + nmat));
                                            }
                                        }
                                        if (!QuickChecks.isMultiType(structure)) {
                                            Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1).setType(mat);
                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, xx, zz, tempyaml);
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, xx, zz, tempyaml);
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, 17 - nx, 17 - nz, tempyaml);
                                            } else {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt((Integer) x * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, (Integer) z * 16 + nz - 1), mat, ny, Math.abs(nz - 17), nx, tempyaml);
                                            }
                                        } else {
                                            Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1).setType(mat);
                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1), mat, ny, nx, nz, tempyaml, chx, chz);
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1), mat, ny, nz, Math.abs(nx - 17), tempyaml, chx, chz);
                                            } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1), mat, ny, 17 - nx, 17 - nz, tempyaml, chx, chz);
                                            } else {
                                                BuildRotationCheck.Set(((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("dir").toString(), Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x - chx) * 16 + nx - 1, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z - chz) * 16 + nz - 1), mat, ny, Math.abs(nz - 17), nx, tempyaml, chx, chz);
                                            }
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
                                            if (Material.getMaterial(req[0]) != null) {
                                                if (((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                    ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                                    if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]) <= Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp")) {
                                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) + Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Block Hp." + req[0]));
                                                    } else {
                                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).replace("hp", Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp."));
                                                    }
                                                    repaired = true;
                                                }
                                            } else {
                                                String[] dat = req[0].split("_");
                                                Material mat = Material.getMaterial(dat[0]);
                                                ItemStack stack = null;
                                                if (mat == WOOL) {
                                                    switch (dat[1]) {
                                                        case "BLACK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.BLACK.getWoolData());
                                                            break;
                                                        case "BLUE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.BLUE.getWoolData());
                                                            break;
                                                        case "BROWN":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.BROWN.getWoolData());
                                                            break;
                                                        case "CYAN":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.CYAN.getWoolData());
                                                            break;
                                                        case "GRAY":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.GRAY.getWoolData());
                                                            break;
                                                        case "GREEN":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.GREEN.getWoolData());
                                                            break;
                                                        case "LIGHT_BLUE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.LIGHT_BLUE.getWoolData());
                                                            break;
                                                        case "LIME":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.LIME.getWoolData());
                                                            break;
                                                        case "MAGENTA":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.MAGENTA.getWoolData());
                                                            break;
                                                        case "ORANGE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.ORANGE.getWoolData());
                                                            break;
                                                        case "PINK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.PINK.getWoolData());
                                                            break;
                                                        case "PURPLE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.PURPLE.getWoolData());
                                                            break;
                                                        case "RED":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.RED.getWoolData());
                                                            break;
                                                        case "SILVER":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.SILVER.getWoolData());
                                                            break;
                                                        case "WHITE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.WHITE.getWoolData());
                                                            break;
                                                        case "YELLOW":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), DyeColor.YELLOW.getWoolData());
                                                            break;
                                                    }
                                                } else if (mat == SANDSTONE) {
                                                    switch (dat[1]) {
                                                        case "CRACKED":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), SandstoneType.CRACKED.getData());
                                                            break;
                                                        case "GLYPHED":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), SandstoneType.GLYPHED.getData());
                                                            break;
                                                        case "SMOOTH":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), SandstoneType.SMOOTH.getData());
                                                            break;
                                                    }
                                                } else if (mat == STEP) {
                                                    switch (dat[1]) {
                                                        case "COBBLESTONE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.COBBLESTONE.getNewData((byte) 0).getData());
                                                            break;
                                                        case "STONE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.STONE.getNewData((byte) 0).getData());
                                                            break;
                                                        case "SANDSTONE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.SANDSTONE.getNewData((byte) 0).getData());
                                                            break;
                                                        case "BRICK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.BRICK.getNewData((byte) 0).getData());
                                                            break;
                                                        case "SMOOTH_BRICK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.SMOOTH_BRICK.getNewData((byte) 0).getData());
                                                            break;
                                                        case "QUARTZ":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.QUARTZ.getNewData((byte) 0).getData());
                                                            break;
                                                        case "NETHER_BRICK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), Material.NETHER_BRICK.getNewData((byte) 0).getData());
                                                            break;
                                                    }
                                                } else {
                                                    switch (dat[1]) {
                                                        case "ACACIA":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.ACACIA.getData());
                                                            break;
                                                        case "BIRCH":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.BIRCH.getData());
                                                            break;
                                                        case "DARK_OAK":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.DARK_OAK.getData());
                                                            break;
                                                        case "GENERIC":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.GENERIC.getData());
                                                            break;
                                                        case "JUNGLE":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.JUNGLE.getData());
                                                            break;
                                                        case "REDWOOD":
                                                            stack = new ItemStack(mat, Integer.parseInt(req[1]), TreeSpecies.REDWOOD.getData());
                                                            break;
                                                    }
                                                }
                                                if (((InventoryHolder) block.getState()).getInventory().contains(stack)) {
                                                    ((InventoryHolder) block.getState()).getInventory().removeItem(stack);
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
                                }
                            } else if (!((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")).equals(Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str") + ".Total Hp"))) {
                                System.out.println(block.getType());
                                System.out.println(block.getLocation().toString());
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
                                    Bukkit.getWorld(UUID.fromString(w)).getPlayers().stream().filter((p) -> (abs(p.getLocation().getChunk().getX() + p.getLocation().getChunk().getZ() - (Integer) x - (Integer) z) <= Config.getInt("Village Structures." + structure + ".Range"))).filter((p) -> (!p.isDead() && QuickChecks.isPlayerInVillage(p.getUniqueId()))).forEach((p) -> {
                                        String tempvil = serverdata.get("playerdata").get(p.getUniqueId().toString()).get("village").toString();
                                        if (QuickChecks.isPartInHashMap(serverdata.get("villages").get(village), "ene", tempvil) || QuickChecks.isEnemyEmpire(village, tempvil)) {
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
                                } else if (QuickChecks.structureIncomeCheck(time, structure, w, x, z) && ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("hp")) == Config.getInt("Village Structures." + structure + ".Total Hp")) {
                                    //INCOME TIME FOR STRUCTURES
                                    String village = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("cla").toString();
                                    if (Config.getString("Village Structures." + structure + ".Type").equals("Normal") || Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
                                        String proc = ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("pro").toString();
                                        if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Required Materials")) {
                                            Boolean cont = true;
                                            for (String s : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Required Materials")) {
                                                String[] req = s.split(":");
                                                if (!((InventoryHolder) block.getState()).getInventory().contains(Material.getMaterial(req[0]), Integer.parseInt(req[1]))) {
                                                    cont = false;
                                                }
                                            }
                                            if (cont) {
                                                for (String s : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Required Materials")) {
                                                    String[] req = s.split(":");
                                                    ((InventoryHolder) block.getState()).getInventory().removeItem(new ItemStack(Material.getMaterial(req[0]), Integer.parseInt(req[1])));
                                                }
                                                serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Revenue") - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Upkeep"));
                                                if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Produced Materials")) {
                                                    for (String p : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Produced Materials")) {
                                                        String[] pro = p.split(":");
                                                        String[] per = pro[1].split("%");
                                                        if (Math.random() * 100 <= Double.parseDouble(per[1])) {
                                                            ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                                        }
                                                    }
                                                }
                                                if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                    Integer sx, sz;
                                                    for (String p : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                        String[] pro = p.split(":");
                                                        for (int i = 0; i < Integer.parseInt(pro[1]); i++) {
                                                            do {
                                                                sx = (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") + 9 - (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range"))));
                                                                sz = (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") + 9 - (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range"))));
                                                            } while (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(block.getLocation().getChunk().getX() * 16 + sx, block.getLocation().getBlockY() + 1, block.getLocation().getChunk().getZ() * 16 + sz).isLiquid() && !Bukkit.getWorld(UUID.fromString(w)).getBlockAt(sx, block.getLocation().getBlockY() + 1, sz).isEmpty());
                                                            Entity s = Bukkit.getWorld(UUID.fromString(w)).spawnEntity(block.getChunk().getBlock(sx, block.getY() + 1, sz).getLocation(), EntityType.fromName(pro[0]));
                                                        }
                                                    }
                                                }
                                            } else {
                                                serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Upkeep"));
                                            }
                                        } else if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Produced Materials")) {
                                            for (String p : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Produced Materials")) {
                                                String[] pro = p.split(":");
                                                String[] per = pro[1].split("%");
                                                if (Math.random() * 100 <= Double.parseDouble(per[1])) {
                                                    ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                                }
                                            }
                                            if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                Integer sx, sz;
                                                for (String p : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                    String[] pro = p.split(":");
                                                    do {
                                                        sx = (-1 * Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (3 + Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")));
                                                        sz = (-1 * Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (3 + Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")));
                                                    } while (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(sx, block.getLocation().getBlockY() + 1, sz).isLiquid() || Bukkit.getWorld(UUID.fromString(w)).getBlockAt(sx, block.getLocation().getBlockY() + 1, sz).isEmpty());
                                                    for (int i = 0; i < Integer.parseInt(pro[1]); i++) {
                                                        Entity s = Bukkit.getWorld(UUID.fromString(w)).spawnEntity(block.getLocation().add(sx, 1, sz), EntityType.fromName(pro[0]));
                                                    }
                                                }
                                            }
                                            serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Revenue") - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Upkeep"));
                                        } else {
                                            if (Config.isList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                Integer sx, sz;
                                                for (String p : Config.getStringList("Village Structures." + structure + ".Productions." + proc + ".Animals")) {
                                                    String[] pro = p.split(":");
                                                    for (int i = 0; i < Integer.parseInt(pro[1]); i++) {
                                                        do {
                                                            sx = (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") + 9 - (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range"))));
                                                            sz = (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range")) + (int) (Math.random() * (Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range") + 9 - (7 - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Animal Spawn Range"))));
                                                        } while (!Bukkit.getWorld(UUID.fromString(w)).getBlockAt(block.getLocation().getChunk().getX() * 16 + sx, block.getLocation().getBlockY() + 1, block.getLocation().getChunk().getZ() * 16 + sz).isLiquid() && !Bukkit.getWorld(UUID.fromString(w)).getBlockAt(sx, block.getLocation().getBlockY() + 1, sz).isEmpty());
                                                        Entity s = Bukkit.getWorld(UUID.fromString(w)).spawnEntity(block.getChunk().getBlock(sx, block.getY() + 1, sz).getLocation(), EntityType.fromName(pro[0]));
                                                    }
                                                }
                                            }
                                            serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) + Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Revenue") - Config.getInt("Village Structures." + structure + ".Productions." + proc + ".Upkeep"));
                                        }
                                    } else {
                                        if (Config.isList("Village Structures." + structure + ".Required Materials")) {
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
                                                if (Config.isList("Village Structures." + structure + ".Produced Materials")) {
                                                    for (String p : Config.getStringList("Village Structures." + structure + ".Produced Materials")) {
                                                        String[] pro = p.split(":");
                                                        String[] per = pro[1].split("%");
                                                        if (Math.random() * 100 <= Double.parseDouble(per[1])) {
                                                            ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                                        }
                                                    }
                                                }
                                            } else {
                                                serverdata.get("villages").get(village).replace("vau", ((Integer) serverdata.get("villages").get(village).get("vau")) - Config.getInt("Village Structures." + structure + ".Upkeep"));
                                            }
                                        } else if (Config.isList("Village Structures." + structure + ".Produced Materials")) {
                                            for (String p : Config.getStringList("Village Structures." + structure + ".Produced Materials")) {
                                                String[] pro = p.split(":");
                                                String[] per = pro[1].split("%");
                                                if (Math.random() * 100 <= Double.parseDouble(per[1])) {
                                                    ((InventoryHolder) block.getState()).getInventory().addItem(new ItemStack(Material.getMaterial(pro[0]), Integer.parseInt(pro[1])));
                                                }
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
                OnPluginSave.onPluginSave();
            }
            for (String e : serverdata.get("empires").keySet()) {
                if (time % tax2 == 0) {
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
                if (serverdata.get("empires").get(e).containsKey("ene")) {
                    ((HashMap) serverdata.get("empires").get(e).get("ene")).keySet().stream().filter((en) -> (((HashMap) serverdata.get("empires").get(e).get("ene")).get(en) != null)).map((en) -> {
                        ((HashMap) serverdata.get("empires").get(e).get("ene")).put(en, ((Integer) ((HashMap) serverdata.get("empires").get(e).get("ene")).get(en)) - 1);
                        return en;
                    }).filter((en) -> (((Integer) ((HashMap) serverdata.get("empires").get(e).get("ene")).get(en)) == 0)).forEach((en) -> {
                        ((HashMap) serverdata.get("empires").get(e).get("ene")).put(en, null);
                    });
                }
            }
            //The temparraylist eleminates the ConcurrentModificationException error (Maybe?)
            temparraylist.clear();
            temparraylist.addAll(serverdata.get("villages").keySet());
            for (String s : ((ArrayList<String>) temparraylist)) {
                if (serverdata.get("villages").get(s).containsKey("ene")) {
                    ((HashMap) serverdata.get("villages").get(s).get("ene")).keySet().stream().filter((e) -> (((HashMap) serverdata.get("villages").get(s).get("ene")).get(e) != null)).map((e) -> {
                        ((HashMap) serverdata.get("villages").get(s).get("ene")).put(e, ((Integer) ((HashMap) serverdata.get("villages").get(s).get("ene")).get(e)) - 1);
                        return e;
                    }).filter((e) -> (((Integer) ((HashMap) serverdata.get("villages").get(s).get("ene")).get(e)) == 0)).forEach((e) -> {
                        ((HashMap) serverdata.get("villages").get(s).get("ene")).put(e, null);
                    });
                }
                if (serverdata.get("villages").get(s).containsKey("inv")) {
                    serverdata.get("villages").get(s).put("inv", ((Integer) serverdata.get("villages").get(s).get("inv")) - 1);
                    if (((Integer) serverdata.get("villages").get(s).get("inv")) == 0) {
                        serverdata.get("villages").get(s).remove("inv");
                    }
                    if ((Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(s).get("own").toString()))).isOnline()) {
                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(s).get("own").toString())).sendMessage(ChatColor.DARK_PURPLE + "The villages initial war immunity timer has expired and therefor other villages can now declare war on you");
                    }
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
                        Defeated.Defeated(s, "Taxes");
                    }
                }
            }
        }, 0L, 20L);
    }
}

/* OLD CODE, IGNORE THIS UNLESS LADDER OR THE BELOW ISSUES COME UP!
 if (mat == TORCH || mat == REDSTONE_TORCH_OFF || mat == REDSTONE_TORCH_ON || mat == LADDER) {
 int dir = 0, cx = -1, cz = -1;
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
 switch (tempyaml.getString("Scematic." + chx + "." + chz + "." + ny + "." + xx + "." + zz + ".dat")) {
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
 if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x + chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z + chz) * 16 + nz + cz).getType().equals(AIR)) {
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
 if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x + chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z + chz) * 16 + nz + cz).getType().equals(AIR)) {
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
 if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x + chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z + chz) * 16 + nz + cz).getType().equals(AIR)) {
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
 if (Bukkit.getWorld(UUID.fromString(w)).getBlockAt(((Integer) x + chx) * 16 + nx + cx, ny + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("base")) - 2, ((Integer) z + chz) * 16 + nz + cz).getType().equals(AIR)) {
 cancel = true;
 }
 break;
 }
 }
 */
