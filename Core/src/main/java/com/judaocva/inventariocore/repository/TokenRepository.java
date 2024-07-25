package com.judaocva.inventariocore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TokenRepository {

    private final DataSource dataSource;

    @Autowired
    public TokenRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveOrUpdateToken(int idUser, String token) {
        String sql = "UPDATE tokens SET token = ? WHERE id_user = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, idUser);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                String insertSql = "INSERT INTO tokens (id_user, token) VALUES (?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    insertStatement.setInt(1, idUser);
                    insertStatement.setString(2, token);
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
