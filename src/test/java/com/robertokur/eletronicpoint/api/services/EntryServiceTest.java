package com.robertokur.eletronicpoint.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.robertokur.eletronicpoint.api.entities.Entry;
import com.robertokur.eletronicpoint.api.repository.EntryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test2")
public class EntryServiceTest {
	@MockBean
	private EntryRepository entryRepository;

	@Autowired
	private EntryService entryService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.entryRepository.save(Mockito.any(Entry.class))).willReturn(new Entry());
	}

	

	@Test
	public void testFindByIdEntry() {
		Optional<Entry> entry= this.entryService.findById(1L);

		assertTrue(entry.isPresent());
	}

	@Test
	public void testInsertEntry() {
		Entry entry = this.entryService.insert(new Entry());

		assertNotNull(entry);
	}
	

}
