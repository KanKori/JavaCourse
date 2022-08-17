package com.repository.invoice.database;

import com.config.JDBCConfig;
import com.model.invoice.Invoice;
import com.model.product.Product;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceRepositoryDB {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryDB.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static InvoiceRepositoryDB instance;

    public static InvoiceRepositoryDB getInstance() {
        if (instance == null) {
            instance = new InvoiceRepositoryDB();
        }
        return instance;
    }

    public void save(Invoice invoice) {
        String sql = "INSERT INTO \"public\".\"Invoice\" (id, sum, products, time) VALUES (?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, invoice);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<Invoice> invoices) {
        String sql = "INSERT INTO \"public\".\"Invoice\" (id, sum, products, time) VALUES (?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Invoice invoice : invoices) {
                setObjectFields(statement, invoice);
                statement.addBatch();
            }
            statement.executeBatch();
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Invoice invoice) {
        statement.setString(1, invoice.getId());
        statement.setString(2, String.valueOf(invoice.getSum()));
        statement.setString(3, String.valueOf(invoice.getProducts()));
        statement.setString(4, String.valueOf(invoice.getTime()));
    }

    public boolean update(Invoice invoice) {
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM \"public\".\"Invoice\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public List<Invoice> getAll() {
        final List<Invoice> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"public\".\"Invoice\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return result;
    }

    @SneakyThrows
    private Invoice setFieldsToObject(final ResultSet resultSet) {
        final List<Product> products = new ArrayList<>(0);
        final LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        final Invoice invoice = new Invoice("", 0, products, time);
        invoice.setId(resultSet.getString("id"));
        return invoice;
    }

    public Optional<Invoice> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Invoice\" WHERE id = ?";
        Optional<Invoice> invoice = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                invoice = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return invoice;
    }
}
