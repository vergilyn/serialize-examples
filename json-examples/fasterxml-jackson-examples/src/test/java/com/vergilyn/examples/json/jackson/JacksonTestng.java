package com.vergilyn.examples.json.jackson;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vergilyn
 * @date 2020-05-06
 */
public class JacksonTestng {

    @Test
    public void serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JacksonDto dto = new JacksonDto().init();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        System.out.println("serialize >>>> " + json);

        Assert.assertFalse(json.matches("ignore"));
    }

    @Test
    public void deserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = JacksonDto.json();

        // 处理特殊字符 ASCII < 32  EX. 制表符、换行符
        objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());

        JacksonDto dto = objectMapper.readValue(json, JacksonDto.class);
        System.out.println("deserialize >>>> " + dto);

        Assert.assertTrue(StringUtils.isBlank(dto.ignoreField));
    }

    @Data
    @ToString
    static class JacksonDto {
        private Integer id;
        private String name;
        private Long qq;
        private String specialChar;

        /**
         * {@linkplain LocalDateTime}、{@linkplain LocalDate}、{@linkplain java.time.LocalTime} 默认序列化格式可能不是期望的
         * <pre>
         *     ex.  LocalDate -> "2020-05-07"
         *     "time":{
         * 	     "year": 2020,
         * 	     "month": "MAY",
         * 	     "dayOfYear": 128,
         * 	     "leapYear": true,
         * 	     "dayOfMonth": 7,
         * 	     "monthValue": 5,
         * 	     "dayOfWeek": "THURSDAY",
         * 	     "era": "CE",
         * 	     "chronology": {
         * 		    "id": "ISO",
         * 		    "calendarType": "iso8601"
         *       }
         *     }
         * </pre>
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        private LocalDate time;

        @JsonIgnore
        private String ignoreField;

        public JacksonDto init(){
            this.id = 1;
            this.name = "vergilyn";
            this.qq = 409839163L;
            this.time = LocalDate.now();
            this.ignoreField = "ignore-field";
            this.specialChar = "vergilyn@vip.qq.com \r\n <a href=\"http://vergilyn.com\">vergilyn.com</a>";

            return this;
        }

        public static String json(){
            return "{"
                    + "\"id\":1,"
                    + "\"name\":\"vergilyn\","
                    + "\"specialChar\":\"vergilyn@vip.qq.com \r\n <a href='http://vergilyn.com'>vergilyn.com</a>\","
                    + "\"qq\":409839163,"
                    + "\"time\":\"2020-05-07\","
                    + "\"ignoreField\":\"ignore-field\"}";
        }
    }
}
