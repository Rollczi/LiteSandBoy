package dev.rollczi.sandboy.placeholder;

import dev.rollczi.sandboy.replacer.ReplacerContext;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.replacer.validator.HeightRangeValidator;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import panda.std.Triple;
import panda.utilities.StringUtils;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Placeholders<T> {

    public static final Placeholders<Triple<ReplacerData, CommandSender, Integer>> GIVE;
    public static final Placeholders<ReplacerData> REPLACER;
    public static final Placeholders<List<String>> PERMISSIONS;
    public static final Placeholders<HeightRangeValidator> HEIGHT_RANGE_VALIDATOR;
    public static final Placeholders<Collection<Material>> BLOCKS;
    public static final Placeholders<ReplacerContext> CONTEXT;

    static {
        REPLACER = new Placeholders<ReplacerData>()
                .property("SANDBOY-TYPE", ReplacerData::getName)
                .property("SANDBOY-DISPLAY-NAME", ReplacerData::getDisplayName);

        GIVE = new Placeholders<Triple<ReplacerData, CommandSender, Integer>>()
                .property("SANDBOY-TYPE", context -> context.getFirst().getName())
                .property("PLAYER", context -> context.getSecond().getName())
                .property("AMOUNT", Triple::getThird);

        PERMISSIONS = new Placeholders<List<String>>()
                .property("PERMISSIONS", list -> Joiner.on(", ").join(list))
                .property("LAST-PERMISSION", list -> list.isEmpty() ? StringUtils.EMPTY : list.get(0));

        HEIGHT_RANGE_VALIDATOR = new Placeholders<HeightRangeValidator>()
                .property("MIN", HeightRangeValidator::getMin)
                .property("MAX", HeightRangeValidator::getMax);

        BLOCKS = new Placeholders<Collection<Material>>()
                .property("BLOCKS", types -> Joiner.on(", ").join(types, type -> StringUtils.capitalize(type.name().toLowerCase())));

        CONTEXT = new Placeholders<ReplacerContext>()
                .property("X", context -> context.getLastBlock().getLocation().getBlockX())
                .property("Y", context -> context.getLastBlock().getLocation().getBlockY())
                .property("Z", context -> context.getLastBlock().getLocation().getBlockZ())
                .property("BLOCK", context -> StringUtils.capitalize(context.getLastBlock().getType().name().toLowerCase()))
                .property("INVALID-X", context -> context.getInvalidBlock().getLocation().getBlockX())
                .property("INVALID-Y", context -> context.getInvalidBlock().getLocation().getBlockY())
                .property("INVALID-Z", context -> context.getInvalidBlock().getLocation().getBlockZ())
                .property("INVALID-BLOCK", context -> StringUtils.capitalize(context.getInvalidBlock().getType().name().toLowerCase()))
                .property("BLOCK-CHANGE-COUNT", ReplacerContext::getBlockChangeCount);

    }

    private final Map<String, Function<T, String>> placeholders = new HashMap<>();

    public Placeholders() {
    }

    public Placeholders<T> raw(String key, Function<T, Object> bind) {
        return Placeholders.of(placeholders, key, t -> String.valueOf(bind.apply(t)));
    }

    public Placeholders<T> raw(String key, Supplier<Object> bind) {
        return Placeholders.of(placeholders, key, t -> String.valueOf(bind.get()));
    }

    public Placeholders<T> property(String key, Function<T, Object> bind) {
        return raw("{" + key + "}", bind);
    }

    public Placeholders<T> property(String key, Supplier<Object> bind) {
        return raw("{" + key + "}", bind);
    }

    public Formatter formatter(T data) {
        Formatter formatter = new Formatter();

        placeholders.forEach((key, bind) -> formatter.register(key, bind.apply(data)));

        return formatter;
    }

    private static <T> Placeholders<T> of(Map<String, Function<T, String>> placeholdersMap, String newKey, Function<T, String> newBind) {
        Placeholders<T> placeholders = new Placeholders<>();

        placeholders.placeholders.putAll(placeholdersMap);
        placeholders.placeholders.put(newKey, newBind);

        return placeholders;
    }

}