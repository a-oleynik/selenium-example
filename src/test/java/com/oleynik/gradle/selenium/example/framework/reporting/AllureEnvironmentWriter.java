package com.oleynik.gradle.selenium.example.framework.reporting;

import com.google.common.collect.ImmutableMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import static com.oleynik.gradle.selenium.example.framework.utils.GeneralUtils.createDirectoryIfNotExist;

public class AllureEnvironmentWriter {
    private static final String ALLURE_RESULTS_DIRECTORY = "allure.results.directory";
    private static final String ALLURE_ENVIRONMENT_XML = "environment.xml";

    public static void writeAllureEnvironment(ImmutableMap<String, String> environmentValuesSet) {
        writeAllureEnvironment(environmentValuesSet, System.getProperty(ALLURE_RESULTS_DIRECTORY));
    }

    public static void writeAllureEnvironment(ImmutableMap<String, String> environmentValues, String customResultsPath) {
        try {
            Document doc = createXmlDocument(environmentValues);
            saveXMLDocument(doc, customResultsPath, ALLURE_ENVIRONMENT_XML);
        } catch (ParserConfigurationException | TransformerException e) {
            System.err.println("Cannot write Allure environment properties file");
            e.printStackTrace();
        }

    }

    public static Document createXmlDocument(ImmutableMap<String, String> environmentValues)
            throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        Element environment = document.createElement("environment");
        document.appendChild(environment);
        environmentValues.forEach((k, v) -> {
            Element parameter = document.createElement("parameter");
            Element key = document.createElement("key");
            Element value = document.createElement("value");
            key.appendChild(document.createTextNode(k));
            value.appendChild(document.createTextNode(v));
            parameter.appendChild(key);
            parameter.appendChild(value);
            environment.appendChild(parameter);
        });
        return document;
    }

    public static void saveXMLDocument(Document doc, String customResultsPath, String fileName)
            throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        createDirectoryIfNotExist(customResultsPath);
        StreamResult result = new StreamResult(
                new File(customResultsPath + File.separator + fileName));
        transformer.transform(source, result);
    }
}
