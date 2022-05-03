package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.replacer.validator.ReplacerValidator;
import org.bukkit.block.Block;

public class ReplacerContext {

    private final Block lastBlock;
    private final Block invalidBlock;
    private final int blockChangeCount;
    private final ReplacerValidator validator;
    private final boolean staticContext;

    public ReplacerContext(Block lastBlock, Block invalidBlock, int blockChangeCount, ReplacerValidator validator) {
        this.lastBlock = lastBlock;
        this.invalidBlock = invalidBlock;
        this.blockChangeCount = blockChangeCount;
        this.validator = validator;
        this.staticContext = false;
    }

    private ReplacerContext(Block lastBlock, Block invalidBlock, int blockChangeCount, ReplacerValidator validator, boolean staticContext) {
        this.lastBlock = lastBlock;
        this.invalidBlock = invalidBlock;
        this.blockChangeCount = blockChangeCount;
        this.validator = validator;
        this.staticContext = staticContext;
    }

    public Block getLastBlock() {
        return lastBlock;
    }

    public Block getInvalidBlock() {
        return invalidBlock;
    }

    public int getBlockChangeCount() {
        return blockChangeCount;
    }

    public ReplacerValidator getValidator() {
        return validator;
    }

    public boolean isStaticContext() {
        return staticContext;
    }

    public static ReplacerContext staticContext(Block block, ReplacerValidator validator) {
        return new ReplacerContext(block, block, 0, validator, true);
    }

}
