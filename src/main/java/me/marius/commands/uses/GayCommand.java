package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GayCommand implements ServerCommand {

    String[] colours =  new String[] { "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff", "45ffc4", "459fff", "4a2bfc", "000000", "b55400", "faef52", "93fc38", "76ff00" };
    String[] percentage = new String[] { "0%", "23%", "63456%", "-123%", "54%", "89%", "56%", "25%", "19%", "π%", "52389%", "null", "0,000000000000000001", "24%", "78%", "99%", "100%", "-3%", "77%" };

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.SPAMALLE || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length >= 2){

                message.delete().queue();
                channel.sendTyping().queue();

                Member targett = message.getMentionedMembers().get(0);

                if(!(targett.getIdLong() == Main.BABA_BOT_1) && !(targett.getIdLong() == Main.BABA_BOT_2) && !(targett.getIdLong() == Main.RYTHM_1) && !(targett.getIdLong() == Main.RYTHM_2)) {

                    //MYSQL
                    Utils.addStatsCommand(1, m, 1);

                    //Länge
                    Random rand = new Random();
                    int i = rand.nextInt(percentage.length);
                    String prozentzahl = percentage[i];
                    //Color
                    Random rand2 = new Random();
                    int i2 = rand2.nextInt(colours.length);
                    String colour = colours[i2];

                    EmbedBuilder gay = new EmbedBuilder()
                            .setTitle("◽ **Wie Gay ist " + targett.getUser().getName() + "?** ◽")
                            .setDescription("**Hier siehst du die Prozentzahl (Wurde überprüft)**")
                            .addField(">>> Gay-Prozentzahl", targett.getUser().getName() + " ist zu: `" + prozentzahl + "` Gay!", false)
                            .setThumbnail(targett.getUser().getAvatarUrl())
                            .setFooter(m.getUser().getName() + " wollte die wahre Gay-Pronzentzahl von " + targett.getUser().getName(), m.getGuild().getIconUrl())
                            .setTimestamp(LocalDateTime.now(Clock.systemUTC()))
                            .setColor(Color.decode("0x"+colour));

                    channel.sendMessage(gay.build()).queue(embedMessage -> {
                        embedMessage.addReaction("✅").queue();
                        embedMessage.addReaction("❌").queue();
                    });
                    gay.clear();

                }else {
                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Nicht verfügbar...").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Um herauszufinden, wie Gay jemand ist, musst du #gay <@Name> schreiben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        }else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
