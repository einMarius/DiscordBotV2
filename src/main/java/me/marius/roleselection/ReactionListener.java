package me.marius.roleselection;

import me.marius.main.Main;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    private Main plugin;
    public ReactionListener(Main plugin) { this.plugin = plugin; }

    private boolean isOnRunning;
    private boolean isOffRunning;

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getChannel().getName().equalsIgnoreCase("test")
                || e.getChannel().getIdLong() == Main.ROLESELECTION) {

            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❗")) {
                // NEWS
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(plugin.NEWS_NOTIFY)).queue();
            }
            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❕")) {
                // REGISTRATION
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(plugin.REGISTRATION_NOTIFY)).queue();
            }
            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {
                // UMFRAGE
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(plugin.UMFRAGE_NOTFIY)).queue();
            }
        }
    }

    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        if (e.getChannel().getName().equalsIgnoreCase("test")
                || e.getChannel().getIdLong() == Main.ROLESELECTION) {

            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❗")) {
                // NEWS
                e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(plugin.NEWS_NOTIFY)).queue();
            }
            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❕")) {
                // REGISTRATION
                e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(plugin.REGISTRATION_NOTIFY)).queue();
            }
            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {
                // UMFRAGE
                e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(plugin.UMFRAGE_NOTFIY)).queue();
            }
        }
    }
}
