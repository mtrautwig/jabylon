/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.security.LoginPage;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;
import de.jutzig.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class LoginPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	public LoginPanel(String id, IModel<T> object, PageParameters parameters) {
		super(id, object, parameters);
		Session theSession = getSession();

		if (theSession instanceof CDOAuthenticatedSession) {
			final CDOAuthenticatedSession session = (CDOAuthenticatedSession) theSession;
			User user = session.getUser();
			if (user != null) {
				LogoutLink link = new LogoutLink("link");
				link.add(new Label("link-label", "Logout " + user.getName()));
				add(link);
				return;
			}
		}
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class);
		link.add(new Label("link-label", "Login"));
		add(link);

	}

	public static class LoginPanelFactory implements PanelFactory, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public <T> Panel createPanel(PageParameters params, IModel<T> input, String id) {

			return new LoginPanel<T>(id, input, params);

		}

	}
}

class LogoutLink extends StatelessLink<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8192886483968891414L;

	public LogoutLink(String id) {
		super(id);
	}

	@Override
	public void onClick() {
		Session theSession = getSession();
		if (theSession instanceof AuthenticatedWebSession) {
			AuthenticatedWebSession session = (AuthenticatedWebSession) theSession;
			session.invalidate();
			MarkupContainer parent = getParent();
			BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class);
			link.add(new Label("link-label", "Login"));
			parent.addOrReplace(link);
		}

	}

}
