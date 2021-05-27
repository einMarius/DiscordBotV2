package me.marius.main;

import me.marius.commands.types.ServerCommand;
import me.marius.commands.uses.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager(){

        this.commands = new ConcurrentHashMap<>();

        this.commands.put("clear", new ClearCommand());
        this.commands.put("aelia", new AeliaCommand());
        this.commands.put("stats", new StatsCommand());
        this.commands.put("createteams", new CreateTeamsCommand());
        this.commands.put("gay", new GayCommand());
        this.commands.put("role-selection", new RoleSelectionCommand());
        this.commands.put("hesterno", new HesternoCommand());
        this.commands.put("plsmeme", new MemeCommand());
        this.commands.put("news", new NewsCommand());
        this.commands.put("odetamo", new OdetAmoCommand());
        this.commands.put("pp", new PPCommand());
        this.commands.put("phoebe", new PhoebeCommand());
        this.commands.put("sexte", new SexteCommand());
        this.commands.put("thais", new ThaisCommand());
        this.commands.put("tongilianus", new TongilianusCommand());
        this.commands.put("top", new TopCommand());
        this.commands.put("umfrage", new UmfrageCommand());
        this.commands.put("zitat", new ZitateCommand());

    }

    public boolean perform(String command, Member m, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {

            cmd.performCommand(m, channel, message);
            return true;
        }
        return false;
    }
}
