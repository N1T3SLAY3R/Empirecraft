/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

/**
 *
 * @author dylan
 */
public class OnEntityBlockForm implements Listener {
    @EventHandler
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        Block block = event.getBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = block.getLocation().getChunk().getX(), z = block.getLocation().getChunk().getZ();
        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                event.setCancelled(true);
            }
        }
    }
}
