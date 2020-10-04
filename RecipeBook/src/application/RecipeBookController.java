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
	
	  @FXML
	void printRecipeButton(ActionEvent event) {
		Recipe recipe = new Recipe();
		recipe.name = "Spicy Gyro";
		recipe.author = "Jonas English";
		recipe.prepTime = 60;
		recipe.cuisine = "Mediterranian";
		recipe.favorite = true;
		Ingredient cinammon = new Ingredient();
		cinammon.ingredientName = "Cinnamon";
		cinammon.ingredientAmount = "1 tsp.";
		recipe.ingredients = new ArrayList<Ingredient>();
		recipe.ingredients.add(cinammon);
		recipe.yield = 4;
		recipe.cookTime = 120;
		recipe.category = Category.MAINDISHES;
		recipe.printRecipe();
	}
	
}
