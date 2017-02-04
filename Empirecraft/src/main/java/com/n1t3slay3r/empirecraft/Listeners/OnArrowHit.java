/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static java.lang.Math.abs;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

/**
 *
 * @author dylan
 */
public class OnArrowHit implements Listener{
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntityType().toString().equals("ARROW") && event.getEntity().getShooter() == null) {
            Entity e = event.getEntity();
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()), e.getLocation().getChunk().getX(), e.getLocation().getChunk().getZ(), "str")) {
                if (Config.getString("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()).get(e.getLocation().getChunk().getX())).get(e.getLocation().getChunk().getZ())).get("str").toString() + ".Type").equals("Archer")) {
                    Vector velocity = e.getVelocity();
                    Location loc = e.getLocation().add(velocity.normalize());
                    Vector temp = new Vector(0, abs(e.getFallDistance()) + 1, 0);
                    Bukkit.getWorld(e.getLocation().getWorld().getUID()).spawnArrow(loc, velocity.subtract(temp), (float) (Config.getDouble("Village Structures." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(e.getLocation().getWorld().getUID().toString()).get(e.getLocation().getChunk().getX())).get(e.getLocation().getChunk().getZ())).get("str").toString() + ".Arrow Speed")), 0);
                    e.remove();
                }
            }
        }
    }
}
