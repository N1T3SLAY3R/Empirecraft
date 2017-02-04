/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

/**
 *
 * @author dylan
 */
public class OnSmeltItem implements Listener{
    @EventHandler
    public void onSmeltItem(FurnaceSmeltEvent event) {
        Config.getStringList("Global Settings.Unsmeltable Items").stream().filter((s) -> (event.getResult().getType().equals(Material.getMaterial(s)))).forEach((_item) -> {
            event.setCancelled(true);
        });
    }
}
