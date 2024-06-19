package org.hospital.management.patients.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import org.hospital.management.patients.configs.adapters.GenderAdapter;
import org.hospital.management.patients.configs.adapters.LocalDateTimeAdapter;
import org.hospital.management.patients.configs.adapters.LocalDateTimeWithMillisecondsAdapter;
import org.hospital.management.patients.enums.Gender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Gson configuration.
 */
@Configuration
public class GsonConfiguration {

    /**
     * Creates gson bean with needed type adapters.
     *
     * @return configured Gson bean
     */
    @Bean
    @Primary
    public Gson gson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Gender.class, new GenderAdapter())
            .create();
    }

    /**
     * Creates gson bean with needed type adapters for creating pagination response.
     *
     * @return configured Gson bean
     */
    @Bean
    @Qualifier("paginationResponseGson")
    public Gson paginationResponseGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeWithMillisecondsAdapter())
            .create();
    }
}
