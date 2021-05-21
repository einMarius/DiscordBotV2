package me.marius.listeners;

import me.marius.main.Main;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class ReactionListener extends ListenerAdapter {

    private Main plugin;
    public ReactionListener(Main plugin) { this.plugin = plugin; }

    private static HashMap<Member, Long> cooldown = new HashMap<Member, Long>();

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {

        if (!(e.getMember().getUser().getIdLong() == 844166843731279912L)) {
            if(!e.getChannel().getName().equals("role-selection") || e.getChannel().getName().equalsIgnoreCase("umfragen") ||
            e.getChannel().getName().equalsIgnoreCase("news")){

                Member member = e.getMember();

                if(cooldown.containsKey(member)){
                    //3*60 für 3min Cooldown
                    long secondsleft = ((cooldown.get(e.getMember()) / 1000) + 3*60) - (System.currentTimeMillis() / 1000);
                    if(secondsleft > 0){
                        System.out.println(e.getMember().getUser().getName() + " hat eine Reaktion hinzugefügt, obwohl der Cooldown für ihn noch aktiviert ist");
                        MySQL.setPunkte(member.getId(), member.getUser().getName(), 0, 0, 1, 0);
                    } else {
                        if (!MySQL.userIsExisting(e.getMember().getId())) {
                            MySQL.createNewPlayer(e.getMember().getId(), e.getMember().getUser().getName(), 1, 0, 1, 0);
                            e.getMember().getGuild().addRoleToMember(e.getMember().getId(), e.getMember().getJDA().getRoleById(plugin.UNRANKED)).queue();
                            cooldown.put(e.getMember(), System.currentTimeMillis());
                        } else {
                            MySQL.setPunkte(e.getMember().getId(), e.getMember().getUser().getName(), 1, 0, 1, 0);
                            cooldown.put(e.getMember(), System.currentTimeMillis());
                            plugin.getLevelRoles().addRoles(e.getMember());
                        }
                    }
                } else {

                    if(!MySQL.userIsExisting(member.getId())){
                        MySQL.createNewPlayer(member.getId(), member.getUser().getName(), 1, 0, 1, 0);
                        e.getMember().getGuild().addRoleToMember(e.getMember().getId(), e.getMember().getJDA().getRoleById(plugin.UNRANKED)).queue();
                        cooldown.put(member, System.currentTimeMillis());
                    } else {
                        MySQL.setPunkte(e.getMember().getUser().getId(), e.getMember().getUser().getName(), 1, 0, 1, 0);
                        cooldown.put(e.getMember(), System.currentTimeMillis());
                        plugin.getLevelRoles().addRoles(member);
                    }
                }
            }
        }
    }
}
