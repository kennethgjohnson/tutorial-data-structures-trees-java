package com.grant.datastructures.tree;

public class KeyValuePair implements Comparable<KeyValuePair> {
    private String key;
    private String value;
    
    public KeyValuePair(String key, String value) {
	this.key=key;
	this.value=value;
    }
    
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public int compareTo(KeyValuePair keyValuePair) {
	return this.key.compareTo(keyValuePair.getKey());
    }
    
    @Override
    public String toString()
    {
	return this.key + " : " + this.value.toString();
	
    }
    
    @Override
    public boolean equals(Object obj) {
        return getKey().equals(((KeyValuePair) obj).getKey());
    }
}
