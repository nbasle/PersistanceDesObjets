package com.yaps.petstore.persistence;

import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.HashmapAccessException;
import com.yaps.petstore.exception.ObjectNotFoundException;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses a HashTable to store objects in it and serializes the hashmap.
 */
public final class HashmapAccessor {

    // ======================================
    // =             Attributes             =
    // ======================================
    private Map _hashmap = new HashMap();

    // ======================================
    // =            Constructors            =
    // ======================================
    public HashmapAccessor() {
    }
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets an object from the HashMap.
     *
     * @param id                Object identifier to be found in the hastable
     * @param hashmapFileName name of the file saved on disk
     * @return Object the object with all its attributs set
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     * @throws HashmapAccessException     is thrown if there's a persistent problem
     */
    public Object select(final String id, final String hashmapFileName) throws ObjectNotFoundException {

        // Loads the Hastable
        loadHastable(hashmapFileName);

        // If the given id doesn't exist we throw a ObjectNotFoundException
        if (!_hashmap.containsKey(id)) {
            throw new ObjectNotFoundException();
        }

        return _hashmap.get(id);
    }

    /**
     * This method return all the objects that are in the serialized hashmap.
     *
     * @param persistentLayerName Name of the file from where the objects are returned
     * @return collection of objects
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws HashmapAccessException     is thrown if there's a persistent problem
     */
    public Collection selectAll(final String persistentLayerName) throws ObjectNotFoundException {

        // Loads the Hastable with all the airports
        loadHastable(persistentLayerName);

        // If the hastable is empty we throw a DataNotFoundException
        if (_hashmap.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        return _hashmap.values();
    }

    /**
     * This method inserts an object into a HashMap and serializes the Hastable on disk.
     *
     * @param object            Object to be inserted into the hastable
     * @param id                identifier of the object to be inserted
     * @param hashmapFileName name of the file saved on disk
     * @throws DuplicateKeyException is thrown when an identical object is already in the hastable
     * @throws HashmapAccessException   is thrown if there's a persistent problem
     */
    public void insert(final Object object, final String id, final String hashmapFileName) throws DuplicateKeyException {

        // Loads the Hastable with all the objects
        loadHastable(hashmapFileName);

        // If the given id already exists we cannot insert the new object
        if (_hashmap.containsKey(id)) {
            throw new DuplicateKeyException();
        }

        // Puts the object into the hastable
        _hashmap.put(id, object);

        // Saves the hastable with all the objects
        saveHastableOnDisk(hashmapFileName);
    }

    /**
     * This method updates an object of the HashMap and serializes the Hastable on disk.
     *
     * @param object            Object to be updated from the hastable
     * @param id                identifier of the object to be updated
     * @param hashmapFileName name of the file saved on disk
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     * @throws HashmapAccessException     is thrown if there's a persistent problem
     */
    public void update(final Object object, final String id, final String hashmapFileName) throws ObjectNotFoundException, DuplicateKeyException {
        remove(id, hashmapFileName);
        insert(object, id, hashmapFileName);
    }

    /**
     * This method deletes an object from the HashMap and serializes the Hastable on disk.
     *
     * @param id                object identifier to be deleted from the hastable
     * @param hashmapFileName name of the file saved on disk
     * @throws ObjectNotFoundException is thrown if the object id not found in the hastable
     * @throws HashmapAccessException     is thrown if there's a persistent problem
     */
    public void remove(final String id, final String hashmapFileName) throws ObjectNotFoundException {

        // Loads the Hastable
        loadHastable(hashmapFileName);

        // If the given id does'nt exist we cannot remove the object from the hashmap
        if (!_hashmap.containsKey(id)) {
            throw new ObjectNotFoundException();
        }

        // The object is removed from the hastable
        _hashmap.remove(id);

        // Saves the hastable
        saveHastableOnDisk(hashmapFileName);
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    // This method loads the hastable with the file saved on disk
    private void loadHastable(final String hashmapFileName) throws HashmapAccessException {

        try {
            final FileInputStream fin = new FileInputStream(hashmapFileName);
            final ObjectInputStream in = new ObjectInputStream(fin);

            // Reads the values from the file
            _hashmap = (HashMap) in.readObject();

            in.close();
        } catch (FileNotFoundException e) {
            // The first call to this method, the file doesn't exist so nothing is done
        } catch (Exception e) {
            throw new HashmapAccessException("Cannot load " + hashmapFileName + " !!!", e);
        }
    }

    // This method saves the hastable on disk
    private void saveHastableOnDisk(final String hashmapFileName) throws HashmapAccessException {

        try {
            final FileOutputStream fout = new FileOutputStream(hashmapFileName);
            final ObjectOutputStream out = new ObjectOutputStream(fout);

            // Saves the hashmap to disk
            out.writeObject(_hashmap);

            out.close();
        } catch (IOException e) {
            throw new HashmapAccessException("Cannot save " + hashmapFileName + " !!!", e);
        }
    }
}
