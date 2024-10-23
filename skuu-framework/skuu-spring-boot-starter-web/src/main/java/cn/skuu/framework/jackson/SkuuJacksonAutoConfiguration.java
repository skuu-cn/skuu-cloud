package cn.skuu.framework.jackson;

import cn.skuu.framework.common.enums.ICode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@AutoConfiguration
@Slf4j
public class SkuuJacksonAutoConfiguration {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public ObjectMapper initObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        //时间处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(STANDARD_FORMAT)));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(STANDARD_FORMAT)));
        //localDateTime按照 "yyyy-MM-dd HH:mm:ss"的格式进行序列化、反序列化
        objectMapper.registerModule(javaTimeModule);
        //枚举处理
        // 声明一个简单Module 对象
        SimpleModule module = new SimpleModule();
        // 给Module 添加一个序列化器
        module.addSerializer(ICode.class, new JsonSerializer<ICode>() {
            @Override
            public void serialize(ICode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                // 开始写入对象
                gen.writeStartObject();
                // 分别指定 k v   code   description
                gen.writeNumberField("code", value.getCode());
                gen.writeStringField("desc", value.getMsg());
                // 显式结束操作
                gen.writeEndObject();
            }
        });
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 注册 Module
        objectMapper.registerModule(module);

        //GMT+8
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
//        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

}
