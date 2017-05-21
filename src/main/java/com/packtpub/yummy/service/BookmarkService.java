package com.packtpub.yummy.service;

import com.packtpub.yummy.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Service
@Transactional
public class BookmarkService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PlatformTransactionManager platformTransactionManager;
    
    public UUID addBookmark(Bookmark bookmark) {
        UUID uuid = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO bookmark (url, uuid, version)" +
                " values (?,?,1)", bookmark.getUrl(), uuid);
        return uuid;
    }

    public Bookmark find(UUID id) {
        return jdbcTemplate.queryForObject("select * from bookmark where uuid=?",
                (rs, rowNum) -> new Bookmark(
                        rs.getString("url"),
                        rs.getObject("uuid", UUID.class),
                        rs.getInt("version")
                ), id);

    }

    public Iterable<Bookmark> findAll() {
        return jdbcTemplate.query("select * from bookmark",
                BeanPropertyRowMapper.newInstance(Bookmark.class));
    }

    public Bookmark update(Bookmark bookmark) {
        find(bookmark.getUuid());
        int update = jdbcTemplate.update(
                "update bookmark set url=?, version=version+1 where uuid=? and version =?",
                bookmark.getUrl(), bookmark.getUuid(), bookmark.getVersion()
        );
        if(update!=1)throw new OptimisticLockingFailureException("Stale update detected for "+bookmark.getUuid());
        return find(bookmark.getUuid());
    }

    public void delete(UUID id) {
        if(jdbcTemplate.update("delete from bookmark where uuid=?", id)!=1)
            throw new NotModifiedDataAccessException("Bookmark already gone");
    }

    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public static class NotModifiedDataAccessException extends DataAccessException{

        public NotModifiedDataAccessException(String msg) {
            super(msg);
        }
    }
}
