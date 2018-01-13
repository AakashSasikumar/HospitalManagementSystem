import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.soap.Text;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewAppointment extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");
        PreparedStatement latestApp = conn.prepareStatement("select appref_id from appointment order by (appref_id) desc");
        ResultSet rs = latestApp.executeQuery();
        String latestAPPID = new String();
        String latestAPID = new String();
        while(rs.next()) {
            latestAPPID = rs.getString(1);
            int latestNum = Integer.parseInt(latestAPPID.substring(2));
            latestNum++;
            String ltstNum = String.valueOf(latestNum);
            if (ltstNum.length() == 1){
                ltstNum = "000" + ltstNum;
            }
            else if (ltstNum.length() == 2) {
                ltstNum = "00" + ltstNum;
            }
            else if (ltstNum.length() == 3) {
                ltstNum = "0" + ltstNum;
            }
            ltstNum = "AP" + ltstNum;
            latestAPID = ltstNum;
            System.out.println(ltstNum);
            break;
        }
        BorderPane parent = new BorderPane();
        Scene scene = new Scene(parent, 700, 150);
        HBox tfHolder = new HBox();
        tfHolder.setPadding(new Insets(20, 0,0 , 0));
        TextField appRef = new TextField();
        appRef.setMinSize(70, 50);
        appRef.setText(latestAPID);
        appRef.setEditable(false);
        appRef.setPromptText("App. Ref. ID");
        TextField appEmp = new TextField();
        appEmp.setMinSize(70, 50);
        appEmp.setPromptText("EMP ID");
        TextField date = new TextField();
        date.setMinSize(70, 50);
        date.setPromptText("yyyy-mm-dd");
        TextField pID = new TextField();
        pID.setMinSize(70, 50);
        pID.setPromptText("P ID");

        tfHolder.getChildren().addAll(appRef, appEmp, date, pID);
        HBox bottomHolder = new HBox();
        bottomHolder.setSpacing(20);
        bottomHolder.setPadding(new Insets(20, 20, 20, 20));

        parent.setTop(tfHolder);
        Button update = new Button("Add");
        update.setMinSize(70, 50);
        Button cancel = new Button("Cancel");
        cancel.setMinSize(70, 50);
        bottomHolder.getChildren().addAll(update, cancel);
        bottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        parent.setBottom(bottomHolder);
        primaryStage.setScene(scene);
        primaryStage.show();

        String finalLatestAPID = latestAPID;
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement ins = conn.prepareStatement("insert into APPOINTMENT VALUEs (?, ?, ?, ?)");
                    ins.setString(1, finalLatestAPID);
                    ins.setString(2, appEmp.getText());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date parsed = null;
                    try {
                        parsed = format.parse(date.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date sql = new Date(parsed.getTime());
                    ins.setDate(3, sql);
                    ins.setString(4, pID.getText());
                    ResultSet inserted = ins.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });


    }
}
