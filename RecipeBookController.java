package application;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.recipeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML private ListView<String> quantity;
    @FXML private ListView<String> ingrediant;

    
    @FXML 
    private MenuBar myMenuBar;

    @FXML
    private TextField searchText;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private Label label;
    
    @FXML
    private Label itemName;
    
    @FXML
    private Label authorName;
    
    @FXML
    private Label cusineType;
    
    @FXML
    private TextArea description;
    
    @FXML
    private TextField instruction;

    @FXML
    private ComboBox<String> searchComboBox;
    
    
    
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
		            recipeModel.GetRecipeFromRowData(rowData);
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
		
		// KEVIN's CODE FOR VIEW PAGE
		List<String> recipe = new ArrayList <> ();
		Nutrition n = new Nutrition();
		Recipe r = new Recipe();

		try {
			recipe = Files.readAllLines(Paths.get("gyro.rcp"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		String[] split = recipe.toArray(new String[0]); // splits string by newline
		String ingredients_quantity []  = split[12].split("/"); // splits the ingredients into the name and quantity


		// splits info that was read in from rcp file into separate variable 
		String recipeName = split[0];
		String author = split[1];
		String cuisine = split[2];
		String desc = split[3];
		String instructions = split[4];
		String servingSize = split[6];
		String prepTime = split[7];
		String cookTime = split[8];
		String category = split[9];
		String fav = split[10];
		String nutrition = split[11];
		String ingredients = ingredients_quantity[0];
		String amount = ingredients_quantity[1];

		itemName.setText(recipeName);
		authorName.setText(author);
		cusineType.setText(cuisine);
		description.setText(desc);
		instruction.setText(instructions);
		ingrediant.getItems().add(ingredients);
		quantity.getItems().add(amount);



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
	
	// Going to create and pull up new fxml document for create recipe page
	// will create a controller class specifically for the recipe page 
	// to separate functionality between pages
	@FXML
	void createPage(ActionEvent event) {
		
		try {
			
		Parent createPage = FXMLLoader.load(getClass().getResource("/application/CreateNewRecipe.fxml"));
		Scene createScene = new Scene(createPage);
		// Menu item (i.e. our little drop down menu when we click on File, edit or help)
		// does not inherit from Node, so we cannot "get the scene"
		// from the menu item, and must use the Menu Bar to do so
		// this allows us to use the same stage (window) and change our scene
		Stage createStage = (Stage) myMenuBar.getScene().getWindow();
		createStage.setScene(createScene);
		createStage.show();
		
		}catch(Exception e) {
			
			System.out.println("ERROR");
			System.out.println("create page should have been pulled up within the same stage");
			e.printStackTrace();
			
		}
		
	}
    
}
