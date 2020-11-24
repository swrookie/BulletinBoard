package com.swrookie.bulletinboard.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime 
{
	@CreatedDate
	@Column(updatable=false)
	private Timestamp createdDate;	// LocalDateTime during creation
	@LastModifiedDate
	private Timestamp modifiedDate;	// LocalDateTime during update
}
