import javafx.application.Platform;
 import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import oracle.jdbc.internal.OracleResultSet;
import oracle.sql.BFILE;


import java.io.FileInputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainPage extends Application{
    public static Label defaultLook;
    public static BorderPane parent;
    public static Date patientDOB;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");

        BorderPane parent = new BorderPane();
        Scene scene = new Scene(parent, 1400, 775);
        VBox menu = new VBox();
        Image bPic = new Image(new FileInputStream("media/hosp.jpg"));
        ImageView defaultLook = new ImageView(bPic);
        defaultLook.setFitWidth(1100);
        defaultLook.setFitHeight(800);
        parent.setCenter(defaultLook);


        menu.setMinWidth(300);
        parent.setLeft(menu);
        //parent.setCenter(display);
        BorderPane profilePic = new BorderPane();
        profilePic.setMinWidth(300);
        profilePic.setMinHeight(250);


        PreparedStatement pname = conn.prepareStatement("select emp_name, IMAGE from employee where emp_id = ?");
        pname.setString(1, Main.EMP_ID);
        ResultSet emp_name = pname.executeQuery();
        String sempName = new String();
        String file = new String();

        while(emp_name.next()) {
            sempName = emp_name.getString(1);
            file = emp_name.getString(2);
        }
        System.out.println(file);
        Image pPic = new Image(new FileInputStream(file));
        ImageView pic = new ImageView(pPic);
        pic.setFitHeight(225);
        pic.setFitWidth(250);
        profilePic.setCenter(pic);
        Label nameHolder = new Label("Welcome back " + sempName);
        nameHolder.setPadding(new Insets(0, 0, 0, 50));
        nameHolder.setAlignment(Pos.CENTER);
        profilePic.setBottom(nameHolder);

        //profilePic.setCenter(new Label().setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY))));
        profilePic.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Button patient = new Button("Patient Details");
        Button newPatient = new Button("Add New Patient");
        Button appointments = new Button("Appointments");
        Button visitors = new Button("Visitors");
        Button bloodBank = new Button("Blood Bank");
        Button profile = new Button("My Profile");
        Button logout = new Button("Logout");

        patient.setMinWidth(300);
        patient.setMinHeight(75);
        //(scene.getHeight() - 150) / 7
        newPatient.setMinWidth(300);
        newPatient.setMinHeight(75);
        appointments.setMinWidth(300);
        appointments.setMinHeight(75);
        profile.setMinHeight(75);
        profile.setMinWidth(300);
        logout.setMinWidth(300);
        logout.setMinHeight(75);
        visitors.setMinSize(300, 75);
        bloodBank.setMinSize(300, 75);


        menu.getChildren().addAll(profilePic, patient, newPatient, appointments,visitors, bloodBank, profile, logout);

        BorderPane patientDetails = new BorderPane();
        Image bg = new Image(new FileInputStream("media/bgg.jpg"));
        ImageView bgV = new ImageView(bg);
        bgV.setFitHeight(800);
        bgV.setFitWidth(1100);
        //patientDetails....
        BackgroundImage bgImg = new BackgroundImage(bg,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1100, 800, false, false, true, false));
        //patientDetails.setBackground(new Background(bgImg));


        Button search = new Button("Search");
        //search.setMinSize(100, 50);
        HBox topHolder = new HBox();
        HBox bottomHolder = new HBox();
        topHolder.setSpacing(20);
        bottomHolder.setSpacing(20);
        bottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHolder.setPadding(new Insets(0, 20, 10, 0));
        Button update = new Button("Update");
        Button cancel = new Button("Cancel");
        update.setMinSize(100, 50);
        cancel.setMinSize(100, 50);
        //cancel.setPadding(new Insets(0, 10, 0, 15));
        //update.setPadding(new Insets(0, 10, 0, 0));
        bottomHolder.getChildren().addAll(update, cancel);
        topHolder.setAlignment(Pos.CENTER);
        topHolder.setPadding(new Insets(50, 0, 10, 0));
        Label id = new Label();
        id.setText("Patient ID");
        id.setMinSize(70, 50);
        TextField pID = new TextField();
        pID.setPromptText("Patient ID");
        //pID.setPadding(new Insets(0, 20, 0, 20));
        pID.setMinSize(70, 50);
        search.setMinSize(100, 50);
        pID.setPromptText("Enter Patient ID");
        topHolder.getChildren().addAll(id, pID, search);

        //All fields of a patient
        TextField patientName = new TextField();
        patientName.setMinSize(70, 50);
        Label name = new Label("Patient Name ");
        name.setMinSize(70, 50);
        TextField age = new TextField();
        age.setMinSize(70, 50);
        Label patientAge = new Label("Patient DOB    ");
        patientAge.setMinSize(70, 50);
        TextField address = new TextField();
        address.setMinSize(70, 50);
        Label patientAddress = new Label("Address          ");
        patientAddress.setMinSize(70, 50);
        TextField email = new TextField();
        email.setMinSize(70, 50);
        Label patientEmail = new Label("Email              ");
        patientEmail.setMinSize(70, 50);
        TextField income = new TextField();
        income.setMinSize(70, 50);
        Label patientIncome = new Label("Income           ");
        patientIncome.setMinSize(70, 50);
        TextField gender = new TextField();
        gender.setMinSize(70, 50);
        Label patientGender = new Label("Gender           ");
        patientGender.setMinSize(70, 50);
        TextField dues = new TextField();
        dues.setMinSize(70, 50);
        Label patientDues = new Label("Dues               ");
        patientDues.setMinSize(70, 50);
        HBox a = new HBox();
        HBox b = new HBox();
        HBox c = new HBox();
        HBox d = new HBox();
        HBox e = new HBox();
        HBox f = new HBox();
        HBox g = new HBox();
        PreparedStatement getPInfo = conn.prepareStatement("select p_name, p_dob, p_addr, p_email, p_income, p_gender, p_dues from patient where p_id = ?");

        //scene.setFill();


        VBox details = new VBox();
        details.setSpacing(20);

        details.setAlignment(Pos.CENTER_LEFT);
        details.setPadding(new Insets(0, 50, 0, 50));

        a.getChildren().addAll(name, patientName);
        b.getChildren().addAll(patientAge, age);
        c.getChildren().addAll(patientAddress, address);
        d.getChildren().addAll(patientEmail, email);
        e.getChildren().addAll(patientIncome, income);
        f.getChildren().addAll(patientGender, gender);
        g.getChildren().addAll(patientDues, dues);

        details.getChildren().addAll(a, b, c, d, e, f, g);

        Button surgeries = new Button("Surgery History");
        //Button addSurg = new Button("Add surgery");
        Button ailements = new Button("Ailments");
        surgeries.setMinSize(150, 50);
        ailements.setMinSize(150, 50);
        VBox dets = new VBox();
        dets.setPadding(new Insets(100, 200, 0, 0));
        dets.getChildren().addAll(surgeries, ailements);
        dets.setSpacing(20);

        patientDetails.setRight(dets);
        patientDetails.setLeft(details);
        patientDetails.setTop(topHolder);
        patientDetails.setBottom(bottomHolder);

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });

        //Add new patient
        BorderPane addNewPatient = new BorderPane();
        //addNewPatient.setBackground(new Background(bgImg));
        PreparedStatement getID = conn.prepareStatement("select p_id from patient order by (p_id) desc");
        ResultSet latestID = getID.executeQuery();
        String latestPID = new String();
        while(latestID.next()) {
            latestPID = latestID.getString(1);
            System.out.println(latestID.getString(1));
            break;
        }
        int number = Integer.parseInt(latestPID.substring(2));
        number++;
        String strNum = String.valueOf(number);
        if (strNum.length() == 1){
            strNum = "000" + strNum;
        }
        else if (strNum.length() == 2) {
            strNum = "00" + strNum;
        }
        else if (strNum.length() == 3) {
            strNum = "0" + strNum;
        }
        strNum = "PA" + strNum;
        final String ID = strNum;
        TextField newID = new TextField();
        newID.setMinSize(70, 50);
        Label newLID = new Label("Patient ID       ");
        TextField newpatientName = new TextField();
        newpatientName.setMinSize(70, 50);
        Label newname = new Label("Patient Name ");
        newname.setMinSize(70, 50);
        TextField newage = new TextField();
        newage.setPromptText("dd-mm-yyy");
        newage.setMinSize(70, 50);
        Label newpatientAge = new Label("Patient DOB    ");
        newpatientAge.setMinSize(70, 50);
        TextField newaddress = new TextField();
        newaddress.setMinSize(70, 50);
        Label newpatientAddress = new Label("Address          ");
        newpatientAddress.setMinSize(70, 50);
        TextField newemail = new TextField();
        newemail.setMinSize(70, 50);
        Label newpatientEmail = new Label("Email              ");
        newpatientEmail.setMinSize(70, 50);
        TextField newincome = new TextField();
        newincome.setMinSize(70, 50);
        Label newpatientIncome = new Label("Income           ");
        newpatientIncome.setMinSize(70, 50);
        TextField newgender = new TextField();
        newgender.setMinSize(70, 50);
        Label newpatientGender = new Label("Gender           ");
        newpatientGender.setMinSize(70, 50);
        TextField newdues = new TextField();
        newdues.setMinSize(70, 50);
        Label newpatientDues = new Label("Dues               ");
        newpatientDues.setMinSize(70, 50);
        HBox nn = new HBox();
        HBox na = new HBox();
        HBox nb = new HBox();
        HBox nc = new HBox();
        HBox nd = new HBox();
        HBox ne = new HBox();
        HBox nf = new HBox();
        HBox ng = new HBox();
        VBox ndetails = new VBox();
        ndetails.setSpacing(20);

        ndetails.setAlignment(Pos.CENTER_LEFT);
        ndetails.setPadding(new Insets(0, 50, 0, 50));
        nn.getChildren().addAll(newLID, newID);
        na.getChildren().addAll(newname, newpatientName);
        nb.getChildren().addAll(newpatientAge, newage);
        nc.getChildren().addAll(newpatientAddress, newaddress);
        nd.getChildren().addAll(newpatientEmail, newemail);
        ne.getChildren().addAll(newpatientIncome, newincome);
        nf.getChildren().addAll(newpatientGender, newgender);
        ng.getChildren().addAll(newpatientDues, newdues);

        HBox nbottomHolder = new HBox();
        nbottomHolder.setSpacing(20);
        nbottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        nbottomHolder.setPadding(new Insets(0, 20, 10, 0));
        Button nadd = new Button("Add");
        Button ncancel = new Button("Cancel");
        nadd.setMinSize(100, 50);
        ncancel.setMinSize(100, 50);
        nbottomHolder.getChildren().addAll(nadd, ncancel);
        ndetails.getChildren().addAll(nn, na, nb, nc, nd, ne, nf, ng);
        addNewPatient.setLeft(ndetails);
        addNewPatient.setBottom(nbottomHolder);
        newID.setEditable(false);
        newID.setText(ID);
        /*PreparedStatement insNewPatient = conn.prepareStatement("insert into patient values (?, ?, ?, ?, ?, ?, ?, ?)");
        newID.setText(strNum);
        insNewPatient.setString(1, strNum);
        insNewPatient.setString(2, newpatientName.getText());
        SimpleDateFormat nformat = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date nparsed = nformat.parse(newage.getText());
        Date nsql = new Date(nparsed.getTime());
        insNewPatient.setDate(3, nsql);*/





        //appointment
        BorderPane appointment = new BorderPane();

        HBox apTopHolder = new HBox();
        apTopHolder.setPadding(new Insets(30, 0, 0, 0));
        Label empSearch = new Label("Emp ID");
        empSearch.setPadding(new Insets(0, 0, 0, 80));
        TextField empSearchField = new TextField();
        empSearchField.setMinSize(70, 50);
        empSearchField.setPadding(new Insets(0, 20, 0, 20));
        Button empSearchBtn = new Button("Search");
        Label patSearch = new Label("App Ref. ID");
        patSearch.setPadding(new Insets(0, 0, 0, 80));
        TextField patSearchField = new TextField();
        patSearchField.setMinSize(70, 50);
        patSearchField.setPadding(new Insets(0, 20, 0, 0));
        Button patSearchBtn = new Button("Search");
        apTopHolder.getChildren().addAll(empSearch, empSearchField, empSearchBtn, patSearch, patSearchField, patSearchBtn);
        empSearchBtn.setMinSize(100, 50);
        patSearchBtn.setMinSize(100, 50);
        appointment.setTop(apTopHolder);
        apTopHolder.setSpacing(20);

        TableView<ApptView> table = new TableView<>();
        final ObservableList<ApptView> data = FXCollections.observableArrayList();
        TableColumn appRef = new TableColumn("App Ref. ID");
        appRef.setCellValueFactory(new PropertyValueFactory<>("appRefID"));
        TableColumn appEmp = new TableColumn("Emp ID");
        appEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
        TableColumn appDate = new TableColumn("Date");
        appDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn appPat = new TableColumn("P ID");
        appPat.setCellValueFactory(new PropertyValueFactory<>("pID"));
        //table.setPadding(new Insets(20, 5, 20, 5));

        table.getColumns().addAll(appRef, appEmp, appDate, appPat);
        table.setItems(data);
        appointment.setCenter(table);

        HBox appBottomHolder = new HBox();
        appBottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        Button newApp = new Button("New Appointment");
        newApp.setMinSize(70, 50);
        Button deleteApp = new Button("Delete Appointment");
        deleteApp.setMinSize(70, 50);
        Button appUpdate = new Button("Update");
        appUpdate.setMinSize(70, 50);
        Button OPD = new Button("OPD");
        OPD.setMinSize(70, 50);
        Button appCancel = new Button("Cancel");
        appCancel.setMinSize(70, 50);
        appBottomHolder.setSpacing(20);
        appBottomHolder.getChildren().addAll(OPD, newApp, deleteApp, appCancel);
        appBottomHolder.setPadding(new Insets(0, 20, 10, 0));

        appointment.setBottom(appBottomHolder);
        newApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new NewAppointment().start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        //Visitors
        BorderPane visits = new BorderPane();

        TableView<Visitors> visitTable = new TableView<>();
        final ObservableList<Visitors> visitData = FXCollections.observableArrayList();
        TableColumn visitorName = new TableColumn("Visitor Name");
        visitorName.setMinWidth(150);
        visitorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn visitpID = new TableColumn("P ID");

        visitpID.setCellValueFactory(new PropertyValueFactory<>("pID"));
        TableColumn visitDate = new TableColumn("Date");
        visitDate.setMinWidth(200);
        visitDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        visitTable.getColumns().addAll(visitorName, visitpID, visitDate);
        visitTable.setItems(visitData);

        HBox visitBottomHolder = new HBox();
        Button visitCancel = new Button("Cancel");
        Button visitAdd = new Button("Add Visitor");
        visitAdd.setMinSize(70, 50);
        visitBottomHolder.getChildren().addAll(visitAdd, visitCancel);
        visitBottomHolder.setSpacing(20);
        visitBottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        visitCancel.setMinSize(70, 50);
        visitBottomHolder.setPadding(new Insets(0, 20, 10, 0));
        visits.setCenter(visitTable);
        visits.setBottom(visitBottomHolder);




        //Blood Bank
        BorderPane blood = new BorderPane();
        HBox bloodBottomHolder = new HBox();
        Button blCancel = new Button("Cancel");
        Button blUpdate = new Button("Add Blood");
        blCancel.setMinSize(70, 50);
        blUpdate.setMinSize(70, 50);
        bloodBottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        bloodBottomHolder.setSpacing(20);
        bloodBottomHolder.setPadding(new Insets(0, 20, 10, 0));
        bloodBottomHolder.getChildren().addAll(blUpdate, blCancel);
        blood.setBottom(bloodBottomHolder);

        BorderPane centerPane = new BorderPane();
        Label currentBloodLab = new Label("Current Blood:");
        VBox currentBlood = new VBox();
        Label op = new Label("O+  ");
        TextField opVal = new TextField();
        opVal.setEditable(false);
        opVal.setMinSize(70, 50);
        Label on = new Label("O-   ");
        TextField onVal = new TextField();
        onVal.setEditable(false);
        onVal.setMinSize(70, 50);
        Label ap = new Label("A+  ");
        TextField apVal = new TextField();
        apVal.setEditable(false);
        apVal.setMinSize(70, 50);
        Label an = new Label("A-   ");
        TextField anVal = new TextField();
        anVal.setEditable(false);
        anVal.setMinSize(70, 50);
        Label bp = new Label("B+  ");
        TextField bpVal = new TextField();
        bpVal.setEditable(false);
        bpVal.setMinSize(70, 50);
        Label bn = new Label("B-   ");
        TextField bnVal = new TextField();
        bnVal.setEditable(false);
        bnVal.setMinSize(70, 50);
        Label abp = new Label("AB+");
        TextField abpVal = new TextField();
        abpVal.setEditable(false);
        abpVal.setMinSize(70, 50);
        Label abn = new Label("AB- ");
        TextField abnVal = new TextField();
        abnVal.setEditable(false);
        abnVal.setMinSize(70, 50);

        HBox bla = new HBox();
        bla.getChildren().addAll(op, opVal);
        bla.setSpacing(20);
        HBox blb = new HBox();
        blb.getChildren().addAll(on, onVal);
        blb.setSpacing(20);
        HBox blc = new HBox();
        blc.getChildren().addAll(ap, apVal);
        blc.setSpacing(20);
        HBox bld = new HBox();
        bld.setSpacing(20);
        bld.getChildren().addAll(an, anVal);
        HBox ble = new HBox();
        ble.setSpacing(20);
        ble.getChildren().addAll(bp, bpVal);
        HBox blf = new HBox();
        blf.setSpacing(20);
        blf.getChildren().addAll(bn, bnVal);
        HBox blg = new HBox();
        blg.setSpacing(20);
        blg.getChildren().addAll(abp, abpVal);
        HBox blh = new HBox();
        blh.setSpacing(20);
        blh.getChildren().addAll(abn, abnVal);

        currentBlood.getChildren().addAll(currentBloodLab, bla, blb, blc, bld, ble, blf, blg, blh);
        centerPane.setLeft(currentBlood);
        currentBlood.setPadding(new Insets(150,40,0,100));

        VBox newBlood = new VBox();
        Label opa = new Label("O+  ");
        TextField opaVal = new TextField();
        opaVal.setText("0");
        opaVal.setMinSize(70, 50);
        Label ona = new Label("O-   ");
        TextField onaVal = new TextField();
        onaVal.setText("0");
        onaVal.setMinSize(70, 50);
        Label apa = new Label("A+  ");
        TextField apaVal = new TextField();
        apaVal.setText("0");
        apaVal.setMinSize(70, 50);
        Label ana = new Label("A-   ");
        TextField anaVal = new TextField();
        anaVal.setText("0");
        anaVal.setMinSize(70, 50);
        Label bpa = new Label("B+  ");
        TextField bpaVal = new TextField();
        bpaVal.setText("0");
        bpaVal.setMinSize(70, 50);
        Label bna = new Label("B-   ");
        TextField bnaVal = new TextField();
        bnaVal.setText("0");
        bnaVal.setMinSize(70, 50);
        Label abpa = new Label("AB+");
        TextField abpaVal = new TextField();
        abpaVal.setText("0");
        abpaVal.setMinSize(70, 50);
        Label abna = new Label("AB- ");
        TextField abnaVal = new TextField();
        abnaVal.setText("0");
        abnaVal.setMinSize(70, 50);

        HBox blaa = new HBox();
        Label increment = new Label("Increase By:                           ");
        blaa.getChildren().addAll(opa, opaVal);
        blaa.setSpacing(20);
        HBox blba = new HBox();
        blba.getChildren().addAll(ona, onaVal);
        blba.setSpacing(20);
        HBox blca = new HBox();
        blca.getChildren().addAll(apa, apaVal);
        blca.setSpacing(20);
        HBox blda = new HBox();
        blda.setSpacing(20);
        blda.getChildren().addAll(ana, anaVal);
        HBox blea = new HBox();
        blea.setSpacing(20);
        blea.getChildren().addAll(bpa, bpaVal);
        HBox blfa = new HBox();
        blfa.setSpacing(20);
        blfa.getChildren().addAll(bna, bnaVal);
        HBox blga = new HBox();
        blga.setSpacing(20);
        blga.getChildren().addAll(abpa, abpaVal);
        HBox blha = new HBox();
        blha.setSpacing(20);
        blha.getChildren().addAll(abna, abnaVal);

        newBlood.getChildren().addAll(increment, blaa, blba, blca, blda, blea, blfa, blga, blha);
        newBlood.setPadding(new Insets(0, 100, 0, 0));
        newBlood.setAlignment(Pos.CENTER_RIGHT);
        centerPane.setRight(newBlood);


        blood.setCenter(centerPane);

        BorderPane myProfile = new BorderPane();

        //myProfile
        BorderPane profileDetails = new BorderPane();
        PreparedStatement profileStatement = conn.prepareStatement("select emp_id, emp_name, emp_addr, emp_type, emp_dept, emp_dob, emp_pw from employee where emp_id = ?");
        profileStatement.setString(1, Main.EMP_ID);
        ResultSet empInfo = profileStatement.executeQuery();
        Label eID = new Label("ID                 ");
        TextField empID = new TextField();
        eID.setMinSize(70, 50);
        empID.setMinSize(70, 50);
        empID.setEditable(false);
        Label empName = new Label("Name           ");
        TextField eName = new TextField();
        eName.setEditable(false);
        empName.setMinSize(70, 50);
        eName.setMinSize(70, 50);
        Label empAddr = new Label("Address       ");
        TextField eAddr = new TextField();
        eAddr.setEditable(false);
        empAddr.setMinSize(70, 50);
        eAddr.setMinSize(70, 50);
        Label designation = new Label("Designation ");
        TextField eDesignation = new TextField();
        eDesignation.setEditable(false);
        designation.setMinSize(70, 50);
        eDesignation.setMinSize(70, 50);
        Label department = new Label("Department ");
        TextField eDepartment = new TextField();
        eDepartment.setEditable(false);
        department.setMinSize(70, 50);
        eDepartment.setMinSize(70, 50);
        Label eAge = new Label("Age              ");
        TextField empAge = new TextField();
        empAge.setEditable(false);
        eAge.setMinSize(70, 50);
        empAge.setMinSize(70, 50);
        Label ePass = new Label("Password     ");
        PasswordField empPass = new PasswordField();
        ePass.setMinSize(70, 50);
        empPass.setMinSize(70, 50);
        while (empInfo.next()) {
            empID.setText(empInfo.getString(1));
            eName.setText(empInfo.getString(2));
            eAddr.setText(empInfo.getString(3));
            eDesignation.setText(empInfo.getString(4));
            eDepartment.setText(empInfo.getString(5));
            empAge.setText(empInfo.getString(6));
            empPass.setText(empInfo.getString(7));
        }
        HBox a1 = new HBox();
        HBox b1 = new HBox();
        HBox c1 = new HBox();
        HBox d1 = new HBox();
        HBox e1 = new HBox();
        HBox f1 = new HBox();
        HBox g1 = new HBox();
        a1.getChildren().addAll(eID, empID);
        b1.getChildren().addAll(empName, eName);
        c1.getChildren().addAll(empAddr, eAddr);
        d1.getChildren().addAll(designation, eDesignation);
        e1.getChildren().addAll(department, eDepartment);
        f1.getChildren().addAll(eAge, empAge);
        g1.getChildren().addAll(ePass, empPass);
        VBox empDetails = new VBox(20);
        empDetails.setAlignment(Pos.CENTER_LEFT);
        empDetails.setPadding(new Insets(0, 50, 0, 50));
        empDetails.getChildren().addAll(a1, b1, c1, d1, e1, f1, g1);
        HBox profBottomHolder = new HBox(20);
        Button updatePass = new Button("Update Password");
        Button profCancel = new Button("Cancel");
        profCancel.setMinSize(70, 50);
        updatePass.setMinSize(70, 50);
        profBottomHolder.getChildren().addAll(updatePass, profCancel);
        profileDetails.setLeft(empDetails);
        profBottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        profBottomHolder.setPadding(new Insets(0, 20, 10, 50));
        profileDetails.setBottom(profBottomHolder);



        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome");
        primaryStage.show();
        deleteApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new DeleteApplication().start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        visitAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new AddVisitor().start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        OPD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new OPD().start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        bloodBank.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(blood);
                try {
                    PreparedStatement getBlood = conn.prepareStatement("select BLOOD_AMT from BLOODBANK");
                    ResultSet bloods = getBlood.executeQuery();
                    int i = 0;
                    while(bloods.next()) {
                        if (i == 7) {
                            abnVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;
                        }
                        if (i == 6) {
                            abpVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;
                        }
                        if (i == 5) {
                            bnVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;
                        }
                        if (i == 4) {
                            bpVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;

                        }
                        if (i == 3) {
                            anVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;

                        }
                        if (i==2) {
                            apVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;

                        }
                        if (i == 1) {
                            onVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;

                        }
                        if (i == 0) {
                            opVal.setText(String.valueOf(bloods.getFloat(1)));
                            i++;
                        }
                        System.out.println(i);
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });
        blUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement updateop = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'O+'");
                    PreparedStatement updateon = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'O-'");
                    PreparedStatement updateap = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'A+'");
                    PreparedStatement updatean = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'A-'");
                    PreparedStatement updatebp = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'B+'");
                    PreparedStatement updatebn = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'B-'");
                    PreparedStatement updateabp = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'AB+'");
                    PreparedStatement updateabn = conn.prepareStatement("update BLOODBANK set BLOOD_AMT = BLOOD_AMT + ? where BLOODGROUP = 'AB-'");

                    updateop.setInt(1, Integer.parseInt(opaVal.getText()));
                    updateon.setInt(1, Integer.parseInt(onaVal.getText()));
                    updateap.setInt(1, Integer.parseInt(apaVal.getText()));
                    updatean.setInt(1, Integer.parseInt(anaVal.getText()));
                    updatebp.setInt(1, Integer.parseInt(bpaVal.getText()));
                    updatebn.setInt(1, Integer.parseInt(bnaVal.getText()));
                    updateabp.setInt(1, Integer.parseInt(abpaVal.getText()));
                    updateabn.setInt(1, Integer.parseInt(abnaVal.getText()));

                    ResultSet a = updateop.executeQuery();
                    ResultSet b = updateon.executeQuery();
                    ResultSet c = updateap.executeQuery();
                    ResultSet d = updatean.executeQuery();
                    ResultSet e = updatebp.executeQuery();
                    ResultSet f = updatebn.executeQuery();
                    ResultSet g = updateabp.executeQuery();
                    ResultSet h = updateabn.executeQuery();

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });
        blCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });
        empSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i =0; i <table.getItems().size(); i++) {
                    table.getItems().clear();
                }
                try {
                    PreparedStatement getApt = conn.prepareStatement("select * from APPOINTMENT where emp_id = '" + empSearchField.getText() + "'");
                    ResultSet appts = getApt.executeQuery();
                    while(appts.next()) {
                        String appReffID = appts.getString(1);
                        String appEmpID = appts.getString(2);
                        String date = appts.getString(3);
                        String pID = appts.getString(4);
                        data.addAll(new ApptView(appReffID, appEmpID, date, pID));
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });

        patSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i =0; i <table.getItems().size(); i++) {
                    table.getItems().clear();
                }
                try {
                    PreparedStatement getApt = conn.prepareStatement("select * from APPOINTMENT where APPREF_ID = '" + patSearchField.getText() + "'");
                    ResultSet appts = getApt.executeQuery();
                    while(appts.next()) {
                        String appReffID = appts.getString(1);
                        String appEmpID = appts.getString(2);
                        String date = appts.getString(3);
                        String pID = appts.getString(4);
                        data.addAll(new ApptView(appReffID, appEmpID, date, pID));
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });

        visitCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    getPInfo.setString(1, pID.getText());
                    ResultSet patientInfo = getPInfo.executeQuery();
                    while(patientInfo.next()) {

                        patientName.setText(patientInfo.getString(1));
                        patientDOB = patientInfo.getDate(2);
                        age.setText(patientDOB.toString());
                        address.setText(patientInfo.getString(3));
                        email.setText(patientInfo.getString(4));
                        income.setText(String.valueOf(patientInfo.getDouble(5)));
                        gender.setText(patientInfo.getString(6));
                        dues.setText(patientInfo.getString(7));
                       // patientInfo.get

                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

            }
        });

        updatePass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement updatePassword = conn.prepareStatement("update employee set emp_pw = ? where emp_id = ?");
                    updatePassword.setString(1, empPass.getText());
                    updatePassword.setString(2, Main.EMP_ID);
                    updatePassword.executeQuery();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

            }
        });

        nadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    PreparedStatement insNewPatient = conn.prepareStatement("insert into patient values (?, '', to_date('10-MAR-1984','DD-MON-YYYY'), '', '', '', 0, 0)");
                    System.out.println();
                    insNewPatient.setString(1, newID.getText());
                    /*insNewPatient.setString(1, ID);
                    insNewPatient.setString(2, newpatientName.getText());
                    SimpleDateFormat nformat = new SimpleDateFormat("dd-mm-yyyy");
                    java.util.Date nparsed = nformat.parse(newage.getText());
                    Date nsql = new Date(nparsed.getTime());
                    insNewPatient.setDate(3, nsql);
                    insNewPatient.setString(4, newaddress.getText());
                    insNewPatient.setString(5, newemail.getText());
                    insNewPatient.setString(7, newgender.getText());
                    insNewPatient.setInt(6, Integer.parseInt(newincome.getText()));
                    insNewPatient.setInt(8, Integer.parseInt(newdues.getText()));*/
                    insNewPatient.executeQuery();
                    PreparedStatement insStmnt = conn.prepareStatement("update patient set p_name = ?, p_dob = ?, p_addr = ?, p_email = ?, p_income = ?, p_gender = ?, p_dues = ? where p_id = ?");
                    insStmnt.setString(1, newpatientName.getText());
                    SimpleDateFormat nformat = new SimpleDateFormat("dd-mm-yyyy");
                    java.util.Date nparsed = nformat.parse(newage.getText());
                    System.out.println(newpatientName.getText());
                    System.out.println(newage.getText());
                    System.out.println(newaddress.getText());
                    System.out.println(newemail.getText());
                    System.out.println(newincome.getText());
                    System.out.println(newgender.getText());
                    System.out.println(newdues.getText());
                    Date nsql = new Date(nparsed.getTime());
                    insStmnt.setDate(2, nsql);
                    insStmnt.setString(3, newaddress.getText());
                    insStmnt.setString(4, newemail.getText());
                    insStmnt.setString(5, newincome.getText());
                    insStmnt.setString(6, newgender.getText());
                    insStmnt.setString(7, newdues.getText());
                    insStmnt.setString(8, ID);
                    ResultSet insP = insStmnt.executeQuery();

                } catch (SQLException e2) {
                e2.printStackTrace();
                } catch (ParseException e2) {
                e2.printStackTrace();
            }
            }
        });

        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement updatePatient = conn.prepareStatement("update patient set p_name = ?, p_dob = ?, p_addr = ?, p_email = ?, p_income = ?, p_gender = ?, p_dues = ? where p_id = ? ");
                    updatePatient.setString(1, patientName.getText());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date parsed = format.parse(age.getText());
                    Date sql = new Date(parsed.getTime());
                    updatePatient.setDate(2, sql);
                    updatePatient.setString(3, address.getText());
                    updatePatient.setString(4, email.getText());
                    updatePatient.setString(5, income.getText());
                    updatePatient.setString(6, gender.getText());
                    updatePatient.setString(7, dues.getText());
                    updatePatient.setString(8, pID.getText());
                    updatePatient.executeQuery();
                    //updatePatient.setDate()
                } catch (SQLException e2) {
                    e2.printStackTrace();
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }

            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new GUI().start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

        surgeries.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new Surgeries(pID.getText()).start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        ailements.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new Ailments(pID.getText()).start(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        newPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(addNewPatient);
            }
        });
        appointments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(appointment);
            }
        });

        patient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(patientDetails);
            }
        });

        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(profileDetails);
            }
        });

        visitors.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i =0; i < visitTable.getItems().size(); i++) {
                    visitTable.getItems().clear();
                }
                parent.setCenter(visits);
                try {
                    PreparedStatement getVisits = conn.prepareStatement("select * from VISITORS");
                    ResultSet visits = getVisits.executeQuery();
                    while(visits.next()) {
                        String name = visits.getString(1);
                        String pId = visits.getString(2);
                        String date = visits.getString(3);
                        visitData.addAll(new Visitors(name, pId, date));
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });

        profCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });

        ncancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });
        appCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setCenter(defaultLook);
            }
        });
    }
    public class ApptView {
        public String getAppRefID() {
            return appRefID.get();
        }

        public SimpleStringProperty appRefIDProperty() {
            return appRefID;
        }

        public void setAppRefID(String appRefID) {
            this.appRefID.set(appRefID);
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

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getpID() {
            return pID.get();
        }

        public SimpleStringProperty pIDProperty() {
            return pID;
        }

        public void setpID(String pID) {
            this.pID.set(pID);
        }

        private final SimpleStringProperty appRefID;
        private final SimpleStringProperty empID;
        private final SimpleStringProperty date;
        private final SimpleStringProperty pID;

        ApptView(String appRefID, String empID, String date, String pID){
            this.appRefID = new SimpleStringProperty(appRefID);
            this.empID = new SimpleStringProperty(empID);
            this.date = new SimpleStringProperty(date);
            this.pID = new SimpleStringProperty(pID);
        }
    }
    public class Visitors {
        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getpID() {
            return pID.get();
        }

        public SimpleStringProperty pIDProperty() {
            return pID;
        }

        public void setpID(String pID) {
            this.pID.set(pID);
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

        private final SimpleStringProperty name;
        private final SimpleStringProperty pID;
        private final SimpleStringProperty date;
        Visitors(String name, String pID, String date){
            this.name = new SimpleStringProperty(name);
            this.pID = new SimpleStringProperty(pID);
            this.date = new SimpleStringProperty(date);
        }
    }
}
