package vttp_paf.day29_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp_paf.day29_workshop.services.MarvelService;

@SpringBootApplication
public class Day29WorkshopApplication implements CommandLineRunner {

	// @Autowired
    // private MarvelService marvelSvc;
	
	@Override
	public void run(String... args) {
		
		// marvelSvc.search("man");
		// System.exit(0);
	}

	public static void main(String[] args) {
		SpringApplication.run(Day29WorkshopApplication.class, args);
	}

}
