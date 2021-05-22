package me.marius.main;

import me.marius.listeners.*;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    final private String token = "ODQ0MTY2ODQzNzMxMjc5OTEy.YKOdow.PmL6zcspbRLlLdW79pogYe6Teso";

    /*
    *
    * Rollen Ids für den Baumbalabunga Discord
    *
    * */
    final public long UNRANKED = 824983261197500440L;
    final public long RANK_1 = 824983198039015444L;
    final public long RANK_2 = 824983050458759198L;
    final public long RANK_3 = 824983002970325002L;
    final public long RANK_4 = 824982992170516500L;
    final public long RANK_5 = 824982938717782046L;

    final public long NEWS_NOTIFY = 816243134090444812L;
    final public long REGISTRATION_NOTIFY = 816256434634358795L;
    final public long UMFRAGE_NOTFIY = 816385581738885182L;

    /*
    *
    * Channel Ids für den Baumbalabunga Discord
    *
    * */
    final public static long MOINTSCHÖ = 812007747087368202L;
    final public static long ROLESELECTION = 816270150859882496L;
    final public static long UMFRAGE = 811948730142949436L;
    final public static long NEWS = 735904029606936598L;
    final public static long KOMISCHELINKS = 525729848413519890L;
    final public static long MEMESUNDMEHR = 673615226955890710L;
    final public static long LSMODS = 736308928438206464L;
    final public static long NORMALEGESPRÄCHE = 811906973229645835L;
    final public static long MUSIK = 723621796083138631L;
    final public static long STATS = 825103970270707743L;
    final public static long SPAMALLE = 823902444714328064L;
    final public static long SCHULCHAT = 640912830870192140L;
    final public static long ZITATE = 799597621224275988L;
    final public static long ABIIDEEN = 819166858497622026L;

    private Main instance;
    private CommandManager commandManager;
    private LevelRoles levelRoles;
    private Utils utils;

    private JDABuilder jdaBuilder;

    public static void main(String[] args){

        new Main();

    }

    public Main(){

        instance = this;
        commandManager = new CommandManager();
        levelRoles = new LevelRoles(this);
        utils = new Utils(this);


        MySQL.connect();
        MySQL.createTables();

        jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.setActivity(Activity.watching("github.com/einmarius..."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        JDA bot = null;


        //--------------Bot-Register---------------//
        //jdaBuilder.addEventListeners(new GuildMemberJoin(this));
        //jdaBuilder.addEventListeners(new GuildMemberLeave());
        jdaBuilder.addEventListeners(new JoinChannelEvent(this));
        jdaBuilder.addEventListeners(new ReactionListener(this));
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
    public LevelRoles getLevelRoles() { return levelRoles; }
    public Utils getUtils() { return utils; }
}
