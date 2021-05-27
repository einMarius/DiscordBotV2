package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class PhoebeCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.SPAMALLE || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length == 1){
                message.delete().queue();
                channel.sendTyping().queue();

                //MySQL
                Utils.addStatsCommand(1, m, 1);

                EmbedBuilder phoebe = new EmbedBuilder()
                        .setTitle(" **LATEIN** ")
                        .setDescription("**Cum** sint crura tibi similent quae cornua lunae, \nin rhytio poteras, Phoebe, lavare pedes.")
                        .addField(">>> Übersetzung für Dumme: ", "Weil du Unterschenkel hast, die den Hörnern des Mondes ähneln, konntest du Phoebus deine Füße in einem Trinkhorn waschen.", false)
                        .setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl())
                        .setColor(0xe3be7f);

                channel.sendMessage(phoebe.build()).queue();
                phoebe.clear();

            } else {
                message.delete().queue();
                channel.sendTyping();
                channel.sendMessage("Für den puren Latein Genuss musst du #phoebe schreiben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
