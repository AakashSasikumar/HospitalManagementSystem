import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GUI extends Application {

    @Override
    public void init(){
        //declare the oracle db connection here
        //img.setText("Loller");
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
       VBox parent = new VBox();
       parent.setSpacing(20);
       Image hospLogo = new Image(new FileInputStream("D:\\Code\\Java\\IdeaProjects\\HospitalManagementSystem\\src\\HospLogo.png"));
       ImageView imageHolder = new ImageView(hospLogo);
       imageHolder.setFitWidth(80);
       imageHolder.setFitHeight(120);
       HBox img = new HBox();
       img.setAlignment(Pos.CENTER);
       img.getChildren().add(imageHolder);
       VBox unameArea = new VBox();
       Label username = new Label("ID");
       TextField inpUsername = new TextField();
       inpUsername.setPromptText("Enter your ID");

       unameArea.getChildren().addAll(username, inpUsername);

       VBox pwdArea = new VBox();

       TextField textField = new TextField();
       textField.setPromptText("Enter password");
       PasswordField inpPassword = new PasswordField();
       inpPassword.setPromptText("Enter password");
       textField.setManaged(false);
       textField.setVisible(false);


       Label password = new Label("Password");
       Button login = new Button("Login");

       CheckBox button = new CheckBox("Show Password");

       textField.managedProperty().bind(button.selectedProperty());
       textField.visibleProperty().bind(button.selectedProperty());

       inpPassword.managedProperty().bind(button.selectedProperty().not());
       inpPassword.visibleProperty().bind(button.selectedProperty().not());

       textField.textProperty().bindBidirectional(inpPassword.textProperty());


       VBox hideArea = new VBox();
       VBox incCor = new VBox();
       Label wrongPassword = new Label();
       wrongPassword.setVisible(true);

       incCor.getChildren().add(wrongPassword);
       incCor.setAlignment(Pos.CENTER_RIGHT);
       hideArea.setAlignment(Pos.CENTER_LEFT);
       hideArea.getChildren().addAll(button, incCor);

       pwdArea.getChildren().addAll(password, textField, inpPassword);
       unameArea.setSpacing(5);
       pwdArea.setSpacing(5);
       hideArea.setPadding(new Insets(0, 30, 0, 30));
       unameArea.setPadding(new Insets(0, 30, 0, 30));
       pwdArea.setPadding(new Insets(0, 30, 0, 30));

       HBox loginNode = new HBox();
       loginNode.setAlignment(Pos.CENTER);
       loginNode.getChildren().add(login);
       //login.setAlignment(Pos.CENTER);



       parent.getChildren().addAll(img, unameArea, pwdArea, hideArea, loginNode);
       Scene scene = new Scene(parent, 400, 400);
       primaryStage.setScene(scene);
       primaryStage.setTitle("Login");
       primaryStage.show();

       login.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               String username = inpUsername.getText();
               String password = inpPassword.getText();
               //System.out.println(username);
               //System.out.println(password);
               //System.out.println("hello");
               if (username.length() != 7 && !username.startsWith("EMP")) {
                   System.out.println("Username or Password incorrect");
                   wrongPassword.setText("Username or Password Incorrect");
                   wrongPassword.setTextFill(Color.RED);
               }
               else {
                   try {
                       //System.out.println("lel");

                       Main.loginVerification.setString(1, username);
                       ResultSet rs = Main.loginVerification.executeQuery();
                       //System.out.println(rs.getFetchSize());
                       int pwd = rs.findColumn("emp_pw");
                       //System.out.println(pwd);
                       //System.out.println(pwd);
                       while(rs.next()) {
                           //System.out.println("leller");

                           String pass = rs.getString(pwd);
                           //System.out.println(pass);
                           //System.out.println(pass);
                           if (password.equals(pass)) {
                               //System.out.println("successfully logged in");
                               wrongPassword.setText("Successfully Logged in");
                               Main.EMP_ID = username;
                               wrongPassword.setTextFill(Color.GREEN);
                               primaryStage.close();
                               Platform.runLater(new Runnable() {
                                   public void run() {
                                       try {
                                           new MainPage().start(new Stage());
                                       } catch (Exception e) {
                                           e.printStackTrace();
                                       }
                                   }
                               });


                           }
                           else {
                               //System.out.println("username or password wrong");
                               wrongPassword.setText("Username or Password Incorrect");
                               wrongPassword.setTextFill(Color.RED);
                           }
                       }
                   }
                   catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
        parent.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER) {
                login.fire();
                event.consume();
            }
        });

    }
    //we don't need this function for now
    /*@Override
    public void stop(){

    }*/
}
