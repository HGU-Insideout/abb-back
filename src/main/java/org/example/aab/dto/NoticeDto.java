package org.example.aab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.aab.controller.form.NoticeForm;
import org.example.aab.domain.Notice;

@Getter
@Setter
@Builder
public class NoticeDto {

    private String title;
    private String content;


    public static NoticeDto from(NoticeForm noticeForm){
        return NoticeDto.builder()
                .title(noticeForm.getTitle())
                .content(noticeForm.getContent())
                .build();

    }
}
