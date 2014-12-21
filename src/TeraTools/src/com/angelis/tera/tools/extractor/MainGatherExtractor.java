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
    private static int mapId;

    private static Map<Integer, Element> elements = new FastMap<>();
    private static Map<Element, Set<Element>> spawns = new FastMap<>();

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("gathers");
        doc.appendChild(rootElement);
        
        BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Angelis/Desktop/t-watch-p5/log.txt")));
        
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.length() < 8) {
                continue;
            }
            
            if (line.substring(4, 8).equals("A1B8")) {
                mapId = ByteBuffer.wrap((PrintUtils.hex2bytes(PrintUtils.reverseHex(line.substring(8, 16))))).getInt();
                continue;
            }
            
            if (line.substring(4, 8).equals("839F")) {
                parseLine(doc, line);
            }
        }
        br.close();

        for (Element creatureElement : elements.values()) {
            rootElement.appendChild(creatureElement);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:/Users/Angelis/Desktop/t-watch-p5/gather-converted.xml"));

        transformer.transform(source, result);
    }
    
    private static void parseLine(Document doc, String line) throws Exception {
        String npcRealData = line.substring(24);

        int id = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(0, 8))), 0);
        // gatherCount
        float x = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(16, 24))), 0);
        float y = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(24, 32))), 0);
        float z = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(32, 40))), 0);

        
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
        
        Element spawnRootElement = doc.createElement("spawn");
        spawnRootElement.setAttribute("mapId", String.valueOf(mapId));
        spawnRootElement.setAttribute("x", String.valueOf(x));
        spawnRootElement.setAttribute("y", String.valueOf(y));
        spawnRootElement.setAttribute("z", String.valueOf(z));
        
        boolean contained = false;
        for (Element element : spawnElements) {
            if (((Node) element).isEqualNode((Node) spawnRootElement)) {
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
