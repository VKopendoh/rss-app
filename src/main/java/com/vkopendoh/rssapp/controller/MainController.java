package com.vkopendoh.rssapp.controller;

import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.service.RssService;
import com.vkopendoh.rssapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private RssService rssService;

    private UserService userService;

    public MainController(RssService rssService, UserService userService) {
        this.rssService = rssService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String start(Map<String, Object> model) {
        return "index";
    }

    @GetMapping("/edit-rsslinks")
    public String main(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userService.getUserById(user.getId());
        model.addAttribute("rsslinks", currentUser.getRssLinks());
        return "edit-rsslinks";
    }

    @PostMapping("/edit-rsslinks")
    public String add(@RequestParam String url, @AuthenticationPrincipal User user, Model model) {
        if (url != null && url.trim().length() > 0) {
            RssLink link = rssService.getLinkByUrl(url);
            link.addUser(user);
            rssService.saveLink(link);
            rssService.saveDataByLink(link);
        }
        main(user, model);
        return "redirect:edit-rsslinks";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String url, @AuthenticationPrincipal User user, Model model) {
        User currentUser = userService.getUserById(user.getId());
        RssLink link = rssService.getLinkByUrl(url);
        currentUser.getRssLinks().remove(link);
        userService.addUser(currentUser);
        main(user, model);
        return "redirect:edit-rsslinks";
    }

    @GetMapping("/rss-list")
    public String showRssList(@AuthenticationPrincipal User user, Model model, @PageableDefault Pageable pageable) {
        User currentUser = userService.getUserById(user.getId());
        List<RssLink> rssLinks = currentUser.getRssLinks();
        Page<RssData> rssDataList = rssService.getDataByRssLinks(rssLinks, pageable);
        model.addAttribute("url", "/rss-list");
        model.addAttribute("rssdatalist", rssDataList);
        return "rss-list";
    }
}
