import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        RadioChannelCollection collection = new RadioChannelCollection();

        collection.addChannel(
                new RadioChannel(ChannelType.ENGLISH, 101.5));

        collection.addChannel(
                new RadioChannel(ChannelType.GERMAN, 98.3));

        collection.addChannel(
                new RadioChannel(ChannelType.FRENCH, 105.7));

        Iterator<RadioChannel> iterator = collection.iterator();

        while (iterator.hasNext()) {
            RadioChannel channel = iterator.next();

            System.out.println(
                    "Type: " + channel.getType() +
                            ", Frequency: " + channel.getFrequency() + " MHz");
        }
    }
}
