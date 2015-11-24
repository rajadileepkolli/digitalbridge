package com.digitalbridge.mongodb.event;

import java.lang.reflect.Field;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * DbRefFieldCallback class.
 * </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

	private boolean idFound;

	/** {@inheritDoc} */
	public void doWith(Field field) throws IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(Id.class)) {
			idFound = true;
		}
	}

	/**
	 * <p>
	 * isIdFound.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isIdFound() {
		return idFound;
	}

}
