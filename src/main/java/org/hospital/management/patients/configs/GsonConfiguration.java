package org.hospital.management.patients.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import org.hospital.management.patients.configs.adapters.GenderAdapter;
import org.hospital.management.patients.configs.adapters.LocalDateTimeAdapter;
import org.hospital.management.patients.enums.Gender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Gender.class, new GenderAdapter())
            .create();
    }
}
