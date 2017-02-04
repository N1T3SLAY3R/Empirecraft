/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.MemberCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.tempHashMap;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author dylan
 */
public class Leave {
    public static void Leave(String playervillage, String playername, Player player, String position) {
        ((ArrayList) serverdata.get("villages").get(playervillage).get(position)).remove(playername);
        if (((ArrayList) serverdata.get("villages").get(playervillage).get(position)).isEmpty()) {
            serverdata.get("villages").get(playervillage).remove(position);
        }
        player.sendMessage(ChatColor.BLUE + "You have successfully left the village " + ChatColor.AQUA + playervillage);
        if (tempHashMap.get("chc").containsKey(playername)) {
            if (tempHashMap.get("chc").get(playername).equals("val") || tempHashMap.get("chc").get(playername).equals("vmal") || tempHashMap.get("chc").get(playername).equals("vally") || tempHashMap.get("chc").get(playername).equals("eal") || tempHashMap.get("chc").get(playername).equals("ealy")) {
                tempHashMap.get("chc").remove(playername);
            }
        }
        serverdata.get("playerdata").remove(playername);
        if (Bukkit.getOfflinePlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).isOnline()) {
            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.DARK_PURPLE + " has left the village");
        }
        temparraylist.clear();
        if (serverdata.get("villages").get(playervillage) != null) {
            if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("mem"));
            }
            if (serverdata.get("villages").get(playervillage).get("man") != null) {
                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("man"));
            }
        }
        temparraylist.stream().filter((p) -> (Bukkit.getOfflinePlayer(p).isOnline())).forEach((p) -> {
            Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.DARK_PURPLE + " has left the village");
        });
    }
}
