package com.soa.vie.takaful.batch;

import javax.sql.DataSource;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.util.Constants;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MensuelCustomReader extends JdbcCursorItemReader<Cotisation> {

    public MensuelCustomReader(@Autowired DataSource dataSource) {
        setDataSource(dataSource);
        setSql(Constants.SQLREQUESTMENSEULCOTISATION);
        setRowMapper(new CotisationRowMapper());
    }

}
