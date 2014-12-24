/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mindmapsfresh;

/**
 *
 * @author shruthi
 */
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.python.util.PythonInterpreter;

public class Functions {

    static Functions f = new Functions();
    static String json = "{\n";

    public static File saveUrl(String filename, String urlString) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        File dir = new File("HTMl");
        dir.mkdir();

        File file;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(dir + "/" + filename);
            file = new File(dir + "/" + filename);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        return file;
    }

    public static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    public static void print(Object o) {
        if (!(o.equals("") || o.toString().isEmpty())) {
            System.out.println(o);
        }

    }

    public static void printArray(String[][] paras) {
        int len = paras.length;
        f.print(".......................Para-wise.................");
        for (int i = 0; i < len; i++) {
            f.print("Para :  " + i + "\nTitle :  " + paras[i][0]);

            for (int j = 1; j < paras[i].length; j++) {
                String s = paras[i][j];
                if (!(s == null || s.equals("listen") || s.length() < 3))// || s.startsWith("[") || Character.isDigit(s.charAt(0)) ) || s.isEmpty())
                {
                    f.print(i + "  " + paras[i][j]);
                }

            }
            f.print("__________________________");

        }

    }

    public static void printHeadings(String[] headings) {
        int len = headings.length;
        for (int i = 0; i < len; i++) {
            if (headings[i] != null) {
                f.print(i + " " + headings[i]);
            }

        }

    }

    public static void openBrowser(String url, String f) throws IOException, URISyntaxException {
        String protocol = "";
        /* if(f.equals("w"))
        protocol = "http://";
        else   protocol = "file://";*/
        print(url);


        if (Desktop.isDesktopSupported()) {

            Desktop.getDesktop().browse(new URI(protocol + url));
        }

    }

    public static void wordcloud(String title, String[][] keywords) throws IOException {

        String output = f.readFile("/home/shruthi/NetBeansProjects/MindMapsFresh/D3/jasondavies-d3-cloud-7fbd81e/examples/original.html");
        // Document render = Jsoup.parse(output);
        //f.print(render.getElementsByTag("body").select("script"));
        String content = "";


        int len = keywords.length;
        f.print(len);
        f.print(keywords.length);
        for (int j = 0; j < len; j++) {
            for (int i = 0; i < keywords[j].length; i++) {
                if (!(keywords[j][i] == null || keywords[j][i].contains("\"") || keywords[j][i].equals(""))) {
                    content = content + "\"" + keywords[j][i] + "\"" + ",";
                }

            }
        }
        String html = "<script src=\"../lib/d3/d3.js\"></script> \n";
        html = html
                + "<script src=\"../d3.layout.cloud.js\"></script>\n <body> \n<script> \n"
                + "  d3.layout.cloud().size([300, 300])" + "\n"
                + ".words(["
                + content
                + "]" + output;

        f.writeToFile(html);

    }

    public static void writeToFile(String html) {
        try {
            // Create file
            FileWriter fstream = new FileWriter("/home/shruthi/NetBeansProjects/MindMapsFresh/D3/mbostock-d3-38a6c30/examples/data/data.json");
            // FileWriter fstream = new FileWriter("/home/shruthi/NetBeansProjects/MindMapsFresh/D3/jasondavies-d3-cloud-7fbd81e/examples/simple.html");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(html);
            //Close the output stream
            out.close();
            //f.openBrowser("/home/shruthi/NetBeansProjects/MindMapsFresh/HTMl/jasondavies-d3-cloud-7fbd81e/examples/hello.html", "f");
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void mindmap(String[][] keywords, String maintitle, int levelfin) {

        String j = new String();
        String q = "\"";
        String cm = ",";
        String co = ":";
        String n = "\n";
        String name = q + "name" + q + ": ";
        String size = q + "size" + q + ": ";
        String children = q + "children" + q + ": [" + n;
        //"children": [
        j = "{" + name + q + maintitle + q + cm + n;
        j = j + children + "{";

        int len = keywords.length;
        // f.print(keywords[1][0]);
        int i = 1;
        int level1 = 0;
        while (i < len && level1 != levelfin) {
            //   f.print(keywords[1][0]);
            if (keywords[i][0] != null && (!keywords[i][0].isEmpty() || !keywords[i][0].equals(""))) {

                j = j + n + name + q + keywords[i][0] + q + cm + n + children + n;

                int level2 = 0;
                int k = 1;
                int level2len = keywords[i].length;
                while (k < level2len && level2 != 10) {

                    if (!(keywords[i][k] == "")) //  if((keywords[i][k]!=null || !keywords[i][k].isEmpty() || keywords[i][k]!=" "))
                    {

                        j = j + "{" + name;
                        j = j + q + keywords[i][k] + q + cm + size + "3938}";
                        if (level2 != 9) {
                            j = j + "," + n;
                        } else {
                            //  f.print(level2);
                            j = j + "]" + n + "}," + n + "{";
                        }
                        //  else j= j+"]"+n+"}]}";
                        level2++;
                    }
                    k++;
                }

                level1++;

            }
            i++;

        }

        j = j.substring(0, (j.length() - 3));
        j = j + "]}";
        j = j.replace("{\"name\": \"null\",\"size\": 3938},", "");
        j = j.replace("\n", "");
        j = j.replace(",{\"name\": \"null\",\"size\": 3938}", "");
        j = j.replace("{\"name\": \"\",\"size\": 3938},", "");
        j = j.replace(",{\"name\": \"\",\"size\": 3938}", "");
        j = j.replace("null", "..");
        j = j.replace("{\"name\": \"\",\"children\": []}", "");
        j = j.replace("[{\"name\": \"\",\"size\": 3938}]", "[]"); // for a node with no children
        // f.print(j);
        f.writeToFile(j);


    }

    public static void RunPythonServer(String str) throws URISyntaxException {

        try {
            Process p = Runtime.getRuntime().exec("python -m SimpleHTTPServer 8000");
            
            
            if (str.equals("Default")) //openBrowser("http://localhost:8000/NetBeansProjects/MindMapsFresh/D3/jasondavies-d3-cloud-7fbd81e/examples/simple.html", "f");
            {
            
                openBrowser("http://localhost:8000/D3/mbostock-d3-38a6c30/examples/tree/tree-interactive.html", "f");
            }
            //p.waitFor();
            
            /* BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=reader.readLine();
            while(line!=null)
            {
            System.out.println(line);
            line=reader.readLine();
            }*/

        } catch (IOException e1) {
        }/* catch (InterruptedException e2) {
        }*/

        System.out.println("Done");

    }
}
