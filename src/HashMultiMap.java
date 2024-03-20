import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap;

public class HashMultiMap <K,V>{
    protected static class Map<K,V> {

        protected static class MapEntry<K,V> {
            private K k;
            private V v;
            public MapEntry(K key, V value) {
                k = key;
                v = value;
            }
            public K getKey() {
                return k;
            }
            public void setKey(K key) {
                k = key;
            }
            public V getValue() {
                return v;
            }
            public V setValue(V value) {
                V old = v;
                v = value;
                return old;
            }
        }

        private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

        public Map() {}

        private int findIndex(K key) {
            int n = table.size();
            for(int j = 0; j < n; j++) {
                if(table.get(j).getKey().equals(key)) {
                    return j;
                }
            }
            return -1;
        }

        public int size() {
            return table.size();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public V get(K key) {
            int j = findIndex(key);
            if(j == -1) {
                return null;
            }
            else {
                return table.get(j).getValue();
            }
        }

        public V put(K key, V value) {
            int j = findIndex(key);
            if(j == -1) {
                table.add(new MapEntry<>(key, value));
                return null;
            }
            else {
                return table.get(j).setValue(value);
            }
        }

        public V remove(K key) {
            int j = findIndex(key);
            int n = size();
            if(j == -1) {
                return null;
            }
            V answer = table.get(j).getValue();
            if(j != n - 1) {
                table.set(j, table.get(n - 1));
            }
            table.remove(n - 1);
            return answer;
        }
    }
    Map<K, List<V>> map = new Map<>();
    int total = 0;

    public HashMultiMap() {}

    public int size() {
        return total;
    }

    public boolean isEmpty() {
        return total == 0;
    }

    Iterable<V> get(K key) {
        List<V> secondary = map.get(key);
        if(secondary != null) {
            return secondary;
        }
        return new ArrayList<>();
    }

    void put(K key, V value) {
        List<V> secondary = map.get(key);
        if(secondary == null) {
            secondary = new ArrayList<>();
            map.put(key, secondary);
        }
        secondary.add(value);
        total++;
    }

    boolean remove(K key, V value) {
        boolean wasRemoved = false;
        List<V> secondary = map.get(key);
        if(secondary != null) {
            wasRemoved = secondary.remove(value);
            if(wasRemoved) {
                total--;
                if(secondary.isEmpty()) {
                    map.remove(key);
                }
            }
        }
        return wasRemoved;
    }

    Iterable<V> removeAll(K key) {
        List<V> secondary = map.get(key);
        if(secondary != null) {
            total -= secondary.size();
            map.remove(key);
        }
        else {
            secondary = new ArrayList<>();
        }
        return secondary;
    }
}
