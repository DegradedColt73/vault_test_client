package vaultview;

import driver.VaultDriver;
import exceptions.EmptyResponseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vault.Entity;
import vault.EntityField;
import vault.VaultDataFieldType;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


public class VaultView {
    @FXML
    private ListView<ListElement> listView;
    @FXML
    private AnchorPane listPane;
    @FXML
    private AnchorPane sidePane;
    @FXML
    private Button addEntryButton;
    @FXML
    private Button removeEntryButton;
    private VaultDriver vaultDriver;
    private Entity currentEntity;

    public VaultDriver getVaultDriver() {
        return vaultDriver;
    }

    public void setVaultDriver(VaultDriver vaultDriver) {
        this.vaultDriver = vaultDriver;
    }

    private class CustomListCell extends ListCell<ListElement> {
        private HBox content;
        private Text name;

        public CustomListCell() {
            super();
            name = new Text();
            VBox vBox = new VBox(name);
            content = new HBox(new Label(""), vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(ListElement item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                name.setText(item.getName());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    private EventHandler<MouseEvent> listClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                this.outputItem(listView.getSelectionModel().getSelectedItem().getId());
            } catch (EmptyResponseException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }

        private void outputItem(int id) throws EmptyResponseException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, SQLException, IllegalBlockSizeException, InvalidKeyException {
            vaultDriver.loadEntity(id);
            Entity entity = vaultDriver.getCurrentEntity();
            System.out.println(entity.getFields());
            setSideView();
        }
    };
    private EventHandler<MouseEvent> addFieldClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                saveItem(listView.getSelectionModel().getSelectedItem().getId());
            } catch (EmptyResponseException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            TextInputDialog dialog = new TextInputDialog("Enter field name");
            dialog.setHeaderText("Enter field name");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                vaultDriver.getCurrentEntity().getFields().put(result.get(), new EntityField("", VaultDataFieldType.PASSWORD));
                setSideView();
            }
        }
    };
    private EventHandler<MouseEvent> saveEntityClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                saveItem(listView.getSelectionModel().getSelectedItem().getId());
            } catch (EmptyResponseException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    };

    public void fillEntityList() throws SQLException {
        Node[] nodes = new Node[2];
        nodes[0] = listPane.getChildren().get(0);
        nodes[1] = listPane.getChildren().get(1);
        listPane.getChildren().clear();
        listPane.getChildren().add(nodes[0]);
        listPane.getChildren().add(nodes[1]);
        ObservableList<ListElement> data = FXCollections.observableArrayList();
        this.vaultDriver.listEntities().forEach((id, name) -> {
            data.add(new ListElement(id, name.substring(1, name.length() - 1)));
        });
        final ListView<ListElement> listView = new ListView<ListElement>(data);
        this.listView = listView;
        listView.setCellFactory(new Callback<ListView<ListElement>, ListCell<ListElement>>() {
            @Override
            public ListCell<ListElement> call(ListView<ListElement> listView) {
                return new CustomListCell();
            }
        });
        listView.setOnMouseClicked(listClickEvent);
        listPane.getChildren().add(listView);
    }

    public void deleteEntity() throws EmptyResponseException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, SQLException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        this.vaultDriver.deleteEntity(this.listView.getSelectionModel().getSelectedItem().getId());
        this.fillEntityList();
        this.sidePane.getChildren().clear();
    }

    public void addEntity() throws BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, SQLException, NoSuchPaddingException, InvalidKeyException {
        TextInputDialog dialog = new TextInputDialog("Enter Entity name");
        dialog.setHeaderText("Enter entity name");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            EntityField tmpEntityField = new EntityField("", VaultDataFieldType.PASSWORD);
            Map<String, EntityField> map = new LinkedHashMap<String, EntityField>();
            map.put("login", tmpEntityField);
            map.put("password", tmpEntityField);
            vaultDriver.createNewEntity(result.get(), map);
        }
        this.fillEntityList();
    }

    private void setSideView() {
        sidePane.getChildren().clear();
        double y = 20;
        for (Map.Entry<String, EntityField> entry : this.vaultDriver.getCurrentEntity().getFields().entrySet()) {
            String name = entry.getKey();
            EntityField field = entry.getValue();
            Node label = new Label(name);
            label.setLayoutY(y);
            label.setLayoutX(50);
            y += 20;
            Node content = new TextField(field.getFieldContent());
            content.setLayoutY(y);
            content.setLayoutX(50);
            Node button = new Button("-");
            button.setLayoutY(y);
            button.setLayoutX(200);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    vaultDriver.getCurrentEntity().getFields().remove(name);
                    try {
                        vaultDriver.updateEntity(listView.getSelectionModel().getSelectedItem().getId(), listView.getSelectionModel().getSelectedItem().getName(), vaultDriver.getCurrentEntity().getFields());
                        vaultDriver.saveUpdatedEntity();
                        setSideView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (EmptyResponseException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    }
                }
            });
            y += 50;
            sidePane.getChildren().add(label);
            sidePane.getChildren().add(content);
            sidePane.getChildren().add(button);
        }
        y += 50;
        Node addFieldButton = new Button("+");
        addFieldButton.setOnMouseClicked(this.addFieldClickEvent);
        addFieldButton.setLayoutX(10);
        addFieldButton.setLayoutY(y);
        Node saveButton = new Button("Save");
        saveButton.setOnMouseClicked(this.saveEntityClickEvent);
        saveButton.setLayoutX(50);
        saveButton.setLayoutY(y);
        sidePane.getChildren().add(addFieldButton);
        sidePane.getChildren().add(saveButton);
    }

    private void saveItem(int id) throws EmptyResponseException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, SQLException, NoSuchPaddingException, InvalidKeyException {
        Entity entity = new Entity(listView.getSelectionModel().getSelectedItem().getName());
        Map<String, EntityField> fields = new LinkedHashMap<>();
        int size = sidePane.getChildren().size();
        for (int i = 0; i < size - 2; i += 3) {
            Label tmpLabel = (Label) sidePane.getChildren().get(i);
            TextField tmpTextField = (TextField) sidePane.getChildren().get(i + 1);
            fields.put(tmpLabel.getText(), new EntityField(tmpTextField.getText(), VaultDataFieldType.PASSWORD));
        }
        entity.setFields(fields);
        vaultDriver.updateEntity(id, entity.getName(), entity.getFields());
        vaultDriver.saveUpdatedEntity();
    }
}

