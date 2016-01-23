package de.philipgrabow.build.UUID;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UUID implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin (PlayerJoinEvent e ) throws EventException {
		File UUIDlist = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDlist);
		cfg.set(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
		try {
			cfg.save(UUIDlist);
		} catch (IOException e1) {
			System.out.println("Fehler beim Speichern!");
		}
	}

}
