package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.RssLink;
import com.vkopendoh.rssapp.repository.RssDataRepository;
import com.vkopendoh.rssapp.repository.RssLinkRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;


@Component
public class RssLoaderDaemon {

    private RssLinkRepository rssLinkRepository;

    private final RssDataRepository rssDataRepository;

    private RssService rssService;

    public RssLoaderDaemon(RssLinkRepository rssLinkRepository,
                           RssDataRepository rssDataRepository, RssService rssService) {
        this.rssLinkRepository = rssLinkRepository;
        this.rssDataRepository = rssDataRepository;
        this.rssService = rssService;
    }

    @Async
    @Scheduled(fixedRate = 30000)
    public void loadData() {
        Logger log = Logger.getLogger("Mylogger");
        log.info("=====> Started refresh rss data....");
        List<RssLink> links = rssLinkRepository.findAll();
        links.forEach(l -> rssDataRepository.saveAll(rssService.parseToRssData(l.getUrl())));
        log.info("=====> Data refreshed....");
    }

}
