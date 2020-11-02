package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.recipeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecipeBookController implements Initializable {
	
	private RecipeHandler recipeHandler;
	
	@FXML 
	private TableView<TableData> tableView;
	
    @FXML private TableColumn<TableData, String> titleTableColumn;
    @FXML private TableColumn<TableData, String> cuisineTableColumn;
    @FXML private TableColumn<TableData, String> locationTableColumn;
    @FXML private TableColumn<TableData, String> authorTableColumn;
    @FXML private TableColumn<TableData, LocalDate> dateTableColumn;
    @FXML private TableColumn<TableData, String> categoryTableColumn;


    @FXML
    private TextField searchText;
    
    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchComboBox;

    //Called at the Start of the program.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<String> searchComboBoxList = FXCollections.observableArrayList("Title", "Author", "Category", "Favorite", "Cuisine");
		
		titleTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("title"));
		cuisineTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("cuisine"));
		dateTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, LocalDate>("date"));
		categoryTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("category"));
		authorTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("author"));
		locationTableColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("location"));
		
		tableView.setRowFactory( tv -> {
		    TableRow<TableData> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            TableData rowData = row.getItem();
		            System.out.println(rowData.getTitle());
		        }
		    });
		    return row ;
		});
		
		recipeHandler = new RecipeHandler();
		
		searchComboBox.setValue("Title");
		searchComboBox.setItems(searchComboBoxList);
		
		//Does something when the Search Bar text is Changed.
		/*
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
		    //System.out.println("textfield changed from " + oldValue + " to " + newValue);
		});	
		*/
	}
    
	//Button to test the Recipe Object - Outputs to Console.
	@FXML
	void printRecipeButton(ActionEvent event) {
		recipeModel.TestButton();
	}
	
	//Called when the Search Button is Clicked or when Enter is pressed in the searchText TextField.
	@FXML
    void onClickSearchButton(ActionEvent event) {
    	recipeHandler.recipeFileDict = recipeHandler.getRecipes();
    	ObservableList<TableData> data = FXCollections.observableArrayList();
    	recipeModel.Search(recipeHandler.recipeFileDict, data, searchText, tableView, recipeHandler.recipes, searchComboBox);
	}
    
}
