package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login") 
@PageTitle("Login | Mila Library")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm(); 

	public LoginView() {
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

		login.setAction("login");

		Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");

		add(new HorizontalLayout(img, new H1("Mila Library")));
		add(login);
		add(new Paragraph("A place for creating your own library system 🤗"));
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}