package de.enmacc;

import de.enmacc.data.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventsmaccApplicationTests {

	@Autowired
	private EventRepository data;

	@Test
	public void contextLoads() {
	}

}
