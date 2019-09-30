package com.vkopendoh.rssapp.beans;

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
import com.vkopendoh.rssapp.repository.UserRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Service
public class RssService extends AbstractRssFeedView {

    @Autowired
    RssDataRepository rssDataRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setEncoding("windows-1251");
        feed.setTitle("RSS аггрегатор новостей");
        feed.setDescription("Все наиболее интересные и последние новости в одном месте.");
        feed.setLink("http://www.kami-shop.ru/");
        feed.setPubDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        User currentUser = (User) map.get("user");
        User user = userRepository.findById(currentUser.getId()).orElseGet(() -> currentUser);
        List<RssLink> rssLinks = user.getRssLinks();
        List<RssData> rssDataList = rssDataRepository.findAllRecentRssDataWithConstrains(rssLinks);

        List<Item> resultRss = new ArrayList<>();

        rssDataList.forEach(data -> {
            Item entry = new Item();
            entry.setTitle(data.getTitle());
            entry.setLink(data.getUrl());
            entry.setPubDate(Timestamp.valueOf(data.getDateTime()));
            List<Enclosure> enclosures = new ArrayList<>();
            Enclosure enc = new Enclosure();
            enc.setUrl(data.getImage());
            enclosures.add(enc);
            entry.setEnclosures(enclosures);
            resultRss.add(entry);
        });
        return resultRss;
    }

    @Async
    public List<RssData> parse(String url) {
        List<RssData> rssEntries = new ArrayList<>();
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();

                SyndFeed feed = input.build(new XmlReader(stream));
                System.out.println(feed.getTitle());
                System.out.println(feed.getPublishedDate());
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
                    RssData rssEntry = new RssData(entry.getLink(), entry.getTitle(), imageUrl
                            , LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault()), new RssLink(url));
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
}
