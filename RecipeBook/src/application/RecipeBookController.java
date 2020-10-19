package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Recipe.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class RecipeBookController implements Initializable {
	
	@FXML private TableView<String> tableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	//Button to test the Recipe Object - Outputs to Console.
	  @FXML
	void printRecipeButton(ActionEvent event) {
		Recipe recipe = new Recipe();
		recipe.name = "Spicy Gyro";
		recipe.author = "Jonas English";
		recipe.prepTime = 60;
		recipe.cuisine = "Mediterranian";
		recipe.favorite = true;
		recipe.addIngredients("Cinammon", "1 tsp.");;
		recipe.yield = 4;
		recipe.cookTime = 120;
		recipe.category = Category.MAINDISHES;
		recipe.desc = "Some Spicy Gyros right here.";
		recipe.instruct = "Make it!";
		recipe.printRecipe();
		Nutrition nutrition = new Nutrition();
		nutrition.totalServ = 4;
		nutrition.amountServ = 4;
		nutrition.calories = 250;
		nutrition.totalFat = 1.0;
		nutrition.satFat = 2.0;
		nutrition.cholest = 3.0;
		nutrition.sodium = 4.2;
		nutrition.totalCarb = 6.5;
		nutrition.dietFiber = 18.54;
		nutrition.sugar = 20;
		nutrition.protien = 9001;
		nutrition.vitaminA = 100;
		nutrition.vitaminC = 777;
		recipe.nutrition = nutrition;
		RecipeHandler recipeHandler = new RecipeHandler();
		File file = null;
		try {
			file = recipeHandler.save(recipe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Recipe newRecipe = new Recipe();
		try {
			newRecipe = recipeHandler.load(file);
			newRecipe.printRecipe();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
