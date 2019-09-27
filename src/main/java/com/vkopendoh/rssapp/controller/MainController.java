package com.vkopendoh.rssapp.controller;

import com.vkopendoh.rssapp.entity.RssLink;
import com.vkopendoh.rssapp.repository.RssLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    RssLinkRepository rssLinkRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "index";
    }

    @GetMapping("/edit-rsslinks")
    public String main(Model model) {
        model.addAttribute("rsslinks", rssLinkRepository.findAll());
        return "edit-rsslinks";
    }

    @PostMapping("/edit-rsslinks")
    public String add(@RequestParam String url, Model model) {
        RssLink rssLink = new RssLink(url);
        rssLinkRepository.save(rssLink);
        main(model);
        return "edit-rsslinks";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        rssLinkRepository.deleteById(id);
        main(model);
        return "edit-rsslinks";
    }
}
