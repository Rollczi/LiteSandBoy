package dev.rollczi.sandboy.replacer;

import org.bukkit.Location;

import java.util.function.Function;

@FunctionalInterface
public interface NextMove extends Function<Location, Location> {

    NextMove UP = location -> location.clone().add(0, 1, 0);

    NextMove DOWN = location -> location.clone().add(0, -1, 0);

    NextMove DOUBLE_UP = location -> location.clone().add(0, 2, 0);

    NextMove DOUBLE_DOWN = location -> location.clone().add(0, -2, 0);

}
