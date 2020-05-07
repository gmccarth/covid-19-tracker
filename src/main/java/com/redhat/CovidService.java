package com.redhat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.enterprise.context.ApplicationScoped;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@ApplicationScoped
public class CovidService {
	static String dailySrc ="https://covidtracking.com/api/states";
	static String historySrc = "https://covidtracking.com/api/v1/states/daily.json";
	
	public String daily(String state) throws JSONException, IOException {
	
        
        JSONObject json = getStateJsonObject(state, "daily");    
		
		return "Confirmed cases for " + state.toUpperCase() + " : " + json.get("positive") + " as of " + json.get("lastUpdateEt") ;

	}
	
	public String history(String state) throws JSONException, IOException {
	
        
        JSONObject json = getStateJsonObject(state, "historical");    
		
		return  json.toString() ;

	}
		
		
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}	
	
	public static JSONObject getStateJsonObject(String state, String period) throws IOException, JSONException {
	    JSONArray jsonArr= null;
	    JSONObject jsonObj = new JSONObject();
	    if(period.equals("daily")) {
	    	 jsonArr = retrieveJson(dailySrc);
	    	 int stateIdx = -1;
	    	 for(int i=0; i<jsonArr.length(); i++) {
		    	  JSONObject current = jsonArr.getJSONObject(i);
		    	  if (current.get("state").equals(state.toUpperCase())){
		    		  stateIdx = i;
		    		  System.out.println("State index = " + stateIdx);
		    		  jsonObj = current;
		    		  break;
		    	  }
		      }
	    }
	    else {
	    	jsonArr = retrieveJson(historySrc);
	    	JSONArray confirmedArr = new JSONArray();
	    	JSONArray dateArr = new JSONArray();
	    	JSONArray dataPointsArr = new JSONArray();
	    	for(int i=jsonArr.length()-1; i>0; i--) {
		    	  JSONObject current = jsonArr.getJSONObject(i);
		    	  
		    	  if (current.get("state").equals(state.toUpperCase())){
		    		  JSONObject dataPoint = new JSONObject();
		    		  dataPoint.put("label", current.get("date") );
		    		  dataPoint.put("y", current.get("positive"));
		    		  dataPointsArr.put(dataPoint);
			    	  dateArr.put(current.get("date"));
			    	  confirmedArr.put(current.get("positive"));
		    		}
		      }

	    	
//	    	jsonObj.put("dates", dateArr);
//	    	jsonObj.put("confirmed", confirmedArr);
	    	jsonObj.put("dataPoints", dataPointsArr);
	    	
	    }
	   
	      
	      return jsonObj;
	   
	  }
	
	public static JSONArray retrieveJson(String source) throws MalformedURLException, IOException {
		InputStream is = new URL(source).openStream(); 
		try {
			
			 BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			 String strJson = readAll(rd);
			 JSONArray jsonArray = (JSONArray) new JSONObject(new JSONTokener("{data:"+strJson+"}")).get("data");
			 return jsonArray;

		 } finally {
		      is.close();
		    }
	}

}
