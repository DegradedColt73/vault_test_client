package homescreen;

import createscreen.CreateScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import passwordscreen.PasswordScreen;

import java.io.File;
import java.io.IOException;

public class HomeScreen {

    @FXML
    private AnchorPane anchor;
    private Stage stage;

    public void onOpenVault (ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a vault file");
        this.stage = (Stage)anchor.getScene().getWindow();
        File file = fileChooser.showOpenDialog(this.stage);
        if(file == null) return;
        String url = file.getAbsolutePath();
        this.askForPassword(url);
    }

    public void onCreateVault (ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pick location for new vault");
        this.stage = (Stage)anchor.getScene().getWindow();
        File file = directoryChooser.showDialog(this.stage);
        if(file == null) return;
        String url = file.getAbsolutePath();
        this.createNew(url);
    }

    private void askForPassword(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../passwordscreen/password_screen.fxml"));
        Parent root = fxmlLoader.load();
        PasswordScreen passwordScreen = fxmlLoader.getController();
        passwordScreen.setVaultUrl(url);
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        stage.show();
    }

    private void createNew(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../createscreen/create_screen.fxml"));
        Parent root = fxmlLoader.load();
        CreateScreen createScreen = fxmlLoader.getController();
        createScreen.setVaultUrl(url);
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        stage.show();
    }
}
