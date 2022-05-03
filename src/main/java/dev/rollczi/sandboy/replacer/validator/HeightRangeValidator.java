package dev.rollczi.sandboy.replacer.validator;

import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.placeholder.Placeholders;
import dev.rollczi.sandboy.replacer.MessageContainer;
import dev.rollczi.sandboy.replacer.ReplacerContext;
import org.bukkit.Location;
import org.bukkit.Material;
import panda.utilities.text.Formatter;

public class HeightRangeValidator implements ReplacerValidator, MessageContainer {

    private final int min;
    private final int max;

    public HeightRangeValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public boolean test(Location nextLocation, Material nextType, Integer round) {
        return min <= nextLocation.getY() && max >= nextLocation.getY();
    }

    @Override
    public Formatter getFormatter() {
        return Placeholders.HEIGHT_RANGE_VALIDATOR.formatter(this);
    }

    @Override
    public String getMessage(MessagesConfig config, ReplacerContext context) {
        return context.isStaticContext() ? config.heightRangeInfoStatic : config.heightRangeInfo;
    }

}
