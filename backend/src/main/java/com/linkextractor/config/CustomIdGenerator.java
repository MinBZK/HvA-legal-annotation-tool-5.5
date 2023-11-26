package com.linkextractor.config;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.linkextractor.models.Rechtsbetrekking;
import com.linkextractor.models.Rechtsfeit;
import com.linkextractor.models.Rechtssubject;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object){
        String prefix = "";
        String query = "";
        JdbcConnectionAccess connection = session.getJdbcConnectionAccess();

        if (object instanceof Rechtsfeit) {
            prefix = "RF";
            query = "select count(rf_code) as Id from Rechtsfeit";
        } else if (object instanceof Rechtsbetrekking) {
            prefix = "RB";
            query = "select count(rb_code) as Id from Rechtsbetrekking";
        } else if(object instanceof Rechtssubject){
            prefix = "RS";
            query = "select count(rs_code) as Id from Rechtssubject";
        }

        try {
            JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
            Connection con = jdbcConnectionAccess.obtainConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int id = resultSet.getInt(1) + 101;
                String generateId = prefix + new Integer(id).toString();
                return generateId;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
