package io.github.thatsmusic99.headsplus.locale;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

import java.lang.reflect.InvocationTargetException;

public class LocaleManager {

    private static LocaleManager instance;
    private static Locale locale;

    public void setupLocale() {
        instance = this;
        try {
            try {
                try {
                    setLocale((Locale) Class.forName("io.github.thatsmusic99.headsplus.locale." + HeadsPlusConfig.getMessages().getString("locale").toLowerCase()).getConstructor().newInstance());
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (!locale.active()) {
                    HeadsPlus.getInstance().log.info("[HeadsPlus] Language requested is being developed. Setting to English whilst it is.");
                    setLocale(new en_uk());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                HeadsPlus.getInstance().log.warning("[HeadsPlus] Failed to load the locale settings!");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            HeadsPlus.getInstance().log.warning("[HeadsPlus] Failed to load the locale settings! This is caused by an invalid name provided. Setting locale to en_uk...");
            setLocale(new en_uk());
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
