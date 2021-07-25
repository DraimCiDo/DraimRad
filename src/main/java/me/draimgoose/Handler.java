package me.draimgoose;

import com.google.common.base.Strings;
import org.bukkit.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Handler implements Listener {

    @EventHandler
    public void weatherf(WeatherChangeEvent e){
        for (Player p : Bukkit.getOnlinePlayers()){
            if(e.toWeatherState()){
                p.sendMessage(ChatColor.RED + "");
                p.sendMessage(ChatColor.DARK_RED + "     Обратите внимание");
                p.sendMessage(ChatColor.RED + "");
                p.sendMessage(ChatColor.GRAY + " На поверхности начался кислотный дождь, поэтому, ");
                p.sendMessage(ChatColor.GRAY + " пожалуйста, воздержитесь от попыток выйти на поверхность. ");
                p.sendMessage(ChatColor.RED + "");
            }
            else {
                p.sendMessage(ChatColor.RED + "");
                p.sendMessage(ChatColor.DARK_RED + "     Обратите внимание");
                p.sendMessage(ChatColor.RED + "");
                p.sendMessage(ChatColor.GRAY + " Кислотный дождь на поверхности закончился, теперь вы");
                p.sendMessage(ChatColor.GRAY + " можете подняться на поверхность, но не забывайте, что на ");
                p.sendMessage(ChatColor.GRAY + " поверхности по-прежнему нечем дышать.");
                p.sendMessage(ChatColor.RED + "");
            }
        }
    }

    public void damageforhelmet() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getInventory().getHelmet() != null) {
                ItemStack i = p.getInventory().getHelmet();
                ItemMeta im = i.getItemMeta();
                int dur;
                dur = i.getType().getMaxDurability();
                if (i.getType() == Material.LEATHER_HELMET || i.getType() == Material.IRON_HELMET || i.getType() == Material.GOLDEN_HELMET || i.getType() == Material.DIAMOND_HELMET || i.getType() == Material.NETHERITE_HELMET) {
                    if (p.getLocation().getBlock().getLightFromSky() >= 1) {
                        if (im instanceof Damageable) {
                            Damageable dmg = (Damageable) im;
                            if (dmg.getDamage() >= i.getType().getMaxDurability()){p.getInventory().removeItem(i); p.sendTitle(ChatColor.RED + "Внимание!",ChatColor.RED + "Ваш противогаз сломался!",0,40,0); p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK,1,0); p.spawnParticle(Particle.SNEEZE,p.getLocation(),56,0.5,1.2,0.6,0);}
                            dmg.setDamage(dmg.getDamage() + 1);
                            i.setItemMeta(im);
                            int dm = dmg.getDamage();
                            int dmf = dur - dm;
                            if(dmf == 10 || dmf == 9 || dmf == 8 || dmf == 7 || dmf == 6 || dmf == 5 || dmf == 4 || dmf == 3 || dmf == 2 || dmf == 1){
                                p.sendMessage(ChatColor.GRAY + "Осталось " + ChatColor.RED+dmf + ChatColor.GRAY +" секунд" + ChatColor.RED+" Замените противогаз!");
                            } else

                                p.sendMessage(ChatColor.GRAY + "Противогаз: " + ChatColor.GRAY + "[" + getProgressBar(dmf,i.getType().getMaxDurability(),40, '|', ChatColor.GREEN, ChatColor.GRAY)+ ChatColor.GRAY + "]");

                        }
                    }
                }
            }
        }
    }

    public void damagearmor(){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null){
                ItemStack c = p.getInventory().getChestplate();
                ItemStack l = p.getInventory().getLeggings();
                ItemStack b = p.getInventory().getBoots();
                ItemMeta ic = c.getItemMeta();
                ItemMeta il = l.getItemMeta();
                ItemMeta ib = b.getItemMeta();
                int cd = c.getType().getMaxDurability();
                int ld = l.getType().getMaxDurability();
                int bd = b.getType().getMaxDurability();
                if(p.isInWater()){
                    if (ic instanceof Damageable && il instanceof  Damageable && ib instanceof Damageable){
                        Damageable dmgc = (Damageable) ic;
                        Damageable dmgl = (Damageable) il;
                        Damageable dmgb = (Damageable) ib;
                        if(dmgc.getDamage() >= c.getType().getMaxDurability()) {
                            p.getInventory().removeItem(c);
                            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0);
                        }
                        if(dmgl.getDamage() >= l.getType().getMaxDurability()) {
                            p.getInventory().removeItem(l);
                            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0);
                        }
                        if(dmgb.getDamage() >= b.getType().getMaxDurability()) {
                            p.getInventory().removeItem(b);
                            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0);
                        }
                        dmgc.setDamage(dmgc.getDamage() + 1);
                        dmgl.setDamage(dmgl.getDamage() + 1);
                        dmgb.setDamage(dmgb.getDamage() + 1);
                        c.setItemMeta(ic);
                        l.setItemMeta(il);
                        b.setItemMeta(ib);
                    }
                }
            }
        }
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

}