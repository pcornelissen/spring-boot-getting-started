package com.packtpub.yummy.service;

import com.packtpub.yummy.model.Bookmark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookmarkDao extends CrudRepository<Bookmark, UUID>{
}
