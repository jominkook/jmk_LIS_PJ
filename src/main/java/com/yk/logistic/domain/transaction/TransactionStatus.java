package com.yk.logistic.domain.transaction;

public enum TransactionStatus {
    PENDING,    // 거래 대기 중
    COMPLETED,  // 거래 완료
    CANCELLED   // 거래 취소
}