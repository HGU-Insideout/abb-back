package org.example.aab.controller.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.aab.domain.Notice;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeListResponse extends ApiResponse {

    private List<Notice> notices;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public NoticeListResponse(List<Notice> notices, int totalPages, int currentPage, int pageSize) {
        this.notices = notices;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    // Getters and Setters
}
