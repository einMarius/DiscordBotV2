package me.marius.main;

import me.marius.commands.types.ServerCommand;
import me.marius.commands.uses.AeliaCommand;
import me.marius.commands.uses.ClearCommand;
import me.marius.commands.uses.StatsCommand;
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
