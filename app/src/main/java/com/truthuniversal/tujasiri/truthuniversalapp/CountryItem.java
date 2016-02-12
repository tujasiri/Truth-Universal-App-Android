package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 1/20/16.
 */
public class CountryItem {

    String country_name = "";
    String country_abbreviation = "";

    public CountryItem(){
        String country_name = "";
        String country_abbreviation = "";

        this.country_name = country_name;
        this.country_abbreviation = country_abbreviation;

    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_abbreviation() {
        return country_abbreviation;
    }

    public void setCountry_abbreviation(String country_abbreviation) {
        this.country_abbreviation = country_abbreviation;
    }
}
