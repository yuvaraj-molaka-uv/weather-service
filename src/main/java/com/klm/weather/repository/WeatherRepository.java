package com.klm.weather.repository;

import com.klm.weather.model.Weather;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class WeatherRepository {
	private final Map<Integer, Weather> weatherData = new ConcurrentHashMap<>();
	private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Weather save(Weather weather) {
        weather.setId(idGenerator.getAndIncrement());
        weatherData.put(weather.getId(), weather);
        log.info("weather information saved");
        return weather;
    }

    public List<Weather> findAll() {
        return new ArrayList<>(weatherData.values());
    }

    public Optional<Weather> findById(int id) {
        return Optional.ofNullable(weatherData.get(id));
    }

    public List<Weather> findByDate(String date) {
        return weatherData.values().stream()
                .filter(w -> w.getDate().toString().equals(date))
                .sorted(Comparator.comparingInt(Weather::getId))
                .collect(Collectors.toList());
    }

    public List<Weather> findByCities(List<String> cities) {
        return weatherData.values().stream()
                .filter(w -> cities.contains(w.getCity().toLowerCase()))
                .sorted(Comparator.comparingInt(Weather::getId))
                .collect(Collectors.toList());
    }

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
