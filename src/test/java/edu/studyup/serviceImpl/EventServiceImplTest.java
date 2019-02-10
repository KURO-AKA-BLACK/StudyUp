package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);
	}
		

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	
	//HW 2
	//Test if it throws exception when the length of the name is more than 20
	@Test
	void testUpdateEventName_NameLength_GoodCase_NameLengthGreaterThanTwenty() {
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(1, "This name is too long for the function");
		  });
	}
	@Test
	void testUpdateEventName_NameLength_BadCase_NameLengthGreaterThanTwenty() {
		Assertions.assertDoesNotThrow(() -> {
			eventServiceImpl.updateEventName(1, "This name is too long for the function");
		  });
	}
	//Test if it throws exception when the length of the name is 20
	@Test
	void testUpdateEventName_NameLength_GoodCase_NameLengthEqualsToTwenty() {
		Assertions.assertDoesNotThrow(() -> eventServiceImpl.updateEventName(1, "This is twenty words"));		
	}
	
	@Test 
	void testUpdateEventName_NameLength_BadCase_NameLengthEqualsToTwenty() {
		Assertions.assertThrows(StudyUpException.class, () -> {eventServiceImpl.updateEventName(1, "This is twenty words");});
	}
	//Test if it throws exception when the length of the name is 20
	@Test
	void testUpdateEventName_NameLength_GoodCase_NameLengthLessThanTwenty() {
		Assertions.assertDoesNotThrow(() -> eventServiceImpl.updateEventName(1, "This is ten words"));		
	}
	
	@Test
	void testUpdateEventName_NameLength_BadCase_NameLengthLessThanTwenty() {
		Assertions.assertThrows(StudyUpException.class, () -> {eventServiceImpl.updateEventName(1, "This is ten words");});
	}
	
	//test if future event is active.
	@Test
	void testGetActiveEvent_GoodCase() {
		Calendar myCalendar = new GregorianCalendar(2033, 2, 11);
		Date myDate = myCalendar.getTime();
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		activeEvents.get(0).setDate(myDate);
		for (int i = 0; i < activeEvents.size(); i++) {
			assertTrue(activeEvents.get(i).getDate().compareTo(new Date()) >= 0);
		}
	}

	@Test
	void testGetActiveEvent_BadCase() {
		Calendar myCalendar = new GregorianCalendar(2000, 2, 11);
		Date myDate = myCalendar.getTime();
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		activeEvents.get(0).setDate(myDate);
		for (int i = 0; i < activeEvents.size(); i++) {
			assertTrue(activeEvents.get(i).getDate().compareTo(new Date()) >= 0);
		}
	}
}
