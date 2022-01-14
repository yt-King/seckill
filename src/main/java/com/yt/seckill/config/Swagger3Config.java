package com.yt.seckill.config;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * @author 应涛
 * @date 2022/1/14
 * @function：swagger配置类
 */
@Configuration
public class Swagger3Config {

    @Bean
    public Docket createRestApi() {
        // 配置OAS 3.0协议
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                // 查找有@Tag注解的类，并生成一个对应的分组；类下面的所有http请求方法，都会生成对应的API接口
                // 通过这个配置，就可以将那些没有添加@Tag注解的控制器类排除掉
                .apis(RequestHandlerSelectors.withClassAnnotation(Tag.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("银行秒杀系统后台API")
                .description("银行秒杀系统 Doc文档")
                .termsOfServiceUrl("http://www.zufeyyds.top/")
                .contact(new Contact("ytKing", "http://www.zufeyyds.top/", "yt1577396227@163.com"))
                .version("1.0.0")
                .build();
    }

}
