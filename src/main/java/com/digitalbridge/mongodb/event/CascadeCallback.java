package com.digitalbridge.mongodb.event;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.digitalbridge.annotation.CascadeSave;
import com.digitalbridge.annotation.CascadeSaveList;

/**
 * <p>
 * CascadeCallback class.
 * </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class CascadeCallback implements FieldCallback {

	private Object source;
	private MongoOperations mongoOperations;

	/**
	 * <p>
	 * Constructor for CascadeCallback.
	 * </p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param mongoOperations a
	 * {@link org.springframework.data.mongodb.core.MongoOperations} object.
	 */
	public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
		this.source = source;
		this.setMongoOperations(mongoOperations);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void doWith(Field field)
			throws IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class)
				&& field.isAnnotationPresent(CascadeSave.class)) {
			final Object fieldValue = field.get(getSource());
			checkAndCreateIDIfNotExists(fieldValue);
		}
		else if (field.isAnnotationPresent(DBRef.class)
				&& field.isAnnotationPresent(CascadeSaveList.class)) {
			final List<Object> fieldValueList = (List<Object>) field.get(getSource());
			if (fieldValueList != null && !fieldValueList.isEmpty()) {
				for (Object fieldValue : fieldValueList) {
					checkAndCreateIDIfNotExists(fieldValue);
				}
			}
		}
	}

	/**
	 * @param fieldValue
	 */
	private void checkAndCreateIDIfNotExists(final Object fieldValue) {
		if (fieldValue != null) {
			DbRefFieldCallback callback = new DbRefFieldCallback();

			ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

			if (!callback.isIdFound()) {
				throw new MappingException(
						"Cannot perform cascade save on child object without id set");
			}

			getMongoOperations().save(fieldValue);
		}
	}

	/**
	 * <p>
	 * Getter for the field <code>source</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * <p>
	 * Setter for the field <code>source</code>.
	 * </p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 */
	public void setSource(Object source) {
		this.source = source;
	}

	/**
	 * <p>
	 * Getter for the field <code>mongoOperations</code>.
	 * </p>
	 *
	 * @return a {@link org.springframework.data.mongodb.core.MongoOperations} object.
	 */
	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	/**
	 * <p>
	 * Setter for the field <code>mongoOperations</code>.
	 * </p>
	 *
	 * @param mongoOperations a
	 * {@link org.springframework.data.mongodb.core.MongoOperations} object.
	 */
	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

}
