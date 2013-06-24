package me.ultimate.UltimateSpleef.ArenaManager;

import me.ultimate.UltimateSpleef.UltimateSpleef;
import me.ultimate.UltimateSpleef.CustomEvents.PlayerArenaJoinEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaJoining implements Listener {

    UltimateSpleef US;

    public ArenaJoining(UltimateSpleef US) {
        this.US = US;
    }

    @EventHandler
    public void onPlayerArenaJoin(PlayerArenaJoinEvent event) {
        event.getPlayer().teleport(event.getArena().joinLoc);
        US.send(event.getPlayer(), US.ARENA_JOIN_MESSAGE.replaceAll("%arena%", event.getArena().getName()));
    }
}
