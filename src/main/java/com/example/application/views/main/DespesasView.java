package com.example.application.views.main;


import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Entidades.Usuario;
import Persistencia.DespesaDAO;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import Entidades.Despesa;
import com.vaadin.flow.spring.annotation.RouteScope;


@PageTitle("Despesas")
@Route(value = "despesas")
public class DespesasView extends HorizontalLayout {
    public DespesasView(){

        setSizeFull();
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout layoutMain = new VerticalLayout();

        layoutMain.setSizeFull();
        layoutMain.setPadding(true);
        layoutMain.setAlignItems(Alignment.CENTER);
        layoutMain.setJustifyContentMode(JustifyContentMode.CENTER);

        Div div = new Div();
        VerticalLayout layout = new VerticalLayout();
        TextField text = new TextField();
        DatePicker data = new DatePicker("Start date");
        TextField categoria = new TextField();
        NumberField preco = new NumberField();
        Div dollarPrefix = new Div();
        Button btnAdd = new Button("Adicionar");
        Usuario user = new Usuario();
        Despesa despesa = new Despesa();
        DespesaDAO dao = new DespesaDAO();
        Grid<Despesa> grid = new Grid<>(Despesa.class, false);
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        Paragraph dialogPar = new Paragraph("Deseja excluir item?");
        Button dialogBot = new Button("EXCLUIR");
        Button excluirTudo = new Button("EXCLUIR LISTA");

        SideNav nav = new SideNav();
        SideNavItem conta = new SideNavItem("Login", LoginView.class, VaadinIcon.USER.create());
        SideNavItem a = new SideNavItem("Despesas", DespesasView.class, VaadinIcon.LIST.create());

        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);


        nav.addItem(conta, a);


        dialog.getElement().setAttribute("aria-label", "Create new employee");
        dialogPar.setClassName("excluir");
        dialogBot.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_LARGE);


        excluirTudo.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_LARGE);
        excluirTudo.addClickListener((event) -> {
            dao.deletarTodos();
            grid.setItems(dao.selecionarTodos());
            notification.show("Todos os registros deletados!");
        });


        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        dialogLayout.add(dialogPar);
        dialogLayout.add(dialogBot);



        dialog.add(dialogLayout);

        layout.setWidth("1000px");
        layout.setHeight("500px");


        grid.getStyle().set("height", "auto");

        grid.addColumn(Despesa::getId).setHeader("ID");
         grid.addColumn(Despesa::getDespesa).setHeader("Despesa").setKey("Despesa").setFooter(dao.quantidadeDespesa() + " Despesas");
         grid.addColumn(Despesa::getCategoria).setHeader("Categoria").setKey("Categoria").setFooter(dao.categoriasTotal() + " Categorias");;
         grid.addColumn(Despesa::getPreco).setHeader("Preco").setKey("Preco").setFooter("Total R$" + dao.precoTotal());;
         grid.addColumn(Despesa::getData).setHeader("Data");
        grid.setItems(dao.selecionarTodos());

        grid.addSelectionListener(selection -> {
            Optional<Despesa> optionalDespesa = selection.getFirstSelectedItem();
            dialogBot.addClickListener(buttonClickEvent -> {
                var excluir = optionalDespesa.isPresent();
                if (excluir){
                    dao.deletar(optionalDespesa.get());
                    dialog.close();
                    grid.setItems(dao.selecionarTodos());
                    notification.show("Registro excluido!");
                }
            });
            if (optionalDespesa.isPresent()) {


                dialog.open();
                grid.setItems(dao.selecionarTodos());


            }
        });



        text.setLabel("Despesa");
        text.setPlaceholder("Supermercado");
        text.setClearButtonVisible(true);
        text.setPrefixComponent(VaadinIcon.ROCKET.create());
        
        categoria.getStyle().set("padding", "1em");
        categoria.setLabel("Categoria");
        categoria.setPlaceholder("Compras da casa");
        categoria.setClearButtonVisible(true);
        categoria.setPrefixComponent(VaadinIcon.BOOKMARK.create());

        preco.setLabel("Preço");
        dollarPrefix.setText("R$");
        preco.setPrefixComponent(dollarPrefix);

        data.getStyle().set("padding", "1em");
        data.setPlaceholder("27/04/2002");

        btnAdd.getStyle().set("margin-left", "1em");
        btnAdd.addClickShortcut(Key.ENTER);
        btnAdd.addClickListener((event)->{
            String des = text.getValue();
            String cat = categoria.getValue();
            Double pre = preco.getValue();
            String dat = data.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            despesa.setDespesa(des);
            despesa.setCategoria(cat);
            despesa.setPreco(pre);
            despesa.setData(dat);

            dao.inserir(despesa);

            grid.getColumnByKey("Despesa").setFooter(dao.quantidadeDespesa() + " Despesas");
            grid.getColumnByKey("Categoria").setFooter(dao.categoriasTotal() + " Categorias");;
            grid.getColumnByKey("Preco").setFooter("Total R$" + dao.precoTotal());;

            grid.setItems(dao.selecionarTodos());
            notification.show("Novo registro adicionado com sucesso!");
        });

        div.add(text);
        div.add(categoria);
        div.add(preco);
        div.add(data);
        div.add(btnAdd);


        layout.add(grid);

        layoutMain.add(div);
        layoutMain.add(dialog);
        layoutMain.add(layout);
        layoutMain.add(excluirTudo);

        add(nav);
        add(layoutMain);

    }



}
