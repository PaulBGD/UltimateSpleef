package me.ultimate.UltimateSpleef;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Utils {

    public static List<Location> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Location> blocks = new ArrayList<Location>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    blocks.add(new Location(loc1.getWorld(), x, y, z));
                }
            }
        }

        return blocks;
    }

    public Location getLocation(String path, FileConfiguration config) {
        Location loc = new Location(Bukkit.getWorld(config.getString(path + "World")), config.getDouble(path + "EndX"),
                config.getDouble(path + "EndY"), config.getDouble(path + "EndZ"), config.getInt(path + "EndYaw"),
                config.getInt(path + "EndPitch"));
        return loc;
    }
}
