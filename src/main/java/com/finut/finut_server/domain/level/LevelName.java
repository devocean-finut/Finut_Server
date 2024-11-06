package com.finut.finut_server.domain.level;

public enum LevelName {
    PARTTIMEJOB("아르바이트"),
    INTERN("인턴"),
    STAFF("사원"),
    ASSOCIATEMANAGER("대리"),
    MANAGER("과장"),
    SENIORMANAGER("차장"),
    DIRECTOR("부장");

    private final String koreanName;

    LevelName(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
