package com.klm.weather.service;

import org.springframework.stereotype.Service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public Weather save(Weather weather) {
    	log.info("Saving weather information");
        return repository.save(weather);
    }

    public List<Weather> findAll(String date, List<String> cities, String sort) {
    	log.info("Get all weather data");
        List<Weather> weatherList = repository.findAll();
        log.info("weather data list fetched and the size is: "+weatherList.size());

        if (date != null) {
        	log.info("Get weather data by the given date: "+date);
            weatherList = repository.findByDate(date);
        }
        
        if (cities != null && !cities.isEmpty()) {
        	log.info("Get weather data by the given city: "+cities);
            weatherList = repository.findByCities(cities);
        }

        if ("date".equals(sort)) {
        	log.info("Get weather data in sorder order");
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
        	log.info("Get weather data in reverse order");
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
    	log.info("Get weather data by the given ID: "+id);
        return repository.findById(id);
    }
}