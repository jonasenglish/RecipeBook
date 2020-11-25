package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import application.RecipeHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.recipeModel;
import application.Recipe;
import application.Recipe.Category;

public class CreateNewRecipeController implements Initializable {
	
    @FXML
    private TextArea instructions;

    @FXML
    private TextField ingredientName;

    @FXML
    private TableColumn<IngredientTableData, String> ingredientNameColumn;

    @FXML
    private TableView<IngredientTableData> ingredientTableView;
    
    @FXML
    private TableColumn<IngredientTableData, String> ingredientAmountColumn;
    
    @FXML
    private TextField author;

    @FXML
    private TextField ingredientAmount;

    @FXML
    private TextField cookTime;

    @FXML
    private TextField description;

    @FXML
    private TextField cuisine;

    @FXML
    private TextField prepTime;

    @FXML
    private TextField recipeName;

    @FXML
    private CheckBox favoriteButton;
    
    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button addIngredient;

    @FXML
    private Button deleteIngredient;

    @FXML
    private TextField yields;
    
    @FXML
    private TextField image;

    @FXML
    private Button saveRecipe;
	
	Recipe recipe = new Recipe();
	
	RecipeHandler recipeHandle = new RecipeHandler();
	
	ObservableList<IngredientTableData> observableListITD;
	
	@FXML
	public void onClickSaveRecipe(ActionEvent event) {
		String fieldName = "";
		Alert error = new Alert(AlertType.ERROR);
		
		try {
			
			// still need to figure out how to parse
			// ingredients/instructions into an array to
			// save to file
			// so come back to ingredients/instructions fields
			String recipeNames = recipeName.getText();
			recipe.setName(recipeNames);
			
			try{
				int yield = Integer.parseInt(yields.getText());
				recipe.setYield(yield);
			}catch(NumberFormatException e){
				fieldName = "Yield";
				error.setContentText("Please Only input numbers in the " + fieldName + " Field!");
				error.showAndWait();
				return;
			}
			try{
				int prep = Integer.parseInt(prepTime.getText());
				recipe.setPrepTime(prep);
			}catch(NumberFormatException e){
				fieldName = "Preperation Time";
				error.setContentText("Please Only input numbers in the " + fieldName + " Field!");
				error.showAndWait();
				return;
			}
			try{
				int cook = Integer.parseInt(cookTime.getText());
				recipe.setCookTime(cook);
			}catch(NumberFormatException e){
				fieldName = "Cook Time";
				error.setContentText("Please Only input numbers in the " + fieldName + " Field!");
				error.showAndWait();
				return;
			}
			
			String descriptions = description.getText();
			recipe.setDesc(descriptions);
			String instruction = instructions.getText();
			recipe.setInstruct(instruction);
			String authors = author.getText();
			recipe.setAuthor(authors);
			String cuisines = cuisine.getText();
			recipe.setCuisine(cuisines);
			recipe.setIngredients(getIngrediants());
			recipe.setCategory(Category.valueOf(categoryComboBox.getValue()));
			recipe.setFavorite(favoriteButton.isSelected());
			if(!(image.getText().contains("http"))){
				String fixImageURL = "file:" + image.getText();
				recipe.setImageURL(fixImageURL);
			}else{
				recipe.setImageURL(image.getText());
			}
			File file = recipeHandle.save(recipe);
			if(file.exists())
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Recipe Saved!");
				alert.showAndWait();
			}
			
		} catch (IOException e) {
			
			System.out.println("ERROR");
			System.out.println("Inside save Function within CreateNewRecipeController Class");
			e.printStackTrace();
			
		}
		
	}
	
	private ArrayList<Ingredient> getIngrediants() {
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		for(IngredientTableData ingrediantTableData : observableListITD)
			ingredients.add(ingrediantTableData.getIngredient());
		return ingredients;
	}

	@FXML
	public void onClickHomePage(ActionEvent event) {
		
		try {
		Parent homePage = FXMLLoader.load(getClass().getResource("/application/RecipeBook.fxml"));
		Scene homeScene = new Scene(homePage);
		Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		homeStage.setScene(homeScene);
		homeStage.show();
//		Parent root = (Parent) loader.load();
//		Stage stage = new Stage();
//		stage.setScene(new Scene(rootPane));
//		stage.show();
		
		
		}catch(Exception e) {
			
			System.out.println("home recipe page should have popped up");
			
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<IngredientTableData, String>("ingredientName"));
		ingredientAmountColumn.setCellValueFactory(new PropertyValueFactory<IngredientTableData, String>("ingredientAmount"));

		observableListITD = FXCollections.observableArrayList();
		ingredientTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ObservableList<String> categoryComboBoxList = FXCollections.observableArrayList("APPETIZERS", "BEVERAGES",
				"SOUPS", "SALADS",
				"VEGETABLES", "MAINDISHES",
				"BREADS", "ROLLS",
				"DESSERTS", "MISCELLANEOUS");
		categoryComboBox.setValue("APPETIZERS");
		categoryComboBox.setItems(categoryComboBoxList);
		
		if(RecipeBookController.edit){
			edit(RecipeBookController.getCurrentViewRecipe());
			RecipeBookController.edit = false;
		}
	}
	
	//Adds Ingredient to Table on Click
	@FXML
	void onClickAddIngrediant(ActionEvent event) {
		 String ingredientNameText = ingredientName.getText();
		 String ingredientAmountText = ingredientAmount.getText();
		 if(ingredientAmountText.contains("@") || ingredientNameText.contains("@")){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setContentText("ERROR! \"@\" is an Illegal Character in Ingredient Amount and Name!\nPlease change the value.");
			 alert.showAndWait();
			 return;
		 }
		 Ingredient ingredient = new Ingredient(ingredientNameText, ingredientAmountText);
		 IngredientTableData nData = new IngredientTableData(ingredientNameText, ingredientAmountText, ingredient);
		 observableListITD.add(nData);
		 ingredientTableView.setItems(observableListITD);
		 //System.out.println("ADDED");
	 }
	
	//Removes ingredient from Table on Click
	@FXML
	void onClickDeleteIngrediant(ActionEvent event) {
		 for(IngredientTableData ingrediantTableData : observableListITD)
			 if(ingredientTableView.getSelectionModel().getSelectedItem().equals(ingrediantTableData)){
				 observableListITD.remove(ingrediantTableData);
				 break;
			 }
		 ingredientTableView.setItems(observableListITD);
	 }

	//Gets image location
	@FXML
	void onClickFindImage(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Recipe File");
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
    	File imageFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
    	if(imageFile == null)
    		return;
		
    	image.setText(imageFile.getAbsolutePath());	
	}
	
	public void edit(Recipe recipe) {
		instructions.setText(recipe.instruct);
		author.setText(recipe.author);
		cookTime.setText(recipe.cookTime + "");
		description.setText(recipe.desc);
		cuisine.setText(recipe.cuisine);
		prepTime.setText(recipe.prepTime + "");
		recipeName.setText(recipe.name);
		favoriteButton.setSelected(recipe.favorite);
		yields.setText(recipe.yield + "");
		recipeModel.setUpIngredientTableView(ingredientTableView, recipe.getIngredients());
		observableListITD = ingredientTableView.getItems();
		image.setText(recipe.imageURL);
	 }

}
