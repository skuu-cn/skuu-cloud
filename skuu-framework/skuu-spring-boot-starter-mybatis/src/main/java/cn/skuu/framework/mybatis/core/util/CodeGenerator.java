package cn.skuu.framework.mybatis.core.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码自动生成
 *
 * @author dcx
 * @since 2022-08-11 23:47
 **/
public class CodeGenerator {

    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false", "root", "1122");
    private static final String moduleName = "/sdx-data-model";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？"))
                        .outputDir(System.getProperty("user.dir") + moduleName + "/src/main/java")
                        .enableSwagger())
                // 包配置,由于设置了parent，mapper.xml无法定制，需要手动挪一下
                .packageConfig((scanner, builder) -> builder.parent("com.seaboxdata.datamodel").xml("xml"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle().enableFileOverride()
                        .entityBuilder().enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();

    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
