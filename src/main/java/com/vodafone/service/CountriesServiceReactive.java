//package com.vodafone.service;
//
//import com.vodafone.client.RestCountriesClient;
//import com.vodafone.models.CountriesResponse;
//import com.vodafone.models.Country;
//import io.smallrye.mutiny.Uni;
//import org.eclipse.microprofile.rest.client.inject.RestClient;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import java.util.*;
//import java.util.logging.Logger;
//
//
//@ApplicationScoped
//public class CountriesService {
//
//    Logger logger = Logger.getLogger(CountriesService.class.getName());
//    private RestCountriesClient restClient;
//
//    @Inject
//    MongoService mongoService;
//
//    public CountriesService(@RestClient RestCountriesClient restClient) {
//        this.restClient = restClient;
//    }
//
//    public Uni<CountriesResponse> getCountriesResponse(String param, String paramType) {
//
//
//    }
//}
