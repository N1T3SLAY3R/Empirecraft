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
import org.bukkit.event.inventory.CraftItemEvent;

/**
 *
 * @author dylan
 */
public class OnCraftItem implements Listener{
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Config.getStringList("Global Settings.Uncraftable Items").stream().filter((s) -> (event.getCurrentItem().getType().equals(Material.getMaterial(s)))).forEach((_item) -> {
            event.setCancelled(true);
        });
    }
}
