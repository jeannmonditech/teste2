package com.monditech.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DateTypeAdapter extends TypeAdapter<Date> {
	
	@Override
	public void write(JsonWriter out, Date value) throws IOException {
        
		out.value(value.toString());

	}

	@SuppressWarnings("deprecation")
	@Override
	public Date read(JsonReader in) throws IOException {

        String date = in.nextString();
       
        if (date.contains(".")) {
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:hh:ss.mmm");
        	
        	try {
				return dateFormat.parse(date);
			}
        	catch (ParseException e) {

				e.printStackTrace();
				
			}

        }
        
        return new Date(date);
        
	}
	
}