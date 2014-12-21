package com.angelis.tera.game.domain.dao.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.game.domain.entity.database.ZoneEntity;
import com.angelis.tera.game.process.services.DatabaseService;

public class ZoneDAO extends AbstractDatabaseDAO<ZoneEntity> {

    public ZoneDAO() {
        super(ZoneEntity.class);
    }

    public ZoneEntity readByDatas(final byte[] datas) {
        ZoneEntity zone = null;
        
        final Transaction transaction = this.getTransaction();
        
        final Query query = this.createQuery("from ZoneEntity where datas = :datas");
        query.setBinary("datas", datas);
        
        zone = (ZoneEntity) query.uniqueResult();
        
        transaction.commit();
        
        return zone;
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }

}
