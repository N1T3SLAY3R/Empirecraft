/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.DiplomacyCommands;

import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.temparraylist;
import static com.n1t3slay3r.empirecraft.main.Main.tempstring;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author dylan
 */
public class RequestList {
    public static void RequestList(String section, String playersection, CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + "Ally Requests");
        if (serverdata.get(section).get(playersection).get("alr") != null) {
            if (serverdata.get(section).get(playersection).get("alr") != null) {
                tempstring = ChatColor.AQUA + "";
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get(section).get(playersection).get("alr"));
                ((ArrayList) serverdata.get(section).get(playersection).get("alr")).stream().map((s) -> {
                    tempstring += s;
                    return s;
                }).map((s) -> {
                    temparraylist.remove((String) s);
                    return s;
                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                });
                sender.sendMessage(tempstring);
            } else {
                sender.sendMessage(ChatColor.AQUA + "None");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "None");
        }
        sender.sendMessage(ChatColor.BLUE + "Truce Requests");
        if (serverdata.get(section).get(playersection).get("trr") != null) {
            if (serverdata.get(section).get(playersection).get("trr") != null) {
                tempstring = ChatColor.AQUA + "";
                temparraylist.clear();
                temparraylist.addAll((ArrayList) serverdata.get(section).get(playersection).get("trr"));
                ((ArrayList) serverdata.get(section).get(playersection).get("trr")).stream().map((s) -> {
                    tempstring += s;
                    return s;
                }).map((s) -> {
                    temparraylist.remove((String) s);
                    return s;
                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                });
                sender.sendMessage(tempstring);
            } else {
                sender.sendMessage(ChatColor.AQUA + "None");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "None");
        }
    }
}
