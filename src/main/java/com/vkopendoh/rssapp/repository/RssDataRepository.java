package com.vkopendoh.rssapp.repository;

import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RssDataRepository extends JpaRepository<RssData, String> {
    @Query(
            value = "select r from RssData r where r.link in :links order by r.dateTime desc "
    )
    Page<RssData> findRssDataByUserRssLinks(@Param("links") List<RssLink> links, Pageable pageable);
}
