package service;

import com.assignment.spring.api.Main;
import com.assignment.spring.api.Sys;
import com.assignment.spring.api.Weather;
import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.Coord;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.entity.WeatherInfoEntity;
import com.assignment.spring.entity.Wind;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.Units;
import com.assignment.spring.service.WeatherService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    public final static String CITY = "test";
    public static final String COUNTRY = "RS";
    public static final double TEMP = 100.0;
    public static final List<WeatherEntity> WEATHER_ENTITIES = Arrays
            .asList(
                    new WeatherEntity("Novi Sad", "RS", 32.0, "c"),
                    new WeatherEntity("Beograd", "RS", 33.0, "c"),
                    new WeatherEntity("Amsterdan", "NL", 33.0, "c"));


    @Mock
    private WeatherRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment env;

    @InjectMocks
    private WeatherService service;

    @Test
    public void shouldReturnWeatherEntity() throws NotFoundException {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCity(CITY);
        WeatherResponse response = new WeatherResponse();
        Sys sys = new Sys();
        Main main = new Main();
        main.setTemp(TEMP);
        sys.setCountry(COUNTRY);
        response.setName(CITY);
        response.setSys(sys);
        response.setMain(main);
        WeatherInfoEntity wie = new WeatherInfoEntity();
        wie.setWeather(weatherEntity);
        wie.setId(1);
        Wind wind = new Wind();
        wind.setId(1);
        Coord coord = new Coord();
        coord.setId(1);
        response.setWeather(Arrays.asList(new Weather()));
        response.setCoord(new com.assignment.spring.api.Coord());
        response.setWind(new com.assignment.spring.api.Wind());

        ResponseEntity responseEntity = new ResponseEntity(response, HttpStatus.OK);

        when(env.getProperty(anyString())).thenReturn("test");
        when(restTemplate.getForEntity("test", WeatherResponse.class)).thenReturn(responseEntity);
        when(repository.save(any())).thenReturn(new WeatherEntity());

        Assertions.assertNotNull(service.getWeatherByCity(CITY, Units.FAHRENHEIT.getUnit()));
        Assertions.assertNotNull(service.getWeatherByCity(CITY, Units.CELSIUS.getUnit()));
    }
}
