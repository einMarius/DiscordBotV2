package me.marius.main;

import me.marius.listeners.*;
import me.marius.mysql.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class Main {

    final private String token = "TOKEN";

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

    final public static long ALLGEMEIN_TEST = 811935946064265229L;

    /*
    *
    *Client Ids von BaumbalabungaV1, BaumbalabungaV2, Rythm1, Rythm2
    *
    */
    final public static long BABA_BOT_1 = 811985115306655774L;
    final public static long BABA_BOT_2 = 844166843731279912L;
    final public static long RYTHM_1 = 235088799074484224L;
    final public static long RYTHM_2 = 252128902418268161L;


    private Main instance;
    private CommandManager commandManager;
    private LevelRoles levelRoles;
    private Utils utils;

    private JDABuilder jdaBuilder;

    //InChannelPoints
    private Thread points;
    public static ArrayList<Member> invoicechannel = new ArrayList<>();

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
        jdaBuilder.addEventListeners(new JoinLeaveSwitchChannelEvent(this));
        jdaBuilder.addEventListeners(new ReactionListener(this));
        jdaBuilder.addEventListeners(new CommandListener(this));

        jdaBuilder.addEventListeners(new me.marius.roleselection.ReactionListener(this));
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

    public void runAddVoiceChannelPoints(Member m) {
        this.points = new Thread(() -> {
            long time = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() >= time + 1000 * 60) {
                    time = System.currentTimeMillis();

                    if(invoicechannel.contains(m)) {
                        if (!MySQL.userIsExisting(m.getId())) {
                            MySQL.createNewPlayer(m.getId(), m.getUser().getName(), 0, 0, 0, 1, 0);
                        } else {
                            MySQL.setPunkte(m.getId(), m.getUser().getName(), 0, 0, 0, 1, 0);
                        }
                    }
                }
            }
        });
        this.points.setName("AddVoiceChannelPoints");
        this.points.start();
    }

}
