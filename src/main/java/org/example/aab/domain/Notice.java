package org.example.aab.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.aab.dto.NoticeDto;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends Basetime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public static Notice toNotice(NoticeDto noticeDto){
        return Notice.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .build();

    }

    public void update(NoticeDto noticeDto){
        this.title = noticeDto.getTitle();
        this.content = noticeDto.getContent();
    }

}
