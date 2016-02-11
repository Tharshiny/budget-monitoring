package controllers;

import models.Category;
import models.Operation;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class OperationController extends Controller{

	static Form<Operation> operationForm=Form.form(Operation.class);
	
	public Result add(){
		Form<Operation> filledForm=operationForm.bindFromRequest();
		Result result=null;
		if(filledForm.hasErrors()){
			result=badRequest();
		}else{
			Operation.add(filledForm.get());
			result=ok(Json.toJson(Operation.all()));
		}	
		return result;
	}
	
	public Result listAll(){
		return ok(Json.toJson(Operation.all()));
	}
	
	public Result display(String id){

		Operation operation=Operation.findById(id);
		
		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(operation));
		}
		return result;
	}
			
	public Result displaytitle(String title){
		Operation operation=Operation.findByTitle(title);
		
		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(operation));
		}
		return result;
	}
	
	public Result delete(String id){
		Operation operation=Operation.findById(id);
		
		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			Operation.delete(operation);
			result=ok(Json.toJson("deleted"));
		}
		return result;
		
	}
	
	public Result updateTitle(String id, String title){
		Operation.updateTitle(id, title);
		return display(id);
	}
	
	public Result updateAmount(String id, String amount){
		Double newAmount=Double.parseDouble(amount);
		Operation.updateAmount(id, newAmount);
		return display(id);
	}
	
	public Result addTag(String id, String title){
		return display(id);
		//TODO
	}
	
	public Result updateCategory(String id, String categoryTitle){
		Operation.updateCategory(id, new Category(categoryTitle));
		return display(id);
	}
			
	
}
