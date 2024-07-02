package de.htwsaar.esch.Codeopolis.DomainModel;

import java.util.Arrays;
import java.util.Comparator;

import de.htwsaar.esch.Codeopolis.DomainModel.Harvest.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * The Silo class represents a storage unit for a specific type of grain.
 */
public class Silo implements Serializable, Comparable<Silo> {
    //private Harvest[] stock;
	private PriorityQueue<Harvest> stock = new PriorityQueue<>(Comparator.comparing(Harvest::getDurability));
			
    //-----private LinkedList<Harvest> stock;
    private final int capacity;
    private int fillLevel;

    /**
     * Constructs a Silo object with the specified initial capacity.
     *
     * @param capacity The initial capacity of the silo.
     */
    public Silo(int capacity) {
        this.capacity = capacity;
        //-----this.stock = new LinkedList<>() ;
        this.fillLevel = 0;
    }
    
    /**
     * Copy constructor for the Silo class.
     * Creates a new Silo object as a deep copy of another Silo object.
     * This constructor is used to ensure that each property of the Silo,
     * including mutable objects, is copied and independent of the original object.
     *
     * @param other The Silo object to copy.
     */
    public Silo(Silo other) {
        this.capacity = other.capacity;
        this.fillLevel = other.fillLevel;
       
        //this.stock = new LinkedList<>();
        
        //other.stock.forEach(harvest -> this.stock.addLast(Harvest.createHarvest(harvest)));
        other.stock.forEach(harvest -> this.stock.add(Harvest.createHarvest(harvest)));
        
    }

    /**
     * Stores a harvest in the silo if there is available capacity.
     *
     * @param harvest The harvest to be stored in the silo.
     * @return The amount of grain that could not be stored due to capacity limitations.
     */
    public Harvest store(Harvest harvest) {
    	 // Check if the grain type matches the existing grain in the silo
        if (fillLevel > 0 && stock.peek().getGrainType() != harvest.getGrainType()) {
            throw new IllegalArgumentException("The grain type of the given Harvest does not match the grain type of the silo");
        }
        
        // Check if there is enough space in the silo
        if (fillLevel >= capacity) {
            return harvest; // The silo is already full, cannot be stored
        }
        
        if(fillLevel < capacity) {
	        // Check if the entire harvest can be stored
	        int remainingCapacity = this.capacity - this.fillLevel;
	        if(harvest.getAmount() <= remainingCapacity) {
                stock.add(harvest);
	        	this.fillLevel += harvest.getAmount();
	        	return null;
	        }
	        else {
	        	// Split the harvest and store the remaining amount
	            Harvest remainingHarvest = harvest.split(remainingCapacity);
                stock.add(remainingHarvest);
	            this.fillLevel += remainingHarvest.getAmount();
	            return harvest; // Return the surplus amount
	        }
        }
        else {
            // Depot is full, return the amount of grain that could not be stored
            return harvest;
        }
    }
    
    /**
     * Empties the silo by removing all stored harvests and returning them.
     * 
     * @return An array containing all the removed harvests from the silo.
     *         If the silo is empty, an empty array is returned.
     */
    public Harvest[] emptySilo() {
        if (stock.isEmpty()) {
            return null;
        }
        else {
            Harvest[] removedHarvests = new Harvest[stock.size()];
            Iterator<Harvest> it = stock.iterator();
            int i = 0;
            while (it.hasNext()) {
                removedHarvests[i++] = it.next();
            }
            stock.clear();
            fillLevel = 0;
            return removedHarvests;
        }
    }

    /**
     * Extends the capacity of the stock array.
     */
    private void extendStock() {
      /*  int newCapacity = capacity * 2;
        Harvest[] newStock = new Harvest[newCapacity];
        for (int i = 0; i < stock.length; i++) {
  1          newStock[i] = stock[i];
        }
        stock = newStock;*/
    }
    

    /**
     * Takes out a specified amount of grain from the silo.
     *
     * @param amount The amount of grain to be taken out.
     * @return The actual amount of grain taken out from the silo.
     */
    public int takeOut(int amount) {
        int takenAmount = 0;
        Iterator<Harvest> it = stock.iterator();
        while (it.hasNext() && amount > 0) {
            Harvest currentHarvest = it.next();
            int taken = currentHarvest.remove(amount);
            amount -= taken;
            takenAmount += taken;
            if (currentHarvest.getAmount() == 0) {
                it.remove();
            }
        }

        this.fillLevel -= takenAmount;
        return takenAmount;
    }

    /**
     * Gets the current fill level of the silo.
     *
     * @return The number of harvests currently stored in the silo.
     */
    public int getFillLevel() {
    	return this.fillLevel;
    }

    /**
     * Gets the capacity of the silo.
     *
     * @return The maximum number of harvests the silo can store.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the grain type stored in the silo.
     *
     * @return A string representation of the grain type.
     */
    public Game.GrainType getGrainType() {
        // Assuming each silo stores only one type of grain, we can retrieve the grain type from the first stored harvest
        if (fillLevel > 0 && stock.peek() != null) {
            return stock.peek().getGrainType();
        } 
        else {
            return null; 
        }
    }
    
    /**
     * Retrieves the number of harvests currently stored in the silo.
     *
     * @return The number of harvests stored in the silo.
     */
    public int getHarvestCount() {
        return stock.size();
    }
    
    /**
     * Simulates the decay of grain in all harvests stored in the silo over time.
     *
     * @param currentYear The current year used to calculate the decay.
     * @return The total amount of grain that decayed in all harvests in the silo.
     */
    public int decay(int currentYear) {
    	class Vars {
    		int totalDecayedAmount = 0;
    	}
    	
    	final Vars vars = new Vars();
    	
    	stock.forEach(harvest -> vars.totalDecayedAmount += harvest.decay(currentYear));
    	
        fillLevel -= vars.totalDecayedAmount;
        
        return vars.totalDecayedAmount;
    }

    @Override
    public int compareTo(Silo other) {
        return this.fillLevel - other.fillLevel;
    }
}

