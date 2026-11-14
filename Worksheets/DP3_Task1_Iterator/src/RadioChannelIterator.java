import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RadioChannelIterator implements Iterator<RadioChannel> {

    private final List<RadioChannel> channels;
    private int position = 0;

    public RadioChannelIterator(List<RadioChannel> channels) {
        this.channels = channels;
    }

    @Override
    public boolean hasNext() {
        return position < channels.size();
    }

    @Override
    public RadioChannel next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return channels.get(position++);
    }
}
