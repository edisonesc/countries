package com.example.myapplication;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {
    private SvgFileResolver svgFileResolver;
    @Override
    public Resource<SVG> decode(InputStream source, int width, int height) throws IOException {
        svgFileResolver = new SvgFileResolver();
        try{
            SVG svg = SVG.getFromInputStream(source);
            svg.registerExternalFileResolver(svgFileResolver);

            return new SimpleResource<SVG>(svg);

        }
        catch (SVGParseException e)
        {
            throw new IOException("Cannot load SVG from stream", e);
        }
    }

    @Override
    public String getId() {
        return null;
    }
}
