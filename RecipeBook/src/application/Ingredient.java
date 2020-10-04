package application;

//This is the class object for containing ingredient information.
public class Ingredient {
		public String ingredientName;
		public String ingredientAmount;
		
		public void printIngrediant(){
			System.out.println(this.ingredientName + " - " + this.ingredientAmount);
		}
}
