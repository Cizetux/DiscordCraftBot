package me.cizetux.discordbot.command;
/*
import me.cizetux.discordbot.DiscordBotPlugin;
import me.cizetux.discordbot.commons.RushyDiscordChannel;
import me.cizetux.discordbot.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DiscordCommand implements CommandExecutor {

    private DiscordBotPlugin plugin;

    public DiscordCommand(DiscordBotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("discord")) {
            return false;
        }

        if (args.length > 0) {
            String arg1 = args[0];

            if (arg1.equalsIgnoreCase("send")) {

                if (args.length < 4){
                    sender.sendMessage("§C/discord send <channel> <title> <color> <content>");
                    return false;
                }

                String channelName = args[1];
                RushyDiscordChannel discordChannel = plugin.getChannel(channelName);
                String title = args[2];
                StringBuilder content = new StringBuilder();

                String colorName = args[3];
                Color color = Colors.getColor(colorName);

                for (int i = 4; i < args.length; i++){
                    content.append(args[i]);
                    content.append(' ');
                }

                discordChannel.sendEmbed(
                        title,
                        content.toString(),
                        color
                );

            }
        }
        return false;
    }
}
*/