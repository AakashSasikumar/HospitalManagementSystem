import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.text.TabExpander;
import javax.xml.soap.Text;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Surgeries extends Application {
    String patientID = new String();
    Surgeries(String a){
        patientID = a;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane parent = new BorderPane();
        HBox bottomHolder = new HBox();
        Button add = new Button("Add New Surgery");
        add.setMinSize(70, 50);
        Button cancel = new Button("Cancel");
        cancel.setMinSize(70, 50);
        bottomHolder.getChildren().addAll(add, cancel);
        Scene scene = new Scene(parent, 1000, 550);
        parent.setBottom(bottomHolder);
        bottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHolder.setSpacing(20);
        bottomHolder.setPadding(new Insets(0, 20, 20, 20));
        //System.out.println(patientID);
        TableView<SurgView> table = new TableView<>();
        final ObservableList<SurgView> data = FXCollections.observableArrayList();
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");
        PreparedStatement ps = conn.prepareStatement("select * from surgery where p_id = '" + patientID + "'");
        ResultSet surgs = ps.executeQuery();
        while(surgs.next()) {
            String id = surgs.getString(1);
            String pid = surgs.getString(2);
            String room = surgs.getString(3);
            String type = surgs.getString(4);
            String fee = surgs.getString(5);
            String dt = surgs.getString(6);
            String on = surgs.getString(7);
            String bgrp = surgs.getString(8);
            String bamt = surgs.getString(9);
            //System.out.println(id+pid+room+type+fee+dt+on+bgrp+bamt);
            data.addAll(new SurgView(id, pid, room, type, fee,dt,  on, bgrp, bamt));


        }

        TableColumn surgID = new TableColumn("Surgery ID");
        surgID.setMinWidth(100);
        surgID.setCellValueFactory(new PropertyValueFactory<>("surgID"));
        TableColumn pID = new TableColumn("P ID");
        pID.setCellValueFactory(new PropertyValueFactory<>("pID"));
        TableColumn roomNo = new TableColumn("Room");
        roomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        TableColumn ongoing = new TableColumn("Ongoing");
        ongoing.setCellValueFactory(new PropertyValueFactory<>("ongoing"));
        TableColumn surgType = new TableColumn("Type");
        surgType.setCellValueFactory(new PropertyValueFactory<>("surgType"));
        TableColumn surgFee = new TableColumn("Fee");
        surgFee.setCellValueFactory(new PropertyValueFactory<>("surgFee"));
        TableColumn bloodGrp = new TableColumn("Blood Group");
        bloodGrp.setCellValueFactory(new PropertyValueFactory<>("bloodGrp"));
        TableColumn bloodAmt = new TableColumn("Bl. Amount");
        bloodAmt.setCellValueFactory(new PropertyValueFactory<>("bloodAmt"));
        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        //table.setFixedCellSize(5);
        //table.setMaxSize(scene.getWidth(), 400);
        //table.setMinSize(primaryStage.getWidth(), 400);

        table.getColumns().addAll(surgID, pID, roomNo, surgType, surgFee, date, ongoing,  bloodGrp, bloodAmt);
        table.setItems(data);
        parent.setTop(table);



        //Add new patient

        VBox bottomParent = new VBox();
        Label addNew = new Label("Add new Surgery");
        addNew.setAlignment(Pos.TOP_LEFT);
        bottomParent.getChildren().add(addNew);

        TextField sID = new TextField();
        sID.setMinSize(70, 50);
        PreparedStatement getLatestSID = conn.prepareStatement("select surg_id from surgery order by (surg_id) desc");
        ResultSet latestSID = getLatestSID.executeQuery();
        String latest = new String();
        while(latestSID.next()) {
            String id = latestSID.getString(1);
            int num = Integer.parseInt(id.substring(3));
            num++;
            String strNum = String.valueOf(num);
            if (strNum.length() == 1) {
                strNum = "000" + strNum;
            }
            else if (strNum.length() == 2) {
                strNum = "00" + strNum;
            }
            else if (strNum.length() == 3) {
                strNum = "0" + strNum;
            }
            strNum = "SUR" + strNum;
            latest = strNum;
            break;
        }
        System.out.println(latest);
        sID.setText(latest);
        sID.setEditable(false);


        TextField paID = new TextField();
        paID.setPromptText("Patient ID");
        paID.setMinSize(70, 50);
        TextField room = new TextField();
        room.setMinSize(70, 50);
        room.setPromptText("RoomNo");
        TextField type = new TextField();
        type.setMinSize(70, 50);
        type.setPromptText("Type");
        TextField fee = new TextField();
        fee.setMinSize(70, 50);
        fee.setPromptText("Fee");
        TextField dt = new TextField();
        dt.setPromptText("yyyy-mm-dd");
        dt.setMinSize(70, 50);
        TextField ong = new TextField();
        ong.setMinSize(70, 50);
        ong.setPromptText("Ongoing(Yes/No");
        TextField bgrp = new TextField();
        bgrp.setMinSize(70, 50);
        bgrp.setPromptText("Blood Group");
        TextField bldAmt = new TextField();
        bldAmt.setMinSize(70, 50);
        bldAmt.setPromptText("Blood amount");
        HBox holder = new HBox();
        holder.getChildren().addAll(sID, paID, room, type, fee, dt, ong, bgrp, bldAmt);
        holder.setAlignment(Pos.TOP_CENTER);
        bottomParent.getChildren().addAll(holder);





        //Scene scene = new Scene(parent, 1000, 500);
        primaryStage.setScene(scene);
        parent.setCenter(bottomParent);
        primaryStage.setTitle("Welcome");
        primaryStage.show();



        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement addNew = conn.prepareStatement("insert into surgery values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    addNew.setString(1, sID.getText());
                    addNew.setString(2, paID.getText());
                    addNew.setInt(3, Integer.parseInt(room.getText()));
                    addNew.setString(4, type.getText());
                    addNew.setInt(5, Integer.parseInt(fee.getText()));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date parsed = null;
                    try {
                        parsed = format.parse(dt.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date sql = new Date(parsed.getTime());
                    addNew.setDate(6, sql);
                    addNew.setString(7, ong.getText());
                    addNew.setString(8, bgrp.getText());
                    addNew.setInt(9, Integer.parseInt(bldAmt.getText()));
                    ResultSet inp = addNew.executeQuery();
                    PreparedStatement deductBlood = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT - ?");
                    deductBlood.setInt(1, Integer.parseInt(bldAmt.getText()));
                    deductBlood.executeQuery();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });




    }
    public static class SurgView {
        public String getSurgID() {
            return surgID.get();
        }

        public SimpleStringProperty surgIDProperty() {
            return surgID;
        }

        public void setSurgID(String surgID) {
            this.surgID.set(surgID);
        }

        private final SimpleStringProperty surgID;

        public String getpID() {
            return pID.get();
        }

        public SimpleStringProperty pIDProperty() {
            return pID;
        }

        public void setpID(String pID) {
            this.pID.set(pID);
        }

        public String getRoomNo() {
            return roomNo.get();
        }

        public SimpleStringProperty roomNoProperty() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo.set(roomNo);
        }

        public String getOngoing() {
            return ongoing.get();
        }

        public SimpleStringProperty ongoingProperty() {
            return ongoing;
        }

        public void setOngoing(String ongoing) {
            this.ongoing.set(ongoing);
        }

        public String getSurgType() {
            return surgType.get();
        }

        public SimpleStringProperty surgTypeProperty() {
            return surgType;
        }

        public void setSurgType(String surgType) {
            this.surgType.set(surgType);
        }

        public String getSurgFee() {
            return surgFee.get();
        }

        public SimpleStringProperty surgFeeProperty() {
            return surgFee;
        }

        public void setSurgFee(String surgFee) {
            this.surgFee.set(surgFee);
        }

        public String getBloodGrp() {
            return bloodGrp.get();
        }

        public SimpleStringProperty bloodGrpProperty() {
            return bloodGrp;
        }

        public void setBloodGrp(String bloodGrp) {
            this.bloodGrp.set(bloodGrp);
        }

        public String getBloodAmt() {
            return bloodAmt.get();
        }

        public SimpleStringProperty bloodAmtProperty() {
            return bloodAmt;
        }

        public void setBloodAmt(String bloodAmt) {
            this.bloodAmt.set(bloodAmt);
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

        private final SimpleStringProperty pID;
        private  final SimpleStringProperty roomNo;
        private final SimpleStringProperty ongoing;
        private final SimpleStringProperty surgType;
        private final SimpleStringProperty surgFee;
        private final SimpleStringProperty bloodGrp;
        private final SimpleStringProperty bloodAmt;
        private final SimpleStringProperty date;

        private SurgView(String surgID, String pID, String roomNo, String surgType,String surgFee , String date, String ongoing, String bloodGrp, String bloodAmt){
            this.surgID = new SimpleStringProperty(surgID);
            this.pID = new SimpleStringProperty(pID);
            this.roomNo = new SimpleStringProperty(roomNo);
            this.ongoing = new SimpleStringProperty(ongoing);
            this.surgType = new SimpleStringProperty(surgType);
            this.surgFee = new SimpleStringProperty(surgFee);
            this.bloodGrp = new SimpleStringProperty(bloodGrp);
            this.bloodAmt = new SimpleStringProperty(bloodAmt);
            this.date = new SimpleStringProperty(date);


        }

    }
}
