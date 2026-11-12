package weather.subject;

import weather.observers.WeatherObserver;

public interface WeatherSubject {

    void registerObserver(WeatherObserver observer);

    void removeObserver(WeatherObserver observer);

    void notifyObservers();
}
