package com.redhat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

@Path("/reports")
public class TrackerResource {

	@Inject
	ReportRepository repo;
	

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/country/{country}")
    public List<DailyReport> getAll(@PathParam String country) {
    	System.out.println("got " + country);
    	return repo.findAllReportsForCountry(country);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/confirmed/{country}")
    public JSONObject getConfirmedCases(@PathParam String country) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getConfirmedCases(country);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("confirmedCases"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("confirmedCases") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/deaths/{country}")
    public JSONObject getDeaths(@PathParam String country) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getDeaths(country);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("deaths"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("deaths") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/states/confirmed/{state}")
    public JSONObject getConfirmedCasesInStates(@PathParam String state) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getConfirmedCasesInStates(state);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("confirmedCases"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("confirmedCases") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/states/confirmed/{state}/{county}")
    public JSONObject getConfirmedCasesInCounties(@PathParam String state, @PathParam String county) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getConfirmedCasesInCounties(state, county);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("confirmedCases"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("confirmedCases") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("states/deaths/{state}")
    public JSONObject getDeathsInStates(@PathParam String state) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getDeathsInStates(state);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("deaths"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("deaths") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("states/deaths/{state}/{county}")
    public JSONObject getDeathsInCounties(@PathParam String state, @PathParam String county) {
    	Map<String,Integer> cases = new HashMap<String,Integer>();
    	JSONArray confirmedArr = new JSONArray();
    	StringBuffer sb = new StringBuffer();
    	AggregateIterable<Document> result = repo.getDeathsInCounties(state, county);
    	MongoCursor<Document> iterator = result.iterator();
    	while(iterator.hasNext()) {
    		Document next = iterator.next();
    		cases.put(next.getString("_id"), next.getInteger("deaths"));
    		sb.append(next.getString("_id") + ":" + next.getInteger("deaths") + ", ");
    	}
    	TreeMap<String,Integer> casesByDate = new TreeMap<>();
    	casesByDate.putAll(cases);
    	JSONObject results = new JSONObject();
    	for(Map.Entry<String,Integer> entry : casesByDate.entrySet()) {
    		JSONObject sortedObj = new JSONObject();  
    		sortedObj.put("label", entry.getKey()) ;
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/combined/{country}")
    public JSONObject getCombinedStats(@PathParam String country) {
    	JSONObject results = new JSONObject();
    	JSONObject confirmed = getConfirmedCases(country);
    	JSONObject deaths = getDeaths(country);
    	results.put("confirmed", confirmed.get("dataPoints"));
    	results.put("deaths", deaths.get("dataPoints"));
    	
    	return results;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("states/combined/{state}")
    public JSONObject getCombinedStatsForStates(@PathParam String state) {
    	JSONObject results = new JSONObject();
    	JSONObject confirmed = getConfirmedCasesInStates(state);
    	JSONObject deaths = getDeathsInStates(state);
    	results.put("confirmed", confirmed.get("dataPoints"));
    	results.put("deaths", deaths.get("dataPoints"));
    	
    	return results;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("states/combined/{state}/{county}")
    public JSONObject getCombinedStatsForCounties(@PathParam String state, @PathParam String county) {
    	JSONObject results = new JSONObject();
    	JSONObject confirmed = getConfirmedCasesInCounties(state, county);
    	JSONObject deaths = getDeathsInCounties(state, county);
    	results.put("confirmed", confirmed.get("dataPoints"));
    	results.put("deaths", deaths.get("dataPoints"));
    	
    	return results;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/countries")
    public JSONArray getAllCountries(){
    	List<String> unsortedCountries = repo.getCountryList();
    	List<String> countries = new ArrayList<String> ();
    	for(String country: unsortedCountries) {
    		if(!countries.contains(country.toUpperCase())) {
    			countries.add(country.toUpperCase());
        		System.out.println("country added:" + country);
    		}
    		else System.out.println("country skipped:" + country);
    	}
    	Collections.sort(countries);
    	System.out.println("# of countries:" + countries.size());
    	
    	JSONArray jsonArr = new JSONArray();
    	for(String country: countries) {
    		JSONObject jsonObj = new JSONObject();
    		jsonObj.put("name", country);
    		jsonArr.put(jsonObj);
    	}
    	return jsonArr;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/states")
    public JSONArray getUSStates(){
    	List<String> unsortedStates = repo.getStatesList();
    	List<String> sortedStates = new ArrayList<String> ();
    	JSONArray jsonArr = new JSONArray();
    	for(String state: unsortedStates) {
    		if(!sortedStates.contains(state.toUpperCase())&!state.equalsIgnoreCase("recovered")) {
    			sortedStates.add(state.toUpperCase());
        		System.out.println("State added:" + state);

    		}
    		else System.out.println("State skipped:" + state);
    	}
    	Collections.sort(sortedStates);
    	for(String state: sortedStates) {
    		JSONObject jsonObj = new JSONObject();
    		jsonObj.put("name", state);
    		jsonArr.put(jsonObj);
    	}
    	System.out.println("# of states:" + sortedStates.size());

    	return jsonArr;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/counties/{state}")
    public JSONArray getCountiesinState(@PathParam String state){
    	List<String> unsortedCounties = repo.getCountyList(state);
    	List<String> sortedCounties = new ArrayList<String> ();
    	JSONArray jsonArr = new JSONArray();
    	for(String county: unsortedCounties) {
    		if(!sortedCounties.contains(county.toUpperCase())&!county.equalsIgnoreCase("unassigned")) {
    			sortedCounties.add(county.toUpperCase());
        		System.out.println("State added:" + county);

    		}
    		else System.out.println("State skipped:" + county);
    	}
    	Collections.sort(sortedCounties);
    	for(String county: sortedCounties) {
    		JSONObject jsonObj = new JSONObject();
    		jsonObj.put("name", county);
    		jsonArr.put(jsonObj);
    	}
    	System.out.println("# of counties:" + sortedCounties.size());

    	return jsonArr;
    }
    
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/count")
    public long count() {
    	return repo.count();
    }
}
