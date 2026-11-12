package weather.subject;

import java.util.ArrayList;
import java.util.List;
import weather.observers.WeatherObserver;

public class WeatherData implements WeatherSubject {

    private final List<WeatherObserver> observers = new ArrayList<>();

    private double temperature;
    private double humidity;
    private double pressure;

    @Override
    public void registerObserver(WeatherObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : new ArrayList<>(observers)) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(double temperature, double humidity,
                                double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        notifyObservers();
    }
}
