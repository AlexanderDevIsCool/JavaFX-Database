package kursoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class FXMLKursovaLController implements Initializable {

    private final SQLConnect sqlcon = new SQLConnect();
    private final String selectEmployee = "SELECT * FROM employee";
    private final String selectGoods = "SELECT * FROM goods";
    private final String selectProduct = "SELECT * FROM product";
    private final HashMap<Integer, Integer> countItems = new HashMap();
    private final HashMap<Integer, Integer> sellItems = new HashMap();
    private Integer[] selected = new Integer[100];
    private boolean opeationEmplSwitch = false, windowSwitch = true, updateEmployee = true;
    private Integer selectedIndex = 0, selectedId = 0, i_count = -1;
    private Double price = 0.0;
    private double Rightprefwidth = 0.0d;
    private ResultSet rs = null;
    private ObservableList<EmployeeData> employeeList;
    private ObservableList<EmployeeData> goodsList;
    private ObservableList<String> dayList, monthList, yearList;
    private final FileChooser fileChooser = new FileChooser();
    private final FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    private final FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    private final FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
    private final Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    private final Alert alertEror = new Alert(AlertType.ERROR);
    private final Alert alertInfo = new Alert(AlertType.INFORMATION);
    private final Alert alertSearch = new Alert(AlertType.CONFIRMATION);
    private final TableColumn<EmployeeData, ?> columnPosition = new TableColumn("Посада");
    private final TableColumn<EmployeeData, ?> columnId = new TableColumn("ID");
    private final TableColumn<EmployeeData, ?> columnName = new TableColumn("Ім'я");
    private final TableColumn<EmployeeData, ?> columnSurName = new TableColumn("Прізвище");
    private final TableColumn<EmployeeData, ?> columnAge = new TableColumn("Дата народження");
    private final TableColumn<EmployeeData, ?> columnAddress = new TableColumn("Адрес");
    private final TableColumn<EmployeeData, ?> columnTelefon = new TableColumn("Телефон");
    private final TableColumn<EmployeeData, ?> columnSalary = new TableColumn("Зарплата");
    private final TableColumn<EmployeeData, ?> columnAnimalId = new TableColumn("ID");
    private final TableColumn<EmployeeData, ?> columnAnimalType = new TableColumn("Тип");
    private final TableColumn<EmployeeData, ?> columnAnimalName = new TableColumn("Назва");
    private final TableColumn<EmployeeData, ?> columnAnimalPrice = new TableColumn("Ціна");
    private final TableColumn<EmployeeData, ?> columnAnimalCountry = new TableColumn("Країна");
    private final TableColumn<EmployeeData, ?> columnAnimalAvgLife = new TableColumn("Сер. життя");
    private final TableColumn<EmployeeData, ?> columnAnimalEat = new TableColumn("Їда");
    private final TableColumn<EmployeeData, ?> columnAnimalProvider = new TableColumn("Постачальник");
    private final TableColumn<EmployeeData, ?> columnAnimalAge = new TableColumn("Вік");
    private final TableColumn<EmployeeData, ?> columnAnimalCount = new TableColumn("Кількість");

    @FXML
    private TableView<EmployeeData> TableData;

    // Insert Employee Nodes
    @FXML
    private AnchorPane EmplInsertPane;
    @FXML
    private TextField EmplName, EmplSurName, EmplAddress, EmplTelefon,
            EmplPosition, EmplSalary, EmplFoto;
    @FXML
    private ComboBox<String> EmplDay, EmplMonth, EmplYear;
    @FXML
    private ImageView EmplImage;

    // Insert Goods Nodes  
    @FXML
    private AnchorPane AnmlInsertPane;
    @FXML
    private TextField AnmlType, AnmlName, AnmlPrice, AnmlCountry,
            AnmlAvgLife, AnmlProvider, AnmlAge, AnmlEat, AnmlFoto, AnmlCount;

    @FXML
    private ImageView AnmlImage;
    @FXML
    private Accordion SellAcordion;
    @FXML
    private Label PriceShow;
    @FXML
    private VBox SellBox;
    @FXML
    private BorderPane MainBorderPane;
    @FXML
    private MenuItem SellMenuItem, searchGoods, searchEmployee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (windowSwitch) {
            employeeList = FXCollections.observableArrayList();
            goodsList = FXCollections.observableArrayList();
            dayList = FXCollections.observableArrayList();
            monthList = FXCollections.observableArrayList();
            yearList = FXCollections.observableArrayList();
            Rightprefwidth = SellBox.getPrefWidth();
            MainBorderPane.setRight(null);
            Platform.runLater(() -> {
                setCellEmployee();
                createEmployeeTable();
                Stage st = (Stage)MainBorderPane.getScene().getWindow();
                st.setTitle("Управління базою даних зоомагазину");
            });
        }
        windowSwitch = false;

    }

    // Methods for pull data from table EMPLOYEE and show in tableview 
    private void createEmployeeTable() {
        try {
            goodsList.clear();
            employeeList.clear();
            rs = sqlcon.getResultSet(selectEmployee);
            while (rs.next()) {
                employeeList.add(new EmployeeData("" + rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(9),
                        rs.getString(4), rs.getString(5), "" + rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex) {
            System.out.println("Initialize sqlException");
        }
        SellMenuItem.setVisible(false);
        searchGoods.setVisible(false);
        searchEmployee.setVisible(true);
        TableData.setItems(employeeList);
    }

    private void setCellEmployee() {
        TableData.getColumns().clear();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSurName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableData.getColumns().addAll(columnId, columnName, columnSurName, columnAge, columnAddress, columnTelefon, columnSalary, columnPosition);
    }

    // Methods for pull data from table EMPLOYEE and show in tableview 
    private void createGoodsTable() {
        try {
            employeeList.clear();
            goodsList.clear();
            rs = sqlcon.getResultSet(selectGoods);
            int i = 0;
            while (rs.next()) {
                countItems.put(rs.getInt("AnimalID"), rs.getInt("AnimalCount"));
                goodsList.add(new EmployeeData("" + rs.getInt(1), rs.getString(2), rs.getString(3), "" + rs.getInt(4),
                        rs.getString(5), "" + rs.getInt(6), "" + rs.getString(7), rs.getString(8), "" + rs.getFloat(9), rs.getString(10),
                        "" + rs.getInt("AnimalCount")));
            }
        } catch (SQLException ex) {
            System.out.println("Initialize sqlException");
        }
        SellMenuItem.setVisible(true);
        searchGoods.setVisible(true);
        searchEmployee.setVisible(false);
        TableData.setItems(goodsList);
    }

    private void setCellGoods() {
        TableData.getColumns().clear();
        columnAnimalId.setCellValueFactory(new PropertyValueFactory<>("animalID"));
        columnAnimalType.setCellValueFactory(new PropertyValueFactory<>("animalType"));
        columnAnimalName.setCellValueFactory(new PropertyValueFactory<>("animalName"));
        columnAnimalPrice.setCellValueFactory(new PropertyValueFactory<>("animalPrice"));
        columnAnimalCountry.setCellValueFactory(new PropertyValueFactory<>("animalCountry"));
        columnAnimalAvgLife.setCellValueFactory(new PropertyValueFactory<>("animalAvgLife"));
        columnAnimalEat.setCellValueFactory(new PropertyValueFactory<>("animalEat"));
        columnAnimalProvider.setCellValueFactory(new PropertyValueFactory<>("animalProvider"));
        columnAnimalAge.setCellValueFactory(new PropertyValueFactory<>("animalAge"));
        columnAnimalCount.setCellValueFactory(new PropertyValueFactory<>("animalCount"));

        TableData.getColumns().addAll(columnAnimalId, columnAnimalType, columnAnimalName, columnAnimalPrice,
                columnAnimalCountry, columnAnimalAvgLife, columnAnimalEat, columnAnimalProvider, columnAnimalAge, columnAnimalCount);
    }

    // Actions for menu item Дія
    @FXML
    public void menuActionExit(ActionEvent ex) {
        alertConfirm.setTitle("Вихід з програми");
        alertConfirm.setContentText("Так / Ні");
        alertConfirm.setHeaderText("Ви хочите вийти з програми ?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            System.exit(0);
        } else {
            alertConfirm.close();
        }

    }

    // Actions for menu item Операції
    @FXML
    public void menuOperationInsert(ActionEvent ex) {
        showInsertWindow();
    }

    @FXML
    public void menuOperationUpdate(ActionEvent event) {
        try {
            if (!employeeList.isEmpty()) {
                rs = sqlcon.getResultSet("Select * from employee where EmployeeID = " + selectedId + " ");
                showUpdateWindow();
                Platform.runLater(() -> {
                    try {
                        while (rs.next()) {
                            EmplAddress.setText(rs.getString("EmployeeAddress"));
                            EmplFoto.setText(rs.getString("EmployeeFoto"));
                            BufferedImage img = ImageIO.read(new File(rs.getString("EmployeeFoto")));
                            Image image = SwingFXUtils.toFXImage(img, null);
                            EmplImage.setImage(image);
                            EmplName.setText(rs.getString("EmployeeName"));
                            EmplPosition.setText(rs.getString("EmployeePosition"));
                            EmplSalary.setText("" + rs.getInt("EmployeeSalary"));
                            EmplSurName.setText(rs.getString("EmployeeSurName"));
                            EmplTelefon.setText(rs.getString("EmployeeTelefon"));
                            String[] data = rs.getString("EmployeeAge").split("-");
                            EmplDay.setValue(data[0]);
                            EmplMonth.setValue(data[1]);
                            EmplYear.setValue(data[2]);
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQLException open menu update Employee");
                    } catch (IOException ex) {
                        System.out.println("IOException open menu update Employee");
                    }
                });
            } else if (!goodsList.isEmpty()) {
                rs = sqlcon.getResultSet("Select * from goods where AnimalID = " + selectedId + " ");
                showUpdateWindow();
                Platform.runLater(() -> {
                    try {
                        while (rs.next()) {
                            AnmlType.setText(rs.getString("AnimalType"));
                            AnmlName.setText(rs.getString("AnimalName"));
                            AnmlPrice.setText("" + rs.getInt("AnimalPrice"));
                            AnmlCountry.setText(rs.getString("AnimalCountry"));
                            AnmlAvgLife.setText("" + rs.getInt("AnimalAvgLife"));
                            AnmlProvider.setText(rs.getString("AnimalProvider"));
                            AnmlAge.setText("" + rs.getFloat("AnimalAge"));
                            AnmlEat.setText(rs.getString("AnimalEat"));
                            BufferedImage img = ImageIO.read(new File(rs.getString("AnimalFoto")));
                            Image image = SwingFXUtils.toFXImage(img, null);
                            AnmlFoto.setText(rs.getString("AnimalFoto"));
                            AnmlCount.setText(rs.getString("AnimalCount"));
                            AnmlImage.setImage(image);
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQLException open menu update Animals");
                    } catch (IOException ex) {
                        System.out.println("IOException open menu update Animals");
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println("SQLException open menu update Employee");
        }

    }

    private void showInsertWindow() {
        opeationEmplSwitch = true;
        if (updateEmployee) {
            System.out.println("FFFF");
            showOperationWindow("Вікно добавлення працівника");
            SellMenuItem.setVisible(false);
        } else {
            System.out.println("FFFFAAA");
            showOperationWindow("Вікно добавлення тварини");
            SellMenuItem.setVisible(true);
        }
    }

    private void showUpdateWindow() {
        if(goodsList.isEmpty()) {
        showOperationWindow("Вікно редагування даних працівника");
        SellMenuItem.setVisible(false);
        } else {
            showOperationWindow("Вікно редагування даних тварини");
            SellMenuItem.setVisible(true);
        }
        opeationEmplSwitch = false;

    }

    private void showOperationWindow(String title) {
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(
                        FXMLKursovaLController.this.getClass().getResource("FXMLOperation.fxml"));
                fxmlLoader.setController(FXMLKursovaLController.this);
                Parent root = fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.setTitle(title);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UTILITY);
                stage.initOwner(
                        (TableData.getScene().getWindow()));
                stage.show();
                if (updateEmployee) {
                    AnmlInsertPane.setVisible(false);
                    EmplInsertPane.setVisible(true);
                    loadBirthdayBoxes();
                } else {
                    EmplInsertPane.setVisible(false);
                    AnmlInsertPane.setVisible(true);
                }
                stage.setOnCloseRequest(event -> {
                    Platform.runLater(() -> {
                        if (updateEmployee) {
                            setCellEmployee();
                            createEmployeeTable();
                        } else {
                            setCellGoods();
                            createGoodsTable();
                        }
                    });
                });
            } catch (IOException ex) {
                System.out.println("Error create show insert window");
            }
        });
    }

    private void loadBirthdayBoxes() {
        if (!employeeList.isEmpty()) {
            for (int i = 1; i <= 31; i++) {
                dayList.add("" + i);
            }
            for (int i = 1; i <= 12; i++) {
                monthList.add("" + i);
            }
            for (int i = 1900; i <= 2050; i++) {
                yearList.add("" + i);
            }

            EmplDay.setItems(dayList);
            EmplMonth.setItems(monthList);
            EmplYear.setItems(yearList);
        }
    }

    // Actions for Operations Insert table Employee
    @FXML
    public void showImageDialogEmplInsert(ActionEvent event) {
        fileChooser.setTitle("Виберіть катринку");
        File emplPhoto = fileChooser.showOpenDialog(EmplName.getScene().getWindow());
        if (emplPhoto != null) {
            try {
                EmplFoto.setText(emplPhoto.getAbsolutePath());
                BufferedImage buff = ImageIO.read(emplPhoto);
                Image photo = SwingFXUtils.toFXImage(buff, null);
                EmplImage.setImage(photo);
            } catch (IOException ex) {
                alertEror.setTitle("Помилка добавлення картинки");
                alertEror.setHeaderText("Попробуйте добавити картинку щераз,"
                        + "або використайте інший шлях");
                alertEror.show();
            }
        }
    }

    @FXML
    public void insertEmpl(ActionEvent event) {
        try {
            boolean result;
            System.out.println("IIIIIIIII");
            if (opeationEmplSwitch) {
                System.out.println("IIIIIIIII");
                String path = EmplFoto.getText().replace("\\", "/");
                result = sqlcon.insertEmployee(EmplName.getText(), EmplSurName.getText(),
                        EmplDay.getValue() + "-" + EmplMonth.getValue() + "-" + EmplYear.getValue(),
                        EmplAddress.getText(), EmplTelefon.getText(), Integer.parseInt(EmplSalary.getText()),
                        EmplPosition.getText(), path);
            } else {
                String path = EmplFoto.getText().replace("\\", "/");
                result = sqlcon.updateEmployee(selectedId, EmplName.getText(), EmplSurName.getText(),
                        EmplDay.getValue() + "-" + EmplMonth.getValue() + "-" + EmplYear.getValue(),
                        EmplAddress.getText(), EmplTelefon.getText(), Integer.parseInt(EmplSalary.getText()),
                        EmplPosition.getText(), path);
            }
            if (!result) {
                alertInfo.setTitle("Інформація добавлення запису");
                alertInfo.setHeaderText("Інформація добавлена успішно!");
                alertInfo.show();
            } else {
                alertEror.setTitle("Помилка добавлення інформації");
                alertEror.setHeaderText("Помилка добавлення працівника, "
                        + "перевірте пральність введення інформації");
                alertEror.show();
            }
        } catch (SQLException ex) {

        }
    }

    @FXML
    public void menuOperationDelete(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                alertConfirm.setTitle("Підтвердження видалення");
                alertConfirm.setContentText("Так / Ні ");
                alertConfirm.setHeaderText("Ви хочите видалити данні про: " + (employeeList.isEmpty() ? goodsList.get(selectedIndex).getAnimalName()
                        : employeeList.get(selectedIndex).getName())
                        + " " + (employeeList.isEmpty() ? goodsList.get(selectedIndex).getAnimalType()
                        : employeeList.get(selectedIndex).getSurname()));
                Optional<ButtonType> choise = alertConfirm.showAndWait();
                if (choise.get().equals(ButtonType.OK)) {
                    boolean result = (employeeList.isEmpty() ? sqlcon.deleteRecord("goods", selectedId) : sqlcon.deleteRecord("employee", selectedId));
                    if (!result) {
                        alertInfo.setTitle("Видалення інформації");
                        alertInfo.setHeaderText("Інформацію видалено успішно!");
                        alertInfo.show();
                        if (updateEmployee) {
                            setCellEmployee();
                            createEmployeeTable();
                        } else {
                            setCellGoods();
                            createGoodsTable();
                        }
                    } else {
                        alertEror.setTitle("Помилка видалення інформації");
                        alertEror.setHeaderText("Помилка видалення інформації");
                        alertEror.show();
                    }
                } else {
                    alertConfirm.close();
                }
            } catch (SQLException e) {
                System.out.println("employee delete sqlex");
            }
        });
    }

    private void clearInsertNodes() {
        EmplAddress.setText("");
        EmplFoto.setText("");
        EmplImage.setImage(null);
        EmplName.setText("");
        EmplPosition.setText("");
        EmplSalary.setText("");
        EmplSurName.setText("");
        EmplTelefon.setText("");
    }

    @FXML
    public void tableDataMouseClick(MouseEvent event) {
        selectedIndex = TableData.getSelectionModel().getSelectedIndex();
        if (!TableData.getSelectionModel().isEmpty() && !employeeList.isEmpty()) {
            selectedId = Integer.parseInt(TableData.getSelectionModel().getSelectedItem().getId());
        } else if (!TableData.getSelectionModel().isEmpty() && !goodsList.isEmpty()) {
            selectedId = Integer.parseInt(TableData.getSelectionModel().getSelectedItem().getAnimalID());
        }
    }

    // events for menu tables
    @FXML
    public void menuTablesAnimals(ActionEvent event) {
        Platform.runLater(() -> {
            updateEmployee = false;
            setCellGoods();
            createGoodsTable();
        });
    }

    @FXML
    public void menuTablesEmployee(ActionEvent event) {
        Platform.runLater(() -> {
            updateEmployee = true;
            setCellEmployee();
            createEmployeeTable();
        });
    }

    @FXML
    public void insertAnml(ActionEvent event) {
        try {
            boolean result;

            if (opeationEmplSwitch) {
                String path = AnmlFoto.getText().replace("\\", "/");
                result = sqlcon.insertGoods(AnmlType.getText(), AnmlName.getText(),
                        Integer.parseInt(AnmlPrice.getText()), AnmlCountry.getText(),
                        Integer.parseInt(AnmlAvgLife.getText()), AnmlEat.getText(),
                        AnmlProvider.getText(), Float.parseFloat(AnmlAge.getText()),
                        path, Integer.parseInt(AnmlCount.getText()));
            } else {
                String path = AnmlFoto.getText().replace("\\", "/");
                result = sqlcon.updateGoods(selectedId, AnmlType.getText(), AnmlName.getText(),
                        Integer.parseInt(AnmlPrice.getText()), AnmlCountry.getText(),
                        Integer.parseInt(AnmlAvgLife.getText()), AnmlEat.getText(),
                        AnmlProvider.getText(), Float.parseFloat(AnmlAge.getText()),
                        path, AnmlCount.getText());
            }
            if (!result) {
                alertInfo.setTitle("Інформація добавлення запису");
                alertInfo.setHeaderText("Інформація добавлена успішно!");
                alertInfo.show();
            } else {
                alertEror.setTitle("Помилка добавлення інформації");
                alertEror.setHeaderText("Помилка добавлення тварини, "
                        + "перевірте пральність введення інформації");
                alertEror.show();
            }
        } catch (SQLException ex) {
            System.out.println("DAAAAA");
        }
    }

    @FXML
    public void showImageDialogAnmlInsert(ActionEvent event) {
        fileChooser.setTitle("Виберіть катринку");
        File emplPhoto = fileChooser.showOpenDialog(AnmlName.getScene().getWindow());
        if (emplPhoto != null) {
            try {
                AnmlFoto.setText(emplPhoto.getAbsolutePath());
                BufferedImage buff = ImageIO.read(emplPhoto);
                Image photo = SwingFXUtils.toFXImage(buff, null);
                AnmlImage.setImage(photo);
            } catch (IOException ex) {
                alertEror.setTitle("Помилка добавлення картинки");
                alertEror.setHeaderText("Попробуйте добавити картинку щераз,"
                        + "або використайте інший шлях");
                alertEror.show();
            }
        }
    }

    @FXML
    public void menuOperationSell(ActionEvent event) {
        if (TableData.getSelectionModel().isEmpty()) {
            alertEror.setContentText("Виберіть товар");
            alertEror.setHeaderText("Ви не вибрали товар");
            alertEror.setTitle("Помилка");
            alertEror.show();
            return;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SellAcordion.getPanes().isEmpty()) {
                    Timeline timeline = new Timeline();
                    if (MainBorderPane.getRight() == null) {
                        MainBorderPane.setRight(SellBox);
                        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(SellBox.prefWidthProperty(), 0)),
                                new KeyFrame(Duration.millis(350), new KeyValue(SellBox.prefWidthProperty(), Rightprefwidth)));
                        timeline.play();
                    } else {
                        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(SellBox.prefWidthProperty(), Rightprefwidth)),
                                new KeyFrame(Duration.millis(350), new KeyValue(SellBox.prefWidthProperty(), 0)));
                        timeline.play();
                        timeline.setOnFinished(event1 -> MainBorderPane.setRight(null));
                    }
                }
                try {
                    TitledPane tp = new TitledPane();
                    VBox itemsBox = new VBox();
                    BufferedImage buffimage = ImageIO.read(new File(goodsList.get(selectedIndex).getAnimalPhoto()));
                    Image image = SwingFXUtils.toFXImage(buffimage, null);
                    ImageView foto = new ImageView();
                    Label country = new Label();
                    country.setText("Країна: " + goodsList.get(selectedIndex).getAnimalCountry());
                    Label avgLife = new Label();
                    avgLife.setText("Сер. життя: " + goodsList.get(selectedIndex).getAnimalAvgLife());
                    Label age = new Label();
                    age.setText("Вік: " + goodsList.get(selectedIndex).getAnimalAge());
                    Label provider = new Label();
                    provider.setText("Постачальник: " + goodsList.get(selectedIndex).getAnimalProvider());
                    foto.setFitWidth(272);
                    foto.setFitHeight(272);
                    foto.setImage(image);
                    itemsBox.getChildren().addAll(foto, country, avgLife, age, provider);
                    tp.setContent(itemsBox);
                    tp.setText(goodsList.get(selectedIndex).getAnimalType() + " - "
                            + goodsList.get(selectedIndex).getAnimalName() + ", Ціна: " + goodsList.get(selectedIndex).getAnimalPrice());
                    price += Integer.parseInt(goodsList.get(selectedIndex).getAnimalPrice());
                    PriceShow.setText("Ціна: " + price);
                    SellAcordion.getPanes().add(tp);
                    selected[++i_count]
                            = Integer.parseInt(goodsList.get(selectedIndex).getAnimalID());
                } catch (IOException ex) {
                    System.out.println("tp exxxxxxxxxxxxxxxxx");
                }
            }
        });
    }

    @FXML
    public void canselSell(ActionEvent event) {
        SellAcordion.getPanes().clear();
        PriceShow.setText("");
        price = 0.0d;
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(SellBox.prefWidthProperty(), Rightprefwidth)),
                new KeyFrame(Duration.millis(350), new KeyValue(SellBox.prefWidthProperty(), 0)));
        timeline.play();
        timeline.setOnFinished(event1 -> MainBorderPane.setRight(null));
    }

    public void actionSell(ActionEvent event) {

        for (int i = 0; i < SellAcordion.getPanes().size(); i++) {
            int c = 0;
            for (int b = 0; b < SellAcordion.getPanes().size(); b++) {
                if (selected[i] != null && selected[i].equals(selected[b])) {
                    c += 1;
                }
            }
            sellItems.put(selected[i], c);
        }
        boolean isGood = true;
        for (Map.Entry<Integer, Integer> m : sellItems.entrySet()) {
            if (countItems.get(m.getKey()) < m.getValue()) {
                isGood = false;
                break;
            }
            isGood = true;
        }

        if (isGood) {
            try {
                for (Map.Entry<Integer, Integer> s : sellItems.entrySet()) {
                    for (Map.Entry<Integer, Integer> m : countItems.entrySet()) {
                        if (m.getKey().equals(s.getKey())) {
                            sqlcon.commandMe("UPDATE goods SET AnimalCount = " + (countItems.get(m.getKey()) - sellItems.get(m.getKey()))
                                    + " WHERE AnimalID = " + m.getKey() + " ");
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Sell goods ex");
            }
            createGoodsTable();
            canselSell(event);
        } else {
            alertEror.setContentText("Ви ввели товар перевишаючий його кількість");
            alertEror.show();
        }
    }

    @FXML
    public void removeSellPane(ActionEvent event) {
        if (SellAcordion.getExpandedPane() == null) {
            alertEror.setContentText("Виберіть товар");
            alertEror.setHeaderText("Ви не вибрали товар");
            alertEror.setTitle("Помилка");
            alertEror.show();
            return;
        }
        SellAcordion.getPanes().remove(SellAcordion.getExpandedPane());
        ObservableList<TitledPane> tip = SellAcordion.getPanes();
        int i = 0;
        for (TitledPane t : tip) {
            i++;
            if (t.equals(SellAcordion.getExpandedPane())) {
                break;
            }
        }
        Double price1 = 0.0d;
        System.out.println(PriceShow.getText().substring(6));
        try {
            rs = sqlcon.getResultSet("Select AnimalPrice FROM goods WHERE AnimalID = " + selected[i] + " ");
            while (rs.next()) {
                System.out.println("AAAAAAAAAAAAAAAAAAAA"+rs.getInt("AnimalPrice"));
                price1 = (Double.parseDouble(PriceShow.getText().substring(6)) - rs.getInt("AnimalPrice"));
                price = price1;
            }

        } catch (SQLException ex) {
            System.out.println("sqlex");
        }
        System.out.println("PRICE: "+price1);
        PriceShow.setText("Ціна: " + price1);
        System.out.println("i: " + i);
        selected[i] = null;
        setCellGoods();
        createGoodsTable();
        if (SellAcordion.getPanes().isEmpty()) {
            PriceShow.setText("");
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(SellBox.prefWidthProperty(), Rightprefwidth)),
                    new KeyFrame(Duration.millis(350), new KeyValue(SellBox.prefWidthProperty(), 0)));
            timeline.play();
            timeline.setOnFinished(event1 -> MainBorderPane.setRight(null));
        }
    }
    
    @FXML
    public void menuSearch(ActionEvent event) {
       // setCellEmployee();
        TextField search = new TextField();
        alertSearch.setGraphic(search);
        alertSearch.setContentText("Ви шукаєте по Імені, Прізвищу або Даті народження - 00-00-0000");
        alertSearch.setHeaderText("Пошук");
        alertSearch.setTitle("Пошук по таблиці працівники");
        Optional<ButtonType> choose = alertSearch.showAndWait();
        System.out.println("Hello");
        if (choose.get().equals(ButtonType.OK)) {
            try {
                String searchContent = search.getText();
                goodsList.clear();
                employeeList.clear();
                rs = sqlcon.getResultSet("SELECT * FROM employee WHERE EmployeeName = '" + searchContent + "' "
                        + "OR EmployeeSurName = '" + searchContent + "' "
                        + "OR EmployeeAge = '" + searchContent + "' "
                );
                while (rs.next()) {
                    employeeList.add(new EmployeeData("" + rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(9),
                            rs.getString(4), rs.getString(5), "" + rs.getString(6), rs.getString(7), rs.getString(8)));
                }
            } catch (SQLException ex) {
                System.out.println("Initialize sqlException");
            }
            TableData.setItems(employeeList);           
        }
    }  
    
    @FXML
    public void animalSearch(ActionEvent event) {
        TextField search = new TextField();
        alertSearch.setGraphic(search);
        alertSearch.setContentText("Ви шукаєте по Назві та Типу тварини");
        alertSearch.setHeaderText("Пошук");
        alertSearch.setTitle("Пошук по таблиці тварин");
        Optional<ButtonType> choose = alertSearch.showAndWait();
        if (choose.get().equals(ButtonType.OK)) {
            try {
                String searchContent = search.getText();
                goodsList.clear();
                employeeList.clear();
                rs = sqlcon.getResultSet("SELECT * FROM goods WHERE AnimalName = '" + searchContent + "' "
                        + "OR AnimalType = '" + searchContent + "' "
                );
                while (rs.next()) {
                    goodsList.add(new EmployeeData("" + rs.getInt(1), rs.getString(2), rs.getString(3), "" + rs.getInt(4),
                        rs.getString(5), "" + rs.getInt(6), "" + rs.getString(7), rs.getString(8), "" + rs.getFloat(9), rs.getString(10),
                        "" + rs.getInt("AnimalCount")));
                }
            } catch (SQLException ex) {
                System.out.println("Initialize sqlException");
            }
            TableData.setItems(goodsList);
        }
    }
}
