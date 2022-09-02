package com.repository.product.mongo.phone;

import com.config.MongoDBConfig;
import com.google.gson.Gson;
import com.model.product.laptop.Laptop;
import com.model.product.phone.Phone;
import com.mongodb.client.MongoCollection;
import com.repository.product.mongo.laptop.LaptopRepositoryMongo;
import com.repository.product.phone.PhoneRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class PhoneRepositoryMongo extends PhoneRepository {
    private final MongoCollection<Document> collection;
    private final Gson gson;
    private static PhoneRepositoryMongo instance;

    public PhoneRepositoryMongo() {
        collection = MongoDBConfig.getMongoDatabase().getCollection(Laptop.class.getSimpleName());
        gson = new Gson();
    }

    public static PhoneRepositoryMongo getInstance() {
        if (instance == null) {
            instance = PhoneRepositoryMongo.getInstance();
        }
        return instance;
    }

    @Override
    public void save(Phone product) {
        collection.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Phone> products) {
        List<Document> documents = products.stream()
                .map(phone -> Document.parse(gson.toJson(phone)))
                .toList();
        collection.insertMany(documents);
    }

    public List<Phone> findAll() {
        return collection.find()
                .map(document -> gson.fromJson(document.toJson(), Phone.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Phone> findById(String id) {
        Phone found = collection.find(eq("id", id))
                .map(document -> gson.fromJson(document.toJson(), Phone.class))
                .first();
        return Optional.ofNullable(found);
    }

    @Override
    public boolean update(Phone product) {
        collection.updateOne(eq("id", product.getId()), new Document("$set", gson.toJson(product)));
        return true;
    }

    @Override
    public boolean delete(String id) {
        collection.deleteOne(eq("id", id));
        return true;
    }
}
