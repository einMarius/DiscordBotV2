package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class OdetAmoCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.SPAMALLE || channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length == 1) {
                message.delete().queue();
                channel.sendTyping().queue();

                //MySQL
                Utils.addStatsCommand(1, m, 1);

                EmbedBuilder odetamo = new EmbedBuilder()
                        .setTitle("**LATEIN**")
                        .setDescription("**Odi** et amo. Quare id faciam, fortasse requiris. \nNescio, sed fieri sentio et excrucior.")
                        .setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl())
                        .setColor(0xe3be7f);

                channel.sendMessage(odetamo.build()).queue();
                odetamo.clear();

            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #odetamo schreiben").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
