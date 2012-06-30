package me.SgtMjrME.ArrowActivate;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowListener implements Listener{
	private ArrowActivate a;
	
	ArrowListener(ArrowActivate b)
	{
		a = b;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void ProjectileEvent(ProjectileHitEvent e)
	{
		if (e.getEntity().getShooter() instanceof Player)
		a.blockHit(e);
	}
}
