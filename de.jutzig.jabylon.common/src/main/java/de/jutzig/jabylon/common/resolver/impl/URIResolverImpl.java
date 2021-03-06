/**
 *
 */
package de.jutzig.jabylon.common.resolver.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.common.resolver.URIHandler;
import de.jutzig.jabylon.common.resolver.URIResolver;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
@Component
@Service
public class URIResolverImpl implements URIResolver {

    @Reference(referenceInterface=URIHandler.class,cardinality=ReferenceCardinality.OPTIONAL_MULTIPLE,bind="addHandler",unbind="removeHandler",policy=ReferencePolicy.DYNAMIC)
    private List<URIHandler> handlers = new CopyOnWriteArrayList<URIHandler>();
    private CDOSession session;
    private CDOView view;
    @Reference(cardinality=ReferenceCardinality.MANDATORY_UNARY)
    private RepositoryConnector repositoryConnector;


    public void addHandler(URIHandler handler)
    {
        handlers.add(handler);
    }

    public void removeHandler(URIHandler handler) {
        handlers.remove(handler);
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * de.jutzig.jabylon.common.resolver.URIResolver#resolve(org.eclipse.emf
     * .common.util.URI)
     */
    @Override
    public Object resolve(URI uri) {
        for (URIHandler handler : handlers) {
            if(handler.canHandle(uri))
                return handler.resolve(uri);
        }
        return null;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.jutzig.jabylon.common.resolver.URIResolver#resolve(java.lang.String)
     */
    @Override
    public Object resolve(String path) {
        if(path==null)
            return resolve((URI)null);
        URI uri = URI.createURI(path, true);
        return resolve(uri);
    }

    public void bindRepositoryConnector(RepositoryConnector connector) {
        session = connector.createSession();
        view = session.openView();
    }

    public void unbindRepositoryConnector(RepositoryConnector connector) {
        view.close();
        view = null;
        session.close();
        session = null;
    }

    @Deactivate
    public void deactivate() {
        if(view!=null)
            view.close();
        if(session!=null)
            session.close();
    }

    @Override
    public CDOObject resolve(CDOID id) {
        return view.getObject(id);
    }

    @Override
    public CDOObject resolveWithTransaction(CDOID id) {
        return session.openTransaction().getObject(id);
    }


}
