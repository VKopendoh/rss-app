package com.vkopendoh.rssapp.repository;

import com.vkopendoh.rssapp.model.RssData;
import com.vkopendoh.rssapp.model.RssLink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RssDataRepository extends CrudRepository<RssData, String> {
    @Query(
            value = "select r from RssData r where r.link in :links order by r.dateTime desc "

    )
    List<RssData> findAllRecentRssDataWithConstrains(@Param("links") List<RssLink> links);
}
