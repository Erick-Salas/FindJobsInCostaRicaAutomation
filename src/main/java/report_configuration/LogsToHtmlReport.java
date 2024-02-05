package report_configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static page_configuration.BaseProperties.getProperty;
public class LogsToHtmlReport {

    //Read Logs and convert them into an HTML file
    public static void generateHtmlReport() {

        try (BufferedReader reader = new BufferedReader(new FileReader(getProperty("logLocation")));
             FileWriter writer = new FileWriter(getProperty("htmlReportLocation"))) {

            writer.write("<html><body><style>.tab-space { margin-left: 20px; }</style><table border='1'>");
            writer.write("<tr><th>List of Job(s)</th></tr>");


            String line;
            while ((line = reader.readLine()) != null) {

                // Parse log entry
                String[] parts = line.split("-");
                String threadName = parts[3];
                String message = line.substring(line.indexOf(threadName), line.length() );

                if (message.startsWith(" Page:")) {
                    writer.write("<tr bgcolor=" + "lightcoral" + ">");
                    writer.write("<td><strong>" + message + "</strong></td>");
                    writer.write("</tr>");
                }if(message.startsWith(" https")){
                    writer.write("<tr>");
                    writer.write("<td><p class=\"tab-space\"> - <a href = \"" + message + "\">"+message+"</a></p></td>");
                    writer.write("</tr>");
                    writer.write("<tr>");
                    writer.write("<td>");
                    writer.write("<hr />");
                    writer.write("</td>");
                    writer.write("</tr>");
                }
                else {
                    // Write log entry as a table row in HTML
                    writer.write("<tr>");
                    writer.write("<td><p class=\"tab-space\">" + message + "</p></td>");
                    writer.write("</tr>");

                }

            }

            writer.write("</table></body></html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
