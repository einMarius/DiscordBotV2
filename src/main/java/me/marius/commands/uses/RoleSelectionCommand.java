package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RoleSelectionCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if (m.hasPermission(Permission.ADMINISTRATOR)) {
            if(channel.getIdLong() == Main.ROLESELECTION || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
                if(args.length == 1){

                    message.delete().queue();
                    channel.sendTyping().queue();

                    EmbedBuilder roleSelection = new EmbedBuilder()
                            .setTitle("**Baumbalabunga | Benachrichtigungen**")
                            .setDescription("**Reagiere mit dem jeweiligen Emoji, um bestimmte Benachrichtigungen zu erhalten.**")
                            .addField("**News-Role**", ">>> ❗`-` News", true)
                            .addField("**Registration-Role**", ">>> ❕`-` Registration", false)
                            .addField("**Umfrage-Role**", ">>> ✅ -` Umfrage", false)
                            .setThumbnail(m.getGuild().getIconUrl())
                            .setFooter("Bot created by Marius")
                            .setColor(Color.decode("0x242323"));

                    channel.sendMessage(roleSelection.build()).queue( embedMessage -> {

                        embedMessage.addReaction("❗").queue();
                        embedMessage.addReaction("❕").queue();
                        embedMessage.addReaction("✅").queue();

                    });

                    roleSelection.clear();

                } else {
                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #roleselection").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").queue();
    }
}
