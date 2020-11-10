package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Hashtable;

import application.Nutrition;
import application.Recipe;
import application.RecipeHandler;
import application.TableData;
import application.Recipe.Category;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class recipeModel {
	
	//Search for file based on the SearchComboBox and SearchText
	public static void Search(Hashtable<Recipe, File> recipeFileDict, ObservableList<TableData> data, TextField searchText, 
		TableView<TableData> tableView, ArrayList<Recipe> recipes, ComboBox<String> searchComboBox) {
		
		Alert empty = new Alert(AlertType.WARNING);
		if(recipeFileDict.isEmpty()){
    		empty.setTitle("No Results");
    		empty.setContentText("The Recipe Folder contains no useable Recipes!");
    		empty.showAndWait();
    	}else{
    		for(Recipe recipe : recipes){
    			File file = recipeFileDict.get(recipe);
    			TableData nData;
    			switch(searchComboBox.getValue()){
    				case "Title":
    					nData = titleSearch(recipe, file, searchText.getText());
    					if(nData != null)
    					data.add(nData);
    					break;
    				case "Author":
    					nData = authorSearch(recipe, file, searchText.getText());
    					if(nData != null)
    					data.add(nData);
    					break;
    				case "Cuisine":
    					nData = cuisineSearch(recipe, file, searchText.getText());
    					if(nData != null)
    					data.add(nData);
    					break;
    				case "Category":
    					nData = categorySearch(recipe, file, searchText.getText());
    					if(nData != null)
    					data.add(nData);
    					break;
    				case "Favorite":
    					nData = favoriteSearch(recipe, file, searchText.getText());
    					if(nData != null)
    					data.add(nData);
    					break;
    			}
    		}
			tableView.setItems(data);
			tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			//If no Results from Search
			if(data.isEmpty()){
				empty.setTitle("No Results");
	    		empty.setContentText("No Results for that search.");
	    		empty.showAndWait();
			}
    	}
    }

	private static TableData favoriteSearch(Recipe recipe, File file, String string) {
		if(recipe.name.toLowerCase().contains(string.toLowerCase()) && recipe.favorite == true){
			LocalDate date = getLocalDate(file);
			TableData nData = new TableData(recipe.name, recipe.author, recipe.category.toString(), recipe.cuisine, file.getAbsolutePath(), date, recipe);
			return nData;
		}
		return null;
	}

	private static TableData categorySearch(Recipe recipe, File file, String string) {
		if(recipe.category.toString().toLowerCase().contains(string.toLowerCase())){
			LocalDate date = getLocalDate(file);
			TableData nData = new TableData(recipe.name, recipe.author, recipe.category.toString(), recipe.cuisine, file.getAbsolutePath(), date, recipe);
			return nData;
		}
		return null;
	}

	private static TableData cuisineSearch(Recipe recipe, File file, String string) {
		if(recipe.cuisine.toLowerCase().contains(string.toLowerCase())){
			LocalDate date = getLocalDate(file);
			TableData nData = new TableData(recipe.name, recipe.author, recipe.category.toString(), recipe.cuisine, file.getAbsolutePath(), date, recipe);
			return nData;
		}
		return null;
	}

	private static TableData authorSearch(Recipe recipe, File file, String string) {
		if(recipe.author.toLowerCase().contains(string.toLowerCase())){
			LocalDate date = getLocalDate(file);
			TableData nData = new TableData(recipe.name, recipe.author, recipe.category.toString(), recipe.cuisine, file.getAbsolutePath(), date, recipe);
			return nData;
		}
		return null;
	}

	private static TableData titleSearch(Recipe recipe, File file, String string) {
		if(recipe.name.toLowerCase().contains(string.toLowerCase())){
			LocalDate date = getLocalDate(file);
			TableData nData = new TableData(recipe.name, recipe.author, recipe.category.toString(), recipe.cuisine, file.getAbsolutePath(), date, recipe);
			return nData;
		}
		return null;
	}

	//Gets the Date the File was Created on.
	private static LocalDate getLocalDate(File file){
	    LocalDate date = null;
		try {
			Path path = Paths.get(file.getAbsolutePath());
		    FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
		    String time = creationTime.toString();
		    String year = time.substring(0, 4);
		    String month = time.substring(5, 7);
		    String dayOfMonth = time.substring(8, 10);
		    System.out.println(year  + " " + month + " " + dayOfMonth);
		    date = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(month)), Integer.parseInt(dayOfMonth));
		} catch (IOException ex) {
		    System.out.println("Local Date Error!");
		}
		return date;
	}
	
	//Test Recipe Saving and Printing to Console.
	public static void TestButton() {
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
		nutrition.setTotalServ(4);
		nutrition.setAmountServ(4);
		nutrition.setCalories(250);
		nutrition.setTotalFat(1.0);
		nutrition.setSatFat(2.0);
		nutrition.setCholest(3.0);
		nutrition.setSodium(4.2);
		nutrition.setTotalCarb(6.5);
		nutrition.setDietFiber(18.54);
		nutrition.setSugar(20);
		nutrition.setProtien(9001);
		nutrition.setVitaminA(100);
		nutrition.setVitaminC(777);
		recipe.nutrition = nutrition;
		RecipeHandler recipeHandler = new RecipeHandler();
		File file = null;
		try {
			file = recipeHandler.save(recipe);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Recipe newRecipe = new Recipe();
		try {
			newRecipe = recipeHandler.load(file);
			newRecipe.printRecipe();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//Gets the Recipe from the Search Table
	public static Recipe GetRecipeFromRowData(TableData rowData) {
		Recipe recipe = rowData.getRecipe();
		//recipe.printRecipe(); //Print to Console.
		return recipe;
	}
}
