package dev.rollczi.sandboy.replacer.implementations;

import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.replacer.NextMove;
import dev.rollczi.sandboy.replacer.Replacer;
import dev.rollczi.sandboy.replacer.ReplacerContext;
import dev.rollczi.sandboy.replacer.validator.ReplacerValidator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import panda.std.Result;
import panda.std.reactive.Completable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StandardReplacer implements Replacer {

    protected final Material to;
    protected final NextMove nextMove;
    protected final int delay;
    protected final Set<ReplacerValidator> validators = new HashSet<>();

    public StandardReplacer(Material to, NextMove nextMove, int delay, ReplacerValidator... validators) {
        this.to = to;
        this.nextMove = nextMove;
        this.delay = delay;
        this.validators.addAll(Arrays.asList(validators));
        this.validators.add(ReplacerValidator.WORLD_HEIGHT);
    }

    @Override
    public Completable<ReplacerContext> replacer(Location location) {
        return this.nextReplace(location, 1, Completable.create());
    }

    @Override
    public Result<Boolean, ReplacerContext> validFirstReplace(Location location) {
        Block block = location.getBlock();
        Location next = nextMove.apply(location);
        Block nextBlock = next.getBlock();

        for (ReplacerValidator validator : validators) {
            if (!validator.test(location, Material.AIR, 0)) {
                return Result.error(ReplacerContext.staticContext(block, validator));
            }

            if (!validator.test(next, nextBlock.getType(), 1)) {
                return Result.error(ReplacerContext.staticContext(nextBlock, validator));
            }
        }

        return Result.ok(true);
    }

    @Override
    public void addValidator(ReplacerValidator validator) {
        validators.add(validator);
    }

    @Override
    public void removeValidator(ReplacerValidator validator) {
        validators.remove(validator);
    }

    private Completable<ReplacerContext> nextReplace(Location location, int round, Completable<ReplacerContext> completable) {
        Block lastBlock = location.getBlock();
        lastBlock.setType(to);

        Location next = nextMove.apply(location);
        Block nextBlock = next.getBlock();

        for (ReplacerValidator validator : validators) {
            if (!validator.test(next, nextBlock.getType(), round)) {
                return completable.complete(new ReplacerContext(lastBlock, nextBlock, round, validator));
            }
        }

        Bukkit.getScheduler().runTaskLater(LiteSandBoy.getInstance(), () -> nextReplace(next, round + 1, completable), 10L);
        return completable;
    }

}
