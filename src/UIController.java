import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class UIController {

    @FXML
    public static Label imageHolder;
    @FXML
    public static VBox box;

    public UIController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(imageHolder.getText());
    }


}
