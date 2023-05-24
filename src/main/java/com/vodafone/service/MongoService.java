package com.vodafone.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.vodafone.models.CountriesResponse;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.jboss.resteasy.reactive.common.providers.serialisers.jsonp.JsonpUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class MongoService {

    @PostConstruct
    void init() {
        createIndex();
    }

    Logger logger = Logger.getLogger(CountriesService.class.getName());

    @Inject
    ReactiveMongoClient mongoClient;

    public Uni<CountriesResponse> getCachedCountriesResponse(String param, String paramType, String collectionName) {

        ReactiveMongoCollection<CountriesResponse> reactiveMongoCollection = mongoClient.getDatabase("mydatabase").getCollection("CountriesCache", CountriesResponse.class);
        return capitalizeFirstLetter(param).onItem().transformToUni(s -> {
            return reactiveMongoCollection.find(eq(paramType, s)).collect().first().onItem().transform(countriesResponse -> {
                if (countriesResponse != null) logger.info("fetched from cache");
                return countriesResponse;
            });
        });

//        ReactiveMongoCollection<Document> reactiveMongoCollection = mongoClient.getDatabase("mydatabase").getCollection("CountriesCache");
//        return capitalizeFirstLetter(param).onItem().transformToUni(s -> {
//            return reactiveMongoCollection.find(eq(paramType, s)).map(document -> {
//                CountriesResponse countriesResponse = new CountriesResponse();
//                countriesResponse.setCountryName(document.getString("countryName"));
//                countriesResponse.setCountryCode("countryCode");
//                countriesResponse.setCapital("capital");
//                countriesResponse.setContinent("continent");
//                countriesResponse.setOfficialLanguage("officialLanguage");
//                countriesResponse.setCurrencyName("currencyName");
////                if (countriesResponse != null) logger.info("fetched from cache");
//                return countriesResponse;
//            }).collect().first();
//        });
    }


    public Uni<Void> addToCache(CountriesResponse countriesResponse, String collectionName) {
        Document document = new Document()
                .append("countryName", countriesResponse.getCountryName())
                .append("countryCode", countriesResponse.getCountryCode())
                .append("capital", countriesResponse.getCapital())
                .append("continent", countriesResponse.getContinent())
                .append("officialLanguage", countriesResponse.getOfficialLanguage())
                .append("currencyName", countriesResponse.getCurrencyName())
                .append("timeOfEntry", new Date());

        mongoClient.getDatabase("mydatabase").getCollection("CountriesCache").insertOne(document).onItem().ignore();

        Uni<Void> uni = Uni.createFrom().nullItem();
        return uni;
    }

    public Uni<Void> createIndex() {
//        IndexOptions indexOptions = new IndexOptions();
//        indexOptions.expireAfter(60L, TimeUnit.SECONDS);
        mongoClient.getDatabase("mydatabase").getCollection("CountriesCache")
                .createIndex(new Document("timeOfEntry", 1), new IndexOptions().expireAfter(5L, TimeUnit.MINUTES));
        return Uni.createFrom().nullItem();
    }

    public Uni<String> capitalizeFirstLetter(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return Uni.createFrom().item(new String(chars));
    }

}
