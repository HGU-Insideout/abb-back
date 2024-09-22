package org.example.aab.controller.restcontroller;

import lombok.RequiredArgsConstructor;
import org.example.aab.controller.form.NoticeForm;
import org.example.aab.controller.response.ApiResponse;
import org.example.aab.controller.response.NoticeIdResponse;
import org.example.aab.controller.response.NoticeListResponse;
import org.example.aab.controller.response.NoticeResponse;
import org.example.aab.domain.Notice;
import org.example.aab.dto.NoticeDto;
import org.example.aab.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class NoticeRestController {
    private final NoticeService noticeService;

    @PostMapping("/notice")
    public ResponseEntity<ApiResponse> createNotice(@RequestBody NoticeForm noticeForm) {
        Long noticeId = noticeService.createNotice(NoticeDto.from(noticeForm));
        ApiResponse response = new NoticeIdResponse(noticeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice")
    public ResponseEntity<ApiResponse> readPageableNotice(@RequestParam(defaultValue = "0") int page) {
        Page<Notice> noticePage = noticeService.readPageable(page);
        List<Notice> noticeList = noticePage.getContent();
        ApiResponse response = new NoticeListResponse(noticeList, noticePage.getTotalPages(), noticePage.getNumber(), noticePage.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> readNotice(@PathVariable Long noticeId){
        Notice notice = noticeService.readNotice(noticeId);
        ApiResponse response = new NoticeResponse(notice);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        ApiResponse response = new NoticeIdResponse(noticeId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeForm noticeForm) {
        noticeService.updateNotice(noticeId, NoticeDto.from(noticeForm));
        ApiResponse response = new NoticeIdResponse(noticeId);
        return ResponseEntity.ok(response);

    }

}



