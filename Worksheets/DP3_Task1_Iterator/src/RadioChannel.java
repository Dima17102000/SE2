public class RadioChannel {

    private final ChannelType type;
    private final double frequency;

    public RadioChannel(ChannelType type, double frequency) {
        this.type = type;
        this.frequency = frequency;
    }

    public ChannelType getType() {
        return type;
    }

    public double getFrequency() {
        return frequency;
    }
}
