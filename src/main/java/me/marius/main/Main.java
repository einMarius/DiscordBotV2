package me.marius.main;

import me.marius.listeners.CommandListener;
import me.marius.listeners.GuildMemberJoin;
import me.marius.listeners.GuildMemberLeave;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    final private String token = "ODQ0MTY2ODQzNzMxMjc5OTEy.YKOdow.jGjoXZ_SEy9wvotWkm3fIQWC0kk";

    private Main instance;
    private CommandManager commandManager;
    private MySQL mySQL;
    private LevelRoles levelRoles;
    private Utils utils;

    private JDABuilder jdaBuilder;

    public static void main(String[] args){

        new Main();

    }

    public Main(){

        instance = this;
        commandManager = new CommandManager();
        mySQL = new MySQL();
        levelRoles = new LevelRoles(this);
        utils = new Utils(this);


        mySQL.connect();
        mySQL.createTables();

        jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.setActivity(Activity.watching("auf den Discord herab..."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        JDA bot = null;


        //--------------Bot-Register---------------//
        jdaBuilder.addEventListeners(new GuildMemberJoin(this));
        jdaBuilder.addEventListeners(new GuildMemberLeave());
        jdaBuilder.addEventListeners(new CommandListener(this));
        //--------------Bot-Register---------------//

        try {
            bot = jdaBuilder.build();
            bot.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[BaumbalabungaBot] Der Bot, sowie alle Systeme wurden gestartet!");

    }

    public Main getInstance() { return instance; }
    public CommandManager getCommandManager() { return commandManager; }
    public MySQL getMySQL() { return mySQL; }
    public LevelRoles getLevelRoles() { return levelRoles; }
    public Utils getUtils() { return utils; }
}
