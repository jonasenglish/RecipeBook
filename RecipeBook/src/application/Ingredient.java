package application;

//This is the class object for containing ingredient information.
public class Ingredient {
	
		public String ingredientName,ingredientAmount;
		
		public Ingredient(String ingredientName, String ingredientAmount) {
			this.ingredientName = ingredientName;
			this.ingredientAmount = ingredientAmount;
		}
		
		public Ingredient(){}

		public void printIngrediant(){
			System.out.println(this.ingredientName + " - " + this.ingredientAmount);
		}
}
