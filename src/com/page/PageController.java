package com.page;

import com.dao.DaoImpl;
import com.model.Books;
import com.model.Users;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.dao.Dao;




public class PageController implements Initializable {
    
    Integer selectionId = 0;
    
    @FXML
    private TextField nameTF;
    @FXML
    private TextField authorTF;
    @FXML
    private TextField pageCountTF;
    @FXML
    private TextField amountTF;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button addLanguageBtn;
    @FXML
    private ComboBox<String> themaComboBox;
    @FXML
    private Button addThemaBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField searchTF;
    @FXML
    private TextField minimalAmountTF;
    @FXML
    private TextField maximalAmountTF;
    @FXML
    private Button amountFilterBtn;
    @FXML
    private TextField minimalPageCountTF;
    @FXML
    private TextField maximalPageCountTF;
    @FXML
    private Button pageCountFilterBtn;
    @FXML
    private Button clearAllFilterBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private Button filterBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox soldCheckBox;
    @FXML
    private CheckBox unsoldCheckBox;
    @FXML
    private CheckBox allCheckBox;
    @FXML
    private Button showBtn;
    @FXML
    private Button hideBtn;
    @FXML
    private Label welcomeLbl;
    @FXML
    private Button logOutBtn;
    @FXML
    private TableView<Books> tableView;
    @FXML
    private TableColumn<Books, Integer> idColumn;
    @FXML
    private TableColumn<Books, String> nameColumn;
    @FXML
    private TableColumn<Books, String> authorColumn;
    @FXML
    private TableColumn<Books, Integer> pageCountColumn;
    @FXML
    private TableColumn<Books, Double> amountColumn;
    @FXML
    private TableColumn<Books, String> languageColumn;
    @FXML
    private TableColumn<Books, String> themaColumn;
    @FXML
    private TableColumn<Books, String> statusColumn;
    @FXML
    private Label warningLbl;
    
    
    private Users user;
    
    public void setUser(Users selectedUser){
        this.user = selectedUser;
    }
    
    DaoImpl dao = new DaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadLanguageCB();
        loadThemaCB();
        tableView.setVisible(false);
        
