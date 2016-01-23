package de.philipgrabow.executor;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GodwithNightVisionMode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(sender.hasPermission("bcp.godmode")) {
				if(cmd.getName().equalsIgnoreCase("godmode")) {
					GodwithNightVision((Player) sender);
					return true;
				}
			} else {
				sender.sendMessage("Du hast keine Berechtigung dafür!");
				return true;
			}
		} else {
			sender.sendMessage("Der Command kann bloß von einem Spieler ausgeführt werden!");
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public void GodwithNightVision(Player p) {
		if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
		//GODMODE
		p.setAllowFlight(true);
		p.setGameMode(GameMode.CREATIVE);
		String pname = p.getName();
		p.setDisplayName("§c[GOD] §f" + pname);
//		p.setFlySpeed(2);
//		p.setPlayerListName("§c[GOD]§f" + pname);
//		p.setWalkSpeed(2);
		p.setHealth(20.0);
		p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
		//
		//NightVision
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999*500, 999*500));
		p.sendMessage("GodMode Aktiviert!!!");
		//
		} else {
			p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			p.setDisplayName(p.getName());
			p.sendMessage("GodMode Deaktiviert!!!");
		}
	}
	
	


}
