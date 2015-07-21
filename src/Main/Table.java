package Main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Matt on 7/17/2015.
 */
public class Table
{
    PApplet p;
    PFont pfont;
    private LinkedList<PVector> dataPoints;
    private PVector position;
    private int width, height;
    private ArrayList<String> columns;


    public Table(PApplet _p, PFont _pfont)
    {
        p = _p;
        pfont = _pfont;
        dataPoints = new LinkedList<PVector>();
        position = new PVector();
        columns = new ArrayList<String>(4);
        width = 0;
        height = 300;
    }

    public void display()
    {
        p.textSize(24);
        p.textAlign(PConstants.RIGHT);
        int lengthTmp = 0;
        int numCol = columns.size();
        int ind = 0;
        for (String s : columns)
        {
            lengthTmp+=p.textWidth(s);
            p.text(s, position.x + lengthTmp*1.5f, position.y+25);
            if (ind < numCol-1)
                p.line(position.x + lengthTmp*1.5f + 20, position.y, position.x + lengthTmp*1.5f + 20, height);
            ind++;
        }

        width = (int) ((lengthTmp + p.textWidth(columns.get(columns.size()-1))) * 1.5);
        p.line(position.x, position.y, position.x, height);
        p.line(position.x, position.y, width, position.y);
        p.line(width, position.y, width, height);
        p.line(position.x, height, width,height);
        int size = dataPoints.size();
        if (size > 5) size = 5;
        for (int i = 0; i < size; i++)
        {
            p.line(position.x, position.y + i*40+40, width, position.y + i*40+40);
            p.text((int)dataPoints.get(i).x, position.x + 60, position.y + i*40+70);
        }
    }

    public void addColumn(String title)
    {
        columns.add(title);
    }

    public void setData(LinkedList<PVector> data)
    {
        dataPoints = data;
    }

    public void setPosition(PVector p)
    {
        position = p;
    }

    public PVector getPosition()
    {
        return position;
    }
    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
