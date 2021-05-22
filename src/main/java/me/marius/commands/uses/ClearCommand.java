package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ServerCommand {

    int clearamount;
    private boolean isRunningClearCommand;

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {

            String args[] = message.getContentDisplay().split(" ");

            if (args.length == 2) {

                try {
                    clearamount = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    channel.sendMessage("Du musst eine Zahl angeben!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    return;
                }

                    isRunningClearCommand = !isRunningClearCommand;

                    new Thread(() -> {
                        while (isRunningClearCommand) {

                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                            }

                            OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);

                            List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(args[1]) + 1).complete();
                            messages.removeIf(message1 -> message1.getTimeCreated().isBefore(twoWeeksAgo));

                            channel.deleteMessages(messages).complete();

                            isRunningClearCommand = false;

                        }
                    }).start();

                    channel.sendTyping().queue();

                    new Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {

                            EmbedBuilder info = new EmbedBuilder();
                            info.setTitle("**Information**");
                            info.setDescription(clearamount + " Nachrichten gelöscht.");
                            info.setFooter("Nachrichten von " + m.getUser().getName() + " gelöscht.", m.getUser().getAvatarUrl());
                            info.setColor(Color.RED);

                            channel.sendMessage(info.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                            info.clear();
                        }
                    }, 500);

            } else {
                message.delete().queue();
                channel.sendMessage("Benutze: #clear AMOUNT").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else
            channel.sendMessage("Dazu hast du keine Berechtigung!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }
}
