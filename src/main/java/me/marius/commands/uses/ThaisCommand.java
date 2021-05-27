package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class ThaisCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.SPAMALLE || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length == 1){
                message.delete().queue();
                channel.sendTyping().queue();

                //MySQL
                Utils.addStatsCommand(1, m, 1);

                EmbedBuilder thais = new EmbedBuilder()
                        .setTitle(" **LATEIN** ")
                        .setDescription("**Thais** habet nigros, niveos Laecania dentes. \nQuae ratio est? Emptos haec habet, illa suos.")
                        .setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl())
                        .setColor(0xe3be7f);

                channel.sendMessage(thais.build()).queue();
                thais.clear();

            } else {
                message.delete().queue();
                channel.sendTyping();
                channel.sendMessage("Für den puren Latein Genuss musst du #thais schreiben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
