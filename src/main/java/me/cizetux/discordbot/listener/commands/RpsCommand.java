package me.cizetux.discordbot.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Random;

public class RpsCommand extends BotCmdExecutor {

    public RpsCommand() {
        super("rps", "Rock Paper Scissors game !!!");

        addOption(
                new OptionData(
                        OptionType.STRING,
                        "choice",
                        "Gives your decision!"
                ).addChoices(
                        new Command.Choice("rock", "rock"),
                        new Command.Choice("paper", "paper"),
                        new Command.Choice("scissors", "scissors")
                )
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        if (event.getUser().isBot()) return;

        if (event.getName().equals("rps")) {

            OptionMapping userChoice = event.getOptions().get(0);
            String userChoiceString = userChoice.getAsString().toLowerCase();

            // Il faut un enum et surtout forcer un parametre dans la commande discord
            if (!userChoiceString.equals("rock") && !userChoiceString.equals("paper") && !userChoiceString.equals("scissors")) {
                event.reply("Please provide a valid choice: rock, paper, or scissors").queue();
                return;
            } // C'est bien de mettre ça ?

            String botChoice = getBotChoice();
            String result = getResult(userChoiceString, botChoice);
            event.reply("Bot chooses " + botChoice + ". " + result).queue();

        }
    }

    private String getBotChoice() {
        Random random = new Random();
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return "rock";
            case 1:
                return "paper";
            default:
                return "scissors";
        }

        /*
         String[] choices = {"rock", "paper", "scissors"};
    return choices[new Random().nextInt(choices.length)];

    C'est meilleur ça ?
         */
    }

    private String getResult(String userChoice, String botChoice) {
        if (userChoice.equals(botChoice))
            return "It's a tie!";
        else if ((userChoice.equals("rock") && botChoice.equals("scissors")) ||
                (userChoice.equals("paper") && botChoice.equals("rock")) ||
                (userChoice.equals("scissors") && botChoice.equals("paper")))
            return "You win!";
        else
            return "You lose!";
    }

}
