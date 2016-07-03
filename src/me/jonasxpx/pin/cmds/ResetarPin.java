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
				sender.sendMessage("�9[PIN] Jogador " + args[0].toLowerCase() +  " Adicionado ao pr�-reset de PIN");
				return true; 
			}
		}
		if(args.length > 0){
			return true;
		}
		if(!canReset(sender.getName())){
			sender.sendMessage("�9[PIN] �bVoc� n�o pode resetar seu PIN, comando liberado ap�s ativa��o VIP." );
			return true;
		}
		
		XPXPin.getInstance().getData().unregisterPin(sender.getName());
		remove(sender.getName());
		sender.sendMessage("�9[PIN] �bA��o concluida!.");
		return false;
	}
}
