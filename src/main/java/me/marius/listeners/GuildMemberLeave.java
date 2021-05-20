package me.marius.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Random;

public class GuildMemberLeave extends ListenerAdapter {

    private String emoji = "❔";
    private String emoji1 = "❓";
    String[] messages = {

            "Ohhhhh! %member% hat den Discord-Server verlassen.",
            "Oh nein! Wieso ist denn %member% gegangen?",
            "Auf Wiedersehen, %member%!",
            "Wir haben jemanden verloren, %member%!",
            "Woooooooosh. %member% ist weggeflogen!",
            "Ein wildes %member% ist verschwunden!",
            "%member% wurde von Wanningers Adlerblick erwischt!",
            "%member% wurde von Vannis Lostheit erwischt!.",
            "%member% wurde ge-Jommet!"

    };

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {

        Guild guild = e.getGuild();
        User user = e.getUser();
        JDA client = e.getJDA();

        Random rand = new Random();
        int number = rand.nextInt(messages.length);

        EmbedBuilder builder = new EmbedBuilder()
                .setDescription(emoji + " " + messages[number].replace("%member%", e.getMember().getAsMention()) + " " + emoji1)
                .setColor(0xe3672d);

        List<TextChannel> channels = guild.getTextChannelsByName("moin-tschööö", true);
        for(TextChannel channel : channels) {
            channel.sendMessage(builder.build()).queue();
        }

        System.out.println("Leave");



        e.getGuild().getDefaultChannel().sendMessage(builder.build()).queue();
        builder.clear();
    }

}
