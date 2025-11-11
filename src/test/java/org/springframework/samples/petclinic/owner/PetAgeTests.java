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

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Pet age calculation functionality.
 *
 * @author PetClinic Team
 */
class PetAgeTests {

	@Test
	void testNewbornPetAge() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now());
		assertThat(pet.getAge()).isEqualTo("Less than 1 year old");
	}

	@Test
	void testOneYearOldPet() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(1));
		assertThat(pet.getAge()).isEqualTo("1 year old");
	}

	@Test
	void testMultipleYearsOldPet() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(5));
		assertThat(pet.getAge()).isEqualTo("5 years old");
	}

	@Test
	void testFutureBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().plusDays(1));
		assertThat(pet.getAge()).isEqualTo("Not yet born");
	}

	@Test
	void testNullBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(null);
		assertThat(pet.getAge()).isEqualTo("Unknown");
	}

	@Test
	void testPetBornYesterday() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusDays(1));
		assertThat(pet.getAge()).isEqualTo("Less than 1 year old");
	}

}
