package com.example.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.myapplication.Adapter.CountryAdapter;
import com.example.myapplication.Entity.Country;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Country> countryList;
    private CountryAdapter adapter;
    private String url = "https://restcountries.eu/rest/v2/all";

//    private Toolbar toolbar;
//    private List<String> lastSearches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


//        toolbar = findViewById(R.id.toolbar);
//        setActionBar(toolbar);
//        toolbar.setTitle("Country");
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        mList = findViewById(R.id.recyclerView);
        countryList = new ArrayList<>();
        adapter = new CountryAdapter(getApplicationContext(), countryList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());


        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();



//        lastSearches = loadSearchSuggestionsFromDisk();
//        materialSearchBar.setLastSuggestions(lastSearches);




    }


    private void getData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Country country = new Country();
                        country.setName(jsonObject.getString("name"));
                        country.setAlpha2Code(jsonObject.getString("alpha2Code"));
                        country.setAlpha3Code(jsonObject.getString("alpha3Code"));
                        country.setCallingCodes(jsonObject.getString("callingCodes"));
                        country.setCapital(jsonObject.getString("capital"));
                        country.setAltSpellings(jsonObject.getJSONArray("altSpellings"));
                        country.setRegion(jsonObject.getString("region"));
                        country.setSubregion(jsonObject.getString("subregion"));
                        country.setPopulation(jsonObject.getLong("population"));
                        country.setDemonym(jsonObject.getString("demonym"));
                        country.setArea(jsonObject.getLong("area"));
                        country.setGini(jsonObject.getDouble("gini"));
                        country.setTimezones(jsonObject.getString("timezones"));
                        country.setNativeName(jsonObject.getString("nativeName"));
                        country.setNumericCode(jsonObject.getString("numericCode"));
                        country.setFlag(jsonObject.getString("flag"));
                        country.setCioc(jsonObject.getString("cioc"));
                        country.setLatlng(jsonObject.getJSONArray("latlng"));
                        country.setCurrencies(jsonObject.getJSONArray("currencies"));
                        country.setLanguages(jsonObject.getJSONArray("languages"));
                        country.setTranslations(jsonObject.getJSONObject("translations"));
                        country.setRegionalBlocks(jsonObject.getJSONArray("regionalBlocs"));
                        country.setBorders(jsonObject.getJSONArray("borders"));



                        countryList.add(country);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
                adapter.getFilter().filter(s);

                return false;

            }
        });
        return true;
    }


}
