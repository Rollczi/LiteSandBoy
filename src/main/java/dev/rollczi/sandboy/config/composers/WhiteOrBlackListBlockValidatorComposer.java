package dev.rollczi.sandboy.config.composers;

import dev.rollczi.sandboy.replacer.validator.WhiteOrBlackListBlockValidator;
import org.bukkit.Material;
import panda.std.Result;
import panda.utilities.text.Joiner;

import java.util.Collections;
import java.util.HashSet;

public class WhiteOrBlackListBlockValidatorComposer implements LiteComposer<WhiteOrBlackListBlockValidator> {

    @Override
    public Result<WhiteOrBlackListBlockValidator, Exception> deserialize(String text) {
        if (text.equalsIgnoreCase("none")) {
            return Result.ok(new WhiteOrBlackListBlockValidator(Collections.emptyList(), true));
        }

        String[] data = text.split(" ");

        if (data.length != 3 || !data[1].equals("->")) {
            return Result.error(new UnsupportedOperationException("Cannot deserialize " + text));
        }

        String typeList = data[0];
        boolean blacklist = typeList.equals("blacklist");

        if (!blacklist && !typeList.equals("whitelist")) {
            return Result.error(new UnsupportedOperationException("Cannot deserialize type of list:" + typeList));
        }

        HashSet<Material> blocks = new HashSet<>();

        for (String blockType : data[2].split(",")) {
            Material material = Material.getMaterial(blockType);

            if (material == null) {
                return Result.error(new UnsupportedOperationException("Cannot deserialize \"" + blockType + "\" to block type"));
            }

            blocks.add(material);
        }

        return Result.ok(new WhiteOrBlackListBlockValidator(blocks, blacklist));
    }

    @Override
    public Result<String, Exception> serialize(WhiteOrBlackListBlockValidator validator) {
        String type = validator.isBlacklist() ? "blacklist" : "whitelist";
        Joiner blocks = Joiner.on(",").join(validator.getBlockedBlocks(), Enum::name);

        return Result.ok(type +  " -> " + blocks);
    }

}