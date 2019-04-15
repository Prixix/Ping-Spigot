package de.prixix.ping;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	
	FileConfiguration config = getConfig();
	
	public void onEnable() {
		
		config.addDefault("permission", false);
		config.addDefault("permission_name", "ping.use");
		config.addDefault("no_permission", "§cYou can't use this command.");
		config.addDefault("message", "§aYour ping: [ping]ms.");
	    config.options().copyDefaults(true);
	    saveConfig();
	    
	    getCommand("ping").setExecutor(this);
	    
		getServer().getConsoleSender().sendMessage("§7[§aPING§7] §3Has been loaded.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 0) {
			Player p = (Player) sender;
			int ping = ((CraftPlayer) p).getHandle().ping; 
			if(config.getBoolean("permission")) {
				if(p.hasPermission(config.getString("permission_name"))) {
					p.sendMessage(config.getString("message").replace("[ping]", String.valueOf(ping)));
				} else {
					p.sendMessage(config.getString("no_permission"));
				}
			} else {
				p.sendMessage(config.getString("message").replace("[ping]", String.valueOf(ping)));
			}
			} else if(args.length == 1){
				// check other ping
			}
		} else {
			sender.sendMessage("§7[§aPING§7] §cYou need to be a player.");
		}
		return true;
	}
}
