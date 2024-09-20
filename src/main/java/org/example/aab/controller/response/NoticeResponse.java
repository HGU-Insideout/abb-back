package org.example.aab.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.aab.domain.Notice;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse extends ApiResponse {

    private String title;
    private String content;

    // Notice 객체를 받아서 변환하는 생성자 추가
    public NoticeResponse(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
    }
}
