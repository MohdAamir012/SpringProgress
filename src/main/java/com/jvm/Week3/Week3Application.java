package com.jvm.Week3;

import com.jvm.Week3.di.Coffee;
import com.jvm.Week3.di.HotDrink;
import com.jvm.Week3.di.Restraunt;
import com.jvm.Week3.di.Tea;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Week3Application {

//	@Bean
//	Restraunt restraunt(HotDrink hotDrink){
//		Restraunt restraunt = new Restraunt();
//		restraunt.hotDrink=hotDrink;
//		return restraunt;
//	}
//
//	@Bean
//	Tea tea(){
//		return new Tea();
//	}
//
//	@Bean
//	Coffee coffee(){
//		return new Coffee();
//	}

	public static void main(String[] args) {

	ConfigurableApplicationContext configurableApplicationContext= SpringApplication.run(Week3Application.class, args);
		System.out.println(configurableApplicationContext.getBean(Restraunt.class).hotDrink);;
		System.out.println("Hellooo sir it is week3 Application!");
	}

}
