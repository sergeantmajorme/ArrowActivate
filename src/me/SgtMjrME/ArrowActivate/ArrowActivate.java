package me.SgtMjrME.ArrowActivate;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.material.Button;
import org.bukkit.material.Wool;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ArrowActivate extends JavaPlugin{
	private Logger log;
	private PluginManager pm;
	private ArrowListener arrowListener;
	
	@Override
	public void onEnable()
	{
		log = getServer().getLogger();
		pm = getServer().getPluginManager();
		arrowListener = new ArrowListener(this);
		pm.registerEvents(arrowListener, this);
		log.info("[AA] Activated");
	}
	
	@Override
	public void onDisable()
	{
		//Nothing
	}
	
	public void blockHit(ProjectileHitEvent e)
	{
		Arrow a;
		if (!(e.getEntity() instanceof Arrow))
			return;
		a = (Arrow) e.getEntity();
		if (!(a.getShooter() instanceof Player))
			return;
		Location l = a.getLocation();
		for (int y = 0; y<2; y++)
			for(int x = 0;x<5;x++)
				for(int z = 0;z<5;z++)
				{
					Location temp = l.clone();
					temp.add(x-2, y-1, z-2);
					if (temp.getBlock().getTypeId() == 77 /*button*/)
					{
						Location hold = temp.clone();
						int tempx = (int) temp.getBlock().getX();
						int tempy = (int) temp.getBlock().getY();
						int tempz = (int) temp.getBlock().getZ();
						getBlockLoc(temp);
						if (temp.getBlock().getTypeId() != 35)
							return;
						Wool wool = (Wool) temp.getBlock().getState().getData();
						if (!wool.getColor().name().equals("RED"))
							return;
						a.remove();
						Player player = (Player) e.getEntity().getShooter();
						net.minecraft.server.Block.byId[hold.getBlock().getTypeId()].interact(((CraftWorld)temp.getWorld()).getHandle(),tempx,tempy,tempz, ((CraftPlayer)player).getHandle());
						temp.getBlock().getState().update(true);
						break;
					}
				}
	}

	private void getBlockLoc(Location l) {
		BlockFace face = ((Button) l.getBlock().getState().getData()).getAttachedFace();
		if (face.equals(BlockFace.EAST))
			l.setZ(l.getZ() - 1);
		else if (face.equals(BlockFace.WEST))
			l.setZ(l.getZ() + 1);
		else if (face.equals(BlockFace.NORTH))
			l.setX(l.getZ() - 1);
		else if (face.equals(BlockFace.SOUTH))
			l.setX(l.getZ() + 1);
	}
	
	
}
