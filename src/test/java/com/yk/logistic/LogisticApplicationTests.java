package com.yk.logistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yk.logistic.dto.ChangeMemberRequest;
import com.yk.logistic.dto.JoinMemberRequest;
import com.yk.logistic.repository.MemberRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LogisticApplicationTests {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	MemberRepository memberRepository;
	

	
	@BeforeEach //테스트 실행 전 실행하는 메서드
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
		memberRepository.deleteAll();
	}
	
	
	@DisplayName("회원 가입에 성공한다.")
	@Test
    public void testJoin() throws Exception {
		
		//given
		final String url =  "/member/join";
        final String name = "name";
        final String email = "email";
        final String password = "password";
        final JoinMemberRequest userRequest = new JoinMemberRequest(name,email,password);
        
        //객체 json으로 직렬화
        final String RequestBody = objectMapper.writeValueAsString(userRequest);

        //when
        ResultActions result = mockMvc.perform(post(url)
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(objectMapper.writeValueAsString(RequestBody)));
                //.andExpect(status().isOk());
      
		//then
        result.andExpect(status().isOk());		
    }
	
	@DisplayName("패스워드 변경에 성공한다.")
	@Test
    public void testChangePw() throws Exception {
		
		//given
		final String url =  "/member/changePw";
        final String email = "email";
        final String password = "newpassword";
        final ChangeMemberRequest userRequest = new ChangeMemberRequest(email,password);
        
        //객체 json으로 직렬화
        final String RequestBody = objectMapper.writeValueAsString(userRequest);

        //when
        ResultActions result = mockMvc.perform(put(url)
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(objectMapper.writeValueAsString(RequestBody)));
               // .andExpect(status().isOk());
        		
        //then
        result.andExpect(status().isOk());
        		
    }
	

}
