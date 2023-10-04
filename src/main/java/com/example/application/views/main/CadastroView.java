package com.example.application.views.main;

import Entidades.Usuario;
import Persistencia.UsuarioDAO;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Cadastro de usuário")
@Route(value = "cadastro")
public class CadastroView extends VerticalLayout {
    public CadastroView() {
        Usuario user = new Usuario();
        UsuarioDAO dao = new UsuarioDAO();

        HorizontalLayout main = new HorizontalLayout();
        main.setJustifyContentMode(JustifyContentMode.CENTER);
        main.setAlignItems(Alignment.START);

        setSizeFull();
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setAlignItems(Alignment.START);


        VerticalLayout layout2 = new VerticalLayout();
        layout2.setPadding(true);
        layout2.setJustifyContentMode(JustifyContentMode.CENTER);
        layout2.setAlignItems(Alignment.START);

        TextField nome_cadastro = new TextField();
        nome_cadastro.setLabel("Nome");
        nome_cadastro.setPlaceholder("Alexandre");
        nome_cadastro.setRequired(true);
        nome_cadastro.setClearButtonVisible(true);

        TextField sobrenome_cadastro = new TextField();
        sobrenome_cadastro.setLabel("Sobrenome");
        sobrenome_cadastro.setPlaceholder("Madalena de Souza");
        sobrenome_cadastro.setRequired(true);
        sobrenome_cadastro.setClearButtonVisible(true);

        NumberField idade_cadastro = new NumberField();
        idade_cadastro.setLabel("Idade");
        idade_cadastro.setPlaceholder("18");
        idade_cadastro.setRequired(true);
        idade_cadastro.setClearButtonVisible(true);

        H1 titulo = new H1("Cadastro de Usuário");
        EmailField email_cadastro = new EmailField();
        PasswordField senha_cadastro = new PasswordField();
        Button btn_cadastro = new Button("Cadastrar");

        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        email_cadastro.setLabel("E-mail");
        email_cadastro.setPlaceholder("email@email.com");
        email_cadastro.setErrorMessage("Digite um e-mail válido!");
        email_cadastro.setRequired(true);
        email_cadastro.setClearButtonVisible(true);


        senha_cadastro.setLabel("Senha");
        senha_cadastro.setPlaceholder("senha");
        senha_cadastro.setRequired(true);
        senha_cadastro.setClearButtonVisible(true);


        btn_cadastro.addClickShortcut(Key.ENTER);
        btn_cadastro.addClickListener((event)->{
           String email = email_cadastro.getValue();
           String senha = senha_cadastro.getValue();
           String nome = nome_cadastro.getValue();
           String sobrenome = sobrenome_cadastro.getValue();
           Double idade = idade_cadastro.getValue();


           if (email != "" || senha != "" || nome != "" || sobrenome != "" || idade != null){

               user.setNome(nome);
               user.setSobrenome(sobrenome);
               user.setIdade(idade);
               user.setEmail(email);
               user.setSenha(senha);

               var testeEmail = dao.validarEmailUsuario(user);

               if (testeEmail.getEmail() == null){
                   dao.inserir(user);
                   notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                   notification.show("Usuário Cadastrado com sucesso!");
                   UI.getCurrent().navigate("despesas");
               }else{
                   notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                   notification.show("E-mail já cadastrado! Por favor, insira outro endereço de e-mail!.");
               }
           }else{
               notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
               notification.show("Por favor, preencha todos os campos adequadamente!");
           }



        });

        add(titulo);

        layout2.add(nome_cadastro);
        layout2.add(sobrenome_cadastro);
        layout2.add(idade_cadastro);

        layout.add(email_cadastro);
        layout.add(senha_cadastro);

        main.add(layout2);
        main.add(layout);

        add(main);
        add(btn_cadastro);



    }
}
