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

import java.util.ArrayList;
import java.util.Random;

public class ExplodePlayer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, 8385);
        beginKills();
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Enabled ExplodePlayer!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Timer started....");

    }

    public void beginKills() {
        new BukkitRunnable() {
            public void run() {
                    Player target = getRandomPlayer();
                    if (target == null) return;
                    if (target.getGameMode() == GameMode.CREATIVE) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + "You were saved this time! Won't be so lucky next time!");
                        return;
                    }
                    if (target.isDead()) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + "You were saved this time! Won't be so lucky next time!");
                        return;
                    }
                    target.setHealth(0);
                    target.getLocation().getWorld().createExplosion(target.getLocation(), 1, true);
                    Bukkit.broadcastMessage(ChatColor.RED + "60 seconds tell next explosion!");
            }
        }.runTaskTimer(this, 0L, 1200L);
    }

    public static Player getRandomPlayer() {
        ArrayList<Player> online = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (online.size() != 0) {
            return online.get(new Random().nextInt(online.size()));
        }
        return null;
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (player == null) return;
        event.setDeathMessage(null);
        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " died! Who's next?");
    }

}
