package com.itechart.web;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.itechart.model.scheduler.TestShedule;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) throws SchedulerException {

		SpringApplication.run(WebApplication.class, args);

//		TestShedule testShedule = new TestShedule();
//		testShedule.startBirthdayShedule();

//		Path currentRelativePath = Paths.get("");
//		String s = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current relative path is: " + s);
//		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
//		Path path = FileSystems.getDefault().getPath("newYear.txt").toAbsolutePath();
//		System.out.println(path);
//		System.out.println();

	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(WebApplication.class);
//	}
}
