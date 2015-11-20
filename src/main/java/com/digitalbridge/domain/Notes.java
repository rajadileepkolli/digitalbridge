package com.digitalbridge.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p> Notes class. </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
@Document(collection = "notes")
public class Notes {

    @Id private String id;

    private String note;

    private Date date;

    private Integer score;

    /**
     * <p>
     * Constructor for Notes.
     * </p>
     */
    public Notes() {

    }

    /**
     * <p>
     * Constructor for Notes.
     * </p>
     *
     * @param note a {@link java.lang.String} object.
     * @param date a {@link java.util.Date} object.
     * @param score a {@link java.lang.Integer} object.
     */
    public Notes(String note, Date date, Integer score) {
        this.note = note;
        this.date = date;
        this.score = score;
    }

    /**
     * <p>
     * Constructor for Notes.
     * </p>
     *
     * @param id a {@link java.lang.String} object.
     */
    public Notes(String id) {
        this.id = id;
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
     * Getter for the field <code>note</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNote() {
        return note;
    }

    /**
     * <p>
     * Setter for the field <code>note</code>.
     * </p>
     *
     * @param note a {@link java.lang.String} object.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * <p>
     * Getter for the field <code>date</code>.
     * </p>
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getDate() {
        return date;
    }

    /**
     * <p>
     * Setter for the field <code>date</code>.
     * </p>
     *
     * @param date a {@link java.util.Date} object.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>
     * Getter for the field <code>score</code>.
     * </p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * <p>
     * Setter for the field <code>score</code>.
     * </p>
     *
     * @param score a {@link java.lang.Integer} object.
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
