package finalyearproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FXMLDocumentController implements Initializable {

    int pg = 1;

    @FXML
    public Label label;

    @FXML
    private TextField textfield;

    @FXML
    private TextArea code;

    @FXML
    private TextField address1;

    @FXML
    private TextField address2;

    @FXML
    private WebView webview;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        textfield.clear();
        SpeechToText st = new SpeechToText();
        textfield.setText(st.speech());
        label.setText("Press GO");
    }

    @FXML
    private void handleGoAction(ActionEvent event) throws IOException {
        String s = textfield.getText();
        String c="";
        FinalYearProject f = new FinalYearProject();
        int temp = f.FYP(s, pg);
        pg = temp;
        String dir = System.getProperty("user.dir");
        WebEngine engine = webview.getEngine();
        RandomAccessFile index = new RandomAccessFile(dir + "/src/finalyearproject/page" + pg + ".html", "rw");
        engine.load("file:\\\\\\" + dir + "/src/finalyearproject/page" + pg + ".html");
        address1.setText("file:\\\\\\" + dir + "/src/finalyearproject/page" + pg + ".html");
        address2.setText(dir + "/src/finalyearproject/page" + pg + ".html");
        index.seek(0);
        code.clear();
        while ((c = index.readLine()) != null) { 
        code.appendText(c+"\n");
        }
        textfield.setText("");
    }

    @FXML
    private void handleRefreshAction(ActionEvent event) throws IOException {
        String s = address1.getText();
        WebEngine engine = webview.getEngine();
        engine.load(s);
    }
    
    @FXML
    private void handleSaveAction(ActionEvent event) throws IOException {
        String s = address2.getText();
        String str = address1.getText();
        String c;
        RandomAccessFile index = new RandomAccessFile(s, "rw");
        PrintWriter del1 = new PrintWriter(s);
        del1.print(" ");
        del1.close();
        c=code.getText();
        index.writeBytes(c);
        WebEngine engine = webview.getEngine();
        engine.load(str);        
    }

    @FXML
    public void textLabel(String s) {
        label.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        label.setText("Press button");
    }
}
