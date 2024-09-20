package org.example.aab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.example.aab.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.aab.domain.Notice;
import org.example.aab.dto.NoticeDto;
import org.example.aab.repository.NoticeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long createNotice(NoticeDto noticeDto){
        Notice notice = noticeRepository.save(Notice.toNotice(noticeDto));
        return notice.getId();
    }

    public Page<Notice> readPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);  // 페이지 번호와 10개씩 페이징
        return noticeRepository.findAll(pageable);  // Page 객체 반환
    }

    public Notice readNotice(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

    public void updateNotice(Long noticeId, NoticeDto noticeDto){
        Notice notice = noticeRepository.findById(noticeId)
                                        .orElseThrow(NoticeNotFoundException::new);
        notice.update(noticeDto);
        noticeRepository.save(notice);
    }

    public void deleteNotice(Long noticeId){
        noticeRepository.deleteById(noticeId);
    }



}
