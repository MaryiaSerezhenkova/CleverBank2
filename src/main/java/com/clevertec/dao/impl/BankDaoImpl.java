package com.clevertec.dao.impl;

import com.clevertec.dao.BankDao;
import com.clevertec.domain.entity.Bank;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.clevertec.dbo.HikariCPDataSource.getConnection;

public class BankDaoImpl implements BankDao {

    private static final String INSERT_SQL = "INSERT INTO app.bank (dt_create, name) values (?, ?);";

    private static final String SELECT_BY_ID_SQL = "Select * from app.bank where id=?;";

    private static final String SELECT_SQL = "Select * from app.bank;";

    public BankDaoImpl(HikariDataSource instance) {
    }

    @Override
    public Bank create(Bank bank) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            s.setObject(1,
                    bank.getDtCreate());
            s.setString(2, bank.getName());
            int updated = s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            if (rs.next()) {
                return get(rs.getLong(1));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("При сохранении данных произошла ошибка", e);
        }
    }

    @Override
    public Bank get(long id) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(SELECT_BY_ID_SQL);
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Bank bank = new Bank();
                bank.setId(rs.getLong("id"));
                bank.setName(rs.getString("name"));
                bank.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                return bank;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bank> getAll() {
        try {
            Connection o = getConnection();
            Statement s = o.createStatement();
            ResultSet rs = s.executeQuery(SELECT_SQL);
            List<Bank> list = new ArrayList<Bank>();
            while (rs.next()) {
                Bank p = new Bank();
                p.setId(rs.getLong("id"));
                p.setName(rs.getString("name"));
                p.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
