package com.yk.logistic.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {
    private String email;
    private String password;
    private String name; // name 필드 추가
}