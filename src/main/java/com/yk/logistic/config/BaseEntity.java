package com.yk.logistic.config;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    protected Status status= Status.valueOf(Status.ACTIVE.toString());


    public enum Status {
        ACTIVE,
        DELETE
    }

    public void updateStatus(Status status){
        this.status=status;
    }
}