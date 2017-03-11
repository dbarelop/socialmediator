package es.ucode.oesia.random.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MappingController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/feed")
    public String feed() {
        return "feed";
    }
}
