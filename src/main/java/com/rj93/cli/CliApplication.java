package com.rj93.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

import java.io.IOException;


@SpringBootApplication
@CommandScan
public class CliApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CliApplication.class, args);
	}

}
