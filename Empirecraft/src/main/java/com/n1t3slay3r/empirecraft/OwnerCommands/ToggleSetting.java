/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.OwnerCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author dylan
 */
public class ToggleSetting {
    public static void ToggleSetting(String playervillage, CommandSender sender, String[] args) {
        switch (args[2]) {
            case "fire":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("fire", "on");
                        sender.sendMessage(ChatColor.BLUE + "Fire has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("fire", "off");
                        sender.sendMessage(ChatColor.BLUE + "Fire has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "pvp":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("pvp", "on");
                        sender.sendMessage(ChatColor.BLUE + "Pvp has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("pvp", "off");
                        sender.sendMessage(ChatColor.BLUE + "Pvp has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "explosions":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("expl", "on");
                        sender.sendMessage(ChatColor.BLUE + "Explosions has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("expl", "off");
                        sender.sendMessage(ChatColor.BLUE + "Explosions has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            case "mobs":
                switch (args[3]) {
                    case "on":
                        serverdata.get("villages").get(playervillage).put("mobs", "on");
                        sender.sendMessage(ChatColor.BLUE + "Mob spawning has been enabled in the entire village");
                        break;
                    case "off":
                        serverdata.get("villages").get(playervillage).put("mobs", "off");
                        sender.sendMessage(ChatColor.BLUE + "Mob spawning has been disabled in the entire village");
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the value to on or off");
                        break;
                }
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + args[2] + " is not a valid setting to toggle, you can can change fire, pvp, explosions, and mobs");
                break;

        }
    }
}
