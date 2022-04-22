package project;

import java.time.LocalDate;
import java.util.ArrayList;

public class Goal {
	
	public ArrayList<String> fitnessGoals;
	public ArrayList<String> nutritionalGoals;
	public LocalDate date;
	
	public Goal(LocalDate date) {
		fitnessGoals = new ArrayList<>();
		nutritionalGoals = new ArrayList<>();
		this.date = date;
	}
	
	public void addFitness(String fitnessGoal) {
		fitnessGoals.add(fitnessGoal);
	}
	
	public void addNutritional(String nutritionalGoal) {
		nutritionalGoals.add(nutritionalGoal);
	}
	
	public ArrayList<String> getFitness() {
		return this.fitnessGoals;
	}
	
	public ArrayList<String> getNutritional() {
		return this.nutritionalGoals;
	}
	
	public boolean equals(LocalDate date) {
		return this.date.equals(date);
	}
	
	public boolean equals(Goal goal) {
		return this.date.equals(goal.date);
	}
	
	public String toString() {
		return this.date.toString();
	}
}
