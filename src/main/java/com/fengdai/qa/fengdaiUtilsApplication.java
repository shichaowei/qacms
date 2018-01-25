package com.fengdai.qa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author hzweisc
 *
 */
//指定aop事务执行顺序，已保证在切换数据源的后面
@EnableTransactionManagement(order = 2)
@SpringBootApplication
@ServletComponentScan
public class fengdaiUtilsApplication {
    public static void main(String[] args) {
       SpringApplication.run(fengdaiUtilsApplication.class, args);
    }
}