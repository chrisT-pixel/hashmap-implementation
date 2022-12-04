import java.util.Arrays;
import java.util.Objects;



public class HashMapChaining<K, V> {
	
	public Value<K, V>[] table;   //Array of Entry.
    public int capacity = 1;  //Initial capacity of HashMap
    public double loadThreshold;
    public int size = 0;

 
    @SuppressWarnings("unchecked")
    public HashMapChaining(double loadThreshold) {
        table = new Value[capacity];
        this.loadThreshold = loadThreshold;
		
    }
    
    
    
   
    public Value<K, V>[] getTable() {
		return table;
	}




	public void setTable(Value<K, V>[] table) {
		this.table = table;
	}




	/**
     * Method allows you put key-value pair in HashMapCustom.
     * If the map already contains a mapping for the key, the old value is replaced.
     * Note: method does not allows you to put null key though it allows null values.
     * Implementation allows you to put custom objects as a key as well.
     * Key Features: implementation provides you with following features:-
     * >provide complete functionality how to override equals method.
     * >provide complete functionality how to override hashCode method.
     *
     * @param newKey
     * @param data
     */
    public void put(K newKey, V data, boolean increaseSize) {
    	
    	if (newKey == null) {
            
    		System.out.println("Item neds a key so hash can be created");
    		return;    //does not allow to store null.
    		
    	}
    	
    	if(increaseSize) {
         	 this.size++;
         }
      	
      	 if(loadReached()) {
       		reHashLarger(newKey, data);
       		return;
       	}

        //calculate hash of key.
        int hash = hash(newKey);
        
        //create new entry.
        Value<K, V> newEntry = new Value<K, V>(newKey, data, null);
        
        //if table location does not contain any entry, store entry there.
        if (table[hash] == null) {
        	
        	table[hash] = newEntry;
        } 
        
        else {
           
        	Value<K, V> previous = null;
        	Value<K, V> current = table[hash];

            while (current != null) { //we have reached last entry of bucket.
                
            	if (current.key.equals(newKey)) {
                    
            		if (previous == null) {  //node has to be insert on first of bucket.
                        
            			newEntry.next = current.next;
                        this.table[hash] = newEntry;
                        
                        return;
                    } 
            		
            		else {
                        
            			newEntry.next = current.next;
                        previous.next = newEntry;
                       
                       
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
        
 
    }

    /**
     * Method returns value corresponding to key.
     *
     * @param key
     */
    public V get(K key) {
        
    	int hash = hash(key);
        
    	if (table[hash] == null) {
            
    		return null;
        } 
    	
    	else {
            
    		Value<K, V> temp = table[hash];
            
    		while (temp != null) {
                
    			if (temp.key.equals(key))
                    
    				return temp.value;
                
    			temp = temp.next; //return value corresponding to key.
            }
            
    		return null;   //returns null if key is not found.
       
    	}
    }


    /**
     * Method removes key-value pair from HashMapCustom.
     *
     * @param key
     */
    public void remove(K deleteKey, boolean decreaseSize) {

        int hash = hash(deleteKey);

        if (table[hash] == null) {
            return;
        } 
        
         else {
            
        	Value<K, V> previous = null;
            Value<K, V> current = table[hash];

            while (current != null) { //we have reached last entry node of bucket.
                
            	if (current.key.equals(deleteKey)) {
                    
            		if (previous == null) {  //delete first entry node.
                        
            			table[hash] = table[hash].next;
            			
            			if(decreaseSize) {
            	        	 this.size--;
            	        }
            	     	
            	     	 if(loadSpaceAvailable()) {
            	      		reHashSmaller(deleteKey);
            	      		return;
            	      	}
            			
                        
            			return;
                    } 
            		
            		else {
                        
            			previous.next = current.next;
            			
            			if(decreaseSize) {
            	        	 this.size--;
            	        }
            	     	
            	     	 if(loadSpaceAvailable()) {
            	      		reHashSmaller(deleteKey);
            	      		return;
            	      	}
            			
                        return;
                    }
                }
                
            	previous = current;
                current = current.next;
            }
            
            if(decreaseSize) {
           	 this.size--;
           }
        	
        	 if(loadSpaceAvailable()) {
         		reHashSmaller(deleteKey);
         		return;
         	}
        }

    }


    /**
     * Method displays all key-value pairs present in HashMapCustom.,
     * insertion order is not guaranteed, for maintaining insertion order
     * refer LinkedHashMapCustom.
     *
     * @param key
     */
    public void display() {

        for (int i = 0; i < capacity; i++) {
            
        	if (table[i] != null) {
                
        		Value<K, V> entry = table[i];
                
        		while (entry != null) {
                    System.out.print("{" + entry.key + "=" + entry.value + "}" + " ");
                    entry = entry.next;
                }
            }
        }

    }

    /**
     * Method implements hashing functionality, which helps in finding the appropriate
     * bucket location to store our data.
     * This is very important method, as performance of HashMapCustom is very much
     * dependent on  this method's implementation.
     *
     * @param key
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public boolean isEmpty() {
    	
    	if(this.size > 0) {
    		return false;
    	}
    	
    	else {
    		return true;
    	}
    }
    
    @SuppressWarnings("unchecked")
    public void reHashLarger(K newKey, V data) {
    	
    	int newSize = this.table.length * 2;
    	Value<K, V>[] copyTable = Arrays.copyOf(this.table, this.table.length);
    	
    	this.capacity = newSize;
    	this.setTable(new Value[newSize]); 
    	
    	for(int i = 0; i < copyTable.length; i++) {
    		
    		if(copyTable[i] != null) {
    			
    			this.put(copyTable[i].key, copyTable[i].value, false);
    			
    			Value<K, V> current = copyTable[i];
    			
    			while(current.next != null) {
    				
    				this.put(current.next.key, current.next.value, false);
    				current = current.next;
    			}
    			
    		}
    	}
    	
    	this.put(newKey, data, false);
    	
    }
    
    public void reHashSmaller(K deleteKey) {
    	
    	double dubSize = (double)this.size;
    	double newSize = dubSize / this.loadThreshold;
    	int newSizeInt = (int)newSize;
    	Value<K, V>[] copyTable = Arrays.copyOf(this.table, this.table.length);
    	
    	this.capacity = newSizeInt;
    	this.setTable(new Value[newSizeInt]); 
    	
    	for(int i = 0; i < copyTable.length; i++) {
    		
    		if(copyTable[i] != null) {
    			
    			this.put(copyTable[i].key, copyTable[i].value, false);
    			
    			Value<K, V> current = copyTable[i];
    			
    			while(current.next != null) {
    				
    				this.put(current.next.key, current.next.value, false);
    				current = current.next;
    			}
    			
    		}
    	}
    	
    	this.remove(deleteKey, false);
    	
    }
    
    
    public boolean loadReached() {
    	
    	double dubLength = (double)this.table.length;
    	double dubSize = (double)this.size;
    	
    	if(dubSize / dubLength > this.loadThreshold) {
    		
    		return true;
    	}
    	
    	else {
    		return false;
    	}
    }
    
    public boolean loadSpaceAvailable() {
    	
    	double dubLength = (double)this.table.length;
    	double dubSize = (double)this.size;
    	
    	if(dubSize / dubLength < this.loadThreshold) {
    		return true;
    	}
    	
    	else {
    		return false;
    	}
    }




	@Override
	public int hashCode() {
		return Objects.hash(capacity, loadThreshold, size);
	}




	@Override
	public String toString() {
		return "HashMapChaining [table=" + Arrays.toString(table) + ", capacity=" + capacity + ", loadThreshold="
				+ loadThreshold + ", size=" + size + "]";
	}
	
	




	
    

}
