package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.Scanner;

import Model.recipeModel;
import application.Recipe.Category;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

//Class to handle Recipe Files
public class RecipeHandler {
	
	//Path to the folder containing recipes.
	public String recipeFolderPath;
	
	//Dictionary containing available recipes and their associated file
	public Hashtable<Recipe, File> recipeFileDict;
	public ArrayList<Recipe> recipes;
	
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
		fileWriter.write(recipe.getName() + "\n" + recipe.getAuthor() + "\n" + recipe.getCuisine() + "\n" + recipe.getDesc() + "\n" + recipe.getInstruct() + "\n" + 
		recipe.imageURL + "\n" + recipe.getYield() + "\n" + recipe.getPrepTime() + "\n" + recipe.getCookTime() + "\n" + recipe.getCategory() + "\n" + recipe.favorite);
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
		
		try{
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
		} catch(Exception e){
			throw e;
		}
		
		return recipe;
	}
	
	//Loads nutrition
	public Nutrition loadNutrition(Scanner scanner){
		
		Nutrition nutrition = new Nutrition();
		
		String check = scanner.next();
		if(check.equals("NONUTRITION")){
			//System.out.println(check);
			scanner.nextLine();
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
	
	public Hashtable<Recipe, File> getRecipes(){
		Hashtable<Recipe, File> recipeFileDict = new Hashtable<Recipe, File>();
		recipes = new ArrayList<Recipe>();
		File folder = new File(recipeFolderPath);
		try{
		for(final File file : folder.listFiles()){
			if(file.isFile() && file.getName().contains(".rcp")){
				try{
					Recipe recipe = load(file);
					recipes.add(recipe);
					recipeFileDict.put(recipe, file);
				}catch(Exception e){
					System.out.print("Error, Something went wrong while reading " + file.getName());
				}
			}
		}
		}catch(NullPointerException e){
			System.out.println("Error, Recipe Folder is empty!");
		}
		return recipeFileDict;
	}

	public void delete(Recipe recipe) {
		ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);
		ButtonType okButton = new ButtonType("Ok", ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + recipe.name + "?", yesButton, noButton);
		alert.getButtonTypes().setAll(yesButton, noButton);
		Optional<ButtonType> result = alert.showAndWait();
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(okButton);
		if (result.orElse(noButton) == yesButton) {
			File recipeFile = recipeFileDict.get(recipe);
			if(recipeFile.delete()){
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText(recipe.name + " was deleted.");
				alert.showAndWait();
				recipeFileDict.remove(recipe);
			}else{
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Error, could not delete " + recipeFile.getName() + ".\nAt path:\n " + recipeFile.getPath());
				alert.showAndWait();
			}
		}
	}
}
