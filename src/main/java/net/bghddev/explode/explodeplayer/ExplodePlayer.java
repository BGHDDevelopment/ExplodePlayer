package net.bghddev.explode.explodeplayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ExplodePlayer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        beginKills();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void beginKills() {
        new BukkitRunnable() {
            public void run() {
                Player target = Bukkit.getOnlinePlayers().iterator().next();
                if (target == null) return;
                if (target.getGameMode() == GameMode.CREATIVE) return;
                if (target.isDead()) return;
                target.setHealth(0);
                target.getLocation().getWorld().createExplosion(target.getLocation(), 1, true);
                Bukkit.broadcastMessage(ChatColor.RED + "60 seconds to next explosion!");
            }
        }.runTaskTimer(this, 0L, 1200L);
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (player == null) return;
        event.setDeathMessage(null);
        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " died! Who's next?");
    }

}
