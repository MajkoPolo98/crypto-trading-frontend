package com.cryptogame.views.organisations;

import com.cryptogame.client.organisation.OrganisationClient;
import com.cryptogame.client.organisation.OrganisationTransactionClient;
import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.*;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.math.BigDecimal;
import java.util.Map;

@Route("organisation/menu")
public class OrganisationMenuView extends VerticalLayout {

    public OrganisationMenuView(
            OrganisationClient organisationClient,
            OrganisationTransactionClient organisationTransactionClient,
            UserClient userClient
    ){

        MainMenu menu = new MainMenu();
        add(menu.createMenuBar(userClient));

        Long userId = VaadinSession.getCurrent().getAttribute(User.class).getId();
        User user = userClient.getUser(userId);
        Organisation organisation = organisationClient.getOrganisationByName(user.getGroup_name());

        Map<String, BigDecimal> organisationCoinMap = organisation.getOrganisation_wallet();

        Text moneyAvailable = new Text("Money available: " + organisation.getOrganisation_funds());
        add(moneyAvailable);

        TextField moneyAmount = new TextField();
        Button sendMoneyButton = new Button("Send to organisation", event -> {
            try {
                organisationClient.sendMoneyToOrganisation(new BigDecimal(moneyAmount.getValue()));
                Notification.show("Money sent");
                UI.getCurrent().getPage().reload();
            } catch (Exception e){
                Notification.show("Couldn't send money");
            }
        });
        add(moneyAmount);
        add(sendMoneyButton);

        Text text = new Text("Wallet");
        add(text);

        Grid<CryptoInWallet> walletGrid = new Grid<>();
        walletGrid.addColumn(CryptoInWallet::getSymbol).setHeader("Symbol");
        walletGrid.addColumn(CryptoInWallet::getAmount).setHeader("Amount owned");
        walletGrid.addColumn(crypto -> organisationCoinMap.get(crypto.getSymbol())).setHeader("Crypto price");
        walletGrid.addComponentColumn(crypto -> sellForOrganisation(crypto.getSymbol(), organisationTransactionClient));
        walletGrid.setItems(organisation.crypto());
        add(walletGrid);

        Text transactions = new Text("Transactions");
        add(transactions);

        Grid<OrganisationTransaction> transactionGrid = new Grid<>();
        transactionGrid.addColumn(OrganisationTransaction::getTransaction_date).setHeader("Transaction date");
        transactionGrid.addColumn(OrganisationTransaction::getCrypto_symbol).setHeader("Crypto symbol");
        transactionGrid.addColumn(OrganisationTransaction::getCrypto_amount).setHeader("Crypto amount change");
        transactionGrid.addColumn(OrganisationTransaction::getMoney).setHeader("Money change");
        transactionGrid.addColumn(OrganisationTransaction::getWorth_now).setHeader("Worth now");
        transactionGrid.setItems(organisationTransactionClient.getOrganisationTransactions(organisation.getId()));
        add(transactionGrid);
    }

    private VerticalLayout sellForOrganisation(String symbol, OrganisationTransactionClient organisationTransactionClient){

        VerticalLayout layout = new VerticalLayout();
        TextField cryptoAmount = new TextField("crypto amount");
        User loggedUser = VaadinSession.getCurrent().getAttribute(User.class);
        Button buy = new Button("Sell crypto", event -> {
            OrganisationTransaction transaction = new OrganisationTransaction(loggedUser.getId(), loggedUser.getGroup_name(), symbol, new BigDecimal(cryptoAmount.getValue()), null);
            organisationTransactionClient.sellCrypto(transaction);
            UI.getCurrent().getPage().reload();
            Notification.show("Crypto sold!");
        });
        layout.add(cryptoAmount, buy);
        return layout;
    }
}
