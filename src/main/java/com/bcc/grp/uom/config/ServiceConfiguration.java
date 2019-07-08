package com.bcc.grp.uom.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
@Getter
public class ServiceConfiguration {

    @Value("${service.properties.short-code:STR}")
    private String shortCode;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
