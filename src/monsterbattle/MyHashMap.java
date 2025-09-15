
package monsterbattle;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap <K, V> {
    
    public List<V> values() {
        List<V> valueList = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                valueList.add(entry.value);
                entry = entry.next;
            }
        }
        return valueList;
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    

    private int capacity = 100;  
    private Entry<K, V>[] table;

    public MyHashMap() {
        table = new Entry[capacity];
    }

    private int hash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> current = table[index];
            Entry<K, V> prev = null;
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; 
                    return;
                }
                prev = current;
                current = current.next;
            }
            prev.next = newEntry;  
        }
    }
    public void remove(K key) {
        int index = hash(key);
        Entry<K,V> current = table[index];
        Entry<K,V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;  
                } else {
                    prev.next = current.next;  
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;  
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    
}

