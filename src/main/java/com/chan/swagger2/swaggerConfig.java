package com.chan.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//启用swagger2功能主键
@EnableSwagger2
public class swaggerConfig {

    //api文档实例
    @Bean
    public Docket createRestfullApi() {
        return new
                //文档类型：DocumentationType.SWAGGER_2
                Docket(DocumentationType.SWAGGER_2)
                //api信息
                .apiInfo(apiInfo())
                //构建api选择器
                .select()
                //api选择器选择api的包
                .apis(RequestHandlerSelectors.basePackage("com.chan.controller"))
                //api选择器选择包路径下任何api显示在文档中
                .paths(PathSelectors.any())
                //创建文档
                .build();
    }

    //接口的相关信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My API DOM")
                .description("API DOM")
                .termsOfServiceUrl("")
                .version("1.0")
                .license("")
                .licenseUrl("www.baidu.com")
                .build();
    }


}
