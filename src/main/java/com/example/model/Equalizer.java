package com.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class Equalizer {
	private HashMap<Category, ArrayList<Product>> equalizer;
	
	public Equalizer() {
		this.equalizer = new HashMap<>();
	}
	
	public Map<Category, ArrayList<Product>> getEqualizer() {
		return Collections.unmodifiableMap(this.equalizer);
	}

}
