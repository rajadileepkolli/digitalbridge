package com.digitalbridge.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p> User class. </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@Document(collection = "users")
public class User {

  @Id private String id;

  @Indexed(unique = true,direction = IndexDirection.ASCENDING) private String userName;

  private String password;

  private List<GrantedAuthority> roles;

  /**
   * <p>
   * Constructor for User.
   * </p>
   *
   * @param userName a {@link java.lang.String} object.
   * @param password a {@link java.lang.String} object.
   * @param roles a {@link java.util.List} object.
   * @param id a {@link java.lang.String} object.
   */
  public User(String id, String userName, String password, List<GrantedAuthority> roles) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.roles = roles;
  }

  /**
   * <p>
   * Constructor for User.
   * </p>
   */
  public User() {
    
  }
  
  /**
   * <p>Constructor for User.</p>
   *
   * @param userName a {@link java.lang.String} object.
   * @param password a {@link java.lang.String} object.
   * @param roles a {@link java.util.List} object.
   */
  public User(String userName, String password, List<GrantedAuthority> roles) {
    this.userName = userName;
    this.password = password;
    this.roles = roles;
  }

  /**
   * <p>Getter for the field <code>id</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getId() {
    return id;
  }

  /**
   * <p>Setter for the field <code>id</code>.</p>
   *
   * @param id a {@link java.lang.String} object.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>
   * Setter for the field <code>userName</code>.
   * </p>
   *
   * @param userName a {@link java.lang.String} object.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * <p>
   * Getter for the field <code>userName</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * <p>
   * Setter for the field <code>password</code>.
   * </p>
   *
   * @param password a {@link java.lang.String} object.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * <p>
   * Getter for the field <code>password</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPassword() {
    return password;
  }

  /**
   * <p>
   * Setter for the field <code>roles</code>.
   * </p>
   *
   * @param roles a {@link java.util.List} object.
   */
  public void setRoles(List<GrantedAuthority> roles) {
    this.roles = roles;
  }

  /**
   * <p>
   * Getter for the field <code>roles</code>.
   * </p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<GrantedAuthority> getRoles() {
    return roles;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }



}
