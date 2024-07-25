package com.judaocva.inventariocore.repository;

import com.judaocva.inventariocore.dto.UserDto;
import com.judaocva.inventariocore.miscellaneous.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository {

    private final DataSource dataSource;

    @Autowired
    public UsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createUser(UserDto userRequestDto) {
        String sql = "INSERT INTO users (name, email, password, phone, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userRequestDto.getName());
            preparedStatement.setString(2, userRequestDto.getEmail());
            preparedStatement.setString(3, userRequestDto.getPassword());
            preparedStatement.setString(4, userRequestDto.getPhone());
            preparedStatement.setInt(5, Constants.STATUS_ACTIVE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(UserDto userRequestDto) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<Object> params = new ArrayList<>();

        if (userRequestDto.getName() != null) {
            sql.append("name = ?, ");
            params.add(userRequestDto.getName());
        }
        if (userRequestDto.getEmail() != null) {
            sql.append("email = ?, ");
            params.add(userRequestDto.getEmail());
        }
        if (userRequestDto.getPassword() != null) {
            sql.append("password = ?, ");
            params.add(userRequestDto.getPassword());
        }
        if (userRequestDto.getPhone() != null) {
            sql.append("phone = ?, ");
            params.add(userRequestDto.getPhone());
        }
        if (userRequestDto.getStatus() != 0) {
            sql.append("status = ?, ");
            params.add(userRequestDto.getStatus());
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        params.add(userRequestDto.getId());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPasswordByEmail(String email) {
        String sql = "SELECT password FROM users WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                } else {
                    return ("User not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDto getUserByEmail(String email) {
        String sql = "SELECT id,name,email,password,phone,status FROM users WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserDto userDto = new UserDto();
                    userDto.setId(resultSet.getInt("id"));
                    userDto.setName(resultSet.getString("name"));
                    userDto.setEmail(resultSet.getString("email"));
                    userDto.setPassword(resultSet.getString("password"));
                    userDto.setPhone(resultSet.getString("phone"));
                    userDto.setStatus(resultSet.getInt("status"));
                    return userDto;
                } else {
                    throw new RuntimeException("User not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}