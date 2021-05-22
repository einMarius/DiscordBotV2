package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class AeliaCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.SPAMALLE){
            if(args.length == 1){
                message.delete().queue();
                channel.sendTyping();

                //MYSQL
                Utils.addStatsCommand(3, m, 1);

                EmbedBuilder aelia = new EmbedBuilder()
                        .setTitle(" **LATEIN** ")
                        .setDescription("**Si** memini, fuerant tibi quattuor, Aelia, dentes: \nExpulit una duos tussis et una duos. \nIam secura potes totis tussire diebus: \nNil istic quod agat tertia tussis habet.")
                        .addField(">>> Übersetzung für Dumme: ", "Wenn ich mich erinnere hattest du 4 Zähne gehabt Aelia: Ein Husten hat zwei herausgeschleudert und noch einer zwei. Schon kannst du die ganzen Tage lang sorglos husten: Ein dritter Husten hat nichts was er dort treiben könnte.", false)
                        .setFooter(m.getUser().getName() + " wollte den puren Latein-Genuss", m.getUser().getAvatarUrl())
                        .setColor(0xe3be7f);

                channel.sendMessage(aelia.build()).queue();
                aelia.clear();

            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Für den puren Latein-Genuss musst du #Aelia schreiben").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
