package me.cizetux.discordbot.config;

import java.util.*;

public record DiscordConfig(
        String botToken,
        Map<String, String> channels,
        List<CommandConfig> commands
) {

    /*
    public static DiscordConfig parse(FileConfiguration configuration) {

        String botToken = configuration.getString("bot-token");
        ConfigurationSection channelsSection = configuration.getConfigurationSection("channels");

        Set<String> keys = channelsSection.getKeys(false);
        Map<String, String> channels = new HashMap<>();
        for (String channelName : keys) {
            String channelId = channelsSection.getString(channelName);
            channels.put(channelName, channelId);
        }

        List<CommandConfig> commands = new ArrayList<>();
        ConfigurationSection cmdSection = configuration.getConfigurationSection("commands");
        Set<String> commandNames = cmdSection.getKeys(false);
        for (String commandName : commandNames){
            ConfigurationSection section = cmdSection.getConfigurationSection(commandName);
            String description = section.getString("description");
            commands.add(new CommandConfig(commandName, description));
        }

        return new DiscordConfig(botToken, channels, commands);
    }*/
}
