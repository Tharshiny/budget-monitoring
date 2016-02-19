package models;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.LocalDateTime;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;


import uk.co.panaxiom.playjongo.PlayJongo;

public class Operation {

	public static MongoCollection operations() {
		return PlayJongo.getCollection("operations");
	}

	@MongoId
	@MongoObjectId
	public String id;

	public String title;
	public Double amount;
	public String creationDate;
	public String lastModified=LocalDateTime.now().toString();
	public Category category;
	public List<Tag> tags;

	public Operation(){
		System.out.println("constructor by default");
	}

	public Operation(String title, double amount){

		System.out.println("constructor with args");

		this.title=title;
		this.amount=amount;
		this.lastModified=LocalDateTime.now().toString();
	}

	public static void add(Operation operation) {  	
		operation.creationDate=LocalDateTime.now().toString();
		operations().insert(operation);
	}

	public static void add(String title, double amount) {   	
		operations().insert(new Operation(title,amount));
	}

	public static void delete(Operation operation) {
		operations().remove(new ObjectId(operation.id));
	} 

	public static void update(String id, Operation updatedOperation){
		operations().update(new ObjectId(id)).with(updatedOperation);
	}

	public static void addTag(String id, String tag){
//    	operations().update(new ObjectId(id)).with("{$addToSet:{tags:#}, lastModified:#}", new Tag(tag), LocalDateTime.now().toString());	
		Tag.create(tag);
		operations().update(new ObjectId(id)).with("{$addToSet:{tags:#}}", Tag.findByTitle(tag));
//		operations().update(new ObjectId(id)).with("{lastModified:#}", LocalDateTime.now().toString());
	}

	public static void removeTag(String id, Tag tag){
		operations().update(new ObjectId(id)).with("{$pull:{tags:#}}", tag);
	}

	public static boolean isTagAdded(String id, String tag){
		boolean isAdded=false;

		Operation operation=findById(id);
		
		if(operation.tags!=null){
			int i=0;
			boolean next=true;
			while(i<operation.tags.size()&&next){
				if(operation.tags.get(i).title.equals(tag)){
					next=false;
					isAdded=true;
				}   	
				i=i+1;
			}
		}
		return isAdded;
	}

	public static MongoCursor<Operation> all(){
		return operations().find().as(Operation.class);
	}

	public static Operation findById(String id) {
		return operations().findOne("{_id:#}", new ObjectId(id)).as(Operation.class);
	}

	public static MongoCursor<Operation> findByTitle(String title) {
		return operations().find("{title:#}", title).as(Operation.class);
	}


	@Override
	public String toString() {
		return "Operation [id=" + id + ", title=" + title + ", amount=" + amount + ", creationDate=" + creationDate
				+ ", lastModified=" + lastModified + ", category=" + category + ", tags=" + tags + "]";
	}
}
