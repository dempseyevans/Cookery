package com.cookery.cookery.Controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookery.cookery.entity.Recipe;
import com.cookery.cookery.service.RecipeGeneratorService;


@Controller
@RequestMapping("/generator")
public class RecipeGeneratorController {

    @Autowired
    RecipeGeneratorService recipeGeneratorService;
    
    //Show the Recipe Generator Page
    @GetMapping
    public String recipeGeneratorForm(@RequestParam(value = "costRange", required=false) Double costRange, @RequestParam(value = "descriptor", required= false) String descriptor, @RequestParam(value="maxCookTime", required=false) Integer maxCookTime, Model model) {
    
        // Check if this is an initial page load
        if (costRange == null && descriptor == null && maxCookTime == null) {
            return "recipeGenerator"; // Return the page without generating a recipe
        }

        try{
            //Generate random recipe using filters
            Recipe recipe = recipeGeneratorService.generateRandomRecipe(costRange, descriptor, maxCookTime);
    
            //Add the recipe to the model after submitting
            model.addAttribute("recipe", recipe);

        } catch (NoSuchElementException e){
            //Error for no found recipes
            model.addAttribute("errorMessage", "No recipes match your criteria");
        }
        return "recipeGenerator";
        
    }
    
}
