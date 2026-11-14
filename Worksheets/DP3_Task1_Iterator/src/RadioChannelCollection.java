import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadioChannelCollection implements Iterable<RadioChannel> {

    private final List<RadioChannel> channels = new ArrayList<>();

    public void addChannel(RadioChannel channel) {
        channels.add(channel);
    }

    @Override
    public Iterator<RadioChannel> iterator() {
        return new RadioChannelIterator(channels);
    }
}
