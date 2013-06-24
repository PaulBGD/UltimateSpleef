package me.ultimate.UltimateSpleef.Commands;

import me.ultimate.UltimateSpleef.UltimateSpleef;
import me.ultimate.UltimateSpleef.ArenaManager.Arena;
import me.ultimate.UltimateSpleef.ArenaManager.ArenaManager;
import me.ultimate.UltimateSpleef.CustomEvents.PlayerArenaJoinEvent;
import me.ultimate.UltimateSpleef.Enums.RoR;

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
            RoR type = Arena.getCostType();
            int cost = Arena.getCost();
            if (type.equals(RoR.Money)) {
                if (US.econ.getBalance(p.getName()) >= cost) {
                    PlayerArenaJoinEvent event = new PlayerArenaJoinEvent(p, Arena);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        Arena.addPlayer(p);
                        US.econ.withdrawPlayer(p.getName(), cost);
                    }
                } else {
                    int needed = (int) (cost - US.econ.getBalance(p.getName()));
                    US.send(p, US.NOT_ENOUGH_MONEY.replaceAll("%bal%", needed + ""));
                }
            }
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
