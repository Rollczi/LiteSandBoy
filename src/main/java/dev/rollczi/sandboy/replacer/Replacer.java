package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.replacer.validator.ReplacerValidator;
import org.bukkit.Location;
import panda.std.Result;
import panda.std.reactive.Completable;

public interface Replacer {

    Completable<ReplacerContext> replacer(Location location);

    Result<Boolean, ReplacerContext> validFirstReplace(Location location);

    void addValidator(ReplacerValidator validator);

    void removeValidator(ReplacerValidator validator);

}
