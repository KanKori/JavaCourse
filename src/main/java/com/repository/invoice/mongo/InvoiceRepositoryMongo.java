package com.repository.invoice.mongo;

import com.config.MongoDBConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;


public class InvoiceRepositoryMongo implements IInvoiceRepositoryMongo {
    private final MongoDatabase MONGO_DATABASE = MongoDBConfig.getMongoDatabase();
    private final MongoCollection<Document> collection;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter
                    (LocalDateTime.class,
                            (JsonSerializer<LocalDateTime>)
                                    (localDateTime, type, jsonSerializationContext)
                                            -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>)
                            (json, type, jsonDeserializationContext)
                                    -> LocalDateTime.parse(json.getAsString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create();

    public InvoiceRepositoryMongo() {
        collection = MONGO_DATABASE.getCollection(Invoice.class.getSimpleName());
    }

    @Override
    public boolean save(Invoice invoice) {
        collection.insertOne(Document.parse(invoiceToJson(invoice)));
        return true;
    }

    @Override
    public boolean update(Invoice invoice) {
        UpdateResult result = collection.replaceOne(eq("id", invoice.getId()),
                Document.parse(invoiceToJson(invoice)));

        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id) {
        DeleteResult result = collection.deleteOne(eq("id", id));
        return result.getDeletedCount() > 0;
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Bson filter = Filters.eq("id", id);
        return collection.find(filter).map(x -> gson.fromJson(x.toJson(), Invoice.class))
                .into(new ArrayList<>()).stream().findFirst();
    }

    @Override
    public List<Invoice> findAllGreaterThanInputSumInvoices(double price) {
        List<Invoice> invoices = new ArrayList<>();
        Document unwind = new Document("$unwind", "$products");
        Document group = new Document("$group", new Document("_id", "$id")
                .append("total_price", new Document("$sum", "$products.price")));
        Document match = new Document("$match", new Document("total_price",
                new Document("$gt", price)));

        List<Document> pipeline = asList(unwind, group, match);
        AggregateIterable<Document> res = collection.aggregate(pipeline);
        for (Document re : res) {
            invoices.add(findById(re.getString("_id")).orElseThrow(() -> {
                        throw new IllegalArgumentException("Could not find invoice");
                    }
            ));
        }
        return invoices;
    }

    @Override
    public int getInvoiceCount() {
        return (int) collection.countDocuments();
    }

    @Override
    public void sortBySum() {
        Document unwind = new Document("$unwind", "$products");
        Document group1 = new Document("$group", new Document("_id", "$id")
                .append("total_products", new Document("$sum", "$products.price")));
        Document group2 = new Document("$group", new Document("_id", "$total_products")
                .append("sum", new Document("$first", "$total_products"))
                .append("invoices", new Document("$addToSet", "$_id")));

        List<Document> pipeline = asList(unwind, group1, group2);
        AggregateIterable<Document> res = collection.aggregate(pipeline);
        for (Document doc : res) {
            System.out.printf("Invoices-(%s) sum-%f%n", String.join(",", doc.getList("invoices", String.class)),
                    doc.getDouble("sum"));
        }
    }

    public static String invoiceToJson(Invoice inv) {
        String json = gson.toJson(inv);
        json = json.substring(0, json.length() - 1) +
                ",\"created\": \"" + inv.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\"}";
        return json;
    }
}
