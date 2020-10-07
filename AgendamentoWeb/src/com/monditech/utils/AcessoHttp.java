package com.monditech.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class AcessoHttp {
	
	@SuppressWarnings("resource")
	public static String sendFormDataPost(String url, String json, String token, String usuario, byte[][] anexos, String nomeAnexo) throws Exception {
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			post.addHeader("Authorization", "Bearer " + token);
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			for (int i = 0; i < anexos.length; i++) {
				
				builder.addBinaryBody("anexo", anexos[i], ContentType.DEFAULT_BINARY, nomeAnexo.split(";")[i]);
				
			}

			builder.addTextBody("requisicao", json, ContentType.APPLICATION_JSON);

			HttpEntity entity = builder.build();
			post.setEntity(entity);
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			String retorno = client.execute(post, responseHandler);

			return retorno;
		}
		catch(Exception ex) {
			
			ex.getStackTrace();
			System.out.println(ex.getMessage());
			
			throw ex;
			
		}

	}
	

	public static String sendPost(String url, String json, String token, String usuario) throws Exception {
    	
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        
        try {

            request.setDoOutput(true);
            request.setDoInput(true);	            	            
            request.setRequestMethod("POST");
            request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            request.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");            
            
            if (token != null && usuario != null) {

            	request.setRequestProperty("userid", usuario);
            	request.setRequestProperty("Authorization", "Bearer " + token);
            	
            }	            
            	
            request.connect();
            
            OutputStream outputStream = request.getOutputStream();           
        	
            outputStream.write(json.getBytes("UTF-8"));          
            
            if (request.getResponseCode() == HttpURLConnection.HTTP_OK) {            	
            	
            	return readResponse(request, true);
            	
            }
            else {
            	
            	throw new Exception(readResponse(request, false));
            	
            }
           
        }
        finally {
        	
            request.disconnect();
            
        }
	    
	}
	
	public static String sendGet(String url, String token, boolean retornaJson, String usuario) throws Exception {

		HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();

        try {

            request.setDoOutput(true);
            request.setDoInput(true);
            request.setRequestMethod("GET");
            request.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");	            
            
            if (retornaJson) {
            	
            	request.setRequestProperty("Content-Type", "application/json");
            	
            }
            
            if (token != null && usuario != null) {

            	request.setRequestProperty("userid", usuario);
            	request.setRequestProperty("Authorization", "Bearer " + token);
            	
            }	            
            
            request.connect();

            if (request.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	
	            return readResponse(request, true);
            	
            }
            else {	          
            	
            	throw new Exception(readResponse(request, false));
            	
            }

        }
        finally {
        	
            request.disconnect();
            
        }
	    
	}

	private static String readResponse(HttpURLConnection request, boolean success) throws Exception {		          
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(success == true ? request.getInputStream() : request.getErrorStream()));
    	String line;
    	StringBuffer response = new StringBuffer();

		while ((line = in.readLine()) != null) {
		    response.append(line);
		}
		
		in.close();    	
		
	    return response.toString();
	    
	}

}
