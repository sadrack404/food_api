package com.algaworks.algafood.api;

import com.algaworks.algafood.api.util.DatabaseCleaner;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-teste.properties")
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    DatabaseCleaner databaseCleaner;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        preparaDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoListarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornar2Cozinhas_QuandoListarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(2));
        //.body("nome", hasItems("Tailandesa", "Indiana "));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinhas() {
        given()
                .body("{\"nome\": \"Chinesa\"}")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    private void preparaDados(){
        Cozinha c1 = new Cozinha();
        c1.setNome("Tailandesa");
        cozinhaRepository.save(c1);

        Cozinha c2 = new Cozinha();
        c1.setNome("Russa");
        cozinhaRepository.save(c2);
    }

}