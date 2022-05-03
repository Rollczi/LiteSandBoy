package dev.rollczi.sandboy.config.composers;

import org.bukkit.enchantments.Enchantment;
import panda.std.Option;
import panda.std.Result;

public class EnchantmentComposer implements LiteComposer<Enchantment> {

    @Override
    public Result<Enchantment, Exception> deserialize(String name) {
        return Option.of(Enchantment.getByName(name))
                .toResult(new UnsupportedOperationException("Cant deserialize Enchantment: " + name));
    }

    @Override
    public Result<String, Exception> serialize(Enchantment enchantment) {
        return Result.ok(enchantment.getName());
    }

}
