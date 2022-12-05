package vttp_paf.day26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp_paf.day26.repositories.TVShowsRepository;

@SpringBootApplication
public class Day26Application  {
// implements CommandLineRunner
	// @Autowired
	// private TVShowsRepository tvShowRepo;

	// @Override
	// public void run(String... args) {

	// 	// List<Document> results = tvShowRepo.findTVShowByLanguage("English");
	// 	List<Document> results = tvShowRepo.findTVShowByRating(6.5f, 1990);

	// 	for (Document d : results) {
	// 		Document ratingDoc = d.get("rating", Document.class);
	// 		/*
	// 		 * 
	// 		 */
	// 		System.out.printf("Name: %s\nSummary: %s\n", d.getString("name"), d.getString("summary"), ratingDoc.get("average"));
	// 	}

	// 	System.exit(0);
	// }

	public static void main(String[] args) {
		SpringApplication.run(Day26Application.class, args);
	}

}
