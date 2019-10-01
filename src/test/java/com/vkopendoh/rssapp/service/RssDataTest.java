package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/data.sql"})
public class RssDataTest {

    @Autowired
    RssService rssService;

    Pageable pageable;

    @Test
    public void saveDataAndGetDataByLink() {
        RssLink link = rssService.getLinkFromRepo("https://testguild.com/feed/");
        rssService.saveDataByLink(link);
        List<RssLink> links = new ArrayList<>();
        links.add(link);
        Page<RssData> dataList = rssService.getDataByRssLinks(links, pageable);
        Long el = dataList.getTotalElements();
        Assert.assertTrue(el == 10);
    }

}