package me.marius.main;

import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashMap;

public class Utils {

    private static Main plugin;
    public Utils(Main plugin) { this.plugin = plugin; }

    private static HashMap<Member, Long> cooldowntime = new HashMap<Member, Long>();

    public static void addStatsCommand(long cooldown, Member member, int punkte){

        if (cooldowntime.containsKey(member)) {
            long secondsleft = ((cooldowntime.get(member) / 1000) + cooldown*60) - (System.currentTimeMillis() / 1000);
            if (secondsleft > 0) {
                System.out.println(member.getUser().getName() + " hat einen Befehl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
                System.out.println(secondsleft);
                return;
            } else {
                if (!MySQL.userIsExisting(member.getId())) {
                    MySQL.createNewPlayer(member.getId(), member.getUser().getName(), punkte, 0, 0, 0, 0);
                    member.getGuild().addRoleToMember(member.getId(), member.getJDA().getRoleById(plugin.UNRANKED)).queue();
                    cooldowntime.put(member, System.currentTimeMillis());
                } else {
                    MySQL.setPunkte(member.getId(), member.getUser().getName(), punkte, 0, 0, 0, 0);
                    cooldowntime.put(member, System.currentTimeMillis());
                    plugin.getLevelRoles().addRoles(member);
                }
            }
        } else {
            if (!MySQL.userIsExisting(member.getId())) {
                MySQL.createNewPlayer(member.getId(), member.getUser().getName(), punkte, 0, 0, 0, 0);
                member.getGuild().addRoleToMember(member.getId(), member.getJDA().getRoleById(plugin.UNRANKED)).queue();
                cooldowntime.put(member, System.currentTimeMillis());
            } else {
                MySQL.setPunkte(member.getId(), member.getUser().getName(), punkte, 0, 0, 0, 0);
                cooldowntime.put(member, System.currentTimeMillis());
                plugin.getLevelRoles().addRoles(member);
            }
        }
    }
}
