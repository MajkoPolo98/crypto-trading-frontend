package com.cryptogame.views.organisations;

import com.cryptogame.client.organisation.OrganisationClient;
import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.Organisation;
import com.cryptogame.views.market.MainMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;

@Route("organisation")
public class OrganisationsView extends VerticalLayout {

    public OrganisationsView(OrganisationClient organisationClient, UserClient userClient) {
        Grid<Organisation> organisationGrid = new Grid<>(Organisation.class);
        organisationGrid.removeAllColumns();
        organisationGrid.addColumn(Organisation::getOrganisation_name).setHeader("Organisation_name");
        organisationGrid.addColumn(Organisation::getOrganisation_funds).setHeader("Organisation_funds");
        organisationGrid.addColumn(Organisation::getOrganisation_wallet).setHeader("Organisation_wallet");
        organisationGrid.addColumn(organisation -> organisation.getUsers().stream()
                .map(id -> userClient.getUser(id).getName())
                .collect(Collectors.toList())).setHeader("Users");
        organisationGrid.addComponentColumn(organisation -> new Button("Join Organisation", event -> {
            Notification.show("You joined to the organisation!");
            userClient.joinOrganisation(organisation);
            UI.getCurrent().getPage().reload();
        }));
        Button button = new Button("Create organisation", event -> UI.getCurrent().navigate("organisation/creator"));
        MainMenu menu = new MainMenu();

        organisationGrid.setItems(organisationClient.getOrganisations());

        add(menu.createMenuBar(),
                organisationGrid,
                button);
    }


}
