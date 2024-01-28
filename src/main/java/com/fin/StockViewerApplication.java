package com.fin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.fin"})
@SpringBootApplication
public class StockViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockViewerApplication.class, args);
	}

}
