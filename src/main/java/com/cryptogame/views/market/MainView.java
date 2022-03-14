package com.cryptogame.views.market;

import com.cryptogame.client.coin.CoinClient;
import com.cryptogame.domain.Coin;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("Market")
public class MainView extends VerticalLayout {


    private MainMenu mainMenu = new MainMenu();
    private Grid<Coin> grid = new Grid<>(Coin.class);

    public MainView(CoinClient coinClient) {
        setAlignItems(Alignment.CENTER);
        add(mainMenu.createMenuBar());

        Grid<Coin> grid = new Grid<>(Coin.class);
        grid.setColumns("symbol", "name", "price");
        grid.addComponentColumn(coin -> {
            Image image = new Image(coin.getLogo_url(), "coin image");
            image.setHeight(20, Unit.PIXELS);
            image.setHeight(20, Unit.PIXELS);
            return image;
        }).setHeader("image");
        List<Coin> coinList = coinClient.getCoins();
        grid.setItems(coinList);
        add(grid);

    }

}