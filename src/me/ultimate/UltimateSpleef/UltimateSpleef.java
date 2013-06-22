package me.ultimate.UltimateSpleef;

import java.io.File;
import java.io.IOException;

import me.ultimate.UltimateSpleef.Commands.SpleefCommand;

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
        if (!messages.exists()) {
            getLogger().warning("Could not find messages.yml! Generating default one now..");
            setupMessageDefaults(messages, msgConfig);
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

    public String NOT_ENOUGH_ARGUMENTS = "You do not have enough arguments!";
    public String NOT_A_PLAYER = "&8[&aUltimateSpleef&8]&a You are not a player!";
    public String ARGUMENT_DOESNT_EXIST = "You did something wrong! Try doing /spleef help!";
    public String PLAYER_NOT_HAVE_PERMISSION = "&4You do not have the permission %perm%!";

    private void setupMessageDefaults(File file, FileConfiguration msg) {
        msg.set("NOT_ENOUGH_ARGUMENTS", NOT_ENOUGH_ARGUMENTS);
        msg.set("NOT_A_PLAYER", NOT_A_PLAYER);
        msg.set("ARGUMENT_DOESNT_EXIST", ARGUMENT_DOESNT_EXIST);
        msg.set("PLAYER_NOT_HAVE_PERMISSION", PLAYER_NOT_HAVE_PERMISSION);
        try {
            msg.save(file);
        } catch (IOException e) {
            sendError(e, "Could not save messages file");
        }
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
     
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
     
        return (WorldEditPlugin) plugin;
    }
}
