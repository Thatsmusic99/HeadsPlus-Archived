package io.github.thatsmusic99.headsplus.locale;

import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;

public class LocaleManager {

    private static LocaleManager instance;
    private static En_UK en_uk;

    public void setupLocale() {
        instance = this;
        try {
            En_UK.instance = En_UK.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        en_uk = En_UK.getInstance();
    }

    public static LocaleManager getInstance() {
        return instance;
    }

    public static String getLocale() {
        return HeadsPlusConfig.getMessages().getString("locale");
    }
}
