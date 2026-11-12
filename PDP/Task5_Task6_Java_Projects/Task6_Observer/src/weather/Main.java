package weather;

import weather.subject.WeatherData;
import weather.observers.CurrentConditionsDisplay;
import weather.observers.StatisticsDisplay;
import weather.observers.ForecastDisplay;
import weather.external.ThirdPartyDisplay;

public class Main {

    public static void main(String[] args) {

        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statisticsDisplay);
        weatherData.registerObserver(forecastDisplay);

        System.out.println("--- First measurement ---");
        weatherData.setMeasurements(20.0, 65.0, 1015.0);

        System.out.println("\n--- Second measurement ---");
        weatherData.setMeasurements(18.0, 75.0, 1007.0);

        // Dynamically register a third-party observer
        ThirdPartyDisplay externalDisplay = new ThirdPartyDisplay();
        weatherData.registerObserver(externalDisplay);

        System.out.println("\n--- Third measurement (external observer added) ---");
        weatherData.setMeasurements(22.0, 60.0, 1010.0);

        // Remove the third-party observer
        weatherData.removeObserver(externalDisplay);

        System.out.println("\n--- Fourth measurement (external observer removed) ---");
        weatherData.setMeasurements(21.0, 62.0, 1011.0);
    }
}
