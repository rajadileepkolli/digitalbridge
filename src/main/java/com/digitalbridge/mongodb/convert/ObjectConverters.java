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

public class ObjectConverters {

	public static List<Converter<?, ?>> getConvertersToRegister() {
		List<Converter<?, ?>> list = new ArrayList<Converter<?, ?>>();
		list.add(DbObjectToUserConverter.INSTANCE);
		return list;
	}

	@ReadingConverter
	public enum DbObjectToUserConverter implements Converter<DBObject, User> {
		INSTANCE;

		@SuppressWarnings("unchecked")
		@Override
		public User convert(DBObject source) {

			List<BasicDBObject> db0 = (List<BasicDBObject>) source.get("roles");
			List<GrantedAuthority> authorities = new ArrayList<>(
					db0.size());

			for (BasicDBObject role : db0) {
				authorities.add(new SimpleGrantedAuthority(role.getString("role")));
			}

			return new User(source.get("_id").toString(),
					source.get("userName").toString(), source.get("password").toString(),
					authorities);
		}
	}
}
