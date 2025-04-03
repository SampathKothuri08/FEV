
package DataInload;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;


/**
 * The XMlInload class is responsiable for reading in XML data files 
 * As well as converting them into a java object respresentation
 * @author doug
 */
public class XMLInload 
{

    
    public static void main(String[] args)
    {
        try{
            //the file to use
            File fXmlFile = new File("/home/doug/Desktop/Thesis/Code/XML/SampleXML/sample1");
            //defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //lets the application aquire a DOM object
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //represents the entire xml Object
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("head");
 
            System.out.println("----------------------------");
 
            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
 
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
 
                    System.out.println("title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
			System.out.println("type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
			System.out.println("tools : " + eElement.getElementsByTagName("tools").item(0).getTextContent());
			System.out.println("NumQuestions : " + eElement.getElementsByTagName("NumQuestions").item(0).getTextContent());
			
 
		}
	}

            
            
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    
}
