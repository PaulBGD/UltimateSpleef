package me.ultimate.UltimateSpleef.Commands;

import me.ultimate.UltimateSpleef.UltimateSpleef;
import me.ultimate.UltimateSpleef.ArenaManager.Arena;
import me.ultimate.UltimateSpleef.ArenaManager.ArenaManager;
import me.ultimate.UltimateSpleef.CustomEvents.PlayerArenaJoinEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Join implements BaseCommand {

    @Override
    public void perform(UltimateSpleef US, Player p, String args) {
        boolean cont = true;
        Arena Arena = null;
        for (Arena arna : ArenaManager.arenaDefault) {
            if (cont && arna.getName().equalsIgnoreCase(args)) {
                Arena = arna;
                cont = false;
            }
        }
        if (!cont) {
            //Arena exists! Annnnd I forgot to check if the player has the item/money required to join..
            Arena.addPlayer(p);
            Bukkit.getPluginManager().callEvent(new PlayerArenaJoinEvent(p, Arena));
        } else {
            //The arena doesn't exist!
            US.send(p, US.ARENA_DOESNT_EXIST.replaceAll("%arena%", args.toLowerCase()));
        }
    }

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getPermission() {
        return "ultimatespleef.player";
    }

}
