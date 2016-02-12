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
    public String creationDate=LocalDateTime.now().toString();
    public String lastModified;
    public Category category;
    public List<Tag> tags;

    public Operation(){
    	System.out.println("constructor by default");
    	this.lastModified=LocalDateTime.now().toString();
    }
    
    public Operation(String title, double amount){
    	
    	System.out.println("constructor with args");
    	
    	this.title=title;
    	this.amount=amount;
    	this.creationDate=LocalDateTime.now().toString();
    	this.lastModified=this.creationDate;
    }
    
    public static void add(Operation operation) {
    	System.out.println("add operation"); 
    	
        operations().insert(operation);
    }
    
    public static void add(String title, double amount) {
    	System.out.println("add arguments to create operation");
    	
        operations().insert(new Operation(title,amount));
    }

    public static void delete(Operation operation) {
    	operations().remove(new ObjectId(operation.id));
    } 
    
    public static void update(String id, Operation updatedOperation){
    	System.out.println("update operation with new elements");
    	System.out.println(updatedOperation.toString());
    	updatedOperation.creationDate=null;
    	operations().update(new ObjectId(id)).with(updatedOperation);
    }

	public static void addTag(String id, Tag newTag){
//    	operations().update(new ObjectId(id)).with("{tag:#, lastModified:#}", newTag, LocalDateTime.now().toString());
    }
    
    public static void removeTag(String id, Tag tag){
    	
    }
    
    public static MongoCursor<Operation> all(){
    	return operations().find().as(Operation.class);
    }

    public static Operation findById(String id) {
        return operations().findOne("{_id:#}", new ObjectId(id)).as(Operation.class);
    }
    
    public static Operation findByTitle(String title) {
        return operations().findOne("{title:#}", title).as(Operation.class);
    }
    
    
    @Override
	public String toString() {
		return "Operation [id=" + id + ", title=" + title + ", amount=" + amount + ", creationDate=" + creationDate
				+ ", lastModified=" + lastModified + ", category=" + category + ", tags=" + tags + "]";
	}
}
