package me.jonasxpx.pin.cmds;

import me.jonasxpx.pin.XPXPin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AtivarPin implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		XPXPin.getInstance().getManagerPin().register((Player)sender);;
		return false;
	}
}
