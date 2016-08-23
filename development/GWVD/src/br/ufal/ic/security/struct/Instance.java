package br.ufal.ic.security.struct;

import java.util.HashMap;

public class Instance {
	
	private HashMap<String, String> hash;
	
	public Instance(String[] attributes) {
		hash = new HashMap<>(attributes.length);
		for (String key : attributes) {
			hash.put(key, "");
		}
	}
	
	public String get(String key){
		return hash.get(key);
	}
	
	public String put(String key, String value){
		return hash.put(key, value);
	}
	
	@Override
	public String toString() {
		String tuple="";
		for (String iterable : hash.keySet()) {
			tuple+=hash.get(iterable)+",";
		}
		tuple+="&%$";
		return tuple.replace(",&%$", "\n");
	}
}
