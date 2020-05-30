package pl.edu.agh.soa.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public abstract class AbstractRepository<E> {

    @PersistenceContext(unitName = "TrainsUnit")
    protected EntityManager em;

    protected CriteriaBuilder cb;

    @PostConstruct
    public void loadCriteriaBuilder() {
        cb = em.getCriteriaBuilder();
    }

    abstract Class<E> getType();

    public void create(E entity) {
        em.persist(entity);
    }

    public E edit(E object){
        return em.merge(object);
    }

    public void delete(Object id) {
        E o = this.find(id);
        em.remove(o);
    }

    public void flush() {
        em.flush();
    }

    public E find(Object id) {
        return em.find(this.getType(), id);
    }

    public List<E> findAll() {
        var query = cb.createQuery(this.getType());
        query.select(query.from(this.getType()));
        return this.em.createQuery(query)
                .getResultList();
    }
}
