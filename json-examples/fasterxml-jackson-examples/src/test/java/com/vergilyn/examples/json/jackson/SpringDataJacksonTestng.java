package com.vergilyn.examples.json.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;
import org.testng.annotations.Test;

/**
 * @author vergilyn
 * @date 2020-05-06
 */
public class SpringDataJacksonTestng {

    /**
     * <pre>
     * {@linkplain ElasticsearchRestTemplate#ElasticsearchRestTemplate(org.elasticsearch.client.RestHighLevelClient)}
     * -> {@linkplain DefaultResultMapper#DefaultResultMapper(org.springframework.data.mapping.context.MappingContext)}
     * -> {@linkplain DefaultResultMapper#initEntityMapper(org.springframework.data.mapping.context.MappingContext)}
     * -> {@linkplain DefaultEntityMapper#DefaultEntityMapper(org.springframework.data.mapping.context.MappingContext)}
     * </pre>
     */
    @Test
    public void init(){
        ObjectMapper objectMapper = new ObjectMapper();

        // objectMapper.registerModule(new DefaultEntityMapper.SpringDataElasticsearchModule(context));
        objectMapper.registerModule(new CustomGeoModule());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
}
