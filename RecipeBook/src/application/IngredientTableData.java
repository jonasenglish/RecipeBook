package application;

import javafx.beans.property.SimpleStringProperty;

public class IngredientTableData {
	
	private SimpleStringProperty ingredientName,ingredientAmount;
	private Ingredient ingredient;
	
	public IngredientTableData(String ingrediantName, String ingredientAmount, Ingredient ingredient){
		this.ingredientName = new SimpleStringProperty(ingrediantName);
		this.ingredientAmount = new SimpleStringProperty(ingredientAmount);
		this.ingredient = ingredient;
	}

	public String getIngredientName() {
		return ingredientName.get();
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = new SimpleStringProperty(ingredientName);
	}

	public String getIngredientAmount() {
		return ingredientAmount.get();
	}

	public void setIngredientAmount(String ingredientAmount) {
		this.ingredientAmount = new SimpleStringProperty(ingredientAmount);
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	
}
