package models;

import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import uk.co.panaxiom.playjongo.PlayJongo;

public class Budget {

	public static MongoCollection budgets() {
		return PlayJongo.getCollection("budgets");
	}
	
	@MongoId
	@MongoObjectId
	public String id;
	
	public String title;
	List<Operation> operations;
	public double total=assessAmount(operations);
	Budget parentBudget;
	
	public Budget(){};
	public Budget(String title){
		this.title=title;
	};
	
	public Budget(String title, Budget parentBudget){
		this.title=title;
		this.parentBudget=parentBudget;
	};
	
	public static void create(Budget budget){
		budgets().insert(budget);
	}
	
	public static void create(String title){
		budgets().insert(new Budget(title));
	}
	
	public static void create(String title, Budget parentBudget){
		budgets().insert(new Budget(title, parentBudget));
	}
	
	public static void remove(Budget budget){
		budgets().remove(new ObjectId(budget.id));
	}
	
	public static void update(String id, Budget updatedBudget){
		budgets().update(new ObjectId(id)).with(updatedBudget);
	}
	
	public static MongoCursor<Budget> all(){
		return budgets().find().as(Budget.class);
	}
	
	public static Budget findById(String id){
		return budgets().findOne("{_id:#}", new ObjectId(id)).as(Budget.class);
	}
	
	public static MongoCursor<Budget> findByTitle(String title) {
		return budgets().find("{title:#}", title).as(Budget.class);
	}
	
	public static void addOperation(String id, Operation operation){
		budgets().update(new ObjectId(id)).with("{$addToSet:{operations:#}}", operation);
	}
	
	public static void removeOperation(String id, Operation operation){
		budgets().update(new ObjectId(id)).with("{$pull:{operations:#}}",operation);
	}
	
	private double assessAmount(List<Operation> operations){
		double amount=0;
		
		if(operations!=null){
			for(int i=0;i<=operations.size();i++){
				amount=amount+operations.get(i).amount;
			}
		}
		return amount;
	}
	
}
