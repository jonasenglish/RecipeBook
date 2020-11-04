package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

public class TableData {

	private SimpleStringProperty title, author, category, cuisine, location;
	private LocalDate date;
	private Recipe recipe;
	
	public TableData(String title, String author, String category, String cuisine, String location, LocalDate date, Recipe recipe){
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.category = new SimpleStringProperty(category);
		this.cuisine = new SimpleStringProperty(cuisine);
		this.location = new SimpleStringProperty(location);
		this.date = date;
		this.recipe = recipe;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(SimpleStringProperty author) {
		this.author = author;
	}

	public String getCategory() {
		return category.get();
	}

	public void setCategory(SimpleStringProperty category) {
		this.category = category;
	}

	public String getCuisine() {
		return cuisine.get();
	}

	public void setCuisine(SimpleStringProperty cuisine) {
		this.cuisine = cuisine;
	}

	public String getLocation() {
		return location.get();
	}

	public void setLocation(SimpleStringProperty location) {
		this.location = location;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	
}
