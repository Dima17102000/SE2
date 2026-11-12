package weather.observers;

public class ForecastDisplay implements WeatherObserver {

    private Double previousPressure;
    private Double previousTemperature;

    @Override
    public void update(double temperature, double humidity, double pressure) {

        if (previousPressure == null) {
            System.out.println("Forecast: Not enough data yet.");
        } else if (pressure < previousPressure) {
            System.out.println("Forecast: Cooler, rainier weather expected.");
        } else if (temperature > previousTemperature) {
            System.out.println("Forecast: Better weather expected.");
        } else {
            System.out.println("Forecast: No clear improvement indicated.");
        }

        previousPressure = pressure;
        previousTemperature = temperature;
    }
}
