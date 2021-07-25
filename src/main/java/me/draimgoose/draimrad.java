package me.draimgoose;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public final class draimrad extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Handler(), this);
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
        scheduler.scheduleSyncRepeatingTask(this, this::checklight, 0L, 0);
        scheduler.scheduleSyncRepeatingTask(this, this::playsound, 0L, 20);
        scheduler.scheduleSyncRepeatingTask(this, new Handler()::damageforhelmet, 0L, 20);
        scheduler.scheduleSyncRepeatingTask(this, this::damagep, 0, 10);
        scheduler.scheduleSyncRepeatingTask(this, this::raindamage, 0, 10);
        scheduler.scheduleSyncRepeatingTask(this, this::voice, 0, 35);
        scheduler.scheduleSyncRepeatingTask(this, new Handler()::damagearmor, 0L,8);
        saveConfig();

    }

    @Override
    public void onDisable() {

    }

    public void voice(){
        for(Player p : Bukkit.getOnlinePlayers()){
            ItemStack i = p.getInventory().getHelmet();
            if(i != null){
                p.playSound(p.getLocation(),Sound.ENTITY_TURTLE_AMBIENT_LAND,10,0.5f);
            }
        }
    }

    public void checklight() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack i = p.getInventory().getHelmet();
            if (p.getLocation().getBlock().getLightFromSky() >= 1) {
                p.spawnParticle(Particle.ASH, p.getLocation(), 100, 10, 1, 10);
                if (i == null) p.sendMessage(ChatColor.RED + "Осторожно! Опасный уровень радиации!");
            }
        }
    }

    public void playsound() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().getBlock().getLightFromSky() >= 1) {
                p.playSound(p.getLocation(), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, 1, 1);

            }
        }
    }

    public void damagep(){
        for (Player p : Bukkit.getOnlinePlayers()){
            ItemStack i = p.getInventory().getHelmet();
            if (p.getLocation().getBlock().getLightFromSky() >= 1){
                if (p.getGameMode() == GameMode.SURVIVAL  && i == null){
                    p.damage(2);
                    p.spawnParticle(Particle.DAMAGE_INDICATOR, p.getLocation(), 5, 1, 1, 1);
                }
            }
        }
    }

    public void raindamage() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack c = p.getInventory().getChestplate();
            ItemStack l = p.getInventory().getLeggings();
            ItemStack b = p.getInventory().getBoots();
            if (p.isInWater() && p.getGameMode() == GameMode.SURVIVAL) {
                if (c == null || l == null || b == null) {
                    p.damage(2);
                    p.spawnParticle(Particle.CLOUD, p.getLocation(), 10, 1, 1, 1);
                }
            }
        }
    }
}