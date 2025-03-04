package com.klm.weather.service;

import org.springframework.stereotype.Service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public Weather save(Weather weather) {
        return repository.save(weather);
    }

    public List<Weather> findAll(String date, List<String> cities, String sort) {
        List<Weather> weatherList = repository.findAll();

        if (date != null) {
            weatherList = repository.findByDate(date);
        }
        
        if (cities != null && !cities.isEmpty()) {
            weatherList = repository.findByCities(cities);
        }

        if ("date".equals(sort)) {
            Collections.sort(weatherList, new Comparator<Weather>() {
                @Override
                public int compare(Weather w1, Weather w2) {
                    int dateComparison = w1.getDate().compareTo(w2.getDate());
                    if (dateComparison == 0) {
                        return Integer.compare(w1.getId(), w2.getId());
                    }
                    return dateComparison;
                }
            });
        } else if ("-date".equals(sort)) {
            Collections.sort(weatherList, new Comparator<Weather>() {
                @Override
                public int compare(Weather w1, Weather w2) {
                    int dateComparison = w2.getDate().compareTo(w1.getDate()); // Reverse order
                    if (dateComparison == 0) {
                        return Integer.compare(w1.getId(), w2.getId());
                    }
                    return dateComparison;
                }
            });
        }

        return weatherList;
    }

    public Optional<Weather> findById(int id) {
        return repository.findById(id);
    }
}