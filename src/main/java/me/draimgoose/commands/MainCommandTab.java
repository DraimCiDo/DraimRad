package me.draimgoose.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                if (sender.hasPermission("draimrad.reload"))
                    list.add("reload");
        }
        return list;
    }
}
