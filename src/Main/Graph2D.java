package Main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import java.util.LinkedList;

/**
 * Created by Matt on 7/16/2015.
 */
public class Graph2D
{
    PApplet p;
    PFont pfont;
    private LinkedList<PVector> dataPoints;
    private PVector position;
    private int width, height;
    public float scaleX, scaleY;
    boolean guidelinesOn;
    int xAxisLabelCount;
    int yAxisLabelCount;

    public Graph2D(PApplet _p, PFont _pfont)
    {
        p = _p;
        pfont = _pfont;
        dataPoints = new LinkedList<PVector>();
        position = new PVector();
        scaleX = 35;
        scaleY = 3;
        xAxisLabelCount = 10;
        yAxisLabelCount = 5;
        guidelinesOn = true;
    }

    public void update()
    {
        for (PVector v : dataPoints)
        {
            p.ellipse(v.x * scaleX, v.y * scaleY, 5,5);
        }
    }

    public void display()
    {
        if (dataPoints.isEmpty())
            return;
        //flip screen axis for drawing graph
        p.pushMatrix();
        p.scale(1, -1);
        p.translate(0 + position.x, -p.height + position.y);

        //draw guidelines
        if (guidelinesOn)
        {
            p.stroke(255, 128);
            for (int i = 0; i < width / (width / xAxisLabelCount); i++)
            {
                p.line(i * (width / xAxisLabelCount), 0, i * (width / xAxisLabelCount), height);
            }
            for (int i = 0; i < height / (height / yAxisLabelCount); i++)
            {
                p.line(0, i * (height / yAxisLabelCount), width, i * (height / yAxisLabelCount));
            }
        }
        //draw lines
        PVector prev = dataPoints.getFirst();
        for (PVector v : dataPoints)
        {
            p.stroke(255,255);
            p.line(prev.x * scaleX, prev.y * scaleY, v.x * scaleX, v.y * scaleY);
            prev = v;
        }

        //draw data points
        for (PVector v : dataPoints)
        {
            if (Main.CollisionFunctions.pointBall(p.mouseX-position.x, p.height - p.mouseY-position.y, v.x * scaleX, v.y * scaleY, 20))
            {
                p.stroke(255, 0, 0);
                p.fill(255,0,0);
                if (p.mousePressed)
                {
                    v.x = (p.mouseX-position.x)/scaleX;
                    v.y = (p.height - p.mouseY-position.y)/scaleY;
                }
            }
            else
            {
                p.stroke(255, 255);
                p.fill(255,255);
            }

            p.ellipse(v.x * scaleX, v.y * scaleY, 8,8);
        }

        //draw graph window bounds
        p.strokeWeight(3);
        p.stroke(255);
        p.line(0,0,width,0); //bottom line
        p.line(0,0,0,height); //left side
        p.line(0,height,width,height); //top side
        p.line(width,0, width,height); //right side

        //draw graph labels
        p.textAlign(PConstants.RIGHT);
        p.textSize(24);
        p.pushMatrix();
        p.scale(1,-1);
        p.stroke(255,255);
        p.fill(255,255);
        for (int i = 0; i < height / (height / yAxisLabelCount); i++)
        {
            p.translate(0, 0); // move to where you want the text to be

            p.text((int)(i*(height/yAxisLabelCount)/scaleY), -10, -i*(height/yAxisLabelCount));
        }
        for (int i = 0; i < width / (width / xAxisLabelCount); i++)
        {
            p.translate(0, 0); // move to where you want the text to be

            p.text((int)(i*(width/xAxisLabelCount)/scaleX), i*(width/xAxisLabelCount) + 5, 30 );
        }
        p.popMatrix();
        p.popMatrix();

    }

    public void addPoint(PVector v)
    {
        int index = 0;
        for (PVector p : dataPoints)
        {
            if (p.x < v.x)
                index++;
        }
        dataPoints.add(index, v);
        float largestX = 0, largestY = 0;
        for (PVector p : dataPoints)
        {
            if (p.x*scaleX > largestX)
                largestX = p.x*scaleX;
            if (p.y*scaleY > largestY)
                largestY = p.y*scaleY;
        }
        width = (int) (largestX * 1.1);
        height = (int) (largestY * 1.1);
    }

    public void clearData()
    {
        dataPoints.clear();
    }
    /**
     *
     * @param x test
     * @return corresponding y value
     */
    public float getY(float x)
    {
        //find nearest surrounding points Todo: deal with corner cases
        PVector v1 = new PVector();
        PVector v2 = new PVector();
        for (PVector v : dataPoints)
        {
            if (v.x <= x)
            {
                v1 = v;
                if (dataPoints.indexOf(v1)+1 <= dataPoints.size()-1)
                    v2 = dataPoints.get(dataPoints.indexOf(v1)+1);
                else
                    v2 = v1;
            }
        }

        return v1.y + ((v2.y - v1.y)/(v2.x - v1.x))*(x - v1.x);
    }
    public void setPosition(PVector v)
    {
        position = v;
    }

    public PVector getPosition()
    {
        return position;
    }

    public int getWidth()
    {
        return width;
    }

    public LinkedList<PVector> getDataPoints()
    {
        return dataPoints;
    }

    public void setScaleX(float sx)
    {
        scaleX = sx;
        updateStuff();
    }
    public void setScaleY(float sy)
    {
        scaleY = sy;
        updateStuff();
    }
    private void updateStuff()
    {
        float largestX = 0, largestY = 0;
        for (PVector p : dataPoints)
        {
            if (p.x*scaleX > largestX)
                largestX = p.x*scaleX;
            if (p.y*scaleY > largestY)
                largestY = p.y*scaleY;
        }
        width = (int) (largestX * 1.1);
        height = (int) (largestY * 1.1);
    }
}
