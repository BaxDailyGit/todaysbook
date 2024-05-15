package com.example.todaysbook.constant;

import java.util.regex.Pattern;

public final class Constant {
    private Constant() {}
    public static final int REVIEW_CONTENT_MAX_LENGTH = 500;

    public static final int NICKNAME_MIN_LENGTH = 2;

    public static final int NICKNAME_MAX_LENGTH = 8;

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    public static final Pattern DEFAULT_ADDRESS_PATTERN = Pattern.compile(", ");
    public static final Pattern ZIPCODE_PATTERN = Pattern.compile("^\\d{5}$");

    public static final int BOOK_TITLE_MAX_LENGTH = 200;
    public static final int AUTHOR_MAX_LENGTH = 200;
    public static final int PUBLISHER_MAX_LENGTH = 20;
    public static final int DESCRIPTION_MAX_LENGTH = 300;

    public static final int LIST_TITLE_MAX_LENGTH = 30;
    public static final int LIST_SIZE = 10;


    public static final int DEFAULT_QUANTITY = 20;
    public static final String DEFAULT_NATION = "한국";
    public static final String DEFAULT_PROMPT = "%s 기준으로 최근에 많이 팔린 책의 제목 %d개를 추천해 주세요. 신뢰할 수 있는 최신 정보를 바탕으로 정확한 책 제목만 나열하여 주세요. 존재하지 않는 책 제목은 추천하지 마세요. 답변에는 책의 저자, 출처, 참고, 이미지 등 다른 내용은 포함하지 말아주세요.";
    public static final double DEFAULT_TEMPERATURE = 0.5;

}
