/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.sandboy.utils;

import dev.rollczi.sandboy.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack itemStack;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = new ItemStack(itemStack);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (short) data);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(new ItemStack(itemStack));
    }

    public static ItemBuilder of(ItemBuilder itemBuilder) {
        return new ItemBuilder(itemBuilder.build());
    }

    public static ItemBuilder none() {
        return new ItemBuilder(Material.STONE).setName("&7None");
    }

    public ItemBuilder setType(Material type) {
        this.itemStack.setType(type);
        return this;
    }

//    public ItemBuilder setData(int data) {
//        return setData((byte) data);
//    }
//
//    public ItemBuilder setData(byte data) {
//        MaterialData materialData = itemStack.getData();
//
//        if (materialData == null) {
//
//            itemStack.setData(new MaterialData(itemStack.getType(), data));
//            System.out.println("data -: " + itemStack.getData());
//            return this;
//        }
//
//        materialData.setData(data);
//        itemStack.setData(materialData);
//        System.out.println("data +: " + itemStack.getData());
//        return this;
//    }

    public ItemBuilder setName(String name) {
        return onMeta(meta -> meta.setDisplayName(ChatUtils.color(name)));
    }

    public ItemBuilder setLore(List<String> lore) {
        return onMeta(meta -> meta.setLore(ChatUtils.color(lore)));
    }

    public ItemBuilder setLore(String... lore) {
        return onMeta(meta -> meta.setLore(ChatUtils.color(lore)));
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::addEnchantment);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        return onMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level) {
        return onMeta(meta -> {
            this.removeEnchantments();
            meta.addEnchant(enchantment, level, true);
        });
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        return onMeta(meta -> meta.removeEnchant(enchantment));
    }

    public ItemBuilder removeEnchantments() {
        itemStack.getEnchantments().keySet().forEach(this::removeEnchantment);
        return this;
    }

    public ItemBuilder setHideAttributes(boolean hide) {
        return setItemFlag(ItemFlag.HIDE_ATTRIBUTES, hide);
    }

    public ItemBuilder setItemFlag(ItemFlag itemFlag, boolean hide) {
        return onMeta(meta -> {
            if (hide) {
                meta.addItemFlags(itemFlag);
                return;
            }

            meta.removeItemFlags(itemFlag);
        });
    }

    public ItemBuilder setItemFlags(Iterable<ItemFlag> itemFlags) {
        itemFlags.forEach(itemFlag -> setItemFlag(itemFlag, true));
        return this;
    }

    public ItemStack build() {
        return new ItemStack(this.itemStack);
    }

    public ItemBuilder onMeta(Consumer<ItemMeta> metaConsumer) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (meta == null) {
            return this;
        }

        metaConsumer.accept(meta);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public boolean isSimilar(ItemStack item) {
        return this.itemStack.isSimilar(item);
    }

    public boolean isSimilar(ItemBuilder item) {
        return this.itemStack.isSimilar(item.itemStack);
    }

}
