import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");

        BorderPane parent = new BorderPane();
        Scene scene = new Scene(parent, 250, 50);
        HBox holder = new HBox();
        TextField ref = new TextField();
        ref.setMinSize(70, 50);
        ref.setPromptText("App. Ref. ID");
        Button del = new Button("Delete");
        del.setMinSize(70, 50);
        holder.getChildren().addAll(ref, del);
        parent.setCenter(holder);
        primaryStage.setScene(scene);
        primaryStage.show();
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PreparedStatement stmnt = null;
                try {
                    stmnt = conn.prepareStatement("delete from APPOINTMENT where APPREF_ID = ?");
                    stmnt.setString(1, ref.getText());
                    stmnt.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
