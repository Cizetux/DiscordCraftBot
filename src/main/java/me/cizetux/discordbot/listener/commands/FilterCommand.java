package me.cizetux.discordbot.listener.commands;

import me.cizetux.discordbot.JDAThread;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class FilterCommand extends BotCmdExecutor {

    private JDAThread bot;

    public FilterCommand(JDAThread bot) {
        super("filter", "Gérer le mode filter");
        this.bot = bot;
    }

    public void execute(SlashCommandInteractionEvent event) {

        bot.setFilterEnabled(!bot.isFilterEnabled());

        String info = bot.isFilterEnabled() ? "activé" : "désactivé";
        String message = "Vous avez " + info + " le toggle mode.";
        //event.reply(new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))
               // .queue();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Toggle")
                .setDescription(message)
                .build();

        event.replyEmbeds(embed).queue();

        System.out.println("Debug command interaction: \n" +
                "channelname=" + event.getChannel().getName()
                + "\nchannelID: " + event.getChannel().getId());
    }
}
