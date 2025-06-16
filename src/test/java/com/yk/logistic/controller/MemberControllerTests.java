//package com.yk.logistic.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yk.logistic.dto.member.ChangeMemberRequest;
//import com.yk.logistic.dto.member.JoinMemberRequest;
//import com.yk.logistic.service.member.MemberService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class MemberControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private MemberService memberService;
//
//    @BeforeEach
//    public void setUp() {
//        // 필요 시 추가 설정
//    }
//
//    @DisplayName("회원 가입에 성공한다.")
//    @Test
//    public void testJoin() throws Exception {
//        // given
//        final String url = "/member/join";
//        final String name = "name";
//        final String email = "email@example.com";
//        final String password = "password";
//        final JoinMemberRequest userRequest = new JoinMemberRequest(name, email, password);
//
//        // 객체 json으로 직렬화
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        // when
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType("application/json")
//                .content(requestBody));
//
//        // then
//        result.andExpect(status().isOk());
//    }
//
//    @DisplayName("패스워드 변경에 성공한다.")
//    @Test
//    public void testChangePw() throws Exception {
//        // given
//        final String url = "/member/changePw";
//        final String email = "email@example.com";
//        final String password = "newpassword";
//        final ChangeMemberRequest userRequest = new ChangeMemberRequest(email, password);
//
//        // 객체 json으로 직렬화
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        // when
//        ResultActions result = mockMvc.perform(put(url)
//                .contentType("application/json")
//                .content(requestBody));
//
//        // then
//        result.andExpect(status().isOk());
//    }
//}