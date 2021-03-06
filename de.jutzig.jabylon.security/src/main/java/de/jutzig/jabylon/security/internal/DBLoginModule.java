package de.jutzig.jabylon.security.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.eclipse.emf.cdo.view.CDOView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.security.JabylonSecurityBundle;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;

public class DBLoginModule implements LoginModule {
    static final String EMPTY_STRING = "";
    Subject subj;
    CallbackHandler cbHandler;
    boolean authenticated = false;
    String user;
    String pw;
    List<Permission> permissions = new ArrayList<Permission>();

    private static final Logger logger = LoggerFactory.getLogger(DBLoginModule.class);

    public DBLoginModule() {
    }

    @Override
    public boolean abort() throws LoginException {
        this.authenticated = false;
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        if(this.authenticated) {
            subj.getPublicCredentials().add(user);
            addPermissions(subj.getPrivateCredentials());
        } else {
            subj.getPublicCredentials().remove(user);
            subj.getPrivateCredentials().removeAll(permissions);
            permissions = new ArrayList<Permission>();
        }
        return true;
    }

    private void addPermissions(Set<Object> privateCredentials) {
        for (Permission permission : permissions) {
            privateCredentials.add(permission);
        }
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subj = subject;
        this.cbHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("User:");
        PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
        try {
            cbHandler.handle(new Callback[]{nameCallback, passwordCallback});
        } catch(Exception e) {
            logger.error("Login failed",e);
        }

        user = nameCallback.getName();
        pw = EMPTY_STRING;
        if(passwordCallback.getPassword() != null) {
            pw = String.valueOf(passwordCallback.getPassword());
        }

        this.authenticated = checkLogin(user, pw);
        return this.authenticated;
    }

    private boolean checkLogin(String userName, String pw) {
        if(JabylonSecurityBundle.getRepositoryConnector()==null)
            return false;

        CDOView view = JabylonSecurityBundle.getRepositoryConnector().openView();
        try {
            UserManagement userManagement = (UserManagement)view.getResource(ServerConstants.USERS_RESOURCE).getContents().get(0);
            User user = userManagement.findUserByName(userName);
            if(user==null || pw == null)
                return false;

            if(pw.equals(user.getPassword())) {
                permissions = user.getAllPermissions();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Login failed",e);
            return false;
        } finally {
            view.close();
        }
    }

    @Override
    public boolean logout() throws LoginException {
        this.authenticated = false;
        return true;
    }
}
