package com.flyingosred.app.perpetualcalendar.database.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlFile {

    private final String mPath;

    private Document mDocument = null;

    private Element mRootElement = null;

    public XmlFile(String path, String root) {
        mPath = path;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            mDocument = dBuilder.newDocument();
            mRootElement = mDocument.createElement(root);
            mDocument.appendChild(mRootElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            mDocument.setXmlStandalone(true);
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(mDocument);
            StreamResult result = new StreamResult(mPath);
            transformer.transform(source, result);
            System.out.println("String xml file output to " + mPath);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
