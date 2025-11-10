/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Pet age calculation functionality.
 *
 * @author PetClinic Team
 */
class PetAgeTests {

	@Test
	void testGetAgeWithNullBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(null);
		
		assertThat(pet.getAge()).isEqualTo("Unknown");
	}

	@Test
	void testGetAgeWithFutureBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().plusDays(10));
		
		assertThat(pet.getAge()).isEqualTo("Not yet born");
	}

	@Test
	void testGetAgeWithBirthDateToday() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now());
		
		assertThat(pet.getAge()).isEqualTo("Less than 1 Month");
	}

	@Test
	void testGetAgeWithBirthDateLessThanOneMonthAgo() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusDays(15));
		
		assertThat(pet.getAge()).isEqualTo("Less than 1 Month");
	}

	@Test
	void testGetAgeWithExactlyOneMonthOld() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusMonths(1));
		
		assertThat(pet.getAge()).isEqualTo("1 Month");
	}

	@Test
	void testGetAgeWithMultipleMonthsNoYears() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusMonths(5));
		
		assertThat(pet.getAge()).isEqualTo("5 Months");
	}

	@Test
	void testGetAgeWithExactlyOneYearOld() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(1));
		
		assertThat(pet.getAge()).isEqualTo("1 Year");
	}

	@Test
	void testGetAgeWithMultipleYearsNoMonths() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(3));
		
		assertThat(pet.getAge()).isEqualTo("3 Years");
	}

	@Test
	void testGetAgeWithYearsAndMonths() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(2).minusMonths(6));
		
		assertThat(pet.getAge()).isEqualTo("2 Years 6 Months");
	}

	@Test
	void testGetAgeWithOneYearAndOneMonth() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(1).minusMonths(1));
		
		assertThat(pet.getAge()).isEqualTo("1 Year 1 Month");
	}

	@Test
	void testGetAgeWithSpecificDate() {
		Pet pet = new Pet();
		// Set birth date to a specific past date for predictable testing
		pet.setBirthDate(LocalDate.of(2020, 5, 15));
		
		String age = pet.getAge();
		// Age should contain "Years" and possibly "Months"
		assertThat(age).matches("\\d+ Years?(\\s+\\d+ Months?)?");
	}

	@Test
	void testGetAgeWithLeapYearBirthDate() {
		Pet pet = new Pet();
		// Born on leap day
		pet.setBirthDate(LocalDate.of(2020, 2, 29));
		
		String age = pet.getAge();
		// Should calculate age correctly even with leap year
		assertThat(age).isNotNull();
		assertThat(age).doesNotContain("Unknown");
		assertThat(age).doesNotContain("Not yet born");
	}

	@Test
	void testGetAgeWithOldPet() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(15).minusMonths(3));
		
		assertThat(pet.getAge()).isEqualTo("15 Years 3 Months");
	}

	@Test
	void testGetAgeFormatConsistency() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(4).minusMonths(7));
		
		String age = pet.getAge();
		// Verify format matches expected pattern
		assertThat(age).matches("\\d+ Years \\d+ Months?");
	}

	@Test
	void testGetAgeWithElevenMonths() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusMonths(11));
		
		assertThat(pet.getAge()).isEqualTo("11 Months");
	}

	@Test
	void testGetAgeDoesNotModifyPet() {
		Pet pet = new Pet();
		LocalDate originalDate = LocalDate.now().minusYears(2);
		pet.setBirthDate(originalDate);
		
		// Call getAge multiple times
		pet.getAge();
		pet.getAge();
		
		// Verify birth date hasn't changed
		assertThat(pet.getBirthDate()).isEqualTo(originalDate);
	}

}
