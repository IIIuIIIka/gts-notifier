package com.gts.notifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gts.notifier.controller.UserController;
import com.gts.notifier.data.UserName;
import com.gts.notifier.data.UserTimeSlot;
import com.gts.notifier.dto.TimeSlotDTO;
import com.gts.notifier.dto.UserDTO;
import com.gts.notifier.model.User;
import com.gts.notifier.service.UserService;

@WebMvcTest(controllers = UserController.class)
class NotifierApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserService userService;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void givenUserDTO_whenCreateUser_thenReturnSaved() throws Exception {
		
		TimeSlotDTO slot = new TimeSlotDTO();
		slot.setWeekDay("SATURDAY");
		slot.setStartTime("12:00");
		slot.setEndTime("15:35");
		
		UserDTO user = new UserDTO();
		user.setFirstName("Максим");
		user.setLastName("Петров");
		user.setTimeSlots( Arrays.asList(slot) );
		
		given( userService.create( any(User.class) ) )
				.willAnswer( (invocation) -> invocation.getArgument(0) );
		
		ResultActions response = 
				mockMvc.perform( post( "/api/v1/users" )
					   .contentType( MediaType.APPLICATION_JSON )
					   .content( mapper.writeValueAsString(user) ) );
		
		response.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.firstName", is( user.getFirstName() ) ) )
				.andExpect( jsonPath( "$.lastName", is( user.getLastName() ) ) )
				.andExpect( jsonPath( "$.timeSlots[0].weekDay", is( user.getTimeSlots().get(0).getWeekDay() ) ) );
	}
	
	@Test
	public void givenUserId_whenGetUserById_thenReturnUser() throws Exception {
		
		Long userId = 1L;
		User user = User.builder()
						.name(new UserName("Иван", "Трубин"))
						.timeSlots(Arrays.asList(new UserTimeSlot(DayOfWeek.THURSDAY, 
								LocalTime.of(7, 15), 
								LocalTime.of(20, 20))))
						.build();
		given( userService.findById(userId) ).willReturn( Optional.of(user) );
		
		ResultActions response = mockMvc.perform(get("/api/v1/users/{id}", userId));
		
		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(user.getName().getFirstName())))
				.andExpect(jsonPath("$.lastName", is(user.getName().getLastName())))
				.andExpect(jsonPath("$.timeSlots[0].weekDay", is(user.getTimeSlots().get(0).getWeekDay().toString())));
		
	}
	
	@Test
	public void givenWrongUserId_whenGetUserById_thenReturnUser() throws Exception {
		
		Long userId = 1L;
		User user = User.builder()
						.name(new UserName("Иван", "Трубин"))
						.timeSlots(Arrays.asList(new UserTimeSlot(DayOfWeek.THURSDAY, 
								LocalTime.of(7, 15), 
								LocalTime.of(20, 20))))
						.build();
		given( userService.findById(userId) ).willReturn( Optional.empty() );
		
		ResultActions response = mockMvc.perform(get("/api/v1/users/{id}", userId));
		
		response.andDo(print())
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void givenWrongUserId_whenUpdatingUser_thenReturnUser() throws Exception {
		
		Long userId = 1L;
		
		TimeSlotDTO slot = new TimeSlotDTO();
		slot.setWeekDay("SATURDAY");
		slot.setStartTime("12:00");
		slot.setEndTime("15:35");
		
		UserDTO user = new UserDTO();
		user.setFirstName("Максим");
		user.setLastName("Петров");
		user.setTimeSlots( Arrays.asList(slot) );
		
		given( userService.update( any(User.class) ) )
				.willAnswer( (invocation) -> invocation.getArgument(0) );
		given( userService.findById(userId) ).willReturn( Optional.empty() );
		
		ResultActions response = 
				mockMvc.perform(put( "/api/v1/users/{id}", userId )
					   .contentType( MediaType.APPLICATION_JSON )
					   .content( mapper.writeValueAsString(user) ) );
		
		response.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenUserId_whenDeletingUser_thenReturnOk() throws Exception {
		
		Long userId = 2L;
		User user = User.builder()
				.name(new UserName("Иван", "Трубин"))
				.timeSlots(Arrays.asList(new UserTimeSlot(DayOfWeek.THURSDAY, 
						LocalTime.of(7, 15), 
						LocalTime.of(20, 20))))
				.build();
		given(userService.findById(userId)).willReturn(Optional.of(user));
		willDoNothing().given( userService).delete(userId);
		
		ResultActions response = mockMvc.perform(delete("/api/v1/users/{id}", userId));
		
		response.andDo(print())
				.andExpect(status().isOk());
	}
}
