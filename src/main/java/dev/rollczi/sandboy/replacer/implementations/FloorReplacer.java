package dev.rollczi.sandboy.replacer.implementations;

import dev.rollczi.sandboy.replacer.NextMove;
import dev.rollczi.sandboy.replacer.ReplacerContext;
import dev.rollczi.sandboy.replacer.validator.ReplacerValidator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import panda.std.reactive.Completable;

public class FloorReplacer extends StandardReplacer {

    private final Material floor;

    public FloorReplacer(Material to, Material floor, NextMove nextMove, int delay, ReplacerValidator... validators) {
        super(to, nextMove, delay, validators);
        this.floor = floor;
    }

    @Override
    public Completable<ReplacerContext> replacer(Location location) {
        Block lastBlock = location.getBlock();
        lastBlock.setType(floor);

        Location next = nextMove.apply(location);
        Block nextBlock = next.getBlock();

        for (ReplacerValidator validator : validators) {
            if (!validator.test(next, Material.AIR, 0)) {
                return Completable.completed(new ReplacerContext(lastBlock, nextBlock, 0, validator));
            }
        }

        return super.replacer(next);
    }

}
