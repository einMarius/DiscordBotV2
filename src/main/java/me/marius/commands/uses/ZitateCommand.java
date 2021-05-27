package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ZitateCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (channel.getIdLong() == Main.ZITATE || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if (args.length > 2) {
                message.delete().queue();
                channel.sendTyping().queue();

                //MYSQL
                Utils.addStatsCommand(3, m, 3);

                String lehrer = args[1];
                String zitat = "";
                for (int i = 2; i < args.length; i++)
                    zitat = String.valueOf(zitat) + " " + args[i];

                EmbedBuilder info = new EmbedBuilder()
                        .setTitle("Zitat")
                        .setDescription(lehrer + " sagte: " + zitat + "")
                        .setFooter("Zitat erstellt von " + m.getUser().getName(), m.getUser().getAvatarUrl())
                        .setColor(Color.RED);

                channel.sendMessage(info.build()).queue();
                info.clear();

            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Benutze: #zitate <Person> <Zitat>!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
