package me.ultimate.UltimateSpleef.CustomEvents;

import me.ultimate.UltimateSpleef.ArenaManager.Arena;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArenaQuitEvent extends Event  {

    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private Arena arena;
    LeaveCauses cause;

    public PlayerArenaQuitEvent(Player p, Arena arena, LeaveCauses cause) {
        this.p = p;
        this.arena = arena;
        this.cause = cause;
    }
    
    public LeaveCauses getCause(){
        return cause;
    }

    public Player getPlayer() {
        return p;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
