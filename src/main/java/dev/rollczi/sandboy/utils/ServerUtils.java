package dev.rollczi.sandboy.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class ServerUtils {

    public static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static boolean isLegacyServer() {
        switch (ServerUtils.SERVER_VERSION) {
            case "v1_13_R1":
            case "v1_13_R2":
            case "v1_14_R1":
            case "v1_15_R1":
            case "v1_16_R1":
            case "v1_16_R2":
            case "v1_16_R3":
            case "v1_17_R1":
            case "v1_18_R1":
            case "v1_18_R2":
                return false;
            default:
                return true;
        }
    }

    public static boolean is1_12vOrNoLegacy() {
        if ("v1_12_R1".equals(ServerUtils.SERVER_VERSION)) {
            return true;
        }

        return !isLegacyServer();
    }

    public static boolean is1_16vOrHigher() {
        switch (ServerUtils.SERVER_VERSION) {
            case "v1_16_R1":
            case "v1_16_R2":
            case "v1_16_R3":
            case "v1_17_R1":
            case "v1_18_R1":
            case "v1_18_R2":
                return true;
            default:
                return false;
        }
    }

    private final static int LEGACY_WORLD_MIN_HEIGHT = 0;

    public static int getWorldMinHeight(World world) {
        try {
            return world.getMinHeight();
        }
        catch (NoSuchMethodError ignored) {
            return LEGACY_WORLD_MIN_HEIGHT;
        }
    }

}
