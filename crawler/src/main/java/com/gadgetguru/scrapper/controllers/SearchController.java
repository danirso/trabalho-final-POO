package com.gadgetguru.scrapper.controllers;

import org.springframework.ui.Model;
import com.gadgetguru.scrapper.Crawl;
import com.gadgetguru.scrapper.Crawl2;
import com.gadgetguru.scrapper.Crawl3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final Crawl crawlService;
    private final Crawl2 crawlService2;
    private final Crawl3 crawlService3;

    @Autowired
    public SearchController(Crawl crawlService, Crawl2 crawlService2, Crawl3 crawlService3) {
        this.crawlService = crawlService;
        this.crawlService2 = crawlService2;
        this.crawlService3 = crawlService3; 
    }

    @GetMapping("/")
    public String showSearchPage() {
        return "index"; 
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam("search") String searchTerm, Model model) {

        Product produto1 = crawlService.crawlForProduct(searchTerm);
        model.addAttribute("product1",produto1);

        Product produto2 = crawlService2.crawlForProduct(searchTerm); 
        model.addAttribute("product2", produto2);

        Product produto3 = crawlService3.crawlForProduct(searchTerm);
        model.addAttribute("product3", produto3);
        return "result"; 
    }
    
}
