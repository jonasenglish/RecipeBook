package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import application.Recipe.Category;

//Class to handle Recipe Files
public class RecipeHandler {
	
	//Path to the folder containing recipes.
	public String recipeFolderPath;
	
	//The Default path to the Recipes Folder is in the Same folder as the application.
	public RecipeHandler(){
		this.recipeFolderPath = getClass().getResource("").getPath() + "/recipes/";
	}
	
	//Changing the path to the folder containing Recipes. Useful if the folder containing recipes is different than the default.
	public RecipeHandler(String recipeFolderPath) {
		this.recipeFolderPath = recipeFolderPath;
	}
	
	//Saves the given Recipe file into a .rcp (Recipe) file.
	public File save(Recipe recipe) throws IOException{
		
		//Testing Recipe Folder
		File recipeFolder = new File(recipeFolderPath);
		if(!recipeFolder.exists())
			recipeFolder.mkdir();
		
		//Save file at (RecipeName-AuthorName.rcp)
		File recipeFile = new File(recipeFolderPath + recipe.name + "-" + recipe.author + ".rcp");
		try{
			//Creating File
			if(recipeFile.createNewFile()){
				System.out.println("File Created: " + recipeFile.getAbsolutePath());
			}
			else{
				System.out.println("File already exists with that name. Saving over that File.");
				System.out.println("File Created: " + recipeFile.getAbsolutePath());
			}
		}catch(IOException e){
			System.out.println("Error.");
			e.printStackTrace();
		}
	
		//Saving to File
		FileWriter fileWriter = new FileWriter(recipeFile);
		fileWriter.write(recipe.name + "\n" + recipe.author + "\n" + recipe.cuisine + "\n" + recipe.desc + "\n" + recipe.instruct + "\n" + 
		recipe.imageURL + "\n" + recipe.yield + "\n" + recipe.prepTime + "\n" + recipe.cookTime + "\n" + recipe.category + "\n" + recipe.favorite);
		saveNutrition(recipe.nutrition, fileWriter);
		saveIngredient(recipe.ingredients, fileWriter);
		fileWriter.close();
		return recipeFile;
	}
	
	//Saves nutrition
	public void saveNutrition(Nutrition nutrition, FileWriter fileWriter) throws IOException{
		
		if(nutrition != null)
			fileWriter.write("\n" + nutrition.totalServ + "\n" + nutrition.amountServ + "\n" + nutrition.calories + "\n" + nutrition.totalFat + "\n" + nutrition.satFat + "\n" + 
				nutrition.cholest + "\n" + nutrition.sodium + "\n" + nutrition.totalCarb + "\n" + nutrition.dietFiber + "\n" + nutrition.sugar + "\n" + 
				nutrition.protien + "\n" + nutrition.vitaminA  + "\n" + nutrition.vitaminC);
		else{
			fileWriter.write("\nNONUTRITION");
		}

	}
	
	//Saves ingredients
	public void saveIngredient(ArrayList<Ingredient> ingredients, FileWriter fileWriter) throws IOException{
		
		for(Ingredient i: ingredients){
			fileWriter.write("\n" + i.ingredientName + "@" + i.ingredientAmount);
		}
		
	}
	
	//Loads the given .rcp (Recipe) file's content into a recipe object.
	public Recipe load(File file) throws FileNotFoundException{
		Recipe recipe = new Recipe();
		
		Scanner scanner = new Scanner(file);
		
		recipe.name = scanner.nextLine();
		recipe.author = scanner.nextLine();
		recipe.cuisine = scanner.nextLine();
		recipe.desc = scanner.nextLine();
		recipe.instruct = scanner.nextLine();
		recipe.imageURL = scanner.nextLine();
		
		recipe.yield = scanner.nextInt();
		recipe.prepTime = scanner.nextInt();
		recipe.cookTime = scanner.nextInt();
		
		Category category = Category.valueOf(scanner.next());
		recipe.category = category;
		
		recipe.favorite = scanner.nextBoolean();
		
		recipe.nutrition = loadNutrition(scanner);
		recipe.ingredients = loadIngredient(scanner);
		
		return recipe;
	}
	
	//Loads nutrition
	public Nutrition loadNutrition(Scanner scanner){
		
		Nutrition nutrition = new Nutrition();
		
		String check = scanner.next();
		if(check.equals("NONUTRITION")){
			System.out.println(check);
			return nutrition;
		}else{
			
			nutrition.totalServ = Integer.parseInt(check);
			nutrition.amountServ = scanner.nextInt();
			nutrition.calories = scanner.nextInt();
			nutrition.totalFat = scanner.nextDouble();
			nutrition.satFat = scanner.nextDouble();
			nutrition.cholest = scanner.nextDouble();
			nutrition.sodium = scanner.nextDouble();
			nutrition.totalCarb = scanner.nextDouble();
			nutrition.dietFiber = scanner.nextDouble();
			nutrition.sugar = scanner.nextInt();
			nutrition.protien = scanner.nextInt();
			nutrition.vitaminA = scanner.nextInt();
			nutrition.vitaminC = scanner.nextInt();
			scanner.nextLine();
		}
		
		return nutrition;
	}
	
	//Warning: Splitting on "@". Should probably not allow users to input "@" in ingredient names and amounts. - JE
	//Loads ingredients
	public ArrayList<Ingredient> loadIngredient(Scanner scanner){
		
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		String line = scanner.nextLine();
		
		while(line != null && !line.trim().isEmpty()){
			//System.out.println(line);
			String[] ingrediantSplit = line.split("@");
			Ingredient newIngrediant = new Ingredient();
			newIngrediant.ingredientName = ingrediantSplit[0];
			newIngrediant.ingredientAmount = ingrediantSplit[1];
			ingredients.add(newIngrediant);
			if(scanner.hasNextLine())line = scanner.nextLine();
			else line = null;
		}
			
		return ingredients;
		
	}
	
}
