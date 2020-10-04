package application;

import java.util.ArrayList;
import java.util.HashMap;

// This is the Object Class to contain all information on recipes
public class Recipe {

	//The Recipe's name, e.g. "Spicy Gyros"
	public String name;
	//The recipe author's name.
	public String author;
	//The cuisine type of the recipe, e.g. "Mediterranean"
	public String cuisine;
	
	//The yield of the recipe, e.g. (1) serving(s)
	public int yield;
	//The preparation time of the recipe, represented in minutes.
	public int prepTime;
	//The cook time of the recipe, represented in minutes.
	public int cookTime;
	
	//This is the Category of the dish, This will appear as a Drop-Down menu for the user.
	public enum Category{
		APPETIZERS, BEVERAGES,
		SOUPS, SALADS,
		VEGETABLES, MAINDISHES,
		BREADS, ROLLS,
		DESSERTS, MISCELLANEOUS
	}
	public Category category;
	
	public ArrayList<Ingredient> ingredients;
	
	//Whether or not this recipe has been favorited.
	public boolean favorite;
	
	public void addIngredients(String ingredientName, String ingrediantAmount){
		
	}
	
	public void printRecipe(){
		System.out.println("Name: " + this.name);
		System.out.println("Author: " + this.author);
		System.out.println("Cuisine: " + this.cuisine);
		System.out.println("Yield: " + this.yield);
		System.out.println("Prep Time: " + this.prepTime + " minutes");
		System.out.println("Cook Time: " + this.cookTime + " minutes");
		System.out.println("Category: " + this.category);
		for(int i = 0; i < ingredients.size(); i++){
			this.ingredients.get(i).printIngrediant();
		}
		if(this.favorite){
			System.out.println("This recipe is a Favorite");
		}
	}
}
