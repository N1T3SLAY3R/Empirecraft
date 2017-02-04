/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Uncategorized;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 *
 * @author dylan
 */
public class StructureBookWriteUp {
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
        if (!QuickChecks.isMultiType(structure)) {
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
}
