package com.repository.product.mongo.laptop;

import com.config.MongoDBConfig;
import com.google.gson.Gson;
import com.model.product.laptop.Laptop;
import com.mongodb.client.MongoCollection;
import com.repository.product.laptop.LaptopRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class LaptopRepositoryMongo extends LaptopRepository {
    private final MongoCollection<Document> collection;
    private final Gson gson;
    private static LaptopRepositoryMongo instance;

    public LaptopRepositoryMongo() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Laptop.class.getSimpleName());
        gson = new Gson();
    }

    public static LaptopRepositoryMongo getInstance() {
        if (instance == null) {
            instance = LaptopRepositoryMongo.getInstance();
        }
        return instance;
    }

    @Override
    public void save(Laptop product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Laptop> products) {
        List<Document> documents = products.stream()
                .map(laptop -> Document.parse(gson.toJson(laptop)))
                .toList();
        collection.insertMany(documents);
    }

    public List<Laptop> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Laptop.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Laptop found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Laptop.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public boolean update(Laptop product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
        return true;
    }

    @Override
    public boolean delete(String id) {
        collection.deleteOne(eq("id", id));
        return true;
    }
}
