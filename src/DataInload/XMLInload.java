
package DataInload;

import DataObject.XMLObject;
import DataObject.Datapoint;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;



/**
 * The XMlInload class is tasked for reading in XML data files 
 * As well as converting them into a java objects 
 * @author doug
 */
public class XMLInload 
{
    //XML Object
    private XMLObject evalData;
    
   
   /**
     * Class datainLoad reads in the XML doc from file and creates the Evaluation Object
     * This class is designed to be interacted with through a managing class 
     * Future revisions may include passing the XML file to the method
     * 
     */ 
    public void dataInload()
    {
        
        String title = " ", type = " ", tools = " ", NumQuestions = " ", label1 = "", label2 = "";     
        
        
        String label = "", group = "", valueX = "";
        //int valueX = 0, valueY = 0, edge = 0;
        int  edge = 0;
        Double valueY = 0.0;
        Double percent = 0.0;
        
        try{
            //the file to use
            //e paths of the images you wish to use
            //private String path = "C:\\Users\\Lab member\\Desktop\\Data\\Pictures\\l1.jpg";
            //for windows box C:\\Users\\Lab member\\Desktop\\Thesis\\sample1



            File fXmlFile = new File("Data/many2many.xml");


            //defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //lets the application aquire a DOM object
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //represents the entire xml Object
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //gets information from the head portion of the document
            NodeList nList = doc.getElementsByTagName("head");
 
            //System.out.println("----------------------------");
 
            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
 
		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
                    Element eElement = (Element) nNode;

                    title =  eElement.getElementsByTagName("title").item(0).getTextContent();
                    type =  eElement.getElementsByTagName("type").item(0).getTextContent();
                    tools = eElement.getElementsByTagName("tools").item(0).getTextContent();
                    NumQuestions = eElement.getElementsByTagName("NumQuestions").item(0).getTextContent();
                                                
            //TESTING    
            //System.out.println("title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
            //System.out.println("type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
	    //System.out.println("tools : " + eElement.getElementsByTagName("tools").item(0).getTextContent());
            //System.out.println("NumQuestions : " + eElement.getElementsByTagName("NumQuestions").item(0).getTextContent());
			
 
		}//end of if
	}//end of loop
               
        this.createEvalObject(title, type, tools, NumQuestions);    
            
        //now loop to extract data elements from the body    
        nList = doc.getElementsByTagName("datapoint");    
        
        
        
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node nNode = nList.item(i);
            
            if(nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                
                label = eElement.getElementsByTagName("label").item(0).getTextContent();
                group = eElement.getElementsByTagName("group").item(0).getTextContent();
                valueX = eElement.getElementsByTagName("valueX").item(0).getTextContent();
                
                /**
                 * If the field is empty no need to do a type conversion
                 */
                //if(!(eElement.getElementsByTagName("valueX").item(0).getTextContent().equals("")))
                //{
                   // valueX = Integer.parseInt(eElement.getElementsByTagName("valueX").item(0).getTextContent());
                    
                //}
                
                if(!(eElement.getElementsByTagName("valueY").item(0).getTextContent().equals("")))
                {
                    valueY = Double.parseDouble(eElement.getElementsByTagName("valueY").item(0).getTextContent());
                }
                
                if(!(eElement.getElementsByTagName("percent").item(0).getTextContent().equals("")))
                {
                    percent = Double.parseDouble(eElement.getElementsByTagName("percent").item(0).getTextContent());
                }

                if(!(eElement.getElementsByTagName("edges").item(0).getTextContent().equals("")))
                {
                    edge = Integer.parseInt(eElement.getElementsByTagName("edges").item(0).getTextContent());
                }
                
                //TESTING
                //System.out.println("label: "  + label);
                //System.out.println("group: " + group);
                //System.out.println("valueX: " + valueX);
                //System.out.println("valueY: " + valueY);
                //System.out.println("percent: " + valueY);
                //System.out.println("edge: " + edge);
                
                this.createDatapoint(new Datapoint(label, group, valueX, valueY, percent, edge));
                
            }//end of if
            
        }//end for
             
       //now loop to many to many relationship info   
       nList = doc.getElementsByTagName("many2many");
       
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node nNode = nList.item(i);
            
            if(nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                label1 = eElement.getElementsByTagName("label1").item(0).getTextContent();
                label2 = eElement.getElementsByTagName("label2").item(0).getTextContent();
            }
        }
        
        
        
        
        }//end try
        catch(Exception e)
        {
            e.printStackTrace();
            //a File not found exception will just crash the system, better to exit
            System.exit(0);
        }//end catch
   
        
        
    }//end method
    
    /**
     * Used by the DOM parser to create an eval object
     * 
     * @param title the evaluation title
     * @param type the type of evaluation
     * @param tools what viz tools where used
     * @param NumQuestions the number of questions desired 
     */
    private void createEvalObject(String title, String type, String tools, String NumQuestions)
    {
        this.evalData =  new XMLObject(title, type, tools, NumQuestions);  
    }
    
    private void createMany2Many(String label1, String label2)
    {
        this.evalData.addMany2Many(label1, label2);
    }
    
    /**
     * Adds a datapoint to the eval object 
     * datapoint should be created by the calling method
     * @param d  the datapoint
     */
    private void createDatapoint(Datapoint d)
    {
        this.evalData.addDatapoints(d);
    }
    
    /**
     * returns a reference to the evalObject 
     * @return 
     */
    public XMLObject getEval()
    {
        return this.evalData;
    }
       
    
}
