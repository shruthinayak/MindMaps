package mindmapsfresh;

import java.applet.Applet;

import java.awt.*;
//Abstract Window ToolKit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
//File not found
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class HelloApple extends Applet implements ActionListener {

    Main obj = new Main(); //Object of other package files.
    Functions f = new Functions();
    String currentDir = new File(".").getAbsolutePath();
    //System.out.println();
    // f.print(currentDir.substring(currentDir.length()-4));
    File dir = new File("HTMl");

    {
        dir.mkdir();
    }
    File files = new File(currentDir + "/HTMl");
    TextField inputLine = new TextField(50);
    Button CreateMindMap = new Button("Download");
    JComboBox drop = new JComboBox();
    CheckboxGroup category = new CheckboxGroup();
    Button Go = new Button("Go");
    Button close = new Button("Exit");
    String newline = "\n";
    JComboBox levels = new JComboBox();
    {
    
    {
        for (int i = 1; i < 15; i++) {
            levels.addItem(i);

        }
    }


    ArrayList<String> names = new ArrayList<String>(Arrays.asList(files.list()));

    {
        for (Iterator<String> i = names.iterator(); i.hasNext();) {
            String item = i.next();
            
               // String s = URLDecoder.decode(item, "UTF-8");
                drop.addItem(item);

                //drop.addItem(URLDecoder.decode(item, "UTF-8"));
               }
            

        }
    }

    public HelloApple() {

        setSize(1000,500);
        add(new Checkbox("New Topic", category, true));
        add(inputLine);


        add(new Checkbox("Existing Topic", category, false));
        addHorizontalLine(Color.gray);
        add(drop);
        addHorizontalLine(Color.gray);
        add("Levels", levels);
        addHorizontalLine(Color.gray);
        //category.setSelectedCheckbox()


        /*** Download ***/
        /*inputLine.setVisible(false);
        CreateMindMap.setVisible(false);*/
        //add(CreateMindMap);
        /*********************************/
        /*** Existing ***/
        /*drop.setVisible(false);
        open.setVisible(false);*/
        // add(open);
        /**************************/
        /*
        add(inputLine);
        add(CreateMindMap);
        add(drop);

        CreateMindMap.addActionListener(this);*/
        add(Go);
        Go.addActionListener(this);

        add(close);
        close.addActionListener(this);

    }

    private void addHorizontalLine(Color c) {
        // Add a Canvas 10000 pixels wide but
        // only 1 pixel high, which acts as
        // a horizontal line.
        Canvas line = new Canvas();
        line.setSize(10000, 1);
        line.setBackground(c);
        add(line);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == close) {
            System.exit(0);
        } else {
            String label = category.getSelectedCheckbox().getLabel();

            if (label.equals("New Topic")) {
                String url = inputLine.getText();
                if (url.isEmpty() || !url.contains("en.wikipedia.org")) {
                    JOptionPane.showMessageDialog(inputLine, "Invalid ! Try Again!");
                } else {
                    try {

                        String filename = url.substring(url.lastIndexOf("/") + 1);
                        System.out.print(filename);
                        String level = levels.getSelectedItem().toString();
                        obj.start(filename + ".html", url, level);
                    } catch (IOException ex) {
///////
                        Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                /*inputLine.setVisible(true);
                CreateMindMap.setVisible(true);
                CreateMindMap.addActionListener(this);*/
            } else if (label.equals("Existing Topic")) {
                /*           drop.setVisible(true);
                open.setVisible(true);*/
                String choice = drop.getSelectedItem().toString();
                try {
                    //System.out.println(choice);
                    String level = levels.getSelectedItem().toString();
                    obj.start(choice, "", level);
                } catch (IOException ex) {
                    Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            /* String s = inputLine.getText();
            f.print(s);
            try {
            obj.start("test.html", s);
            } catch (IOException ex) {
            Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
            Logger.getLogger(HelloApple.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }
}
