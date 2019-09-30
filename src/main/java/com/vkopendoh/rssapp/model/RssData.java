package com.vkopendoh.rssapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RssData {

    @Id
    private String url;

    private String title;

    private String image;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rsslink_id")
    private RssLink link;

    public RssData() {
    }

    public RssData(String url, String title, String image, LocalDateTime dateTime, RssLink link) {
        this.url = url;
        this.title = title;
        this.image = image;
        this.dateTime = dateTime;
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public RssLink getLink() {
        return link;
    }

    public void setLink(RssLink link) {
        this.link = link;
    }
}
