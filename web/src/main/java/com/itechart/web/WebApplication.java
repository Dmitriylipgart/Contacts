package com.itechart.web;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import scheduler.TestShedule;

import java.io.File;
import java.io.FileNotFoundException;


@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) throws SchedulerException {

		SpringApplication.run(WebApplication.class, args);

		TestShedule testShedule = new TestShedule();
		testShedule.startBirthdayShedule();

//		Path currentRelativePath = Paths.get("");
//		String s = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current relative path is: " + s);
//		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
//		Path path = FileSystems.getDefault().getPath("newYear.txt").toAbsolutePath();
//		System.out.println(path);
//		System.out.println();

	}

}
