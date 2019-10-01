package com.vkopendoh.rssapp.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class RssLink {
    @Id
    private String url;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "rsslink_user",
            joinColumns = {@JoinColumn(name = "rsslink_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    private Timestamp pubTime;

    @OneToMany(mappedBy = "link", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RssData> rssDataList;

    public RssLink() {
    }

    public RssLink(String url) {
        this.url = url;
    }

    public RssLink(String url, Set<User> users) {
        this.url = url;
        this.users = users;
    }

    public RssLink(String url, Timestamp pubTime) {
        this.url = url;
        this.pubTime = pubTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<RssData> getRssDataList() {
        return rssDataList;
    }

    public void setRssDataList(List<RssData> rssDataList) {
        this.rssDataList = rssDataList;
    }

    public Timestamp getPubTime() {
        return pubTime;
    }

    public void setPubTime(Timestamp pubTime) {
        this.pubTime = pubTime;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }
}
