package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class TopCommand implements ServerCommand {

    private ShardManager shardManager;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.STATS || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length == 1){
                message.delete().queue();
                channel.sendTyping().queue();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("⚠ **Die Top 10** ⚠");
                builder.setDescription("**Das ist die aktuelle Top 10**");
                for(int i = 0; i < MySQL.getRanking().size(); i++){
                    int place = i + 1;
                    builder.addField(">>> Platz " + place, MySQL.getRanking().get(i) + " mit " + MySQL.getPunkteForTop().get(i) + " Punkten", false);
                }
                builder.setThumbnail(m.getGuild().getIconUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());
                builder.setColor(Color.GREEN);

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            } else {
                message.delete().queue();
                channel.sendTyping();
                channel.sendMessage("Benutze: #top!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
