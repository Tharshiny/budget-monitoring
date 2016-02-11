package controllers;

import models.Category;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CategoryController extends Controller{

static Form<Category> categoryForm=Form.form(Category.class);
	
	public Result create(){
		Form<Category> filledForm=categoryForm.bindFromRequest();
		Result result=null;
		if(filledForm.hasErrors()){
			result=badRequest();
		}else{
			Category.create(filledForm.get());
			result=ok(Json.toJson(Category.all()));
		}	
		return result;
	}
	
	public Result listAll(){
		return ok(Json.toJson(Category.all()));
	}
	
	public Result display(String id){

		Category category=Category.findById(id);
		
		Result result=null;
		if(category == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(category));
		}
		return result;
	}
	
	public Result delete(String id){
		Category category=Category.findById(id);
		
		Result result=null;
		if(category == null){
			result=notFound();
		}else{
			Category.delete(category);
			result=ok(Json.toJson("deleted"));
		}
		return result;
	}
	
}
