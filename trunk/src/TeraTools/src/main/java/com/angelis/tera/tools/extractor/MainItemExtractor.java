package com.angelis.tera.tools.extractor;

import java.io.File;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javolution.util.FastMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainItemExtractor {

    private static final String ITEMS_URL = "http://www.teratome.com/items";
    private static final String ITEM_URL = "http://www.teratome.com";
    
    private final Map<Integer, Data> datas = new FastMap<>();
    
    public class Data {
        int id;
        boolean trade = true;
        int saleValue;
        boolean storeInBank = true;
        boolean storeInGuildBank = true;
        
        int level;
        int requiredLevel;
        String category;
        boolean bindOnEquip;
        boolean beObtained = true;
        boolean beDestroyed = true;
    }
    
    public MainItemExtractor(final File file) throws Exception {
        final File folder = file.getParentFile();
        parseMainPage(ITEMS_URL);
        for (int i = 2 ; i < 19328 ; i++) {
            parseMainPage(ITEMS_URL+"/page/"+i);
        }
        buildXml(folder);
    }
    
    private final void parseMainPage(final String stringUrl) throws Exception {
        final Document doc = Jsoup.connect(stringUrl).get();
        final ListIterator<Element> elements = doc.select("tr").listIterator();
        while (elements.hasNext()) {
            final String itemUrl = elements.next().attr("data-href");
            if (StringUtils.isEmpty(itemUrl)) {
                continue;
            }
            
            parseItemPage(itemUrl);
        }
    }
    
    private final void parseItemPage(final String stringUrl) throws Exception {
        final Data data = new Data();
        
        final String temp = stringUrl.replace("/item/", "");
        final int id = Integer.parseInt(temp.substring(0, temp.indexOf("/")));
        data.id = id;
        
        final Document doc = Jsoup.connect(ITEM_URL+stringUrl).get();
        final ListIterator<Element> restrictions = doc.select("div.tttt-restrictions").listIterator();
        if (restrictions.hasNext()) {
            for (final String restriction : restrictions.next().html().split(", ")) {
                switch (restriction) {
                    case "Cannot trade":
                        data.trade = false;
                    break;
                    
                    case "No sale value":
                        data.saleValue = -1;
                    break;
                    
                    case "Cannot be stored in bank":
                        data.storeInBank = false;
                    break;
                    
                    case "Cannot be stored in the guild bank":
                        data.storeInGuildBank = false;
                    break;
                }
            }
        }
        
        final ListIterator<Element> infos = doc.select("div.infobox > ul > li > div").listIterator();
        while (infos.hasNext()) {
            final String[] infoData = infos.next().html().split(": ");
            switch (infoData[0]) {
                case "Level":
                    data.level = Integer.parseInt(infoData[1]);
                break;
                
                case "Required level":
                    data.requiredLevel = Integer.parseInt(infoData[1]);
                break;
                
                case "Category":
                    data.category = infoData[1];
                break;
                
                case "Binds on equip":
                    data.bindOnEquip = true;
                break;
                
                case "Cannot be obtained":
                    data.beObtained = false;
                break;
                
                case "Cannot be destroyed":
                    
                break;
            }
        }
        
        this.datas.put(id, data);
    }
    
    private final void buildXml(final File folder) throws Exception {
        final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        final org.w3c.dom.Document doc = docBuilder.newDocument();
        final org.w3c.dom.Element rootElement = doc.createElement("items");
        rootElement.setAttribute("xmlns", "http://angelis.com/items");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:schemaLocation", "http://angelis.com/item_templates ../schema/items.xsd ");
        doc.appendChild(rootElement);

        for (final Data data : this.datas.values()) {
            final org.w3c.dom.Element itemElement = doc.createElement("item");
            itemElement.setAttribute("id", String.valueOf(data.id));
            itemElement.setAttribute("trade", String.valueOf(data.trade));
            itemElement.setAttribute("sale_value", String.valueOf(data.saleValue));
            itemElement.setAttribute("store_in_bank", String.valueOf(data.storeInBank));
            itemElement.setAttribute("store_in_guild_bank", String.valueOf(data.storeInGuildBank));
            itemElement.setAttribute("level", String.valueOf(data.level));
            itemElement.setAttribute("required_level", String.valueOf(data.requiredLevel));
            itemElement.setAttribute("category", String.valueOf(data.category));
            itemElement.setAttribute("bind_on_equip", String.valueOf(data.bindOnEquip));
            itemElement.setAttribute("can_be_obtain", String.valueOf(data.beObtained));
            itemElement.setAttribute("can_be_destroyed", String.valueOf(data.beDestroyed));
            
            rootElement.appendChild(itemElement);
        }
        
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        final DOMSource source = new DOMSource(doc);
        final StreamResult result = new StreamResult(new File(folder.getAbsolutePath()+"/item_templates.xml"));

        transformer.transform(source, result);
    }
}
