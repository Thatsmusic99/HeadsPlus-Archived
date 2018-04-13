package io.github.thatsmusic99.headsplus.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.HPPlayer;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DebugFileCreator {

    public String createReport(Exception e, String when) throws IOException {
        JSONArray array1 = new JSONArray();
        JSONObject o1 = new JSONObject();
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (System.currentTimeMillis()));
        o1.put("Date", date);
        o1.put("Special message", getErrorHeader());
        try {
            o1.put("HeadsPlus version", HeadsPlus.getInstance().getDescription().getVersion());
            o1.put("NMS version", HeadsPlus.getInstance().nms.getClass().getSimpleName());
            o1.put("Has Vault hooked", HeadsPlus.getInstance().econ());
        } catch (NullPointerException ignored) {

        }

        o1.put("Server version", Bukkit.getVersion());
        JSONObject o3 = new JSONObject();
        try {
            o3.put("Droppable heads enabled", HeadsPlus.getInstance().drops);
            o3.put("Auto reload on first join", HeadsPlus.getInstance().arofj);
            o3.put("Uses heads selector", HeadsPlus.getInstance().db);
            o3.put("Uses leaderboards", HeadsPlus.getInstance().lb);
            o3.put("Stops placement of sellable heads", HeadsPlus.getInstance().stopP);
            o3.put("MySQL is enabled", HeadsPlus.getInstance().con);
            o3.put("Player death messages", HeadsPlus.getInstance().dm);
            o3.put("Uses challenges", HeadsPlus.getInstance().chal);
            o3.put("Total challenges", HeadsPlus.getInstance().challenges.size());
            o3.put("Total levels", HeadsPlus.getInstance().levels.size());
        } catch (NullPointerException ignored) {

        }
        o3.put("Cached players", HPPlayer.players.size());
        o1.put("Plugin values", o3);
        if (e != null) {
            JSONObject o4 = new JSONObject();
            o4.put("Message", e.getMessage());
            try {
                o4.put("Cause", e.getCause().getClass().getName());
            } catch (NullPointerException ignored) {

            }
            o4.put("Fired when", when);
            JSONArray array = new JSONArray();
            for (String str : ExceptionUtils.getStackTrace(e).split("\r\n\t")) {
                array.add(str);
            }
            o4.put("Stacktrace", array);
            o1.put("Exception details", o4);
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        array1.add(o1);
        String str = gson.toJson(array1);
        OutputStreamWriter fw = null;
        boolean cancelled = false;
        File fr = null;
        for (int i = 0; !cancelled; i++) {
            File f2 = new File(HeadsPlus.getInstance().getDataFolder() + "/debug");
            if (!f2.exists()) {
                f2.mkdir();
            }
            File f = new File(HeadsPlus.getInstance().getDataFolder() + "/debug/", date.replaceAll(":", "_").replaceAll("/", ".") + "-REPORT-" + i + ".json");
            if (!f.exists()) {
                fr = f;
                cancelled = true;
            }
        }
        fw = new OutputStreamWriter(new FileOutputStream(fr), Charsets.UTF_8);
        try {
            fw.write(str.replace("\u0026", "&"));
        } finally {
            fw.flush();
            fw.close();
        }
        return fr.getName();
    }

    private String getErrorHeader() {
        List<String> msgs = new ArrayList<>();
        msgs.add("Oh sorry, did I hurt you?");
        msgs.add("Oopsies! I'm vewwy sowwy for dis.");
        msgs.add("The plugin works well with a few exceptions, amirite?");
        msgs.add("Who put the milk in before the cereal?");
        msgs.add("Please don't put me on Santa's naughty list.");
        msgs.add("Off with yo- no, the plugin's - head!");
        msgs.add("Ohhh, what does this button do???");
        msgs.add("Please, I'm only getting started.");
        msgs.add("Our highly trained cat is working on this.");
        msgs.add("Uh oh.");
        msgs.add("help ive fallen over and i can't get up i need @someone");
        msgs.add(":sobbing:");
        msgs.add("SOMEBODY TOUCHA MY SPAGHEAD.");
        msgs.add("Yeeeaaaah... Chris, what's happening? I'm gonna need you to fix this...");
        msgs.add("Keyboard not found. Press F1 to continue.");
        msgs.add("Correct way of not doing this found. Good luck, soldier.");
        msgs.add("Sorry lad but your mouse was disconnected. Press \"OK\" to continue.");
        msgs.add("MEMORY ERROR - I forgot what I was meant to say.");
        msgs.add("DEJA VU!!! I'VE JUST BEEN TO THIS PLACE BEFOOOOOOREEE HIGHER ON THE STREET and that's not a good thing.");
        msgs.add("Your plugin has ran into a problem and has to dump an error file. Error code: AGGHHHHHH.");
        msgs.add("System Error: Windows XP isn't an OS.");
        msgs.add("[INFO]: Task failed successfully.");
        msgs.add("NullPointer for president!");
        msgs.add("Critical process probably died, we're looking into it");
        msgs.add("Still, could've been a blue screen.");
        msgs.add("Something smells - it's my code.");
        msgs.add("One does not simply HeadsPlusPlusPlusPlusPlus.");
        msgs.add("I have a dream that I'LL ACTUALLY WORK.");
        msgs.add("I need a hug.");
        msgs.add("Y tho?");
        int random = new Random().nextInt(msgs.size());
        return msgs.get(random);
    }
}
