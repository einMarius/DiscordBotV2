package me.marius.roleselection;

import me.marius.main.Main;
import net.dv8tion.jda.api.entities.Member;
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
                || e.getChannel().getName().equalsIgnoreCase("role-selection")) {

            isOnRunning = !isOnRunning;

            if (isOnRunning) {
                new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        while (isOnRunning) {

                            try {
                                Thread.sleep(100);
                            }catch(InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                            }

                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❗")) {
                                Member m = e.getMember();
                                // NEWS
                                e.getGuild().addRoleToMember(m, e.getJDA().getRoleById(plugin.NEWS_NOTIFY)).queue();
                            }
                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❕")) {
                                Member m = e.getMember();
                                // REGISTRATION
                                e.getGuild().addRoleToMember(m, e.getJDA().getRoleById(plugin.REGISTRATION_NOTIFY)).queue();
                            }
                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {
                                Member m = e.getMember();
                                // UMFRAGE
                                e.getGuild().addRoleToMember(m, e.getJDA().getRoleById(plugin.UMFRAGE_NOTFIY)).queue();
                            }
                            isOnRunning = false;
                        }
                    }
                }.start();
            }
        }
    }

    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        if (e.getChannel().getName().equalsIgnoreCase("test")
                || e.getChannel().getName().equalsIgnoreCase("role-selection")) {

            isOffRunning = !isOffRunning;

            if (isOffRunning) {
                new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        while (isOffRunning) {

                            try {
                                Thread.sleep(100);
                            }catch(InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.out.println("[BaumbalabungaBot] Thread was interrupted, Failed to complete operation");
                            }

                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❗")) {
                                // NEWS
                                Member m = e.getMember();
                                e.getGuild().removeRoleFromMember(m, e.getJDA().getRoleById(plugin.NEWS_NOTIFY)).queue();
                            }
                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❕")) {
                                Member m = e.getMember();
                                // REGISTRATION
                                e.getGuild().removeRoleFromMember(m, e.getJDA().getRoleById(plugin.REGISTRATION_NOTIFY)).queue();
                            }
                            if (e.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {
                                Member m = e.getMember();
                                // UMFRAGE
                                e.getGuild().removeRoleFromMember(m, e.getJDA().getRoleById(plugin.UMFRAGE_NOTFIY)).queue();
                            }

                            isOffRunning = false;
                        }
                    }
                }.start();
            }
        }
    }

}
