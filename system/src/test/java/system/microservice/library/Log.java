
package system.microservice.library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class Log {
    
    private String defaultErrorLogFileName = "Default-Error-Logs";
    private String defaultLogPath = "logs/tests/";
    private String defaultLogExtension = "txt";
    private String logFileName = null;
    private String className = null;

    public Log(String className, String logFileName) {
        String content = "Unknown error.";
        int exitCode = 1;

        if (className == null || className.isBlank()) {
            content = "Set class name.";
            this.saveLogAndExit(this.defaultErrorLogFileName, content, exitCode);
        } else {
            this.className = className;
        }
        
        if (logFileName == null || logFileName.isBlank()) {
            content = "Set log filename.";
            this.saveLogAndExit(this.defaultErrorLogFileName, content, exitCode);
        } else {
            this.logFileName = logFileName;
        }
    }

    public void save(String content) {
        this.writeLogFile(this.logFileName, this.defaultMessageLogFormat(content));
    }

    private void saveLogAndExit(String outputFileName, String content, int exitCode) {
        this.writeLogFile(outputFileName, this.defaultErrorMessageLogFormat(content));
        System.exit(exitCode);
    }

    private void writeLogFile(String outputFileName, String content) {
        String fileName = 
            this.defaultLogPath + "/" + outputFileName + "." + this.defaultLogExtension;

        try {
            BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName, true));
            writer.append(content);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("Could not write file: "+ outputFileName);
            System.exit(1);
        }
    }

    private String defaultMessageLogFormat(String content) {
        String finalContent = 
            
            "Date..: "+ Instant.now().toString() +"\n"+
            "Class.: "+ this.className +"\n"+
            "Result: \n\n"+ content +"\n"+
            "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";

        return finalContent;
    }

    private String defaultErrorMessageLogFormat(String content) {
        String finalContent = "Date..: "+ Instant.now().toString() +"\n";

        if (className != null && !className.isBlank()) {
            finalContent += "Class.: "+ this.className +"\n";
        }
     
        finalContent +=
            "Error.: "+ content +"\n"+
            "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";

        return finalContent;
    }
}
