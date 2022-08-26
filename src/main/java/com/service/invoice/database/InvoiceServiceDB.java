package com.service.invoice.database;

import com.config.JDBCConfig;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.repository.invoice.database.InvoiceRepositoryDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceServiceDB {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryDB.class);
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private final InvoiceRepositoryDB invoiceRepositoryDB;

    public InvoiceServiceDB(InvoiceRepositoryDB invoiceRepositoryDB) {
        this.invoiceRepositoryDB = invoiceRepositoryDB;
    }

    public void createAndSaveInvoiceFromList(List<AbstractProduct> invoiceProducts) {
        Invoice<AbstractProduct> invoice = new Invoice<>();
        invoice.setLocalDateTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(AbstractProduct::getPrice).sum());
        invoice.setProducts(new ArrayList<>(invoiceProducts));
        invoiceRepositoryDB.save(invoice);
    }

    public List<Invoice<AbstractProduct>> getInvoicesCostlyThanPrice(double price) {
        String sql = "SELECT * FROM \"public\".\"Invoice\" WHERE sum > ?;";

        List<String> sqlList = new ArrayList<>();
        sqlList.add("SELECT * FROM \"public\".\"Phone\" WHERE id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Tablet\" WHERE id = ?;");
        sqlList.add("SELECT * FROM \"public\".\"Laptop\" WHERE id = ?;");


        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            List<Invoice<AbstractProduct>> invoices = new ArrayList<>();

            preparedStatement.setDouble(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Invoice<AbstractProduct> invoice = invoiceRepositoryDB.createFromResultSet(resultSet);
                invoice.setProducts(invoiceRepositoryDB.createProducts(sqlList, invoice));
                invoices.add(invoice);
            }

            return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public int getInvoiceCount() {
        String count = "SELECT count(id) AS count FROM \"public\".\"Invoice\"";
        final int NONE = 0;
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(count);
            if (resultSet.next()) {
                return resultSet.getInt("count");
            } else {
                return NONE;
            }
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public void updateInvoiceDateTime(String id, LocalDateTime dateTime) {
        invoiceRepositoryDB.findById(id).ifPresentOrElse(invoice -> {
            String sqlDate = "UPDATE \"public\".\"Invoice\" SET date = ?, time = ? WHERE id = ?;";

            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sqlDate)) {
                preparedStatement.setDate(1, Date.valueOf(dateTime.toLocalDate()));
                preparedStatement.setTime(2, Time.valueOf(dateTime.toLocalTime()));
                preparedStatement.setString(3, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                LOGGER.error(String.valueOf(e));
                throw new RuntimeException(e);
            }
        }, () -> LOGGER.info("id " + id + "not found"));
    }

    public Map<Double, Double> groupBySum() {
        Map<Double, Double> countSum = new HashMap<>();
        String groupBySum = "SELECT count(id) AS count, sum FROM \"public\".\"Invoice\" GROUP BY sum;";
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(groupBySum);
            while (resultSet.next()) {
                countSum.put(resultSet.getDouble("sum"), resultSet.getDouble("count"));
            }
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return countSum;
    }
}