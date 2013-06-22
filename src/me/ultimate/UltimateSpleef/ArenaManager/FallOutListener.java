package me.ultimate.UltimateSpleef.ArenaManager;

import me.ultimate.UltimateSpleef.CustomEvents.LeaveCauses;
import me.ultimate.UltimateSpleef.CustomEvents.PlayerArenaQuitEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FallOutListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (ArenaManager.isPlayerInArena(event.getPlayer())) {
            Player p = event.getPlayer();
            Arena arena = ArenaManager.getPlayerArena(event.getPlayer());
            if (p.getLocation().getBlockY() < arena.lowestY) {
                Bukkit.getServer().getPluginManager()
                        .callEvent(new PlayerArenaQuitEvent(p, arena, LeaveCauses.Player_Fallout));
            }
        }
    }
}
