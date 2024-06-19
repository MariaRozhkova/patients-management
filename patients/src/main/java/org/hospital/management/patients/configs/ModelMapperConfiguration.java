package org.hospital.management.patients.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper configuration.
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Creates model mapper bean.
     *
     * @return ModelMapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
