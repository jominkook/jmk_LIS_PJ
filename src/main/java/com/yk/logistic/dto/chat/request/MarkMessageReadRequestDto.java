package com.yk.logistic.dto.chat.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkMessageReadRequestDto {
    private Long userId; // 읽은 사용자 ID
}
