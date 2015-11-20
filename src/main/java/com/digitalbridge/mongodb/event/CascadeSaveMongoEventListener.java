package com.digitalbridge.mongodb.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

/**
 * <p>CascadeSaveMongoEventListener class.</p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

	@Autowired private MongoOperations mongoOperations;

	/** {@inheritDoc} */
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		ReflectionUtils.doWithFields(event.getSource().getClass(), new CascadeCallback(event.getSource(), mongoOperations));
	}
	
}
