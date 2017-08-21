package com.xcosta.xmoney.api;

import com.xcosta.xmoney.api.configurations.XMoneyProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(XMoneyProperty.class)
public class XMoneyApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(XMoneyApiApplication.class, args);
	}
}
