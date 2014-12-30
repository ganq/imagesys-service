package com.mysoft.b2b.imagesys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class MongoService {
	
	private static final Logger log = Logger.getLogger(MongoService.class);

	private static Datastore datastore;
	
	private String addresses;
	
	private String databaseName;

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void init() {
		List<ServerAddress> addr = new ArrayList<ServerAddress>();
		try {
			String [] _adds = addresses.split(",");
			for(String _path : _adds){
				addr.add(new ServerAddress(_path));
			}
			Mongo mongo = new Mongo(addr);
			Morphia morphia = new Morphia();
			morphia.mapPackage("com.mysoft.b2b.image.vo.BaseLogVo");			
			datastore = morphia.createDatastore(mongo, databaseName);
			datastore.ensureIndexes();  
		} catch (Exception e) {
			log.error("mongo run error ",e);
		}
	}
	
	public void saveModel(Object log){
		datastore.save(log);
	}
}
