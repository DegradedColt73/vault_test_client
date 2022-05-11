package passwordscreen;

import driver.VaultDriver;
import exceptions.EmptyResponseException;
import exceptions.IncorrectPasswordException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vault.VaultDoesNotExistException;
import vaultview.VaultView;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordScreen {
    @FXML
    private Button confirmPassword;
    @FXML
    private Label errorText;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private AnchorPane anchor;
    private String vaultUrl;
    private Stage stage;

    public void goBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../homescreen/home_screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.stage = (Stage)anchor.getScene().getWindow();
        this.stage.setScene(scene);
        stage.show();
    }

    public void checkPassword() throws IOException {
        this.stage = (Stage)anchor.getScene().getWindow();
        this.connectToVaultDriver();
    }

    private void connectToVaultDriver() throws IOException {
        VaultDriver vaultDriver = new VaultDriver();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vaultview/vault_view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.stage = (Stage)anchor.getScene().getWindow();
        try {
            vaultDriver.connectToVault(this.vaultUrl, passwordInput.getText());
            this.stage.setScene(scene);
            stage.show();
            this.errorText.setText("");
            VaultView vaultView = fxmlLoader.getController();
            vaultView.setVaultDriver(vaultDriver);
            vaultView.fillEntityList();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IncorrectPasswordException e) {
            this.errorText.setText("Oops, provided password seems to be wrong");
        } catch (VaultDoesNotExistException e) {
            e.printStackTrace();
        } catch (EmptyResponseException e) {
            e.printStackTrace();
        }
    }

    public String getVaultUrl() {
        return vaultUrl;
    }

    public void setVaultUrl(String vaultUrl) {
        this.vaultUrl = vaultUrl;
    }
}
