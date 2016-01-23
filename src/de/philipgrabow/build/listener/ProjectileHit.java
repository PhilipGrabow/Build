package de.philipgrabow.build.listener;

import org.bukkit.EntityEffect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class ProjectileHit implements Listener {
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onProjectileHit (ProjectileHitEvent e) {
		e.getEntity().setVelocity(new Vector(0,3,0));
		e.getEntity().playEffect(EntityEffect.DEATH);
		
	}

}
