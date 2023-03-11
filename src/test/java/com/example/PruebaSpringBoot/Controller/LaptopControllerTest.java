package com.example.PruebaSpringBoot.Controller;

import com.example.PruebaSpringBoot.Entity.Laptop;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;
    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:"+port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        //tengo que ver que me devuelve el metodo a probar
        ResponseEntity<Laptop[]> response;
        // con los metodos de template solicito a la url e indico que tipo de dato debe esperar.
        //como no puedo decirle que devuelve una lista porque es una interface.
        // trabajo con array y luego lo paso a una lista.
        response = testRestTemplate.getForEntity("/laptops",Laptop[].class);

        // ahora analizo la respuesta con los asserts
        assertEquals(HttpStatus.OK,response.getStatusCode());

        //puedo mostrar el array para poder verificar aun mas
        Arrays.asList(response.getBody());
        System.out.println(Arrays.asList(response.getBody()).size());

    }

    @Test
    void createOK() {
        // aca tengo que definir que es lo que voy a enviar.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.Arrays.asList(MediaType.APPLICATION_JSON));

        //defino el json a enviar
        String json = """
                {
                    "name": "msi"
                }
                """;

        //realizo la peticion
        HttpEntity<String> request = new HttpEntity<>(json,headers);


        //obtengo la respuesta.
        ResponseEntity<Laptop> response;
        response = testRestTemplate.exchange("/laptops/create",HttpMethod.POST,request,Laptop.class);

        //verifico
        Laptop result = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1L,result.getId());
        assertEquals("msi",result.getName());

    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response;
        response = testRestTemplate.getForEntity("/laptops/99",Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    void createNO() {
        // aca tengo que definir que es lo que voy a enviar.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.Arrays.asList(MediaType.APPLICATION_JSON));

        //defino el json a enviar
        String json = """
                {
                    "id": 1,
                    "name": "asus"
                }
                """;
        //realizo la peticion
        HttpEntity<String> request = new HttpEntity<>(json,headers);




        //obtengo la respuesta.
        ResponseEntity<Laptop> response;
        response = testRestTemplate.exchange("/laptops/create",HttpMethod.POST,request,Laptop.class);
                //testRestTemplate.getForEntity("/laptops/create",Laptop.class);

        //verifico
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

    }

    @Test
    void deleteAll() {
        ResponseEntity<Laptop> response;
        response = testRestTemplate.getForEntity("/laptops/deleteAll",Laptop.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

}