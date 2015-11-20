package com.digitalbridge.mongodb.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.digitalbridge.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * <p>
 * ObjectConverters class.
 * </p>
 *
 * @author rajakolli
 * @version 1:0
 */
public class ObjectConverters {
  /**
   * <p>
   * getConvertersToRegister.
   * </p>
   *
   * @return a {@link java.util.List} object.
   */
  public static List<Converter<?, ?>> getConvertersToRegister() {
    List<Converter<?, ?>> list = new ArrayList<Converter<?, ?>>();
    list.add(DbObjectToUserConverter.INSTANCE);
    // list.add(UserToDbObjectConverter.INSTANCE);
    return list;
  }

  @ReadingConverter
  public static enum DbObjectToUserConverter implements Converter<DBObject, User> {
    INSTANCE;

    @SuppressWarnings("unchecked")
    @Override
    public User convert(DBObject source) {

      if (source == null) {
        return null;
      }

      List<BasicDBObject> db0 = (List<BasicDBObject>) source.get("roles");
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(db0.size());

      for (BasicDBObject role : db0) {
        authorities.add(new SimpleGrantedAuthority(role.getString("role")));
      }

      return new User(source.get("_id").toString(), source.get("userName").toString(),
          source.get("password").toString(), authorities);
    }
  }
}
