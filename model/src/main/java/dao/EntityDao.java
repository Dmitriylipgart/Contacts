package dao;

import entity.Entity;
import java.util.List;

public interface EntityDao <T extends Entity> {
     void create(T t);
     T getById(int id);
     void update(T t);
     void delete(List<Integer> id);
     List <T> readAll(int page, int size);

}
