package com.vkopendoh.rssapp.repository;

import com.vkopendoh.rssapp.model.RssLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssLinkRepository extends JpaRepository<RssLink, String> {
}
