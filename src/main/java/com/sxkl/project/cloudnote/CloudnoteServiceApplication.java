package com.sxkl.project.cloudnote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CloudnoteServiceApplication { //extends SpringBootServletInitializer

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(CloudnoteServiceApplication.class, args);
		log.info("***************启动成功！**************");
		log.info("**********You Know, for Search*******");
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		System.setProperty("es.set.netty.runtime.available.processors", "false");
//		return builder.sources(CloudnoteServiceApplication.class);
//	}
}
