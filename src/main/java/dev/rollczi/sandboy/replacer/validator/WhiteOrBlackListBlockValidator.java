package dev.rollczi.sandboy.replacer.validator;

import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.placeholder.Placeholders;
import dev.rollczi.sandboy.replacer.MessageContainer;
import dev.rollczi.sandboy.replacer.ReplacerContext;
import org.bukkit.Location;
import org.bukkit.Material;
import panda.utilities.text.Formatter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WhiteOrBlackListBlockValidator implements ReplacerValidator, MessageContainer {

    private final Set<Material> blockedBlocks = new HashSet<>();
    private final boolean blacklist;

    public WhiteOrBlackListBlockValidator(Collection<Material> blockedBlocks, boolean blacklist) {
        this.blockedBlocks.addAll(blockedBlocks);
        this.blacklist = blacklist;
    }

    public Set<Material> getBlockedBlocks() {
        return Collections.unmodifiableSet(blockedBlocks);
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public boolean isWhitelist() {
        return !blacklist;
    }

    @Override
    public boolean test(Location nextLocation, Material nextType, Integer round) {
        return blacklist != blockedBlocks.contains(nextType);
    }

    @Override
    public Formatter getFormatter() {
        return Placeholders.BLOCKS.formatter(this.blockedBlocks);
    }

    @Override
    public String getMessage(MessagesConfig config, ReplacerContext context) {
        if (this.isWhitelist()) {
            return context.isStaticContext() ? config.whitelistInfoStatic : config.whitelistInfo;
        }

        return context.isStaticContext() ? config.blacklistInfoStatic : config.blacklistInfo;
    }

}
