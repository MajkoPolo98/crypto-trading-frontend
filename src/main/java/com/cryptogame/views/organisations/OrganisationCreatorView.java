package com.cryptogame.views.organisations;

import com.cryptogame.client.organisation.OrganisationClient;
import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.Organisation;
import com.cryptogame.domain.User;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import java.math.BigDecimal;
import java.util.Arrays;

@Route("organisation/creator")
public class OrganisationCreatorView extends VerticalLayout {
    public OrganisationCreatorView(OrganisationClient organisationClient, UserClient userClient) {
        TextField username = new TextField("Username");

        MainMenu menu = new MainMenu();

        add(
                menu.createMenuBar(userClient),

                new H1("Create organisation"),
                username,
                new Button("Register", event -> {
                    try {
                        User user = VaadinSession.getCurrent().getAttribute(User.class);
                        Organisation organisation = new Organisation(username.getValue(), BigDecimal.ZERO);
                        organisation.setUsers(Arrays.asList(user.getId()));

                        organisationClient.createOrganisation(organisation);
                        UI.getCurrent().navigate("organisation");
                    } catch (Exception e) {
                        Notification.show("Wrong credentials.");
                    }
                }),
                new RouterLink("Go back", OrganisationsView.class)
        );
    }
}
