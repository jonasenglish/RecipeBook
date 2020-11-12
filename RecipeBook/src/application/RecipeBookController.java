package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RecipeBookController implements Initializable {
	
    @FXML
    private MenuItem openFileOption;
	
	@FXML
	private Label recipeTitleLabel;
	
    @FXML
    private Tab recipeViewTab;
    
    @FXML
    private TabPane tabPane;
	
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
    private MenuBar myMenuBar;

    @FXML
    private TextField searchText;
    
    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchComboBox;
    
    @FXML private TableColumn<IngredientTableData, String> ingrediantNameColumn;
    @FXML private TableView<IngredientTableData> ingrediantTableView;
    @FXML private TableColumn<IngredientTableData, String> ingrediantAmountColumn;

    //Called at the Start of the program.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<String> searchComboBoxList = FXCollections.observableArrayList("Title", "Author", "Category", "Favorite", "Cuisine");
		
		ingrediantNameColumn.setCellValueFactory(new PropertyValueFactory<IngredientTableData, String>("ingredientName"));
		ingrediantAmountColumn.setCellValueFactory(new PropertyValueFactory<IngredientTableData, String>("ingredientAmount"));
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
		            view(recipeModel.GetRecipeFromRowData(rowData));
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
    
	//Open a File Chooser window to select a recipe file.
    @FXML
    void onClickOpenFileOption(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Recipe File");
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RCP", "*.rcp"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
    	File recipeFolderPath = new File(recipeHandler.recipeFolderPath);
    	fileChooser.setInitialDirectory(recipeFolderPath);
    	File recipeFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
    	if(recipeFile == null)
    		return;
    	try {
			Recipe recipe = recipeHandler.load(recipeFile);
			view(recipe);
		} catch (FileNotFoundException e) {
			System.out.println("There was an error loading a recipe from File Chooser!");
		}
    }
	
	private void view(Recipe recipe) {
		//Change the current tab to the view tab.
		tabPane.getSelectionModel().select(recipeViewTab);
		//Display Recipe Name
		recipeTitleLabel.setText(recipe.name);
		//Sets up the ingredients Table
		recipeModel.setUpIngredientTableView(ingrediantTableView, recipe.ingredients);
		
	}
}
