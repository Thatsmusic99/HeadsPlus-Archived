package io.github.thatsmusic99.headsplus.locale;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class LocaleManager {

    private static LocaleManager instance;
    private static Locale locale;

    public void setupLocale() {
        instance = this;
        try {
            try {
                setLocale((Locale) Class.forName("io.github.thatsmusic99.headsplus.locale." + HeadsPlusConfig.getMessages().getString("locale").toLowerCase()).getClass().newInstance().newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                HeadsPlus.getInstance().log.warning("[HeadsPlus] Failed to load the locale settings!");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            HeadsPlus.getInstance().log.warning("[HeadsPlus] Failed to load the locale settings! This is caused by an invalid name provided. Setting locale to en_uk...");
            setLocale(new en_uk());
            e.printStackTrace();
        }
    }

    public static LocaleManager getInstance() {
        return instance;
    }

    public static Locale getLocale() {
        return locale;
    }
    public static void setLocale(Locale l) {
        locale = l;
    }

}
