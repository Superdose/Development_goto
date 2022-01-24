package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Notice;
import com.eazybytes.springsecuritysection2.repository.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticesController {

  private final NoticeRepository noticeRepository;

  @GetMapping("/notices")
  public List<Notice> getNotices() {
    return this.noticeRepository.findAllActiveNotices();
  }

}
