package me.jonasxpx.pin;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.jonasxpx.pin.cmds.AtivarPin;
import me.jonasxpx.pin.cmds.RecuperarPin;
import me.jonasxpx.pin.cmds.ResetarPin;
import me.jonasxpx.pin.managers.ManagerPin;
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
	private static HashMap<String, Integer> tentativas = new HashMap<>();
	private static String chPassword = null;
	
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
		chPassword = getConfig().getString("Sistema.AlterarSenha");
	}
	
	public static XPXPin getInstance(){
		return instance;
	}
	
	public MSQLConnection getData(){
		if(!checkMySQLConnection())
			loadConfigOfDB();
		return connection;
	}
	
	public ManagerPin getManagerPin(){
		return mp;
	}
	
	private MSQLConnection loadConfigOfDB(){
		getLogger().log(Level.INFO, "Carregando dados do banco de dados, e inicializando...");
		return new MSQLConnection(getConfig().getString("DataBase.address"), getConfig().getString("DataBase.database"), getConfig().getString("DataBase.username"), getConfig().getString("DataBase.password"));
	}
	
	public static boolean checkMySQLConnection(){
		return connection == null ? false : true;
	}
	
	public static boolean isOverTentativa(Player player){
		if(tentativas.containsKey(getPlayerIP(player))){
			if(tentativas.get(getPlayerIP(player)) > 5){
				return true;
			}
		}
		return false;
	}
	
	public static void registerTentativa(Player player){
		if(tentativas.containsKey(getPlayerIP(player))){
			tentativas.replace(getPlayerIP(player), tentativas.get(getPlayerIP(player)) + 1);
		} else {
			tentativas.put(getPlayerIP(player), 2);
		}
	}
	
	public static int getTentativas(Player player){
		return (tentativas.get(getPlayerIP(player)) != null ? tentativas.get(getPlayerIP(player)) : 1);
	}
	
	private static String getPlayerIP(Player player){
		return player.getAddress().getAddress().getHostAddress();
	}
	
	public static String getChangePasswordCommand(String player, String password){
		return chPassword.replaceAll("@player", player).replaceAll("@senha", password);
	}
}
