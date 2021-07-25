package me.draimgoose.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CheckLight implements Listener {

    @EventHandler
    public void Checklight() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack i = p.getInventory().getHelmet();
            if (p.getLocation().getBlock().getLightFromSky() >= 1) {
                p.spawnParticle(Particle.ASH, p.getLocation(), 100, 10, 1, 10);
                if (i == null) p.sendMessage(ChatColor.RED + "Осторожно! Опасный уровень радиации!");
            }
        }
    }
}
