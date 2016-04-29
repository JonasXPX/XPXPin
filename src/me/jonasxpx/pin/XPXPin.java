package me.jonasxpx.pin;

import java.util.logging.Level;

import me.jonasxpx.pin.cmds.AtivarPin;
import me.jonasxpx.pin.cmds.RecuperarPin;
import me.jonasxpx.pin.cmds.ResetarPin;
import me.jonasxpx.pin.managers.ManagerPin;

import org.bukkit.plugin.java.JavaPlugin;
/**
 * 
 * @author jonas
 * @Factory 01:03 - 29-Apr-2016
 * @version 0.1b
 * @category plugin
 */
public class XPXPin extends JavaPlugin{

	private static XPXPin instance;
	private static MSQLConnection connection;
	private static ManagerPin mp;
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		instance = this;
		mp = new ManagerPin();
		getCommand("ativarpin").setExecutor(new AtivarPin());
		getCommand("recuperarpin").setExecutor(new RecuperarPin());
		getCommand("resetarpin").setExecutor(new ResetarPin());
		connection = loadConfigOfDB();
	}
	
	public static XPXPin getInstance(){
		return instance;
	}
	
	public MSQLConnection getData(){
		return connection;
	}
	
	public ManagerPin getManagerPin(){
		return mp;
	}
	
	private MSQLConnection loadConfigOfDB(){
		getLogger().log(Level.INFO, "Carregando dados do banco de dados, e inicializando...");
		return new MSQLConnection(getConfig().getString("DataBase.address"), getConfig().getString("DataBase.database"), getConfig().getString("DataBase.username"), getConfig().getString("DataBase.password"));
	}
}
