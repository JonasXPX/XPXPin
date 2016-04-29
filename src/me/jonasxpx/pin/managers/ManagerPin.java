package me.jonasxpx.pin.managers;

import java.util.Random;

import me.jonasxpx.pin.MSQLConnection;
import me.jonasxpx.pin.XPXPin;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManagerPin {

	public ManagerPin(){}
	
	
	public void register(Player player){
		MSQLConnection data = XPXPin.getInstance().getData();
		
		if(data.isRegistred(player.getName())){
			player.sendMessage("§b[PIN] Seu §lPIN§b já está registrado, você só pode resetar seu PIN ativando um VIP.");
			return;
		}

		Random r = new Random();
		int pin = r.nextInt(9999999);
		
		data.registerPin(player.getName(), pin);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				player.kickPlayer("§c§lATENÇAO! §bseu pin foi gerado §c§lANOTE SEU PIN§b para não perdê-lo\n\n"
						+ "§bSEU PIN É: §b§l" + pin);
			}
		}.runTask(XPXPin.getInstance());
	}
	
	public void resetPassword(Player player, String pin){
		MSQLConnection data = XPXPin.getInstance().getData();
		
		if(!data.isRegistred(player.getName())){
			player.sendMessage("§9[PIN] §bEssa conta não tem um PIN cadastrado.");
			return;
		}
		
		try{
			if(!data.equalsPin(player.getName(), Integer.parseInt(pin))){
				player.sendMessage("§9[PIN] §bPIN incorreto.");
				return;
			}
		}catch(NumberFormatException e){
			player.sendMessage("§9[PIN] §bSeu PIN deve conter somente números.");
			return;
		}
		
		Random r = new Random();
		int password = r.nextInt(99999999);
		
		XPXPin.getInstance().getServer().dispatchCommand(XPXPin.getInstance().getServer().getConsoleSender()
				, "authme changepassword " + player.getName() + " " + password);

		new BukkitRunnable() {
			@Override
			public void run() {
				player.kickPlayer("§c§lATENÇAO! §bVOCÊ ALTEROU SUA SENHA USANDO SEU PIN\n\n"
						+ "§bSUA NOVA SENHA É: §6§l" + password + "\n\n"
								+ "§c§lAnote se necessário, §baltere sua senha com §l/changepassword");
			}
		}.runTask(XPXPin.getInstance());
	}
	
	
	public void resetar(String player){
		MSQLConnection data = XPXPin.getInstance().getData();
		
		if(!data.isRegistred(player)){
			return;
		}
		
		data.unregisterPin(player);
	}
	
}


