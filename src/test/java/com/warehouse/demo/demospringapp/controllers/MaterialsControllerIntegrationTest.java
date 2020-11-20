package com.warehouse.demo.demospringapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.warehouse.demo.demospringapp.error.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MaterialsControllerIntegrationTest {

    @Autowired
    private MaterialsController materialsController;

    @Test
    public void testSearch() throws Exception {
        //Test positive outcome
        String positive_outcome = materialsController.searchMaterialsResult(new ExtendedModelMap(), "Boron");
        assertEquals(positive_outcome, "material-search-found");

        //Test negative outcome
        assertThrows(EntityNotFoundException.class, ()->{
            materialsController.searchMaterialsResult(new ExtendedModelMap(), "ABC");
        });
    }


    
}
