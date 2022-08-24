package com.enrollment.config;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("cursoMapper")
    public ModelMapper cursoMapper(){
        return new ModelMapper();
    }

    @Bean("estudianteMapper")
    public ModelMapper estudianteMapper(){
        return new ModelMapper();
    }

    @Bean("matriculaMapper")
    public ModelMapper matriculaMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
        return mapper;
    }
}
