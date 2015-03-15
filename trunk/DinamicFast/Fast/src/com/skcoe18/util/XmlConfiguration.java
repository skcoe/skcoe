package com.skcoe18.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlConfiguration
{
  private Element rootNode = null;

  public static XmlConfiguration load(String filename)
    throws ParserConfigurationException, IOException, SAXException
  {
    FileInputStream fin = null;
    try {
      fin = new FileInputStream(filename);

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();

      InputSource insource = new InputSource(new InputStreamReader(fin, "UTF-8"));
      Document document = builder.parse(insource);
      XmlConfiguration instance = new XmlConfiguration();
      instance.rootNode = document.getDocumentElement();
      return instance;
    } finally {
      if (fin != null) try {
          fin.close();
        }
        catch (Exception e)
        {
        } 
    }
  }

  public String getValue(Element parent_elem, String sectionname, String key) { return getValue(parent_elem, 0, sectionname, key); }


  public String getValue(String sectionname, String key)
  {
    return getValue(null, 0, sectionname, key);
  }

  public String getValue(int index, String sectionname, String key)
  {
    return getValue(null, index, sectionname, key);
  }

  public String getValue(Element parent_elem, int index, String sectionname, String key)
  {
    Element sectionNode = getSectionNode(parent_elem, index, sectionname);
    return sectionNode.getAttribute(key);
  }

  public Properties getValues(Element parent_elem, String sectionname)
  {
    return getValues(parent_elem, 0, sectionname);
  }

  public Properties getValues(String sectionname)
  {
    return getValues(null, 0, sectionname);
  }

  public Properties getValues(int index, String sectionname)
  {
    return getValues(null, index, sectionname);
  }

  public Properties getValues(Element parent_elem, int index, String sectionname)
  {
    Properties retProp = new Properties();
    Element sectionNode = getSectionNode(parent_elem, index, sectionname);

    if (sectionNode == null) return null;
    NamedNodeMap nodemap = sectionNode.getAttributes();
    for (int i = 0; i < nodemap.getLength(); i++) {
      Attr attrnode = (Attr)nodemap.item(i);
      retProp.setProperty(attrnode.getName(), attrnode.getValue());
    }
    return retProp;
  }

  public Element getSectionNode(Element parent_elem, int index, String sectionname)
  {
    if (parent_elem == null) {
      parent_elem = this.rootNode;
    }
    if ((sectionname == null) || (sectionname.length() == 0)) {
      return parent_elem;
    }
    NodeList nodeList = null;
    String tagname = null;
    int pos = sectionname.indexOf("/");

    while (pos != -1) {
      tagname = sectionname.substring(0, pos);
      sectionname = sectionname.substring(pos + 1);
      nodeList = parent_elem.getElementsByTagName(tagname);
      if (nodeList.getLength() == 0) return null;
      pos = sectionname.indexOf("/");
      parent_elem = (Element)nodeList.item(0);
    }
    nodeList = parent_elem.getElementsByTagName(sectionname);
    if (nodeList.getLength() <= index) return null;
    return (Element)nodeList.item(index);
  }

  public int getKeyCount(String sectionname)
  {
    return getKeyCount(null, sectionname);
  }

  public int getKeyCount(Element parent_elem, String sectionname)
  {
    if (parent_elem == null) {
      parent_elem = this.rootNode;
    }
    if ((sectionname == null) || (sectionname.length() == 0)) {
      return 0;
    }

    NodeList nodeList = null;
    String tagname = null;
    int pos = sectionname.indexOf("/");

    while (pos != -1) {
      tagname = sectionname.substring(0, pos);
      sectionname = sectionname.substring(pos + 1);
      nodeList = parent_elem.getElementsByTagName(tagname);
      if (nodeList.getLength() == 0) return 0;
      pos = sectionname.indexOf("/");
      parent_elem = (Element)nodeList.item(0);
    }
    nodeList = parent_elem.getElementsByTagName(sectionname);
    return nodeList.getLength();
  }

  public static void main(String[] args) {
    try {
      XmlConfiguration conf = load(args[0]);
      String portnum = conf.getValue("Server", "port");
      System.out.println("[Server], port = " + portnum);
      String siebelserver = conf.getValue(0, "ListOfSiebelServers/SiebelServer", "name");
      System.out.println("[SiebelServer], name = " + siebelserver);
      System.out.println("The number of connections = " + conf.getKeyCount("ListOfConnections/Connection"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
