package com.cryptogame.views.market;

import com.cryptogame.domain.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

public class MainMenu extends VerticalLayout {
    public HorizontalLayout createMenu() {
        setAlignItems(Alignment.CENTER);
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(createButton("Home", "Market"));
        layout.add(new Button("Cryptos"));
        layout.add(new Button("Organisations"));
        layout.add(new Button("Transactions"));

        return layout;
    }

    public Button createButton(String text, String destination){
        return new Button(text, event -> UI.getCurrent().navigate(destination));
    }

    public VerticalLayout createMenuBar(){
        setAlignItems(Alignment.CENTER);
        VerticalLayout layout = new VerticalLayout();
        MenuBar menuBar = new MenuBar();
        Text selected = new Text("");
        ComponentEventListener<ClickEvent<MenuItem>> listener = e -> selected.setText(e.getSource().getText());

        menuBar.addItem("Market", event -> UI.getCurrent().navigate("Market"));

        MenuItem user = menuBar.addItem("User");
        SubMenu userSubMenu = user.getSubMenu();
        userSubMenu.addItem("All users", event -> UI.getCurrent().navigate("user"));
        userSubMenu.addItem("User menu", event -> UI.getCurrent().navigate("user/menu"));

        MenuItem organisation = menuBar.addItem("Organisation");
        SubMenu organisationSubMenu = organisation.getSubMenu();
        organisationSubMenu.addItem("All organisations", event -> UI.getCurrent().navigate("organisation"));
        organisationSubMenu.addItem("Organisation menu", event -> UI.getCurrent().navigate("organisation/menu"));

        MenuItem logOut = menuBar.addItem("Log out", event -> {
            VaadinSession.getCurrent().setAttribute(User.class, null);
            UI.getCurrent().navigate("");
        });
        menuBar.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        logOut.addThemeNames();

        layout.add(menuBar);

        return layout;
    }
}