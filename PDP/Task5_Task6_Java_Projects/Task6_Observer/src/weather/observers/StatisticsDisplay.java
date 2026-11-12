package weather.observers;

public class StatisticsDisplay implements WeatherObserver {

    private int count;

    private final double[] sums = new double[3];

    private final double[] minimums = {
        Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY
    };

    private final double[] maximums = {
        Double.NEGATIVE_INFINITY,
        Double.NEGATIVE_INFINITY,
        Double.NEGATIVE_INFINITY
    };

    @Override
    public void update(double temperature, double humidity, double pressure) {

        double[] values = {temperature, humidity, pressure};
        String[] names = {"Temperature", "Humidity", "Pressure"};

        count++;

        for (int i = 0; i < values.length; i++) {
            sums[i] += values[i];
            minimums[i] = Math.min(minimums[i], values[i]);
            maximums[i] = Math.max(maximums[i], values[i]);

            System.out.printf("%s: avg=%.1f, min=%.1f, max=%.1f%n",
                              names[i], sums[i] / count,
                              minimums[i], maximums[i]);
        }
    }
}
