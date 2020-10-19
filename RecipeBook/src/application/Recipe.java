package application;

import java.util.ArrayList;

// This is the Object Class to contain all information on recipes
public class Recipe {

	//Initializing ingredients when the object is made.
	public Recipe(){
		ingredients = new ArrayList<Ingredient>();
	}
	
	//The Recipe's name, e.g. "Spicy Gyros"
	public String name;
	//The recipe author's name.
	public String author;
	//The cuisine type of the recipe, e.g. "Mediterranean"
	public String cuisine;
	//Description
	public String desc;
	//Recipe instructions
	public String instruct;
	//URL to the Recipe Image
	public String imageURL;
	
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
	
	//Nutritional information.
	public Nutrition nutrition;
	
	//Adds an Ingredient to given Ingredient List
	public void addIngredients(String ingredientName, String ingrediantAmount){
		Ingredient ingredient = new Ingredient();
		ingredient.ingredientAmount = ingrediantAmount;
		ingredient.ingredientName = ingredientName;
		this.ingredients.add(ingredient);
	}
	
	public void printRecipe(){
		System.out.println("Name: " + this.name);
		System.out.println("Author: " + this.author);
		System.out.println("Cuisine: " + this.cuisine);
		System.out.println("Yield: " + this.yield);
		System.out.println("Description: " + this.desc);
		System.out.println("Prep Time: " + this.prepTime + " minutes");
		System.out.println("Cook Time: " + this.cookTime + " minutes");
		System.out.println("Category: " + this.category);
		if(this.nutrition != null){
			System.out.println("Total Servings: " + this.nutrition.totalServ );
			System.out.println("Serving Amount: " + this.nutrition.amountServ );
			System.out.println("Calories: " + this.nutrition.calories );
			System.out.println("Saturated Fat: " + this.nutrition.satFat );
			System.out.println("Cholesterol: " + this.nutrition.cholest );
			System.out.println("Sodium: " + this.nutrition.sodium );
			System.out.println("Total Carbs: " + this.nutrition.totalCarb );
			System.out.println("Dietary Fiber: " + this.nutrition.dietFiber );
			System.out.println("Sugar(g): " + this.nutrition.sugar);
			System.out.println("Protien: " + this.nutrition.protien );
			System.out.println("Vitamin A: " + this.nutrition.vitaminA );
			System.out.println("Vitamin C: " + this.nutrition.vitaminC );
		}
		for(int i = 0; i < ingredients.size(); i++){
			this.ingredients.get(i).printIngrediant();
		}
		System.out.println("Instructions: " + this.instruct);
		if(this.favorite){
			System.out.println("This recipe is a Favorite");
		}
	}
}
