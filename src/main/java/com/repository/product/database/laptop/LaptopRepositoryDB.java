package com.repository.product.database.laptop;

import com.config.JDBCConfig;
import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.repository.product.IAbstractProductRepository;
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

public class LaptopRepositoryDB implements IAbstractProductRepository<Laptop> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopRepositoryDB.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static LaptopRepositoryDB instance;

    public static LaptopRepositoryDB getInstance() {
        if (instance == null) {
            instance = new LaptopRepositoryDB();
        }
        return instance;
    }

    @Override
    public void save(Laptop laptop) {
        String sql = "INSERT INTO \"public\".\"Laptop\" (id, model, manufacturer) VALUES (?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, laptop);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Laptop> laptops) {
        String sql = "INSERT INTO \"public\".\"Laptop\" (id, model, manufacturer) VALUES (?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Laptop laptop : laptops) {
                setObjectFields(statement, laptop);
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
    private void setObjectFields(final PreparedStatement statement, final Laptop laptop) {
        statement.setString(1, laptop.getId());
        statement.setString(2, laptop.getModel());
        statement.setString(3, laptop.getLaptopManufacturer().name());
        statement.setString(4, laptop.getTitle());
        statement.setInt(5, laptop.getCount());
        statement.setDouble(6, laptop.getCount());
    }

    @Override
    public boolean update(Laptop laptop) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM \"public\".\"Laptop\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Laptop getRandomProduct() {
        return null;
    }

    @Override
    public List<Laptop> getAll() {
        final List<Laptop> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"public\".\"Laptop\"");
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
    private Laptop setFieldsToObject(final ResultSet resultSet) {
        final String model = resultSet.getString("model");
        final LaptopManufacturer manufacturer = EnumUtils.getEnum(LaptopManufacturer.class, resultSet.getString("manufacturer"),
                LaptopManufacturer.NONE);
        final Laptop laptop = new Laptop("", 0, 0.0, model, manufacturer);
        laptop.setId(resultSet.getString("id"));
        return laptop;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Laptop\" WHERE id = ?";
        Optional<Laptop> laptop = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laptop = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return laptop;
    }
}
