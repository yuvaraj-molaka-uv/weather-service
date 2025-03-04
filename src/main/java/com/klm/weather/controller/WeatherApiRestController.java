package com.klm.weather.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klm.weather.model.Weather;
import com.klm.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

	private final WeatherService service;

    public WeatherApiRestController(WeatherService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
        Weather createdWeather = service.save(weather);
        return ResponseEntity.status(201).body(createdWeather);
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getWeather(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String sort) {
        
        List<String> cities = city != null ? List.of(city.toLowerCase().split(",")) : null;
        List<Weather> weatherList = service.findAll(date, cities, sort);

        return ResponseEntity.ok(weatherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable int id) {
    	Optional<Weather> weather = service.findById(id);
    	if (weather.isPresent()) {
    	    return ResponseEntity.ok(weather.get());
    	} else {
    	    return ResponseEntity.notFound().build();
    	}
    }

}
