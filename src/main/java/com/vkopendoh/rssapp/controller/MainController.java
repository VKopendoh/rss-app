package com.vkopendoh.rssapp.controller;

import com.vkopendoh.rssapp.beans.RssService;
import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.repository.RssDataRepository;
import com.vkopendoh.rssapp.repository.RssLinkRepository;
import com.vkopendoh.rssapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    RssLinkRepository rssLinkRepository;

    @Autowired
    RssDataRepository rssDataRepository;

    @Autowired
    RssService rssService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String start(Map<String, Object> model) {
        return "index";
    }

    @GetMapping("/edit-rsslinks")
    public String main(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepository.findById(user.getId()).orElseGet(() -> user);
        model.addAttribute("rsslinks", currentUser.getRssLinks());
        return "edit-rsslinks";
    }

    @PostMapping("/edit-rsslinks")
    public String add(@RequestParam String url, @AuthenticationPrincipal User user, Model model) {
        RssLink link = rssLinkRepository.findById(url).orElseGet(() -> new RssLink(url, Timestamp.valueOf(LocalDateTime.now())));
        link.addUser(user);
        rssLinkRepository.save(link);
        rssDataRepository.saveAll(rssService.parse(link.getUrl()));
        main(user, model);
        return "redirect:edit-rsslinks";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String id, @AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepository.findById(user.getId()).orElseGet(() -> user);
        RssLink link = rssLinkRepository.findById(id).orElseGet(() -> new RssLink(""));
        currentUser.getRssLinks().remove(link);
        userRepository.save(currentUser);
        main(user, model);
        return "redirect:edit-rsslinks";
    }

    @GetMapping("/rss-list")
    public String showRssList(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userRepository.findById(user.getId()).orElseGet(() -> user);
        List<RssLink> rssLinks = currentUser.getRssLinks();
        // rssLinks.forEach(item -> rssService.parse(item.getUrl()));
        List<RssData> rssDataList = rssDataRepository.findAllRecentRssDataWithConstrains(rssLinks);
        model.addAttribute("rssdatalist", rssDataList);
        return "rss-list";
    }
}
