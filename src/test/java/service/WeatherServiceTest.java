package service;

import com.assignment.spring.api.Main;
import com.assignment.spring.api.Sys;
import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    public final static String CITY = "test";
    public static final String COUNTRY = "RS";
    public static final double TEMP = 100.0;

    @Mock
    private WeatherRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment env;

    @InjectMocks
    private WeatherService service;

    @Test
    public void shouldReturnWeatherEntity() {
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
        ResponseEntity responseEntity = new ResponseEntity(response, HttpStatus.OK);

        when(env.getProperty(anyString())).thenReturn("test");
        when(restTemplate.getForEntity("test", WeatherResponse.class)).thenReturn(responseEntity);
        when(repository.save(any())).thenReturn(new WeatherEntity());

        Assertions.assertNotNull(service.getWeatherByCity(CITY));
    }
}
