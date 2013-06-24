//Arena Object
package me.ultimate.UltimateSpleef.ArenaManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ultimate.UltimateSpleef.UltimateSpleef;
import me.ultimate.UltimateSpleef.Utils;
import me.ultimate.UltimateSpleef.Enums.RoR;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class Arena {

    public String arenaName;
    public World arenaWorld;
    UltimateSpleef US;
    File arenaBoard = new File(US.dataFolder + File.separator + "Arenas/" + arenaName + "Arena.schematic");
    List<String> arenaPlayers = new ArrayList<String>();
    boolean running = false;
    List<Location> arenaBlocks = new ArrayList<Location>();
    File configFile = new File(US.dataFolder + File.separator + "Arenas/" + arenaName + "Arena.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    int lowestY;
    RoR costType = RoR.None;
    RoR rewardType;
    String reward;
    int cost;
    Location endLoc;
    Location joinLoc;

    public Arena(String arenaName, World arenaWorld, UltimateSpleef US) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            US.sendError(e, "Couldn't save/reload " + arenaName + "'s config file!");
        }
        this.arenaName = arenaName;
        this.arenaWorld = arenaWorld;
        this.US = US;
        arenaBoard = new File(US.dataFolder + File.separator + "Arenas/" + arenaName + "Arena.schematic");
        int x = config.getInt("Location1.x");
        int y = config.getInt("Location1.y");
        int z = config.getInt("Location1.z");
        int x2 = config.getInt("Location2.x");
        int y2 = config.getInt("Location2.y");
        int z2 = config.getInt("Location2.z");
        Location loc1 = new Location(arenaWorld, x, y, z);
        Location loc2 = new Location(arenaWorld, x2, y2, z2);
        arenaBlocks = Utils.blocksFromTwoPoints(loc1, loc2);
        lowestY = getLowestY();
        if (getConfig().getString("CostType").equalsIgnoreCase("Money")) {
            costType = RoR.Money;
        }
        reward = getConfig().getString("Reward");
        cost = getConfig().getInt("Cost");
        joinLoc = US.Utils.getLocation("Join", config);
        endLoc = US.Utils.getLocation("End", config);
    }

    public void saveArenaBoard(Player p) {
        File targetFile = arenaBoard;
        SchematicFormat format = SchematicFormat.MCEDIT;
        CuboidClipboard clipboard;
        try {
            clipboard = US.getWorldEdit().getSession(p).getClipboard();
            format.save(clipboard, targetFile);
        } catch (EmptyClipboardException e) {
            US.sendError(e, "Could not save schematic to file");
        } catch (IOException e) {
            US.sendError(e, "Could not save schematic to file");
        } catch (DataException e) {
            US.sendError(e, "Could not save schematic to file");
        }
        config.set("PasteLocation.x", p.getLocation().getBlockX());
        config.set("PasteLocation.y", p.getLocation().getBlockY());
        config.set("PasteLocation.z", p.getLocation().getBlockZ());
    }

    public void loadArenaBoard() {
        File targetFile = arenaBoard;
        SchematicFormat format = SchematicFormat.MCEDIT;
        EditSession es = new EditSession(new BukkitWorld(null), 9999999);
        CuboidClipboard clip;
        try {
            clip = format.load(targetFile);
            clip.paste(es, BukkitUtil.toVector(new Location(arenaWorld, config.getInt("PasteLocation.x"), config
                    .getInt("PasteLocation.y"), config.getInt("z"))), false);
        } catch (IOException e) {
            US.sendError(e, "Could not load/paste schematic");
        } catch (DataException e) {
            US.sendError(e, "Could not load/paste schematic");
        } catch (MaxChangedBlocksException e) {
            US.sendError(e, "Could not load/paste schematic");
        }
    }

    public String getName() {
        return arenaName;
    }

    public World getWorld() {
        return arenaWorld;
    }

    public boolean isRunning() {
        return running;
    }

    public List<String> getPlayers() {
        return arenaPlayers;
    }

    public void addPlayer(Player p) {
        arenaPlayers.add(p.getName());
    }

    public void removePlayer(Player p) {
        arenaPlayers.remove(p.getName());
    }

    public void endArena() {
        for (String pName : this.getPlayers()) {
            Player p = Bukkit.getPlayer(pName);
            p.teleport(endLoc);
        }
    }

    public void startArena() {

    }

    public boolean isArenaBlock(Location loc) {
        if (arenaBlocks.contains(loc))
            return true;
        return false;
    }

    public boolean isArenaBlock(Block block) {
        return isArenaBlock(block.getLocation());
    }

    public int getLowestY() {
        int y = 256;
        for (Location loc : arenaBlocks) {
            if (loc.getBlockY() < 256)
                y = loc.getBlockX();
        }
        return y;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public RoR getRewardType() {
        return rewardType;
    }

    public RoR getCostType() {
        return costType;
    }

    public String getReward() {
        return reward;
    }

    public int getCost() {
        return cost;
    }

}
