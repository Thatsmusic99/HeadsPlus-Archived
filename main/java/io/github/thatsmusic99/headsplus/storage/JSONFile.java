package io.github.thatsmusic99.headsplus.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.thatsmusic99.headsplus.HeadsPlus;
import org.apache.commons.io.Charsets;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.*;

public interface JSONFile {

    String getName();

    default void create() throws IOException {
        File f = new File(HeadsPlus.getInstance().getDataFolder() + File.separator + "storage");
        if (!f.exists()) {
            f.mkdirs();
        }
        File jsonfile = new File(f + File.separator, getName() + ".json");
        if (!jsonfile.exists()) {
            JSONObject o = new JSONObject();
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            String s = gson.toJson(o);
            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f), Charsets.UTF_8);
            try {
                fw.write(s.replace("\u0026", "&"));
            } finally {
                fw.flush();
                fw.close();
            }
        }
    }

    void writeData(Player p, Object... values);

    default void save() throws IOException {
        File f = new File(HeadsPlus.getInstance().getDataFolder() + File.separator + "storage");
        if (!f.exists()) {
            f.mkdirs();
        }
        File jsonfile = new File(f + File.separator, getName() + ".json");
        if (jsonfile.exists()) {
            PrintWriter writer = new PrintWriter(jsonfile);
            writer.print("");
            writer.close();
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String s = gson.toJson(getJSON());
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(jsonfile), Charsets.UTF_8);
        try {
            fw.write(s.replace("\u0026", "&"));
        } finally {
            fw.flush();
            fw.close();
        }
    }

    JSONObject getJSON();

    Object getData(Object key);
}
