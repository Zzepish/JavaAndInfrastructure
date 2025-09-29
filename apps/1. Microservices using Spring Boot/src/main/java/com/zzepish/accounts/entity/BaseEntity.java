package com.zzepish.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Column(updatable = false)
    protected String createdBy;

    @Column(insertable = false)
    protected LocalDateTime updatedAt;

    @Column(insertable = false)
    protected String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = "Admin";
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "Admin";
    }
}
