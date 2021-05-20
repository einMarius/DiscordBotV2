package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class StatsCommand implements ServerCommand {


    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == 825103970270707743L || channel.getIdLong() == 825331970166751252L){

        }

    }
}
