package dev.rt.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.rt.credit.application.system.dto.request.CustomerDto
import dev.rt.credit.application.system.dto.request.CustomerUpdateDto
import dev.rt.credit.application.system.entity.Customer
import dev.rt.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.Random

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CustomerResourceTest {
  @Autowired
  private lateinit var customerRepository: CustomerRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  companion object {
    const val URL: String = "/api/customers"
  }

  @BeforeEach
  fun setup() = customerRepository.deleteAll()

  @AfterEach
  fun tearDown() = customerRepository.deleteAll()

  @Test
  fun `should create a customer and return 201 status`() {
    val customerDto: CustomerDto = builderCustomerDto()
    val valueAsString: String = objectMapper.writeValueAsString(customerDto)
    mockMvc.perform(
      MockMvcRequestBuilders.post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(valueAsString)
    )
      .andExpect(MockMvcResultMatchers.status().isCreated)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Regi"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Tex"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("28475934625"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("regitex@gmail.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("3500.0"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("19400000"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Avenida Jorge Tibirica, 1314"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not save a customer with same CPF and return 409 status`() {
    customerRepository.save(builderCustomerDto().toEntity())
    val customerDto: CustomerDto = builderCustomerDto()
    val valueAsString: String = objectMapper.writeValueAsString(customerDto)
    mockMvc.perform(
      MockMvcRequestBuilders.post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(valueAsString)
    )
      .andExpect(MockMvcResultMatchers.status().isConflict)
      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict! Consult the documentation"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.exception")
          .value("class org.springframework.dao.DataIntegrityViolationException")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not save a customer with empty firstName and return 400 status`() {
    val customerDto: CustomerDto = builderCustomerDto(firstName = "")
    val valueAsString: String = objectMapper.writeValueAsString(customerDto)
    mockMvc.perform(
      MockMvcRequestBuilders.post(URL)
        .content(valueAsString)
        .contentType(MediaType.APPLICATION_JSON)
    )
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.title")
        .value("Bad Request! Consult the documentation"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.exception")
          .value("class org.springframework.web.bind.MethodArgumentNotValidException")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should find customer by id and return 200 status`() {
    val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
    mockMvc.perform(
      MockMvcRequestBuilders.get("$URL/${customer.id}")
        .accept(MediaType.APPLICATION_JSON)
    )
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Regi"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Tex"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("28475934625"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("regitex@gmail.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("3500.0"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("19400000"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Avenida Jorge Tibirica, 1314"))
      //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not find customer with invalid id and return 400 status`() {
    val invalidId: Long = 2L
    mockMvc.perform(
      MockMvcRequestBuilders.get("$URL/$invalidId")
        .accept(MediaType.APPLICATION_JSON)
    )
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.title")
        .value("Bad Request! Consult the documentation"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.exception")
          .value("class dev.rt.credit.application.system.exception.BusinessException")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should delete customer by id and return 204 status`() {
    val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
    mockMvc.perform(
      MockMvcRequestBuilders.delete("$URL/${customer.id}")
        .accept(MediaType.APPLICATION_JSON)
    )
      .andExpect(MockMvcResultMatchers.status().isNoContent)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not delete customer by id and return 400 status`() {
    val invalidId: Long = Random().nextLong()
    mockMvc.perform(
      MockMvcRequestBuilders.delete("$URL/${invalidId}")
        .accept(MediaType.APPLICATION_JSON)
    )
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.title")
        .value("Bad Request! Consult the documentation"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.exception")
          .value("class dev.rt.credit.application.system.exception.BusinessException")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should update a customer and return 200 status`() {
    val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
    val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
    val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
    mockMvc.perform(
      MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
        .contentType(MediaType.APPLICATION_JSON)
        .content(valueAsString)
    )
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Reginaldo"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Teixeira"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("28475934625"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("regitex@gmail.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("5500.0"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("86705778"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Aracari do Pantanal, 99"))
      //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not update a customer with invalid id and return 400 status`() {
    val invalidId: Long = Random().nextLong()
    val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
    val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
    mockMvc.perform(
      MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(valueAsString)
    )
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.title")
        .value("Bad Request! Consult the documentation"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.exception")
          .value("class dev.rt.credit.application.system.exception.BusinessException")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
      .andDo(MockMvcResultHandlers.print())
  }


  private fun builderCustomerDto(
    firstName: String = "Regi",
    lastName: String = "Tex",
    cpf: String = "28475934625",
    email: String = "regitex@gmail.com",
    income: BigDecimal = BigDecimal.valueOf(3500.0),
    password: String = "123456",
    zipCode: String = "19400000",
    street: String = "Avenida Jorge Tibirica, 1314",
  ) = CustomerDto(
    firstName = firstName,
    lastName = lastName,
    cpf = cpf,
    email = email,
    income = income,
    password = password,
    zipCode = zipCode,
    street = street
  )

  private fun builderCustomerUpdateDto(
    firstName: String = "Reginaldo",
    lastName: String = "Teixeira",
    income: BigDecimal = BigDecimal.valueOf(5500.0),
    zipCode: String = "86705778",
    street: String = "Rua Aracari do Pantanal, 99"
  ): CustomerUpdateDto = CustomerUpdateDto(
    firstName = firstName,
    lastName = lastName,
    income = income,
    zipCode = zipCode,
    street = street
  )
}