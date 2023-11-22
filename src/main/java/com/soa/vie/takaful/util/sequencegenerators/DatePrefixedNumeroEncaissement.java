package com.soa.vie.takaful.util.sequencegenerators;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class DatePrefixedNumeroEncaissement extends SequenceStyleGenerator {

    public static final String DATE_FORMAT_PARAMETERS = "dateFormat";
    public static final String DATE_FORMAT_DEFAULTS = "%tY";

    public static final String NUMBER_FORMAT_PARAMETERS = "number";
    public static final String NUMBER_FORMAT_DEFAULTS = "%05d";

    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return String.format(format, LocalDate.now(), super.generate(session, object));
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);

        String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETERS, params, DATE_FORMAT_DEFAULTS)
                .replace("%", "%1");

        String number = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETERS, params, NUMBER_FORMAT_DEFAULTS)
                .replace("%", "%");

        this.format = dateFormat + number;
    }

}
