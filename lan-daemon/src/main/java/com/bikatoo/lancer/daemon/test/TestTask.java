package com.bikatoo.lancer.daemon.test;

import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestTask {

  @PostConstruct
  public void init() {
    log.info("[+] {} initialized", getClass());
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void perHourTestTask() {
    log.info("perHourTestTask [{}]", new Date());
  }
}
