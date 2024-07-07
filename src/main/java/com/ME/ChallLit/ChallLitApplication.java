package com.ME.ChallLit;

import com.ME.ChallLit.Principal.Principal;
import com.ME.ChallLit.Repository.AutoresRepository;
import com.ME.ChallLit.Repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallLitApplication implements CommandLineRunner {

	@Autowired
	private LibrosRepository librosRepository;
	@Autowired
	private AutoresRepository autoresRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallLitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal main = new Principal(librosRepository, autoresRepository);

		main.menu();
	}
}
