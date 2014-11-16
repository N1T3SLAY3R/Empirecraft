package com.n1t3slay3r.empirecraft.main;

import com.n1t3slay3r.empirecraft.Commands.DiplomacyCommands;
import com.n1t3slay3r.empirecraft.Commands.MainCommands;
import com.n1t3slay3r.empirecraft.Commands.MainConversions;
import com.n1t3slay3r.empirecraft.Commands.ManageCommands;
import com.n1t3slay3r.empirecraft.Commands.MemberCommands;
import com.n1t3slay3r.empirecraft.Commands.OwnerCommands;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unchecked")
public class Main extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
    private static int disabled = 0;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            disabled = 1;
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            disabled = 2;
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /*private boolean setupChat() {
     RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
     chat = rsp.getProvider();
     return chat != null;
     }

     private boolean setupPermissions() {
     RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
     perms = rsp.getProvider();
     return perms != null;
     }*/
    public static File pluginFolder;
    public static File structureFolder;
    public static File configFile;
    public static FileConfiguration Config;
    public static File villagesFile;
    public static FileConfiguration Villages;
    public static File serverdataFile;
    public static File tempfile;
    public static String tempstring;
    public static ArrayList<String> temparraylist = new ArrayList<>();
    public static Map<String, HashMap<String, HashMap>> serverdata = new HashMap<>();
    public static Map<String, HashMap> tempHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //setupPermissions();
        //setupChat();
        pluginFolder = getDataFolder();
        Config = new YamlConfiguration();
        Villages = new YamlConfiguration();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }
        configFile = new File(pluginFolder, "Config.yml");
        structureFolder = new File(pluginFolder, "Structure Layout Files");
        villagesFile = new File(pluginFolder, "Villages.yml");
        serverdataFile = new File(pluginFolder, "Serverdata.bin");
        if (!structureFolder.exists()) {
            structureFolder.mkdir();
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Config.load(configFile);
            } catch (IOException | InvalidConfigurationException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!serverdataFile.exists()) {
            try {
                serverdataFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                serverdata = SLAPI.load(serverdataFile);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Config.options().header("Configuration Help: http://dev.bukkit.org/bukkit-plugins/empirecraft/pages/configuration-info/");
        Bukkit.getWorlds().stream().forEach((w) -> {
            temparraylist.add(w.getName());
        });
        Config.addDefault("Global Settings.Worlds Enabled", temparraylist.toArray());
        Config.addDefault("Global Settings.Auto Update Notifier", "on");
        Config.addDefault("Global Settings.Auto Save Interval", 300);
        Config.addDefault("Global Settings.Villagesyml file", "off");
        Config.addDefault("Global Settings.Backup files", "on");
        Config.addDefault("Global Settings.Local Chat Range", 6);
        Config.addDefault("Village Settings.Require Materials To Build", "on");
        Config.addDefault("Village Settings.Creation Cost", 1000);
        Config.addDefault("Village Settings.Initial Cash In Village Vault", 100);
        Config.addDefault("Village Settings.Name Max Length", 15);
        Config.addDefault("Village Settings.Home Teleport Delay", 2);
        Config.addDefault("Village Settings.Build Delay", 2);
        Config.addDefault("Village Settings.Destruction Delay", 1);
        Config.addDefault("Village Settings.Tax Delay", 86400);
        Config.addDefault("Village Settings.War Time Delay", 86400);
        Config.addDefault("Village Settings.Debt Before Village Loss", 0);
        Config.addDefault("Village Settings.Toggle Settings.Fire Enabled", "on");
        Config.addDefault("Village Settings.Toggle Settings.Pvp Enabled", "on");
        Config.addDefault("Village Settings.Toggle Settings.Explosions Enabled", "on");
        Config.addDefault("Village Settings.Toggle Settings.Mobs Enabled", "on");
        Config.addDefault("Village Settings.Regions To Ignore", new ArrayList());
        Config.addDefault("Village Settings.Default Rank", "CHANGE THIS");
        temparraylist.clear();
        temparraylist.add("LADDER");
        temparraylist.add("TORCH");
        temparraylist.add("REDSTONE_TORCH_ON");
        temparraylist.add("REDSTONE_TORCH_OFF");
        Config.addDefault("Village Settings.Placeable/Destroyble Blocks In Structures", temparraylist.toArray());
        temparraylist.clear();
        temparraylist.add("REDSTONE_TORCH_OFF:REDSTONE_TORCH_ON");
        temparraylist.add("WATER_BUCKET:WATER");
        temparraylist.add("LAVA_BUCKET:LAVA");
        temparraylist.add("DIODE_BLOCK_OFF:DIODE_BLOCK_ON");
        temparraylist.add("REDSTONE_COMPARATOR_OFF:REDSTONE_COMPARATOR_ON");
        temparraylist.add("REDSTONE_LAMP_OFF:REDSTONE_LAMP_ON");
        temparraylist.add("DIRT:GRASS");
        Config.addDefault("Village Settings.Block Equivalents", temparraylist.toArray());
        Config.addDefault("Empire Settings.Creation Cost", 10000);
        Config.addDefault("Empire Settings.Initial Cash In Empires Vault", 1000);
        Config.addDefault("Empire Settings.Name Max Length", 15);
        //Allow multiple tps based on number ov villages in empire
        Config.addDefault("Empire Settings.Teleport Delay", 2);
        Config.addDefault("Empire Settings.Tax Delay", 86400);
        Config.addDefault("Empire Settings.War Time Delay", 172800);
        Config.addDefault("Empire Settings.Debt Before Empire Loss", 0);
        Config.addDefault("Empire Settings.Default Rank", "Example 1");
        Config.addDefault("Player Plots.Members.Modify", "off");
        Config.addDefault("Player Plots.Members.Doors", "on");
        Config.addDefault("Player Plots.Members.Buttons", "on");
        Config.addDefault("Player Plots.Members.Levers", "on");
        Config.addDefault("Player Plots.Members.Containers", "off");
        Config.addDefault("Player Plots.Allys.Modify", "off");
        Config.addDefault("Player Plots.Allys.Doors", "on");
        Config.addDefault("Player Plots.Allys.Buttons", "on");
        Config.addDefault("Player Plots.Allys.Levers", "on");
        Config.addDefault("Player Plots.Allys.Containers", "off");
        Config.addDefault("Player Plots.Outsiders.Modify", "off");
        Config.addDefault("Player Plots.Outsiders.Doors", "off");
        Config.addDefault("Player Plots.Outsiders.Buttons", "off");
        Config.addDefault("Player Plots.Outsiders.Levers", "off");
        Config.addDefault("Player Plots.Outsiders.Containers", "off");
        if (!Config.isList("Empire Ranks")) {
            Config.addDefault("Empire Ranks.Example 1.Number of creatable teleport locations", 2);
            Config.addDefault("Empire Ranks.Example 1.Upkeep", 500);
            Config.addDefault("Empire Ranks.Example 1.Revenue", 0);
            Config.addDefault("Empire Ranks.Example 1.Maximum villages allowed in empire", 4);
            Config.addDefault("Empire Ranks.Example 2.Number of creatable teleport locations", 4);
            Config.addDefault("Empire Ranks.Example 2.Upkeep", 400);
            Config.addDefault("Empire Ranks.Example 2.Revenue", 0);
            Config.addDefault("Empire Ranks.Example 2.Upgraded From", "Example 1");
            Config.addDefault("Empire Ranks.Example 2.Upgrade Cost", 15000);
            Config.addDefault("Empire Ranks.Example 2.Maximum villages allowed in empire", 8);
        }
        Config.options().copyDefaults(true);
        try {
            Config.save(configFile);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Config.load(configFile);
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (serverdata.get("empires") == null) {
            serverdata.put("empires", new HashMap<>());
        }
        if (serverdata.get("villages") == null) {
            serverdata.put("villages", new HashMap<>());
        }
        if (serverdata.get("playerdata") == null) {
            serverdata.put("playerdata", new HashMap<>());
        }
        if (serverdata.get("worldmap") == null) {
            serverdata.put("worldmap", new HashMap<>());
        }
        tempHashMap.put("mainchest", new HashMap<>());
        tempHashMap.put("tpx", new HashMap<>());
        tempHashMap.put("tpy", new HashMap<>());
        tempHashMap.put("tpz", new HashMap<>());
        tempHashMap.put("incometimer", new HashMap<>());
        tempHashMap.put("lcx", new HashMap<>());
        tempHashMap.put("lcz", new HashMap<>());
        tempHashMap.put("chc", new HashMap<>());
        tempHashMap.put("arr", new HashMap<>());
        if (Config.isList("Village Settings.Block Equivalents")) {
            tempHashMap.put("ble", new HashMap<>());
            tempHashMap.get("ble").put("a", Config.getStringList("Village Settings.Block Equivalents"));
        }
        if (Config.isConfigurationSection("Village Structures")) {
            Config.getConfigurationSection("Village Structures").getKeys(false).stream().forEach((s) -> {
                tempHashMap.get("incometimer").put(s, Config.get("Village Structures." + s + ".Income Timer"));
            });
        }
        RepetitiveMethods.test(this);
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        if (Config.getString("Global Settings.Auto Update Notifier").equals("on")) {
            System.out.println("Checking for updates on bukkit");
            Update updateCheck = new Update(80075, "ed2919ef1dcca33b92ac5571e73d53ba1e474a4e");
        }
    }

    @Override
    public void onDisable() {
        if (disabled == 0) {
            MainConversions.onPluginSave();
        } else if (disabled == 1) {
            log.severe(String.format(ChatColor.DARK_RED + "[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
        } else if (disabled == 2) {
            log.severe(String.format(ChatColor.DARK_RED + "[%s] - Disabled due to no economy system hooked ion with vault (Ex. Essentials or iConomy)", getDescription().getName()));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Config.getStringList("Global Settings.Worlds Enabled").contains(player.getWorld().getName())) {
                if (label.equalsIgnoreCase("ec") || label.equalsIgnoreCase("empirecraft")) {
                    if (args.length > 0) {
                        switch (args[0]) {
                            case "create":
                                if (args.length > 2) {
                                    MainCommands.Create(sender, player, args);
                                } else {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "/ec create village <name>" + ChatColor.GREEN + " Creates a village where you are the leader for $" + ChatColor.DARK_GREEN + Config.getInt("Village Settings.Creation Cost") + ChatColor.DARK_GREEN + "\n/ec create empire <player> <player village name>:<empire name>" + ChatColor.GREEN + " Player=a manager of your village whose is to become the new owner of the second village making you an empire. Cost to do so: $" + ChatColor.DARK_GREEN + Config.getInt("Empire Settings.Creation Cost"));
                                }
                                break;
                            case "join":
                                if (args.length >= 2) {
                                    if (player.hasPermission("empirecraft.join")) {
                                        if (!MainConversions.isPartInHashMap(serverdata.get("playerdata"), player.getUniqueId().toString(), "village")) {
                                            tempstring = "";
                                            for (int i = 1; i < args.length; i++) {
                                                tempstring += args[i] + " ";
                                            }
                                            tempstring = tempstring.trim();
                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "app", player.getUniqueId().toString())) {
                                                    if (MainConversions.isPlayerInArrayList(serverdata.get("playerdata").get(player.getUniqueId().toString()), "vii", tempstring)) {
                                                        serverdata.get("playerdata").get(player.getUniqueId().toString()).remove("vii");
                                                        serverdata.get("playerdata").get(player.getUniqueId().toString()).put("village", tempstring);
                                                        sender.sendMessage(ChatColor.BLUE + "You have joined the village " + ChatColor.AQUA + tempstring);
                                                        if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())) != null) {
                                                            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.BLUE + " has joined the village");
                                                        }
                                                        temparraylist.clear();
                                                        if (serverdata.get("villages").get(tempstring) != null) {
                                                            if (serverdata.get("villages").get(tempstring).get("mem") != null) {
                                                                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("mem"));
                                                            }
                                                            if (serverdata.get("villages").get(tempstring).get("man") != null) {
                                                                temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(tempstring).get("man"));
                                                            }
                                                        }
                                                        temparraylist.stream().filter((p) -> (Bukkit.getPlayer(UUID.fromString(p)).isOnline())).forEach((p) -> {
                                                            if (!player.getName().equals(p)) {
                                                                Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.BLUE + " has joined the village");
                                                            }
                                                        });
                                                        if (serverdata.get("villages").get(tempstring).get("mem") == null) {
                                                            serverdata.get("villages").get(tempstring).put("mem", new ArrayList<>());
                                                        }
                                                        ((ArrayList) serverdata.get("villages").get(tempstring).get("mem")).add(player.getUniqueId().toString());
                                                    } else if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "app", player.getUniqueId().toString())) {
                                                        MainCommands.Apply(tempstring, player.getUniqueId().toString());
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You have already sent a request to join " + ChatColor.RED + tempstring);
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You have already sent a request to join " + ChatColor.RED + tempstring);
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "This village does not exists!");
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "You already belong to a village");
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec join <village>");
                                }
                                break;
                            case "invitations":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.invitations")) {
                                        if (serverdata.get("playerdata").get(player.getUniqueId().toString()).get("vii") != null) {
                                            tempstring = ChatColor.BLUE + "";
                                            temparraylist.clear();
                                            temparraylist.addAll((ArrayList) serverdata.get("playerdata").get(player.getUniqueId().toString()).get("vii"));
                                            temparraylist.stream().map((s) -> {
                                                tempstring += s;
                                                return s;
                                            }).map((s) -> {
                                                temparraylist.remove(s);
                                                return s;
                                            }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                            });
                                            sender.sendMessage(tempstring);
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "You have no village invite requests");
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec invitations");
                                }
                                break;
                            case "map":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.map")) {
                                        int X = player.getLocation().getChunk().getX(), Z = player.getLocation().getChunk().getZ();
                                        tempstring = ChatColor.BLUE + "                              Map\n";
                                        for (int z = -4; z < 5; z++) {
                                            for (int x = -8; x < 9; x++) {
                                                if (x != 0 || z != 0) {
                                                    if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), X + x, Z + z, "cla")) {
                                                        if (serverdata.get("playerdata").containsKey(player.getUniqueId().toString())) {
                                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(X + x)).get(Z + z)).get("cla").equals((String) serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village"))) {
                                                                tempstring += ChatColor.AQUA;
                                                            } else if (MainConversions.isPartInHashMap(serverdata.get("villages").get((String) serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village")), "ene", (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(X + x)).get(Z + z)).get("cla")).toString())) {
                                                                tempstring += ChatColor.RED;
                                                            } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get((String) serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village")), "all", (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(X + x)).get(Z + z)).get("cla")).toString())) {
                                                                tempstring += ChatColor.GREEN;
                                                            } else {
                                                                tempstring += ChatColor.YELLOW;
                                                            }
                                                        } else {
                                                            tempstring += ChatColor.YELLOW;
                                                        }
                                                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), X + x, Z + z, "str")) {
                                                            String structure = ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(X + x)).get(Z + z)).get("str").toString();
                                                            if (Config.isConfigurationSection("Village Ranks." + structure)) {
                                                                tempstring += "R ";
                                                            } else {
                                                                tempstring += "S ";
                                                            }
                                                        } else {
                                                            tempstring += "+ ";
                                                        }
                                                    } else {
                                                        tempstring += ChatColor.GRAY + "-  ";
                                                    }
                                                } else {
                                                    tempstring += ChatColor.BLACK + "x ";
                                                }
                                            }
                                            if (z == -4) {
                                                tempstring += ChatColor.AQUA + "            Key";
                                            } else if (z == -3) {
                                                tempstring += ChatColor.AQUA + " ⬛ = Your village's territory";
                                            } else if (z == -2) {
                                                tempstring += ChatColor.RED + " ⬛ = Enemy territory";
                                            } else if (z == -1) {
                                                tempstring += ChatColor.GREEN + " ⬛ = Ally territory";
                                            } else if (z == 0) {
                                                tempstring += ChatColor.YELLOW + " ⬛ = Neutral territory";
                                            } else if (z == 1) {
                                                tempstring += ChatColor.GRAY + " -  = Wilderness territory";
                                            } else if (z == 2) {
                                                tempstring += ChatColor.AQUA + " R = Rank/Home/Base";
                                            } else if (z == 3) {
                                                tempstring += ChatColor.AQUA + " S = Regular structure";
                                            } else if (z == 4) {
                                                tempstring += ChatColor.BLACK + " x = Your location";
                                            }
                                            tempstring += "\n";
                                        }
                                        sender.sendMessage(tempstring);
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec map");
                                }
                                break;
                            case "info":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.info")) {
                                        tempstring = ChatColor.BLUE + "Chunk " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + ChatColor.BLUE + "\n";
                                        if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                            tempstring += ChatColor.BLUE + "Owned By: " + ChatColor.AQUA + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla");
                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("str")) {
                                                tempstring += ChatColor.BLUE + "\nStructure Type: " + ChatColor.AQUA + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str");
                                                tempstring += ChatColor.BLUE + "\nHp: " + ChatColor.AQUA + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("hp");
                                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("con")) {
                                                    tempstring += ChatColor.BLUE + "\nUnder Construction: " + ChatColor.AQUA + "True";
                                                } else {
                                                    tempstring += ChatColor.BLUE + "\nUnder Construction: " + ChatColor.AQUA + "False";
                                                }
                                            }
                                        } else {
                                            tempstring += "Currently is wilderness territory";
                                        }
                                        sender.sendMessage(tempstring);
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec info");
                                }
                                break;
                            case "help":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.help")) {
                                        sender.sendMessage(ChatColor.GOLD + "http://dev.bukkit.org/bukkit-plugins/empirecraft/pages/help-guide-tutorial/");
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec help");
                                }
                                break;
                            case "version":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.version")) {
                                        sender.sendMessage(ChatColor.DARK_PURPLE + "Version: " + this.getDescription().getVersion());
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec version");
                                }
                                break;
                            case "villagelist":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.villagelist")) {
                                        if (!serverdata.get("villages").isEmpty()) {
                                            tempstring = ChatColor.BLUE + "Villages\n" + ChatColor.AQUA;
                                            temparraylist.clear();
                                            temparraylist.addAll(serverdata.get("villages").keySet());
                                            serverdata.get("villages").keySet().stream().map((s) -> {
                                                tempstring += s;
                                                return s;
                                            }).map((s) -> {
                                                temparraylist.remove(s);
                                                return s;
                                            }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                            });
                                            tempstring += ChatColor.BLUE + "\nTotal Villages: " + ChatColor.AQUA + serverdata.get("villages").keySet().size();
                                            sender.sendMessage(tempstring);
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_RED + "There are currently no villages");
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec villagelist");
                                }
                                break;
                            case "chatchannel":
                                if (args.length == 2) {
                                    if (player.hasPermission("empirecraft.chatchannel")) {
                                        switch (args[1]) {
                                            case "local":
                                                if (player.hasPermission("empirecraft.chatchannel.local")) {
                                                    tempHashMap.get("chc").put(player.getUniqueId().toString(), "loc");
                                                    sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to local, meaning you can only communicate with players within " + Config.get("Global Settings.Local Chat Range") + " chunks of you");
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "world":
                                                if (player.hasPermission("empirecraft.chatchannel.world")) {
                                                    tempHashMap.get("chc").put(player.getUniqueId().toString(), "wor");
                                                    sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to world, meaning you can only communicate with players in your current world");
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "global":
                                                if (player.hasPermission("empirecraft.chatchannel.global")) {
                                                    tempHashMap.get("chc").remove(player.getUniqueId().toString());
                                                    sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to global, meaning you can you can now communicate with everyone on the server");
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "village":
                                                if (player.hasPermission("empirecraft.chatchannel.village")) {
                                                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                                                        tempHashMap.get("chc").put(player.getUniqueId().toString(), "val");
                                                        sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to village, meaning you can only communicate with the members of your current village");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You must belong to a village inorder to switch to a village chat channel");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "villagemanagers":
                                                if (player.hasPermission("empirecraft.chatchannel.villagemanagers")) {
                                                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                                                        tempHashMap.get("chc").put(player.getUniqueId().toString(), "vma");
                                                        sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to villagemanagers, meaning you can only communicate with otherts of your appropriate rank");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You must belong to a village inorder to switch to a village chat channel");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "villageallies":
                                                if (player.hasPermission("empirecraft.chatchannel.villageallies")) {
                                                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                                                        tempHashMap.get("chc").put(player.getUniqueId().toString(), "valy");
                                                        sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to villageallies, meaning you can only communicate with players of your village or an allied village");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You must belong to a village inorder to switch to a village chat channel");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "empire":
                                                if (player.hasPermission("empirecraft.chatchannel.empire")) {
                                                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                                                        if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("emp")) {
                                                            tempHashMap.get("chc").put(player.getUniqueId().toString(), "eal");
                                                            sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to global, meaning you can only communicate with players villages within the empire");
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You must belong to a empire inorder to switch to an empire chat channel");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You must belong to a empire inorder to switch to an empire chat channel");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            case "empireallies":
                                                if (player.hasPermission("empirecraft.chatchannel.empireallies")) {
                                                    if (MainConversions.isPlayerInVillage(player.getUniqueId())) {
                                                        if (serverdata.get("villages").get(serverdata.get("playerdata").get(player.getUniqueId().toString()).get("village").toString()).containsKey("emp")) {
                                                            tempHashMap.get("chc").put(player.getUniqueId().toString(), "ealy");
                                                            sender.sendMessage(ChatColor.BLUE + "Your chat channel has been set to global, meaning you can only communicate with players in your empire or an allied empire");
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You must belong to a empire inorder to switch to an empire chat channel");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "You must belong to a empire inorder to switch to an empire chat channel");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                                break;
                                            default:
                                                sender.sendMessage(ChatColor.DARK_RED + "The chat channel " + ChatColor.RED + args[1] + ChatColor.DARK_RED + " does not exsist, use" + ChatColor.RED + "/ec chatchanneltypes" + ChatColor.DARK_RED + " to veiw a list of the possible types");
                                                break;
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec chatchannel <channel>");
                                }
                                break;
                            case "chatchanneltypes":
                                if (args.length == 1) {
                                    if (player.hasPermission("empirecraft.chatchanneltypes")) {
                                        sender.sendMessage(ChatColor.BLUE + "Available Chatchannel Types:\n" + ChatColor.AQUA + "local " + ChatColor.BLUE + "Limits your range of chat to " + Config.get("Global Settings.Local Chat Range") + " chunks\n"
                                                + ChatColor.AQUA + "world " + ChatColor.BLUE + "Limits your chat to the world your in and vice versa\n"
                                                + ChatColor.AQUA + "global " + ChatColor.BLUE + "Default, everyone can see your messages and vice versa\n"
                                                + ChatColor.AQUA + "village " + ChatColor.BLUE + "Limits your chat to your village\n"
                                                + ChatColor.AQUA + "villagemanagers " + ChatColor.BLUE + "Limits your chat to the village owner and managers\n"
                                                + ChatColor.AQUA + "villageallies " + ChatColor.BLUE + "Limits your chat to  allied villages\n"
                                                + ChatColor.AQUA + "empire " + ChatColor.BLUE + "Limits your chat to your empire villages and their members etc.\n"
                                                + ChatColor.AQUA + "empireallies " + ChatColor.BLUE + "Limits your chat to  allied empires and your own empire");
                                    } else {
                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "/ec chatchanneltypes" + ChatColor.GREEN + " Displays a list of the possible chat channels that you can enter");
                                }
                                break;
                            case "admin":
                                if (args.length > 1) {
                                    switch (args[1]) {
                                        case "createstructure":
                                            if (args.length >= 5) {
                                                if (player.hasPermission("empirecraft.admin.createstructure")) {
                                                    if (MainConversions.isInteger(args[2])) {
                                                        if (Integer.parseInt(args[2]) > 0) {
                                                            if ((Integer.parseInt(args[2]) + player.getLocation().getBlockY() - 1) < player.getWorld().getMaxHeight()) {
                                                                tempstring = "";
                                                                for (int i = 4; i < args.length; i++) {
                                                                    tempstring += args[i] + " ";
                                                                }
                                                                tempstring = tempstring.trim();
                                                                tempfile = new File(structureFolder, tempstring + ".yml");
                                                                if (!tempfile.exists()) {
                                                                    switch (args[3]) {
                                                                        case "normal":
                                                                            if (tempHashMap.get("mainchest").get(player.getUniqueId().toString()) == null) {
                                                                                tempHashMap.get("mainchest").put(player.getUniqueId().toString(), new HashMap<>());
                                                                            }
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("name", tempstring);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("type", "normal");
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("height", Integer.parseInt(args[2]) - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("baseheight", player.getLocation().getBlockY() - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("chunk", player.getLocation().getChunk());
                                                                            sender.sendMessage(ChatColor.BLUE + "Please right click a block with a " + ChatColor.AQUA + "STICK" + ChatColor.BLUE + " in this structure size to be the main chest. (any items produced or consumed, especially for building the structure will be found/put in here)");
                                                                            break;
                                                                        case "camp":
                                                                            if (tempHashMap.get("mainchest").get(player.getUniqueId().toString()) == null) {
                                                                                tempHashMap.get("mainchest").put(player.getUniqueId().toString(), new HashMap<>());
                                                                            }
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("name", tempstring);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("type", "camp");
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("height", Integer.parseInt(args[2]) - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("baseheight", player.getLocation().getBlockY() - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("chunk", player.getLocation().getChunk());
                                                                            sender.sendMessage(ChatColor.BLUE + "Please right click a block with a " + ChatColor.AQUA + "STICK" + ChatColor.BLUE + " in this structure size to be the main chest. (any items produced or consumed, especially for building the structure will be found/put in here)");
                                                                            break;
                                                                        case "archer":
                                                                            if (tempHashMap.get("mainchest").get(player.getUniqueId().toString()) == null) {
                                                                                tempHashMap.get("mainchest").put(player.getUniqueId().toString(), new HashMap<>());
                                                                            }
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("name", tempstring);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("type", "archer");
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("height", Integer.parseInt(args[2]) - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("baseheight", player.getLocation().getBlockY() - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("chunk", player.getLocation().getChunk());
                                                                            sender.sendMessage(ChatColor.BLUE + "Please right click a block with a " + ChatColor.AQUA + "STICK" + ChatColor.BLUE + " in this structure size to be the main chest. (any items produced or consumed, especially for building the structure will be found/put in here)");
                                                                            break;
                                                                        case "rank":
                                                                            if (tempHashMap.get("mainchest").get(player.getUniqueId().toString()) == null) {
                                                                                tempHashMap.get("mainchest").put(player.getUniqueId().toString(), new HashMap<>());
                                                                            }
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("name", tempstring);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("type", "rank");
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("height", Integer.parseInt(args[2]) - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("baseheight", player.getLocation().getBlockY() - 1);
                                                                            ((HashMap) tempHashMap.get("mainchest").get(player.getUniqueId().toString())).put("chunk", player.getLocation().getChunk());
                                                                            sender.sendMessage(ChatColor.BLUE + "Please right click a block with a " + ChatColor.AQUA + "STICK" + ChatColor.BLUE + " in this structure size to be the main chest. (any items produced or consumed, especially for building the structure will be found/put in here)");
                                                                            break;
                                                                        default:
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The structure type " + ChatColor.RED + args[3] + ChatColor.DARK_RED + " does not exsist, use /ec admin structuretypes to veiw a list of the possible types");
                                                                            break;
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "The structure name " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " already exsists");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "Your structure height cannot be higher than the worlds height limit which is " + player.getWorld().getMaxHeight());
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot create a structure standing below the map");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Your structure height must be an integer");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec admin createstructure <height> <type> <name> " + ChatColor.RED + "height being the block your standing on plus the <height> and name being the structure name (its x/z is the chunk your standing in)");
                                            }
                                            break;
                                        case "deletestructure":
                                            if (args.length >= 3) {
                                                if (player.hasPermission("empirecraft.admin.deletestructure")) {
                                                    tempstring = "";
                                                    for (int i = 2; i < args.length; i++) {
                                                        tempstring += args[i] + " ";
                                                    }
                                                    tempstring = tempstring.trim();
                                                    tempfile = new File(structureFolder, tempstring + ".yml");
                                                    if (tempfile.exists()) {
                                                        tempfile.delete();
                                                        if (Config.isConfigurationSection("Village Ranks")) {
                                                            Config.getConfigurationSection("Village Ranks").getKeys(false).stream().filter((s) -> (s.equals(tempstring))).forEach((s) -> {
                                                                Config.set("Village Ranks." + s, null);
                                                            });
                                                        }
                                                        if (Config.isConfigurationSection("Village Structures")) {
                                                            Config.getConfigurationSection("Village Structures").getKeys(false).stream().filter((s) -> (s.equals(tempstring))).forEach((s) -> {
                                                                Config.set("Village Structures." + s, null);
                                                            });
                                                        }
                                                        sender.sendMessage(ChatColor.BLUE + "The structure " + ChatColor.AQUA + tempstring + ChatColor.BLUE + " has been sucessfully deleted");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "The structure " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec admin deletestructure <name> " + ChatColor.RED + "Deletes the structure from the config settings and removes its layout file");
                                            }
                                            break;
                                        case "structuretypes":
                                            if (args.length == 2) {
                                                if (player.hasPermission("empirecraft.admin.structuretypes")) {
                                                    sender.sendMessage(ChatColor.BLUE + "Available Structure Types: " + ChatColor.AQUA + "Normal" + ChatColor.BLUE + " (Standard), "
                                                            + ChatColor.AQUA + "Camp " + ChatColor.BLUE + "(Does not need to be built on a claimed plot/built on different worlds), "
                                                            + ChatColor.AQUA + "Archer " + ChatColor.BLUE + "(Shoots an arrow(s) at enemy players), "
                                                            + ChatColor.AQUA + "Rank " + ChatColor.BLUE + "(Base/main/home structure)");
                                                } else {
                                                    sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "/ec admin structuretypes" + ChatColor.GREEN + " Displays a list of the possible structure types that you can create");
                                            }
                                            break;
                                        case "1":
                                            if (args.length == 2) {
                                                sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/ec admin createstructure <height> <type> <name>" + ChatColor.GREEN + " Creates a structure format (height from your foot block to the <height>) with the size of the chunk your currently standing in\n"
                                                        + ChatColor.DARK_GREEN + "/ec admin deletestructure <name>" + ChatColor.GREEN + " Deletes the structure file and save data based on its <name>\n"
                                                        + ChatColor.DARK_GREEN + "/ec admin structuretypes" + ChatColor.GREEN + " Displays a list of the possible structure types that you can create\n"
                                                        + ChatColor.DARK_GREEN + "page <1/1>");
                                            } else {
                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec <page>");
                                            }
                                            break;
                                        default:
                                            sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec admin <page>");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/ec admin createstructure <height> <type> <name>" + ChatColor.GREEN + " Creates a structure format (height from your foot block to the <height>) with the size of the chunk your currently standing in\n"
                                            + ChatColor.DARK_GREEN + "/ec admin deletestructure <name>" + ChatColor.GREEN + " Deletes the structure file and save data based on its <name>\n"
                                            + ChatColor.DARK_GREEN + "/ec admin structuretypes" + ChatColor.GREEN + " Displays a list of the possible structure types that you can create\n"
                                            + ChatColor.DARK_GREEN + "page <1/1>");
                                }
                                break;
                            case "1":
                                if (args.length == 1) {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil" + ChatColor.GREEN + " Displays all village commands\n"
                                            + ChatColor.DARK_GREEN + "/ec create" + ChatColor.GREEN + " Creates a village or an empire\n"
                                            + ChatColor.DARK_GREEN + "/ec join" + ChatColor.GREEN + " Sends an request to join the target village\n"
                                            + ChatColor.DARK_GREEN + "/ec map" + ChatColor.GREEN + " Displays what chunks of land are claimed in your near-by area\n"
                                            + ChatColor.DARK_GREEN + "/ec info" + ChatColor.GREEN + " Gives you info about the chunk your standing in\n"
                                            + ChatColor.DARK_GREEN + "/ec help" + ChatColor.GREEN + " Links you to the official plugin help/guide/tutorial page\n"
                                            + ChatColor.DARK_GREEN + "page <1/2>");
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec <page>");
                                }
                                break;
                            case "2":
                                if (args.length == 1) {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/ec version" + ChatColor.GREEN + " Displays this current plugins version number\n"
                                            + ChatColor.DARK_GREEN + "/ec invitations" + ChatColor.GREEN + " Displays a list of village invites requesting you to join them\n"
                                            + ChatColor.DARK_GREEN + "/ec villagelist" + ChatColor.GREEN + " Displays a list of all the villages in the server\n"
                                            + ChatColor.DARK_GREEN + "/ec chatchannel <channel>" + ChatColor.GREEN + " Changes your chat channel to the selected\n"
                                            + ChatColor.DARK_GREEN + "/ec chatchanneltypes" + ChatColor.GREEN + " Displays a list of the possible chat channels that you can enter\n"
                                            + ChatColor.DARK_GREEN + "/ec admin" + ChatColor.GREEN + " Displays a list of the admin commands (including structure creation)\n"
                                            + ChatColor.DARK_GREEN + "page <2/2>");
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec <page>");
                                }
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /ec <page>");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil" + ChatColor.GREEN + " Displays all village commands\n"
                                + ChatColor.DARK_GREEN + "/ec create" + ChatColor.GREEN + " Creates a village or an empire\n"
                                + ChatColor.DARK_GREEN + "/ec join" + ChatColor.GREEN + " Sends an request to join the target village\n"
                                + ChatColor.DARK_GREEN + "/ec map" + ChatColor.GREEN + " Displays what chunks of land are claimed in your near-by area\n"
                                + ChatColor.DARK_GREEN + "/ec info" + ChatColor.GREEN + " Gives you info about the chunk your standing in\n"
                                + ChatColor.DARK_GREEN + "/ec help" + ChatColor.GREEN + " Links you to the official plugin help/guide/tutorial page\n"
                                + ChatColor.DARK_GREEN + "page <1/2>");
                    }
                } else if (label.equalsIgnoreCase("vil") || label.equalsIgnoreCase("village")) {
                    if (serverdata.get("playerdata").containsKey(player.getUniqueId().toString())) {
                        if (serverdata.get("playerdata").get(player.getUniqueId().toString()).containsKey("village")) {
                            if (args.length > 0) {
                                String playername = player.getUniqueId().toString(), playervillage = (String) serverdata.get("playerdata").get(playername).get("village");
                                switch (args[0]) {
                                    case "owner":
                                        if (args.length > 1) {
                                            switch (args[1]) {
                                                case "retire":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.owner.retire")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "mem", args[2])) {
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).remove(args[2]);
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("mem");
                                                                    }
                                                                    if (serverdata.get("villages").get(playervillage).get("man") == null) {
                                                                        serverdata.get("villages").get(playervillage).put("man", new ArrayList<>());
                                                                    }
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).add(playername);
                                                                    serverdata.get("villages").get(playervillage).put("own", args[2]);
                                                                    Bukkit.getPlayer(args[2]).sendMessage(ChatColor.DARK_PURPLE + "You have been promoted to a manager");
                                                                    sender.sendMessage(ChatColor.BLUE + "You have successfully given " + ChatColor.AQUA + args[2] + ChatColor.BLUE + " leadership to the village, and you have been set to a village manager");
                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", args[2])) {
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).add(playername);
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).remove(args[2]);
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("man")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("man");
                                                                    }
                                                                    serverdata.get("villages").get(playervillage).put("own", args[2]);
                                                                    if (Bukkit.getPlayer(args[2]) != null) {
                                                                        Bukkit.getPlayer(args[2]).sendMessage(ChatColor.DARK_PURPLE + "You have been promoted to a manager");
                                                                    }
                                                                    sender.sendMessage(ChatColor.BLUE + "You have successfully given " + ChatColor.AQUA + args[2] + ChatColor.BLUE + " leadership to the village, and you have been set to a village manager");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " does not live in the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner retire <name>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner retire <name>" + ChatColor.GREEN + " Changes the leader of your village and sets you to an assistant");
                                                    }
                                                    break;
                                                case "promote":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.owner.promote")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " is already a manager of the village");
                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "mem", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    if (serverdata.get("villages").get(playervillage).get("man") == null) {
                                                                        serverdata.get("villages").get(playervillage).put("man", new ArrayList<>());
                                                                    }
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).add(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("mem");
                                                                    }
                                                                    if (Bukkit.getPlayer(args[2]) != null) {
                                                                        Bukkit.getPlayer(args[2]).sendMessage(ChatColor.DARK_PURPLE + "You have been promoted to a manager");
                                                                    }
                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has successfully become a village manager!");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " does not live in the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner promote <name>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner promote <name>" + ChatColor.GREEN + " Promotes a member to an assistant who can use the manage commands");
                                                    }
                                                    break;
                                                case "demote":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.owner.demote")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "mem", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    sender.sendMessage(ChatColor.DARK_RED + args[2] + " is already a member of the village");
                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    if (serverdata.get("villages").get(playervillage).get("mem") == null) {
                                                                        serverdata.get("villages").get(playervillage).put("mem", new ArrayList<>());
                                                                    }
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).add(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("man")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("man");
                                                                    }
                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has been reduced back to a member");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " does not live in the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner demote <name>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner demote <name>" + ChatColor.GREEN + " Demotes an assistant back to a member");
                                                    }
                                                    break;
                                                case "abandon":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.owner.abandon")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (serverdata.get("villages").get(playervillage).containsKey("emp")) {
                                                                    if (!serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("mav").equals(playervillage)) {
                                                                        OwnerCommands.Abandon(playervillage, playername, player);
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot abandon your village when you are the owner of an empire, you must first either abandon your empire or retire a new leader village to it");
                                                                    }
                                                                } else {
                                                                    OwnerCommands.Abandon(playervillage, playername, player);
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner abandon");
                                                    }
                                                    break;
                                                case "settax":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.owner.settax")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isInteger(args[2])) {
                                                                    serverdata.get("villages").get(playervillage).put("tax", args[2]);
                                                                    sender.sendMessage(ChatColor.BLUE + "Daily tax has been set to $" + ChatColor.AQUA + args[2] + ChatColor.BLUE + " for all village members and manages");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set number values as tax");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner settax <$$$$>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner settax" + ChatColor.GREEN + " Sets the daily (24hr) tax price, players who cannot pay will go into debt and you will see what their debt is");
                                                    }
                                                    break;
                                                case "description":
                                                    if (args.length > 2) {
                                                        if (player.hasPermission("empirecraft.village.owner.description")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                tempstring = "";
                                                                for (int i = 2; i < args.length; i++) {
                                                                    tempstring += args[i] + " ";
                                                                }
                                                                tempstring = tempstring.trim();
                                                                serverdata.get("villages").get(playervillage).put("des", tempstring);
                                                                sender.sendMessage(ChatColor.BLUE + "Description set to: " + ChatColor.AQUA + tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner description <text>" + ChatColor.GREEN + " Sets the description for all to see");
                                                    }
                                                    break;
                                                case "togglesetting":
                                                    if (args.length == 4) {
                                                        if (player.hasPermission("empirecraft.village.owner.togglesetting.*")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                OwnerCommands.togglesetting(playervillage, sender, args);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 4 || args.length == 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner togglesetting <setting>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner togglesetting <setting>" + ChatColor.GREEN + " You can toggle fire, pvp, explosions, and mobs");
                                                    }
                                                    break;
                                                case "settinglist":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.owner.settinglist")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                sender.sendMessage(ChatColor.BLUE + "Setting List\n/vil owner togglesetting fire <on/off>" + ChatColor.AQUA + " Enables/Disables firespread(does not effect wars)\n"
                                                                        + ChatColor.BLUE + "/vil owner togglesetting pvp <on/off>" + ChatColor.AQUA + " Enables/Disables pvp(does not effect wars)\n"
                                                                        + ChatColor.BLUE + "/vil owner togglesetting explosions <on/off>" + ChatColor.AQUA + " Enables/Disables explosions (does not effect wars)\n"
                                                                        + ChatColor.BLUE + "/vil owner togglesetting mobs <on/off>" + ChatColor.AQUA + " Enables/Disables mob spawning");
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner settinglist");
                                                    }
                                                    break;
                                                case "1":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner retire <name>" + ChatColor.GREEN + " Changes the leader of your village and sets you to an assistant\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner promote <name>" + ChatColor.GREEN + " Promotes a member to an assistant who can use the manage commands\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner demote <name>" + ChatColor.GREEN + " Demotes an assistant back to a member\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner abandon" + ChatColor.GREEN + " Destroys all buildings except for plots over a minute and removes the village completely\n"
                                                                + ChatColor.DARK_GREEN + "page <1/3>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner <page>");
                                                    }
                                                    break;
                                                case "2":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner settax <$$$$>" + ChatColor.GREEN + " Sets the daily (24hr) tax price, players who cannot pay will go into debt and you will see what their debt is\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner description <text>" + ChatColor.GREEN + " Sets the description for all to see\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner togglesetting <setting> <on/off>" + ChatColor.GREEN + " You can toggle fire, pvp, explosions, and mobs\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner settinglist" + ChatColor.GREEN + " View which settings are currently enabled and disabled\n"
                                                                + ChatColor.DARK_GREEN + "page <2/3>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner <page>");
                                                    }
                                                    break;
                                                case "3":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner diplomacy" + ChatColor.GREEN + " View a list of different commands regarding other villages\n"
                                                                + ChatColor.DARK_GREEN + "page <3/3>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner <page>");
                                                    }
                                                    break;
                                                case "diplomacy":
                                                    if (args.length > 2) {
                                                        switch (args[2]) {
                                                            case "war":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.war")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (serverdata.get("villages").get(playervillage).get("ene") != null) {
                                                                                        if (!((HashMap) serverdata.get("villages").get(playervillage).get("ene")).containsKey(tempstring)) {
                                                                                            DiplomacyCommands.War("villages", playervillage, tempstring, playername);
                                                                                        } else {
                                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are already at war with " + ChatColor.RED + tempstring);
                                                                                        }
                                                                                    } else {
                                                                                        DiplomacyCommands.War("villages", playervillage, tempstring, playername);
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot declare war on yourself!");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner diplomacy war <village>" + ChatColor.GREEN + " Declare war on the enemy village");
                                                                }
                                                                break;
                                                            case "truce":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.truce")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (MainConversions.isPartInHashMap(serverdata.get("villages").get(tempstring), "ene", playervillage)) {
                                                                                        if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "trr", tempstring)) {
                                                                                            if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "trr", playervillage)) {
                                                                                                if (serverdata.get("villages").get(tempstring).get("trr") == null) {
                                                                                                    serverdata.get("villages").get(tempstring).put("trr", new ArrayList<>());
                                                                                                }
                                                                                                ((ArrayList) serverdata.get("villages").get(tempstring).get("trr")).add(playervillage);
                                                                                                sender.sendMessage(ChatColor.BLUE + "You have successfully sent a truce request to " + ChatColor.AQUA + tempstring);
                                                                                                if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())) != null) {
                                                                                                    Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.AQUA + playername + ChatColor.BLUE + ", has requested a truce with you, type /vil owner acceptrequest " + ChatColor.AQUA + playervillage + ChatColor.BLUE + " to end this war");
                                                                                                }
                                                                                            } else {
                                                                                                sender.sendMessage(ChatColor.DARK_RED + "You have already requested a truce with " + ChatColor.RED + tempstring);
                                                                                            }
                                                                                        } else {
                                                                                            DiplomacyCommands.Truce("villages", playervillage, tempstring, playername);
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot request to have a truce when your not at war with " + ChatColor.RED + tempstring);
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot request a truce with yourself!");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner diplomacy truce <village>" + ChatColor.GREEN + " Send a request for the current war to end");
                                                                }
                                                                break;
                                                            case "alliance":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.alliance")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (!MainConversions.isPartInHashMap(serverdata.get("villages").get(tempstring), "ene", playervillage)) {
                                                                                        if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "all", playervillage)) {
                                                                                            if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "alr", tempstring)) {
                                                                                                if (!MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "alr", playervillage)) {
                                                                                                    if (serverdata.get("villages").get(tempstring).get("alr") == null) {
                                                                                                        serverdata.get("villages").get(tempstring).put("alr", new ArrayList<>());
                                                                                                    }
                                                                                                    ((ArrayList) serverdata.get("villages").get(tempstring).get("alr")).add(playervillage);
                                                                                                    sender.sendMessage(ChatColor.BLUE + "You have successfully sent an alliance request to " + ChatColor.AQUA + tempstring);
                                                                                                    if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())) != null) {
                                                                                                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.AQUA + playername + ChatColor.BLUE + ", has requested an alliance with you, type /vil owner acceptrequest " + ChatColor.AQUA + playervillage + ChatColor.BLUE + " to form the alliance");
                                                                                                    }
                                                                                                } else {
                                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You have already requested an alliance with " + ChatColor.RED + tempstring);
                                                                                                }
                                                                                            } else {
                                                                                                DiplomacyCommands.Alliance("villages", playervillage, tempstring, playername);
                                                                                            }
                                                                                        } else {
                                                                                            sender.sendMessage(ChatColor.DARK_RED + "You already have an alliance with " + ChatColor.RED + tempstring);
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot request to have an alliance with your enemy");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot have an alliance with yourself!");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else if (args.length > 4) {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy alliance <village>");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner diplomacy alliance <village>" + ChatColor.GREEN + " Send a request for an alliance");
                                                                }
                                                                break;
                                                            case "neutralize":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.neutralize")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "all", playervillage)) {
                                                                                        if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "all", playervillage)) {
                                                                                            DiplomacyCommands.Neutralize("villages", playervillage, tempstring, playername);
                                                                                        } else {
                                                                                            sender.sendMessage(ChatColor.DARK_RED + "You can only neutralize allys");
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You can only neutralize allys, use /vil owner diplomacy truce <village> to end wars");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot have an alliance with yourself!");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_GREEN + "/vil owner diplomacy neutralize <village>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral");
                                                                }
                                                                break;
                                                            case "acceptrequest":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.acceptrequest")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "alr", tempstring)) {
                                                                                        DiplomacyCommands.Alliance("villages", playervillage, tempstring, playername);
                                                                                    } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "trr", tempstring)) {
                                                                                        DiplomacyCommands.Truce("villages", playervillage, tempstring, playername);
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " currently has no requests of you");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot accept requests from yourself!");
                                                                                }
                                                                            } else {
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "alr", tempstring)) {
                                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("alr")).remove(tempstring);
                                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("alr")).isEmpty()) {
                                                                                        serverdata.get("villages").get(playervillage).remove("alr");
                                                                                    }
                                                                                }
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "trr", tempstring)) {
                                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("trr")).remove(tempstring);
                                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("trr")).isEmpty()) {
                                                                                        serverdata.get("villages").get(playervillage).remove("trr");
                                                                                    }
                                                                                }
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy acceptrequest <village>");
                                                                }
                                                                break;
                                                            case "denyrequest":
                                                                if (args.length > 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.denyrequest")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            tempstring = "";
                                                                            for (int i = 3; i < args.length; i++) {
                                                                                tempstring += args[i] + " ";
                                                                            }
                                                                            tempstring = tempstring.trim();
                                                                            if (serverdata.get("villages").containsKey(tempstring)) {
                                                                                if (!playervillage.equals(tempstring)) {
                                                                                    if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "alr", tempstring)) {
                                                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("alr")).remove(tempstring);
                                                                                        if (((ArrayList) serverdata.get("villages").get(playervillage).get("alr")).isEmpty()) {
                                                                                            serverdata.get("villages").get(playervillage).remove("alr");
                                                                                        }
                                                                                        sender.sendMessage(ChatColor.DARK_PURPLE + "You have successfully denied " + ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + "'s alliance request");
                                                                                        if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).isOnline()) {
                                                                                            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + playername + ChatColor.DARK_PURPLE + ", owner of " + ChatColor.LIGHT_PURPLE + playervillage + ChatColor.DARK_PURPLE + ", has denied your alliance request");
                                                                                        }
                                                                                    } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(tempstring), "trr", tempstring)) {
                                                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("trr")).remove(tempstring);
                                                                                        if (((ArrayList) serverdata.get("villages").get(playervillage).get("trr")).isEmpty()) {
                                                                                            serverdata.get("villages").get(playervillage).remove("trr");
                                                                                        }
                                                                                        sender.sendMessage(ChatColor.DARK_PURPLE + "You have successfully denied " + ChatColor.LIGHT_PURPLE + tempstring + ChatColor.DARK_PURPLE + "'s truce request");
                                                                                        if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(tempstring).get("own").toString())).isOnline()) {
                                                                                            Bukkit.getPlayer(UUID.fromString(Villages.get(tempstring + ".owner").toString())).sendMessage(ChatColor.LIGHT_PURPLE + playername + ChatColor.DARK_PURPLE + ", owner of " + ChatColor.LIGHT_PURPLE + playervillage + ChatColor.DARK_PURPLE + ", has denied your truce request");
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.RED + tempstring + ChatColor.DARK_RED + " currently has no requests of you");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot accept requests from yourself!");
                                                                                }
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "The village name: " + ChatColor.RED + tempstring + ChatColor.DARK_RED + ", does not exsist");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy denyrequest <village>");
                                                                }
                                                                break;
                                                            case "requestlist":
                                                                if (args.length == 3) {
                                                                    if (player.hasPermission("empirecraft.village.owner.diplomacy.requestlist")) {
                                                                        if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                            if (serverdata.get("villages").get(playervillage).get("alr") != null || serverdata.get("villages").get(playervillage).get("trr") != null) {
                                                                                DiplomacyCommands.RequestList("villages", playervillage, sender);
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "There are currently no diplomacy requests for the village");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You are not the owner of this village!");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy requestlist");
                                                                }
                                                                break;
                                                            case "1":
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner diplomacy war <name>" + ChatColor.GREEN + " Declare war on the enemy village\n"
                                                                        + ChatColor.DARK_GREEN + "/vil owner diplomacy truce <name>" + ChatColor.GREEN + " Send a request for the current war to end\n"
                                                                        + ChatColor.DARK_GREEN + "/vil owner diplomacy alliance <name>" + ChatColor.GREEN + " Send a request for an alliance\n"
                                                                        + ChatColor.DARK_GREEN + "/vil owner diplomacy neutralize <name>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral\n"
                                                                        + ChatColor.DARK_GREEN + "page <1/2>");
                                                                break;
                                                            case "2":
                                                                sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner diplomacy acceptrequest <village>" + ChatColor.GREEN + " Creates an alliance/truce with the target village\n"
                                                                        + ChatColor.DARK_GREEN + "/vil owner diplomacy denyrequest <village>" + ChatColor.GREEN + " Denys the allaince/truce request of the target village\n"
                                                                        + ChatColor.DARK_GREEN + "/vil owner diplomacy requestlist" + ChatColor.GREEN + " Views a list of all alliance/truce requests\n"
                                                                        + ChatColor.DARK_GREEN + "page <2/2>");
                                                                break;
                                                            default:
                                                                sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy <page>");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner diplomacy war <name>" + ChatColor.GREEN + " Declare war on the enemy village\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner diplomacy truce <name>" + ChatColor.GREEN + " Send a request for the current war to end\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner diplomacy alliance <name>" + ChatColor.GREEN + " Send a request for an alliance\n"
                                                                + ChatColor.DARK_GREEN + "/vil owner diplomacy neutralize <name>" + ChatColor.GREEN + " Remove your alliance and make each other neurtral\n"
                                                                + ChatColor.DARK_GREEN + "page <1/2>");
                                                    }
                                                default:
                                                    if (!args[1].equals("diplomacy")) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner <page>");
                                                    }
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner retire <name>" + ChatColor.GREEN + " Changes the leader of your village and sets you to an assistant\n"
                                                    + ChatColor.DARK_GREEN + "/vil owner promote <name>" + ChatColor.GREEN + " Promotes a member to an assistant who can use the manage commands\n"
                                                    + ChatColor.DARK_GREEN + "/vil owner demote <name>" + ChatColor.GREEN + " Demotes an assistant back to a member\n"
                                                    + ChatColor.DARK_GREEN + "/vil owner abandon" + ChatColor.GREEN + " Destroys all buildings except for plots over a minute and removes the village completely\n"
                                                    + ChatColor.DARK_GREEN + "page <1/3>");
                                        }
                                        break;
                                    case "manage":
                                        if (args.length > 1) {
                                            switch (args[1]) {
                                                case "invite":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.invite")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                Player tempplayer = Bukkit.getPlayer(args[2]);
                                                                if (tempplayer != null) {
                                                                    if (serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()) != null) {
                                                                        if (serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).get("vii") != null) {
                                                                            if (!MainConversions.isPlayerInArrayList(serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()), "vii", playervillage)) {
                                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "app", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("app")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("app")).isEmpty()) {
                                                                                        serverdata.get("villages").get(playervillage).remove("app");
                                                                                    }
                                                                                    serverdata.get("playerdata").get(player.getUniqueId().toString()).put("village", playervillage);
                                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has already applied so they have been successfully added into the village");
                                                                                    if (tempplayer.isOnline()) {
                                                                                        tempplayer.sendMessage(ChatColor.BLUE + "You have been successfully added into the village " + ChatColor.AQUA + playervillage);
                                                                                    }
                                                                                    if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())) != null) {
                                                                                        Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has joined the village");
                                                                                    }
                                                                                    temparraylist.clear();
                                                                                    if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("mem"));
                                                                                    }
                                                                                    if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("man"));
                                                                                    }
                                                                                    temparraylist.stream().filter((p) -> (Bukkit.getPlayer(p).isOnline())).forEach((p) -> {
                                                                                        if (!playername.equals(p)) {
                                                                                            Bukkit.getPlayer(p).sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has joined the village");
                                                                                        }
                                                                                    });
                                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).add(args[2]);
                                                                                } else if (serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()) == null) {
                                                                                    serverdata.get("playerdata").put(Bukkit.getPlayer(args[2]).getUniqueId().toString(), new HashMap<>());
                                                                                }
                                                                                serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).put("vii", new ArrayList<>());
                                                                                ((ArrayList) serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).get("vii")).add(playervillage);
                                                                                tempplayer.sendMessage(ChatColor.DARK_PURPLE + "You have been successfully invited to join the village " + ChatColor.LIGHT_PURPLE + serverdata.get("playerdata").get(playername).get("village"));
                                                                                sender.sendMessage(ChatColor.BLUE + "You have been successfully invited " + ChatColor.AQUA + args[2] + ChatColor.BLUE + " to join the village");
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " has already been invited to join the village");
                                                                            }
                                                                        } else {
                                                                            serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).put("vii", new ArrayList<>());
                                                                            ((ArrayList) serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).get("vii")).add(playervillage);
                                                                            tempplayer.sendMessage(ChatColor.DARK_PURPLE + "You have been successfully invited to join the village " + ChatColor.LIGHT_PURPLE + serverdata.get("playerdata").get(playername).get("village"));
                                                                            sender.sendMessage(ChatColor.BLUE + "You have been successfully invited " + ChatColor.AQUA + args[2] + ChatColor.BLUE + " to join the village");
                                                                        }
                                                                    } else {
                                                                        serverdata.get("playerdata").put(Bukkit.getPlayer(args[2]).getUniqueId().toString(), new HashMap<>());
                                                                        serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).put("vii", new ArrayList<>());
                                                                        ((ArrayList) serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).get("vii")).add(playervillage);
                                                                        tempplayer.sendMessage(ChatColor.DARK_PURPLE + "You have been successfully invited to join the village " + ChatColor.LIGHT_PURPLE + serverdata.get("playerdata").get(playername).get("village"));
                                                                        sender.sendMessage(ChatColor.BLUE + "You have been successfully invited " + ChatColor.AQUA + args[2] + ChatColor.BLUE + " to join the village");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " is not online right now");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage invite <player>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage invite <player>" + ChatColor.GREEN + " Invites an online player to join your village");
                                                    }
                                                    break;
                                                case "kick":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.kick")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("man")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("man");
                                                                    }
                                                                    serverdata.get("playerdata").remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has been successfully kicked from the village");
                                                                    Player tempplayer = Bukkit.getPlayer(args[2]);
                                                                    if (tempplayer.isOnline()) {
                                                                        tempplayer.sendMessage(ChatColor.DARK_RED + "You have been kicked from " + ChatColor.RED + playervillage + ChatColor.DARK_RED + " by " + ChatColor.RED + Bukkit.getPlayer(UUID.fromString(playername)).getName());
                                                                    }
                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "mem", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("mem");
                                                                    }
                                                                    serverdata.get("playerdata").remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has been successfully kicked from the village");
                                                                    Player tempplayer = Bukkit.getPlayer(args[2]);
                                                                    if (tempplayer.isOnline()) {
                                                                        tempplayer.sendMessage(ChatColor.DARK_RED + "You have been kicked from " + ChatColor.RED + playervillage + ChatColor.DARK_RED + " by " + ChatColor.RED + Bukkit.getPlayer(UUID.fromString(playername)).getName());
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " does not exsist in the village database");
                                                                }
                                                            } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername)) {
                                                                if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot kick " + ChatColor.RED + args[2] + ChatColor.DARK_RED + ", because they are also a manager like yourself");
                                                                } else if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "mem", Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                    ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).isEmpty()) {
                                                                        serverdata.get("villages").get(playervillage).remove("mem");
                                                                    }
                                                                    serverdata.get("playerdata").remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has been successfully kicked from the village");
                                                                    Player tempplayer = Bukkit.getPlayer(args[2]);
                                                                    if (tempplayer.isOnline()) {
                                                                        tempplayer.sendMessage(ChatColor.DARK_RED + "You have been kicked from " + ChatColor.RED + playervillage + ChatColor.DARK_RED + " by " + ChatColor.RED + Bukkit.getPlayer(UUID.fromString(playername)).getName());
                                                                    }
                                                                } else if (serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot kick the owner of your village");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.RED + args[2] + ChatColor.DARK_RED + " does not exsist in the village database");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage kick <player>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage kick <player>" + ChatColor.GREEN + " Kicks a player from your village");
                                                    }
                                                    break;
                                                case "applications":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.applications")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (serverdata.get("villages").get(playervillage).get("app") != null) {
                                                                    tempstring = ChatColor.BLUE + "";
                                                                    temparraylist.stream().map((s) -> {
                                                                        tempstring += Bukkit.getPlayer(UUID.fromString(s)).getName();
                                                                        return s;
                                                                    }).map((s) -> {
                                                                        temparraylist.remove(s);
                                                                        return s;
                                                                    }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                        tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                    });
                                                                    sender.sendMessage(tempstring);
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage applications");
                                                    }
                                                    break;
                                                case "accept":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.accept")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (serverdata.get("villages").get(playervillage).get("app") != null) {
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("app")).contains(Bukkit.getPlayer(args[2]).getUniqueId().toString())) {
                                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("app")).remove(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                        if (((ArrayList) serverdata.get("villages").get(playervillage).get("app")).isEmpty()) {
                                                                            serverdata.get("villages").get(playervillage).remove("app");
                                                                        }
                                                                        serverdata.get("playerdata").put(Bukkit.getPlayer(args[2]).getUniqueId().toString(), new HashMap<>());
                                                                        serverdata.get("playerdata").get(Bukkit.getPlayer(args[2]).getUniqueId().toString()).put("village", playervillage);
                                                                        sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has been successfully added into the village");
                                                                        Player tempplayer = Bukkit.getPlayer(args[2]);
                                                                        if (tempplayer.isOnline()) {
                                                                            tempplayer.sendMessage(ChatColor.BLUE + "You have been successfully added into the village " + ChatColor.AQUA + playervillage);
                                                                        }
                                                                        if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())) != null) {
                                                                            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has joined the village");
                                                                        }
                                                                        temparraylist.clear();
                                                                        if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("mem"));
                                                                        } else {
                                                                            serverdata.get("villages").get(playervillage).put("mem", new ArrayList<>());
                                                                        }
                                                                        if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                            temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("man"));
                                                                        }
                                                                        temparraylist.stream().filter((p) -> (Bukkit.getPlayer(UUID.fromString(p)).isOnline() && !playername.equals(p))).forEach((p) -> {
                                                                            Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.AQUA + args[2] + ChatColor.BLUE + " has joined the village");
                                                                        });
                                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).add(Bukkit.getPlayer(args[2]).getUniqueId().toString());
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "This player was not found in the village applications database");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }

                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage accept <player>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage accept <player>" + ChatColor.GREEN + " Accepts a players request to join the village");
                                                    }
                                                    break;
                                                case "deny":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.deny")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (serverdata.get("villages").get(playervillage).get("app") != null) {
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("app")).contains(args[2])) {
                                                                        ((ArrayList) serverdata.get("villages").get(playervillage).get("app")).remove(args[2]);
                                                                        if (((ArrayList) serverdata.get("villages").get(playervillage).get("app")).isEmpty()) {
                                                                            serverdata.get("villages").get(playervillage).remove("app");
                                                                        }
                                                                        sender.sendMessage(ChatColor.AQUA + args[1] + ChatColor.BLUE + " has been sucessfully removed from the applications list");
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.RED + args[1] + ChatColor.DARK_RED + " does not exsist in the applications list");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There are currently no applications to join the village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage deny <player>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage deny <player>" + ChatColor.GREEN + " Removes a players pending application to join the village");
                                                    }
                                                    break;
                                                case "withdraw":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.withdraw")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isInteger(args[2])) {
                                                                    if (((Integer) serverdata.get("villages").get(playervillage).get("vau")) >= Integer.parseInt(args[2])) {
                                                                        serverdata.get("villages").get(playervillage).put("vau", ((Integer) serverdata.get("villages").get(playervillage).get("vau")) - Integer.parseInt(args[2]));
                                                                        econ.depositPlayer(player, Integer.parseInt(args[2]));
                                                                        sender.sendMessage(ChatColor.BLUE + "You succesfully deposited $" + ChatColor.AQUA + args[2] + ChatColor.BLUE + " into the village vault");
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot withdraw $" + ChatColor.RED + args[2] + ChatColor.DARK_RED + " when the village vault only has $" + ChatColor.RED + serverdata.get("villages").get(playervillage).get("vau"));
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You can only withdraw $ as a whole number");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage withdraw <$$$$>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage withdraw <$$$$>" + ChatColor.GREEN + " Takes a sum of money from the village's vault");
                                                    }
                                                    break;
                                                case "claim":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.claim")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                (Config.getConfigurationSection("Village Ranks").getKeys(false)).stream().filter((o) -> (((String) serverdata.get("villages").get(playervillage).get("vir")).equals(o))).forEach((o) -> {
                                                                    if (((int) serverdata.get("villages").get(playervillage).get("plc")) < Config.getInt("Village Ranks." + o + ".Max Plots")) {
                                                                        if (!MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                            if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
                                                                                if (((int) serverdata.get("villages").get(playervillage).get("plc")) != 0) {
                                                                                    if (MainConversions.isNearByVillageYours(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), playervillage)) {
                                                                                        serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                                                                        if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                                                                            serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                                                                        }
                                                                                        ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                                                                        ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                                                                        sender.sendMessage(ChatColor.BLUE + "You have successfully claimed the chunk of land at X " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + "");
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You must connect all your plots");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You must create a rank building first, then you can claim the land surrounding it");
                                                                                }
                                                                            } else {
                                                                                Boolean cont = true;
                                                                                World world = Bukkit.getWorld(player.getWorld().getUID());
                                                                                for (int y = 1; y < world.getMaxHeight(); y++) {
                                                                                    for (int x = 0; x < 16; x++) {
                                                                                        for (int z = 0; z < 16; z++) {
                                                                                            Vector vector = new Vector(x + player.getLocation().getChunk().getX() * 16, y, z + player.getLocation().getChunk().getZ() * 16);
                                                                                            ApplicableRegionSet set = WGBukkit.getRegionManager(world).getApplicableRegions(vector);
                                                                                            int tempint = 0;
                                                                                            if (set.size() > 0) {
                                                                                                for (ProtectedRegion r : set) {
                                                                                                    if (Config.getStringList("Village Settings.Regions To Ignore").contains(r.getId())) {
                                                                                                        tempint++;
                                                                                                    }
                                                                                                }
                                                                                                if (set.size() != tempint) {
                                                                                                    cont = false;
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                                if (cont) {
                                                                                    if (((int) serverdata.get("villages").get(playervillage).get("plc")) != 0) {
                                                                                        if (MainConversions.isNearByVillageYours(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), playervillage)) {
                                                                                            serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) + 1);
                                                                                            if (serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX()) == null) {
                                                                                                serverdata.get("worldmap").get(player.getWorld().getUID().toString()).put(player.getLocation().getChunk().getX(), new HashMap<>());
                                                                                            }
                                                                                            ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).put(player.getLocation().getChunk().getZ(), new HashMap<>());
                                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("cla", playervillage);
                                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully claimed the chunk of land at X " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + "");
                                                                                        } else {
                                                                                            sender.sendMessage(ChatColor.DARK_RED + "You must connect all your plots");
                                                                                        }
                                                                                    } else {
                                                                                        sender.sendMessage(ChatColor.DARK_RED + "You must create a rank building first, then you can claim the land surrounding it");
                                                                                    }
                                                                                } else {
                                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot claim land where there is part of a world edit region already exsisting there");
                                                                                }
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "This chunk of land has already been claimed");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You already have the " + ChatColor.RED + serverdata.get("villages").get(playervillage).get("plc") + ChatColor.DARK_RED + "/" + ChatColor.RED + Config.getInt("Village Ranks." + o + ".Max Plots") + ChatColor.DARK_RED + " plots claimed");
                                                                    }
                                                                });
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage claim");
                                                    }
                                                    break;
                                                case "unclaim":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.unclaim")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (!MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("str")) {
                                                                            serverdata.get("villages").get(playervillage).replace("plc", ((int) serverdata.get("villages").get(playervillage).get("plc")) - 1);
                                                                            ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).remove(player.getLocation().getChunk().getZ());
                                                                            if (serverdata.get("villages").get(playervillage).containsKey("emp")) {
                                                                                if (serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).containsKey("tp")) {
                                                                                    for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("tp")).keySet())) {
                                                                                        Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("tp")).get("z").toString()));
                                                                                        if (loc.getChunk().getX() == player.getLocation().getChunk().getX() && loc.getChunk().getZ() == player.getLocation().getChunk().getZ()) {
                                                                                            ((HashMap) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("tp")).remove(t);
                                                                                        }
                                                                                    }
                                                                                }
                                                                                if (serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).containsKey("all")) {
                                                                                    for (String e : ((ArrayList<String>) serverdata.get("empires").get(serverdata.get("villages").get(playervillage).get("emp").toString()).get("all"))) {
                                                                                        if (serverdata.get("empires").get(e).containsKey("tp")) {
                                                                                            for (String t : ((ArrayList<String>) ((HashMap) serverdata.get("empires").get(e).get("tp")).keySet())) {
                                                                                                Location loc = new Location(Bukkit.getWorld(UUID.fromString(((HashMap) serverdata.get("empires").get(e).get("tp")).get("w").toString())), Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("x").toString()), 1, Integer.parseInt(((HashMap) serverdata.get("empires").get(e).get("tp")).get("z").toString()));
                                                                                                if (loc.getChunk().getX() == player.getLocation().getChunk().getX() && loc.getChunk().getZ() == player.getLocation().getChunk().getZ()) {
                                                                                                    ((HashMap) serverdata.get("empires").get(e).get("tp")).remove(t);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully unclaimed the chunk of land at X " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + "");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot unclaim your land when theres a structure on it, use " + ChatColor.RED + "/vil manage takedown" + ChatColor.DARK_RED + " first to confirm your actions");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot unclaim someone elses land");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There is no claimed chunk of land here");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage unclaim");
                                                    }
                                                    break;
                                                case "build":
                                                    if (args.length > 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.build")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                tempstring = "";
                                                                for (int i = 3; i < args.length; i++) {
                                                                    tempstring += args[i] + " ";
                                                                }
                                                                tempstring = tempstring.trim();
                                                                ManageCommands.buildInitiation(sender, player, playervillage, args);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage build <Direction(N/E/S/W)> <structure>");
                                                    }
                                                    break;
                                                case "buildlist":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.buildlist")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                tempstring = ChatColor.BLUE + "Village Ranks\n" + ChatColor.AQUA;
                                                                temparraylist.clear();
                                                                temparraylist.addAll(Config.getConfigurationSection("Village Ranks").getKeys(false));
                                                                Boolean cont = true, init = true;
                                                                for (String s : Config.getConfigurationSection("Village Ranks").getKeys(false)) {
                                                                    temparraylist.remove(s);
                                                                    for (String t : Config.getStringList("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Structure Limits")) {
                                                                        String[] spl = t.split(":");
                                                                        if (spl[0].equals(s) && Integer.parseInt(spl[1]) == 0) {
                                                                            cont = false;
                                                                        }
                                                                    }
                                                                    if (cont) {
                                                                        if (!init) {
                                                                            tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                        }
                                                                        tempstring += s;
                                                                        init = false;
                                                                    } else {
                                                                        cont = true;
                                                                    }
                                                                }
                                                                tempstring += ChatColor.BLUE + "\nVillage Structures\n" + ChatColor.AQUA;
                                                                if (Config.isConfigurationSection("Village Structures")) {
                                                                    temparraylist.clear();
                                                                    temparraylist.addAll(Config.getConfigurationSection("Village Structures").getKeys(false));
                                                                    cont = true;
                                                                    init = true;
                                                                    for (String s : Config.getConfigurationSection("Village Structures").getKeys(false)) {
                                                                        temparraylist.remove(s);
                                                                        for (String t : Config.getStringList("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Structure Limits")) {
                                                                            String[] spl = t.split(":");
                                                                            if (spl[0].equals(s) && spl[1].equals("0")) {
                                                                                cont = false;
                                                                            }
                                                                        }
                                                                        if (cont) {
                                                                            if (!init) {
                                                                                tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                            }
                                                                            tempstring += s;
                                                                            init = false;
                                                                        } else {
                                                                            cont = true;
                                                                        }
                                                                    }
                                                                } else {
                                                                    tempstring += "None";
                                                                }
                                                                sender.sendMessage(tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage buildlist");
                                                    }
                                                    break;
                                                case "buildinfo":
                                                    if (args.length > 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.buildlist")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                tempstring = "";
                                                                for (int i = 2; i < args.length; i++) {
                                                                    tempstring += args[i] + " ";
                                                                }
                                                                tempstring = tempstring.trim();
                                                                tempfile = new File(structureFolder, tempstring + ".yml");
                                                                String tempstring2 = "";
                                                                if (tempfile.exists()) {
                                                                    if (Config.isConfigurationSection("Village Structures." + tempstring)) {
                                                                        tempstring2 += ChatColor.BLUE + "                                        " + ChatColor.AQUA + tempstring
                                                                                + ChatColor.BLUE + "\nType: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Type")
                                                                                + ChatColor.BLUE + "          Creation Cost: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Creation Cost")
                                                                                + ChatColor.BLUE + "          Income Time Delay: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Income Timer")
                                                                                + ChatColor.BLUE + "\nUpkeep: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Upkeep")
                                                                                + ChatColor.BLUE + "          Revenue: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Revenue")
                                                                                + ChatColor.BLUE + "          Total Hp: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Total Hp");
                                                                        if (Config.isList("Village Structures." + tempstring + ".Upgraded From")) {
                                                                            tempstring2 += ChatColor.BLUE + "\nUpgraded From: " + ChatColor.AQUA + Config.get("Village Structures." + tempstring + ".Upgraded From");
                                                                        }
                                                                        if (Config.isList("Village Structures." + tempstring + ".Required Materials")) {
                                                                            tempstring2 = ChatColor.BLUE + "Required Materials (Type/Amount): \n" + ChatColor.AQUA;
                                                                            temparraylist.clear();
                                                                            temparraylist.addAll(Config.getStringList("Village Structures." + tempstring + ".Required Materials"));
                                                                            for (String s : Config.getStringList("Village Structures." + tempstring + ".Required Materials")) {
                                                                                String[] req = s.split(":");
                                                                                tempstring2 += req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                                                                                temparraylist.remove(s);
                                                                                if (!temparraylist.isEmpty()) {
                                                                                    tempstring2 += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                                }
                                                                            }
                                                                        }
                                                                        if (Config.isList("Village Structures." + tempstring + ".Produced Materials")) {
                                                                            tempstring2 = ChatColor.BLUE + "Produced Materials (Type/Amount): \n" + ChatColor.AQUA;
                                                                            temparraylist.clear();
                                                                            temparraylist.addAll(Config.getStringList("Village Structures." + tempstring + ".Produced Materials"));
                                                                            for (String s : Config.getStringList("Village Structures." + tempstring + ".Produced Materials")) {
                                                                                String[] req = s.split(":");
                                                                                tempstring2 += req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                                                                                temparraylist.remove(s);
                                                                                if (!temparraylist.isEmpty()) {
                                                                                    tempstring2 += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        tempstring2 += ChatColor.BLUE + "                                        " + tempstring
                                                                                + ChatColor.BLUE + "\nMax Plots: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Max Plots")
                                                                                + ChatColor.BLUE + "          Creation Cost: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Creation Cost")
                                                                                + ChatColor.BLUE + "          Upkeep: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Upkeep")
                                                                                + ChatColor.BLUE + "\nMax Villagers: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Max Villagers")
                                                                                + ChatColor.BLUE + "          Total Hp: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Total Hp");
                                                                        if (Config.isConfigurationSection("Village Ranks." + tempstring + ".Upgraded From")) {
                                                                            tempstring2 += ChatColor.BLUE + "Upgraded From: " + ChatColor.AQUA + Config.get("Village Ranks." + tempstring + ".Upgraded From");
                                                                        }
                                                                        if (Config.isList("Village Ranks." + tempstring + ".Structure Limits")) {
                                                                            tempstring2 = ChatColor.BLUE + "Structure Limits (Name/Amount): " + ChatColor.AQUA;
                                                                            temparraylist.clear();
                                                                            temparraylist.addAll(Config.getStringList("Village Structures." + tempstring + ".Structure Limits"));
                                                                            for (String s : Config.getStringList("Village Structures." + tempstring + ".Structure Limits")) {
                                                                                String[] req = s.split(":");
                                                                                tempstring2 += req[0] + ChatColor.BLUE + " / " + ChatColor.AQUA + req[1];
                                                                                temparraylist.remove(s);
                                                                                if (!temparraylist.isEmpty()) {
                                                                                    tempstring2 += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                    sender.sendMessage(tempstring2);
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "The structure named " + ChatColor.RED + tempstring + ChatColor.DARK_RED + " does not exsist");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage buildinfo <name>");
                                                    }
                                                    break;
                                                case "takedown":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.takedown")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "str")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (!Config.isConfigurationSection("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"))) {
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("str");
                                                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("con")) {
                                                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("con");
                                                                            }
                                                                            if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("cle")) {
                                                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("cle");
                                                                            }
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("hp");
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("dir");
                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully removed the protection/identity surrounding this structure, it is now breakable");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot takedown your rank/home structure, the only way to do so would be to create a new village");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot takedown a structure in somebody else's village");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There is currently no structure here to take down");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage takedown");
                                                    }
                                                    break;
                                                case "forsale":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.manage.forsale")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (!((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("playerplot")) {
                                                                            if (MainConversions.isInteger(args[2])) {
                                                                                ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("forsale", Integer.parseInt(args[2]));
                                                                                sender.sendMessage(ChatColor.BLUE + "You have successfully put the chunk of land at X " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + ChatColor.BLUE + " for sale at a price of $" + ChatColor.AQUA + args[2]);
                                                                            } else {
                                                                                sender.sendMessage(ChatColor.DARK_RED + "You can only set the price to a positive integer");
                                                                            }
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You cannot set a player owned plot forsale, use " + ChatColor.RED + "/vil manage revokeplot " + ChatColor.DARK_RED + "first to revoke their ownership");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot put another village's for sale");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There is no claimed chunk of land here");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage forsale <$$$$>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil manage forsale <$$$$>" + ChatColor.GREEN + " Sets the chunk your standing in forsale so that members of your village can buy it");
                                                    }
                                                    break;
                                                case "notforsale":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.notforsale")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("forsale")) {
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("forsale");
                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully put the chunk of land at X " + ChatColor.AQUA + player.getLocation().getChunk().getX() + ChatColor.BLUE + ", Z " + ChatColor.AQUA + player.getLocation().getChunk().getZ() + " not for sale");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "This plot is currently not for sale");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot change the property of someone else's land");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There is no claimed chunk of land here");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage notforsale");
                                                    }
                                                    break;
                                                case "revokeplot":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.revokeplot")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("playerplot")) {
                                                                            sender.sendMessage(ChatColor.BLUE + "You have took away " + ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("playerplot").toString())) + ChatColor.BLUE + " control over this plot of land");
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("playerplot");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "No village member currently owns that plot of land");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot change the property of someone else's land");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "There is no claimed chunk of land here");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member revokeplot");
                                                    }
                                                    break;
                                                case "sethome":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.sethome")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla") && ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("str")) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                        if (Config.isConfigurationSection("Village Ranks." + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("str"))) {
                                                                            serverdata.get("villages").get(playervillage).replace("rcx", player.getLocation().getBlockX());
                                                                            serverdata.get("villages").get(playervillage).replace("rcy", player.getLocation().getBlockY());
                                                                            serverdata.get("villages").get(playervillage).replace("rcz", player.getLocation().getBlockZ());
                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully set the new home teleport location");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "You can only set the home spawn point in your rank buildings territory");
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You can only set the home spawn point in your rank buildings territory");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You can only set the home spawn point in your rank buildings territory");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member sethome");
                                                    }
                                                    break;
                                                case "viewdebt":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.manage.viewdebt")) {
                                                            if (MainConversions.isPlayerInArrayList(serverdata.get("villages").get(playervillage), "man", playername) || serverdata.get("villages").get(playervillage).get("own").equals(playername)) {
                                                                if (serverdata.get("villages").get(playervillage).containsKey("debt")) {
                                                                    tempstring = "";
                                                                    for (String p : ((ArrayList<String>) ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).keySet())) {
                                                                        tempstring += ChatColor.BLUE + Bukkit.getOfflinePlayer(UUID.fromString(p)).getName();
                                                                        if (Integer.parseInt(((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(p).toString()) > 0) {
                                                                            tempstring += ChatColor.AQUA + " +$" + ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(p) + "\n";
                                                                        } else {
                                                                            tempstring += ChatColor.RED + " -$" + ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(p) + "\n";
                                                                        }
                                                                    }
                                                                    String trim = tempstring.trim();
                                                                    sender.sendMessage(trim);
                                                                } else {
                                                                    sender.sendMessage(ChatColor.BLUE + "Currently everyone is neutral with their payments (no one is above or below the natural payment level)");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You are not a manager of this village!");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member viewdebt");
                                                    }
                                                    break;
                                                case "1":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil manage invite <name>" + ChatColor.GREEN + " Invites an online player to join your village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage kick <name>" + ChatColor.GREEN + " Kicks a player from your village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage applications" + ChatColor.GREEN + " Displays a list of players wanting to join the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage accept <name>" + ChatColor.GREEN + " Accepts a players request to join the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage deny <name>" + ChatColor.GREEN + " Removes a players pending application to join the village\n"
                                                                + ChatColor.DARK_GREEN + "page <1/3>");
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /village manage <page>");
                                                    }
                                                    break;
                                                case "2":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil manage claim" + ChatColor.GREEN + " Claims land for the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage unclaim" + ChatColor.GREEN + " Unclaims land from the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage forsale <$$$$>" + ChatColor.GREEN + " Sets the chunk your standing in forsale so that members of your village can buy it\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage notforsale" + ChatColor.GREEN + " Sets the chunk your standing in to be unbuyable\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage revokeplot" + ChatColor.GREEN + " Sets the plot to be neutrally owned\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage sethome" + ChatColor.GREEN + " Sets home teleport location within the rank structure's chunk\n"
                                                                + ChatColor.DARK_GREEN + "page <2/3>");
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /village manage <page>");
                                                    }
                                                    break;
                                                case "3":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil manage build <Direction(N/E/S/W)> <structure>" + ChatColor.GREEN + " Builds a structure over a certain period of time\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage takedown" + ChatColor.GREEN + " Destroy the structure in the chunk you are standing in\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage buildlist" + ChatColor.GREEN + " Lists all the types of structures you can build\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage buildinfo <name>" + ChatColor.GREEN + " Gives a finite description about the specified structure\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage withdraw <$$$$>" + ChatColor.GREEN + " Takes a sum of money from the village's vault\n"
                                                                + ChatColor.DARK_GREEN + "/vil manage viewdebt" + ChatColor.GREEN + " Names every manager and member of the villages tax payments, whether they missed some and are negative or donated and have gone positive\n"
                                                                + ChatColor.DARK_GREEN + "page <3/3>");
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage <page>");
                                                    }
                                                    break;
                                                default:
                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil manage <page>");
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil manage invite <name>" + ChatColor.GREEN + " Invites an online player to join your village\n"
                                                    + ChatColor.DARK_GREEN + "/vil manage kick <name>" + ChatColor.GREEN + " Kicks a player from your village\n"
                                                    + ChatColor.DARK_GREEN + "/vil manage applications" + ChatColor.GREEN + " Displays a list of players wanting to join the village\n"
                                                    + ChatColor.DARK_GREEN + "/vil manage accept <name>" + ChatColor.GREEN + " Accepts a players request to join the village\n"
                                                    + ChatColor.DARK_GREEN + "/vil manage deny <name>" + ChatColor.GREEN + " Removes a players pending application to join the village\n"
                                                    + ChatColor.DARK_GREEN + "page <1/3>");
                                        }
                                        break;
                                    case "member":
                                        if (args.length > 1) {
                                            switch (args[1]) {
                                                case "info":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.info")) {
                                                            int tempint = 1;
                                                            if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                tempint += ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).size();
                                                            }
                                                            if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                tempint += ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).size();
                                                            }
                                                            tempstring = ChatColor.BLUE + "                                        " + playervillage + "\nOwner: " + ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).getName()
                                                                    + ChatColor.BLUE + "\nVillagers: " + ChatColor.AQUA + tempint + ChatColor.BLUE + "/" + ChatColor.AQUA + Config.getString("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Max Villagers")
                                                                    + ChatColor.BLUE + "\nPlots Claimed: " + ChatColor.AQUA + serverdata.get("villages").get(playervillage).get("plc") + ChatColor.BLUE + "/" + ChatColor.AQUA + Config.getString("Village Ranks." + serverdata.get("villages").get(playervillage).get("vir") + ".Max Plots")
                                                                    + ChatColor.BLUE + "\nMoney In Vault: " + ChatColor.AQUA + serverdata.get("villages").get(playervillage).get("vau");
                                                            if (serverdata.get("villages").get(playervillage).containsKey("des")) {
                                                                tempstring += ChatColor.BLUE + "\nDescription: " + ChatColor.AQUA + serverdata.get("villages").get(playervillage).get("des");
                                                            }
                                                            sender.sendMessage(tempstring);
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member info");
                                                    }
                                                    break;
                                                case "leave":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.leave")) {
                                                            if (serverdata.get("villages").get(playervillage).get("own") != playername) {
                                                                if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("man")).contains(playername)) {
                                                                        MemberCommands.Leave(playervillage, playername, player, "man");
                                                                    }
                                                                } else if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                    if (((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).contains(playername)) {
                                                                        MemberCommands.Leave(playervillage, playername, player, "mem");
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "You cannot leave the village as its owner, you must either abandon it or retire another person to owner");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot leave the village as its owner, you must either abandon it or retire another person to owner");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You cannot leave the village as its owner, you must either abandon it or retire another person to owner");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member leave");
                                                    }
                                                    break;
                                                case "deposit":
                                                    if (args.length == 3) {
                                                        if (player.hasPermission("empirecraft.village.member.deposit")) {
                                                            if (MainConversions.isInteger(args[2])) {
                                                                if (econ.has(player, Integer.parseInt(args[2]))) {
                                                                    serverdata.get("villages").get(playervillage).put("vau", ((Integer) serverdata.get("villages").get(playervillage).get("vau")) + Integer.parseInt(args[2]));
                                                                    econ.withdrawPlayer(player, Integer.parseInt(args[2]));
                                                                    if (serverdata.get("villages").get(playervillage).containsKey("debt")) {
                                                                        if (((HashMap) serverdata.get("villages").get(playervillage).get("debt")).containsKey(player)) {
                                                                            ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).put(player, ((Integer) ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(player)) - Integer.parseInt(args[2]));
                                                                            if (((Integer) ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(player)) == 0) {
                                                                                ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).remove(player);
                                                                                if (((HashMap) serverdata.get("villages").get(playervillage).get("debt")).isEmpty()) {
                                                                                    serverdata.get("villages").get(playervillage).remove("debt");
                                                                                }
                                                                            }
                                                                        } else {
                                                                            ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).put(player, Integer.parseInt(args[2]));
                                                                        }
                                                                    } else {
                                                                        serverdata.get("villages").get(playervillage).put("debt", new HashMap<>());
                                                                        ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).put(player, Integer.parseInt(args[2]));
                                                                    }
                                                                    temparraylist.clear();
                                                                    if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("mem"));
                                                                    }
                                                                    if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                        temparraylist.addAll((Collection<? extends String>) serverdata.get("villages").get(playervillage).get("man"));
                                                                    }
                                                                    temparraylist.stream().filter((p) -> ((Bukkit.getPlayer(UUID.fromString(p))).isOnline())).forEach((p) -> {
                                                                        if (!Bukkit.getPlayer(UUID.fromString(p)).equals(player)) {
                                                                            Bukkit.getPlayer(UUID.fromString(p)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", Has just donated $" + ChatColor.LIGHT_PURPLE + args[2] + ChatColor.DARK_PURPLE + " to the village!");
                                                                        }
                                                                    });
                                                                    if (Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).isOnline()) {
                                                                        if (!Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).toString().equals(player.getUniqueId().toString())) {
                                                                            Bukkit.getPlayer(UUID.fromString(serverdata.get("villages").get(playervillage).get("own").toString())).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + ", Has just donated $" + ChatColor.LIGHT_PURPLE + args[2] + ChatColor.DARK_PURPLE + " to the village!");
                                                                        }
                                                                    }
                                                                    sender.sendMessage(ChatColor.BLUE + "You succesfully deposited $" + ChatColor.AQUA + args[2] + ChatColor.BLUE + " into the village vault");
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot deposit $" + ChatColor.RED + args[2] + ChatColor.DARK_RED + " when you only have $" + ChatColor.RED + econ.getBalance(player));
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "You can only deposit $ as a whole number");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 3) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member deposit <$$$$>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil member deposit <$$$$>" + ChatColor.GREEN + " Contribute money to your guild (does not affect tax cost)");
                                                    }
                                                    break;
                                                case "home":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.home")) {
                                                            if (Config.getLong("Village Settings.Home Teleport Delay") != 0) {
                                                                tempHashMap.get("tpx").put(playername, player.getLocation().getBlockX());
                                                                tempHashMap.get("tpy").put(playername, player.getLocation().getBlockY());
                                                                tempHashMap.get("tpz").put(playername, player.getLocation().getBlockZ());
                                                                sender.sendMessage(ChatColor.BLUE + "You will teleport to your homeblock in " + Config.getInt("Village Settings.Home Teleport Delay") + " seconds, do not move");
                                                                this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                                                                    if (tempHashMap.get("tpx").containsKey(playername)) {
                                                                        if (Bukkit.getPlayer(UUID.fromString(playername)) != null && ((Integer) tempHashMap.get("tpx").get(playername)) == player.getLocation().getBlockX() && ((Integer) tempHashMap.get("tpy").get(playername)) == player.getLocation().getBlockY() && ((Integer) tempHashMap.get("tpz").get(playername)) == player.getLocation().getBlockZ()) {
                                                                            Location loc = new Location(Bukkit.getWorld(UUID.fromString(serverdata.get("villages").get(playervillage).get("rcw").toString())), (Integer) serverdata.get("villages").get(playervillage).get("rcx"), (Integer) serverdata.get("villages").get(playervillage).get("rcy"), (Integer) serverdata.get("villages").get(playervillage).get("rcz"));
                                                                            player.teleport(loc);
                                                                            tempHashMap.get("tpx").remove(playername);
                                                                            tempHashMap.get("tpy").remove(playername);
                                                                            tempHashMap.get("tpz").remove(playername);
                                                                        }
                                                                    }
                                                                }, Config.getLong("Village Settings.Home Teleport Delay") * 20);
                                                            } else {
                                                                Location loc = new Location(Bukkit.getWorld(serverdata.get("villages").get(playervillage).get("rcw").toString()), (Integer) serverdata.get("villages").get(playervillage).get("rcx"), (Integer) serverdata.get("villages").get(playervillage).get("rcy"), (Integer) serverdata.get("villages").get(playervillage).get("rcz"));
                                                                player.teleport(loc);
                                                                sender.sendMessage(ChatColor.BLUE + "Teleporting now");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member home");
                                                    }
                                                    break;
                                                case "setperm":
                                                    if (args.length == 5) {
                                                        if (player.hasPermission("empirecraft.village.member.setperm")) {
                                                            MemberCommands.Setperm(playername, sender, args);
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else if (args.length > 5 || args.length == 3 || args.length == 4) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member setperm <perm> <relation> <on/off>");
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil member setperm <perm> <relation> <on/off>" + ChatColor.GREEN + " Defines your own plots for permissions of others to do stuff on them");
                                                    }
                                                    break;
                                                case "permlist":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.permlist")) {
                                                            sender.sendMessage(ChatColor.AQUA + "Permissions List\n/vil member setperm modify <relation> <on/off>" + ChatColor.BLUE + " Enables/Disables build/destroying blocks\n"
                                                                    + ChatColor.AQUA + "/vil member setperm doors <relation> <on/off>" + ChatColor.BLUE + " Enables/Disables door use\n"
                                                                    + ChatColor.AQUA + "/vil member setperm buttons <relation> <on/off>" + ChatColor.BLUE + " Enables/Disables button use\n"
                                                                    + ChatColor.AQUA + "/vil member setperm levers <relation> <on/off>" + ChatColor.BLUE + " Enables/Disables lever use\n"
                                                                    + ChatColor.AQUA + "/vil member setperm containers <relation> <on/off>" + ChatColor.BLUE + " Enables/Disables container use\n"
                                                                    + ChatColor.AQUA + "Relations can either be member,outsider,ally or playernames");
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "/vil member permlist" + ChatColor.GREEN + " Lists all the possible permissions that you can set");
                                                    }
                                                    break;
                                                case "tax":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.tax")) {
                                                            tempstring = ChatColor.BLUE + "Server time: " + ChatColor.AQUA;
                                                            Calendar cal = Calendar.getInstance();
                                                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                                                            int min = cal.get(Calendar.MINUTE);
                                                            int sec = cal.get(Calendar.SECOND);
                                                            tempstring += String.format("%02d:%02d:%02d", hour, min, sec);
                                                            tempstring += ChatColor.BLUE + "\nTime left until next tax: " + ChatColor.AQUA;
                                                            Long tax = Config.getLong("Village Settings.Tax Delay");
                                                            Long dif = Math.abs(tax - hour * 3600 + min * 60 + sec);
                                                            hour = 0;
                                                            min = 0;
                                                            sec = 0;
                                                            while (dif >= 0) {
                                                                dif -= 3600;
                                                                hour += 1;
                                                            }
                                                            dif += 3600;
                                                            while (dif >= 0) {
                                                                dif -= 60;
                                                                min += 1;
                                                            }
                                                            dif += 60;
                                                            while (dif > 0) {
                                                                dif -= 1;
                                                                sec += 1;
                                                            }
                                                            tempstring += String.format("%02d:%02d:%02d", hour, min, sec);
                                                            tempstring += ChatColor.BLUE + "\nTax Amount: $" + ChatColor.AQUA;
                                                            if (serverdata.get("villages").get(playervillage).containsKey("tax")) {
                                                                tempstring += serverdata.get("villages").get(playervillage).get("tax");
                                                            } else {
                                                                tempstring += "0";
                                                            }
                                                            if (serverdata.get("villages").get(playervillage).containsKey("debt")) {
                                                                if (((HashMap) serverdata.get("villages").get(playervillage).get("debt")).containsKey(player.getName())) {
                                                                    if (Integer.parseInt(((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(playername).toString()) > 0) {
                                                                        tempstring += ChatColor.BLUE + "\nYour Donations: " + ChatColor.AQUA + "+$ " + ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(playername);
                                                                    } else {
                                                                        tempstring += ChatColor.BLUE + "\nYour Debt: " + ChatColor.AQUA + "-$ " + ((HashMap) serverdata.get("villages").get(playervillage).get("debt")).get(playername);
                                                                    }
                                                                } else {
                                                                    tempstring += ChatColor.BLUE + "\nYou currently have no debt";
                                                                }
                                                            } else {
                                                                tempstring += ChatColor.BLUE + "\nYou currently have no debt";
                                                            }
                                                            sender.sendMessage(tempstring);
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member taxtime");
                                                    }
                                                    break;
                                                case "buyplot":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.buyplot")) {
                                                            if (MainConversions.isWorldChunkClaimed(serverdata.get("worldmap").get(player.getWorld().getUID().toString()), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), "cla")) {
                                                                if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("cla").equals(playervillage)) {
                                                                    if (((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).containsKey("forsale")) {
                                                                        if (((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("forsale")) <= econ.getBalance(player)) {
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).put("playerplot", playername);
                                                                            serverdata.get("villages").get(playervillage).put("vau", ((Integer) serverdata.get("villages").get(playervillage).get("vau")) + ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("forsale")));
                                                                            econ.withdrawPlayer(player, ((Integer) ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("forsale")));
                                                                            sender.sendMessage(ChatColor.BLUE + "You have successfully bought the plot for $" + ChatColor.AQUA + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("forsale").toString());
                                                                            ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).remove("forsale");
                                                                        } else {
                                                                            sender.sendMessage(ChatColor.DARK_RED + "The cost to purchase that plot is $" + ChatColor.RED + ((HashMap) ((HashMap) serverdata.get("worldmap").get(player.getWorld().getUID().toString()).get(player.getLocation().getChunk().getX())).get(player.getLocation().getChunk().getZ())).get("forsale") + ChatColor.DARK_RED + " while you only have $" + ChatColor.RED + econ.getBalance(player));
                                                                        }
                                                                    } else {
                                                                        sender.sendMessage(ChatColor.DARK_RED + "This plot is currently not for sale");
                                                                    }
                                                                } else {
                                                                    sender.sendMessage(ChatColor.DARK_RED + "You cannot buy the property of someone else's village");
                                                                }
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "There is no claimed chunk of land here");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member buyplot");
                                                    }
                                                    break;
                                                case "memberlist":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.memberlist")) {
                                                            if (serverdata.get("villages").get(playervillage).get("mem") != null) {
                                                                tempstring = ChatColor.BLUE + "Members\n" + ChatColor.AQUA + "";
                                                                temparraylist.clear();
                                                                temparraylist.addAll((ArrayList) serverdata.get("villages").get(playervillage).get("mem"));
                                                                ((ArrayList<String>) serverdata.get("villages").get(playervillage).get("mem")).stream().map((s) -> {
                                                                    tempstring += Bukkit.getServer().getOfflinePlayer(UUID.fromString(s)).getName();
                                                                    return s;
                                                                }).map((s) -> {
                                                                    temparraylist.remove(s);
                                                                    return s;
                                                                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                });
                                                                tempstring += ChatColor.BLUE + "\nTotal Members: " + ChatColor.AQUA + ((ArrayList) serverdata.get("villages").get(playervillage).get("mem")).size();
                                                                sender.sendMessage(tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "There are currently no members in the village");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member memberlist");
                                                    }
                                                    break;
                                                case "managerlist":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.managerlist")) {
                                                            if (serverdata.get("villages").get(playervillage).get("man") != null) {
                                                                tempstring = ChatColor.BLUE + "Managers\n" + ChatColor.AQUA + "";
                                                                temparraylist.clear();
                                                                temparraylist.addAll((ArrayList) serverdata.get("villages").get(playervillage).get("man"));
                                                                ((ArrayList<String>) serverdata.get("villages").get(playervillage).get("man")).stream().map((s) -> {
                                                                    tempstring += Bukkit.getServer().getOfflinePlayer(UUID.fromString(s)).getName();
                                                                    return s;
                                                                }).map((s) -> {
                                                                    temparraylist.remove(s);
                                                                    return s;
                                                                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                });
                                                                tempstring += ChatColor.BLUE + "\nTotal Managers: " + ChatColor.AQUA + ((ArrayList) serverdata.get("villages").get(playervillage).get("man")).size();
                                                                sender.sendMessage(tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.DARK_RED + "There are currently no managers in the village");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member managerlist");
                                                    }
                                                    break;
                                                case "relations":
                                                    if (args.length == 2) {
                                                        if (player.hasPermission("empirecraft.village.member.relations")) {
                                                            sender.sendMessage(ChatColor.BLUE + "Allies");
                                                            if (serverdata.get("villages").get(playervillage).get("all") != null) {
                                                                tempstring = ChatColor.AQUA + "";
                                                                temparraylist.clear();
                                                                temparraylist.addAll((ArrayList) serverdata.get("villages").get(playervillage).get("all"));
                                                                ((ArrayList<String>) serverdata.get("villages").get(playervillage).get("all")).stream().map((s) -> {
                                                                    tempstring += s;
                                                                    return s;
                                                                }).map((s) -> {
                                                                    temparraylist.remove(s);
                                                                    return s;
                                                                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                });
                                                                tempstring += ChatColor.BLUE + "\nTotal Allies: " + ChatColor.AQUA + ((ArrayList) serverdata.get("villages").get(playervillage).get("all")).size();
                                                                sender.sendMessage(tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.AQUA + "None");
                                                            }
                                                            sender.sendMessage(ChatColor.BLUE + "Enemies");
                                                            if (serverdata.get("villages").get(playervillage).get("ene") != null) {
                                                                tempstring = ChatColor.AQUA + "";
                                                                temparraylist.clear();
                                                                temparraylist.addAll(((HashMap) serverdata.get("villages").get(playervillage).get("ene")).keySet());
                                                                ((HashMap<String, Integer>) serverdata.get("villages").get(playervillage).get("ene")).keySet().stream().map((s) -> {
                                                                    tempstring += s;
                                                                    return s;
                                                                }).map((s) -> {
                                                                    temparraylist.remove(s);
                                                                    return s;
                                                                }).filter((_item) -> (!temparraylist.isEmpty())).forEach((_item) -> {
                                                                    tempstring += (ChatColor.BLUE + ", " + ChatColor.AQUA);
                                                                });
                                                                tempstring += ChatColor.BLUE + "\nTotal Enemies: " + ChatColor.AQUA + ((HashMap) serverdata.get("villages").get(playervillage).get("ene")).keySet().size();
                                                                sender.sendMessage(tempstring);
                                                            } else {
                                                                sender.sendMessage(ChatColor.AQUA + "None");
                                                            }
                                                        } else {
                                                            sender.sendMessage(ChatColor.DARK_RED + "You lack the permissions to use this command");
                                                        }
                                                    } else {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil owner diplomacy relations");
                                                    }
                                                    break;
                                                case "1":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil member info" + ChatColor.GREEN + " Displays info about your village\n"
                                                                + ChatColor.DARK_GREEN + "/vil member leave" + ChatColor.GREEN + " Leaves your current village\n"
                                                                + ChatColor.DARK_GREEN + "/vil member deposit" + ChatColor.GREEN + " Contribute money to your guild (does not affect tax daily cost, but will lower debt)\n"
                                                                + ChatColor.DARK_GREEN + "/vil member home" + ChatColor.GREEN + " Teleports you to the village home block\n"
                                                                + ChatColor.DARK_GREEN + "/vil member setperm <perm> <relation> <on/off>" + ChatColor.GREEN + " Defines your own plots for permissions of others to do stuff on them\n"
                                                                + ChatColor.DARK_GREEN + "/vil member permlist" + ChatColor.GREEN + " Lists all the possible permissions that you can set\n"
                                                                + ChatColor.DARK_GREEN + "page <1/2>");
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member <page>");
                                                    }
                                                    break;
                                                case "2":
                                                    if (args.length == 2) {
                                                        sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil member memberlist" + ChatColor.GREEN + " Displays a list of all the members in the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil member tax" + ChatColor.GREEN + " Tells you the time till the next tax and what it is\n"
                                                                + ChatColor.DARK_GREEN + "/vil member buyplot" + ChatColor.GREEN + " Purchases the plot that you are currently standing in\n"
                                                                + ChatColor.DARK_GREEN + "/vil member managerlist" + ChatColor.GREEN + " Displays a list of all the managers in the village\n"
                                                                + ChatColor.DARK_GREEN + "/vil member relations" + ChatColor.GREEN + " View all your current alliances and enemys\n"
                                                                + ChatColor.DARK_GREEN + "page <2/2>");
                                                    } else if (args.length > 2) {
                                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member <page>");
                                                    }
                                                    break;
                                                default:
                                                    sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil member <page>");
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil member info" + ChatColor.GREEN + " Displays info about your village\n"
                                                    + ChatColor.DARK_GREEN + "/vil member leave" + ChatColor.GREEN + " Leaves your current village\n"
                                                    + ChatColor.DARK_GREEN + "/vil member deposit" + ChatColor.GREEN + " Contribute money to your guild (does not affect tax daily cost, but will lower debt)\n"
                                                    + ChatColor.DARK_GREEN + "/vil member home" + ChatColor.GREEN + " Teleports you to the village home block\n"
                                                    + ChatColor.DARK_GREEN + "/vil member setperm <perm> <relation> <on/off>" + ChatColor.GREEN + " Defines your own plots for permissions of others to do stuff on them\n"
                                                    + ChatColor.DARK_GREEN + "/vil member permlist" + ChatColor.GREEN + " Lists all the possible permissions that you can set\n"
                                                    + ChatColor.DARK_GREEN + "page <1/2>");
                                        }
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.DARK_RED + "Proper format: /vil");
                                        break;
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_GREEN + "                                        EMPIRECRAFT\n/vil owner" + ChatColor.GREEN + " Contains commands for the village owner\n"
                                        + ChatColor.DARK_GREEN + "/vil manage" + ChatColor.GREEN + " Contains commands for managers of the village\n"
                                        + ChatColor.DARK_GREEN + "/vil member" + ChatColor.GREEN + " Contains commands for members of the village\n"
                                        + ChatColor.DARK_GREEN + "/ec" + ChatColor.GREEN + " Contains the starting off commands\n"
                                        + ChatColor.DARK_GREEN + "page <1/1>");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You must belong in a village to use these commands");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You must belong in a village to use these commands");
                    }
                } else if (label.equalsIgnoreCase("emp") || label.equalsIgnoreCase("empire")) {
                    MainExtended.Empires(sender, player, args);
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Sorry, but empirecraft has been disabled for this world");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You must be in-game to use these commands");
        }
        return true;
    }
}
/*
 KEY THINGS NEED TO BE DONE:
 Entry messages such as building completion etc.

 Others:
 Admin Commands, and map tooltip setup, such as Holographic display hover system
 */
