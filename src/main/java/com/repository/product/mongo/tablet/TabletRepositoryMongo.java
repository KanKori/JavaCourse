package com.repository.product.mongo.tablet;

import com.config.MongoDBConfig;
import com.google.gson.Gson;
import com.model.product.tablet.Tablet;
import com.mongodb.client.MongoCollection;
import com.repository.product.tablet.TabletRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class TabletRepositoryMongo extends TabletRepository {
    private final MongoCollection<Document> collection;
    private final Gson gson;
    private static TabletRepositoryMongo instance;

    public TabletRepositoryMongo() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Tablet.class.getSimpleName());
        gson = new Gson();
    }

    public static TabletRepositoryMongo getInstance() {
        if (instance == null) {
            instance = TabletRepositoryMongo.getInstance();
        }
        return instance;
    }

    @Override
    public void save(Tablet product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Tablet> products) {
        List<Document> documents = products.stream()
                .map(tablet -> Document.parse(gson.toJson(tablet)))
                .toList();
        collection.insertMany(documents);
    }

    public List<Tablet> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Tablet.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Tablet> findById(String id) {
        Tablet found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Tablet.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public boolean update(Tablet product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
        return true;
    }

    @Override
    public boolean delete(String id) {
        collection.deleteOne(eq("id", id));
        return true;
    }
}
