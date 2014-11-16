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
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;

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

    public static boolean listenerBlockBreak(String world, Integer x, Integer z, Player player, Material mat, String evillage, String playeruuid) {
        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
            if (Config.isConfigurationSection("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str"))) {
                if (Config.isInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + mat.toString())) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) - Config.getInt("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + mat.toString()));
                    if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) < 1) {
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
                    } else {
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
                if (Config.isInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + mat.toString())) {
                    ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).replace("hp", ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("hp")) - Config.getInt("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("str") + ".Block Hp." + mat.toString()));
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
                    } else {
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
            if (!Config.getStringList("Village Settings.Placeable/Destroyble Blocks In Structures").contains(mat.toString())) {
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
            if (!Config.getString("Village Structures." + structure + ".Type").equals("Archer")) {
                tempstring = ChatColor.BLUE + "Type: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Type")
                        + ChatColor.BLUE + "\nCreation Cost: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Creation Cost")
                        + ChatColor.BLUE + "\nIncome Time Delay: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Income Timer")
                        + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upkeep")
                        + ChatColor.BLUE + "\nRevenue: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Revenue");
            } else {
                tempstring = ChatColor.BLUE + "Type: " + ChatColor.AQUA + "Archer"
                        + ChatColor.BLUE + "\nCreation Cost: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Creation Cost")
                        + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upkeep")
                        + ChatColor.BLUE + "\nArrow Properties (Type Value): " + ChatColor.AQUA
                        + ChatColor.BLUE + "\nBounce: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Bounce")
                        + ChatColor.BLUE + "\nCritical: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Critical")
                        + ChatColor.BLUE + "\nKnockback: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Knockback")
                        + ChatColor.BLUE + "\nFire Ticks: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Fire Ticks")
                        + ChatColor.BLUE + "\nArrow Speed: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrow Speed")
                        + ChatColor.BLUE + "\nArrow Spread: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrow Spread")
                        + ChatColor.BLUE + "\nArrows Fired: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Arrows Fired")
                        + ChatColor.BLUE + "\nRange (Chunks): " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Range");
            }
            if (Config.isConfigurationSection("Village Structures." + structure + ".Upgraded From")) {
                tempstring += ChatColor.BLUE + "\nUpgraded From: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Upgraded From");
            }
            tempstring += ChatColor.BLUE + "\nTotal Hp: " + ChatColor.AQUA + Config.get("Village Structures." + structure + ".Total Hp");
            pages.add(tempstring);
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
        for (int y = 1; y <= tempyaml.getConfigurationSection("Scematic").getKeys(false).size(); y++) {
            for (int x = 1; x < 17; x++) {
                for (int z = 1; z < 17; z++) {
                    if (!tempyaml.getString("Scematic." + y + "." + x + "." + z + ".id").equals("AIR")) {
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
