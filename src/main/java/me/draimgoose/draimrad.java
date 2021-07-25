package me.draimgoose;

import com.google.common.io.Files;
import me.draimgoose.commands.MainComand;
import me.draimgoose.commands.MainCommandTab;
import me.draimgoose.events.CheckLight;
import me.draimlib.settings.Configuration;
import me.draimlib.settings.Lang;
import me.draimlib.settings.Settings;
import me.draimlib.utils.Messages;
import me.draimlib.utils.MessagesManager;
import org.bukkit.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.Set;


public final class draimrad extends JavaPlugin {

    private Settings settings;
    private Lang lang;
    private MessagesManager messagesManager;

    @Override
    public void onEnable() {
        Messages.log(this, "&9=============================================================");
        Messages.log(this, "&2DraimRad &av" + this.getDescription().getVersion());

        Messages.log(this, "&9=============================================================");
        Messages.log(this, "&2Создано DraimGooSe для DraimCiDo");
        Messages.log(this, "&9=============================================================");
        this.updateConfig();
        this.loadConfigManager();
        Messages.log(this, "&2Чтение загружено!");
        this.registerCommand();
        Messages.log(this, "&2Команды загружены!");
        this.registerPermissions();
        Messages.log(this, "&9=============================================================");
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

    public void loadConfigManager() {
        this.settings = new Settings(this);
        Messages.log(this, "&2Конфигурация загружена!");
        this.lang = new Lang(settings.getLang(), this);
        Messages.log(this, "&2Язык загружен! &7[" + settings.getLang() + "]");
        if (this.messagesManager != null) {
            messagesManager.setLang(lang);
            messagesManager.setSettings(settings);
        } else
            this.messagesManager = new MessagesManager(settings, lang, this);
    }

    public void updateConfig() {
        Configuration.updateConfig("lang/ru_RU.yml", this);
        Configuration oldConfig = new Configuration("config.yml", this);
        if (oldConfig.contains("")) {
            try {
                Files.move(oldConfig.getFile(), new File(oldConfig.getFile().getParentFile(), "old_config.yml"));
                oldConfig = new Configuration("old_config.yml", this);
                this.settings = new Settings(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration.updateConfig("config.yml", this);
    }

    public void registerCommand() {
        PluginCommand mainCommand = this.getCommand("draimr");
        if (mainCommand != null) {
            mainCommand.setExecutor(new MainComand(this));
            mainCommand.setTabCompleter(new MainCommandTab());
        }
    }

    public void registerPermissions() {
        PluginManager pm = getServer().getPluginManager();
        Set<Permission> permissions = pm.getPermissions();
        int n = 0;
        for (EntityType entityType : EntityType.values()) {
            Permission perm = new Permission("draimrad.bypass.entity." + entityType.name());
            if (!permissions.contains(perm)) {
                pm.addPermission(perm);
                n++;
            }
        }
        Messages.log(this, "&2Права для существ загружены! &7[" + n + "]");
    }

    public MessagesManager getMessageManager() {
        return messagesManager;
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

    @Override
    public void onDisable() {

    }
}