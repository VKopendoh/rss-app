package com.vkopendoh.rssapp.service;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.repository.RssDataRepository;
import com.vkopendoh.rssapp.repository.RssLinkRepository;
import com.vkopendoh.rssapp.repository.UserRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RssService extends AbstractRssFeedView {

    private final RssLinkRepository rssLinkRepository;

    private final RssDataRepository rssDataRepository;

    private final UserRepository userRepository;

    public RssService(RssLinkRepository rssLinkRepository,
                      RssDataRepository rssDataRepository, UserRepository userRepository) {
        this.rssLinkRepository = rssLinkRepository;
        this.rssDataRepository = rssDataRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
                                     HttpServletRequest request) {
        feed.setEncoding("windows-1251");
        feed.setTitle("RSS аггрегатор новостей");
        feed.setDescription("Все наиболее интересные и последние новости в одном месте.");
        feed.setLink("http://www.kami-shop.ru/");
        feed.setPubDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse) throws Exception {
        User currentUser = (User) map.get("user");
        User user = userRepository.findById(currentUser.getId()).orElseGet(() -> currentUser);
        List<RssLink> rssLinks = user.getRssLinks();
        Page<RssData> rssDataList = rssDataRepository.findRssDataByUserRssLinks(rssLinks, Pageable.unpaged());
        List<Item> resultRss = new ArrayList<>();

        rssDataList.forEach(rssData -> resultRss.add(rssItemBuilder(rssData)));
        return resultRss;
    }

    private static Item rssItemBuilder(RssData rssData) {
        Item entry = new Item();
        entry.setTitle(rssData.getTitle());
        entry.setLink(rssData.getUrl());
        entry.setPubDate(Timestamp.valueOf(rssData.getDateTime()));
        List<Enclosure> enclosures = new ArrayList<>();
        Enclosure enc = new Enclosure();
        enc.setUrl(rssData.getImage());
        enclosures.add(enc);
        entry.setEnclosures(enclosures);
        return entry;
    }

    @Async
    public List<RssData> parseToRssData(String url) {
        List<RssData> rssEntries = new ArrayList<>();
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                List entries = feed.getEntries();
                Iterator itRSSs = entries.iterator();

                while (itRSSs.hasNext()) {
                    SyndEntry entry = (SyndEntry) itRSSs.next();
                    String imageUrl = "https://www.x-forces.com/wp-content/uploads/2019/07/placeholder.png";
                    SyndEnclosure enc = null;
                    if (entry.getEnclosures() != null && entry.getEnclosures().size() > 0) {
                        enc = entry.getEnclosures().get(0);
                    }
                    if (enc != null && enc.getType().contains("image")) {
                        imageUrl = enc.getUrl();
                    }
                    RssData rssEntry = new RssData(entry.getLink(), entry.getTitle(), imageUrl,
                            LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(),
                                    ZoneId.systemDefault()), new RssLink(url));
                    rssEntries.add(rssEntry);
                }

            } catch (FeedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rssEntries;
    }

    public RssLink getLinkByUrl(String url) {
        return rssLinkRepository.findById(url).orElseGet(() -> new RssLink(url,
                Timestamp.valueOf(LocalDateTime.now())));
    }

    public void saveLink(RssLink link) {
        rssLinkRepository.save(link);
    }

    public void saveDataByLink(RssLink link) {
        rssDataRepository.saveAll(parseToRssData(link.getUrl()));
    }

    public Page<RssData> getDataByRssLinks(List<RssLink> rssLinks, Pageable pageable) {
        return rssDataRepository.findRssDataByUserRssLinks(rssLinks, pageable);
    }
}
