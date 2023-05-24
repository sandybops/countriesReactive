package com.vodafone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    @JsonProperty("name")
    private String countryName;
    @JsonProperty("alpha2Code")
    private String countryCode;
    private String capital;
    @JsonProperty("region")
    private String continent;
    @JsonProperty("languages")
    private List<Languages> officialLanguage;
    @JsonProperty("currencies")
    private List<Currencies> currencyName;

    public Country() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public List<Currencies> getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(List<Currencies> currencyName) {
        this.currencyName = currencyName;
    }

    public List<Languages> getOfficialLanguage() {
        return officialLanguage;
    }

    public void setOfficialLanguage(List<Languages> officialLanguage) {
        this.officialLanguage = officialLanguage;
    }
}
