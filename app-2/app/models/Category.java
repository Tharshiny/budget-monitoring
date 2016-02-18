package models;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import uk.co.panaxiom.playjongo.PlayJongo;

public class Category {

	public static MongoCollection categories() {
        return PlayJongo.getCollection("category");
    }

    @MongoId
    @MongoObjectId
    public String id;

    public String title;
    
    public Category(){}
    
    public Category (String title){
    	this.title=title;
    }
    
    public static void create(Category category){
    	category.title=category.title.toLowerCase();
    	if (!exist(category.title)) categories().insert(category);
    }

	public static void create(String title){
    	title=title.toLowerCase();
    	if (!exist(title)) categories().insert(new Category(title));
    }
    
    public static MongoCursor<Category> all(){
    	return categories().find().as(Category.class);
    }
    
    public static Category findById(String id) {
        return categories().findOne("{_id:#}", new ObjectId(id)).as(Category.class);
    }
    
    public static Category findByTitle(String title) {
        return categories().findOne("{title:#}", title).as(Category.class);
    }
    
    private static boolean exist(String title){
    	boolean exist=false;
    	if (findByTitle(title)!=null) exist=true;
    	return exist;
    }
    
    public static void delete(Category category){
    	categories().remove(new ObjectId(category.id));
    }
    
    @Override
	public String toString() {
		return "Category [id=" + id + ", title=" + title + "]";
	}
}
