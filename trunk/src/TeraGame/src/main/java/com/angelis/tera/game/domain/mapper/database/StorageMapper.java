package com.angelis.tera.game.domain.mapper.database;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.StorageEntity;
import com.angelis.tera.game.domain.entity.database.StorageItemEntity;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.StorageItem;

public class StorageMapper extends AbstractDatabaseMapper<StorageEntity, Storage> {
    
    // MODEL -> ENTITY
    @Override
    protected StorageEntity createNewEmptyEntity() {
        return new StorageEntity();
    }
    
    @Override
    public void map(final Storage model, final StorageEntity entity) {
        // DIRECT
        entity.setStorageType(model.getStorageType());
        entity.setSize(model.getSize());
        entity.setMoney(model.getMoney());
    }
    
    @Override
    protected void finalizeDependencies(final Storage model, final StorageEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // STORAGE ITEMS
        if (!excludedDependencies.contains(StorageEntity.class)) {
            final StorageItemMapper storageItemMapper = MapperManager.getDatabaseMapper(StorageItemMapper.class);
            final List<StorageItemEntity> storageItemEntities = new FastList<>();
            for (final Entry<Integer, StorageItem> entry : model.getStorageItems().entrySet()) {
                final StorageItemEntity storageItemEntity = storageItemMapper.map(entry.getValue());
                storageItemEntity.setSlot(entry.getKey());
                storageItemEntity.setStorage(entity);
                storageItemEntities.add(storageItemEntity);
            }
            entity.setStorageItems(storageItemEntities);
        }
    }

    // ENTITY -> MODEL
    @Override
    protected Storage createNewEmptyModel() {
        return new Storage();
    }
    
    @Override
    public void map(final StorageEntity entity, final Storage model) {
        // DIRECT
        model.setStorageType(entity.getStorageType());
        model.setSize(entity.getSize());
        model.setMoney(entity.getMoney());
    }

    @Override
    protected void finalizeDependencies(final StorageEntity entity, final Storage model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // STORAGE ITEMS
        if (!excludedDependencies.contains(StorageItemEntity.class)) {
            final StorageItemMapper storageItemMapper = MapperManager.getDatabaseMapper(StorageItemMapper.class);
            final Map<Integer, StorageItem> storageItems = new FastMap<>();
            for (final StorageItemEntity storageItemEntity : entity.getStorageItems()) {
                storageItems.put(storageItemEntity.getSlot(), storageItemMapper.map(storageItemEntity));
            }
            model.setStorageItems(storageItems);
        }
    }
    
    @Override
    public void merge(final StorageEntity currentEntity, final StorageEntity existingEntity) {
        BeanUtils.fill(StorageEntity.class, currentEntity, existingEntity);
    }
}
