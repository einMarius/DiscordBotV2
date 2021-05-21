package me.marius.listeners;

import me.marius.main.Main;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class JoinChannelEvent extends ListenerAdapter {

    private Main plugin;
    public JoinChannelEvent(Main plugin) { this.plugin = plugin; }

    private static HashMap<Member, Long> cooldown = new HashMap<Member, Long>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){

        if (cooldown.containsKey(e.getEntity())) {
            long secondsleft = ((cooldown.get(e.getEntity()) / 1000) + 10*60) - (System.currentTimeMillis() / 1000);
            if (secondsleft > 0) {
                System.out.println(e.getEntity().getUser().getName() + " hat einen Channel betreten, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
                MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 0, 0, 0, 1);
                return;
            } else {
                if (!MySQL.userIsExisting(e.getEntity().getId())) {
                    MySQL.createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(plugin.UNRANKED)).queue();
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                } else {
                    MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                    plugin.getLevelRoles().addRoles(e.getEntity());
                }
            }
        } else {
            if (!MySQL.userIsExisting(e.getEntity().getId())) {
                MySQL.createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(plugin.UNRANKED)).queue();
                cooldown.put(e.getEntity(), System.currentTimeMillis());
            } else {
                MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                cooldown.put(e.getEntity(), System.currentTimeMillis());
                plugin.getLevelRoles().addRoles(e.getEntity());
            }
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e){

        if (cooldown.containsKey(e.getEntity())) {
            long secondsleft = ((cooldown.get(e.getEntity()) / 1000) + 10*60) - (System.currentTimeMillis() / 1000);
            if (secondsleft > 0) {
                System.out.println(e.getEntity().getUser().getName() + " hat einen Channel gewechselt, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
                MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 0, 0, 0, 1);
                return;
            } else {
                if (!MySQL.userIsExisting(e.getEntity().getId())) {
                    MySQL.createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(plugin.UNRANKED)).queue();
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                } else {
                    MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                    cooldown.put(e.getEntity(), System.currentTimeMillis());
                    plugin.getLevelRoles().addRoles(e.getEntity());
                }
            }
        } else {
            if (!MySQL.userIsExisting(e.getEntity().getId())) {
                MySQL.createNewPlayer(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                e.getEntity().getGuild().addRoleToMember(e.getEntity().getId(), e.getEntity().getJDA().getRoleById(plugin.UNRANKED)).queue();
                cooldown.put(e.getEntity(), System.currentTimeMillis());
            } else {
                MySQL.setPunkte(e.getEntity().getId(), e.getEntity().getUser().getName(), 1, 0, 0, 1);
                cooldown.put(e.getEntity(), System.currentTimeMillis());
                plugin.getLevelRoles().addRoles(e.getEntity());
            }
        }
    }

}
