//package com.yk.logistic.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.yk.logistic.controller.login.LoginController;
//import com.yk.logistic.service.member.MemberService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class LoginControllerTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private MemberService memberService;
//
//    @InjectMocks
//    private LoginController loginController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
//    }
//
//    @DisplayName("로그인 성공 테스트")
//    @Test
//    public void testLoginSuccess() throws Exception {
//        // given
//        String username = "user";
//        String password = "password";
//
//        // when
//        when(memberService.login(username, password)).thenReturn(true);
//
//        // MockMvc를 사용하여 컨트롤러 테스트
//        ResultActions result = mockMvc.perform(post("/login")
//                .param("username", username)
//                .param("password", password));
//
//        // then
//        result.andExpect(status().isOk());
//              //.andExpect(content().string("Login successful"));
//    }
//    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
//    @Test
//    public void testLoginFailureWrongPassword() throws Exception {
//        // given
//        String username = "user";
//        String password = "wrongpassword";
//
//        // when
//        when(memberService.login(username, password)).thenReturn(false);
//
//        // MockMvc를 사용하여 컨트롤러 테스트
//        ResultActions result = mockMvc.perform(post("/login")
//                .param("username", username)
//                .param("password", password));
//
//        // then
//        result.andExpect(status().isOk());
//              //.andExpect(content().string("Login failed"));
//    }
//
//    @DisplayName("로그인 실패 테스트 - 사용자 없음")
//    @Test
//    public void testLoginFailureUserNotFound() throws Exception {
//        // given
//        String username = "nonexistentuser";
//        String password = "password";
//
//        // when
//        when(memberService.login(username, password)).thenReturn(false);
//
//        // MockMvc를 사용하여 컨트롤러 테스트
//        ResultActions result = mockMvc.perform(post("/login")
//                .param("username", username)
//                .param("password", password));
//
//        // then
//        result.andExpect(status().isOk());
//              //.andExpect(content().string("Login failed"));
//    }
//}