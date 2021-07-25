package me.draimgoose.commands;

import me.draimgoose.draimrad;
import me.draimlib.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

public class MainComand implements CommandExecutor {
    private final draimrad plugin;

    public MainComand(draimrad instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Conversable conversable = null;
        if (sender instanceof ConsoleCommandSender)
            conversable = (ConsoleCommandSender) sender;
        else if (sender instanceof Player)
            conversable = (Player) sender;
        if (args.length >= 1) {
            if (args[0].equals("reload")) {
                if (!(sender instanceof Player) || sender.hasPermission("draimrad.reload")) {
                    plugin.loadConfigManager();
                    Messages.sendMessageText(plugin.getMessageManager(), conversable, " &5Конфигурация перезагружна!");
                } else {
                    Messages.sendMessage(plugin.getMessageManager(), conversable, "permission.general");
                }
            } else {
                Messages.sendMessageText(plugin.getMessageManager(), conversable, " &7Не известная команда " + args[0] + "!");
            }
        } else {
            Messages.sendMessageText(plugin.getMessageManager(), conversable, "Радиация - " + plugin.getName() + " v" + plugin.getDescription().getVersion());
        }
        return true;
    }
}
