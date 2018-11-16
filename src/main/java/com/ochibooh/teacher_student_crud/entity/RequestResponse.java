package com.ochibooh.teacher_student_crud.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RequestResponse {
    int responseCode;
    String message;
    Object data;
}
