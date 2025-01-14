package com.dev.sst.webcrawler.Controller;

import com.dev.sst.webcrawler.Crawler.Crawler;
import org.apache.coyote.Request;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private Crawler crawler=new Crawler();
    @GetMapping("/api/crawl")
    public ResponseEntity<Object> get(@RequestParam String url, @RequestParam int depth){
        System.out.println(url);
        return new ResponseEntity<>(crawler.Helper(url,depth),HttpStatus.ACCEPTED);
    }
    @GetMapping("/api")
    public String test(){
        String hi = "HI";
        return hi;
    }
}
