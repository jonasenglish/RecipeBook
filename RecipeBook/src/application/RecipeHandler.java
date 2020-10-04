package application;

import java.io.File;
import java.util.ArrayList;

//Class to handle Recipe Files
public class RecipeHandler {
	
	//Saves the given Recipe file into a .rcp (Recipe) file.
	public void save(Recipe recipe){
		
		//Save file at (RecipeName-AuthorName.rcp)
		//May need to check for existing files? -optional
		//Any sort of encryption is also completely optional
		
	}
	
	public void saveNutrition(Nutrition nutrition, File file){
		
		//Dedicated method to save the Nutrition. Technically this isn't needed, but its' clean in my opinion.

	}
	
	public void saveIngredient(ArrayList<Ingredient> ingredients, File file){
		
		//Iterate through the list of Ingredients, It may be best to do this last for easier loading
		//Otherwise place something at the end of this to signify the end of the ingredient list.
		
	}
	
	//Loads the given .rcp (Recipe) file's content into a recipe object.
	public Recipe load(File file){
		Recipe recipe = new Recipe();
		
		//Load - Pretty much just copy line by line, however the file was saved.
		
		return recipe;
	}
	
}
