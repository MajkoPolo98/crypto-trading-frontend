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
import java.util.Optional;

@Route("Register")
public class RegisterView extends VerticalLayout {

    public RegisterView(UserClient userClient) {
        setAlignItems(Alignment.CENTER);

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm Password");
        TextField email = new TextField("Email (can be fake)");

        add(
                new H1("Welcome"),
                username,
                password,
                confirmPassword,
                email,
                new Button("Register", event -> {
                    if (username.isEmpty()) {
                        Notification.show("Enter a username");
                    } else if (password.isEmpty()) {
                        Notification.show("Enter a password");
                    } else if (!password.getValue().equals(confirmPassword.getValue())) {
                        Notification.show("Passwords don't match");
                    } else if (email.isEmpty()) {
                        Notification.show("Enter an email");
                    } else {
                        try {
                            User user = new User(username.getValue(), email.getValue(), password.getValue(), false, new BigDecimal(10000));
                            userClient.createUser(user);
                            UI.getCurrent().navigate("");
                        } catch (Exception e) {
                            Notification.show("User with that name already exists");
                        }
                    }
                }),
                new RouterLink("Go back", LoginView.class)
        );
    }

}
