package com.redhat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class ReportRepository implements PanacheMongoRepository<DailyReport> {
	@Inject MongoClient mongoClient;

	public List<DailyReport> findAllReportsForCountry(String country){
		return list("country", country);
	}
	
	public AggregateIterable<Document> getConfirmedCases(String country){

		return getCollection().aggregate(Arrays.asList(
					Aggregates.match(Filters.eq("country",country.toLowerCase())),
					Aggregates.group("$lastUpdate", Accumulators.sum("confirmedCases", "$confirmedCases")),
					Aggregates.project(Projections.include("country","confirmedCases"))
							));
	}
	
	public AggregateIterable<Document> getDeaths(String country){

		return getCollection().aggregate(Arrays.asList(
					Aggregates.match(Filters.eq("country",country.toLowerCase())),
					Aggregates.group("$lastUpdate", Accumulators.sum("deaths", "$deaths")),
					Aggregates.project(Projections.include("country","deaths"))
							));
	}
	
	
	public List<String> getCountryList(){
		List<DailyReport> reports = list("lastUpdate","2020-05-12");
		List<String> countryList = new ArrayList<String>();
		for(DailyReport report : reports) {
			countryList.add(report.country);
			System.out.println(report.country);
		}
		return countryList;
	}
	
	public List<String> getStatesList(){
		List<DailyReport> reports = list("{'lastUpdate':'2020-05-12','country':'us'}");
		List<String> stateList = new ArrayList<String>();
		for(DailyReport report : reports) {
			stateList.add(report.provinceState);
			System.out.println(report.provinceState);
		}
		return stateList;
	}
	
	private MongoCollection getCollection(){
        return mongoClient.getDatabase("covid19report").getCollection("covid19report");
	}
//	public List<DailyReport> getSum(){
//		return list("confirmedCases").where("lastUpdate","2020-05-15");
//	}

}
