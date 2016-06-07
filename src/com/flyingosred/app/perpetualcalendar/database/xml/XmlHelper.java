
package com.flyingosred.app.perpetualcalendar.database.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlHelper {

    private static int INDENT_SIZE = 4;

    public static void createXmlFile(String path, String rootName, String element, String elementName, String item,
            List<String> itemNameList) {
        Document document = DocumentHelper.createDocument();
        document.addComment("Copyright (C) 2016 Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.");
        document.addComment("This file is auto-generated");
        document.addComment("DO NOT MODIFY");
        Element root = document.addElement(rootName);
        Element rootElement = root.addElement(element).addAttribute("name", elementName);
        for (String itemName : itemNameList) {
            rootElement.addElement(item).addText(itemName);
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndentSize(INDENT_SIZE);
        XMLWriter writer;
        try {
            writer = new XMLWriter(new FileOutputStream(path), format);
            writer.write(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createXmlFile(String path, String rootName, String element, Map<String, String> elementValues) {
        Document document = DocumentHelper.createDocument();
        document.addComment("Copyright (C) 2016 Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.");
        document.addComment("This file is auto-generated");
        document.addComment("DO NOT MODIFY");
        Element root = document.addElement(rootName);
        for (Map.Entry<String, String> entry : elementValues.entrySet()) {
            root.addElement(element).addAttribute("name", entry.getKey()).addText(entry.getValue());
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndentSize(INDENT_SIZE);
        XMLWriter writer;
        try {
            writer = new XMLWriter(new FileOutputStream(path), format);
            writer.write(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
