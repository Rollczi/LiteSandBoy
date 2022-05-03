package dev.rollczi.sandboy.config;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessagesConfig implements Serializable {

    @Description("# -- Help Messages --")
    public List<String> infoMessage = Collections.singletonList("&7Help - &a/sandboy help");
    public List<String> helpMessage = Arrays.asList(
            "&a--------------------",
            "&7 - &a/sandboy help",
            "&7 - &a/sandboy list",
            "&7 - &a/sandboy reload",
            "&7 - &a/sandboy give <rodzaj> <gracz> <ilość>",
            "&a--------------------"
    );

    @Description("# -- List Messages --")
    public List<String> listMessageHeader = Collections.singletonList("&a--------------------");
    public List<String> listMessageFooter = Collections.singletonList("&a--------------------");
    @Description("# {SANDBOY-TYPE}, {SANDBOY-DISPLAY-NAME}")
    public String listMessageElement = "&7 - {SANDBOY-DISPLAY-NAME} &a({SANDBOY-TYPE})";

    @Description("# -- Reload Messages --")
    public String reloadMessage = "&aReloaded configuration.";

    @Description("# -- Give Messages --")
    @Description("# {AMOUNT}, {SANDBOY-TYPE}, {SANDBOY-DISPLAY-NAME}, {PLAYER}")
    public String giveMessage = "&aOtrzymales {AMOUNT}x {SANDBOY-TYPE} od {PLAYER}!";
    public String giveMessageSender = "&aDałeś graczowi {PLAYER} {AMOUNT}x {SANDBOY-TYPE}!";
    public String giveMessageSame = "&aOtrzymales {AMOUNT}x {SANDBOY-TYPE} od samego siebie!";


    @Description("# -- Permissions Messages --")
    @Description("# {PERMISSIONS}, {LAST-PERMISSION}")
    public String noPermission = "&cNie posiadasz permisji &e{PERMISSIONS} &cdo tego polecenia!";

    @Description("# -- Other Messages --")
    public String onFoundPlayer = "&cNie znaleziono takiego gracza!";
    public String noFoundReplacer = "&cNie znaleziono takiego farmera!";
    public String noFoundNumber = "&cPodaj poprawną liczbę!";
    public String invalidUseOfCommand = "&cPoprawne Użycie &7/sandboy list|help|reload|give <rodzaj> <gracz> <ilość>";

    @Description("# -- Context Messages --")
    @Description("# {SANDBOY-TYPE}, {SANDBOY-DISPLAY-NAME}, {BLOCK-CHANGE-COUNT}, {X}, {Y}, {Z}, {BLOCK}, {INVALID-X}, {INVALID-Y}, {INVALID-Z}, {INVALID-BLOCK}")
    public String actionBarStart = "&7Postawiono {SANDBOY-DISPLAY-NAME}'a &7na {Y} kratce!";
    public String actionBarEnd = "{SANDBOY-DISPLAY-NAME}&a zakończył pracę na {Y} kratce!";

    @Description("# height range: {MIN}, {MAX}")
    public String heightRangeInfo = "{SANDBOY-DISPLAY-NAME} &7zakończył pracę na {Y} kratce (działa od {MIN} do {MAX})";
    public String heightRangeInfoStatic = "&7Farmer działa od {MIN} do {MAX}";
    @Description("# blacklist: {BLOCKS}")
    public String blacklistInfo = "{SANDBOY-DISPLAY-NAME} &7zakończył pracę, ponieważ napotkał na {INVALID-BLOCK} (y: {INVALID-Y}) (zablokowane bloki {BLOCKS})";
    public String blacklistInfoStatic = "{SANDBOY-DISPLAY-NAME}&c napotkał {INVALID-BLOCK}!";
    @Description("# whitelist: {BLOCKS}")
    public String whitelistInfo = "{SANDBOY-DISPLAY-NAME} &7zakończył pracę, ponieważ napotkał na {INVALID-BLOCK} (y: {INVALID-Y}) (dozwolone bloki {BLOCKS})";
    public String whitelistInfoStatic = "{SANDBOY-DISPLAY-NAME} &7zatrzymuje się na blokach które nie są &a{BLOCKS}";

}
