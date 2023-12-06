package com.linkextractor.backend.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.linkextractor.backend.models.Rechtsbetrekking;
import com.linkextractor.backend.models.Rechtsfeit;
import com.linkextractor.backend.models.Rechtssubject;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "";
        String query = "";

        if (object instanceof Rechtsfeit) {
            prefix = "RF";
            query = "select count(rf_code) as Id from rechtsfeit";
        } else if (object instanceof Rechtsbetrekking) {
            prefix = "RB";
            query = "select count(rb_code) as Id from rechtsbetrekking";
        } else if (object instanceof Rechtssubject) {
            prefix = "RS";
            query = "select count(rs_code) as Id from rechtssubject";
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
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
