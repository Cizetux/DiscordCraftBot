package me.cizetux.discordbot.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BotCmdExecutor {

    private String name;
    private String description;
    private List<String> aliases = new ArrayList<>();
    private List<OptionData> optionDataList = new ArrayList<>();

    public BotCmdExecutor(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<OptionData> getOptionDataList() {
        return optionDataList;
    }

    protected void withAliases(String...aliases){
       this.aliases.addAll(Arrays.stream(aliases).toList());
    }

    public List<String> getAliases() {
        return aliases;
    }

    protected void addOption(OptionData data){
        optionDataList.add(data);
    }

    public abstract void execute(SlashCommandInteractionEvent event);
}
