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
	
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("/{state}")
//    public String daily(@PathParam String state) throws JSONException, IOException {
//        return service.daily(state);
//    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/country/{country}")
    public List<DailyReport> getAll(@PathParam String country) {
    	System.out.println("got " + country);
    	return repo.findAllReportsForCountry(country);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test/{country}")
    public JSONObject getAggregate(@PathParam String country) {
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
    		System.out.println("label=" + entry.getKey() + "->"+ entry.getValue() );
    		sortedObj.put("y", entry.getValue());
    		confirmedArr.put(sortedObj);
    	}
    	results.put("dataPoints",confirmedArr);
    	return results;
    	
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/countries")
    public JSONArray getAllCountries(){
    	List<DailyReport> reports = repo.list("lastUpdate","2020-05-13");
    	List<String> countries = new ArrayList<String> ();
    	for(DailyReport report: reports) {
    		if(!countries.contains(report.country.toUpperCase())) {
    			countries.add(report.country.toUpperCase());
        		System.out.println("country added:" + report.country);
    		}
    		else System.out.println("country skipped:" + report.country);
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
