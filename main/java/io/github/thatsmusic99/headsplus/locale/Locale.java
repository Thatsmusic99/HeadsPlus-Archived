package io.github.thatsmusic99.headsplus.locale;

public interface Locale {

    String getLanguage(); // Returns the language. This will automatically be set.

    String getReloadingMessage(); // Get the config reloading message.
    // English: "Reloading config..."

    String getReloadMessage(); // Get the reloaded config message.
    // This is different to reloading as it's saying the reload was successful.
    // English: "Config has reloaded!"

    String getReloadFailMessage(); // Get the failed config message.
    // This is used when the configuration fails to load.
    // English: "Config failed to reload."

    String getHeadInteractMessage(); // Returns a message when a player interacts with a head.
    // English: "That is <Player>'s head!"

    String getHeadMhfInteractMessage();
    String getHeadMhfInteractMessage2();
    String getSellSuccess();
    String getNotEnoughHeads();
    String getNoHeads();
    String getInvalidArguments();
    String getSellFail();
    String getFalseHead();
    String getFalseItem();
    String getBlacklistHead();
    String getWhitelistHead();
    String getFullInventory();
    String getAlphaNames();
    String getTooManyArguments();
    String getHeadTooLong();
    String getHeadTooShort();
    String getInvalidPageNumber();
    String getInvalidInputInteger();
    String getNoPermissions();
    String getHeadAlreadyAdded();
    String getHeadAddedBlacklist();
    String getHeadNotOnBlacklist();
    String getHeadRemovedBlacklist();

}
