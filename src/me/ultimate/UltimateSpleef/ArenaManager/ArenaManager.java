package me.ultimate.UltimateSpleef.ArenaManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.ultimate.UltimateSpleef.UltimateSpleef;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ArenaManager {

    HashMap<String, Arena> arenas = new HashMap<String, Arena>();
    static List<Arena> arenaDefault = new ArrayList<Arena>();

    UltimateSpleef US;

    File arenasFile;
    FileConfiguration arenasConfig;

    public ArenaManager(UltimateSpleef US) {
        this.US = US;
        arenasFile = new File(US.dataFolder + File.separator + "Arenas.yml");
        arenasConfig = YamlConfiguration.loadConfiguration(arenasFile);
        registerArenas();
    }

    public Arena getArena(String name) {
        if (arenas.containsKey(name))
            return arenas.get(name);
        return null;
    }

    private void registerArenas() {
        if (arenasConfig.isSet("Arenas")) {
            for (Object arna : arenasConfig.getList("Arenas")) {
                Arena arena = new Arena(arna.toString(), Bukkit.getWorld(YamlConfiguration.loadConfiguration(
                        new File(US.dataFolder + File.separator + "Arenas/" + arna.toString() + "Arena.yml"))
                        .getString("World")), US);
                arenas.put(arena.getName(), arena);
                arenaDefault.add(arena);
            }
        }
    }

    public static boolean isPlayerInArena(Player p) {
        for (Arena arena : arenaDefault) {
            if (arena.getPlayers().contains(p)) {
                return true;
            }
        }
        return false;
    }

    public static Arena getPlayerArena(Player p) {
        for (Arena arena : arenaDefault) {
            if (arena.getPlayers().contains(p)) {
                return arena;
            }
        }
        return null;
    }

}
