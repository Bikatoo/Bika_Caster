package com.bikatoo.lancer.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.bikatoo.lancer")
@MapperScan("com.bikatoo.lancer.**.mapper")
@EnableScheduling
public class LancerApplication {

  public static void main(String[] args) {
    SpringApplication.run(LancerApplication.class, args);
  }

}
