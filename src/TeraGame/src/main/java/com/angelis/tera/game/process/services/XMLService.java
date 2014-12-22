package com.angelis.tera.game.process.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.stream.XMLStreamException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javolution.util.FastMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.common.domain.xml.utils.XmlMerger;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.config.GlobalConfig;
import com.angelis.tera.game.domain.entity.xml.creatures.drop.CreatureDropEntityHolder;
import com.angelis.tera.game.domain.entity.xml.creatures.spawn.CreatureSpawnsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.creatures.template.CreatureTemplateEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherDropEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherSpawnEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherTemplateEntityHolder;
import com.angelis.tera.game.domain.entity.xml.items.ItemEntityHolder;
import com.angelis.tera.game.domain.entity.xml.mounts.MountEntityHolder;
import com.angelis.tera.game.domain.entity.xml.pegasus.PegasusFliesEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerBaseStatsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerClassSkillsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerExperienceEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerItemSetEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerRaceSkillsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.quests.QuestEntityHolder;
import com.angelis.tera.game.domain.entity.xml.tradelist.TradelistEntityHolder;
import com.angelis.tera.game.domain.entity.xml.zones.ZoneEntityHolder;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class XMLService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(XMLService.class.getName());

    /** CONSTANTS */
    private static enum Path {
        DATA("./data"), TEMP("./temp");

        public final String path;

        Path(final String path) {
            this.path = path;
        }
    }

    private final Map<Class<? extends AbstractXMLEntity>, AbstractXMLEntity> cache = new FastMap<Class<? extends AbstractXMLEntity>, AbstractXMLEntity>();

    private XMLService() {
    }

    @Override
    public void onInit() {
        final File data = new File(Path.DATA.path);
        if (!data.exists()) {
            data.mkdir();
        }

        final File bin = new File(Path.TEMP.path);
        if (!bin.exists()) {
            bin.mkdir();
        }

        
        this.checkBinFile(QuestEntityHolder.class, "quests");
        this.checkBinFile(MountEntityHolder.class, "mounts");
        this.checkBinFile(TradelistEntityHolder.class, "tradelists");
        this.checkBinFile(PegasusFliesEntityHolder.class, "pegasus_flies");
        
        this.checkBinFile(PlayerExperienceEntityHolder.class, "player_experiences");
        this.checkBinFile(PlayerBaseStatsEntityHolder.class, "player_basestats");
        this.checkBinFile(PlayerRaceSkillsEntityHolder.class, "player_race_skills");
        this.checkBinFile(PlayerClassSkillsEntityHolder.class, "player_class_skills");
        this.checkBinFile(PlayerItemSetEntityHolder.class, "player_itemsets");
        
        this.checkBinFile(CreatureSpawnsEntityHolder.class, "creature_spawns");
        this.checkBinFile(CreatureTemplateEntityHolder.class, "creature_templates");
        this.checkBinFile(CreatureDropEntityHolder.class, "creature_drops");
        
        this.checkBinFile(GatherSpawnEntityHolder.class, "gather_spawns");
        this.checkBinFile(GatherTemplateEntityHolder.class, "gather_templates");
        this.checkBinFile(GatherDropEntityHolder.class, "gather_drops");
        
        this.checkBinFile(ItemEntityHolder.class, "items");
        this.checkBinFile(ZoneEntityHolder.class, "zones");

        log.info("XMLService started");
    }

    @Override
    public void onDestroy() {
        this.cache.clear();
    }

    public void readXMLData(final Class<? extends AbstractXMLEntity> entityClass, final File xml) {
        if (!xml.exists()) {
            log.error("Can't find xml file : " + xml.getPath());
            return;
        }

        try {
            final File schemaFile = new File("data/schema/" + FilenameUtils.removeExtension(xml.getName()) + ".xsd");

            final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = schemaFactory.newSchema(schemaFile);

            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setEventHandler(new ValidationEventHandler() {
                @Override
                public boolean handleEvent(final ValidationEvent event) {
                    log.error(event.getMessage());
                    return false;
                }
            });
            unmarshaller.setSchema(schema);
            
            final File mergedXml = XmlMerger.processFile(xml);
            
            final AbstractXMLEntity entity = (AbstractXMLEntity) unmarshaller.unmarshal(mergedXml);

            if (entity == null) {
                log.error("Unable to unmarshal entity " + entityClass.getName());
                return;
            }

            entity.onLoad();
            this.cache.put(entityClass, entity);
        }
        catch (JAXBException | SAXException | IOException | XMLStreamException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void readBinData(final Class<? extends AbstractXMLEntity> entityClass, final File bin) {
        if (!bin.exists()) {
            log.error("Can't find bin file : " + bin.getPath());
            return;
        }

        final Kryo kryo = new Kryo();
        try {
            final Input input = new Input(new FileInputStream(bin));
            final AbstractXMLEntity entity = kryo.readObject(input, entityClass);
            input.close();

            entity.onLoad();
            this.cache.put(entityClass, entity);
        }
        catch (final FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void writeXMLData(final AbstractXMLEntity entity, final File xml) {
        try {
            final FileWriter out = new FileWriter(xml);
            final JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(entity, out);
            out.close();
        }
        catch (JAXBException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void writeBinData(final AbstractXMLEntity abstractXMLEntity, final File bin) {
        if (abstractXMLEntity == null) {
            return;
        }

        final Kryo kryo = new Kryo();

        try {
            final Output output = new Output(new FileOutputStream(bin));
            kryo.writeObject(output, abstractXMLEntity);
            output.close();
        }
        catch (final FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    public <T extends AbstractXMLEntity> T getEntity(final Class<T> entityClass) {
        @SuppressWarnings("unchecked")
        final
        T entity = (T) this.cache.get(entityClass);
        if (entity == null) {
            log.error(entityClass.getName() + " has no data");
        }

        return entity;
    }
    
    public <T extends AbstractXMLEntity> void clearEntity(final Class<T> entityClass) {
        this.cache.remove(entityClass);
    }

    private void checkBinFile(final Class<? extends AbstractXMLEntity> entityClass, final String... files) {
        for (final String file : files) {
            final File xml = new File(Path.DATA.path + File.separator + "xml" + File.separator + file + ".xml");
            if (!xml.exists()) {
                log.error("Can't find xml file : " + xml.getPath());
                return;
            }

            final File bin = new File(Path.TEMP.path + File.separator + file + ".bin");
            if (bin.exists() && GlobalConfig.GLOBAL_DATA_MANAGEMENT_USE_CACHE) {
                if (bin.lastModified() > xml.lastModified()) {
                    this.readBinData(entityClass, bin);
                    return;
                }
            }

            this.readXMLData(entityClass, xml);

            if (GlobalConfig.GLOBAL_DATA_MANAGEMENT_USE_CACHE) {
                this.writeBinData(this.cache.get(entityClass), bin);
            }
        }
    }

    /** SINGLETON */
    public static XMLService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final XMLService instance = new XMLService();
    }
}
