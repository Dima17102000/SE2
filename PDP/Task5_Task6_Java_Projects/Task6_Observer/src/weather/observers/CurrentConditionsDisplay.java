package weather.observers;

public class CurrentConditionsDisplay implements WeatherObserver {

    @Override
    public void update(double temperature, double humidity, double pressure) {
        System.out.printf("Current weather: %.1f C, %.1f%% humidity, %.1f hPa%n",
                          temperature, humidity, pressure);
    }
}
