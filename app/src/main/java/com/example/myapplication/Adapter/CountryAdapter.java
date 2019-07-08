package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgLoader;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.myapplication.CountryInfo;
import com.example.myapplication.Entity.Country;
import com.example.myapplication.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    private Context context;
    private List<Country> list;
    private List<Country> listFull;

    public CountryAdapter(Context context, List<Country> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        listFull = new ArrayList<>(list);
        View v = LayoutInflater.from(context).inflate(R.layout.countryitem, viewGroup, false);
        requestBuilder = Glide
                .with(context).using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder viewHolder, int position) {
        final Country country = list.get(position);

        viewHolder.countryName.setText(country.getName()); //
        Uri uri = Uri.parse(country.getFlag());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(viewHolder.countryFlag);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CountryInfo.class);
                intent.putExtra("name", country.getName());
                intent.putExtra("alpha2", country.getAlpha2Code());
                intent.putExtra("alpha3", country.getAlpha3Code());
                intent.putExtra("callingCodes", country.getCallingCodes());
                intent.putExtra("capital", country.getCapital());
                intent.putExtra("altSpellings", country.getAltSpellings().toString());
                intent.putExtra("region", country.getRegion());
                intent.putExtra("subRegion", country.getSubregion());
                intent.putExtra("population", country.getPopulation());
                intent.putExtra("demonym", country.getDemonym());
                intent.putExtra("area", country.getArea());
                intent.putExtra("gini", country.getGini());
                intent.putExtra("timezones", country.getTimezones());
                intent.putExtra("nativeNames", country.getNativeName());
                intent.putExtra("numericCode", country.getNumericCode());
                intent.putExtra("cioc", country.getCioc());
                intent.putExtra("flag", country.getFlag());


                intent.putExtra("currencies", country.getCurrencies().toString());
                intent.putExtra("latlng", country.getLatlng().toString());
                intent.putExtra("languages", country.getLanguages().toString());
                intent.putExtra("translations", country.getTranslations().toString());
                intent.putExtra("regionalBlocks", country.getRegionalBlocks().toString());
                intent.putExtra("borders",  country.getBorders().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           List<Country> filteredList =new ArrayList<>();
           if(charSequence == null || charSequence.length() == 0){
               filteredList.addAll(listFull);

           }else {
               String filterPattern = charSequence.toString().toLowerCase().trim();
               for (Country country : listFull){
                   if(country.getName().toLowerCase().contains(filterPattern)
                           || (country.getAlpha2Code().toLowerCase().contains(filterPattern) ||
                            country.getAlpha3Code().toLowerCase().contains(filterPattern))
                   ){
                       filteredList.add(country);
                   }

               }



           }
           FilterResults results = new FilterResults();
           results.values = filteredList;

           return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
          list.clear();
          list.addAll( (List)filterResults.values);
          notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView countryName;
        public ImageView  countryFlag;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.name);
            countryFlag = itemView.findViewById(R.id.flag);
            countryName.setTypeface(EasyFonts.robotoLight(context));
            linearLayout =  itemView.findViewById(R.id.linearLayout);

        }





    }

}
