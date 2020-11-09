package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import application.RecipeHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import application.Recipe;

public class CreateNewRecipeController {
	
	@FXML
	private TextField recipeName;
	
	@FXML
	private TextField yields;
	
	@FXML
	private TextField prepTime;
	
	@FXML
	private TextField cookTime;
	
	@FXML
	private TextField description;
	
	@FXML
	private TextField ingredients;
	
	@FXML
	private TextArea instructions;
	
	@FXML
	private TextField author;
	
	@FXML
	private TextField cuisine;
	
	@FXML
	private Button saveRecipe;
	
	Recipe recipe = new Recipe();
	
	RecipeHandler recipeHandle = new RecipeHandler();
	
	
	@FXML
	public void onClick(MouseEvent event) {
		
		try {
			
			// still need to figure out how to parse
			// ingredients/instructions into an array to
			// save to file
			// so come back to ingredients/instructions fields
			String recipeNames = recipeName.getText();
			recipe.setName(recipeNames);
			int yield = Integer.parseInt(yields.getText());
			recipe.setYield(yield);
			int prep = Integer.parseInt(prepTime.getText());
			recipe.setPrepTime(prep);
			int cook = Integer.parseInt(cookTime.getText());
			recipe.setCookTime(cook);
			String descriptions = description.getText();
			recipe.setDesc(descriptions);
			String ingredient = ingredients.getText();
			recipe.setIngredientsList(ingredient);
			String instruction = instructions.getText();
			recipe.setInstruct(instruction);
			String authors = author.getText();
			recipe.setAuthor(authors);
			String cuisines = cuisine.getText();
			recipe.setCuisine(cuisines);
			recipeHandle.save(recipe);
			
		} catch (IOException e) {
			
			System.out.println("ERROR");
			System.out.println("Inside save Function within CreateNewRecipeController Class");
			e.printStackTrace();
			
		}
		
	}
	
	@FXML
	public void homePage(MouseEvent event) {
		
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
	
	

}
