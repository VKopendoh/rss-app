package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/data.sql"})
public class RssServiceTest {

    @Autowired
    RssService rssService;

    @Test
    public void parse() {
        List<RssData> dataList = rssService.parse("https://testguild.com/feed/");
        RssData data = dataList.get(0);
        Assert.assertTrue(data.getTitle().equals("Selenide a Java Based Automation Framework Overview"));

    }

    @Test
    public void getLinkFromRepo() {
        RssLink link = rssService.getLinkFromRepo("https://testguild.com/feed/");
        LocalDate ld = LocalDate.of(2015, 12, 17);
        Assert.assertTrue(ld.equals(link.getPubTime().toLocalDateTime().toLocalDate()));
    }

    @Test
    public void saveLink() {
        RssLink link = new RssLink("http://localhost/rss");
        rssService.saveLink(link);
        RssLink linkRepo = rssService.getLinkFromRepo(link.getUrl());
        Assert.assertTrue(link.getUrl().equals(linkRepo.getUrl()));
    }
}