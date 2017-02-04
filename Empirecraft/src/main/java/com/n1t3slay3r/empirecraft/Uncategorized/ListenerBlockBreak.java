/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Uncategorized;

import com.n1t3slay3r.empirecraft.OwnerCommands.Defeated;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.structureFolder;
import static com.n1t3slay3r.empirecraft.main.Main.tempfile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Step;
import org.bukkit.material.Tree;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

/**
 *
 * @author dylan
 */
public class ListenerBlockBreak {
    public static boolean listenerBlockBreak(String world, Integer x, Integer z, Player player, Material mat, String evillage, String playeruuid, Block block) {
        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
            String nmat;
            if (mat == null) {
                nmat = null;
            } else switch (mat) {
                case WOOL:
                    nmat = mat.toString() + "_" + ((Wool) block.getState().getData()).getColor().toString();
                    break;
                case SANDSTONE:
                    nmat = mat.toString() + "_" + ((Sandstone) block.getState().getData()).getType().toString();
                    break;
                case WOOD:
                case LOG:
                    nmat = mat.toString() + "_" + ((Tree) block.getState().getData()).getSpecies().toString();
                    break;
                case STEP:
                    nmat = mat.toString() + "_" + ((Step) block.getState().getData()).getMaterial().toString();
                    break;
                case WOOD_STEP:
                    nmat = mat.toString() + "_" + ((WoodenStep) block.getState().getData()).getSpecies().toString();
                    break;
                default:
                    nmat = mat.toString();
                    break;
            }
            if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str"))) {
                if (Config.isInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat)) {
                    if (QuickChecks.isMultiType(((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str").toString())) {
                        x = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("mx"));
                        z = ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("mz"));
                    }
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) - Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + nmat));
                    if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) < 1) {
                        if (QuickChecks.isMultiType(((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str").toString())) {
                            tempfile = new File(structureFolder, ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".yml");
                            FileConfiguration tempyaml = new YamlConfiguration();
                            try {
                                tempyaml.load(tempfile);
                            } catch (IOException | InvalidConfigurationException ex) {
                                Logger.getLogger(ListenerBlockBreak.class.getName()).log(Level.SEVERE, null, ex);
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
                        Defeated.Defeated(evillage, playeruuid);
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
}
