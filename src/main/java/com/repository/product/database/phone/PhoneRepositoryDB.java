package com.repository.product.database.phone;

import com.config.JDBCConfig;
import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.repository.product.ProductRepository;
import com.service.annotation.AnnotationService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneRepositoryDB implements ProductRepository<Phone> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneRepositoryDB.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static PhoneRepositoryDB instance;

    public static PhoneRepositoryDB getInstance() {
        if (instance == null) {
            instance = new PhoneRepositoryDB();
        }
        return instance;
    }

    @Override
    public void save(Phone phone) {
        String sql = "INSERT INTO \"public\".\"Phone\" (id, model, manufacturer) VALUES (?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, phone);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Phone> phones) {
        String sql = "INSERT INTO \"public\".\"Phone\" (id, model, manufacturer) VALUES (?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Phone phone : phones) {
                setObjectFields(statement, phone);
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
    private void setObjectFields(final PreparedStatement statement, final Phone phone) {
        statement.setString(1, phone.getId());
        statement.setString(2, phone.getModel());
        statement.setString(3, phone.getPhoneManufacturer().name());
    }

    @Override
    public boolean update(Phone phone) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM \"public\".\"Phone\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Phone> getAll() {
        final List<Phone> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"public\".\"Phone\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private Phone setFieldsToObject(final ResultSet resultSet) {
        final String model = resultSet.getString("model");
        final PhoneManufacturer manufacturer = EnumUtils.getEnum(PhoneManufacturer.class, resultSet.getString("manufacturer"),
                PhoneManufacturer.NONE);
        final Phone phone = new Phone("", 0, 0.0, model, manufacturer);
        phone.setId(resultSet.getString("id"));
        return phone;
    }

    @Override
    public Optional<Phone> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Phone\" WHERE id = ?";
        Optional<Phone> phone = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                phone = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phone;
    }
}
