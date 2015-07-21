package States;

import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * Created by Matt on 5/25/2015.
 * An empty state
 */
public class BlankState implements IState
{
    PApplet p;
    float x,y;
    ControlP5 ui;
    PFont pfont;
    ControlFont font;
    /**
     * Constructs the state
     * @param _p a referece to the parent PApplet.
     */
    public BlankState(PApplet _p, ControlP5 _ui, PFont _pfont)
    {
        p = _p;
        ui = _ui;
        pfont = _pfont;
    }

    @Override
    /**
     * Calculates physics, animations, and any other actions that happen.
     */
    public void Update(double elapsedTime)
    {
        //update the x location of the square
        x+=100*elapsedTime;
    }

    @Override
    /**
     * Displays everything to the screen.
     */
    public void Render()
    {
        //draw the text and square to the screen
        p.background(35, 49, 63);
        p.textSize(96);
        p.textAlign(p.CENTER);
        p.text("Blank State", p.width / 2, p.height * .3f);
        p.rect(x, y, 50, 50);
    }

    @Override
    /**
     * Sets up any variables needed for the duration of the state.
     */
    public void OnEnter()
    {
        font = new ControlFont(pfont,48);
        x = 0;
        y = p.height*.2f;
        // create a new button with name 'button1'
        ui.addButton("button1")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width/2-100,p.height/2)
                .setSize(200, 200)
                .setColorBackground(p.color(180))
                .setColorForeground(p.color(255))
                .setColorLabel(p.color(0))
                .setCaptionLabel("what?")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(48)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        p.color(50);

    }

    @Override
    /**
     * Cleans up and executes any final tasks before leaving the state.
     */
    public void OnExit()
    {

    }

    public void receiveEvents(ControlEvent event)
    {
        if (event.getController().getName().equals("button1"))
        {
            System.out.println("You pressed button1");
        }
    }

}
