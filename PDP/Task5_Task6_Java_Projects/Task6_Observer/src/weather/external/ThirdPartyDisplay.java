package weather.external;

import weather.observers.WeatherObserver;

public class ThirdPartyDisplay implements WeatherObserver {

    @Override
    public void update(double temperature, double humidity, double pressure) {
        System.out.println("Third-party display: " + temperature + " C");
    }
}
