package com.repository.invoice.database;

import com.config.JDBCConfig;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InvoiceRepositoryDB implements IInvoiceRepository<AbstractProduct> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryDB.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    @Override
    public Invoice<AbstractProduct> createFromResultSet(ResultSet resultSet) {
        try {
            return new Invoice<>(
                    resultSet.getObject("id").toString(),
                    resultSet.getDouble("sum"),
                    null,
                    LocalDateTime.of(
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    )
            );
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Invoice<AbstractProduct> invoice) {
        String sql = "INSERT INTO \"public\".\"Invoice\" (id, sum, time, date) VALUES (?, ?, ?, ?);";

        Map<Class<? extends AbstractProduct>, String> sqlPutMap = new HashMap<>();
        sqlPutMap.put(Phone.class, "UPDATE \"public\".\"Phone\" SET invoice_Phone_id = ? WHERE id = ? AND invoice_id IS NULL;");
        sqlPutMap.put(Tablet.class, "UPDATE \"public\".\"Tablet\" SET invoice_Tablet_id = ? WHERE id = ? AND invoice_id IS NULL;");
        sqlPutMap.put(Laptop.class, "UPDATE \"public\".\"Laptop\" SET invoice_Laptop_id = ? WHERE id = ? AND invoice_id IS NULL;");

        try (PreparedStatement preparedStatementSQL = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            preparedStatementSQL.setString(1, invoice.getId());
            preparedStatementSQL.setDouble(2, invoice.getSum());
            preparedStatementSQL.setTime(3, Time.valueOf(LocalTime.now()));
            preparedStatementSQL.setDate(4, Date.valueOf(LocalDate.now()));
            preparedStatementSQL.execute();

            for (AbstractProduct product : invoice.getProducts()) {
                try (PreparedStatement preparedStatementSQLForProduct = CONNECTION.prepareStatement(sqlPutMap.get(product.getClass()))) {
                    preparedStatementSQLForProduct.setString(1, invoice.getId());
                    preparedStatementSQLForProduct.setString(2, product.getId());
                    preparedStatementSQLForProduct.execute();
                }
            }

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException exception) {
                LOGGER.error(String.valueOf(e));
                throw new RuntimeException(exception);
            }
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public List<AbstractProduct> createProducts(List<String> sqlList, Invoice<AbstractProduct> invoice) {
        List<AbstractProduct> abstractProduct = new ArrayList<>();
        for (String sqlQuery : sqlList) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, invoice.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (sqlQuery.contains(Phone.class.getSimpleName())) {
                        abstractProduct.add(new Phone(
                                resultSet.getString("title"),
                                resultSet.getInt("count"),
                                resultSet.getDouble("price"),
                                resultSet.getString("model"),
                                PhoneManufacturer.valueOf(resultSet.getString("phoneManufacturer"))));
                    } else {
                        if (sqlQuery.contains(Tablet.class.getSimpleName())) {
                            abstractProduct.add(new Tablet(
                                    resultSet.getString("title"),
                                    resultSet.getInt("count"),
                                    resultSet.getDouble("price"),
                                    resultSet.getString("model"),
                                    TabletManufacturer.valueOf(resultSet.getString("tabletManufacturer"))));
                        } else {
                            if (sqlQuery.contains(Laptop.class.getSimpleName())) {
                                abstractProduct.add(new Laptop(
                                        resultSet.getString("title"),
                                        resultSet.getInt("count"),
                                        resultSet.getDouble("price"),
                                        resultSet.getString("model"),
                                        LaptopManufacturer.valueOf(resultSet.getString("laptopManufacturer"))));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                LOGGER.error(String.valueOf(e));
                throw new RuntimeException(e);
            }
        }
        return abstractProduct;
    }

    @Override
    public Optional<Invoice<AbstractProduct>> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Invoice\" WHERE id = ?;";

        List<String> sqlList = new ArrayList<>();
        sqlList.add("SELECT * FROM \"public\".\"Phone\" WHERE invoice_Phone_id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Tablet\" WHERE invoice_Tablet_id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Laptop\" WHERE invoice_Laptop_id = ?;");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Invoice<AbstractProduct> invoice = createFromResultSet(resultSet);
                invoice.setProducts(createProducts(sqlList, invoice));
                return Optional.of(invoice);
            }
            LOGGER.info("id " + id + "not found");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Invoice<AbstractProduct>> findAll() {
        String sql = "SELECT * FROM \"public\".\"Invoice\";";
        List<String> sqlList = new ArrayList<>();
        sqlList.add("SELECT * FROM \"public\".\"Phone\" WHERE invoice_Phone_id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Tablet\" WHERE invoice_Tablet_id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Laptop\" WHERE invoice_Laptop_id = ?;");

        try (Statement statement = CONNECTION.createStatement()) {
            List<Invoice<AbstractProduct>> invoices = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Invoice<AbstractProduct> invoice = createFromResultSet(resultSet);
                invoice.setProducts(createProducts(sqlList, invoice));
                invoices.add(invoice);
            }
            return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
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
}
