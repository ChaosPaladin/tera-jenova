package com.angelis.tera.game.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.game.domain.dao.database.ZoneDAO;
import com.angelis.tera.game.domain.entity.database.ZoneEntity;
import com.angelis.tera.game.domain.mapper.database.ZoneMapper;
import com.angelis.tera.game.process.model.Zone;

public class ZoneDelegate extends AbstractDatabaseDelegate<ZoneEntity, Zone> {

    public ZoneDelegate() {
        super(ZoneMapper.class, ZoneDAO.class);
    }

    public Zone findByDatas(final byte[] datas) {
        return null;
    }
}
