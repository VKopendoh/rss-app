package com.vkopendoh.rssapp.controller;

import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import java.util.Map;

@RestController
public class RssFeedController {

    @Autowired
    private RssService view;

    @GetMapping("/rss")
    public View getFeed(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("user", user);
        return view;
    }
}
