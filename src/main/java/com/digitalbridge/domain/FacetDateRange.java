package com.digitalbridge.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * <p>FacetDateRange class.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
public class FacetDateRange {

  private DateTime startDate;
  private DateTime endDate;

  /**
   * <p>Getter for the field <code>startDate</code>.</p>
   *
   * @return a org.elasticsearch.common.joda.time.DateTime object.
   */
  public DateTime getStartDate() {
    return startDate;
  }

  /**
   * <p>Setter for the field <code>startDate</code>.</p>
   *
   * @param startDate a org.elasticsearch.common.joda.time.DateTime object.
   */
  public void setStartDate(DateTime startDate) {
    this.startDate = startDate;
  }

  /**
   * <p>Getter for the field <code>endDate</code>.</p>
   *
   * @return a org.elasticsearch.common.joda.time.DateTime object.
   */
  public DateTime getEndDate() {
    return endDate;
  }

  /**
   * <p>Setter for the field <code>endDate</code>.</p>
   *
   * @param endDate a org.elasticsearch.common.joda.time.DateTime object.
   */
  public void setEndDate(DateTime endDate) {
    this.endDate = endDate;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
