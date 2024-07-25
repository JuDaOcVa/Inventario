package com.judaocva.inventariocore.repository;

import com.judaocva.inventariocore.dto.UserRequestDto;
import com.judaocva.inventariocore.miscellaneous.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class UsersRepository {

    private final DataSource dataSource;

    @Autowired
    public UsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createUser(UserRequestDto userRequestDto) {
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

    public void updateUser(UserRequestDto userRequestDto) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, phone = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userRequestDto.getName());
            preparedStatement.setString(2, userRequestDto.getEmail());
            preparedStatement.setString(3, userRequestDto.getPassword());
            preparedStatement.setString(4, userRequestDto.getPhone());
            preparedStatement.setInt(5, userRequestDto.getStatus());
            preparedStatement.setInt(6, userRequestDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
