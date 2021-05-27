package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class NewsCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            if(channel.getIdLong() == Main.NEWS || channel.getIdLong() == Main.ALLGEMEIN_TEST){
                if(args.length >= 2) {
                    message.delete().queue();
                    channel.sendTyping().queue();

                    String news = "";
                    for(int i = 1; i < args.length; i++)
                        news = String.valueOf(news) + " " + args[i];

                    EmbedBuilder info = new EmbedBuilder()
                            .setTitle("🎉 **Neuigkeiten** 🎉")
                            .setAuthor(m.getUser().getName())
                            .setThumbnail(m.getGuild().getIconUrl())
                            .addField("Erwähnung", "<@&"+Main.NEWS_NOTIFY+">", false)
                            .setDescription(news)
                            .setFooter(m.getUser().getName() + " hat eine Neuigkeit gepostet!" , m.getUser().getAvatarUrl())
                            .setTimestamp(LocalDateTime.now(Clock.systemUTC()))
                            .setColor(Color.GREEN);

                    channel.sendMessage("||<@&"+Main.NEWS_NOTIFY+">|| \n").embed(info.build()).queue();
                    info.clear();

                } else {
                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #news <Neuigkeit>!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
