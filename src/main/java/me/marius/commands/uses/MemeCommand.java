package me.marius.commands.uses;

import me.marius.commands.types.ServerCommand;
import me.marius.main.Main;
import me.marius.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemeCommand implements ServerCommand {

    String[] colours =  new String[] {
            "ff0000", "ff6600", "fff700", "59ff00", "00ff5e", "00eeff", "003cff", "45ffc4", "459fff", "4a2bfc", "000000", "b55400", "faef52", "93fc38", "76ff00"
    };

    /*
    *
    * Bekommen der Links evtl noch async.
    *
    */

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        String args[] = message.getContentDisplay().split("");

        if(channel.getIdLong() == Main.MEMESUNDMEHR || channel.getIdLong() == Main.ALLGEMEIN_TEST){
            if(args.length >= 1){
                message.delete().queue();
                channel.sendTyping().queue();

                //MYSQL
                Utils.addStatsCommand(1, m, 3);

                //Colour
                Random rand = new Random();
                int i = rand.nextInt(colours.length);
                String colour = colours[i];

                JSONParser parser = new JSONParser();
                String postLink = "";
                String title = "";
                String url = "";
                String author = "";
                Long likes = null;

                try {
                    URL memeURL = new URL("https://meme-api.herokuapp.com/gimme");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));

                    String lines;
                    while((lines = bufferedReader.readLine()) != null) {
                        JSONArray array = new JSONArray();
                        array.add(parser.parse(lines));

                        for(Object o : array) {
                            JSONObject jsonObject = (JSONObject) o;

                            postLink = (String) jsonObject.get("postLink");
                            title = (String) jsonObject.get("title");
                            url = (String) jsonObject.get("url");
                            author = (String) jsonObject.get("author");
                            likes = (Long) jsonObject.get("ups");
                        }
                    }
                    bufferedReader.close();

                    EmbedBuilder meme = new EmbedBuilder()
                            .setTitle(title, postLink)
                            .setImage(url)
                            .setFooter("👍 "+likes+" | Author: " + author)
                            .setColor(Color.decode("0x"+colour));
                    channel.sendMessage(meme.build()).queue();

                } catch (Exception e) {
                    channel.sendMessage("**Es gab einen Fehler beim Laden des Memes!**").queue();
                    e.printStackTrace();
                }

            } else {
                message.delete().queue();
                channel.sendTyping().queue();
                channel.sendMessage("Benutze: #plsmeme!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        } else {
            message.delete().queue();
            channel.sendTyping().queue();
            channel.sendMessage("Benutze für den Command nicht diesen Channel!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}
