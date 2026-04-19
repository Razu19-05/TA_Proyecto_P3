package pe.edu.pucp.SIME.DAO;

import java.util.List;

public interface BaseDAO <T, ID> {
    T load(ID id);
    T save(T t);
    T update(T t);
    void remove(T t);
}
