package com.example.datastructurevisualizer;

/**
 * TODO comment
 *
 * TODO implement special HashTable functionality (what?)
 */
public class HashTable extends DataStructureVisualizer {

    // Hash codes.
    // TODO implement more
    static final int LINEAR_MOD = 0;

    // Code determining the hashing function.
    int hashCode;

    // Number of hash buckets.
    int numBuckets;

    // Table containing keys.
    LinkedList hashTable[];

    /**
     * Constructor for HashTable, determines the hashing function.
     *
     * @param hashCode the code that determines the hashing function.
     */
    HashTable(int numBuckets, int hashCode) {
        this.hashCode = hashCode;
        this.numBuckets = numBuckets;
        hashTable = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; ++i)
            hashTable[i] = new LinkedList();

    }

    /**
     * Hashes a key with a simple modulus operation.
     *
     * @param key the key to be hashed.
     * @return the bucket associated with the key.
     */
    private int linearModHash(int key) {
        return key % hashTable.length;

    }

    /**
     * Uses the hash function to hash the inputed key.
     *
     * @return the hash bucket of the inputed key.
     */
    public int Hash(int key) {

        // Uses a switch to determine the hash function.
        switch(hashCode) {

            // Returns linearModHash.
            case (LINEAR_MOD): return linearModHash(key);
            // Returns 0 if there is no hash function (should not happen).
            default: return 0;

        }
    }

    /**
     * Inserts a key into the hashTable without an animation.
     *
     * @param key the key to be inserted.
     */
    @Override
    public void insertNoAnim(int key) {
        hashTable[Hash(key)].insertNoAnim(key);

    }

    /**
     * Inserts a key into the hashTable with an animation.
     *
     * @param key the key to be inserted.
     */
    @Override
    protected void insertAnim(int key) {
        hashTable[Hash(key)].insertAnim(key);

    }

    /**
     * Removes an item from the hashTable without an animation.
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeNoAnim(int key) {
        hashTable[Hash(key)].removeNoAnim(key);

    }

    /**
     * Removes an item from the hashTable with an animation.
     *
     * @param key the key to be removed.
     */
    @Override
    protected void removeAnim(int key) {
        hashTable[Hash(key)].removeAnim(key);

    }
}