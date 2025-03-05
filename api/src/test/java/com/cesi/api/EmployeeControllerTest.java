package com.cesi.api;

/*import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
*/
import com.jayway.jsonpath.JsonPath;
import java.util.concurrent.atomic.AtomicInteger;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	public MockMvc mockMvc;
       
	@Test
	public void testGetEmployees() throws Exception {
		
		mockMvc.perform(get("/employees")).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName", is("Laurent")));
		
	}
	
    @Test
    public void testGetUnEmployee2() throws Exception {
        System.err.println("/employee/1");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Laurent"));
    }
    
    // CREATE employee at /employee
 
     @Test
    public void testCreateEmployee() throws Exception {
        
              mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"Tenzin\",\"lastName\":\"Dolma\",\"mail\":\"tenzin@gmail.com\",\"password\":\"tenzin\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Tenzin"))
            .andExpect(jsonPath("$.lastName").value("Dolma"))
            .andExpect(jsonPath("$.mail").value("tenzin@gmail.com"))
                .andExpect(jsonPath("$.password").value("tenzin"));
    
   
    }
    //UPDATE employee at /employee/id (PUT)
     @Test
    public void testUpdateEmployee() throws Exception {
        
              mockMvc.perform(put("/employee/7")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"Tenzin\",\"lastName\":\"Dolma\",\"mail\":\"tenzin@gmail.com\",\"password\":\"tenzin\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(7))
            .andExpect(jsonPath("$.firstName").value("Tenzin"))
            .andExpect(jsonPath("$.lastName").value("Dolma"))
            .andExpect(jsonPath("$.mail").value("tenzin@gmail.com"))
            .andExpect(jsonPath("$.password").value("tenzin"));
    

    
    }
     //DELETE employee at /employee/id (DELETE)
     @Test
    public void testCreateAndDeleteEmployee() throws Exception {
                     
    final AtomicInteger savedEmployeeId = new AtomicInteger();
              
    mockMvc.perform(post("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"firstName\":\"Dummy\",\"lastName\":\"Dummy\",\"mail\":\"dummy@gmail.com\",\"password\":\"dummy\"}")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(result -> {
            savedEmployeeId.set(
                JsonPath.parse(result.getResponse().getContentAsString()).read("$.id")
            );
        })
        .andExpect(jsonPath("$.firstName").value("Dummy"));

    System.out.print(this);
    // Delete the employee using the captured ID
    mockMvc.perform(delete("/employee/{id}", savedEmployeeId.get()))
        .andExpect(status().isOk());
    }
}
