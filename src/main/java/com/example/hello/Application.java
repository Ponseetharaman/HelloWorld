package com.example.hello;

import com.blade.embedd.EmbedJettyServer;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.route.RouteHandler;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.VelocityTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.blade.Blade.$;

/**
 * Hello Blade!
 */
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {

		ViewSettings.$().templateEngine(new VelocityTemplateEngine());
		
		$().get("/", new RouteHandler() {
			public void handle(Request request, Response response) {
				response.html("<h1>Hello Blade！</h1>");
			}
		}).get("/hello", new RouteHandler() {
			public void handle(Request request, Response response) {
				request.attribute("name", "jack");
				response.render("hello.vm");
			}
		}).get("/users", new RouteHandler() {
			public void handle(Request request, Response response) {
				List<User> users = new ArrayList<User>(2);
				users.add(new User("jack", 22));
				users.add(new User("王爵nice", 24));
				response.json(users);
			}
		}).before("/.*", new RouteHandler() {
			public void handle(Request request, Response response) {
				LOGGER.info("before ...");
			}
		}).start(EmbedJettyServer.class);

	}

}