
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ramalapure.userinfoapp;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ram Alapure
 */
public class UserInfoApp extends Application {
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    TextField id, fn, ln, em, un, mobile, searchField;
    PasswordField pw;
    DatePicker date;
    final ObservableList options = FXCollections.observableArrayList();
    final ObservableList<User> data = FXCollections.observableArrayList();
    TableView<User> table;
    
    private RadioButton male;
    private RadioButton female;
    private String radioButtonLabel;
    private CheckBox checkBox1, checkBox2, checkBox3;
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();
    
    private FileChooser fileChooser;
    private Button browse;
    private File file;
    private final Desktop desktop = Desktop.getDesktop();
    
    private TextArea textArea;
    private ImageView imageView;
    private Image image;
    
    private FileInputStream fis;
    private Button exportToXL, importXLToDB;
    
    @Override
    public void start(Stage primaryStage) { 
        CheckConnection();
        fillComboBox();
        // create transperant stage
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        primaryStage.setTitle("JavaFX 8 Tutorial 62 - Jasper Report");
        
        primaryStage.getIcons().add(new Image("file:user-icon.png"));
        BorderPane layout = new BorderPane();
        Scene newscene = new Scene(layout, 1200, 700, Color.rgb(0, 0, 0, 0));
        
        Group root = new Group();
        Scene scene = new Scene(root, 320, 200, Color.rgb(0, 0, 0, 0));
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        
        Color foreground = Color.rgb(255, 255, 255, 0.9);
        
        //Rectangila Background
        Rectangle background = new Rectangle(320, 250);
        background.setX(0);
        background.setY(0);
        background.setArcHeight(15);
        background.setArcWidth(15);
        background.setFill(Color.rgb(0 ,0 , 0, 0.55));
        background.setStroke(foreground);
        background.setStrokeWidth(1.5);
        
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10,0,0,10));
        
        Label label = new Label("Label");
        //label.setTextFill(Color.WHITESMOKE);
        label.setFont(new Font("SanSerif", 20));
        
        TextField username = new TextField();
        username.setFont(Font.font("SanSerif", 20));
        username.setPromptText("Username");
        username.getStyleClass().add("field-background");
        
        PasswordField password =new PasswordField();
        password.setFont(Font.font("SanSerif", 20));
        password.setPromptText("Password");
        password.getStyleClass().add("field-background");
        
        password.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
               try{
                String query = "select * from UserDatabase where Username=? and Password=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, username.getText());
                pst.setString(2, password.getText());
                rs = pst.executeQuery();
                
                if(rs.next()){
                    //label.setText("Login Successful");
                    primaryStage.setScene(newscene);
                    primaryStage.show();
                }else{
                    label.setText("Login Failed");
                } 
                username.clear();
                password.clear();
                pst.close();
                rs.close();
            }catch(Exception e1){
                label.setText("SQL Error");
                System.err.println(e1);
            } 
            }
        });        
        
        Button btn = new Button("Login");
        btn.setFont(Font.font("SanSerif", 15));
        btn.setOnAction(e ->{
            try{
                String query = "select * from UserDatabase where Username=? and Password=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, username.getText());
                pst.setString(2, password.getText());
                rs = pst.executeQuery();
                
                if(rs.next()){
                    //label.setText("Login Successful");
                    primaryStage.setScene(newscene);
                    primaryStage.show();
                }else{
                    label.setText("Login Failed");
                } 
                username.clear();
                password.clear();
                pst.close();
                rs.close();
            }catch(Exception e1){
                label.setText("SQL Error");
                System.err.println(e1);
            }
        });
        
        vbox.getChildren().addAll(label, username, password, btn);
        root.getChildren().addAll(background, vbox);
        
        Button logout = new Button("Logout");
        logout.setFont(Font.font("SanSerif", 15));
        logout.setOnAction(e ->{
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        layout.setTop(logout);
        BorderPane.setAlignment(logout, Pos.TOP_RIGHT);
        BorderPane.setMargin(logout, new Insets(10));        
        
        VBox fields = new VBox(5);
        searchField = new TextField();
        searchField.setFont(Font.font("SanSerif", 15));
        searchField.setPromptText("Search");
        searchField.setMaxWidth(200);
        
        Label label1 = new Label("Create New User");
        label1.setFont(new Font("Sanserif", 20));
        
        id = new TextField();
        id.setFont(Font.font("SanSerif", 15));
        id.setPromptText("ID");
        id.setMaxWidth(200);
        
        fn = new TextField();
        fn.setFont(Font.font("SanSerif", 15));
        fn.setPromptText("First Name");
        fn.setMaxWidth(200);
        
        ln = new TextField();
        ln.setFont(Font.font("SanSerif", 15));
        ln.setPromptText("Last Name");
        ln.setMaxWidth(200);
        
        em = new TextField();
        em.setFont(Font.font("SanSerif", 15));
        em.setPromptText("Email");
        em.setMaxWidth(200);
        
        mobile = new TextField();
        mobile.setFont(Font.font("SanSerif", 15));
        mobile.setPromptText("Mobile No.");
        mobile.setMaxWidth(200);
        
        un = new TextField();
        un.setFont(Font.font("SanSerif", 15));
        un.setPromptText("Username");
        un.setMaxWidth(200);
        
        pw = new PasswordField();
        pw.setFont(Font.font("SanSerif", 15));
        pw.setPromptText("Password");
        pw.setMaxWidth(200);
        
        date = new DatePicker();
        date.setPromptText("Date of Birth");
        date.setMaxWidth(200);
        date.setStyle("-fx-font-size:15");
        
        ToggleGroup gender = new ToggleGroup();
        
        male = new RadioButton("Male");
        male.setToggleGroup(gender);
        male.setOnAction(e ->{
            radioButtonLabel = male.getText();
        });
        
        female = new RadioButton("Female");
        female.setToggleGroup(gender);
        female.setOnAction(e ->{
            radioButtonLabel = female.getText();
        });
        
        checkBox1 = new CheckBox("Playing");
        checkBox1.setOnAction(e ->{
            checkBoxList.add(checkBox1.getText());
        });
        checkBox2 = new CheckBox("Singing");
        checkBox2.setOnAction(e ->{
            checkBoxList.add(checkBox2.getText());
        });
        checkBox3 = new CheckBox("Dancing");
        checkBox3.setOnAction(e ->{
            checkBoxList.add(checkBox3.getText());
        });
        
        date.requestFocus();
        male.requestFocus();
        female.requestFocus();
        checkBox1.requestFocus();
        checkBox2.requestFocus();
        checkBox3.requestFocus();
        
        textArea = new TextArea();
        textArea.setFont(Font.font("SanSerif", 12));
        textArea.setPromptText("Path Of Selected File Or Files");
        textArea.setPrefSize(300, 50);
        textArea.setEditable(false);
        
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*txt"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*wav", "*.mp3", "*.aac"),
                new ExtensionFilter("All Files", "*.*")
        );
        
        browse = new Button("Browse");
        browse.setFont(Font.font("SanSerif", 15));
        browse.setOnAction(e ->{
            //Single File Selection
            file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                textArea.setText(file.getAbsolutePath());
                image = new Image(file.toURI().toString(), 100, 150, true, true);//path, PrefWidth, PrefHeight, PreserveRatio, Smooth
                
                imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
                
                layout.setCenter(imageView);
                BorderPane.setAlignment(imageView, Pos.TOP_LEFT);
                
            }
            
            //Multiple File Selection
            /*List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);
            if(fileList != null){
                fileList.stream().forEach(selectedFiles ->{
                    textArea.setText(fileList.toString());
                });
            }*/
        });
        
        Button button = new Button("Save");
        button.setFont(Font.font("SanSerif", 15));
        button.setOnAction(e ->{
            if(validateNumber() & validateFirstName() & validateLastName() & validateEmaill() & validateMobileNo() & validatePassword() & validateFields()){
            try{
                String query = "INSERT INTO UserDatabase (ID, FirstName, LastName, Email, Username, Password, DOB, Gender, MobileNo, Hobbies, Image) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(query);
                
                pst.setString(1, id.getText());
                pst.setString(2, fn.getText());
                pst.setString(3, ln.getText());
                pst.setString(4, em.getText());
                pst.setString(5, un.getText());
                pst.setString(6, pw.getText());
                pst.setString(7, ((TextField)date.getEditor()).getText());
                pst.setString(8, radioButtonLabel);
                pst.setString(9, mobile.getText());
                pst.setString(10, checkBoxList.toString());
                
                fis = new FileInputStream(file);
                pst.setBinaryStream(11, (InputStream)fis, (int)file.length());
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been created.");
                alert.showAndWait();
                
                pst.execute();
                
                pst.close();
                clearFields();
            }catch(SQLException | FileNotFoundException e1){
                label.setText("SQL Error");
                System.err.println(e1);
            }
            fillComboBox();
            refreshTable();
            }
        });
        
        Button update = new Button("Update");
        update.setFont(Font.font("SanSerif", 15));
        update.setOnAction(e ->{
            if(validateNumber() & validateFirstName() & validateLastName() & validateEmaill() & validateMobileNo() & validatePassword() & validateFields()){
            try{
                String query = "update UserDatabase set ID=?, FirstName=?, LastName=?, Email=?, Username=?, Password=?, DOB=?, Gender=?, MobileNo=?, Hobbies=?, Image=? where ID='"+id.getText()+"' ";
                pst = conn.prepareStatement(query);
                
                pst.setString(1, id.getText());
                pst.setString(2, fn.getText());
                pst.setString(3, ln.getText());
                pst.setString(4, em.getText());
                pst.setString(5, un.getText());
                pst.setString(6, pw.getText());
                pst.setString(7, ((TextField)date.getEditor()).getText());
                pst.setString(8, radioButtonLabel);
                pst.setString(9, mobile.getText());
                pst.setString(10, checkBoxList.toString());
                
                fis = new FileInputStream(file);
                pst.setBinaryStream(11, (InputStream)fis, (int)file.length());
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("User details has been updated.");
                alert.showAndWait();
                
                pst.execute();
                
                pst.close();
                clearFields();
            }catch(SQLException | FileNotFoundException e1){
                label.setText("SQL Error");
                System.err.println(e1);
            }
            fillComboBox();
            refreshTable();
            }
        });
        
        fields.getChildren().addAll(searchField, label1, id, fn, ln, em, mobile, un, pw, date, male, female, checkBox1, checkBox2, checkBox3, browse, textArea, button);
        layout.setLeft(fields);
        
        BorderPane.setMargin(fields, new Insets(0, 10, 0, 10));
        
        table = new TableView<>();
        
        TableColumn column1 = new TableColumn("ID");
        column1.setMaxWidth(50);
        column1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        
        TableColumn column2 = new TableColumn("First Name");
        column2.setMinWidth(80);
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn column3 = new TableColumn("Last Name");
        column3.setMinWidth(80);
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn column4 = new TableColumn("Email");
        column4.setMinWidth(150);
        column4.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn column5 = new TableColumn("Username");
        column5.setMinWidth(80);
        column5.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn column6 = new TableColumn("Password");
        column6.setMinWidth(80);
        column6.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        TableColumn column7 = new TableColumn("DOB");
        column7.setMinWidth(70);
        column7.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        
        TableColumn column8 = new TableColumn("Gender");
        column8.setMinWidth(50);
        column8.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        
        TableColumn column9 = new TableColumn("Mobile No.");
        column9.setMinWidth(70);
        column9.setCellValueFactory(new PropertyValueFactory<>("MobileNo"));
        
        TableColumn column10 = new TableColumn("Hobbies");
        column10.setMinWidth(100);
        column10.setCellValueFactory(new PropertyValueFactory<>("Hobbies"));
                
        table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10);
        table.setTableMenuButtonVisible(true);
        
        ScrollPane sp = new ScrollPane(table);
        //sp.setContent(table);
        sp.setPrefSize(600, 200);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(0);
        sp.setDisable(false);
        
        layout.setRight(sp);
        BorderPane.setMargin(sp, new Insets(0, 10, 10, 10));
        
        Button load = new Button("Load Table");
        load.setFont(Font.font("SanSerif", 15));
        load.setOnAction(e ->{
           refreshTable();
        }
        );
        
        ComboBox comboBox = new ComboBox(options);
        comboBox.setMaxHeight(30);
        
        comboBox.setOnAction(e ->{
            
            try {
                String query = "select * from UserDatabase where FirstName = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, (String)comboBox.getSelectionModel().getSelectedItem());
                rs = pst.executeQuery();
                
                while(rs.next()){
                    id.setText(rs.getString("ID"));
                    fn.setText(rs.getString("FirstName"));
                    ln.setText(rs.getString("LastName"));
                    em.setText(rs.getString("Email"));
                    mobile.setText(rs.getString("MobileNo"));
                    un.setText(rs.getString("Username"));
                    pw.setText(rs.getString("Password"));
                    ((TextField)date.getEditor()).setText(rs.getString("DOB"));
                    
                    if("Male".equals(rs.getString("Gender"))){
                        male.setSelected(true);
                    }else if("Female".equals(rs.getString("Gender"))){
                        female.setSelected(true);
                    }else{
                        male.setSelected(false);
                        female.setSelected(false);
                    }
                }
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        Button delete = new Button("Delete User");
        delete.setFont(Font.font("SanSerif", 15));
        delete.setOnAction(e ->{
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete?");
                Optional <ButtonType> action = alert.showAndWait();
                
                if(action.get() == ButtonType.OK){
                    try {
                        String query = "delete from UserDatabase where id = ?";
                        pst = conn.prepareStatement(query);
                        pst.setString(1, id.getText());
                        pst.executeUpdate();

                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            clearFields();
            fillComboBox();
            refreshTable();
            
        });
        
        exportToXL = new Button("Export To Excel");
        exportToXL.setFont(Font.font("Sanserif", 15));
        exportToXL.setOnAction(e->{
            try {
                String query = "Select * from UserDatabase";
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                
                //Apache POI Jar Link-
                //http://a.mbbsindia.com/poi/release/bin/poi-bin-3.13-20150929.zip
                XSSFWorkbook wb = new XSSFWorkbook();//for earlier version use HSSF
                XSSFSheet sheet = wb.createSheet("User Details");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("First Name");
                header.createCell(2).setCellValue("Last Name");
                header.createCell(3).setCellValue("Email");
                
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256*25);//256-character width
                
                sheet.setZoom(150);//scale-150% 
                
                
                int index = 1;
                while(rs.next()){
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString("ID"));
                    row.createCell(1).setCellValue(rs.getString("FirstName"));
                    row.createCell(2).setCellValue(rs.getString("LastName"));
                    row.createCell(3).setCellValue(rs.getString("Email"));
                    index++;                    
                }
                
                FileOutputStream fileOut = new FileOutputStream("UserDetails.xlsx");// befor 2007 version xls
                wb.write(fileOut);
                fileOut.close();
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("User Details Exported in Excel Sheet.");
                alert.showAndWait();
                
                pst.close();
                rs.close();
                
            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        importXLToDB = new Button("Import XL TO DB");
        importXLToDB.setFont(Font.font("Sanserif", 15));
        importXLToDB.setOnAction(e -> {
            try {
                String query = "Insert into UserDatabase(ID, FirstName, LastName, Email) values (?,?,?,?)";
                pst = conn.prepareStatement(query);
                
                FileInputStream fileIn = new FileInputStream(new File("UserInfo.xlsx"));
                
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for(int i=1; i<=sheet.getLastRowNum(); i++){
                    row = sheet.getRow(i);
                    pst.setInt(1, (int) row.getCell(0).getNumericCellValue());
                    pst.setString(2, row.getCell(1).getStringCellValue());
                    pst.setString(3, row.getCell(2).getStringCellValue());
                    pst.setString(4, row.getCell(3).getStringCellValue());
                    pst.execute();
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("User Details Imported From Excel Sheet To Database.");
                alert.showAndWait();
                
                //wb.close();
                fileIn.close();
                pst.close();
                rs.close();
            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            refreshTable();
        });
        
        
        table.setOnMouseClicked(e ->{
            try {
                User user = (User)table.getSelectionModel().getSelectedItem();
                
                String query = "select * from UserDatabase where ID = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, user.getID());
                rs = pst.executeQuery();
                
                while(rs.next()){
                    id.setText(rs.getString("ID"));
                    fn.setText(rs.getString("FirstName"));
                    ln.setText(rs.getString("LastName"));
                    em.setText(rs.getString("Email"));
                    mobile.setText(rs.getString("MobileNo"));
                    un.setText(rs.getString("Username"));
                    pw.setText(rs.getString("Password"));
                    ((TextField)date.getEditor()).setText(rs.getString("DOB"));
                    
                    if(null != rs.getString("Gender"))switch (rs.getString("Gender")) {
                        case "Male":
                            male.setSelected(true);
                            break;
                        case "Female":
                            female.setSelected(true);
                            break;
                        default:
                            male.setSelected(false);
                            female.setSelected(false);
                            break;
                    }else{
                        male.setSelected(false);
                        female.setSelected(false);
                    }
                    
                    // Retrive Hobbies Into CheckBox
                    
                    if(rs.getString("Hobbies")!= null){
                        checkBox1.setSelected(false);
                        checkBox2.setSelected(false);
                        checkBox3.setSelected(false);
                        
                        //hobbies in the string formate - [Playing , Dancing]
                        System.out.println(rs.getString("Hobbies"));
                        
                        String checkBoxString = rs.getString("Hobbies").replace("[", "").replace("]", "");
                        System.out.println(checkBoxString);
                        
                        //now can converert to a list, strip out commas and spaces
                        List<String> hobbylist = Arrays.asList(checkBoxString.split("\\s*,\\s*"));
                        System.out.println(hobbylist);
                        
                        for(String hobby : hobbylist){ 
                            switch(hobby){
                                case "Playing" : checkBox1.setSelected(true);
                                                 break;
                                case "Singing" : checkBox2.setSelected(true);
                                                 break;
                                case "Dancing" : checkBox3.setSelected(true);
                                                 break;
                                default        : checkBox1.setSelected(false);
                                                 checkBox2.setSelected(false);
                                                 checkBox3.setSelected(false);
                            }
                        }                    
                    }else{
                        checkBox1.setSelected(false);
                        checkBox2.setSelected(false);
                        checkBox3.setSelected(false);
                    }
                    
                    InputStream is = rs.getBinaryStream("Image");
                    OutputStream os = new FileOutputStream( new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    while((size = is.read(content)) != -1){
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();
                    
                image = new Image("file:photo.jpg", 100, 150, true, true);
                ImageView imageView1 = new ImageView(image);
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(150);
                imageView1.setPreserveRatio(true);
                
                layout.setCenter(imageView1);
                BorderPane.setAlignment(imageView1, Pos.TOP_LEFT);
                }
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        table.setOnKeyReleased(e ->{
            if(e.getCode()== KeyCode.UP || e.getCode() == KeyCode.DOWN){
                try {
                User user = (User)table.getSelectionModel().getSelectedItem();
                
                String query = "select * from UserDatabase where ID = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, user.getID());
                rs = pst.executeQuery();
                
                while(rs.next()){
                    id.setText(rs.getString("ID"));
                    fn.setText(rs.getString("FirstName"));
                    ln.setText(rs.getString("LastName"));
                    em.setText(rs.getString("Email"));
                    mobile.setText(rs.getString("MobileNo"));
                    un.setText(rs.getString("Username"));
                    pw.setText(rs.getString("Password"));
                    ((TextField)date.getEditor()).setText(rs.getString("DOB"));
                    
                    if(null != rs.getString("Gender"))switch (rs.getString("Gender")) {
                        case "Male":
                            male.setSelected(true);
                            break;
                        case "Female":
                            female.setSelected(true);
                            break;
                        default:
                            male.setSelected(false);
                            female.setSelected(false);
                            break;
                    }else{
                        male.setSelected(false);
                        female.setSelected(false);
                    }
                    
                    if(rs.getString("Hobbies")!= null){
                        //If brackets are not removed, any attempt to convert to an array or list, just adds an additional set of brackets
                        String checkBoxString = rs.getString("Hobbies").replace("[", "").replace("]", "");
                        
                        //now can converert to a list, strip out commas and spaces
                        List<String> hobbylist = Arrays.asList(checkBoxString.split("\\s*,\\s*"));
                        for(String hobby : hobbylist){ 
                            switch(hobby){
                                case "Playing" : checkBox1.setSelected(true);
                                                 break;
                                case "Singing" : checkBox2.setSelected(true);
                                                 break;
                                case "Dancing" : checkBox3.setSelected(true);
                                                 break;
                                default        : checkBox1.setSelected(false);
                                                 checkBox2.setSelected(false);
                                                 checkBox3.setSelected(false);
                            }
                        }                    
                        
                    }
                    
                    InputStream is = rs.getBinaryStream("Image");
                    OutputStream os = new FileOutputStream( new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    while((size = is.read(content)) != -1){
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();
                    
                image = new Image("file:photo.jpg", 100, 150, true, true);
                ImageView imageView1 = new ImageView(image);
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(150);
                imageView1.setPreserveRatio(true);
                
                layout.setCenter(imageView1);
                BorderPane.setAlignment(imageView1, Pos.TOP_LEFT);
                }
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
            }   catch (FileNotFoundException ex) {
                    Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        FilteredList<User> filteredData = new FilteredList<>(data, e -> true);
        searchField.setOnKeyReleased(e ->{
            searchField.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredData.setPredicate((Predicate<? super User>) user->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(user.getID().contains(newValue)){
                        return true;
                    }else if(user.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(user.getLastName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<User> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            
        });
        
        // JavaFX 8 Tutorial 63 - Jasper Report With SQL Query
        Button report = new Button("Report");
        report.setFont(Font.font("SanSerif", 15));
        report.setOnAction(e ->{
            PrintReport viewReport = new PrintReport();
            viewReport.showReport();           
        });
        
        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(load, delete,update, comboBox, exportToXL, importXLToDB, report);
        
        layout.setBottom(hbox);
        BorderPane.setMargin(hbox, new Insets(10,0,10,10));
        
        ListView list = new ListView(options);
        list.setMaxSize(100, 250);
        //layout.setLeft(list);
        //BorderPane.setMargin(list, new Insets(10));        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private boolean validateFirstName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(fn.getText());
        if(m.find() && m.group().equals(fn.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate First Name");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid First Name");
                alert.showAndWait();
            
            return false;            
        }
    }
    private boolean validateLastName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(ln.getText());
        if(m.find() && m.group().equals(ln.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Last Name");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Last Name");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateNumber(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(id.getText());
        if(m.find() && m.group().equals(id.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Number");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Number");
                alert.showAndWait();
            
            return false;            
        }
    }
    private boolean validateEmaill(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(em.getText());
        if(m.find() && m.group().equals(em.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Email");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Email");
                alert.showAndWait();
            
            return false;            
        }
    }
    private boolean validateMobileNo(){
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobile.getText());
        if(m.find() && m.group().equals(mobile.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Mobile Number");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Valid Mobile Number");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validatePassword(){
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
        Matcher m = p.matcher(pw.getText());
        if(m.matches()){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Password");
                alert.setHeaderText(null);
                alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 6 -15");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateFields(){
        if( un.getText().isEmpty()){
            
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Into The Fields");
                alert.showAndWait();
                
                return false;
        }
        if(date.getEditor().getText().isEmpty()){
            
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter The Date");
                alert.showAndWait();
                
                return false;
        }
        if(!(male.isSelected() | female.isSelected())){
            
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please Selct The Gender");
                alert.showAndWait();
                
                return false;
        }
        if(!(checkBox1.isSelected() | checkBox2.isSelected() | checkBox3.isSelected())){
            
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please Selct One of The Hobby ");
                alert.showAndWait();
                
                return false;
        }
        
        return true;
    }
    public void refreshTable(){
        data.clear();
         try{                
                String query = "select * from UserDatabase";
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                
                while(rs.next()){
                    data.add(new User(
                            rs.getString("ID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("DOB"),
                            rs.getString("Gender"),
                            rs.getString("MobileNo"),
                            rs.getString("Hobbies")
                    ));
                    table.setItems(data);                    
                }
                pst.close();
                rs.close();
            }catch(Exception e2){
                System.err.println(e2);
            }
    }
    public void fillComboBox(){
        options.clear();
        try {
            String query = "select FirstName from UserDatabase ";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                options.add(rs.getString("FirstName"));
            }
            
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void clearFields(){
        id.clear();
        fn.clear();
        ln.clear();
        em.clear();
        mobile.clear();
        un.clear();
        pw.clear();
        date.setValue(null);
        date.getEditor().setText(null);
        male.setSelected(false);
        female.setSelected(false);    
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBoxList.clear();
    }
    
    public void CheckConnection(){
        conn = SqlConnection.DbConnector();
        if(conn == null){
            System.out.println("Connection Not Successful");
            System.exit(1);            
        }else{
            System.out.println("Connection Successful");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}