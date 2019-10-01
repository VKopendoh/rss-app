package com.vkopendoh.rssapp.controller;

import com.vkopendoh.rssapp.service.RssService;
import com.vkopendoh.rssapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
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

    @GetMapping("/test")
    public String testRss(){

        return  "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                "<rss version=\"2.0\">\n" +
                "    <channel>\n" +
                "        <title>RSS аггрегатор новостей</title>\n" +
                "        <link>http://www.kami-shop.ru/</link>\n" +
                "        <description>Все наиболее интересные и последние новости в одном месте.</description>\n" +
                "        <pubDate>Tue, 01 Oct 2019 10:25:38 GMT</pubDate>\n" +
                "    </channel>\n" +
                "    <item>\n" +
                "        <title>title</title>\n" +
                "        <link> http://link</link>\n" +
                "        <pubDate>01 Oct 2019 08:18:25 +0000</pubDate>\n" +
                "    </item>\n" +
                "</rss>";
    }

}
