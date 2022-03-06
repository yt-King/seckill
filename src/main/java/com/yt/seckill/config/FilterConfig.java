package com.yt.seckill.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 应涛
 * @date 2021/12/5
 * @function：
 */
@Configuration
public class FilterConfig {
    /**
     * 自定义跨域过滤器
     * @param
     * @return
     */

    @Bean
    public FilterRegistrationBean setFilter(){
        System.out.println("callbackFilter");
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new ResponseFilter());
        filterBean.setOrder(0);
        filterBean.setName("callbackFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

}
