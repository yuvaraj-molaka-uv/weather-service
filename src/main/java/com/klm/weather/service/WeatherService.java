package com.klm.weather.service;

import org.springframework.stereotype.Service;
import com.klm.weather.model.Weather;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public interface WeatherService {

    Weather saveWeather(Weather weather);

    List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort) throws ParseException;

    Optional<Weather> getWeatherById(int id);

}