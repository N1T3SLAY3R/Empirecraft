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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 * @author dylan
 */
public class OnCreatureSpawn implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        String world = entity.getWorld().getUID().toString();
        Integer x = entity.getLocation().getChunk().getX(), z = entity.getLocation().getChunk().getZ();
        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            String pvillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
            if (serverdata.get("villages").get(pvillage).containsKey("mobs")) {
                if (serverdata.get("villages").get(pvillage).get("mobs").equals("off")) {
                    event.setCancelled(true);
                }
            } else if (Config.getString("Village Settings.Toggle Settings.Mobs Enabled").equals("off")) {
                event.setCancelled(true);
            }
        }
    }
}
