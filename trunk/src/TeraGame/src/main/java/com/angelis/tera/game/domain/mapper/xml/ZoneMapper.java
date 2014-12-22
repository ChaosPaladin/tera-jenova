package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.domain.entity.xml.zones.ZoneEntity;
import com.angelis.tera.game.process.model.Zone;

public class ZoneMapper extends AbstractXMLMapper<ZoneEntity, Zone> {

    // ENTITY -> MODEL
    @Override
    protected Zone createNewEmptyModel() {
        return new Zone();
    }

    @Override
    public void map(final ZoneEntity entity, final Zone model) {
        model.setDatas(PrintUtils.hex2bytes(entity.getDatas()));
    }

    @Override
    protected void finalizeDependencies(final ZoneEntity entity, final Zone model) {
        // No dependency
    }

}
