package dev.rollczi.sandboy.replacer.validator;

import dev.rollczi.sandboy.utils.ServerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import panda.std.Option;
import panda.std.Result;
import panda.std.function.TriPredicate;

@FunctionalInterface
public interface ReplacerValidator extends TriPredicate<Location, Material, Integer> {

    boolean test(Location nextLocation, Material nextType, Integer round);

    ReplacerValidator WORLD_HEIGHT = (nextLocation, nextType, round) -> {
        World world = nextLocation.getWorld();

        if (world == null) {
            return false;
        }

        return nextLocation.getY() > ServerUtils.getWorldMinHeight(world) && nextLocation.getY() < world.getMaxHeight();
    };

    ReplacerValidator AIR_ONLY = (nextLocation, nextType, round) -> nextType.isAir();

    ReplacerValidator BEDROCK_STOP = (nextLocation, nextType, round) -> nextType != Material.BEDROCK;

    ReplacerValidator NONE = (nextLocation, nextType, round) -> true;

}
