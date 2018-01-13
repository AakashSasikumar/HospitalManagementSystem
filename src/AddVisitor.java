import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddVisitor extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");

        BorderPane addVisitors = new BorderPane();

        TextField visit = new TextField();
        visit.setMinSize(70, 50);
        visit.setPromptText("Visitor Name");
        TextField PID = new TextField();
        PID.setMinSize(70, 50);
        PID.setPromptText("P ID");
        TextField date = new TextField();
        date.setMinSize(70, 50);
        date.setPromptText("yyyy-mm-dd");
        HBox a = new HBox();
        a.getChildren().addAll(visit, PID, date);

        HBox bottomHolder = new HBox();
        Button add = new Button("Add");
        add.setMinSize(70, 50);
        add.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHolder.getChildren().add(add);
        bottomHolder.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHolder.setPadding(new Insets(0, 10, 10, 0));
        addVisitors.setBottom(bottomHolder);
        addVisitors.setCenter(a);

        Scene scene = new Scene(addVisitors, 550, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PreparedStatement stmnt = conn.prepareStatement("insert into VISITORS values (?, ?, ?)");
                    stmnt.setString(1, visit.getText());
                    stmnt.setString(2, PID.getText());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date parsed = null;
                    try {
                        parsed = format.parse(date.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date sql = new Date(parsed.getTime());
                    stmnt.setDate(3, sql);
                    stmnt.executeQuery();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

