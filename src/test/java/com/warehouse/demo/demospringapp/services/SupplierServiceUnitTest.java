package com.warehouse.demo.demospringapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.warehouse.demo.demospringapp.auth.AuthGroupRepository;
import com.warehouse.demo.demospringapp.domains.Supplier;
import com.warehouse.demo.demospringapp.repos.MaterialRepository;
import com.warehouse.demo.demospringapp.repos.SupplierRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class SupplierServiceUnitTest {
    
    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private AuthGroupRepository authRepo;

    @Mock
    private MaterialRepository matRepo;

    @InjectMocks
    private SupplierService supplierService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSupplierCreation() {
        Supplier testSupplier = new Supplier("John", "Doe", "jdoe@email.com", "password");
        // testSupplier.setFirstname("John");
        // testSupplier.setLastname("Doe");
        // testSupplier.setEmail("jdoe@email.com");
        // testSupplier.setPassword("password");

        System.out.println("\n " + testSupplier.toString());

        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);

        Supplier newSupplier = supplierService.addSupplier(testSupplier);

        System.out.println("Test: \n " + newSupplier.toString());

        assertNotNull(newSupplier.getId());
        assertEquals("John", newSupplier.getFirstname());
        assertEquals("Doe", newSupplier.getLastname());
        assertEquals("jdoe@email.com", newSupplier.getEmail());
        assertEquals(newSupplier.getUsername(), newSupplier.getEmail());
        assertEquals("password", newSupplier.getPassword());       

    }

    // @Test
    // public void testAddMaterial() {
    //     Supplier testSupplier = new Supplier();
    //     Material testMaterial = new Material();
    //     testMaterial.setName("testMaterial");
    //     testMaterial.setDetails("For testing");
    //     testMaterial.setUnits(0);

    //     when(supplierService.addMaterialToList(any(Material.class, Long.class))).thenReturn(testMaterial);
    // }

    
}
