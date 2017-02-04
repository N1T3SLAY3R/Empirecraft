/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import com.n1t3slay3r.empirecraft.Uncategorized.QuickChecks;
import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 *
 * @author dylan
 */
public class OnEntityExplode implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        String world = event.getEntity().getWorld().getUID().toString();
        Integer x, z;
        ArrayList<Block> temparraylist = new ArrayList<>();
        temparraylist.addAll(event.blockList());
        for (Block b : temparraylist) {
            x = b.getLocation().getChunk().getX();
            z = b.getLocation().getChunk().getZ();
            if (QuickChecks.isWorldChunkClaimed(serverdata.get("worldmap").get(world), x, z, "cla")) {
                String pvillage = ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).get("cla").toString();
                if (serverdata.get("villages").get(pvillage).containsKey("expl")) {
                    if (serverdata.get("villages").get(pvillage).get("expl").equals("off") || ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                        event.blockList().remove(b);
                    }
                } else if (Config.getString("Village Settings.Toggle Settings.Explosions Enabled").equals("off") || ((HashMap) ((HashMap) serverdata.get("worldmap").get(world).get(x)).get(z)).containsKey("str")) {
                    event.blockList().remove(b);
                }
            }
        }
    }
}
