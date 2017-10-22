package io.github.thatsmusic99.headsplus;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

class UpdateChecker {

    private final static String versionURL = "https://api.spiget.org/v2/resources/40265/versions?size=1000";
    private final static String descriptionURL = "https://api.spiget.org/v2/resources/40265/updates?size=1000";

    static Object[] getUpdate() {
        JSONArray versionsArray = null;
        try {
            versionsArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(versionURL))));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        Double lastVersion;
        try {
            lastVersion = Double.parseDouble(((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString());
        } catch (NumberFormatException e) {
            String[] s = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString().split("\\.");
            StringBuilder v = new StringBuilder();
            for (int i = 1; i < s.length; i++) {
                v.append(s[i]);
            }
            lastVersion = Double.valueOf(s[0] + "." + v.toString());
        }
        Double currentVersion;
        try {
            currentVersion = Double.parseDouble(HeadsPlus.getInstance().getDescription().getVersion());
        } catch (NumberFormatException e) {
            String[] s = HeadsPlus.getInstance().getDescription().getVersion().split("\\.");
            StringBuilder v = new StringBuilder();
            for (int i = 1; i < s.length; i++) {
                v.append(s[i]);
            }
            currentVersion = Double.valueOf(s[0] + "." + v.toString());
        }
        String latestVersionString = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString();

        HeadsPlus.getInstance().log.info(lastVersion.toString());
        HeadsPlus.getInstance().log.info(currentVersion.toString());
        HeadsPlus.getInstance().log.info(HeadsPlus.getInstance().getDescription().getVersion());

        if (lastVersion > currentVersion) {
            JSONArray updatesArray = null;
            try {
                updatesArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(descriptionURL))));
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
            String updateName = ((JSONObject) updatesArray.get(updatesArray.size() - 1)).get("title").toString();
            return new Object[]{lastVersion, updateName, latestVersionString};
        }
        return null;
    }
}
