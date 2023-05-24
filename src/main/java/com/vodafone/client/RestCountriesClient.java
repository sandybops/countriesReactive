package com.vodafone.client;

import com.vodafone.models.Country;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient
public interface RestCountriesClient {

    @GET
    @Path("/v2/capital/{capital}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Country>> getCountryByCapital(@PathParam("capital") String capital);

    @GET
    @Path("/v2/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Country>> getCountryByName(@PathParam("name") String name);

}