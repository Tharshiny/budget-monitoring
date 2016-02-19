package controllers;

import models.Budget;
import models.Operation;
import models.Tag;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class BudgetController extends Controller{

	static Form<Budget> budgetForm=Form.form(Budget.class);

	public Result add(){
		Form<Budget> filledForm=budgetForm.bindFromRequest();
		Result result=null;
		if(filledForm.hasErrors()){
			result=badRequest();
		}else{
			Budget.create(filledForm.get());
			result=ok(Json.toJson(Budget.all()));
		}	
		return result;
	}

	public Result listAll(){
		return ok(Json.toJson(Budget.all()));
	}

	public Result display(String id){

		Budget budget=Budget.findById(id);

		Result result=null;
		if(budget == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(budget));
		}
		return result;
	}

	public Result displayTitle(String title){

		Result result=null;
		if(Budget.findByTitle(title) == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(Budget.findByTitle(title)));
		}
		return result;
	}

	public Result delete(String id){
		Budget budget=Budget.findById(id);

		Result result=null;
		if(budget == null){
			result=notFound();
		}else{
			Budget.remove(budget);
			result=ok(Json.toJson("deleted"));
		}
		return result;

	}

	public Result update(String id){
		Result result=null;
		Form<Budget> filledForm=budgetForm.bindFromRequest();

		if (filledForm.hasErrors()) {			
			result = badRequest();
		} else {
			Budget.update(id, filledForm.get());
			result =  display(id);
		}

		return result;
	}
	public Result addOperation(String id, String operationId){
		Budget.addOperation(id, Operation.findById(operationId));
		return display(id);
	}

	public Result removeOperation(String id, String operationId){
		Budget.removeOperation(id, Operation.findById(operationId));
		return display(id);
	}

}
