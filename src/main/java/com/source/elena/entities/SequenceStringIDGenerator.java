package com.source.elena.entities;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class SequenceStringIDGenerator extends SequenceStyleGenerator {

	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";

	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";

	public static final String NUMBER_FORMAT_DEFAULT = "%d";

	public static final String VALUE_PREFIX_DEFAULT = "";

	private String valuePrefix;

	private String numberFormat;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		super.configure(LongType.INSTANCE, params, serviceRegistry);
		valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params, VALUE_PREFIX_DEFAULT);
		numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
	}

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		String randomCharacters = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
		String sequenceId = String.format(numberFormat, super.generate(session, object));
		return valuePrefix + sequenceId + "-" + randomCharacters;
	}
}
