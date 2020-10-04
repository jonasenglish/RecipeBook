package application;

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
	}
	
}
