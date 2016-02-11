package models;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
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
    public double amount;
    public DateTime creationDate;
    public DateTime lastModified;
    public Category category;
    public List<Tag> tags;

    public Operation(){}
    
    public Operation(String title, double amount){
    	this.title=title;
    	this.amount=amount;
    	this.creationDate=new DateTime();
    	this.lastModified=this.creationDate;
    }
    
    public static void add(Operation operation) {
        operations().insert(operation);
    }
    
    public static void add(String title, double amount) {
        operations().insert(new Operation(title,amount));
    }

    public static void delete(Operation operation) {
    	operations().remove(new ObjectId(operation.id));
    }
    
    public static void updateTitle(String id, String newTitle){
    	operations().update(new ObjectId(id)).with("{title:#, lastModified:#}", newTitle, new DateTime());
    }
    
    public static void updateAmount(String id, double newAmount){
    	operations().update(new ObjectId(id)).with("{amount:#, lastModified:#}", newAmount, new DateTime());
    }
    
    public static void updateCategory(String id, Category newCategory){
    	operations().update(new ObjectId(id)).with("{category:#, lastModified:#}", newCategory, new DateTime());
    }
    
    public static void addTag(String id, Tag newTag){
//    	operations().update(new ObjectId(id)).with("{tag:#, lastModified:#}", newTag, new DateTime());
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
}
