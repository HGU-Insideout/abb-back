package org.example.aab.exception;


public class NoticeNotFoundException extends RuntimeException{

    private static final String NOTICE_NOT_FOUND = "일치하는 공지가 없습니다.";

    public NoticeNotFoundException(){
        super(NOTICE_NOT_FOUND);
    }
}
