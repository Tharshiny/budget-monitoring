package models;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import uk.co.panaxiom.playjongo.PlayJongo;

public class Tag {
	public static MongoCollection tags() {
        return PlayJongo.getCollection("tags");
    }

    @MongoId
    @MongoObjectId
    public String id;

    public String title;
    
    public Tag(){}
    
    public Tag (String title){
    	this.title=title;
    }
    
    public static void create(Tag tag){
    	tags().insert(tag);
    }
    
    public static void create(String title){
    	tags().insert(new Tag(title));
    }
    
    public static void delete(Tag tag){
    	tags().remove(new ObjectId(tag.id));
    }
    
    public static MongoCursor<Tag> all(){
    	return tags().find().as(Tag.class);
    }
    
    public static Tag findById(String id) {
        return tags().findOne("{_id:#}", new ObjectId(id)).as(Tag.class);
    }
}
