package vttp_paf.day28;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp_paf.day28.repositories.BGGRepository;

@SpringBootApplication
public class Day28Application implements CommandLineRunner {

	@Autowired
	private BGGRepository bRepo;

	@Override
	public void run(String... args) {

		bRepo.search("Twilight Imperium");

		System.exit(0);
	}

	public static void main(String[] args) {
		SpringApplication.run(Day28Application.class, args);
	}

}
