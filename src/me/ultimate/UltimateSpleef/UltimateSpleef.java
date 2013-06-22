package me.ultimate.UltimateSpleef;

import java.io.File;
import java.io.IOException;

import me.ultimate.UltimateSpleef.Commands.SpleefCommand;
import me.ultimate.UltimateSpleef.Enums.LogType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class UltimateSpleef extends JavaPlugin {

    public FileConfiguration msgConfig;
    public File dataFolder;

    public void onEnable() {
        dataFolder = getDataFolder();
        getCommand("spleef").setExecutor(new SpleefCommand(this));
        File messages = new File(getDataFolder() + File.separator + "messages.yml");
        msgConfig = YamlConfiguration.loadConfiguration(messages);
            log("Could not find messages.yml! Generating default one now..", LogType.Warning);
            setupMessageDefaults(messages, msgConfig);
            try {
                msgConfig.save(messages);
            } catch (IOException e) {
                sendError(e, "Could not save messages file");
            }
        if (getWorldEdit() == null) {
            log("Could not find WorldEdit! Disabling..", LogType.Severe);
            getServer().getPluginManager().disablePlugin(this);
        }
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

    private void setupMessageDefaults(File file, FileConfiguration msg) {
        msg.set("NOT_ENOUGH_ARGUMENTS", "You do not have enough arguments!");
        msg.set("NOT_A_PLAYER", "&8[&aUltimateSpleef&8]&a You are not a player!");
        msg.set("ARGUMENT_DOESNT_EXIST", "You did something wrong! Try doing /spleef help!");
        msg.set("PLAYER_NOT_HAVE_PERMISSION", "&4You do not have the permission %perm%!");
        msg.set("ARENA_DOESNT_EXIST", "&4The arena %arena% does not exist!");
        try {
            msg.save(file);
        } catch (IOException e) {
            sendError(e, "Could not save messages file");
        }
    }

    public String NOT_ENOUGH_ARGUMENTS = msgConfig.getString("NOT_ENOUGH_ARGUMENTS");
    public String NOT_A_PLAYER = msgConfig.getString("NOT_A_PLAYER");
    public String ARGUMENT_DOESNT_EXIST = msgConfig.getString("ARGUMENT_DOESNT_EXIST");
    public String PLAYER_NOT_HAVE_PERMISSION = msgConfig.getString("PLAYER_NOT_HAVE_PERMISSION");
    public String ARENA_DOESNT_EXIST = msgConfig.getString("ARENA_DOESNT_EXIST");

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
}
