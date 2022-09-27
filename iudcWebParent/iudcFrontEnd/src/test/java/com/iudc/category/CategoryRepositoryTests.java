/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.category;

import com.iudc.category.CategoryRepository;
import com.iudc.common.entity.Category;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTests {
    
    @Autowired private CategoryRepository repo;
    
    
    @Test
    public void testListEnabledCategories(){
    
        List<Category> categories = repo.findAllEnabled();
        categories.forEach(category -> {
        
            System.out.println(category.getName() + " (" + category.isEnabled() + ")");
            
        
        });
    
    }
    
   @Test
    public void testFindCategoryByAlias() {
        String alias = "Etiquetas";
        Category category = repo.findByAliasEnabled(alias);

        assertThat(category).isNotNull();
    }
    
    
    
}
