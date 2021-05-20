package me.marius.main;

import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class Utils {

    private static Main plugin;
    public Utils(Main plugin) { this.plugin = plugin; }

    private static Map<Member, Long> cooldownhash = new HashedMap<>();

    public static void addStatsCommand(long cooldown, Member member, int punkte){

        if (cooldownhash.containsKey(member)) {
            if (cooldownhash.get(member) > System.currentTimeMillis()) {
                System.out.println(member.getUser().getName() + " hat einen Befhl ausgeführt, obwohl der Cooldown für ihn noch aktiviert ist! (Keine zusätzlichen Punkte)");
            }
        } else {
            if (!plugin.getMySQL().userIsExisting(member.getId())) {
                plugin.getMySQL().createNewPlayer(member.getId(), member.getUser().getName(), punkte, 0, 0, 0);
                //Unranked ID
                member.getGuild().addRoleToMember(member.getId(), member.getJDA().getRoleById(844183463044186122L)).queue();
                cooldownhash.put(member, System.currentTimeMillis() + cooldown * 60 * 1000);
            } else {
                plugin.getMySQL().setPunkte(member.getId(), member.getUser().getName(), punkte, 0, 0);
                cooldownhash.put(member, System.currentTimeMillis() + cooldown * 60 * 1000);
                plugin.getLevelRoles().addRoles(member);
            }
        }
    }
}
