package me.ultimate.UltimateSpleef.CustomEvents;

import me.ultimate.UltimateSpleef.ArenaManager.Arena;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArenaJoinEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;
    private Player p;
    private Arena arena;

    public PlayerArenaJoinEvent(Player p, Arena arena) {
        this.p = p;
        this.arena = arena;
    }

    public Player getPlayer() {
        return p;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        cancel = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
