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

public class PPCommand implements ServerCommand {

    String[] colours =  new String[] {
            "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff", "45ffc4", "459fff", "4a2bfc", "000000", "b55400", "faef52", "93fc38", "76ff00"
    };

    String[] lenght = new String[]{
            "-2mm", "5km", "Hat keinen, hat schon vergeblich danach gesucht", "2938472938745923895km", "14cm", "5mm", "234m", "-932498237498372489", "Kann man nicht messen", "24, was?"
    };

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(channel.getIdLong() == Main.SPAMALLE || channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length >= 2){
                message.delete().queue();
                channel.sendTyping().queue();

                //MySQL
                Utils.addStatsCommand(1, m, 1);

                Member targett = message.getMentionedMembers().get(0);
                if(!(targett.getIdLong() == Main.BABA_BOT_1) && !(targett.getIdLong() == Main.BABA_BOT_2) && !(targett.getIdLong() == Main.RYTHM_1) && !(targett.getIdLong() == Main.RYTHM_2)) {

                    //LÄNGE
                    Random random = new Random();
                    int i2 = random.nextInt(lenght.length);
                    String longus = lenght[i2];
                    //COLOUR
                    Random rand = new Random();
                    int i = rand.nextInt(colours.length);
                    String colour = colours[i];


                    EmbedBuilder builder = new EmbedBuilder()
                            .setTitle("◽ **PP von " + targett.getUser().getName() + "** ◽")
                            .setDescription("**Hier siehst du die Originallänge**")
                            .addField(">>> Pipilänge", "Die Länge beträgt: `" + longus + "`!", false)
                            .setThumbnail(targett.getUser().getAvatarUrl())
                            .setFooter(m.getUser().getName() + " wollte die wahre PP-Länge von " + targett.getUser().getAsMention(), m.getGuild().getIconUrl())
                            .setTimestamp(LocalDateTime.now(Clock.systemUTC()))
                            .setColor(Color.decode("0x" + colour));

                    channel.sendMessage(builder.build()).queue();
                    builder.clear();

                } else {
                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Nicht verfügbar...").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Für die wahre Schwanzlänge von jemandem musst du #pp <@Name> schreiben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
