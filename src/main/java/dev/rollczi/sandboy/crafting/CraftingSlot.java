package dev.rollczi.sandboy.crafting;

import panda.std.stream.PandaStream;

public enum CraftingSlot {
    
    TOP_1('1'),
    TOP_2('2'),
    TOP_3('3'),
    MIDDLE_4('4'),
    MIDDLE_5('5'),
    MIDDLE_6('6'),
    BOTTOM_7('7'),
    BOTTOM_8('8'),
    BOTTOM_9('9');

    private final static int RADIX = 10;
    private final Character key;

    CraftingSlot(char key) {
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    public static CraftingSlot getByChar(char key) {
        return PandaStream.of(values())
                .find(craftingSlot -> craftingSlot.key == key)
                .orThrow(() -> new IllegalArgumentException(key + " is not a CraftingSlot"));
    }

    public static CraftingSlot getByInteger(int key) {
        return getByChar(Character.forDigit(key, RADIX));
    }

    public static String[] getShape() {
        return new String[] { "123", "456", "789" };
    }
    
}          
           