package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Random;

public class CreateTeamsCommand implements ServerCommand {

    /*
    *
    *Not working yet.
    *
    */

    private ArrayList<String> mitspieler = new ArrayList<>();
    private Random rand = new Random();

    private ArrayList<String> team1 = new ArrayList<String>();
    private ArrayList<String> team2 = new ArrayList<String>();

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        /*
        *
        *Hatte zuvor String args[] = message.getContentDisplay().substring(12).split(" "); idk evtl das probieren
        *
        */

        String args[] = message.getContentDisplay().substring(12).split(" ");

        if(channel.getIdLong() == Main.KOMISCHELINKS || channel.getIdLong() == Main.ALLGEMEIN_TEST) {
            if(args.length == 9) {

                message.delete().queue();
                channel.sendTyping().queue();

                for(int i = 1; i < args.length; i++)
                    mitspieler.add(args[i]);

                for(int i = 1; i < mitspieler.size(); i++){
                    int INDEXn = rand.nextInt(mitspieler.size());
                    String player = mitspieler.get(INDEXn);
                    mitspieler.remove(player);
                    switch (i){
                        case 1: case 2: case 3: case 4: case 5:
                            team1.add(player);
                            break;

                        case 6: case 7: case 8: case 9: case 10:
                            team2.add(player);
                            break;

                        default:
                            break;
                    }
                }

                System.out.println(team1);
                System.out.println(team2);

                team1.clear();
                team2.clear();

            }
        }
    }
}
