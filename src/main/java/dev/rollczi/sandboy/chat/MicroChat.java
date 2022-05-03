package dev.rollczi.sandboy.chat;

import dev.rollczi.sandboy.utils.ProtocolUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.stream.PandaStream;
import panda.utilities.text.Formatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MicroChat {

    private final Set<Formatter> formatters = new HashSet<>();
    private final Set<CommandSender> senders = new HashSet<>();

    public MicroChat(CommandSender senders, Formatter... formatters) {
        this.senders.add(senders);
        this.formatters.addAll(Arrays.asList(formatters));
    }

    public MicroChat(Collection<CommandSender> senders, Formatter... formatters) {
        this.senders.addAll(senders);
        this.formatters.addAll(Arrays.asList(formatters));
    }

    public void registerFormatters(Formatter... formatters) {
        this.formatters.addAll(Arrays.asList(formatters));
    }

    private void formatAndConsumeIfMessageIsNotEmpty(String message, Collection<Formatter> formatters, Consumer<String> consumer) {
        if (message.isEmpty()) {
            return;
        }

        Set<Formatter> formatterSet = PandaStream.of(formatters)
                .concat(this.formatters)
                .toSet();

        for (Formatter formatter : formatterSet) {
            message = formatter.format(message);
        }

        consumer.accept(ChatUtils.color(message));
    }

    public void send(String message, Formatter... formatters) {
        formatAndConsumeIfMessageIsNotEmpty(message, Arrays.asList(formatters), formatted -> senders
                .forEach(sender -> sender.sendMessage(formatted)));
    }

    public void actionBar(String message, Formatter... formatters) {
        formatAndConsumeIfMessageIsNotEmpty(message, Arrays.asList(formatters), formatted -> PandaStream
                .of(senders)
                .is(Player.class)
                .forEach(player -> ProtocolUtils.sendActionBar(player, formatted)));
    }
}
