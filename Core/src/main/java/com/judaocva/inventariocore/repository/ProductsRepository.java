package com.judaocva.inventariocore.repository;

import com.judaocva.inventariocore.dto.ProductSaveRequestDto;
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
public class ProductsRepository {

    private final DataSource dataSource;

    @Autowired
    public ProductsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createProduct(ProductSaveRequestDto productSaveRequestDto) {
        String sql = "INSERT INTO products (id_user, product_name, quantity, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productSaveRequestDto.getIdUser());
            preparedStatement.setString(2, productSaveRequestDto.getProductName());
            preparedStatement.setInt(3, productSaveRequestDto.getQuantity());
            preparedStatement.setInt(4, Constants.STATUS_ACTIVE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(ProductSaveRequestDto productSaveRequestDto) {
        StringBuilder sql = new StringBuilder("UPDATE products SET ");
        List<Object> params = new ArrayList<>();
        if (productSaveRequestDto.getProductName() != null) {
            sql.append("product_name = ?, ");
            params.add(productSaveRequestDto.getProductName());
        }

        if (productSaveRequestDto.getStatus() != 0) {
            sql.append("status = ?, ");
            params.add(productSaveRequestDto.getStatus());
        }
        sql.append("quantity = ?, ");
        params.add(productSaveRequestDto.getQuantity());

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        params.add(productSaveRequestDto.getId());

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

    public int getQuantityById(int id) {
        String sql = "SELECT quantity FROM products WHERE id = ? ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantity");
                } else {
                    throw new RuntimeException("Product not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
