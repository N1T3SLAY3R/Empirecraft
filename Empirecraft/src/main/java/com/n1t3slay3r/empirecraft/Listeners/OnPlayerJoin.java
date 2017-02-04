/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Listeners;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author dylan
 */
public class OnPlayerJoin implements Listener{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && Config.getString("Global Settings.Auto Update Notifier").equals("on")) {
            //Update updateCheck = new Update(80075, "ed2919ef1dcca33b92ac5571e73d53ba1e474a4e", player.getUniqueId().toString());
        }
    }
}
