package com.angelis.tera.tools.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.angelis.tera.common.utils.BitConverter;
import com.angelis.tera.common.utils.PrintUtils;

public class MainCreatureExtractor implements Runnable {
    
    private final static String SM_LOAD_TOP_OPCODE = "3EB5";
    private final static String SM_CREATURE_SPAWN = "60EE";
    
    private final File file;
    
    private int mapId;

    private final Map<Integer, Element> creatures = new FastMap<>();
    private final Map<Integer, Element> creatureTemplates = new FastMap<>();

    public MainCreatureExtractor(final File file) {
        this.file = file;
    }
    
    @Override
    public void run() {
        
        try {
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            final Document creatureDoc = docBuilder.newDocument();
            final Document templateDoc = docBuilder.newDocument();
            
            final File folder = file.getParentFile();
            final BufferedReader br = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.length() < 8) {
                    continue;
                }

                if (line.substring(4, 8).equals(SM_LOAD_TOP_OPCODE)) {
                    mapId = ByteBuffer.wrap((PrintUtils.hex2bytes(PrintUtils.reverseHex(line.substring(8, 16))))).getInt();
                    continue;
                }

                if (line.substring(4, 8).equals(SM_CREATURE_SPAWN)) {
                    parseLine(creatureDoc, templateDoc, line);
                }
            }
            br.close();

            createCreatureSpawns(creatureDoc, folder);
            createCreatureTemplates(templateDoc, folder);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    private final void createCreatureSpawns(final Document doc, final File folder) throws TransformerException {
        final Element rootElement = doc.createElement("creature_spawns");
        rootElement.setAttribute("xmlns", "http://angelis.com/creature_spawns");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:schemaLocation", "http://angelis.com/creature_spawns ../schema/creature_spawns.xsd ");
        doc.appendChild(rootElement);
        
        for (final Element creatureElement : creatures.values()) {
            rootElement.appendChild(creatureElement);
        }

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        final DOMSource source = new DOMSource(doc);
        final StreamResult result = new StreamResult(new File(folder.getAbsolutePath()+"/creature_spawns.xml"));

        transformer.transform(source, result);
    }

    private final void createCreatureTemplates(final Document doc, final File folder) throws TransformerException {
        final Element rootElement = doc.createElement("creature_templates");
        rootElement.setAttribute("xmlns", "http://angelis.com/creature_templates");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:schemaLocation", "http://angelis.com/creature_templates ../schema/creature_templates.xsd ");
        doc.appendChild(rootElement);
        
        for (final Element creatureElement : creatureTemplates.values()) {
            rootElement.appendChild(creatureElement);
        }

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        final DOMSource source = new DOMSource(doc);
        final StreamResult result = new StreamResult(new File(folder.getAbsolutePath()+"/creature_templates.xml"));

        transformer.transform(source, result);
    }
    
    private void parseLine(final Document creatureDoc, final Document templateDoc, final String line) throws Exception {
        final String npcRealData = line.substring(52);

        final float x = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(0, 8))), 0);
        final float y = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(8, 16))), 0);
        final float z = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(16, 24))), 0);
        final int heading = BitConverter.toInt16(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(24, 28))), 0);
        //int speed = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(28, 36))), 0);
        final int id = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(36, 44))), 0);
        final short huntingZoneId = BitConverter.toInt16(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(44, 48))), 0);
        final int modelId = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(48, 56))), 0);
        // shit 18
        final String inoffensive = npcRealData.substring(74, 76);

        final int npcFullId = (id+(huntingZoneId*100000));
        
        Element creatureRootElement = creatures.get(npcFullId);
        if (creatureRootElement == null) {
            creatureRootElement = creatureDoc.createElement("creature_spawn");
            creatureRootElement.setAttribute("id", String.valueOf(id));
            creatures.put(npcFullId, creatureRootElement);
        }
        final Element spawnRootElement = creatureDoc.createElement("spawn");
        spawnRootElement.setAttribute("mapId", String.valueOf(mapId));
        spawnRootElement.setAttribute("x", String.valueOf(x));
        spawnRootElement.setAttribute("y", String.valueOf(y));
        spawnRootElement.setAttribute("z", String.valueOf(z));
        spawnRootElement.setAttribute("heading", String.valueOf(heading));
        creatureRootElement.appendChild(spawnRootElement);
        
        Element templateRootElement = creatureTemplates.get(npcFullId);
        if (templateRootElement == null) {
            templateRootElement = templateDoc.createElement("creature_template");
            templateRootElement.setAttribute("npc_full_id", String.valueOf(npcFullId));
            templateRootElement.setAttribute("rank", "MONSTER");
            templateRootElement.setAttribute("exp", "0");
            templateRootElement.setAttribute("hunting_zone_id", String.valueOf(huntingZoneId));
            templateRootElement.setAttribute("model_id", String.valueOf(modelId));
            templateRootElement.setAttribute("offensive", "00".equals(inoffensive) ? "1" : "0");
            creatureTemplates.put(npcFullId, templateRootElement);
        }
    }
}
