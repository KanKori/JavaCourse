package com.repository.product.database.tablet;

import com.config.JDBCConfig;
import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
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

public class TabletRepositoryDBI implements IAbstractProductRepository<Tablet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TabletRepositoryDBI.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static TabletRepositoryDBI instance;

    public static TabletRepositoryDBI getInstance() {
        if (instance == null) {
            instance = new TabletRepositoryDBI();
        }
        return instance;
    }

    @Override
    public void save(Tablet tablet) {
        String sql = "INSERT INTO \"public\".\"Tablet\" (id, model, manufacturer) VALUES (?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, tablet);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Tablet> tablets) {
        String sql = "INSERT INTO \"public\".\"Tablet\" (id, model, manufacturer) VALUES (?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Tablet tablet : tablets) {
                setObjectFields(statement, tablet);
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
    private void setObjectFields(final PreparedStatement statement, final Tablet tablet) {
        statement.setString(1, tablet.getId());
        statement.setString(2, tablet.getModel());
        statement.setString(3, tablet.getTabletManufacturer().name());
    }

    @Override
    public boolean update(Tablet tablet) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM \"public\".\"Tablet\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tablet getRandomProduct() {
        return null;
    }

    @Override
    public List<Tablet> getAll() {
        final List<Tablet> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"public\".\"Tablet\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private Tablet setFieldsToObject(final ResultSet resultSet) {
        final String model = resultSet.getString("model");
        final TabletManufacturer manufacturer = EnumUtils.getEnum(TabletManufacturer.class, resultSet.getString("manufacturer"),
                TabletManufacturer.NONE);
        final Tablet tablet = new Tablet("", 0, 0.0, model, manufacturer);
        tablet.setId(resultSet.getString("id"));
        return tablet;
    }

    @Override
    public Optional<Tablet> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Tablet\" WHERE id = ?";
        Optional<Tablet> tablet = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tablet = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tablet;
    }
}
