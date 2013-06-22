package me.ultimate.UltimateSpleef.Commands;

import org.bukkit.entity.Player;

public abstract interface BaseCommand {

    public abstract void perform(Player p, String args);
    public abstract String getCommand();
    public abstract int getLength();
    public abstract String getPermission();

}
