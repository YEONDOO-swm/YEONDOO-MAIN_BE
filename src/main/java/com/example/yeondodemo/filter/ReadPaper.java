package com.example.yeondodemo.filter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

/**
 * 이 메서드는 Paper를 읽을때 달아야 하는 Annotation입니다.
 * 해당 Annotation 있으면 RecentlyPapeRepository에 기록이 저장됩니다.
 */
public @interface ReadPaper {
}
