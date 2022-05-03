package dev.rollczi.sandboy.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtils {

    public static void giveOrDrop(Player player, ItemBuilder item) {
        giveOrDrop(player, item.build());
    }

    public static void giveOrDrop(Player player, ItemStack itemStack) {
        PlayerInventory inventory = player.getInventory();

        if (hasInventoryFreeSlot(inventory, itemStack)) {
            inventory.addItem(itemStack);
            return;
        }

        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }

    public static boolean hasInventoryFreeSlot(Inventory inv, ItemStack itemStack) {
        if (inv.firstEmpty() != -1) {
            return true;
        }

        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return false;
            }

            if (item.isSimilar(itemStack) && item.getMaxStackSize() > item.getAmount()) {
                return true;
            }
        }

        return false;
    }

}
