
package system.microservice.endpoint;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import system.microservice.application.service.AccountAPI;
import system.microservice.library.*;

@WebMvcTest(AccountAPI.class)
@TestMethodOrder(OrderAnnotation.class)
public class AccountAPITests {

    private Log log = new Log(AccountAPITests.class.getName(), "API-Account");

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    @DisplayName("Web API: cria uma nova conta.")
    public void createAccount() throws Exception 
    {
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders
                .get("/create/111.111.111-11/123/1234/12345-0")
                .accept(MediaType.ALL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(
                "Account created.")));

        this.log.save(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    @DisplayName("Web API: retorna informações da conta.")
    public void getAccountInformations() throws Exception 
    {
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders
                .get("/informations/111.111.111-11")
                .accept(MediaType.ALL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(
                "111.111.111-11")));

        this.log.save(result.andReturn().getResponse().getContentAsString());
    }
}
