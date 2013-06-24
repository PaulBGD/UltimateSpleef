package me.ultimate.UltimateSpleef;

import java.io.File;
import java.io.IOException;

import me.ultimate.UltimateSpleef.ArenaManager.ArenaJoining;
import me.ultimate.UltimateSpleef.ArenaManager.FallOutListener;
import me.ultimate.UltimateSpleef.Commands.SpleefCommand;
import me.ultimate.UltimateSpleef.Enums.LogType;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class UltimateSpleef extends JavaPlugin {

    public FileConfiguration msgConfig;
    public File dataFolder;
    public Economy econ = null;
    public Utils Utils = new Utils();

    public void onEnable() {
        dataFolder = getDataFolder();
        getCommand("spleef").setExecutor(new SpleefCommand(this));
        File messages = new File(dataFolder + File.separator + "messages.yml");
        msgConfig = YamlConfiguration.loadConfiguration(messages);
        log("Could not find messages.yml! Generating default one now..", LogType.Warning);
        setupMessageDefaults(messages, msgConfig);
        if (getWorldEdit() == null) {
            log("Could not find WorldEdit! Disabling..", LogType.Severe);
            getServer().getPluginManager().disablePlugin(this);
        }
        if (!setupEconomy()) {
            log("Could not find vault! Economy rewards are now disabled.", LogType.Severe);
        }
        getServer().getPluginManager().registerEvents(new ArenaJoining(this), this);
        getServer().getPluginManager().registerEvents(new FallOutListener(), this);
    }

    //For error catching
    public void sendError(Exception e) {
        sendError(e, null);
    }

    public void sendError(Exception e, String custom) {
        getLogger().severe("-=-=-=- ERROR -=-=-=-");
        getLogger().severe("Error Cause: " + e.getCause());
        getLogger().severe("Error Message: " + e.getMessage());
        if (custom != null)
            getLogger().severe("Custom Error Message: " + custom);
        getLogger().severe("-=-=- StackTrace -=-=-");
        for (StackTraceElement error : e.getStackTrace()) {
            getLogger().severe(error.toString());
        }
        getLogger().severe("-=-=- End Error -=-=-");
    }

    public String NOT_ENOUGH_ARGUMENTS;
    public String NOT_A_PLAYER;
    public String ARGUMENT_DOESNT_EXIST;
    public String PLAYER_NOT_HAVE_PERMISSION;
    public String ARENA_DOESNT_EXIST;
    public String ARENA_JOIN_MESSAGE;
    public String NOT_ENOUGH_MONEY;

    private void setupMessageDefaults(File file, FileConfiguration msg) {
        msg.set("NOT_ENOUGH_ARGUMENTS", "You do not have enough arguments!");
        msg.set("NOT_A_PLAYER", "&8[&aUltimateSpleef&8]&a You are not a player!");
        msg.set("ARGUMENT_DOESNT_EXIST", "&4You did something wrong! Try doing /spleef help!");
        msg.set("PLAYER_NOT_HAVE_PERMISSION", "&4You do not have the permission %perm%!");
        msg.set("ARENA_DOESNT_EXIST", "&4The arena %arena% does not exist!");
        msg.set("ARENA_JOIN_MESSAGE", "You have joined the arena %arena%!");
        msg.set("NOT_ENOUGH_MONEY", "&4You do not have enough money! You need %bal% more!");
        try {
            msg.save(file);
        } catch (IOException e) {
            sendError(e, "Could not save messages file");
        }
        NOT_ENOUGH_ARGUMENTS = msgConfig.getString("NOT_ENOUGH_ARGUMENTS");
        ARENA_JOIN_MESSAGE = msgConfig.getString("ARENA_JOIN_MESSAGE");
        NOT_ENOUGH_MONEY = msgConfig.getString("NOT_ENOUGH_ARGUMENTS");
        NOT_A_PLAYER = msgConfig.getString("NOT_A_PLAYER");
        ARGUMENT_DOESNT_EXIST = msgConfig.getString("ARGUMENT_DOESNT_EXIST");
        PLAYER_NOT_HAVE_PERMISSION = msgConfig.getString("PLAYER_NOT_HAVE_PERMISSION");
        ARENA_DOESNT_EXIST = msgConfig.getString("ARENA_DOESNT_EXIST");
    }

    public void send(Object p, String message) {
        if (p instanceof Player) {
            ((Player) p)
                    .sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUltimateSpleef&8]&a " + message));
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a " + message));
        }
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");

        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }

        return (WorldEditPlugin) plugin;
    }

    public void log(String msg, LogType type) {
        String prefix = null;
        if (type.equals(LogType.Info))
            prefix = "&8[&aUltimateSpleefInfo&8]&a ";
        if (type.equals(LogType.Warning))
            prefix = "&8[&aUltimateSpleefWarning&8]&c ";
        if (type.equals(LogType.Severe))
            prefix = "&8[&aUltimateSpleefSevere&8]&4 ";
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
