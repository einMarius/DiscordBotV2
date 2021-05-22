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

public class AddRolesCommand implements ServerCommand {

    private boolean isRunningAddRole;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split(" ");

        if(m.hasPermission(Permission.ADMINISTRATOR)){
            if(channel.getIdLong() == Main.ROLESELECTION){
                if(args.length == 1) {

                    message.delete().queue();
                    channel.sendTyping().queue();

                    isRunningAddRole = !isRunningAddRole;

                    new Thread(() -> {
                        while (isRunningAddRole) {

                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            EmbedBuilder roleselection = new EmbedBuilder()
                                    .setTitle("⚠ **Role-Selection** ⚠")
                                    .setDescription("**Reagiere mit dem jeweiligen Emoji, um bestimmte \nBenachrichtigungen zu erhalten. **")
                                    .addField(">>> News-Role", "`Reagiere mit ❗ um in der Zukunft News-Benachrichtigungen zu erhalten` ", false)
                                    .addField(">>> Registration-Role", "`Reagiere mit ❕ um in der Zukunft Registration-Benachrichtigungen zu erhalten`", false)
                                    .addField(">>> Umfrage-Role", "`Reagiere mit ✅ um in der Zukunft Umfrage-Benachrichtigungen zu erhalten`", false)
                                    .setThumbnail(m.getGuild().getIconUrl())
                                    .setFooter("Bot created by Marius", m.getGuild().getIconUrl())
                                    .setColor(Color.GREEN);

                            channel.sendMessage(roleselection.build()).queue(embedMessage -> {

                                embedMessage.addReaction("❗").queue();
                                embedMessage.addReaction("❕").queue();
                                embedMessage.addReaction("✅").queue();

                            });
                            roleselection.clear();

                            isRunningAddRole = false;
                        }
                    }).start();
                } else {
                    message.delete().queue();
                    channel.sendTyping().queue();
                    channel.sendMessage("Benutze: #addroles").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
