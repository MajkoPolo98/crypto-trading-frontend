package com.cryptogame.views.login;

import com.cryptogame.client.user.UserClient;
import com.cryptogame.domain.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.math.BigDecimal;

@Route("Register")
public class RegisterView extends VerticalLayout {
    public RegisterView(UserClient userClient) {
        //setId("login-view");
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        var confirmPassword = new PasswordField("Confirm Password");
        var email = new TextField("Email (can be fake)");

        add(
                new H1("Welcome"),
                username,
                password,
                confirmPassword,
                email,
                new Button("Register", event -> {
                    try {
                        User user = new User(username.getValue(), email.getValue(), password.getValue(), false, new BigDecimal(10000));
                        userClient.createUser(user);
                        UI.getCurrent().navigate("");
                    } catch (Exception e) {
                        Notification.show("Wrong credentials.");
                    }
                }),
                new RouterLink("Go back", LoginView.class)
        );
    }
}
