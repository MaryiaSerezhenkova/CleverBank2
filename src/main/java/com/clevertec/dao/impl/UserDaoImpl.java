package com.clevertec.dao.impl;

import com.clevertec.dao.UserDao;
import com.clevertec.domain.entity.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.LocalDateTime;

import static com.clevertec.dbo.HikariCPDataSource.getConnection;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_SQL = "INSERT INTO app.user (dt_create, dt_update, name) values (?, ?, ?);";

    	private static final String SELECT_BY_ID_SQL = "Select * from app.user where id=?;";

    	private static final String UPDATE_SQL = "UPDATE app.user\r\n"
    			+ "	SET dt_update=?, name=?\r\n"
    			+ "\tWHERE id = ? and dt_update = ?;";

    	private static final String DELETE_SQL = "DELETE FROM app.user WHERE id = ? and dt_update = ?;";

    public UserDaoImpl(HikariDataSource instance) {
    }


    @Override
    public User create(User user) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            s.setObject(1,
                    user.getDtCreate());
            s.setObject(2, user.getDtUpdate());
            s.setString(3, user.getName());
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
    public User update(long id, LocalDateTime dtUpdate, User user) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS);
            s.setObject(1, user.getDtUpdate());
            s.setString(2, user.getName());
            s.setLong(3, id);
            s.setObject(4, dtUpdate);

            int countUpdatedRows = s.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли обновить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Обновили более одной записи");
                }
            }

            return get(id);
        } catch (SQLException e) {
            throw new RuntimeException("При сохранении данных произошла ошибка", e);
        }
    }

    @Override
    public User get(long id) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(SELECT_BY_ID_SQL);
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDtCreate(rs.getTimestamp("dt_create").toLocalDateTime());
                user.setDtUpdate(rs.getTimestamp("dt_update").toLocalDateTime());
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) {
        try {
            Connection o = getConnection();
            PreparedStatement s = o.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS);
            s.setLong(1, id);
            s.setObject(2, dtUpdate);

            int countUpdatedRows = s.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли удалить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Удалили более одной записи");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("При сохранении данных произошла ошибка", e);
        }
    }

}
