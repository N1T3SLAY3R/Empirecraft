/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.HashMap;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author dylan
 */
public class OnPvP implements Listener {
    @EventHandler
    public void onPvP(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(PLAYER)) {
            String playername = ((Player) entity).getName();
            String world = entity.getWorld().getUID().toString();
            Integer cx = entity.getLocation().getChunk().getX(), cz = entity.getLocation().getChunk().getZ();
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), cx, cz, "cla")) {
                String evillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(cx)).get(cz)).get("cla").toString();
                if (QuickChecks.isPartInHashMap(serverdata.get("playerdata"), playername, "village")) {
                    String pvillage = serverdata.get("playerdata").get(playername).get("village").toString();
                    if (pvillage.equals(evillage)) {
                        if (serverdata.get("villages").get(pvillage).containsKey("pvp")) {
                            if (serverdata.get("villages").get(pvillage).get("pvp").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (QuickChecks.isPartInHashMap(serverdata.get("villages").get(evillage), "ene", pvillage)) {
                        //Always allow damage
                    } else if (QuickChecks.isEnemyEmpire(pvillage, evillage)) {
                        //Always allow damage
                    } else {
                        if (serverdata.get("villages").get(pvillage).containsKey("pvp")) {
                            if (serverdata.get("villages").get(pvillage).get("pvp").equals("off")) {
                                event.setCancelled(true);
                            }
                        } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                            event.setCancelled(true);
                        }
                    }
                } else {
                    if (serverdata.get("villages").get(evillage).containsKey("pvp")) {
                        if (serverdata.get("villages").get(evillage).get("pvp").equals("off")) {
                            event.setCancelled(true);
                        }
                    } else if (Config.getString("Village Settings.Toggle Settings.Pvp Enabled").equals("off")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
