package com.bikatoo.lancer.common.model;

import java.util.Map.Entry;

public class MapEntry<K, V> implements Entry<K, V> {
  private K key;
  private V value;

  public MapEntry() {
  }

  public MapEntry(K k, V v) {
    this.key = k;
    this.value = v;
  }

  public K getKey() {
    return this.key;
  }

  public V getValue() {
    return this.value;
  }

  public V setValue(V value) {
    this.value = value;
    return this.value;
  }

  public K setKey(K key) {
    this.key = key;
    return this.key;
  }
}