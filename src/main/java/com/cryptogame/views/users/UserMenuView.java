package com.cryptogame.views.users;

import com.cryptogame.client.coin.CoinClient;
import com.cryptogame.client.user.UserClient;
import com.cryptogame.client.user.UserTransactionClient;
import com.cryptogame.domain.Coin;
import com.cryptogame.domain.CryptoInWallet;
import com.cryptogame.domain.User;
import com.cryptogame.domain.UserTransaction;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("user/menu")
public class UserMenuView extends VerticalLayout {


    public UserMenuView(UserClient userClient, UserTransactionClient userTransactionClient, CoinClient coinClient){

        MainMenu menu = new MainMenu();
        add(menu.createMenuBar());

        Long userId = VaadinSession.getCurrent().getAttribute(User.class).getId();
        User user = userClient.getUser(userId);

/*        List<String> userCoinsSymbols = user.getWallet().keySet().stream().collect(Collectors.toList());
        List<Coin> userCoins = coinClient.getSelectedCoins(userCoinsSymbols);*/

        Map<String, BigDecimal> userCoinMap = user.getWallet();

        Text moneyAvailable = new Text("Money available: " + user.getMoney());
        add(moneyAvailable);

        Text text = new Text("Wallet");
        add(text);

        Text text2 = new Text("Wallet");
        add(text2);

        Grid<CryptoInWallet> walletGrid = new Grid<>();
        walletGrid.addColumn(CryptoInWallet::getSymbol).setHeader("Symbol");
        walletGrid.addColumn(CryptoInWallet::getAmount).setHeader("Amount owned");
        walletGrid.addColumn(crypto -> userCoinMap.get(crypto.getSymbol())).setHeader("Crypto price");
        walletGrid.addComponentColumn(crypto -> sellForUser(crypto.getSymbol(), userTransactionClient));
        walletGrid.setItems(user.crypto());
        add(walletGrid);

        Text transactions = new Text("Transactions");
        add(transactions);

        Grid<UserTransaction> transactionGrid = new Grid<>();
        transactionGrid.addColumn(UserTransaction::getTransaction_date).setHeader("Transaction date");
        transactionGrid.addColumn(UserTransaction::getCrypto_symbol).setHeader("Crypto symbol");
        transactionGrid.addColumn(UserTransaction::getCrypto_amount).setHeader("Crypto amount change");
        transactionGrid.addColumn(UserTransaction::getMoney).setHeader("Money change");
        transactionGrid.addColumn(UserTransaction::getWorth_now).setHeader("Worth now");
        transactionGrid.setItems(userTransactionClient.getUserTransactions(userId));
        add(transactionGrid);
    }

    private VerticalLayout sellForUser(String symbol, UserTransactionClient userTransactionClient){

        VerticalLayout layout = new VerticalLayout();
        TextField cryptoAmount = new TextField("crypto amount");
        User loggedUser = VaadinSession.getCurrent().getAttribute(User.class);
        Button buy = new Button("Sell crypto", event -> {
            UserTransaction transaction = new UserTransaction(loggedUser.getId(), symbol, new BigDecimal(cryptoAmount.getValue()), null);
            userTransactionClient.sellCrypto(transaction);
            UI.getCurrent().getPage().reload();
            Notification.show("Crypto sold!");
        });
        layout.add(cryptoAmount, buy);
        return layout;
    }
}
