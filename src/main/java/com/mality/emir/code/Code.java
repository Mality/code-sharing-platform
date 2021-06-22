package com.mality.emir.code;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @Column(columnDefinition="TEXT")
    private String code;

    private String dateTime;

    private String uuid;

    private int timeLeft;
    private int viewsLeft;

    private boolean restrictedViews;
    private boolean restrictedTime;

    public Code(String code, String dateTime, int timeLeft, int viewsLeft) {
        this.code = code;
        this.dateTime = dateTime;
        this.uuid = UUID.randomUUID().toString();
        this.timeLeft = timeLeft;
        this.viewsLeft = viewsLeft;
        if (viewsLeft <= 0) {
            restrictedViews = false;
        } else {
            restrictedViews = true;
        }
        if (timeLeft <= 0) {
            restrictedTime = false;
        } else {
            restrictedTime = true;
        }
    }

    public Code(String code, LocalDateTime localDateTime, int timeLeft, int viewsLeft) {
        this.code = code;
        String dateTimePattern = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        this.dateTime = localDateTime.format(formatter);
        this.uuid = UUID.randomUUID().toString();
        this.timeLeft = timeLeft;
        this.viewsLeft = viewsLeft;
        if (viewsLeft <= 0) {
            restrictedViews = false;
        } else {
            restrictedViews = true;
        }
        if (timeLeft <= 0) {
            restrictedTime = false;
        } else {
            restrictedTime = true;
        }
    }

    public Code(String code, LocalDateTime localDateTime) {
        this.code = code;
        String dateTimePattern = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        this.dateTime = localDateTime.format(formatter);
    }

    public Code(String code, String dateTime) {
        this.code = code;
        this.dateTime = dateTime;
    }

    public Code(String code) {
        this.code = code;
    }

    public Code() {
    }

    public boolean isRestricted() {
        return restrictedTime || restrictedViews;
    }

    public boolean isExpired() {
        return (restrictedViews && viewsLeft <= 0) || (restrictedTime && getTimeLeft() <= 0);
    }

    public boolean isRestrictedTime() {
        return restrictedTime;
    }

    public void setRestrictedTime(boolean restrictedTime) {
        this.restrictedTime = restrictedTime;
    }

    public boolean isRestrictedViews() {
        return restrictedViews;
    }

    public void setRestrictedViews(boolean restrictedViews) {
        this.restrictedViews = restrictedViews;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTimeLeft() {
        String dateTimePattern = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        long seconds = LocalDateTime.parse(dateTime, formatter).until(LocalDateTime.now(), ChronoUnit.SECONDS);
        return Math.max(0, timeLeft - ((int) seconds));
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = Math.max(0, timeLeft);
    }

    public int getViewsLeft() {
        return viewsLeft;
    }

    public void setViewsLeft(int viewsLeft) {
        this.viewsLeft = Math.max(0, viewsLeft);
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Code{" +
                "codeId=" + codeId +
                ", code='" + code + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", uuid='" + uuid + '\'' +
                ", timeLeft=" + timeLeft +
                ", viewsLeft=" + viewsLeft +
                '}';
    }
}
