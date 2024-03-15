package me.cizetux.discordbot;

import me.cizetux.discordbot.commons.RushyDiscordChannel;
import me.cizetux.discordbot.config.DiscordConfig;
import me.cizetux.discordbot.listener.CommandListener;
import me.cizetux.discordbot.listener.FilterListener;
import me.cizetux.discordbot.listener.PingListener;
import me.cizetux.discordbot.listener.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread that represents the JDA connection. Separated of the Bukkit main thread.
 */
public class JDAThread extends Thread {

    private final DiscordConfig config;

    public final Map<String, RushyDiscordChannel> channels = new HashMap<>();

    private boolean filterEnabled = true;

    public JDAThread(DiscordConfig config) {
        this.config = config;
    }


    @Override
    public void run() {

        try {
            JDA jda = JDABuilder.createDefault(config.botToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build();
            jda.awaitReady();


            registerJdaChannels(jda);

            // Commands
            CommandListener commandListener = new CommandListener();
            setupBotCommands(jda, commandListener,
                    new FilterCommand(this),
                    new TestCommand(),
                    new RpsCommand(),
                    new SuggestionCommand(),
                    new TimezoneCommand(),
                    new GiveawayCommand()
            );

            // Event Registration
            jda.addEventListener(commandListener,
                    new FilterListener(this),
                    new PingListener()
            );

            // Status + Activity
            // jda.getPresence().setStatus(OnlineStatus.IDLE);
            jda.getPresence().setActivity(Activity.streaming("Checkout", "https://www.twitch.tv/Lol?"));
                  //  .setPresence(Activity.of(Activity.ActivityType.PLAYING, "Beep... "), false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupBotCommands(JDA jda, CommandListener commandListener, BotCmdExecutor... commands) {
        CommandListUpdateAction updateAction = jda.updateCommands();
        for (BotCmdExecutor command : commands) {

            SlashCommandData commandData = Commands.slash(
                    command.getName(),
                    command.getDescription()
            );
            commandData.addOptions(command.getOptionDataList());
            updateAction.addCommands(commandData);

            // Aliases commands
            for (String aliases : command.getAliases()) {
                SlashCommandData cmdAliase = Commands.slash(aliases, command.getDescription());
                cmdAliase.addOptions(command.getOptionDataList());
                updateAction.addCommands(cmdAliase);

            }

            commandListener.registerCommandExecutor(command);
        }

        updateAction.queue();
    }

    private void registerJdaChannels(JDA jda) {
        for (String channelName : config.channels().keySet()) {
            String token = config.channels().get(channelName);
            channels.put(channelName, new RushyDiscordChannel(jda, token));
        }
    }

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public void setFilterEnabled(boolean enabled) {
        this.filterEnabled = enabled;
    }
}
