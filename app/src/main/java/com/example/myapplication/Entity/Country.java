package com.example.myapplication.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Country  {
    public Country(){}
    public Country(String name, String alpha2Code, String alpha3Code, String callingCodes, String capital, JSONArray altSpellings, String region, String subregion, String demonym, String timezones, String nativeName, String numericCode, JSONObject translations, String flag, String cioc, JSONArray borders, JSONArray currencies, JSONArray languages, JSONArray regionalBlocks, JSONArray latlng, long population, long area, double gini) {
        this.name = name;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.callingCodes = callingCodes;
        this.capital = capital;
        this.altSpellings = altSpellings;
        this.region = region;
        this.subregion = subregion;
        this.demonym = demonym;
        this.timezones = timezones;
        this.nativeName = nativeName;
        this.numericCode = numericCode;
        this.translations = translations;
        this.flag = flag;
        this.cioc = cioc;
        this.borders = borders;
        this.currencies = currencies;
        this.languages = languages;
        this.regionalBlocks = regionalBlocks;
        this.latlng = latlng;
        this.population = population;
        this.area = area;
        this.gini = gini;
    }

    String
    name;
    String alpha2Code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(String callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public JSONArray getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(JSONArray altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        this.timezones = timezones;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public JSONObject getTranslations() {
        return translations;
    }

    public void setTranslations(JSONObject translations) {
        this.translations = translations;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public JSONArray getBorders() {
        return borders;
    }

    public void setBorders(JSONArray borders) {
        this.borders = borders;
    }

    public JSONArray getCurrencies() {
        return currencies;
    }

    public void setCurrencies(JSONArray currencies) {
        this.currencies = currencies;
    }

    public JSONArray getLanguages() {
        return languages;
    }

    public void setLanguages(JSONArray languages) {
        this.languages = languages;
    }

    public JSONArray getRegionalBlocks() {
        return regionalBlocks;
    }

    public void setRegionalBlocks(JSONArray regionalBlocks) {
        this.regionalBlocks = regionalBlocks;
    }

    public JSONArray getLatlng() {
        return latlng;
    }

    public void setLatlng(JSONArray latlng) {
        this.latlng = latlng;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public double getGini() {
        return gini;
    }

    public void setGini(double gini) {
        this.gini = gini;
    }

    String alpha3Code;
    String callingCodes;
    String capital;

    String region;
    String subregion;
    String demonym;
    String timezones;
    String nativeName;
    String numericCode;
    JSONObject translations;
    String flag;
    String cioc;
    //latlng, borders, languages, languages, regionalBlocks





    JSONArray latlng, currencies, languages, regionalBlocks,altSpellings, borders;
    long population, area;
    double gini;

}
