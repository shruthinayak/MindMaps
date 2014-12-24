/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mindmapsfresh;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

public class Main {

    static Functions f = new Functions();
    static String title;
    static String[][] paras = new String[400][400];
    static String[] headings = new String[200]; //Not used
    public static TitleKeywords[] data = new TitleKeywords[50]; //not Used for now
    public static File files;
    public static int levelfinal;

    public static void start(String filename, String url, String level) throws IOException, URISyntaxException {
        /****************************************************************************/
        f.print(filename);
        f.print(url);
        levelfinal = Integer.parseInt(level);

        if (url.contains("/")) {
            files = f.saveUrl(filename, url);
        }

        String currentDir = new File(".").getAbsolutePath();
        String mainhtml = f.readFile(currentDir + "/HTMl/" + filename);
        Document doc = Jsoup.parse(mainhtml);
        //Not used
        for (int i = 0; i < 50; i++) {
            data[i] = new TitleKeywords();
        }
//

        /****************************************************************************/
        /* Gets the Title of the article */
        title = getTitle(doc);
        if (title.length() > 20) {
            f.print(title);
        }

        /****************************************************************************/
        getParasKeywords(doc);
        //  relateparas(doc);
        //f.printArray(paras);
        f.mindmap(paras, title, levelfinal);
//        base.insert(path, title, paras);
        // f.wordcloud(title,paras);
        String finaloutput = "Default";
        // f.printHeadings(headings);
        //f.mindmap(title, headings, paras);
        f.print("Trying to run Python Server !");
        f.RunPythonServer(finaloutput);

        f.print("Python Server Running ! ");
//f.openBrowser("http://localhost:8000/D3/mbostock-d3-38a6c30/examples/tree/tree-interactive.html", "f");

    }

    /****************************************************************************/
    public static String getTitle(Document doc) {
        String s = doc.getElementById("firstHeading").text();
        f.print(s);
        return s;

    }

    /****************************************************************************/
    public static void getParasKeywords(Document doc) {
        int size = doc.getElementsByTag("p").size();
        int count = -1;
        for (int i = 0; i < size; i++) {
            int sizeanchor = doc.getElementsByTag("p").get(i).select("a").size();
         /*****Not used******/
            String bold = doc.getElementsByTag("p").get(i).select("b").text();

            if (!bold.isEmpty()) {
                count++;
                headings[count] = bold;
            }
/***********/
//f.print("-----------" + headings[count] + "---------------------");
//f.print("--------------------------------------------------------");
            int countj = 0;
            int empty = 0;
            for (int j = 0; j < sizeanchor; j++) {

                paras[i][j] = new String();
                String s = doc.getElementsByTag("p").get(i).select("a").get(j).attr("title");
                if (!(s.equals(" ") || s == null || s.equals("listen") || s.length() < 3 || s.startsWith("[") || Character.isDigit(s.charAt(0)) || s.equals("Wikipedia:Citation needed") || s.isEmpty())) {
                    //f.print(s);
                    //f.print("\n");
                    paras[i][countj] = s;
                    countj++;
                }


            }

            // f.print(empty);
            //paras[i] = trim(paras[i]);
        }

        //  f.printHeadings(headings);
        //f.printArray(paras);
    }

    /***************************************************************************************************/
    public static void relateparas(Document doc) {
        f.print("Hello");
        // f.print(doc);
        //Elements mainarticle = doc.getElementsByClass("rellink relarticle mainarticle");
        Elements mainarticle = doc.getElementsByClass("rellink");
        // f.print(mainarticle.size());


        int mainlen = mainarticle.size();
        if (mainlen < 4) {
            f.print("--------Cant Find---------");
            return;
        }
        int count = 0;
        for (int main = 0; main < mainlen; main++) {
            Element ele = mainarticle.get(main).nextElementSibling();

            /***************************Heading extracted*****************************/
            String raw = mainarticle.get(main).text();
            String heading = raw.substring(raw.indexOf(": ") + 2);
            if (!heading.startsWith("[")) {
                data[count].title = heading;
                count++;
            }
            /***************************Heading extracted*****************************/
            while (!ele.toString().startsWith("<p>")) {
                ele = ele.nextElementSibling();
            }
            int keylen = ele.select("a").size();

//f.print(ele.select("a").get(0).text());
            for (int keys = 0; keys < keylen; keys++) {
                String s = ele.select("a").get(keys).text();
                if (!(s == null || s.equals("listen") || s.startsWith("["))) {
                    data[main].keywords[keys] = ele.select("a").get(keys).text();
                }
            }

        }

        printtitle(data);

    }

    public static void printtitle(TitleKeywords t[]) {
        f.print("-----------------------Main Article Usage ---------------");
        for (int i = 0; i < t.length; i++) {
            f.print("Title : " + t[i].title);
            int len = t[i].keywords.length;
            for (int j = 0; j < len; j++) {
                f.print(t[i].keywords[j]);
            }
            f.print("_______________________");
        }
    }

    public static class TitleKeywords {

        String title = new String();
        String[] keywords = new String[50];

        public TitleKeywords() {
            for (int i = 0; i < keywords.length; i++) {
                keywords[i] = new String();
            }
        }
    }
}
