package com.linkextractor.config;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        String prefix;
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
    
}
