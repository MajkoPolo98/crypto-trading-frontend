package com.cryptogame.views.users;

import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.User;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("user/menu")
public class UserMenuView extends VerticalLayout {
    public UserMenuView(UserClient userClient){
        MainMenu menu = new MainMenu();
        add(menu.createMenuBar());

        Text text = new Text("Wallet");
        add(text);

        Grid<Object> walletGrid = new Grid<>();


        add(walletGrid);

        Text transactions = new Text("Transactions");
        add(transactions);
    }
}
