package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import Persistencia.DespesaDAO;
import Persistencia.UsuarioDAO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */

@SpringBootApplication
@Theme(value = "mytodo")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        DespesaDAO despesaDAO = new DespesaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

//        despesaDAO.dropTable();
//        usuarioDAO.dropTable();
        despesaDAO.criarTabela();
        usuarioDAO.criarTabela();
//        usuarioDAO.deletarTodos();

    }

}

// Evento Listener
// https://vaadin.com/docs/v8/framework/application/application-events

// Icones
// https://vaadin.com/docs/latest/components/icons/default-icons