        loadColumn();
        loadRows();
    }    

    @FXML
    private void languageComboBoxOnKeyReleased(KeyEvent event) {
        
    }

    @FXML
    private void addLanguageBtnOnAction(ActionEvent event) {
        String newLanguage = JOptionPane.showInputDialog(null, "New Language");
        if (newLanguage.equalsIgnoreCase("")) {
            warningLbl.setText("Not Added!Language is Empty!");
        } else {
            if (!languageComboBox.getItems().contains(newLanguage)) {
                if (languageComboBox.getItems().add(newLanguage)) {
                    warningLbl.setText("New language succesfully added!");
                    
                }
            } else {
                warningLbl.setText("Not Added! This language already exists!");
            }
        }
    }

    @FXML
    private void themaComboBoxOnKeyReleased(KeyEvent event) {
        
    }

    @FXML
    private void addThemaBtnOnAction(ActionEvent event) {
        String newThema = JOptionPane.showInputDialog(null, "New Thema");
        if (newThema.equalsIgnoreCase("")) {
            warningLbl.setText("Not Added!Thema is Empty!");
        } else {
            if (!themaComboBox.getItems().contains(newThema)) {
                if (themaComboBox.getItems().add(newThema)) {
                    warningLbl.setText("New thema succesfully added!");
                    
                }
            } else {
                warningLbl.setText("Not Added! This thema already exists!");
            }
        }
    }
    
    public void refresh() {
        tableView.getItems().clear();
        tableView.getItems().addAll(dao.getAllBooks());
    }

    @FXML
    private void saveBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!nameTF.getText().equalsIgnoreCase("") && !authorTF.getText().equalsIgnoreCase("")
                && !amountTF.getText().equalsIgnoreCase("") && !pageCountTF.getText().equalsIgnoreCase("")) {
            try {
                Books newBook = new Books();
                newBook.setName(nameTF.getText());
                newBook.setAuthor(authorTF.getText());
                newBook.setPageCount(Integer.parseInt(pageCountTF.getText()));
                newBook.setAmount(Double.parseDouble(amountTF.getText()));
                newBook.setThema(themaComboBox.getSelectionModel().getSelectedItem());
                newBook.setLanguage(languageComboBox.getSelectionModel().getSelectedItem());
                if (dao.addBook(newBook)) {
                    warningLbl.setText("Book successfully added!");
                    refresh();
                } else {
                    warningLbl.setText("Book not added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningLbl.setText("Please, fill in empty fields!");
        }
    }

    @FXML
    private void updateBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!nameTF.getText().equalsIgnoreCase("") && !authorTF.getText().equalsIgnoreCase("")
                && !pageCountTF.getText().equalsIgnoreCase("") && !amountTF.getText().equalsIgnoreCase("")) {
            try {
                Books book = new Books();
                book.setName(nameTF.getText());
                book.setAuthor(authorTF.getText());
                book.setAmount(Double.parseDouble(amountTF.getText()));
                book.setPageCount(Integer.parseInt(pageCountTF.getText()));
                book.setThema(themaComboBox.getSelectionModel().getSelectedItem());
                book.setLanguage(languageComboBox.getSelectionModel().getSelectedItem());
                book.setId(selectionId);
                if (dao.updateBook(book)) {
                    warningLbl.setText("Book successfully updated!");
                    refresh();
                } else {
                    warningLbl.setText("Book Not Updated");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningLbl.setText("Please, fill in empty fields!");
        }
    }

    @FXML
    private void searchTFOnKeyReleased(KeyEvent event) {
        String keyword = searchTF.getText().toUpperCase().trim();
        if (keyword.equalsIgnoreCase("")) {
            refresh();
        } else {
            List resultList = dao.searchBook(keyword);
            tableView.getItems().clear();
            tableView.getItems().addAll(resultList);
        }
    }

    @FXML
    private void amountFilterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!minimalAmountTF.getText().trim().equalsIgnoreCase("") && !maximalAmountTF.getText().trim().equalsIgnoreCase("")) {
            Double minAmount = Double.parseDouble(minimalAmountTF.getText().trim());
            Double maxAmount = Double.parseDouble(maximalAmountTF.getText().trim());
            List<Books> resultList = dao.filterByAmount(minAmount, maxAmount);
            tableView.getItems().clear();
            tableView.getItems().addAll(resultList);
        } else {
            warningLbl.setText("Please fill in empty fields!");
        }
    }

    @FXML
    private void pageCountFilterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!minimalPageCountTF.getText().trim().equalsIgnoreCase("") && !maximalPageCountTF.getText().trim().equalsIgnoreCase("")) {
            Integer minPageCount = Integer.parseInt(minimalPageCountTF.getText().trim());
            Integer maxPageCount = Integer.parseInt(maximalPageCountTF.getText().trim());
            List<Books> resultList = dao.filterByPageCount(minPageCount, maxPageCount);
            tableView.getItems().clear();
            tableView.getItems().addAll(resultList);
        } else {
            warningLbl.setText("Please fill in empty fields!");
        }
    }

    @FXML
    private void clearAllFilterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        searchTF.setText("");
        minimalAmountTF.setText("");
        maximalAmountTF.setText("");
        minimalPageCountTF.setText("");
        maximalPageCountTF.setText("");
        refresh();
    }

    @FXML
    private void buyBtnOnAction(ActionEvent event) {
        boolean isUpdated = dao.buyBook(selectionId);
        if (isUpdated) {
            refresh();
            warningLbl.setText("Selected book successfully updated!");
        }
    }

    @FXML
    private void filterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (soldCheckBox.isSelected()) {
            List<Books> soldBooks = dao.filterByStatus("SOLD");
            tableView.getItems().clear();
            tableView.getItems().addAll(soldBooks);
        } 
        if(unsoldCheckBox.isSelected()){
            List<Books> notSoldBooks = dao.filterByStatus("NOT SOLD");
            tableView.getItems().clear();
            tableView.getItems().addAll(notSoldBooks);
        } 
        if (allCheckBox.isSelected()) {
            refresh();
        }
    }

    @FXML
    private void deleteBtnOnAction(ActionEvent event) {
        boolean isDeleted = dao.deleteBook(selectionId);
        if (isDeleted) {
            refresh();
            warningLbl.setText("Selected book successfully deleted!");
        } else {
            warningLbl.setText("Not Deleted!");
        }
    }

    @FXML
    private void showBtnOnAction(ActionEvent event) {
        tableView.setVisible(true);
        welcomeLbl.setText("Welcome, "+user.getName()+" "+user.getSurname());
    }

    @FXML
    private void hideBtnOnAction(ActionEvent event) {
        tableView.setVisible(false);
    }

    @FXML
    private void logOutBtnOnAction(ActionEvent event) {
        try {
            int response = JOptionPane.showConfirmDialog(null, "Do you want to log out?");
            if (response == JOptionPane.YES_OPTION) {
                Stage stage1 = (Stage) logOutBtn.getScene().getWindow();
                stage1.close();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Log in Page");
                stage.getIcons().add(new Image("/com/image/login.png"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/login/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tableViewOnMousePressed(MouseEvent event) {
        Books book = tableView.getSelectionModel().getSelectedItem();
        nameTF.setText(book.getName());
        authorTF.setText(book.getAuthor());
        pageCountTF.setText(""+book.getPageCount());
        amountTF.setText(""+book.getAmount());
        themaComboBox.getSelectionModel().select(book.getThema());
        languageComboBox.getSelectionModel().select(book.getLanguage());
        selectionId = book.getId();
        
    }  

    private void loadLanguageCB() {
        List<String> language = new ArrayList<String>();
        languageComboBox.getItems().add("Azerbaijan");
        languageComboBox.getItems().add("English");
        languageComboBox.getItems().add("Turkish");
        languageComboBox.getItems().add("Arabic");
        languageComboBox.getItems().add("Russian");
        languageComboBox.getItems().add("German");
        languageComboBox.getSelectionModel().select("Azerbaijan");
    }

    private void loadThemaCB() {
        List<String> thema = new ArrayList<String>();
        themaComboBox.getItems().add("Romance");
        themaComboBox.getItems().add("Novel");
        themaComboBox.getItems().add("Poem");
        themaComboBox.getItems().add("Detective");
        themaComboBox.getItems().add("Adventure");
        themaComboBox.getItems().add("Scientific"); 
        themaComboBox.getSelectionModel().select("Romance");
    }

    private void loadColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        themaColumn.setCellValueFactory(new PropertyValueFactory<>("thema"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadRows() {
        tableView.getItems().addAll(dao.getAllBooks());
    }
}