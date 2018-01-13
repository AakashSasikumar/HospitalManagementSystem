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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.applet.AppletIllegalArgumentException;
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.*;

public class Ailments extends Application {
    String patientID = new String();
    Ailments(String a){
        patientID = a;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane parent = new BorderPane();
        HBox bottomHolder = new HBox();
        Button add = new Button("Add New Ailment");
        add.setMinSize(70, 50);
        Button cancel = new Button("Cancel");
        cancel.setMinSize(70, 50);
        bottomHolder.getChildren().addAll(add, cancel);
        Scene scene = new Scene(parent, 1000, 550);
        parent.setBottom(bottomHolder);
        bottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHolder.setSpacing(20);
        bottomHolder.setPadding(new Insets(0, 20, 20, 20));
        TableView<AilmentView> table = new TableView<>();
        final ObservableList<AilmentView> data = FXCollections.observableArrayList();
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");
        PreparedStatement ps = conn.prepareStatement("select * from ailments natural join prescription where p_id = '" + patientID + "'");
        ResultSet ailms = ps.executeQuery();
        while(ailms.next()) {
            String p_id = ailms.getString(1);
            String empID = ailms.getString(2);
            String ailm = ailms.getString(3);
            String prescription = ailms.getString(4);
            data.addAll(new AilmentView(p_id, empID, ailm, prescription));
        }
        TableColumn pID = new TableColumn("Patient ID");
        pID.setMinWidth(100);
        pID.setCellValueFactory(new PropertyValueFactory<>("pID"));
        TableColumn empID = new TableColumn("Employee ID");
        empID.setMinWidth(100);
        empID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        TableColumn ailm = new TableColumn("Ailment");
        ailm.setMinWidth(100);
        ailm.setCellValueFactory(new PropertyValueFactory<>("ailm"));
        TableColumn prescription = new TableColumn("Prescription");
        prescription.setMinWidth(100);
        prescription.setCellValueFactory(new PropertyValueFactory<>("prescription"));
        table.getColumns().addAll(pID, empID, ailm, prescription);
        table.setItems(data);
        parent.setTop(table);

        //Add new ailment and prescription
        VBox bottomParent = new VBox();
        Label addNew = new Label("Add Ailment");
        addNew.setAlignment(Pos.TOP_LEFT);
        bottomParent.getChildren().add(addNew);
        TextField pIDField = new TextField();
        pIDField.setPromptText("PID");
        pIDField.setMinSize(70, 50);
        TextField empIDField = new TextField();
        empIDField.setMinSize(70, 50);
        empIDField.setPromptText("EMP ID");
        TextField ailmField = new TextField();
        ailmField.setPromptText("Ailment");
        ailmField.setMinSize(70, 50);
        TextField prescriptionField = new TextField();
        prescriptionField.setMinSize(70, 50);
        prescriptionField.setPromptText("Prescription");
        HBox holder = new HBox();
        holder.getChildren().addAll(pIDField, empIDField, ailmField, prescriptionField);
        holder.setAlignment(Pos.TOP_CENTER);
        bottomParent.getChildren().addAll(holder);




        parent.setCenter(bottomParent);

        primaryStage.setScene(scene);
        primaryStage.show();
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement addAilment = conn.prepareStatement("insert into ailments VALUES (?, ?, ?)");
                    PreparedStatement addPrescription = conn.prepareStatement("insert into PRESCRIPTION VALUES (?, ?, ?)");

                    addAilment.setString(1, patientID);
                    addAilment.setString(2, empIDField.getText());
                    addAilment.setString(3, ailmField.getText());

                    addPrescription.setString(1, patientID);
                    addPrescription.setString(3, empIDField.getText());
                    addPrescription.setString(2, prescriptionField.getText());

                    ResultSet a = addAilment.executeQuery();
                    ResultSet b = addPrescription.executeQuery();

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

    public class AilmentView {
        public String getpID() {
            return pID.get();
        }

        public SimpleStringProperty pIDProperty() {
            return pID;
        }

        public void setpID(String pID) {
            this.pID.set(pID);
        }

        public String getEmpID() {
            return empID.get();
        }

        public SimpleStringProperty empIDProperty() {
            return empID;
        }

        public void setEmpID(String empID) {
            this.empID.set(empID);
        }

        public String getAilm() {
            return ailm.get();
        }

        public SimpleStringProperty ailmProperty() {
            return ailm;
        }

        public void setAilm(String ailm) {
            this.ailm.set(ailm);
        }

        public String getPrescription() {
            return prescription.get();
        }

        public SimpleStringProperty prescriptionProperty() {
            return prescription;
        }

        public void setPrescription(String prescription) {
            this.prescription.set(prescription);
        }

        private final SimpleStringProperty pID;
        private final SimpleStringProperty empID;
        private final SimpleStringProperty ailm;
        private final SimpleStringProperty prescription;

        AilmentView(String pID, String empID, String ailm, String prescription) {
            this.pID = new SimpleStringProperty(pID);
            this.empID = new SimpleStringProperty(empID);
            this.ailm = new SimpleStringProperty(ailm);
            this.prescription = new SimpleStringProperty(prescription);


        }
    }
}
