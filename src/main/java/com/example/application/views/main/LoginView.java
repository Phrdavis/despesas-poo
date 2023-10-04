package com.example.application.views.main;

import Entidades.Usuario;
import Persistencia.UsuarioDAO;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Login")
@Route(value = "")

public class LoginView extends VerticalLayout {

    public LoginView() {

        Usuario user = new Usuario();
        UsuarioDAO dao = new UsuarioDAO();

        //Forms
        VerticalLayout layout = new VerticalLayout();
        VerticalLayout Vlayout = new VerticalLayout();
        HorizontalLayout Hlayout = new HorizontalLayout();

        Paragraph login = new Paragraph("Login");
        EmailField email = new EmailField();
        PasswordField senha = new PasswordField();
        Paragraph esqueceu_senha = new Paragraph("Esqueci minha senha");
        Button btn = new Button("Login");
        Button btn_cadastro = new Button("Cadastro");

        Paragraph copyright = new Paragraph("copyrigth @ 2023 | App de cria");


        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        btn_cadastro.addClickListener((event)->{
            UI.getCurrent().navigate("cadastro");
        });


        //Make de layout
        setSizeFull();
        setSpacing(false);

        layout.setSpacing(false);

        login.setWidthFull();
        login.setClassName("login-title");

        email.setWidthFull();
        email.setLabel("E-mail");
        email.setPlaceholder("email@email.com");
        email.setErrorMessage("Digite um e-mail válido!");
        email.setRequired(true);
        email.setClearButtonVisible(true);

        senha.setWidthFull();
        senha.setLabel("Senha");
        senha.setPlaceholder("senha");
        senha.setRequired(true);
        senha.setClearButtonVisible(true);


        btn.addClickShortcut(Key.ENTER);

        btn.addClickListener((event)->{

            String email_user = email.getValue();
            String senha_user = senha.getValue();

            user.setEmail(email_user);
            user.setSenha(senha_user);

            var validaEmail = dao.validarEmailUsuario(user);
            var validaSenha = dao.validarSenhaUsuario(user);

            if(validaEmail.getEmail() == null){
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.show("E-mail não cadastrado!");

            } else if (validaSenha.getSenha() == null) {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.show("Senha inválida!");
            }else {
                int id = user.getId();
                UI.getCurrent().navigate("despesas");
            }

        });


        btn.getStyle().set("width", "50%");
        btn_cadastro.getStyle().set("width", "50%");

        copyright.addClassNames("position-fixed mb-3");

        Vlayout.setSizeFull();
        Vlayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Vlayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Hlayout.setWidthFull();
        Hlayout.setPadding(true);
        Hlayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Hlayout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.setWidth("300px");

        Hlayout.add(btn_cadastro, btn);
        layout.add(login, email, senha, esqueceu_senha, Hlayout);


        Vlayout.add(layout);
        Vlayout.add(copyright);

        //Screen Components
        add(Vlayout);



    }

}
