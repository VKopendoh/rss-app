package com.vkopendoh.rssapp.beans;

import com.vkopendoh.rssapp.model.RssLink;
import com.vkopendoh.rssapp.repository.RssDataRepository;
import com.vkopendoh.rssapp.repository.RssLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;


@Component
public class RssLoaderDeamon {

    @Autowired
    RssLinkRepository rssLinkRepository;

    @Autowired
    RssDataRepository rssDataRepository;

    @Autowired
    RssService rssService;

    @Async
    @Scheduled(fixedRate = 30000)
    public void loadData() {
        Logger log = Logger.getLogger("Mylogger");
        log.info("=====> Started refresh rss data....");
        List<RssLink> links = (List<RssLink>) rssLinkRepository.findAll();
        links.forEach(l -> rssDataRepository.saveAll(rssService.parse(l.getUrl())));
        log.info("=====> Data refreshed....");
    }

}
