package com.angelis.tera.tools.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javolution.util.FastMap;
import javolution.util.FastSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angelis.tera.common.utils.BitConverter;
import com.angelis.tera.common.utils.PrintUtils;


public class MainGatherExtractor {
    private int mapId;

    private final Map<Integer, Element> elements = new FastMap<>();
    private final Map<Element, Set<Element>> spawns = new FastMap<>();

    public MainGatherExtractor(final File file) throws Exception {
        final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        final Document doc = docBuilder.newDocument();
        final Element rootElement = doc.createElement("gathers");
        rootElement.setAttribute("xmlns", "http://angelis.com/gathers");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:schemaLocation", "http://angelis.com/gather_spawns ../schema/gather_spawns.xsd ");
        doc.appendChild(rootElement);
        
        final File folder = file.getParentFile();
        final BufferedReader br = new BufferedReader(new FileReader(file));
        
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.length() < 8) {
                continue;
            }
            
            if (line.substring(4, 8).equals("3EB5")) {
                mapId = ByteBuffer.wrap((PrintUtils.hex2bytes(PrintUtils.reverseHex(line.substring(8, 16))))).getInt();
                continue;
            }
            
            if (line.substring(4, 8).equals("E4E2")) {
                parseLine(doc, line);
            }
        }
        br.close();

        for (final Element creatureElement : elements.values()) {
            rootElement.appendChild(creatureElement);
        }
        
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        final DOMSource source = new DOMSource(doc);
        final StreamResult result = new StreamResult(new File(folder.getAbsolutePath()+"/gather_spawns.xml"));

        transformer.transform(source, result);
    }
    
    private void parseLine(final Document doc, final String line) throws Exception {
        final String npcRealData = line.substring(24);

        final int id = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(0, 8))), 0);
        // gatherCount
        final float x = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(16, 24))), 0);
        final float y = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(24, 32))), 0);
        final float z = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(32, 40))), 0);

        
        Element creatureRootElement = elements.get(id);
        if (creatureRootElement == null) {
            creatureRootElement = doc.createElement("gather");
            creatureRootElement.setAttribute("id", String.valueOf(id));
            elements.put(id,  creatureRootElement);
        }
        
        Set<Element> spawnElements = spawns.get(creatureRootElement);
        if (spawnElements == null) {
            spawnElements = new FastSet<>();
            spawns.put(creatureRootElement, spawnElements);
        }
        
        final Element spawnRootElement = doc.createElement("spawn");
        spawnRootElement.setAttribute("mapId", String.valueOf(mapId));
        spawnRootElement.setAttribute("x", String.valueOf(x));
        spawnRootElement.setAttribute("y", String.valueOf(y));
        spawnRootElement.setAttribute("z", String.valueOf(z));
        
        boolean contained = false;
        for (final Element element : spawnElements) {
            if (((Node) element).equals(spawnRootElement)) {
                contained = true;
                break;
            }
        }
        
        if (!contained) {
            spawnElements.add(spawnRootElement);
            creatureRootElement.appendChild(spawnRootElement);
        }
    }
}
