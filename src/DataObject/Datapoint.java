/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObject;

/**
 *This class contains infromation about datapoints
 * Converted from XML
 *
 * @author Duug Taggart
 */
public class Datapoint
{
    private String label;
    private String group;
    private String valueX;
    private double valueY;
    private double percent;
    private int edges;


    /**
     * Constructor for a datapoint object
     * Not all Data points will be used
     * Each VizTool is different
     * It is the responsibility of the class calling these methods to check
     * For Null Values and Empty Strings
     *
     * @param label
     * @param group
     * @param valueX
     * @param valueY
     * @param percent
     * @param edges
     */

    public Datapoint(String label, String group, String valueX, double valueY, double percent, int edges)
    {
        this.label = label;
        this.group = group;
        this.valueX = valueX;
        this.valueY = valueY;
        this.percent = percent;
        this.edges = edges;
    }

    /**
     * returns the label for the data point
     * This is a String describing what the datapoint is
     * e.x. A reading from a given day
     * This data will vary with each evaluation tool
     * @return label
     */
   public String getLabel()
   {
       return label;
   }
    /**
    * This method returns what group within the dataset this datapoint belongs to
    * for example in a GMap would list what "country" the datapoint belongs to
    *
    * @return group
    */
    public String getGroup()
    {
        return group;
    }

    /**
     * returns the X coordinate value
     * used for ViZTools with a coordinate system
     * @return valueX
     */
    public String getValueX()
    {
        return valueX;
    }
    /**
     * returns the y coordinate value
     * used for vizTools with a coordinate system
     * @return valueY
     */
    public Double getValueY()
    {
        return valueY;
    }

    /**
     * returns the what percent of the data this point accounts for
     * Used for Pie Charts mostly
     * @return percent
     */
    public Double getPercent()
    {
        return percent;
    }

    /**
     * returns the degree of the datapoint
     * or how many edges it shares with other datapoints
     * @return edges
     */
    public int getEdges()
    {
        return edges;
    }


}
