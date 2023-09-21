package com.example.springbatchdemo.domain.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public abstract class CommonEntity {
    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint default 0")
    protected Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    protected Instant updatedAt;

    public boolean isValid() { return !isDeleted; }
}
