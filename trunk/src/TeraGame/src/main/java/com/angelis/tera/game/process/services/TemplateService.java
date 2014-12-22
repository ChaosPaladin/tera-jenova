package com.angelis.tera.game.process.services;

import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.creatures.template.CreatureTemplateEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.template.CreatureTemplateEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherTemplateEntity;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherTemplateEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.CreatureTemplateMapper;
import com.angelis.tera.game.domain.mapper.xml.GatherTemplateMapper;
import com.angelis.tera.game.process.model.template.CreatureTemplate;
import com.angelis.tera.game.process.model.template.GatherTemplate;

public class TemplateService extends AbstractService {
    /** LOGGER */
    private static Logger log = Logger.getLogger(AccountService.class.getName());
    
    private final Map<Integer, CreatureTemplate> creatureTemplates = new FastMap<>();
    private final Map<Integer, GatherTemplate> gatherTemplates = new FastMap<>();

    private TemplateService() {
    }

    @Override
    public void onInit() {
        // CREATURE TEMPLATE
        final CreatureTemplateMapper creatureTemplateMapper = MapperManager.getXMLMapper(CreatureTemplateMapper.class);
        final Set<CreatureTemplateEntity> creatureTemplateEntities = XMLService.getInstance().getEntity(CreatureTemplateEntityHolder.class).getCreatureTemplates();
        for (final CreatureTemplateEntity creatureTemplateEntity : creatureTemplateEntities) {
            this.creatureTemplates.put(creatureTemplateEntity.getFullId(), creatureTemplateMapper.map(creatureTemplateEntity));
        }
        XMLService.getInstance().clearEntity(CreatureTemplateEntityHolder.class);

        // GATHER TEMPLATE
        final GatherTemplateMapper gatherTemplateMapper = MapperManager.getXMLMapper(GatherTemplateMapper.class);
        final Set<GatherTemplateEntity> gatherTemplateEntities = XMLService.getInstance().getEntity(GatherTemplateEntityHolder.class).getGatherTemplates();
        for (final GatherTemplateEntity gatherTemplateEntity : gatherTemplateEntities) {
            this.gatherTemplates.put(gatherTemplateEntity.getId(), gatherTemplateMapper.map(gatherTemplateEntity));
        }
        XMLService.getInstance().clearEntity(GatherTemplateEntityHolder.class);

        log.info("TemplateService started");
    }

    @Override
    public void onDestroy() {
        log.info("TemplateService stopped");
    }

    public CreatureTemplate getCreatureTemplateByFullId(final int fullId) {
        return this.creatureTemplates.get(fullId);
    }

    public GatherTemplate getGatherTemplateById(final Integer id) {
        return this.gatherTemplates.get(id);
    }

    /** SINGLETON */
    public static TemplateService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final TemplateService instance = new TemplateService();
    }
}
