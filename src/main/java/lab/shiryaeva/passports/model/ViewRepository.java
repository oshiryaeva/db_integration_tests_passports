package lab.shiryaeva.passports.model;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface ViewRepository<T, ID extends Serializable> extends Repository<T, ID> {
    Iterable<T> findAll();
    long count();
}