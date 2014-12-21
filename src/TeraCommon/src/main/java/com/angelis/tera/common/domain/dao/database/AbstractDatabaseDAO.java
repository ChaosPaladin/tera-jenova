package com.angelis.tera.common.domain.dao.database;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

public abstract class AbstractDatabaseDAO<E extends AbstractDatabaseEntity> {

    private final Session session = this.getSession();
    private final Class<E> clazz;
    
    public AbstractDatabaseDAO(final Class<E> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public final E findById(final Integer id) {
        E entity = null;
        
        final Transaction transaction = this.getTransaction();
        entity = (E) session.get(clazz, id);
        transaction.commit();
        
        return entity;
    }

    @SuppressWarnings("unchecked")
    public final List<E> findAll() {
        return session.createQuery("from " + clazz.getName()).list();
    }

    public final void create(final E entity) {
        final Transaction transaction = this.getTransaction();
        final Integer id = (Integer) session.save(entity);
        entity.setId(id);
        transaction.commit();
    }

    public final void update(final E entity) {
        final Transaction transaction = this.getTransaction();

        session.merge(entity);

        transaction.commit();
    }

    public final void delete(final E entity) {
        final Transaction transaction = this.getTransaction();
        
        session.delete(session.merge(entity));

        transaction.commit();
    }

    public final void deleteById(final Integer entityId) {
        final E entity = findById(entityId);
        delete(entity);
    }
    
    public final Transaction getTransaction() {
        return this.session.beginTransaction();
    }
    
    public final Query createQuery(final String query) {
        return this.session.createQuery(query);
    }

    protected abstract Session getSession();
}
