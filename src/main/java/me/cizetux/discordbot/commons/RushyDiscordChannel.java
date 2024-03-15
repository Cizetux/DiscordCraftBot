package me.cizetux.discordbot.commons;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class RushyDiscordChannel {

    private final JDA jda;
    private final String channelID;

    public RushyDiscordChannel(JDA jda, String channelID) {
        this.jda = jda;
        this.channelID = channelID;
    }

    public void sendEmbed(String title, String content, int color) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription(content)
                .setColor(color)
                .build();

        jda.getTextChannelById(channelID).sendMessageEmbeds(embed).queue();
    }

    public void sendEmbed(String title, String content, Color color) {

       sendEmbed(title, content, color.getRGB());
    }

    public void send(String message) {
        jda.getTextChannelById(channelID).sendMessage(message).queue();
    }
}
