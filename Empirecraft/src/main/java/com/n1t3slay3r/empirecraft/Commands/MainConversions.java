/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Commands;

import com.n1t3slay3r.empirecraft.main.Main;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import com.n1t3slay3r.empirecraft.main.SLAPI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.STEP;
import static org.bukkit.Material.WOOD;
import static org.bukkit.Material.WOOD_STEP;
import static org.bukkit.Material.WOOL;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Step;
import org.bukkit.material.Tree;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

/**
 *
 * @author Dylan Malec
 */
public class MainConversions extends Main {

    public static boolean isInteger(String s) {
        try {
            int n = Integer.parseInt(s);
            if (n < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPlayerInVillage(UUID p) {
        if (serverdata.get("playerdata").containsKey(p.toString())) {
            if (serverdata.get("playerdata").get(p.toString()).containsKey("village")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMultiType(String structure) {
        if (Config.isConfigurationSection("Village Structures." + structure)) {
            if (Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMulti(Chunk c, String structure, FileConfiguration file, String dir, String pvil) {
        if (Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
            for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                    if (dir.equalsIgnoreCase("n")) {
                        if (isWorldChunkClaimed(serverdata.get("worldmap").get(c.getWorld().getUID().toString()), Integer.parseInt(x) * -1 + c.getX(), Integer.parseInt(z) * -1 + c.getZ(), "cla")) {
                            if ((!((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).get("cla").equals(pvil) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).containsKey("playerplot")) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).containsKey("str")) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else if (dir.equalsIgnoreCase("e")) {
                        if (isWorldChunkClaimed(serverdata.get("worldmap").get(c.getWorld().getUID().toString()), Integer.parseInt(z) * -1 + c.getX(), Integer.parseInt(x) * -1 + c.getZ(), "cla")) {
                            if ((!((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).get("cla").equals(pvil) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).containsKey("playerplot")) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).containsKey("str")) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else if (dir.equalsIgnoreCase("s")) {
                        if (isWorldChunkClaimed(serverdata.get("worldmap").get(c.getWorld().getUID().toString()), Integer.parseInt(x) + c.getX(), Integer.parseInt(z) + c.getZ(), "cla")) {
                            if ((!((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).get("cla").equals(pvil) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).containsKey("playerplot")) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).containsKey("str")) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (isWorldChunkClaimed(serverdata.get("worldmap").get(c.getWorld().getUID().toString()), Integer.parseInt(z) + c.getX(), Integer.parseInt(x) + c.getZ(), "cla")) {
                            if ((!((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).get("cla").equals(pvil) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).containsKey("playerplot")) || ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).containsKey("str")) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
            if (dir.equalsIgnoreCase("n")) {
                for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                    for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).put("str", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).put("mx", Integer.parseInt(x) * -1);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) * -1 + c.getX())).get(Integer.parseInt(z) * -1 + c.getZ())).put("mz", Integer.parseInt(z) * -1);
                    }
                }
            } else if (dir.equalsIgnoreCase("e")) {
                for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                    for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).put("str", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).put("mx", Integer.parseInt(x) * -1);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) * -1 + c.getX())).get(Integer.parseInt(x) * -1 + c.getZ())).put("mz", Integer.parseInt(z) * -1);
                    }
                }
            } else if (dir.equalsIgnoreCase("s")) {
                for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                    for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).put("str", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).put("mx", Integer.parseInt(x) * -1);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(x) + c.getX())).get(Integer.parseInt(z) + c.getZ())).put("mz", Integer.parseInt(z) * -1);
                    }
                }
            } else {
                for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                    for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).put("str", structure);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).put("mx", Integer.parseInt(x) * -1);
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(c.getWorld().getUID().toString()).get(Integer.parseInt(z) + c.getX())).get(Integer.parseInt(x) + c.getZ())).put("mz", Integer.parseInt(z) * -1);
                    }
                }
            }
        }
        return true;
    }

    public static boolean enemyEmpire(String pvillage, String evillage) {
        if (serverdata.get("villages").get(pvillage).containsKey("emp") && serverdata.get("villages").get(evillage).containsKey("emp")) {
            if (serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).containsKey("ene")) {
                if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).containsKey(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean listenerBlockBreak(String world, Integer x, Integer z, Player player, Material mat, String evillage, String playeruuid, Block block) {
        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
            String nmat;
            if (mat == WOOL) {
                nmat = mat.toString() + "_" + ((Wool) block.getState().getData()).getColor().toString();
            } else if (mat == SANDSTONE) {
                nmat = mat.toString() + "_" + ((Sandstone) block.getState().getData()).getType().toString();
            } else if (mat == WOOD || mat == LOG) {
                nmat = mat.toString() + "_" + ((Tree) block.getState().getData()).getSpecies().toString();
            } else if (mat == STEP) {
                nmat = mat.toString() + "_" + ((Step) block.getState().getData()).getMaterial().toString();
            } else if (mat == WOOD_STEP) {
                nmat = mat.toString() + "_" + ((WoodenStep) block.getState().getData()).getSpecies().toString();
            } else {
                nmat = mat.toString();
            }
            if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str"))) {
                if (Config.isInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat)) {
                    if (isMultiType(((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str").toString())) {
                        x = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("mx"));
                        z = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("mz"));
                    }
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) - Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat));
                    if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) < 1) {
                        if (isMultiType(((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str").toString())) {
                            tempfile = new File(structureFolder, ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".yml");
                            FileConfiguration tempyaml = new YamlConfiguration();
                            try {
                                tempyaml.load(tempfile);
                            } catch (IOException | InvalidConfigurationException ex) {
                                Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            for (String cx : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                                for (String cz : tempyaml.getConfigurationSection("Scematic." + cx).getKeys(false)) {
                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("n")) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x + Integer.parseInt(cx) * -1)).get(z + Integer.parseInt(cz) * -1)).remove("str");
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("e")) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x + Integer.parseInt(cz) * -1)).get(z + Integer.parseInt(cx) * -1)).remove("str");
                                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x)).get(z)).get("dir").toString().equalsIgnoreCase("s")) {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x + Integer.parseInt(cx))).get(z + Integer.parseInt(cz))).remove("str");
                                    } else {
                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(x + Integer.parseInt(cz))).get(z + Integer.parseInt(cx))).remove("str");
                                    }
                                }
                            }
                        }
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("str");
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("hp");
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("dir");
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("base");
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("con")) {
                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("con");
                        }
                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("cle")) {
                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).remove("cle");
                        }
                        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        FireworkEffect.Type type = FireworkEffect.Type.CREEPER;
                        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).withFade(Color.BLACK).with(type).trail(true).build();
                        fwm.addEffect(effect);
                        fwm.setPower(1);
                        fw.setFireworkMeta(fwm);
                        fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        fwm.setPower(2);
                        fw.setFireworkMeta(fwm);
                        fwm.setPower(3);
                        fw.setFireworkMeta(fwm);
                        fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        fwm.setPower(4);
                        fw.setFireworkMeta(fwm);
                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("fir")) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).put("fir", true);
                        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        FireworkEffect.Type type = FireworkEffect.Type.BURST;
                        FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.SILVER).withFade(Color.BLACK).with(type).trail(false).build();
                        fwm.addEffect(effect);
                        fwm.setPower(3);
                        fw.setFireworkMeta(fwm);
                        return true;
                    }
                }
            } else {
                if (Config.isInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat)) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) - Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat));
                    if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) < 1) {
                        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        FireworkEffect.Type type = FireworkEffect.Type.CREEPER;
                        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).withFade(Color.BLACK).with(type).trail(true).build();
                        fwm.addEffect(effect);
                        fwm.setPower(1);
                        fw.setFireworkMeta(fwm);
                        fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        fwm.setPower(2);
                        fw.setFireworkMeta(fwm);
                        fwm.setPower(3);
                        fw.setFireworkMeta(fwm);
                        fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        fwm.setPower(4);
                        fw.setFireworkMeta(fwm);
                        OwnerCommands.Defeated(evillage, playeruuid);
                    } else if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("fir")) {
                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).put("fir", true);
                        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        FireworkEffect.Type type = FireworkEffect.Type.BURST;
                        FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.SILVER).withFade(Color.BLACK).with(type).trail(false).build();
                        fwm.addEffect(effect);
                        fwm.setPower(3);
                        fw.setFireworkMeta(fwm);
                        return true;
                    }
                }
            }
            if (!Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(nmat)) {
                return true;
            }
        }
        return false;
    }

    public static void structureBookWriteUp(String structure, Block block, FileConfiguration tempyaml) {
        ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) is.getItemMeta();
        bm.setTitle(ChatColor.GOLD + "Empirecraft");
        bm.setAuthor(ChatColor.GOLD + "N1T3SLAY3R");
        bm.setDisplayName(ChatColor.AQUA + structure);
        ArrayList<String> pages = new ArrayList<>();
        if (Config.isConfigurationSection("Village Structures." + structure)) {
            tempstring = ChatColor.BLUE + "Type: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Type")
                    + ChatColor.BLUE + "\nCreation Cost: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Creation Cost");
            if (Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                tempstring += ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upkeep")
                        + ChatColor.BLUE + "\nArrow Properties (Type Value): " + ChatColor.AQUA
                        + ChatColor.BLUE + "\nBounce: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Bounce")
                        + ChatColor.BLUE + "\nCritical: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Critical")
                        + ChatColor.BLUE + "\nKnockback: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Knockback")
                        + ChatColor.BLUE + "\nFire Ticks: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Fire Ticks")
                        + ChatColor.BLUE + "\nArrow Speed: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrow Speed")
                        + ChatColor.BLUE + "\nArrow Spread: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrow Spread")
                        + ChatColor.BLUE + "\nArrows Fired: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrows Fired")
                        + ChatColor.BLUE + "\nRange (Chunks): " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Range");
            } else if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal") && !Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
                tempstring += ChatColor.BLUE + "\nIncome Time Delay: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Income Timer")
                        + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upkeep")
                        + ChatColor.BLUE + "\nRevenue: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Revenue");
            }
            if (Config.isConfigurationSection("Village Structures." + structure + ".Upgraded From")) {
                tempstring += ChatColor.BLUE + "\nUpgraded From: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upgraded From");
            }
            tempstring += ChatColor.BLUE + "\nTotal Hp: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Total Hp");
            pages.add(tempstring);
            if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal") && !Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
                if (Config.isList("Village Structures." + structure + ".Required Materials")) {
                    tempstring = ChatColor.BLUE + "Required Materials (Type/Amount): " + ChatColor.AQUA;
                    Config.getStringList("Village Structures." + structure + ".Required Materials").stream().map((s) -> s.split(":")).forEach((req) -> {
                        tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                    });
                    pages.add(tempstring);
                }
                if (Config.isList("Village Structures." + structure + ".Produced Materials")) {
                    tempstring = ChatColor.BLUE + "Produced Materials (Type/Amount): " + ChatColor.AQUA;
                    Config.getStringList("Village Structures." + structure + ".Produced Materials").stream().map((s) -> s.split(":")).forEach((req) -> {
                        tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                    });
                    pages.add(tempstring);
                }
            } else {
                for (String m : Config.getConfigurationSection("Village Structures." + structure + ".Productions").getKeys(false)) {
                    tempstring = ChatColor.BLUE + "Production " + ChatColor.AQUA + m + ChatColor.BLUE + ":"
                            + ChatColor.BLUE + "\nIncome Time Delay: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Productions." + m + ".Income Timer")
                            + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Productions." + m + ".Upkeep")
                            + ChatColor.BLUE + "\nRevenue: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Productions." + m + ".Revenue");
                    if (Config.isList("Village Structures." + structure + ".Productions." + m + ".Animals")) {
                        tempstring += ChatColor.BLUE + "\nAnimals Spawned: " + ChatColor.AQUA;
                        Config.getStringList("Village Structures." + structure + ".Productions." + m + ".Animals").stream().map((s) -> s.split(":")).forEach((req) -> {
                            tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                        });
                        pages.add(tempstring);
                    }
                    if (!Config.getList("Village Structures." + structure + ".Productions." + m + ".Required Materials").isEmpty() || !Config.getList("Village Structures." + structure + ".Productions." + m + ".Produced Materials").isEmpty()) {
                        if (!Config.getList("Village Structures." + structure + ".Productions." + m + ".Required Materials").isEmpty()) {
                            tempstring += ChatColor.BLUE + "\nRequired Materials: " + ChatColor.AQUA;
                            Config.getStringList("Village Structures." + structure + ".Productions." + m + ".Required Materials").stream().map((s) -> s.split(":")).forEach((req) -> {
                                tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                            });
                            pages.add(tempstring);
                        }
                        if (!Config.getList("Village Structures." + structure + ".Productions." + m + ".Produced Materials").isEmpty()) {
                            tempstring += ChatColor.BLUE + "\nProduced Materials: " + ChatColor.AQUA;
                            Config.getStringList("Village Structures." + structure + ".Productions." + m + ".Produced Materials").stream().map((s) -> s.split(":")).forEach((req) -> {
                                tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                            });
                            pages.add(tempstring);
                        }
                    } else {
                        pages.add(tempstring);
                    }
                }
            }
        } else {
            tempstring = ChatColor.BLUE + "Max Plots: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Max Plots")
                    + ChatColor.BLUE + "\nCreation Cost: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Creation Cost")
                    + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Upkeep")
                    + ChatColor.BLUE + "\nMax Villagers: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Max Villagers");
            if (Config.isConfigurationSection("Village Ranks." + structure + ".Upgraded From")) {
                tempstring += ChatColor.BLUE + "\nUpgraded From: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Upgraded From");
            }
            tempstring += ChatColor.BLUE + "\nTotal Hp: " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Total Hp");
            pages.add(tempstring);
            if (Config.isList("Village Ranks." + structure + ".Structure Limits")) {
                tempstring = ChatColor.BLUE + "Structure Limits (Name/Amount): " + ChatColor.AQUA;
                Config.getStringList("Village Structures." + structure + ".Structure Limits").stream().map((s) -> s.split(":")).forEach((req) -> {
                    tempstring += "\n" + req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                });
                pages.add(tempstring);
            }
        }
        tempHashMap.put("temp", new HashMap<>());
        if (!isMultiType(structure)) {
            for (String y : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                for (String x : tempyaml.getConfigurationSection("Scematic." + y).getKeys(false)) {
                    for (String z : tempyaml.getConfigurationSection("Scematic." + y + "." + x).getKeys(false)) {
                        if (tempHashMap.get("temp").containsKey(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id"))) {
                            tempHashMap.get("temp").put(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id"), ((Integer) tempHashMap.get("temp").get(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id"))) + 1);
                        } else if (tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("WOOL") || tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("SANDSTONE") || tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("WOOD") || tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("LOG") || tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("WOOD_STEP") || tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("STEP")) {
                            if (tempHashMap.get("temp").containsKey(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + y + "." + x + "." + z + ".typ"))) {
                                tempHashMap.get("temp").put(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + y + "." + x + "." + z + ".typ"), ((Integer) tempHashMap.get("temp").get(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + y + "." + x + "." + z + ".typ"))) + 1);
                            } else {
                                tempHashMap.get("temp").put(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + y + "." + x + "." + z + ".typ"), 1);
                            }
                        } else {
                            tempHashMap.get("temp").put(tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id"), 1);
                        }
                    }
                }
            }
        } else {
            for (String cx : tempyaml.getConfigurationSection("Scematic").getKeys(false)) {
                for (String cz : tempyaml.getConfigurationSection("Scematic." + cx).getKeys(false)) {
                    for (String y : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz).getKeys(false)) {
                        for (String x : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + y).getKeys(false)) {
                            for (String z : tempyaml.getConfigurationSection("Scematic." + cx + "." + cz + "." + y + "." + x).getKeys(false)) {
                                if (tempHashMap.get("temp").containsKey(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id"))) {
                                    tempHashMap.get("temp").put(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id"), ((Integer) tempHashMap.get("temp").get(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id"))) + 1);
                                } else if (tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("WOOL") || tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("SANDSTONE") || tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("WOOD") || tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("LOG") || tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("WOOD_STEP") || tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id").equals("STEP")) {
                                    if (tempHashMap.get("temp").containsKey(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".typ"))) {
                                        tempHashMap.get("temp").put(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".typ"), ((Integer) tempHashMap.get("temp").get(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".typ"))) + 1);
                                    } else {
                                        tempHashMap.get("temp").put(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id") + "_" + tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".typ"), 1);
                                    }
                                } else {
                                    tempHashMap.get("temp").put(tempyaml.getString("Scematic." + cx + "." + cz + "." + y + "." + x + "." + z + ".id"), 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        tempstring = ChatColor.BLUE + "Building Materials (Type/Amount/Hp): ";
        if (Config.isConfigurationSection("Village Structures." + structure)) {
            tempHashMap.get("temp").keySet().stream().map((n) -> {
                tempstring += "\n" + ChatColor.AQUA + n + ChatColor.BLUE + " / " + ChatColor.AQUA + tempHashMap.get("temp").get(n).toString() + ChatColor.BLUE + " / " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Block Hp." + n);
                return n;
            }).filter((_item) -> (tempstring.length() > 200)).map((_item) -> {
                pages.add(tempstring);
                return _item;
            }).forEach((_item) -> {
                tempstring = ChatColor.BLUE + "Building Materials (Type/Amount/Hp): ";
            });
        } else {
            tempHashMap.get("temp").keySet().stream().map((n) -> {
                tempstring += "\n" + ChatColor.AQUA + n + ChatColor.BLUE + " / " + ChatColor.AQUA + tempHashMap.get("temp").get(n).toString() + ChatColor.BLUE + " / " + ChatColor.AQUA + Config.get("Village Ranks." + structure + ".Block Hp." + n);
                return n;
            }).filter((_item) -> (tempstring.length() > 200)).map((_item) -> {
                pages.add(tempstring);
                return _item;
            }).forEach((_item) -> {
                tempstring = ChatColor.BLUE + "Building Materials (Type/Amount/Hp): ";
            });
        }
        pages.add(tempstring);
        bm.setPages(pages);
        is.setItemMeta(bm);
        ((InventoryHolder) block.getState()).getInventory().addItem(is);
    }

    public static boolean isNotLimitedStructure(String structure, String villagerank) {
        if (Config.isList("Village Ranks." + villagerank + ".Structure Limits")) {
            String[] spl;
            int temp = 0;
            for (String s : Config.getStringList("Village Ranks." + villagerank + ".Structure Limits")) {
                spl = s.split(":");
                if (spl[0].equals(structure)) {
                    for (String w : serverdata.get("worldmap").keySet()) {
                        for (Object x : serverdata.get("worldmap").get(w).keySet()) {
                            for (Object z : ((HashMap) serverdata.get("worldmap").get(w).get(x)).keySet()) {
                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).containsKey("str")) {
                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("str").equals(structure)) {
                                        temp++;
                                    }
                                }
                            }
                        }
                    }
                    if (temp >= Integer.parseInt(spl[1])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean structureIncomeCheck(Long time, String structure, String w, Object x, Object z) {
        if (!Config.getString("Village Structures." + structure + ".Type").equals("Normal")&&!Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
            if (time % ((Integer) tempHashMap.get("incometimer").get(structure)) == 0) {
                return true;
            }
        } else if (time % Config.getInt("Village Structures." + structure + ".Productions." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(w).get(x)).get(z)).get("pro") + ".Income Timer") == 0) {
            return true;
        }
        return false;
    }

    public static boolean isPlayerInArrayList(HashMap<String, ArrayList> hashmap, String part, String playername) {
        if (hashmap != null) {
            if (hashmap.get(part) != null) {
                if (hashmap.get(part).contains(playername)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isPartInHashMap(HashMap<String, HashMap> hashmap, String part, String target) {
        if (hashmap != null) {
            if (hashmap.get(part) != null) {
                if (hashmap.get(part).containsKey(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWorldChunkClaimed(HashMap<Integer, HashMap<Integer, HashMap<String, HashMap>>> hashmap, int X, int Z, String ownedby) {
        if (hashmap != null) {
            if (hashmap.get(X) != null) {
                if (hashmap.get(X).get(Z) != null) {
                    if (hashmap.get(X).get(Z).containsKey(ownedby)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isVillageAlliedOrYoursEmpireWise(String village, String playerempire) {
        for (String v : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("vils"))) {
            if (v.equals(village)) {
                return true;
            }
        }
        if (serverdata.get("empires").get(playerempire).containsKey("all")) {
            for (String e : ((ArrayList<String>) serverdata.get("empires").get(playerempire).get("all"))) {
                for (String v : ((ArrayList<String>) serverdata.get("empires").get(e).get("vils"))) {
                    if (v.equals(village)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNearByVillageYours(HashMap<Integer, HashMap<Integer, HashMap<String, String>>> hashmap, int X, int Z, String playervillage) {
        for (int x = -1; x < 2; x += 2) {
            if (hashmap != null) {
                if (hashmap.get(X + x) != null) {
                    if (hashmap.get(X + x).get(Z) != null) {
                        if (hashmap.get(X + x).get(Z).containsKey("cla")) {
                            if (hashmap.get(X + x).get(Z).get("cla").equals(playervillage)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        for (int z = -1; z < 2; z += 2) {
            if (hashmap != null) {
                if (hashmap.get(X) != null) {
                    if (hashmap.get(X).get(Z + z) != null) {
                        if (hashmap.get(X).get(Z + z).containsKey("cla")) {
                            if (hashmap.get(X).get(Z + z).get("cla").equals(playervillage)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void onPluginSave() {
        try {
            SLAPI.save(serverdata, serverdataFile);
        } catch (Exception ex) {
            Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Config.getString("Global Settings.Backup files").equals("on")) {
            File backupFolder = new File(pluginFolder, "Backup Files");
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }
            File backupFile = new File(backupFolder, (System.currentTimeMillis() / 1000) + ".bin");
            if (!backupFile.exists()) {
                try {
                    backupFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                SLAPI.save(serverdata, backupFile);
            } catch (Exception ex) {
                Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (Config.getString("Global Settings.Villagesyml file").equals("on")) {
            if (!villagesFile.exists()) {
                try {
                    villagesFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            serverdata.get("villages").keySet().stream().map((s) -> {
                Villages.set(s + ".owner", serverdata.get("villages").get(s).get("own"));
                if (serverdata.get("villages").get(s).get("app") != null) {
                    Villages.set(s + ".applications", serverdata.get("villages").get(s).get("app"));
                } else {
                    Villages.set(s + ".applications", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("mem") != null) {
                    Villages.set(s + ".members", serverdata.get("villages").get(s).get("mem"));
                } else {
                    Villages.set(s + ".members", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("man") != null) {
                    Villages.set(s + ".managers", serverdata.get("villages").get(s).get("man"));
                } else {
                    Villages.set(s + ".managers", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("all") != null) {
                    Villages.set(s + ".allies", serverdata.get("villages").get(s).get("all"));
                } else {
                    Villages.set(s + ".allies", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("ene") != null) {
                    Villages.set(s + ".enemies", serverdata.get("villages").get(s).get("ene"));
                } else {
                    Villages.set(s + ".enemies", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("trr") != null) {
                    if (!((ArrayList) (serverdata.get("villages").get(s).get("trr"))).isEmpty()) {
                        Villages.set(s + ".trucerequest", serverdata.get("villages").get(s).get("trr"));
                    } else {
                        Villages.set(s + ".trucerequest", null);
                    }
                } else {
                    Villages.set(s + ".trucerequest", null);
                }
                return s;
            }).forEach((s) -> {
                if (serverdata.get("villages").get(s).get("alr") != null) {
                    if (!((ArrayList) (serverdata.get("villages").get(s).get("alr"))).isEmpty()) {
                        Villages.set(s + ".allyrequest", serverdata.get("villages").get(s).get("alr"));
                    } else {
                        Villages.set(s + ".allyrequest", null);
                    }
                } else {
                    Villages.set(s + ".allyrequest", null);
                }
            });
            try {
                Villages.save(villagesFile);
            } catch (IOException ex) {
                Logger.getLogger(MainConversions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
