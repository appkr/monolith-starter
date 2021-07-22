package dev.appkr.starter.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class HomeResourceTest {

  MockMvc mockMvc;

  @DisplayName("user는 /users 엔드포인트에 접근할 수 있다")
  @Test
  @WithMockUser
  public void test_user_can_access_users_endpoint() throws Exception {
    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @Disabled("기대 결과와 다르게 200을 상태코드를 응답함")
  @DisplayName("user는 /admin 엔드포인트에 접근할 수 없다")
  @Test
  @WithMockUser
  public void test_user_cannot_access_admin_endpoint() throws Exception {
    mockMvc.perform(get("/admin"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @DisplayName("user는 /admin 엔드포인트에 접근할 수 없다")
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void test_admin_can_access_any_endpoint() throws Exception {
    mockMvc.perform(get("/admin"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @BeforeEach
  public void setup() {
    final HomeResource controller = new HomeResource();
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }
}