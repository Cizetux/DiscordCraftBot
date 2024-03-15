package me.cizetux.discordbot.listener.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

public class GiveawayCommand extends BotCmdExecutor {

    public GiveawayCommand() {
        super("giveaway", "Start a giveaway");
        addOption(
                new OptionData
                        (OptionType.STRING, "prize", "The prize for the giveaway").setRequired(true)
        );
        addOption(
                new OptionData
                        (OptionType.INTEGER, "duration", "Duration of the giveaway in minutes").setRequired(true)
        );

    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String prize = event.getOption("prize").getAsString();
        int durationSeconds = (int) event.getOption("duration").getAsLong() * 60;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("\uD83C\uDF89 Giveaway \uD83C\uDF89")
                .setDescription("React with \uD83C\uDF89 to enter!\nPrize: " + prize + "\nEnds in: " + getRemainingTime(durationSeconds))
                .setColor(Color.RED);

        event.getChannel().sendMessageEmbeds(embed.build()).queue(message -> {
            message.addReaction(Emoji.fromUnicode("\uD83C\uDF89")).queue();
            Timer timer = new Timer();

            final int[] remainingSeconds = {durationSeconds}; // Declare a new variable for remaining seconds

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (remainingSeconds[0] <= 0) {
                        message.editMessageEmbeds(
                                new EmbedBuilder()
                                        .setTitle("\uD83C\uDF89 Giveaway \uD83C\uDF89")
                                        .setDescription("Giveaway ended!")
                                        .setColor(Color.RED)
                                        .build()
                        ).queue();
                        // Choose a winner here and announce it
                        // You can use message.retrieveReactionUsers(...) to get participants
                        // and randomly select a winner

                        message.retrieveReactionUsers(Emoji.fromUnicode("\uD83C\uDF89")).queue(users -> {
                            List<Member> participants = new ArrayList<>();
                            for (User user : users) {
                                if (!user.isBot()) {
                                    Member member = event.getGuild().getMemberById(user.getId());
                                    if (member != null) {
                                        participants.add(member);
                                    }
                                }
                            }

                            if (!participants.isEmpty()) {
                                int winnerIndex = new Random().nextInt(participants.size());
                                if (winnerIndex < participants.size()) {
                                    Member winner = participants.get(winnerIndex);
                                    // Announce the winner
                                    event.getChannel().sendMessage("Congratulations to " + winner.getAsMention() + " for winning the giveaway of " + prize + "!").queue();
                                } else {
                                    event.getChannel().sendMessage("Failed to select a winner. Winner index out of bounds.").queue();
                                }
                            } else {
                                event.getChannel().sendMessage("No valid participants found. No winner selected.").queue();
                            }
                        });

                        cancel(); // Stop the timer task
                    } else {
                        message.editMessageEmbeds(
                                new EmbedBuilder()
                                        .setTitle("\uD83C\uDF89 Giveaway \uD83C\uDF89")
                                        .setDescription("React with \uD83C\uDF89 to enter!\nPrize: " + prize + "\nEnds in: " + getRemainingTime(remainingSeconds[0]))
                                        .setColor(Color.GREEN)
                                        .build()
                        ).queue();
                        remainingSeconds[0]--;
                    }
                }
            };

            timer.scheduleAtFixedRate(timerTask, 0, 1000); // Schedule task to run every 1000ms (1 second)
        });

        event.reply("Giveaway started for: " + prize).setEphemeral(true).queue(response -> {
            // Schedule a task to delete the reply message after 5 seconds
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    response.deleteOriginal().queue();
                }
            }, 5000); // 5000 milliseconds (5 seconds)
        });
    }

    private String getRemainingTime(int seconds) {
        int days = seconds / 86400;
        int hours = (seconds % 86400) / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        StringBuilder timeString = new StringBuilder();

        if (days > 0) {
            timeString.append(days).append("d ");
        }
        if (hours > 0) {
            timeString.append(hours).append("h ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append("min ");
        }
        if (remainingSeconds > 0) {
            timeString.append(remainingSeconds).append("s");
        }

        return timeString.toString();
    }
}
