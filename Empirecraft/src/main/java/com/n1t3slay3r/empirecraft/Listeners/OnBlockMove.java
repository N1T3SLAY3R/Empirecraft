/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.HashMap;
import static org.bukkit.Material.LAVA;
import static org.bukkit.Material.STATIONARY_LAVA;
import static org.bukkit.Material.STATIONARY_WATER;
import static org.bukkit.Material.WATER;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

/**
 *
 * @author dylan
 */
public class OnBlockMove implements Listener {
     //LIQUIDS such as water or lava or dragon egg tps
    @EventHandler
    public void onBlockMove(BlockFromToEvent event) {
        Block block = event.getBlock(), to = event.getToBlock();
        String world = block.getWorld().getUID().toString();
        Integer x = to.getLocation().getChunk().getX(), z = to.getLocation().getChunk().getZ();
        if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                if (block.getType().equals(WATER)) {
                    block.setType(STATIONARY_WATER);
                } else if (block.getType().equals(LAVA)) {
                    block.setType(STATIONARY_LAVA);
                }
                event.setCancelled(true);
            }
        }
    }
}
