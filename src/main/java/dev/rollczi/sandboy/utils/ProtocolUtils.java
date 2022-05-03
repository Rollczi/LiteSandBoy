package dev.rollczi.sandboy.utils;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public class ProtocolUtils {

    public static void sendExp(Player player, double exp) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.EXPERIENCE);
        packet.getFloat().write(0, (float) exp < 0.0f ? 0.0f : (float) (exp > 1.0f ? 1.0f : exp));

        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Błąd wysyłania pakietów exp! ", ex);
        }

    }

    public static void resetExp(Player player) {
        player.setExp(player.getExp());
    }

    public static void sendActionBar(Player player, String message) {
        if (message.isEmpty()) {
            return;
        }

        PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);

        try {
            chat.getBytes().write(0, (byte) 2);
        } catch (Throwable throwable) {
            chat.getEnumModifier(EnumWrappers.ChatType.class, 3).write(0, EnumWrappers.ChatType.GAME_INFO);
        }

        chat.getChatComponents().write(0, WrappedChatComponent.fromText(message));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, chat);
        } catch (InvocationTargetException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error sending ActionBar to " + player.getName(), ex);
        }
    }

}
