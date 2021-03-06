package questao03;

public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 16;

    private int n;                                // number of key-value pairs
    private int m;                                // hash table size
    private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    } 

    public SeparateChainingHashST(int m) {
        this.m = m;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    } 

    // resize the hash table to have the given number of chains,
    // rehashing all of the keys
    
    /*private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }*/
    
    private int hash(Key key) {
    	int h = 0;
    	for (int i = 0; i < ((String) key).length(); i++){
    		h = (31 * h + ((String) key).charAt(i)) % m;
    	}
        return h;
    }

    public int size() {
        return n;
    } 

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    } 

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    } 

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if average length of list >= 10
        //if (n >= 10*m) resize(2*m);

        int i = hash(key);
        if (!st[i].contains(key)) n++;
        st[i].put(key, val);
    } 

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        //if (m > INIT_CAPACITY && n <= 2*m) resize(m/2);
    } 

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    } 

    public static void main(String[] args){ 
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            key = key.replace(".","");
            key = key.replace(",","");
            key = key.replace(":","");
            key = key.replace(";","");
            key = key.replace("!","");
            key = key.replace("\"","");
            key = key.replace("-","");
            key = key.replace("\'","");
            key = key.replace("(","");
            key = key.replace(")","");
            key = key.replace("?","");
            key = key.replace("`","");
            key = key.replace("_","");
            key = key.replace("?","");
            st.put(key, i);
        }

        // print keys
        for (int i = 0; i < st.m; i++){
        	StdOut.println((i+1) + " " + st.st[i].size()); 
        }
        
    }

}