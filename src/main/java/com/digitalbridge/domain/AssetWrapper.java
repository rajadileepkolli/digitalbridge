package com.digitalbridge.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import com.digitalbridge.annotation.CascadeSave;
import com.digitalbridge.annotation.CascadeSaveList;

/**
 * <p> AssetWrapper class. </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@Document(collection = "assetwrapper")
public class AssetWrapper {

	@Id private String id;

	@NotNull private String orgAssetId;

	@Field("aName") @Indexed(direction = IndexDirection.ASCENDING) @NotNull @Size(min = 2) private String assetName;

	@NotNull private String borough;

	@NotNull private String cuisine;

	@CascadeSave @DBRef private Address address;

	@CascadeSaveList @DBRef private List<Notes> notes;

	@Version private Long version;

	@CreatedBy private String createdBy;

	@Temporal(TemporalType.TIMESTAMP) @DateTimeFormat(style = "M-") @CreatedDate private Date createdDate;

	@LastModifiedBy private String lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP) @DateTimeFormat(
			style = "M-") @LastModifiedDate @Field("lDate") private Date lastmodifiedDate;

	/**
	 * <p> Getter for the field <code>id</code>. </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>
	 * Setter for the field <code>id</code>.
	 * </p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for the field <code>orginalAssetId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getOrgAssetId() {
		return orgAssetId;
	}

	/**
	 * <p>
	 * Setter for the field <code>orginalAssetId</code>.
	 * </p>
	 *
	 * @param orgAssetId a {@link java.lang.String} object.
	 */
	public void setOrgAssetId(String orgAssetId) {
		this.orgAssetId = orgAssetId;
	}

	/**
	 * <p>
	 * Getter for the field <code>assetName</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * <p>
	 * Setter for the field <code>assetName</code>.
	 * </p>
	 *
	 * @param assetName a {@link java.lang.String} object.
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	/**
	 * <p>
	 * Getter for the field <code>borough</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getBorough() {
		return borough;
	}

	/**
	 * <p>
	 * Setter for the field <code>borough</code>.
	 * </p>
	 *
	 * @param borough a {@link java.lang.String} object.
	 */
	public void setBorough(String borough) {
		this.borough = borough;
	}

	/**
	 * <p>
	 * Getter for the field <code>cuisine</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCuisine() {
		return cuisine;
	}

	/**
	 * <p>
	 * Setter for the field <code>cuisine</code>.
	 * </p>
	 *
	 * @param cuisine a {@link java.lang.String} object.
	 */
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	/**
	 * <p>
	 * Getter for the field <code>address</code>.
	 * </p>
	 *
	 * @return a {@link com.digitalbridge.domain.Address} object.
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * <p>
	 * Setter for the field <code>address</code>.
	 * </p>
	 *
	 * @param address a {@link com.digitalbridge.domain.Address} object.
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * <p>
	 * Getter for the field <code>notes</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Notes> getNotes() {
		return notes;
	}

	/**
	 * <p>
	 * Setter for the field <code>notes</code>.
	 * </p>
	 *
	 * @param notes a {@link java.util.List} object.
	 */
	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	/**
	 * <p>Getter for the field <code>version</code>.</p>
	 *
	 * @return a {@link java.lang.Long} object.
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * <p>Setter for the field <code>version</code>.</p>
	 *
	 * @param version a {@link java.lang.Long} object.
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * <p>Getter for the field <code>createdBy</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * <p>Setter for the field <code>createdBy</code>.</p>
	 *
	 * @param createdBy a {@link java.lang.String} object.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * <p>Getter for the field <code>createdDate</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * <p>Setter for the field <code>createdDate</code>.</p>
	 *
	 * @param createdDate a {@link java.util.Date} object.
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * <p>Getter for the field <code>lastModifiedBy</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * <p>Setter for the field <code>lastModifiedBy</code>.</p>
	 *
	 * @param lastModifiedBy a {@link java.lang.String} object.
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * <p>Getter for the field <code>lastmodifiedDate</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * <p>Setter for the field <code>lastmodifiedDate</code>.</p>
	 *
	 * @param lastmodifiedDate a {@link java.util.Date} object.
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
