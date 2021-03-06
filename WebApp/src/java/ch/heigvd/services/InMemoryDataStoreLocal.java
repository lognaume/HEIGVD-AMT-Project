package ch.heigvd.services;

import java.util.List;
import javax.ejb.Local;
import ch.heigvd.model.entities.KebabShop;

/**
 * 
 * @author Guillaume Milani & Maxime Guillod
 */
@Local
public interface InMemoryDataStoreLocal {
    int insertKebabShop(KebabShop kebabShop);
    KebabShop findKebabShop(int id) throws IndexOutOfBoundsException;
    List<KebabShop> findAllKebabShops();
}
