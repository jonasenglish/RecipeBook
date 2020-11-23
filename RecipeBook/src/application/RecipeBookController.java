package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Hashtable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RecipeBookController implements Initializable {
	
	public static boolean edit;

	@FXML
	private Label recipeTitleLabel;
	
    @FXML
    private Tab recipeViewTab;
    
    @FXML
    private TabPane tabPane;
	
	private RecipeHandler recipeHandler;
	private static Recipe currentViewRecipe;
	
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
    
    @FXML private Label itemName;

    @FXML private Label authorName;

    @FXML private Label cusineType;

    @FXML private TextArea description;

    @FXML private TextArea instruction;
    
    @FXML private CheckBox favoriteCheckBox;
    
    @FXML private ImageView recipeImage;
    
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
	
	@FXML
	void onClickDeleteOption(ActionEvent event){
		if(getCurrentViewRecipe() != null)
			recipeHandler.delete(getCurrentViewRecipe());
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please select a Recipe to delete first!");
			alert.showAndWait();
		}
	}
	
	@FXML
	void onClickEditOption(ActionEvent event){
		if(getCurrentViewRecipe() != null){
			edit = true;
			createPage(event);
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please select a Recipe to edit first!");
			alert.showAndWait();
		}
	}
	
	@FXML
	void onClickAboutOption(ActionEvent event){
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("About");
		info.setHeaderText("Recipe Book");
		info.setContentText("This is an application to save, view, and create recipes.\nDeveloped by:\n\tJonas English\n\tKevin Nguyen\n\tRishika Someshwar\n\tTravis Sauer");
		info.showAndWait();
	}
	
	@FXML
	void onClickFavorite(ActionEvent event){
		currentViewRecipe.favorite = favoriteCheckBox.isSelected();
	}
	
	//Exits the application when clicked.
	@FXML
	void onClickExit(ActionEvent event){
		Main.getPrimaryStage().close();
	}
	
	//Open a File Chooser window to select a recipe file
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
			if(recipeHandler.recipeFileDict == null)
				recipeHandler.recipeFileDict = new Hashtable<Recipe, File>();
			recipeHandler.recipeFileDict.put(recipe, recipeFile);
			view(recipe);
		} catch (FileNotFoundException e) {
			System.out.println("There was an error loading a recipe from File Chooser!");
		}
    }
	
	private void view(Recipe recipe) {
		//Set to current view recipe
		setCurrentViewRecipe(recipe);
		//Change the current tab to the view tab.
		tabPane.getSelectionModel().select(recipeViewTab);
		//Sets up the ingredients Table
		recipeModel.setUpIngredientTableView(ingrediantTableView, recipe.ingredients);
		
		favoriteCheckBox.setSelected(recipe.favorite);	
		itemName.setText(recipe.name);
		authorName.setText("By: " + recipe.author);
		cusineType.setText("Cuisine: " + recipe.cuisine);
		description.setText(recipe.desc);
		instruction.setText(recipe.instruct);
		String fixImageURL = recipe.imageURL;
		Image newImage = new Image(fixImageURL);
		recipeImage.setImage(newImage);
	}

	public static Recipe getCurrentViewRecipe() {
		return currentViewRecipe;
	}

	@SuppressWarnings("static-access")
	public void setCurrentViewRecipe(Recipe currentViewRecipe) {
		this.currentViewRecipe = currentViewRecipe;
	}
}
