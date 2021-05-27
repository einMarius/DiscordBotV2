package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class UmfrageCommand implements ServerCommand {

    private String thumbsup = "✅";
    private String thumbsdown = "❌";

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            if (channel.getIdLong() == Main.UMFRAGE || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
                if (args.length >= 2) {
                    message.delete().queue();
                    channel.sendTyping().queue();

                    String umfrage = "";
                    for (int i = 1; i < args.length; i++)
                        umfrage = String.valueOf(umfrage) + " " + args[i];

                    EmbedBuilder info = new EmbedBuilder()
                            .setTitle("🎆 **Umfrage** 🎆")
                            .setAuthor(m.getNickname())
                            .setThumbnail(m.getGuild().getIconUrl())
                            .setDescription(umfrage)
                            .setFooter(m.getUser().getName() + " hat eine Umfrage gestartet", m.getUser().getAvatarUrl())
                            .setTimestamp(LocalDateTime.now(Clock.systemUTC()))
                            .setColor(Color.CYAN);

                    channel.sendMessage("||<@&"+Main.UMFRAGE_NOTFIY+">|| \n").embed(info.build()).queue(embedMessage -> {

                                embedMessage.addReaction(thumbsup).queue();
                                embedMessage.addReaction(thumbsdown).queue();

                            });
                    info.clear();

                } else {
                    message.delete().queue();
                    channel.sendTyping();
                    channel.sendMessage("Benutze: #umfrage <Umfrage>!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                channel.sendTyping();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
