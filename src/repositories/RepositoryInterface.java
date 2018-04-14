package repositories;

import model.Swipe;
import java.util.*;

public interface RepositoryInterface {

    /**
     *
     * @param item
     */
    void add(Swipe item);

    /**
     *
     * @param id
     * @return
     */
    Swipe getItem(int id);

    List<Swipe> getItems();

    /**
     *
     * @param id
     */
    
    void remove(int id);

    void setItems(List<Swipe> items);

    /**
     *
     * @param filename
     */
    
    void store(String filename);

    /**
     *
     * @return
     */
    @Override
    String toString();
    
}
