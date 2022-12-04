
public class Value<K, V> {
       
	K key;
    V value;
    Value<K, V> next;

     public Value(K key, V value, Value<K, V> next) {
           this.key = key;
           this.value = value;
           this.next = next;
      }

	@Override
	public String toString() {
		return "Value [key=" + key + ", value=" + value + ", next=" + next + "]";
	}
     
     
    
}
