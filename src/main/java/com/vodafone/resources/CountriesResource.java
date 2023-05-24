package com.vodafone.resources;

import com.vodafone.service.CountriesService;
import com.vodafone.client.RestCountriesClient;
import com.vodafone.models.CountriesResponse;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/")
public class CountriesResource {

    @Inject
    @RestClient
    RestCountriesClient countriesClient;
    @Inject
    CountriesService service;


    @GET
    @Path("/capital/{capital-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CountriesResponse> getCountryByCapital(@PathParam("capital-name") String capitalName) {
        return service.getCountriesResponse(capitalName, "capital");
    }

    @GET
    @Path("/country")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CountriesResponse> getCountryByName(@QueryParam("country-name") String countryName) {
        return service.getCountriesResponse(countryName, "countryName");
    }
}
