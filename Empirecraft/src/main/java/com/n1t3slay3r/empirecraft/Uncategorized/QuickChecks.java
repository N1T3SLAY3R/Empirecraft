/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Uncategorized;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author dylan
 */
public class QuickChecks {
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
    
    public static boolean isEnemyEmpire(String pvillage, String evillage) {
        if (serverdata.get("villages").get(pvillage).containsKey("emp") && serverdata.get("villages").get(evillage).containsKey("emp")) {
            if (serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).containsKey("ene")) {
                if (((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(pvillage).get("emp").toString()).get("ene")).containsKey(serverdata.get("empires").get(serverdata.get("villages").get(evillage).get("emp").toString()))) {
                    return true;
                }
            }
        }
        return false;
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
    
    public static boolean isMulti(Chunk c, String structure, FileConfiguration file, String dir, String pvil) {
        if (Config.getString("Village Structures." + structure + ".Type").equals("Multi")) {
            for (String x : file.getConfigurationSection("Scematic").getKeys(false)) {
                for (String z : file.getConfigurationSection("Scematic." + x).getKeys(false)) {
                    if (dir.equalsIgnoreCase("n")) {
                        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(c.getWorld().getUID().toString()), Integer.parseInt(x) * -1 + c.getX(), Integer.parseInt(z) * -1 + c.getZ(), "cla")) {
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
}
