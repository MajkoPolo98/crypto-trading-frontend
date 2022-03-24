package com.cryptogame.views.market;

import com.cryptogame.client.coin.CoinClient;
import com.cryptogame.client.organisation.OrganisationClient;
import com.cryptogame.client.organisation.OrganisationTransactionClient;
import com.cryptogame.client.user.UserClient;
import com.cryptogame.client.user.UserTransactionClient;
import com.cryptogame.domain.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.math.BigDecimal;
import java.util.List;

@Route("Market")
public class MainView extends VerticalLayout {


    private MainMenu mainMenu = new MainMenu();

    public MainView(CoinClient coinClient, UserClient userClient, OrganisationClient organisationClient, UserTransactionClient userTransactionClient, OrganisationTransactionClient organisationTransactionClient) {
        setAlignItems(Alignment.CENTER);
        add(mainMenu.createMenuBar(userClient));


        User user = userClient.getUser(VaadinSession.getCurrent().getAttribute(User.class).getId());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Text userAvailableMoney = new Text("Money available for user: " + user.getMoney());
        horizontalLayout.add(userAvailableMoney);

        HorizontalLayout horizontalLayoutTwo = new HorizontalLayout();
        if(userClient.getUser(VaadinSession.getCurrent().getAttribute(User.class).getId()).getGroup_name() != null) {
            Organisation organisation = organisationClient.getOrganisationByName(user.getGroup_name());
            Text organisationAvailableMoney = new Text("Money available for organisation: " + organisation.getOrganisation_funds());
            horizontalLayoutTwo.add(organisationAvailableMoney);
        }

        add(horizontalLayout);
        add(horizontalLayoutTwo);

        Grid<Coin> grid = new Grid<>(Coin.class);

        grid.setColumns("symbol", "name", "price");
        grid.addComponentColumn(coin -> {
            setAlignItems(Alignment.CENTER);
            Image image = new Image(coin.getLogo_url(), "coin image");
            image.setHeight(200, Unit.PIXELS);
            image.setHeight(200, Unit.PIXELS);
            return image;
        }).setHeader("image");
        grid.addComponentColumn(coin -> buyForUser(coin, userTransactionClient)).setHeader("Buy crypto for yourself");
        if(userClient.getUser(VaadinSession.getCurrent().getAttribute(User.class).getId()).getGroup_name() != null){
            grid.addComponentColumn(coin -> buyFoOrganisation(coin, userClient, organisationTransactionClient)).setHeader("Buy crypto for organisation");
        }

        List<Coin> coinList = coinClient.getCoins();
        grid.setItems(coinList);
        add(grid);


        Text anotherCrypto = new Text("Find another crypto");
        TextField inputSymbol = new TextField("Input crypto symbol");
        Grid<Coin> gridSearch = new Grid<>(Coin.class);

        gridSearch.setColumns("symbol", "name", "price");
        gridSearch.addComponentColumn(coin -> {
            setAlignItems(Alignment.CENTER);
            Image image = new Image(coin.getLogo_url(), "coin image");
            image.setHeight(200, Unit.PIXELS);
            image.setHeight(200, Unit.PIXELS);
            return image;
        }).setHeader("image");
        gridSearch.addComponentColumn(coin -> buyForUser(coin, userTransactionClient)).setHeader("Buy crypto for yourself");
        if(userClient.getUser(VaadinSession.getCurrent().getAttribute(User.class).getId()).getGroup_name() != null){
            gridSearch.addComponentColumn(coin -> buyFoOrganisation(coin, userClient, organisationTransactionClient)).setHeader("Buy crypto for organisation");
        }

        Button button = new Button("Search", event -> {
            try {
                gridSearch.setItems(coinClient.getCoin(inputSymbol.getValue().toUpperCase()));
            } catch (Exception e){
                Notification.show("Couldn't find crypto");
            }
        });

        add(new HorizontalLayout());
        add(new HorizontalLayout());
        add(new HorizontalLayout());
        add(anotherCrypto);
        add(inputSymbol);
        add(button);
        add(gridSearch);


    }

    private VerticalLayout buyForUser(Coin coin, UserTransactionClient userTransactionClient){
        VerticalLayout layout = new VerticalLayout();
        TextField money = new TextField("money");
        User loggedUser = VaadinSession.getCurrent().getAttribute(User.class);
        Button buy = new Button("Buy crypto", event -> {
            try {
                UserTransaction transaction = UserTransaction.builder()
                        .user_id(loggedUser.getId())
                        .crypto_symbol(coin.getSymbol())
                        .money(new BigDecimal(money.getValue())).build();
                userTransactionClient.buyCrypto(transaction);
                UI.getCurrent().getPage().reload();
                Notification.show("Crypto bought!");
            } catch (Exception e) {
                Notification.show("Something went wrong");
            }
        });
        layout.add(money, buy);
        return layout;
    }

    private VerticalLayout buyFoOrganisation(Coin coin, UserClient userClient, OrganisationTransactionClient organisationTransactionClient){
        VerticalLayout layout = new VerticalLayout();
        TextField money = new TextField("money");
        User user = userClient.getUser(VaadinSession.getCurrent().getAttribute(User.class).getId());
        Button buy = new Button("Buy crypto", event -> {
            try {
                OrganisationTransaction transaction = OrganisationTransaction.builder().user_id(user.getId())
                        .organisation_name(user.getGroup_name())
                        .crypto_symbol(coin.getSymbol())
                        .money(new BigDecimal(money.getValue())).build();

                organisationTransactionClient.buyCrypto(transaction);
                Notification.show("Crypto bought!");
            } catch (Exception e) {
                Notification.show("Something went wrong");
            }
        });
        layout.add(money, buy);
        return layout;
    }


}