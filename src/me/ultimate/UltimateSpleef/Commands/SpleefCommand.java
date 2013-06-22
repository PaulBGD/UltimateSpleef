package me.ultimate.UltimateSpleef.Commands;

import java.util.HashMap;
import java.util.Map;

import me.ultimate.UltimateSpleef.UltimateSpleef;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpleefCommand implements CommandExecutor {

    HashMap<String, BaseCommand> commandClasses = new HashMap<String, BaseCommand>();

    UltimateSpleef US;

    public SpleefCommand(UltimateSpleef US) {
        this.US = US;
        registerArgument(new CreateArena());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0) {
                boolean cont = true;
                BaseCommand cmdClass = null;
                for (Map.Entry<String, BaseCommand> entry : commandClasses.entrySet()) {
                    if (cont) {
                        if (entry.getKey().equalsIgnoreCase(args[0])) {
                            cont = false;
                            cmdClass = entry.getValue();
                        }
                    }
                }
                if (!cont) {
                    int argsNeeded = cmdClass.getLength();
                    if (args.length - 1 == argsNeeded) {
                        if (p.hasPermission(cmdClass.getPermission())
                                || p.hasPermission("UltimateSpleef." + cmdClass.getCommand())) {
                            cmdClass.perform(p, args[1]);
                            return true;
                        } else {
                            US.send(p,
                                    US.PLAYER_NOT_HAVE_PERMISSION.replaceAll("%perm%",
                                            "UltimateSpleef." + cmdClass.getCommand()));
                            return true;
                        }
                    } else {
                        //There aren't enough arguments!
                        US.send(p, US.NOT_ENOUGH_ARGUMENTS);
                        return true;
                    }
                } else {
                    //Argument doesn't exist!
                    US.send(p, US.ARGUMENT_DOESNT_EXIST);
                    return true;
                }
            } else {
                //Not enough arguments!
                US.send(p, US.ARGUMENT_DOESNT_EXIST);
                return true;
            }
        } else {
            //Sender is not a player!
            US.send(sender, US.NOT_A_PLAYER);
            return true;
        }
    }

    void registerArgument(BaseCommand baseCmd) {
        commandClasses.put(baseCmd.getCommand(), baseCmd);
    }
}
