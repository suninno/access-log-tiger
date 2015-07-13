package com.lge.cto.swinfra.tiger.service;

import java.util.HashMap;
import java.util.TreeMap;



public class ServiceRoot {
    
    protected static <K,V> HashMap<K,V> newHashMap() {
    	return new HashMap<K,V>();
    }
    
    protected static <K,V> TreeMap<K,V> newTreeMap() {
    	return new TreeMap<K, V>();
    }
}
