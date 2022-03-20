package com.cryptogame.views.users;

import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.User;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("user")
public class UsersView extends VerticalLayout {
    public UsersView(UserClient userClient) {
        setAlignItems(Alignment.CENTER);
        Grid<User> grid = new Grid<>(User.class);
        grid.removeAllColumns();
        grid.addColumn(User::getName).setHeader("User name");
        grid.addColumn(User::getValue).setHeader("User value");
        grid.addColumn(User::getWallet).setHeader("USER TEST");
        grid.addColumn(User::getMoney).setHeader("User free funds");
        grid.addColumn(User::getGroup_name).setHeader("User organisation");

        MainMenu menu = new MainMenu();

        grid.setItems(userClient.getUsers());

        add(menu.createMenuBar(userClient),
                grid);
    }
}
