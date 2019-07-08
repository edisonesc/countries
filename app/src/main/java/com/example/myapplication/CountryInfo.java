package com.example.myapplication;


import android.graphics.drawable.PictureDrawable;
import android.net.Uri;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;



import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;

import com.caverock.androidsvg.SVG;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.klinker.android.sliding.MultiShrinkScroller;
import com.klinker.android.sliding.SlidingActivity;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;


public class CountryInfo extends SlidingActivity{ //false

    private String name, flag, alpha2, alpha3, callingCodes,
            capital, altSpellings,region,subRegion,demonym, gini, timezones,nativeNames,
            numericCode, cioc, latlng, currencies, languages, translations, regionalBlocks, borders;
    private TextView countryPopulation, countryRegion, countryCapital, countryName, countrySubregion,
            countryA2A3, countryDemonym, countryArea, countryTimezones, countryCallingCode, countryRegionalBlocks,
            countryCurrency, countryLanguage, countryTranslations, countryBorders, countryAltSpellings;
    private Long population, area;
    private ArrayList<Double> listLatLng;
    private ArrayList<String>  listAltSpellings, listBorders, listTimezone, listCallingCode;
    private Map<String, Object> currencyMap, languagesMap, translationsMap, regionalBlocksMap;
    private ImageView flagImage;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

//    private MapFragment mapFragment;
//    private GoogleMap map
    private MapView mapView;

    @Override
    protected void configureScroller(MultiShrinkScroller scroller) {
        super.configureScroller(scroller);
        scroller.setIntermediateHeaderHeightRatio(0);
    }

    @Override
    public void setHeaderContent(int resId) {
        super.setHeaderContent(resId);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setPrimaryColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );



        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        setContent(R.layout.activity_country_info);

        flagImage = findViewById(R.id.flag);
        countryPopulation = findViewById(R.id.countryPopulation);
        countryCapital = findViewById(R.id.countryCapital);
        countryRegion = findViewById(R.id.countryRegion);
        countryName = findViewById(R.id.countryName);

        countrySubregion = findViewById(R.id.subRegion);
        countryA2A3 = findViewById(R.id.alpha);
        countryDemonym = findViewById(R.id.demonym);
        countryArea = findViewById(R.id.area);
        countryTimezones = findViewById(R.id.timezone);
        countryCallingCode = findViewById(R.id.callingCode);
        countryRegionalBlocks =findViewById(R.id.regionalBlocks);
        countryCurrency = findViewById(R.id.currency);
        countryLanguage = findViewById(R.id.language);
        countryTranslations = findViewById(R.id.translations);
        countryBorders = findViewById(R.id.border);
        countryAltSpellings = findViewById(R.id.altSpellings);
        TextView[] texts = {countrySubregion, countryA2A3, countryDemonym, countryArea, countryTimezones, countryCallingCode,
        countryRegionalBlocks, countryCurrency, countryLanguage, countryTranslations, countryBorders,countryAltSpellings};
        for (TextView text: texts){
            text.setTypeface(EasyFonts.robotoRegular(this));
        }

