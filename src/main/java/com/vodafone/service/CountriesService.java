package com.vodafone.service;

import com.vodafone.client.RestCountriesClient;
import com.vodafone.models.CountriesResponse;
import com.vodafone.models.Country;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;


@ApplicationScoped
public class CountriesService {

    Logger logger = Logger.getLogger(CountriesService.class.getName());

    @Inject
    @RestClient
    RestCountriesClient restCountriesClient;

    @Inject
    MongoService mongoService;

    public Uni<CountriesResponse> getCountriesResponse(String param, String paramType) {

        return mongoService.getCachedCountriesResponse(param, paramType, "CountriesCache").onItem().ifNotNull().transformToUni(countriesResponse ->
                        Uni.createFrom().item(countriesResponse))
                .onItem().ifNull().switchTo(fetchFromRestCountries(param, paramType));
    }

    public Uni<CountriesResponse> fetchFromRestCountries(String param, String paramType) {
        if (paramType.equals("countryName"))
            return restCountriesClient.getCountryByName(param).onItem().transformToUni(countries -> {
                logger.info("fetched from restcountries");
                return countriesResponseInitAndStore(countries);
            });

        else
            return restCountriesClient.getCountryByCapital(param).onItem().transformToUni(countries -> {
                logger.info("fetched from restcountries");
                return countriesResponseInitAndStore(countries);
            });

    }

    public Uni<CountriesResponse> countriesResponseInitAndStore(List<Country> list) {

        CountriesResponse countriesResponse = new CountriesResponse();

        countriesResponse.setCountryName(list.get(0).getCountryName());
        countriesResponse.setCountryCode(list.get(0).getCountryCode());
        countriesResponse.setCapital(list.get(0).getCapital());
        countriesResponse.setContinent(list.get(0).getContinent());
        list.get(0).getOfficialLanguage().stream().filter(Objects::nonNull).findFirst().ifPresent(languages -> {
            countriesResponse.setOfficialLanguage(languages.getName());
        });
        list.get(0).getCurrencyName().stream().filter(Objects::nonNull).findFirst().ifPresent(currencies -> {
            countriesResponse.setCurrencyName(currencies.getName());
        });

        mongoService.addToCache(countriesResponse, "CountriesCache");
        return Uni.createFrom().item(countriesResponse);
    }
}

