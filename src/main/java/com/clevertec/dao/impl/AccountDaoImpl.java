package com.clevertec.dao.impl;

import com.clevertec.dao.AccountDao;
import com.clevertec.domain.entity.Account;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.clevertec.dbo.HikariCPDataSource.getConnection;

public class AccountDaoImpl implements AccountDao {
    private static final String INSERT_SQL = "INSERT INTO app.account (dt_create,dt_update, iban, currency, bankId, userId, amount) values (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_BY_ID_SQL = "Select * from app.account where id=?;";
    private static final String SELECT_BY_USER_SQL = "Select account.id, account.dt_create, account.iban, account.currency, account.amount, account.bank, account.user from app.account" +
            "JOIN app.user ON account.user=user.id WHERE user=? ;";
    private static final String SELECT_BY_BANK_SQL = "Select account.id, account.dt_create, account.iban, account.currency, account.amount, account.bank, account.user from app.account" +
            "JOIN app.bank ON account.bank=bank.id WHERE bank=? ;";

    public AccountDaoImpl(HikariDataSource instance) {
    }


    @Override
    public Account create(Account acc) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            s.setObject(1,
                    acc.getDtCreate());
            s.setString(2, acc.getIban());
            s.setString(3, acc.getCurrency());
            s.setLong(4, acc.getBankId());
            s.setLong(5, acc.getUserId());
            s.setDouble(6, acc.getBalance());
            s.setObject(7, acc.getDtUpdate());
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
    public Account get(long id) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(SELECT_BY_ID_SQL);
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getLong("id"));
                acc.setIban(rs.getString("iban"));
                acc.setCurrency(rs.getString("currency"));
                acc.setBankId(rs.getLong("bank"));
                acc.setUserId(rs.getLong("user"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                acc.setDtUpdate(rs.getTimestamp("dt_update").toLocalDateTime());
                return acc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Account> userAccounts(long userId) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(SELECT_BY_USER_SQL);
            s.setLong(1, userId);
            List<Account> list = new ArrayList<>();
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getLong("id"));
                acc.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                acc.setIban(rs.getString("iban"));
                acc.setCurrency(rs.getString("currency"));
                acc.setBankId(rs.getLong("bank"));
                acc.setBalance(rs.getDouble("balance"));
                list.add(acc);
            }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
            }


    @Override
    public List<Account> bankAccounts(long bankId) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(SELECT_BY_BANK_SQL);
            s.setLong(1, bankId);
            List<Account> list = new ArrayList<>();
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getLong("id"));
                acc.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                acc.setIban(rs.getString("iban"));
                acc.setCurrency(rs.getString("currency"));
                acc.setUserId(rs.getLong("user"));
                acc.setBalance(rs.getDouble("balance"));
                list.add(acc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
