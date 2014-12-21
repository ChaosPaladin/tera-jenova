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
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.angelis.tera.common.utils.BitConverter;
import com.angelis.tera.common.utils.PrintUtils;

public class MainCreatureExtractor {
    private static int mapId;

    private static Map<Integer, Element> elements = new FastMap<>();

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("creatures");
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

            if (line.substring(4, 8).equals("4BC8")) {
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
        StreamResult result = new StreamResult(new File("C:/Users/Angelis/Desktop/t-watch-p5/creature-converted.xml"));

        transformer.transform(source, result);
    }

    private static void parseLine(Document doc, String line) throws Exception {
        String npcRealData = line.substring(52);

        float x = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(0, 8))), 0);
        float y = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(8, 16))), 0);
        float z = BitConverter.toSingle(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(16, 24))), 0);
        int heading = BitConverter.toInt16(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(24, 28))), 0);
        int speed = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(28, 36))), 0);
        int id = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(36, 44))), 0);
        int creatureType = BitConverter.toInt16(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(44, 48))), 0);
        int modelId = BitConverter.toInt32(PrintUtils.hex2bytes(PrintUtils.reverseHex(npcRealData.substring(48, 56))), 0);
        // shit 18
        String inoffensive = npcRealData.substring(74, 76);

        Element creatureRootElement = elements.get(id);
        if (creatureRootElement == null) {
            creatureRootElement = doc.createElement("creature");
            creatureRootElement.setAttribute("id", String.valueOf(id));
            creatureRootElement.setAttribute("creatureType", String.valueOf(creatureType));
            creatureRootElement.setAttribute("modelId", String.valueOf(modelId));
            creatureRootElement.setAttribute("inoffensive", inoffensive);
            creatureRootElement.setAttribute("speed", String.valueOf(speed));
            elements.put(id, creatureRootElement);
        }

        Element spawnRootElement = doc.createElement("spawn");
        spawnRootElement.setAttribute("mapId", String.valueOf(mapId));
        spawnRootElement.setAttribute("x", String.valueOf(x));
        spawnRootElement.setAttribute("y", String.valueOf(y));
        spawnRootElement.setAttribute("z", String.valueOf(z));
        spawnRootElement.setAttribute("heading", String.valueOf(heading));
        creatureRootElement.appendChild(spawnRootElement);
    }
}
