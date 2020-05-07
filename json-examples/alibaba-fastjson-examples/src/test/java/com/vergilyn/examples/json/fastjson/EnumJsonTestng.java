package com.vergilyn.examples.json.fastjson;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.testng.annotations.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingName;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingToString;

/**
 * <a href="https://www.cnblogs.com/VergiLyn/p/6753706.html">fastjson对enum的处理</a>
 * @author vergilyn
 * @date 2020-05-06
 */
public class EnumJsonTestng {

    // TODO 2020-05-06 待完善
    @Test
    public void test(){
        EnumDto dto = new EnumDto(1, StatusEnum.DELETE);

        System.out.printf("WriteEnumUsingName >>>> %s \r\n", JSON.toJSONString(dto, WriteEnumUsingName));
        System.out.printf("WriteEnumUsingName >>>> %s \r\n", JSON.toJSONString(dto, WriteEnumUsingToString));
    }

    @Data
    @NoArgsConstructor
    static class EnumDto{
        private Integer id;
        private StatusEnum status;

        public EnumDto(Integer id, StatusEnum status) {
            this.id = id;
            this.status = status;
        }
    }

    static enum StatusEnum {
        DELETE("delete", Byte.MIN_VALUE), PUBLISH("publish", Byte.MAX_VALUE);

        StatusEnum(String desc, Byte status) {
            this.desc = desc;
            this.status = status;
        }

        public final Byte status;
        public final String desc;

        public Byte getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return "StatusEnum{" + "status=" + status + ", desc='" + desc + '\'' + '}';
        }
    }
}
