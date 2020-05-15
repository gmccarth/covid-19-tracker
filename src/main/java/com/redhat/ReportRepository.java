package com.redhat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class ReportRepository implements PanacheMongoRepository<DailyReport> {
	public DailyReport findByCountry(String country) {
		System.out.println(LocalDate.of(2020,5,7).atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000);
		return find("country = ?1 and reportDate = ?2", country, LocalDate.of(2020,5,7).atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000).firstResult();
	}
	public List<DailyReport> findAllReportsForCountry(String country){
		return list("country", country);
	}

}
