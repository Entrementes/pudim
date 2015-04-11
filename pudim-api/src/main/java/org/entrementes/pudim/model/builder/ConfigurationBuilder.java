package org.entrementes.pudim.model.builder;

import org.entrementes.pudim.model.Configuration;

public class ConfigurationBuilder {
	
	private final Configuration body;
	
	public ConfigurationBuilder() {
		this.body = new Configuration();
	}

	public ConfigurationBuilder name(String name) {
		this.body.setName(name);
		return this;
	}

	public ConfigurationBuilder value(String value) {
		this.body.setValue(value);
		return this;
	}

	public Configuration build() {
		return this.body;
	}

}
