package io.github.thatsmusic99.headsplus.locale;

public interface Locale {

    boolean active(); // Will be set myself.

    String getLanguage(); // Returns the language (English, Deutsch, etc)

    String getReloadingMessage(); // Get the config reloading message.
    // English: "Reloading config..."

    String getReloadMessage(); // Get the reloaded config message.
    // This is different to reloading as it's saying the reload was successful.
    // English: "Config has reloaded!"

    String getReloadFailMessage(); // Get the failed config message.
    // This is used when the configuration fails to load.
    // English: "Config failed to reload!"

    String getHeadInteractMessage(); // Returns a message when a player interacts with a head.
    // English: "That is <Player>'s head!"

    String getHeadMhfInteractMessage(); // Returns a message when a player interacts with a head (object).
    // English: "That is a <Object>'s head!"

    String getHeadMhfInteractMessage2(); // Returns a message when a player interacts with a head (object).
    // NOTE: This comes up because of how English works. If needed, it can be the same as the above message.
    // English: "That is an <Object>'s head!"

    String getSellSuccess(); // Returns a message when a player successfully sells their head.
    // English: "You successfully sold the head(s) for %l and now have %b!"
    // Parameters: &l - Total price head(s) were sold for.
    // %b - The total balance of a player.

    String getNotEnoughHeads(); // Returns a message if a player tries to sell more heads than they have.
    // English: "You don't have enough heads!"

    String getNoHeads(); // Returns a message if a player doesn't have any actual heads that can be sold in their inventory.
    // English: "You don't have any valid heads in your inventory!"

    String getInvalidArguments(); //

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

    String getHeadAddedWhitelist();

    String getHeadNotOnWhitelist();

    String getHeadRemovedWhitelist();

    String getWorldAlreadyAdded();

    String getWorldAddedBlacklist();

    String getWorldNotOnBlacklist();

    String getWorldRemovedBlacklist();

    String getWorldAddedWhitelist();

    String getWorldNotOnWhitelist();

    String getWorldRemovedWhitelist();

    String getBlacklistOn();

    String getBlacklistAlreadyOn();

    String getBlacklistOff();

    String getBlacklistAlreadyOff();

    String getWBlacklistOn();

    String getWBlacklistAlreadyOn();

    String getWBlacklistOff();

    String getWBlacklistAlreadyOff();

    String getWhitelistOn();

    String getWhitelistAlreadyOn();

    String getWhitelistOff();

    String getWhitelistAlreadyOff();

    String getWWhitelistOn();

    String getWWhitelistAlreadyOn();

    String getWWhitelistOff();

    String getWWhitelistAlreadyOff();

    String getBlacklistFail();

    String getWhitelistFail();

    String getWBlacklistFail();

    String getWWhitelistFail();

    String getDisabledCommand();

    String getEmptyBlacklist();

    String getEmptyWBlacklist();

    String getEmptyWhitelist();

    String getEmptyWWhitelist();

    String getBuySuccess();

    String getBuyFail();

    String versionWord();

    String author();

    String language();

    String descBlacklistAdd();

    String descBlacklistDelete();

    String descBlacklistList();

    String descBlacklistToggle();

    String descBlacklistwAdd();

    String descBlacklistwDelete();

    String descBlacklistwList();

    String descBlacklistwToggle();

    String descInfo();

    String descMCReload();

    String descWhitelistAdd();

    String descWhitelistDelete();

    String descWhitelistList();

    String descWhitelistToggle();

    String descWhitelistwAdd();

    String descWhitelistwDelete();

    String descWhitelistwList();

    String descWhitelistwToggle();

    String descHead();

    String descSellhead();

    String descHeads();

    String descMyHead();

    String getUpdateFound();

    String getCurrentVersion();

    String getNewVersion();

    String getDescription();

}