//package com.vodafone.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vodafone.models.CountriesResponse;
//import org.eclipse.microprofile.reactive.messaging.*;
//
//import javax.inject.Inject;
//import java.util.Optional;
//import java.util.concurrent.CompletionStage;
//import java.util.logging.Logger;
//
//public class KafkaConsumer {
//    @Inject
//    MongoService mongoService;
//
//    @Inject
//    ObjectMapper objectMapper;
//
////    Logger logger = Logger.getLogger(KafkaConsumer.class.getName());
//
////    @Incoming("store-countries-in")
////    public CompletionStage<Void> storeResponse(Message<String> msg) throws JsonProcessingException {
////
////        CountriesResponse countriesResponse = objectMapper.readValue(msg.getPayload(), CountriesResponse.class);
//////        Optional<CountriesResponse> optionalCountriesResponse = mongoService.getCachedCountriesResponse(countriesResponse.getCountryName(), "countryName", "KafkaCountries");
////
////        if (optionalCountriesResponse.isPresent()) {
////            logger.info(countriesResponse.getCountryName() + " cannot be added twice in KafkaCountries collection");
////        } else {
////            logger.info("Added " + countriesResponse.getCountryName() + " in KafkaCountries collection!");
////            mongoService.addToCache(countriesResponse, "KafkaCountries");
////        }
////        return msg.ack();
////    }
//
//}