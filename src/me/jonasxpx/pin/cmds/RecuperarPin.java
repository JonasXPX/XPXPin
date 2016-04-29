package me.jonasxpx.pin.cmds;

import me.jonasxpx.pin.XPXPin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecuperarPin implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length <= 0){
			sender.sendMessage("§9[PIN] §bUtilize: /recuperarpin <PIN>");
			return true;
		}
		XPXPin.getInstance().getManagerPin().resetPassword((Player)sender, args[0]);
		return false;
	}

}
