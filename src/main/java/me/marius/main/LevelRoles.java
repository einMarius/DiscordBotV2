package me.marius.main;

import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.util.Random;

public class LevelRoles {

    private Main plugin;
    public LevelRoles(Main plugin) { this.plugin = plugin; }

    public void addRoles(Member m){

        String[] colours =  new String[] {
                "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff"
        };

        //RANK 5
        if(MySQL.getPunkte(m.getId())+1 == 10000){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_4)).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_5)).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 5**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById(plugin.RANK_5).getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `No new permission.`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });

            //RANK 4
        } else if(MySQL.getPunkte(m.getId())+1 == 1000){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_3)).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_4)).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 4**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById(plugin.RANK_4).getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `No new permission.`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });

            //RANK 3
        } else if(MySQL.getPunkte(m.getId())+1 == 500){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_2)).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_3)).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 3**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById(plugin.RANK_3).getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `No new permission.`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });

            //RANK 2
        } else if(MySQL.getPunkte(m.getId())+1 == 100){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_1)).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_2)).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 2**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById(plugin.RANK_2).getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `No new permission.`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });

            //RANK 1
        } else if(MySQL.getPunkte(m.getId())+1 == 50){

            m.getGuild().removeRoleFromMember(m.getId(), m.getJDA().getRoleById(plugin.UNRANKED)).queue();
            m.getGuild().addRoleToMember(m.getId(), m.getJDA().getRoleById(plugin.RANK_1)).queue();

            m.getUser().openPrivateChannel().queue(channel -> {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("◽ **Levelup** ◽");
                builder.setDescription("**Glückwunsch du bist jetzt Level 1**");
                builder.addField(">>> Neue Rolle: ", "Rolle: `" + m.getJDA().getRoleById(plugin.RANK_1).getName() + "` ", false);
                builder.addField(">>> Neue Berechtigung", "Berechtigung: `No new permission.`", false);
                builder.setThumbnail(m.getUser().getAvatarUrl());
                builder.setFooter("Bot created by Marius", m.getGuild().getIconUrl());

                Random rand = new Random();
                int i = rand.nextInt(colours.length);

                String colour = colours[i];

                builder.setColor(Color.decode("0x" + colour));

                channel.sendMessage(builder.build()).queue();
                builder.clear();

            });
        }
    }

}
