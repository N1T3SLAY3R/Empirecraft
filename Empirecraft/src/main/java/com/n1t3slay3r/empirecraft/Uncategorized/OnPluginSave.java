/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.Uncategorized;

import static com.n1t3slay3r.empirecraft.main.Main.Config;
import static com.n1t3slay3r.empirecraft.main.Main.Villages;
import static com.n1t3slay3r.empirecraft.main.Main.pluginFolder;
import static com.n1t3slay3r.empirecraft.main.Main.serverdata;
import static com.n1t3slay3r.empirecraft.main.Main.serverdataFile;
import static com.n1t3slay3r.empirecraft.main.Main.villagesFile;
import com.n1t3slay3r.empirecraft.main.SLAPI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dylan
 */
public class OnPluginSave {
    public static void onPluginSave() {
        try {
            SLAPI.save(serverdata, serverdataFile);
        } catch (Exception ex) {
            Logger.getLogger(OnPluginSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Config.getString("Global Settings.Backup files").equals("on")) {
            File backupFolder = new File(pluginFolder, "Backup Files");
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }
            File backupFile = new File(backupFolder, (System.currentTimeMillis() / 1000) + ".bin");
            if (!backupFile.exists()) {
                try {
                    backupFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(OnPluginSave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                SLAPI.save(serverdata, backupFile);
            } catch (Exception ex) {
                Logger.getLogger(OnPluginSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (Config.getString("Global Settings.Villagesyml file").equals("on")) {
            if (!villagesFile.exists()) {
                try {
                    villagesFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(OnPluginSave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            serverdata.get("villages").keySet().stream().map((s) -> {
                Villages.set(s + ".owner", serverdata.get("villages").get(s).get("own"));
                if (serverdata.get("villages").get(s).get("app") != null) {
                    Villages.set(s + ".applications", serverdata.get("villages").get(s).get("app"));
                } else {
                    Villages.set(s + ".applications", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("mem") != null) {
                    Villages.set(s + ".members", serverdata.get("villages").get(s).get("mem"));
                } else {
                    Villages.set(s + ".members", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("man") != null) {
                    Villages.set(s + ".managers", serverdata.get("villages").get(s).get("man"));
                } else {
                    Villages.set(s + ".managers", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("all") != null) {
                    Villages.set(s + ".allies", serverdata.get("villages").get(s).get("all"));
                } else {
                    Villages.set(s + ".allies", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("ene") != null) {
                    Villages.set(s + ".enemies", serverdata.get("villages").get(s).get("ene"));
                } else {
                    Villages.set(s + ".enemies", null);
                }
                return s;
            }).map((s) -> {
                if (serverdata.get("villages").get(s).get("trr") != null) {
                    if (!((ArrayList) (serverdata.get("villages").get(s).get("trr"))).isEmpty()) {
                        Villages.set(s + ".trucerequest", serverdata.get("villages").get(s).get("trr"));
                    } else {
                        Villages.set(s + ".trucerequest", null);
                    }
                } else {
                    Villages.set(s + ".trucerequest", null);
                }
                return s;
            }).forEach((s) -> {
                if (serverdata.get("villages").get(s).get("alr") != null) {
                    if (!((ArrayList) (serverdata.get("villages").get(s).get("alr"))).isEmpty()) {
                        Villages.set(s + ".allyrequest", serverdata.get("villages").get(s).get("alr"));
                    } else {
                        Villages.set(s + ".allyrequest", null);
                    }
                } else {
                    Villages.set(s + ".allyrequest", null);
                }
            });
            try {
                Villages.save(villagesFile);
            } catch (IOException ex) {
                Logger.getLogger(OnPluginSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
