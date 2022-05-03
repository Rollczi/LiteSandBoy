package dev.rollczi.sandboy.chat;

import net.md_5.bungee.api.ChatColor;
import panda.std.stream.PandaStream;

import java.util.List;

public class ChatUtils {

    private ChatUtils() {}

    public static String color(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public static List<String> color(Iterable<String> content) {
        return PandaStream.of(content).map(ChatUtils::color).toList();
    }

    public static List<String> color(String... content) {
        return PandaStream.of(content).map(ChatUtils::color).toList();
    }

}
