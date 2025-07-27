package com.saladay.saladay_api.util.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
//                .setSkipNullEnabled(true) // 널 받을지.
                .setFieldAccessLevel(AccessLevel.PRIVATE) // private 설정
//                .setMatchingStrategy(MatchingStrategies.STRICT) // 엄격 근엄 진지
                .setMatchingStrategy(MatchingStrategies.LOOSE) // 편하게 하기 루즈~
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }


}