        countryRegion.setTypeface(EasyFonts.robotoRegular(this));
        countryPopulation.setTypeface(EasyFonts.robotoRegular(this));
        countryCapital.setTypeface(EasyFonts.robotoRegular(this));
        countryName.setTypeface(EasyFonts.caviarDreams(this));

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        Bundle countryInfo = getIntent().getExtras();
        requestBuilder = Glide
                .with(this).using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new com.ahmadrosid.svgloader.SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());


        if(countryInfo!=null){
            name = countryInfo.getString("name");
            alpha2 = countryInfo.getString("alpha2");
            alpha3 = countryInfo.getString("alpha3");

            callingCodes = countryInfo.getString("callingCodes");

            capital = countryInfo.getString("capital");
            region = countryInfo.getString("region");
            subRegion = countryInfo.getString("subRegion");
            population = countryInfo.getLong("population");
            demonym = countryInfo.getString("demonym");
            area = countryInfo.getLong("area");
            gini = countryInfo.getString("gini") == null ?  "N/A" : countryInfo.getString("gini");
            timezones = countryInfo.getString("timezones");
            nativeNames = countryInfo.getString("nativeNames");
            numericCode = countryInfo.getString("numericCode");
            cioc = countryInfo.getString("cioc");
            flag = countryInfo.getString("flag");
            latlng = countryInfo.getString("latlng");
            currencies = countryInfo.getString("currencies");
            languages = countryInfo.getString("languages");
            translations = countryInfo.getString("translations");
            regionalBlocks = countryInfo.getString("regionalBlocks");
            altSpellings = countryInfo.getString("altSpellings");
            borders = countryInfo.getString("borders");


            Uri uri = Uri.parse(flag);
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .into(flagImage);



            try{
                JSONArray latlangArray = new JSONArray(latlng);
                JSONArray currencyArray = new JSONArray(currencies);
                JSONArray languagesArray = new JSONArray(languages);
                JSONArray altSpellingsArray = new JSONArray(altSpellings);
                JSONObject translationObject = new JSONObject(translations);
                JSONArray regionalBlocksArray = new JSONArray(regionalBlocks);
                JSONArray bordersArray = new JSONArray(borders);
                JSONArray timezoneArray = new JSONArray(timezones);
                JSONArray callingCodeArray = new JSONArray(callingCodes);



                currencyMap = new HashMap<>();
                arrayToMap(currencyArray, currencyMap);
                languagesMap = new HashMap<>();
                arrayToMap(languagesArray, languagesMap);
                translationsMap = new HashMap<>();
                translationsMap = jsonToMap(translationObject);
                regionalBlocksMap = new HashMap<>();
                arrayToMap(regionalBlocksArray, regionalBlocksMap);


                listTimezone = new ArrayList<>();
                jsonToArray(timezoneArray, listTimezone);
                listCallingCode = new ArrayList<>();
                jsonToArray(callingCodeArray, listCallingCode);


                listAltSpellings = new ArrayList<>();
                jsonToArray(altSpellingsArray, listAltSpellings);
                listBorders = new ArrayList<>();
                jsonToArray(bordersArray, listBorders);

                listLatLng = new ArrayList<>();
                if (latlangArray != null){
                    int len = latlangArray.length();
                    for(int i = 0; i < len; i++){
                        listLatLng.add(Double.parseDouble(latlangArray.get(i).toString()));
                    }
                }

            }
            catch (JSONException e){e.printStackTrace();}


            countryName.setText(name);
            countryRegion.setText("Region: " + region);
            countryCapital.setText("Capital: " + capital);
            countryPopulation.setText("Population: " + insertCommas(String.valueOf(population)));
            countryCallingCode.setText(listCallingCode.size() == 1 ? listCallingCode.get(0) : "N/A");
            countrySubregion.setText(subRegion);
            countryA2A3.setText(alpha2 + " & " + alpha3);
            countryDemonym.setText(demonym);
            countryArea.setText(insertCommas(String.valueOf(area)) + " km²");

            countryTimezones.setText(listTimezone.size() == 1 ? listTimezone.get(0) : "N/A");
            countryRegionalBlocks.setText(regionalBlocksMap.get("name") == null ? "N/A" : regionalBlocksMap.get("name").toString());


            String altSpellingsText = "";
            if(listAltSpellings.size() > 0){
            for(String s : listAltSpellings){
                altSpellingsText += s + " ";}
            }
            countryAltSpellings.setText(altSpellingsText);
            countryAltSpellings.setSelected(true);
            String currencyText = "";
            for(String key: currencyMap.keySet()){
                currencyText += key.toUpperCase() + ": "+ currencyMap.get(key)+ " | ";
            }

            countryCurrency.setText(currencyText);
            countryCurrency.setSelected(true);
            String langsText = "";

            for(String key: languagesMap.keySet()){
                langsText += languagesMap.get(key) + " ";
            }
            countryLanguage.setText(langsText);
            countryLanguage.setSelected(true);

            String  transText = "";
            for(String key: translationsMap.keySet()){
                transText += key.toUpperCase() + ": "+ translationsMap.get(key)+ " | ";
            }
            countryTranslations.setText(transText);
            countryTranslations.setSelected(true);

            String borderText = "";
            if(listBorders.size() > 0){
                for(String border : listBorders){
                    borderText += border + " ";
                }

            }
            else {borderText = "N/A";}

            countryBorders.setText(borderText);
            countryBorders.setSelected(true);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng coordinates = new LatLng(listLatLng.get(0), listLatLng.get(1));
                    googleMap.addMarker(new MarkerOptions().position(coordinates).title(name));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 5));
                    mapView.onResume();
                }
            });


        }

    }


    private void jsonToArray(JSONArray jsonArray, ArrayList arrayList) throws  JSONException{

        if (jsonArray != null){
            int len = jsonArray.length();
            for(int i = 0; i < len; i++){
                arrayList.add(jsonArray.get(i).toString());
            }
        }
    }
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
    private void arrayToMap(JSONArray array ,Map map){
        try {
            for (int i = 0; i < array.length(); i++) { // languages
                JSONObject j = array.optJSONObject(i);
                Iterator it = j.keys();
                while (it.hasNext()) {
                    String n = it.next().toString();
                    map.put(n, j.getString(n));
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String insertCommas(String str)
    {
        if(str.length() < 4){
            return str;
        }
        return insertCommas(str.substring(0, str.length() - 3)) + "," + str.substring(str.length() - 3, str.length());
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}

