package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.config.ItemSection;
import dev.rollczi.sandboy.replacer.NextMove;
import dev.rollczi.sandboy.replacer.validator.HeightRangeValidator;
import dev.rollczi.sandboy.replacer.validator.WhiteOrBlackListBlockValidator;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

@Contextual
public class ReplacerConfiguration implements Serializable {

    public String name = "none";
    public String displayName = "none";
    public Material block = Material.STONE;
    public Material floorBlock = Material.STONE;
    @Description("# Co ile ticków zamieniać bloki?")
    public int delay = 10;
    @Description("# Dostępne rodzaje przesunięć: UP, DOWN, DOUBLE_UP, DOUBLE_DOWN")
    public NextMove nextMove = NextMove.DOWN;
    @Description("# Przedział od do ilu może działać replacer 'min -> max'")
    public HeightRangeValidator heightRangeValidator = new HeightRangeValidator(0, 255);
    @Description("# Blacklist lub Whitelist bloków 'whitelist -> BLOCK1' 'blacklist -> BLOCK1,BLOCK2,BLOCK3'")
    @Description("# Jeśli nie chcesz używać ustaw na 'none'")
    public WhiteOrBlackListBlockValidator blocksListValidator = new WhiteOrBlackListBlockValidator(Collections.singletonList(Material.AIR), false);

    public ItemSection item = new ItemSection();

    public ReplacerConfiguration() {
    }

    public ReplacerConfiguration(String name, String displayName, Material block, Material floorBlock, NextMove nextMove, HeightRangeValidator heightRangeValidator,
                                 WhiteOrBlackListBlockValidator blocksListValidator, ItemSection item) {
        this.name = name;
        this.displayName = displayName;
        this.block = block;
        this.floorBlock = floorBlock;
        this.nextMove = nextMove;
        this.heightRangeValidator = heightRangeValidator;
        this.blocksListValidator = blocksListValidator;
        this.item = item;
    }


}
