package com.digitalbridge.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Address class.</p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
@Document(collection = "address")
public class Address {

  @Id private String id;

  private String building;

  /**
   * {@code location} is stored in GeoJSON format.
   * 
   * <pre>
   * <code>
   * {
   *   "type" : "Point",
   *   "coordinates" : [ x, y ]
   * }
   * </code>
   * </pre>
   */
  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) private GeoJsonPoint location;

  private String street;

  private String zipcode;

  /**
   * <p>
   * Constructor for Address.
   * </p>
   */
  public Address() {

  }

  /**
   * <p>
   * Getter for the field <code>id</code>.
   * </p>
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
   * Getter for the field <code>building</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getBuilding() {
    return building;
  }

  /**
   * <p>
   * Setter for the field <code>building</code>.
   * </p>
   *
   * @param building a {@link java.lang.String} object.
   */
  public void setBuilding(String building) {
    this.building = building;
  }

  /**
   * <p>
   * Getter for the field <code>location</code>.
   * </p>
   *
   * @return a {@link org.springframework.data.mongodb.core.geo.GeoJsonPoint} object.
   */
  public GeoJsonPoint getLocation() {
    return location;
  }

  /**
   * <p>
   * Setter for the field <code>location</code>.
   * </p>
   *
   * @param location a {@link org.springframework.data.mongodb.core.geo.GeoJsonPoint} object.
   */
  public void setLocation(GeoJsonPoint location) {
    this.location = location;
  }

  /**
   * <p>
   * Getter for the field <code>street</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getStreet() {
    return street;
  }

  /**
   * <p>
   * Setter for the field <code>street</code>.
   * </p>
   *
   * @param street a {@link java.lang.String} object.
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * <p>
   * Getter for the field <code>zipcode</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getZipcode() {
    return zipcode;
  }

  /**
   * <p>
   * Setter for the field <code>zipcode</code>.
   * </p>
   *
   * @param zipcode a {@link java.lang.String} object.
   */
  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
