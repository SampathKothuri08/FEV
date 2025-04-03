/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation.tool;

import DataObject.EvalObject;
import DataObject.Question;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *Class EvalBenchOut Creates an XML file in the format specified by EvalBench
 * This enables task-lists created to be used in EvalBench
 *
 * @author Doug Taggart
 */
public class EvalBenchOut
{

    //root element soo all tasks can append to it
    private Element rootElement;
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;

    public void WriteXML(EvalObject eval)
    {


        try{
                //intilize the documnet builders
                 docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();

                // root elements
		doc = docBuilder.newDocument();

                rootElement = doc.createElement("taskList");
		doc.appendChild(rootElement);


                for(int i = 0; i < eval.getQuestions().size(); i++)
                {
                    this.writeTasks(eval.getQuestions().get(i));
                }


                // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir")));

                transformer.transform(source, result);



        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();;
        }

        catch (TransformerException tfe)
        {
            tfe.printStackTrace();
	}


    }



    private void writeTasks(Question q)
    {


                // task element(s)
		Element task = doc.createElement("task");
		rootElement.appendChild(task);

                // set attribute to task element
		Attr attr = doc.createAttribute("id");
		attr.setValue("TaskId1");
		task.setAttributeNode(attr);
                //ALL CHILDREN OF TASK


                //task-description element
                Element description = doc.createElement("task-description");
                description.appendChild(doc.createTextNode("Task containing a text question."));
                task.appendChild(description);

                //task-instruction element
                Element instruction = doc.createElement("task-instruction");
                instruction.appendChild(doc.createTextNode("Enter a text."));
                task.appendChild(instruction);

                //task-type element
                //this field will need to get filled in with data from the user
                Element type = doc.createElement("task-type");
                type.appendChild(doc.createTextNode("TaskTypeDemoExampleText"));
                task.appendChild(type);

                //configuration element
                Element configuration = doc.createElement("configuration");
                task.appendChild(configuration);

                //configuration element
                Element questions = doc.createElement("questions");
                task.appendChild(questions);

                //ALL CHILDREN OF QUESTIONS
                //ALL GRANDCHILDREN OF TASK

                //text input element
                Element input = doc.createElement("text-input");
                questions.appendChild(input);

                //set attribute to question element
		Attr Qattr = doc.createAttribute("id");
		Qattr.setValue("q1");
		input.setAttributeNode(Qattr);

                //ALL CHILDREN OF TEXT INPUT
                //ALL GRANDCHILDREN OF QUESTIONS
                //ALL GREAT-GRANDCHILDREN OF TASK

                //question text element
                //this data comes from the user
                Element text = doc.createElement("question-text");
                text.appendChild(doc.createTextNode("Enter:"));
                input.appendChild(text);

               //correct answer element
               //data comes from system
               Element correct = doc.createElement("correctAnswer");
               correct.appendChild(doc.createTextNode("correct"));
               input.appendChild(correct);

               //regex element
               Element regex = doc.createElement("regEx");
               regex.appendChild(doc.createTextNode("[a-z]*"));
               input.appendChild(regex);

               //singleLine element
               Element singleLine = doc.createElement("singleLine");
               singleLine.appendChild(doc.createTextNode("false"));
               input.appendChild(singleLine);




    }//end of subclass





}
