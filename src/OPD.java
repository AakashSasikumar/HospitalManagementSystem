import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.xml.soap.Text;
import java.awt.*;
import java.sql.*;


public class OPD extends Application {
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");

    public OPD() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane parent = new BorderPane();
        Scene scene = new Scene(parent, 1000, 550);
        TableView<OpdView> opdTable = new TableView<>();
        final ObservableList<OpdView> data = FXCollections.observableArrayList();
        TableColumn opdRef = new TableColumn("OPD Ref");
        opdRef.setMinWidth(100);
        opdRef.setCellValueFactory(new PropertyValueFactory<>("opdRef"));
        TableColumn eid = new TableColumn("Emp ID");
        eid.setMinWidth(100);
        eid.setCellValueFactory(new PropertyValueFactory<>("eid"));
        TableColumn pid = new TableColumn("P ID");
        pid.setMinWidth(100);
        pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        TableColumn amt = new TableColumn("Amount");
        amt.setMinWidth(100);
        amt.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setMinWidth(100);
        TableColumn room = new TableColumn("Room");
        room.setMinWidth(100);
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        opdTable.getColumns().addAll(opdRef, eid, pid, amt, date, room);
        opdTable.setItems(data);

        HBox apTopHolder = new HBox();
        apTopHolder.setPadding(new Insets(30, 0, 0, 0));
        Label empSearch = new Label("Emp ID");
        empSearch.setPadding(new Insets(0, 0, 0, 80));
        TextField empSearchField = new TextField();
        empSearchField.setMinSize(70, 50);
        empSearchField.setPadding(new Insets(0, 20, 0, 20));
        Button empSearchBtn = new Button("Search");
        Label patSearch = new Label("OPD Ref. ID");
        patSearch.setPadding(new Insets(0, 0, 0, 80));
        TextField patSearchField = new TextField();
        patSearchField.setMinSize(70, 50);
        patSearchField.setPadding(new Insets(0, 20, 0, 0));
        Button patSearchBtn = new Button("Search");
        apTopHolder.getChildren().addAll(empSearch, empSearchField, empSearchBtn, patSearch, patSearchField, patSearchBtn);
        empSearchBtn.setMinSize(100, 50);
        patSearchBtn.setMinSize(100, 50);
        parent.setTop(apTopHolder);
        apTopHolder.setSpacing(20);


        parent.setCenter(opdTable);

        HBox bottomHolder = new HBox();
        //latestRef();
        //HBox textFieldHolder = new HBox();
        TextField ref = new TextField();
        ref.setEditable(false);
        ref.setText(latestRef());
        ref.setMinSize(50, 50);
        TextField empid = new TextField();
        empid.setMinSize(50, 50);
        empid.setPromptText("EMP ID");
        TextField paid = new TextField();
        paid.setPromptText("P ID");
        paid.setMinSize(50 ,50);
        TextField amount = new TextField();
        amount.setMinSize(50, 50);
        amount.setPromptText("Amount Paid");
        TextField dt = new TextField();
        dt.setMinSize(50, 50);
        dt.setPromptText("yyyy-mm-dd");
        TextField rm = new TextField();
        rm.setMinSize(50, 50);
        rm.setPromptText("Room Number");
        Button add = new Button("Add");
        add.setMinSize(70, 50);

        bottomHolder.getChildren().addAll(ref, empid, paid, amount, dt, rm, add);
        parent.setBottom(bottomHolder);


        primaryStage.setScene(scene);
        primaryStage.show();

        empSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ref.setText(latestRef());
                try {
                    System.out.println("asdfasdfasdfasdfasdf");
                    PreparedStatement stmnt = conn.prepareStatement("select * from OPD where emp_id = '" + empSearchField.getText() + "'");
                    //stmnt.setString(1, empid.getText());
                    ResultSet a = stmnt.executeQuery();
                    for (int i =0; i <opdTable.getItems().size(); i++) {
                        opdTable.getItems().clear();
                    }
                    while(a.next()){
                        String opdRef = a.getString(1);
                        String empId = a.getString(2);
                        String paID = a.getString(3);
                        String amt = a.getString(4);
                        String date = a.getString(5);
                        String rm = a.getString(6);
                        data.addAll(new OpdView(opdRef, empId, paID, amt, date, rm));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        patSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ref.setText(latestRef());
                try {
                    PreparedStatement stmnt = conn.prepareStatement("select * from OPD where emp_id = '" + patSearchField.getText() + "'");
                    //stmnt.setString(1, empid.getText());
                    ResultSet a = stmnt.executeQuery();
                    for (int i =0; i <opdTable.getItems().size(); i++) {
                        opdTable.getItems().clear();
                    }
                    while(a.next()){
                        String opdRef = a.getString(1);
                        String empId = a.getString(2);
                        String paID = a.getString(3);
                        String amt = a.getString(4);
                        String date = a.getString(5);
                        String rm = a.getString(6);
                        data.addAll(new OpdView(opdRef, empId, paID, amt, date, rm));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String latestRef(){
        String finalRef = new String();
        try {
            PreparedStatement stmnt = conn.prepareStatement("select opd_ref from OPD order by (opd_ref) desc");
            ResultSet a = stmnt.executeQuery();
            while(a.next()){
                String b = a.getString(1);
                b = b.substring(3);
                System.out.println(b);
                int c = Integer.parseInt(b);
                c++;
                String num = String.valueOf(c);
                if (num.length() == 1) {
                    num  = "00" + num;

                }
                else if (num.length() == 2) {
                    num = "0" + num;
                }
                num = "OPD" + num;
                finalRef = num;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalRef;
    }


    public class OpdView {
        public String getOpdRef() {
            return opdRef.get();
        }

        public SimpleStringProperty opdRefProperty() {
            return opdRef;
        }

        public void setOpdRef(String opdRef) {
            this.opdRef.set(opdRef);
        }

        public String getEid() {
            return eid.get();
        }

        public SimpleStringProperty eidProperty() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid.set(eid);
        }

        public String getPid() {
            return pid.get();
        }

        public SimpleStringProperty pidProperty() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid.set(pid);
        }

        public String getAmount() {
            return amount.get();
        }

        public SimpleStringProperty amountProperty() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount.set(amount);
        }

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getRoom() {
            return room.get();
        }

        public SimpleStringProperty roomProperty() {
            return room;
        }

        public void setRoom(String room) {
            this.room.set(room);
        }

        private final SimpleStringProperty opdRef;
        private final SimpleStringProperty eid;
        private final SimpleStringProperty pid;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty date;
        private final SimpleStringProperty room;

        OpdView(String opdRef, String eid, String pid, String amount, String date, String room){
            this.opdRef = new SimpleStringProperty(opdRef);
            this.eid = new SimpleStringProperty(eid);
            this.pid = new SimpleStringProperty(pid);
            this.amount = new SimpleStringProperty(amount);
            this.date = new SimpleStringProperty(date);
            this.room = new SimpleStringProperty(room);
        }
    }
}
