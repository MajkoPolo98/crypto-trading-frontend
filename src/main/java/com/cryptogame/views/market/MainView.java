package com.cryptogame.views.market;

import com.cryptogame.client.coin.CoinClient;
import com.cryptogame.client.organisation.OrganisationClient;
import com.cryptogame.client.organisation.OrganisationTransactionClient;
import com.cryptogame.client.user.UserTransactionClient;
import com.cryptogame.domain.*;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Route("Market")
public class MainView extends VerticalLayout {


    private MainMenu mainMenu = new MainMenu();
    private Grid<Coin> grid = new Grid<>(Coin.class);

    public MainView(CoinClient coinClient, UserTransactionClient userTransactionClient) {
        setAlignItems(Alignment.CENTER);
        add(mainMenu.createMenuBar());

        Grid<Coin> grid = new Grid<>(Coin.class);
        grid.setColumns("symbol", "name", "price");
        grid.addComponentColumn(coin -> {
            Image image = new Image(coin.getLogo_url(), "coin image");
            image.setHeight(20, Unit.PERCENTAGE);
            image.setHeight(20, Unit.PERCENTAGE);
            return image;
        }).setHeader("image");
        grid.addComponentColumn(coin -> buyForUser(coin, userTransactionClient)).setHeader("Buy crypto for yourself");

        List<Coin> coinList = coinClient.getCoins();
        grid.setItems(coinList);
        add(grid);

    }

    private VerticalLayout buyForUser(Coin coin, UserTransactionClient userTransactionClient){
        VerticalLayout layout = new VerticalLayout();
        TextField money = new TextField("money");
        User loggedUser = VaadinSession.getCurrent().getAttribute(User.class);
        Button buy = new Button("Buy crypto", event -> {
            UserTransaction transaction = new UserTransaction(loggedUser.getId(), coin.getSymbol(), null, new BigDecimal(money.getValue()));
            userTransactionClient.buyCrypto(transaction);
            Notification.show("Crypto bought!");
        });
        layout.add(money, buy);
        return layout;
    }

    private VerticalLayout buyFoOrganisation(Coin coin, OrganisationTransactionClient organisationTransactionClient){
        VerticalLayout layout = new VerticalLayout();
        TextField money = new TextField("money");
        User loggedUser = VaadinSession.getCurrent().getAttribute(User.class);
        String grouoName = loggedUser.getGroup_name();
        Button buy = new Button("Buy crypto", event -> {
            OrganisationTransaction transaction = new OrganisationTransaction(grouoName, coin.getSymbol(), null, new BigDecimal(money.getValue()));
            organisationTransactionClient.buyCrypto(transaction);
            Notification.show("Crypto bought!");
        });
        layout.add(money, buy);
        return layout;
    }


}