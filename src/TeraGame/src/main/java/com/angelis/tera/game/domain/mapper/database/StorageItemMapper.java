package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.StorageItemEntity;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.storage.StorageItem;

public class StorageItemMapper extends AbstractDatabaseMapper<StorageItemEntity, StorageItem> {

    @Override
    protected StorageItemEntity createNewEmptyEntity() {
        return new StorageItemEntity();
    }

    @Override
    public void map(final StorageItem model, final StorageItemEntity entity) {
        entity.setItemId(model.getItemId());
        entity.setCount(model.getCount());
        entity.setColor(model.getColor());
        entity.setCreatorName(model.getCreatorName());
    }

    @Override
    protected void finalizeDependencies(final StorageItem model, final StorageItemEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    // ENTITY -> MODEL
    @Override
    protected StorageItem createNewEmptyModel() {
        return new StorageItem();
    }

    @Override
    public void map(final StorageItemEntity entity, final StorageItem model) {
        model.setItem(new Item(entity.getItemId()));
        model.setCount(entity.getCount());
        model.setColor(entity.getColor());
        model.setCreatorName(entity.getCreatorName());
    }

    @Override
    protected void finalizeDependencies(final StorageItemEntity entity, final StorageItem model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
     // No dependency
    }

    @Override
    public void merge(final StorageItemEntity currentEntity, final StorageItemEntity existingEntity) {
        BeanUtils.fill(StorageItemEntity.class, currentEntity, existingEntity);
    }
}
