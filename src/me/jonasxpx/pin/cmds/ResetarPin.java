package me.jonasxpx.pin.cmds;

import me.jonasxpx.pin.ResetPin;
import me.jonasxpx.pin.XPXPin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetarPin extends ResetPin implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender.isOp()){
			if(args.length == 1){
				putToReset(args[0]);
				sender.sendMessage("§9[PIN] Jogador " + args[0].toLowerCase() +  " Adicionado ao pré-reset de PIN");
				return true; 
			}
		}
		if(args.length > 0){
			return true;
		}
		if(!canReset(sender.getName())){
			sender.sendMessage("§9[PIN] §bVocê não pode resetar seu PIN, comando liberado após ativação VIP." );
			return true;
		}
		
		XPXPin.getInstance().getData().unregisterPin(sender.getName());
		remove(sender.getName());
		sender.sendMessage("§9[PIN] §bAção concluida!.");
		return false;
	}
}
