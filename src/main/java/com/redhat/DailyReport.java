package com.redhat;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection="covid19report")
public class DailyReport extends PanacheMongoEntity{

	public ObjectId id;
	public String country;
	public String lastUpdate;
	public Long reportDate;
	
	public String getProvinceState() {
		return provinceState.toUpperCase();
	}

	public String getAdmin2() {
		return admin2.toUpperCase();
	}

	public String provinceState;
	public String admin2;
	public int confirmedCases;
	public int deaths;
	
	public String getCountry() {
		return country.toUpperCase();
	}
		
	


}
