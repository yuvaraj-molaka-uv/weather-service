package com.klm.weather.service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    private WeatherJPARepository weatherRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Weather saveWeather(Weather weather) {
        Weather savedWeather = weatherRepository.save(weather);
        log.info("Weather information saved: {}", savedWeather);
        return savedWeather;
    }

    @Override
    public List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort) throws ParseException {
        log.info("Get all weather data");
        List<Weather> weatherList = weatherRepository.findAll();
        log.info("weather data list fetched and the size is: {}", weatherList.size());

        if (date != null) {
            log.info("Get weather data by the given date: "+date);
            Date parsedDate = dateFormat.parse(date);
            weatherList = weatherRepository.findByDate(parsedDate);
        }

        if (cities != null && !cities.isEmpty()) {
            log.info("Get weather data by the given city: "+cities);
            weatherList = weatherRepository.findByCityIgnoreCaseIn(cities);
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

    @Override
    public Optional<Weather> getWeatherById(int id) {
        log.info("Get weather data by the given ID: {}", id);
        return weatherRepository.findById(id);
    }

}
