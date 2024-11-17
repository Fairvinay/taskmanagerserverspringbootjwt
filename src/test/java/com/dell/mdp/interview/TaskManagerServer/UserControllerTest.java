package com.dell.mdp.interview.TaskManagerServer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dell.mdp.interview.TaskManagerServer.service.UserInfoDetails;
import com.dell.mdp.interview.TaskManagerServer.service.UserInfoService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;
    @MockBean
    private UserInfoDetails userInfoDetails;
    // Test POST /todos endpoint
    @Test
    public void testGenerateToken() throws Exception {
    	UserDetails newUser  =mock(UserInfoDetails.class);//userInfoDetails;
    	// Set up mock behavior
        when(newUser.getUsername()).thenReturn("user");
        when(newUser.getPassword()).thenReturn("password");
        Mockito.when(userInfoService.loadUserByUsername("user")).thenReturn(newUser);

        mockMvc.perform(post("/generateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"eif82jf8dvj2fjvif\"}")) //\"title\":\"Living room, Kitchen\"
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.token").value("eif82jf8dvj2fjvif"));
               //.andExpect(jsonPath("$.title").value("Living room, Kitchen"));
    }

}
