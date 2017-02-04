/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.MainCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author dylan
 */
public class Apply {
    public static void Apply(String targetvillage, String playername) {
        if (serverdata.get("villages").get(targetvillage).get("app") == null) {
            serverdata.get("villages").get(targetvillage).put("app", new ArrayList<>());
        }
        ((ArrayList) serverdata.get("villages").get(targetvillage).get("app")).add(playername);
        Bukkit.getPlayer(UUID.fromString(playername)).sendMessage(ChatColor.BLUE + "Your application to " + ChatColor.AQUA + targetvillage + ChatColor.BLUE + " has been sent successfully");
        if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(targetvillage).get("own").toString())).isOnline()) {
            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(targetvillage).get("own").toString())).sendMessage(ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + " has sent an application to join the village, to accept use /vil manage accept " + Bukkit.getPlayer(UUID.fromString(playername)).getName());
        }
        if (serverdata.get("villages").get(targetvillage) != null) {
            if (serverdata.get("villages").get(targetvillage).get("man") != null) {
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get("villages").get(targetvillage).get("man"));
                temparraylist.stream().filter((p) -> (Bukkit.getOfflinePlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                    Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + Bukkit.getPlayer(UUID.fromString(playername)).getName() + ChatColor.DARK_PURPLE + " has sent an application to join the village, to accept use /vil manage accept " + Bukkit.getPlayer(UUID.fromString(playername)).getName());
                });
            }
        }
    }
}
