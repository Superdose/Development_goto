package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice, Integer> {

  @Query("from Notice n where current_date between n.noticBegDt and n.noticEndDt")
  List<Notice> findAllActiveNotices();

}
