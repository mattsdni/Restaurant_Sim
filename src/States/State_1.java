package States;

import Main.BellCurve;
import Main.Clock;
import Main.Person;
import controlP5.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import java.io.*;
import java.sql.Time;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matt on 5/25/2015.
 * An empty state
 */
public class State_1 implements IState
{
    PApplet p;
    ControlP5 ui;
    PFont pfont;
    ControlFont font;
    Main.Graph2D graph;
    Main.Table table;
    Slider selectTime;
    DropdownList openHours, closedHours;
    Textfield cookTimeField, partySizeField, numBoothsField, diningDurationField, partySizeVarianceField, numBoothsVarianceField, diningDurationVarianceField;
    boolean pause;
    /**
     * Constructs the state
     * @param _p a referece to the parent PApplet.
     */
    public State_1(PApplet _p, ControlP5 _ui, PFont _pfont)
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

    }

    @Override
    /**
     * Displays everything to the screen.
     */
    public void Render()
    {
        if (pause)
        {
            p.delay(3000);
            pause = false;
        }
        //draw the text and square to the screen
        //p.background(35, 49, 63);
        p.background(0);
        p.textSize(96);
        p.textAlign(PConstants.CENTER);

        p.fill(255);
        PVector hourValueVector = ui.getController("hourValue").getPosition();

        p.text("Time: " + (int) ui.getController("hourValue").getValue(), hourValueVector.x + ui.getController("hourValue").getWidth() / 2, hourValueVector.y + 150);
        graph.display();
        table.display();
        p.textAlign(PConstants.LEFT);
        p.text("Cooking Time: ", p.width * .05f, p.height * .3f);
        p.text("Party Size: ", p.width * .05f, p.height * .35f);
        p.text("Variance: ", p.width * .27f, p.height * .35f);
        p.text("# Booths: ", p.width * .05f, p.height * .4f);
        p.text("Variance: ", p.width * .27f, p.height * .4f);
        p.text("Dining Duration: ", p.width * .05f, p.height * .45f);
        p.text("Variance: ", p.width * .27f, p.height * .45f);
        p.text("Hours: ", p.width * .05f, p.height * .5f);
        p.text("to", p.width * .27f, p.height * .5f);
    }

    @Override
    /**
     * Sets up any variables needed for the duration of the state.
     */
    public void OnEnter()
    {
        font = new ControlFont(pfont,36);

        graph = new Main.Graph2D(p, pfont);

        graph.addPoint(new PVector(10,20));
        graph.addPoint(new PVector(12,80));
        graph.addPoint(new PVector(14,20));
        graph.addPoint(new PVector(18,20));
        graph.addPoint(new PVector(20,20));
        graph.addPoint(new PVector(22,20));

        graph.setPosition(new PVector(p.width / 2, p.height / 2));


        table = new Main.Table(p, pfont);
        table.setPosition(new PVector(p.width*.05f, p.height*.05f));
        table.addColumn("Time");
        table.addColumn("Ppl in Store");
        table.addColumn("Ppl Enter");
        table.addColumn("Ppl Exit");

        ui.addButton("addPoint")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(graph.getPosition().x + graph.getWidth() - 220, graph.getPosition().y + 45)
                .setSize(220, 40)
                .setColorLabel(p.color(255))
                .setCaptionLabel("Add Point")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        ui.addTextfield("input")
                .setPosition(graph.getPosition().x, graph.getPosition().y + 45)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(true)
                .getCaptionLabel()
                .setVisible(false)
        ;
        selectTime = ui.addSlider("hourValue");
        selectTime.setPosition(p.width / 2 - 350, p.height * .8f)
                .setWidth(700)
                .setHeight(50)
                .setRange(10, 22)
                .setValue(10)
                .setNumberOfTickMarks(13)
                .setSliderMode(Slider.FLEXIBLE)
                .getCaptionLabel()
                .setVisible(false)
        ;
        ui.addButton("exportToExcel")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width * .8f, p.height * .9f)
                .setSize(300, 50)
                .setCaptionLabel("Export To Excel")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        ui.addButton("previewData")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(table.getPosition().x + 650, table.getPosition().y)
                .setSize(250, 50)
                .setCaptionLabel("Update Data")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        cookTimeField = ui.addTextfield("cookTimeField");
        cookTimeField.setPosition(p.width * .15f, p.height * .28f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        partySizeField = ui.addTextfield("partySizeField");
        partySizeField.setPosition(p.width * .15f, p.height * .33f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        partySizeVarianceField = ui.addTextfield("partySizeVarianceField");
        partySizeVarianceField.setPosition(p.width * .33f, p.height * .33f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        numBoothsField = ui.addTextfield("numBoothsField");
        numBoothsField.setPosition(p.width * .15f, p.height * .38f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        numBoothsVarianceField = ui.addTextfield("numBoothsVarianceField");
        numBoothsVarianceField.setPosition(p.width * .33f, p.height * .38f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        diningDurationField = ui.addTextfield("diningDurationField");
        diningDurationField.setPosition(p.width * .15f, p.height * .43f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;
        diningDurationVarianceField = ui.addTextfield("diningDurationVarianceField");
        diningDurationVarianceField.setPosition(p.width * .33f, p.height * .43f)
                .setSize(200, 40)
                .setFont(font)
                .setFocus(false)
                .getCaptionLabel()
                .setVisible(false)
        ;



        openHours = ui.addDropdownList("openHour")
                .setPosition(p.width * .15f, p.height * .52f)
                .setSize(200, 400)
        ;
        openHours.setItemHeight(40);
        openHours.setBarHeight(40);
        openHours.captionLabel().set("Open Hours");
        openHours.captionLabel().style().marginTop = 3;
        openHours.captionLabel().style().marginLeft = 3;
        openHours.valueLabel().style().marginTop = 3;
        openHours.addItems(new String[]{"6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00"});
        openHours.getCaptionLabel().setFont(font).setSize(18);
        openHours.getValueLabel().setFont(font).setSize(18);


        closedHours = ui.addDropdownList("closeHour")
                .setPosition(p.width * .33f, p.height * .52f)
                .setSize(200,400)
        ;
        closedHours.setItemHeight(40);
        closedHours.setBarHeight(40);
        closedHours.captionLabel().set("Closed Hours");
        closedHours.captionLabel().style().marginTop = 3;
        closedHours.captionLabel().style().marginLeft = 3;
        closedHours.valueLabel().style().marginTop = 3;
        closedHours.addItems(new String[]{"21:00", "22:00", "23:00", "24:00", "25:00", "26:00"});
        closedHours.getCaptionLabel().setFont(font).setSize(18);
        closedHours.getValueLabel().setFont(font).setSize(18);

        ui.addButton("simulate")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width * .8f, p.height * .6f)
                .setSize(300, 300)
                .setCaptionLabel("Simulate")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        ui.addButton("saveSettings")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width * .05f, 0)
                .setSize(100, 50)
                .setCaptionLabel("Save")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        ui.addButton("loadSettings")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(0, 0)
                .setSize(100, 50)
                .setCaptionLabel("Load")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;



    }

    public void simulate()
    {
        //check all variables before take off
        if (cookTimeField.getValueLabel().getText().isEmpty() ||
                partySizeField.getValueLabel().getText().isEmpty() ||
                numBoothsField.getValueLabel().getText().isEmpty() ||
                diningDurationField.getValueLabel().getText().isEmpty() ||
                partySizeVarianceField.getValueLabel().getText().isEmpty() ||
                diningDurationVarianceField.getValueLabel().getText().isEmpty())
        {
            p.fill(255,0, 0);
            p.rectMode(PConstants.CENTER);
            p.rect(400,p.height*.7f, 600, 300);
            p.fill(255);
            p.textAlign(PConstants.CENTER);
            p.text("ERROR: All Fields must be filled.",400,p.height*.7f);
            pause = true;
            return;
        }

        //load variables into locals
        int cookingTime = Integer.parseInt(cookTimeField.getValueLabel().getText());
        int partySize = Integer.parseInt(partySizeField.getValueLabel().getText());
        int numBooths = Integer.parseInt(numBoothsField.getValueLabel().getText());
        double diningDuration = Double.parseDouble(diningDurationField.getValueLabel().getText());
        double partySizeVariance = Double.parseDouble(partySizeVarianceField.getValueLabel().getText());
        double diningDurationVariance = Double.parseDouble(diningDurationVarianceField.getValueLabel().getText());
        int openHour = (int)selectTime.getMin();
        int closeHour = (int)selectTime.getMax();
        LinkedList<PVector> customerFlow = graph.getDataPoints();
        Clock clock = new Clock(openHour, 0);

        Queue<Person> foodOrderQ = new LinkedList<Person>();
        LinkedList<Person> peopleEating = new LinkedList<Person>();

        int numPeopleAtTen = 0;
        int totalCustomers = 0;
        int longestLine = 0;
        int biggestParty = 0;
        int mostPeopleInRestaurant = 0;
        //this is your captain speaking, we are go for take off
        while (clock.getHours() < closeHour)
        {
            //get a headcount
            if (foodOrderQ.size() + peopleEating.size() > mostPeopleInRestaurant)
                mostPeopleInRestaurant = foodOrderQ.size() + peopleEating.size();

            //check people in line, dequeue if food is done
            if (!foodOrderQ.isEmpty() && clock.compareTo(foodOrderQ.peek().spawnTime.plus(new Clock(0,cookingTime))) >= 0)
            {
                //take the person out of line and seat them
                Person p = foodOrderQ.remove();
                int d = (int) (diningDuration + BellCurve.generateGaussianNoise(diningDurationVariance));
                Clock c = clock.plus(new Clock(d));
                p.setEatingTime(c);
                peopleEating.add(p);
                System.out.println("seated a person\n");
            }

            //check if anyone is done eating
            for (int i = 0; i < peopleEating.size() - 1; i++)
            {
                if (clock.compareTo(peopleEating.get(i).eatingTime) >= 0)
                {
                    peopleEating.remove(i);
                    System.out.println("A person left");
                }
            }

            if ((int)(p.random(0, 1 / (graph.getY(clock.getTimeAsDecimal()) / 60))) == 0)
            {
                //determine party size
                int addThisMany = (int) (partySize + BellCurve.generateGaussianNoise(partySizeVariance));
                addThisMany/=partySize;
                //add to order queue
                for (int i = 0; i < addThisMany; i++)
                {
                    foodOrderQ.add(new Person(clock.getFormattedTime()));
                    totalCustomers++;

                    if (addThisMany > biggestParty)
                        biggestParty = addThisMany;

                    if (foodOrderQ.size() > longestLine)
                        longestLine = foodOrderQ.size();

                    if (clock.getHours() == 10)
                        numPeopleAtTen++;
                }
            }

            clock.tick();
        }
        System.out.println("People at 10: " + numPeopleAtTen);
        System.out.println("Longest Food Queue:" + longestLine);
        System.out.println("Largest Party: " + biggestParty);
        System.out.println("Most People in Restaurant: " + mostPeopleInRestaurant);
        System.out.println("Average people per hour: " + totalCustomers/(closeHour-openHour));
        System.out.println("total customers: " + totalCustomers);
        System.out.println("People in line at close: " + foodOrderQ.size());
        System.out.println("People left in Restaurant: " + peopleEating.size());




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
        //*******************************************************//
        //-------------------Dropdown Menus----------------------//
        //*******************************************************//
        if (event.isGroup())
        {
            if (event.getGroup().getName().equals("openHour"))
            {
                String[] tmp = openHours.getItem((int) event.getGroup().getValue()).getName().split(":");
                selectTime.setMin(Integer.parseInt(tmp[0]));
                selectTime.setNumberOfTickMarks(1+(int) (selectTime.getMax()-selectTime.getMin()));
            }
            if (event.getGroup().getName().equals("closeHour"))
            {
                String[] tmp = closedHours.getItem((int) event.getGroup().getValue()).getName().split(":");
                selectTime.setMax(Integer.parseInt(tmp[0]));
                selectTime.setNumberOfTickMarks(1+(int) (selectTime.getMax()-selectTime.getMin()));
            }
            return;
        }

        //*******************************************************//
        //-----------------------Buttons-------------------------//
        //*******************************************************//
        if (event.getController().getName().equals("addPoint"))
        {
            String tmp = ui.get(Textfield.class, "input").getText();
            tmp = tmp.replace(" ", "");
            String [] tmparr = tmp.split(",");
            float x = Float.parseFloat(tmparr[0]);
            float y = Float.parseFloat(tmparr[1]);
            graph.addPoint(new PVector(x,y));
            ui.get(Textfield.class,"input").clear();
        }
        if (event.getController().getName().equals("previewData"))
        {
            table.setData(graph.getDataPoints());
        }
        if (event.getController().getName().equals("simulate"))
        {
            simulate();
        }
        if (event.getController().getName().equals("loadSettings"))
        {
            try {
                FileReader reader = new FileReader(System.getProperty("user.dir") + "/settings.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;
                int index = 0;

                while ((line = bufferedReader.readLine()) != null)
                {
                    if (index == 0)
                    {
                        //do nothing
                    }
                    if (index == 1)
                    {
                        cookTimeField.getValueLabel().setText(line);
                    }
                    if (index == 2)
                    {
                        partySizeField.getValueLabel().setText(line);
                    }
                    if (index == 3)
                    {
                        numBoothsField.getValueLabel().setText(line);
                    }
                    if (index == 4)
                    {
                        diningDurationField.getValueLabel().setText(line);
                    }
                    if (index == 5)
                    {
                        partySizeVarianceField.getValueLabel().setText(line);
                    }
                    if (index == 6)
                    {
                        diningDurationVarianceField.getValueLabel().setText(line);
                    }
                    if (index == 7)
                    {
                        selectTime.setMin(Float.parseFloat(line));

                    }
                    if (index == 8)
                    {
                        selectTime.setMax(Float.parseFloat(line));
                        selectTime.setNumberOfTickMarks(1+(int) (selectTime.getMax()-selectTime.getMin()));
                    }
                    if (index == 9)
                    {
                        //read graph data
                        graph.clearData();
                        Pattern p = Pattern.compile("\\d+.\\d+");
                        Matcher m = p.matcher(line);
                        int counter = -1;
                        float p1 = 0;
                        float p2 = 0;
                        while (m.find())
                        {
                            counter++;
                            if (counter == 0)
                            {
                                System.out.println(m.group());
                                p1 = Float.parseFloat(m.group());
                            }
                            if (counter == 1)
                            {
                                System.out.println(m.group());
                                p2 = Float.parseFloat(m.group());
                            }
                            if (counter == 2)
                            {
                                graph.addPoint(new PVector(p1, p2));
                                counter = -1;
                            }

                        }
                    }
                    index++;
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getController().getName().equals("saveSettings"))
        {
            try
            {

                FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/settings.txt");

                int cookingTime = Integer.parseInt(cookTimeField.getValueLabel().getText());
                int partySize = Integer.parseInt(partySizeField.getValueLabel().getText());
                int numBooths = Integer.parseInt(numBoothsField.getValueLabel().getText());
                double diningDuration = Double.parseDouble(diningDurationField.getValueLabel().getText());
                double partySizeVariance = Double.parseDouble(partySizeVarianceField.getValueLabel().getText());
                double diningDurationVariance = Double.parseDouble(diningDurationVarianceField.getValueLabel().getText());
                int openHour = (int)selectTime.getMin();
                int closeHour = (int)selectTime.getMax();
                LinkedList<PVector> customerFlow = graph.getDataPoints();
                writer.write("start");
                writer.write("\r\n");   // write new line
                writer.write(cookingTime+"");
                writer.write("\r\n");   // write new line
                writer.write(partySize+"");
                writer.write("\r\n");   // write new line
                writer.write(numBooths+"");
                writer.write("\r\n");   // write new line
                writer.write(diningDuration+"");
                writer.write("\r\n");   // write new line
                writer.write(partySizeVariance+"");
                writer.write("\r\n");   // write new line
                writer.write(diningDurationVariance+"");
                writer.write("\r\n");   // write new line
                writer.write(openHour+"");
                writer.write("\r\n");   // write new line
                writer.write(closeHour+"");
                writer.write("\r\n");   // write new line
                writer.write(customerFlow+"");
                writer.write("\r\n");   // write new line
                writer.write("end");
                writer.close();
            } catch (IOException e)
            {
                System.out.println("You dun did it :(");
            }
        }

        //*******************************************************//
        //-----------------------Text boxes----------------------//
        //*******************************************************//
        if(event.isAssignableFrom(Textfield.class))
        {
            String controller = event.getName();
            String data = event.getStringValue();

            if (controller.compareTo("input") == 0)
            {
                data = data.replace(" ", "");
                String [] tmparr = data.split(",");
                float x = Float.parseFloat(tmparr[0]);
                float y = Float.parseFloat(tmparr[1]);
                graph.addPoint(new PVector(x,y));
                ui.get(Textfield.class,"input").clear();
            }


        }
    }


}
