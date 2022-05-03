package dev.rollczi.sandboy.config.composers;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;

public interface LiteComposer<T> extends Composer<T>, SimpleSerializer<T>, SimpleDeserializer<T> {

}
