package com.javalabs.model;

import java.time.OffsetDateTime;

public interface TrackableEntityModel extends BaseEntity {
	public Long getRevision();

	public void setRevision(Long revision);

	public OffsetDateTime getCreatedDate();

	public void setCreatedDate(OffsetDateTime createdDate);

	public OffsetDateTime getUpdatedDate();

	public void setUpdatedDate(OffsetDateTime updatedDate);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public String getUpdatedBy();

	public void setUpdatedBy(String updatedBy);
}
