package vttp_pa.day21.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_pa.day21.models.Book;
import vttp_pa.day21.repositories.BookRepository;

@Controller
@RequestMapping("/search")
public class SearchController {
    
    @Autowired
    private BookRepository bookRepo;

    private int pgOffset;

    @GetMapping("/results")
    public String getSearched(@RequestParam String input, @RequestParam String limit, Model model) {

        int resultCount = Integer.parseInt(limit);
        pgOffset = 0;

        // Query the database for the books
        List<Book> books = bookRepo.getBooksByName(input, resultCount, pgOffset);

        model.addAttribute("input", input);
        model.addAttribute("limit", resultCount);
        model.addAttribute("offset", pgOffset);
        model.addAttribute("list", books);
        model.addAttribute("hasResult", books.size() > 0);

        return "result";
    }

    @GetMapping("/prev")
    public String getPrevSearched(@RequestParam String input, @RequestParam String limit, @RequestParam String offset, Model model) {

        int resultCount = Integer.parseInt(limit);
        pgOffset = Integer.parseInt(offset);
        pgOffset = pgOffset - resultCount;
        List<Book> books;
        
        if (pgOffset == 0) {
            books = bookRepo.getBooksByName(input, resultCount, 0);
        } else {
            books = bookRepo.getBooksByName(input, resultCount, pgOffset);
        }

        model.addAttribute("input", input);
        model.addAttribute("limit", resultCount);
        model.addAttribute("offset", pgOffset);
        model.addAttribute("list", books);

        return "result";
    }

    @GetMapping("/next")
    public String getNextSearched(@RequestParam String input, @RequestParam String limit, @RequestParam String offset, Model model) {

        int resultCount = Integer.parseInt(limit);
        pgOffset = Integer.parseInt(offset);
        pgOffset = pgOffset + resultCount;
        
        // Query the database for the books
        List<Book> books = bookRepo.getBooksByName(input, resultCount, pgOffset);

        model.addAttribute("input", input);
        model.addAttribute("limit", resultCount);
        model.addAttribute("offset", pgOffset);
        model.addAttribute("list", books);

        return "result";
    }
    
}
