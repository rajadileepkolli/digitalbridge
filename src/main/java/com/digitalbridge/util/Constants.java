package com.digitalbridge.util;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Constants class.
 * </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
public final class Constants {
  /*Mongo Configuration*/
  /** Constant <code>LOCALHOST="localhost"</code> */
  public static final String LOCALHOST = "localhost";
  /** Constant <code>PRIMARYPORT=27017</code> */
  public static final int PRIMARYPORT = 27017;
  /** Constant <code>SECONDARYPORT=27018</code> */
  public static final int SECONDARYPORT = 27018;
  /** Constant <code>TERITORYPORT=27019</code> */
  public static final int TERITORYPORT = 27019;
  /** Constant <code>ARBITERPORT=27020</code> */
  public static final int ARBITERPORT = 27020;

  /*Pagination*/
  /** Constant <code>PAGESIZE=10</code> */
  public static final int PAGESIZE = 10;
  /** Constant <code>ZERO=0</code> */
  public static final int ZERO = 0;
  /** Constant <code>ONE=1</code> */
  public static final int ONE = 1;
  /** Constant <code>TWO=0</code> */
  public static final int TWO = 0;
  /** Constant <code>THREE=3</code> */
  public static final int THREE = 3;
  /** Constant <code>TWELVE=12</code> */
  public static final int TWELVE = 12;

  /*Security Configuration*/
  /** Constant <code>MAXAGE=31536000</code> */
  public static final long MAXAGE = 31536000;

  /*JestClient*/
  /** Constant <code>CONNTIMEOUT=150</code> */
  public static final int CONNTIMEOUT = 150;
  /** Constant <code>READTIMEOUT=300</code> */
  public static final int READTIMEOUT = 300;
  /** Constant <code>MAXTOTALCONNECTION=20</code> */
  public static final int MAXTOTALCONNECTION = 20;
  /** Constant <code>INDEXMISSINGCODE=404</code> */
  public static final int INDEXMISSINGCODE = 404;
  /** Constant <code>CLUSTERBLOCKEXCEPTIONCODE=503</code> */
  public static final int CLUSTERBLOCKEXCEPTIONCODE = 503;
  /** Constant <code>DATEFIELDLIST</code> */
  public static final List<String> DATEFIELDLIST = Arrays.asList("lDate");

  private Constants() {

  }
}
