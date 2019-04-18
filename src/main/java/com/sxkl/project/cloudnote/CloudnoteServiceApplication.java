package com.sxkl.project.cloudnote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CloudnoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudnoteServiceApplication.class, args);
		log.info("***************启动成功！**************");
		log.info("**********You Know, for Search*******");
	}
}
