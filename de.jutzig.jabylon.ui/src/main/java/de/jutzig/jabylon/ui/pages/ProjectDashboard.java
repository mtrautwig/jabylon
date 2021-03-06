package de.jutzig.jabylon.ui.pages;

import java.util.EnumSet;
import java.util.Locale;

import org.eclipse.emf.cdo.CDOObject;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.PropertiesMasterEditor;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.search.SearchResultPage;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

public class ProjectDashboard implements CrumbTrail, ClickListener {

    private Project project;
    private ProjectVersion version;
    private VerticalLayout mainLayout;
    private Table table;

    public ProjectDashboard(String projectName, String versionName) {
        project = MainDashboard.getCurrent().getWorkspace().getProject(projectName);
        version = getProjectVersion(project, versionName);

    }

    private void initialize() {
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.addComponent(createVersionSelection());

        Section section = new Section();
        section.setCaption(Messages.getString("ProjectDashboard_AVAILABLE_LOCALES_SECTION_TITLE"));
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        section.addComponent(layout);
        createContents(layout);
        mainLayout.setSizeFull();
        mainLayout.addComponent(section);
        mainLayout.setExpandRatio(section, 1);

    }

    private Component createVersionSelection() {
        ComboBox versionSelector = new ComboBox(Messages.getString("ProjectDashboard_VERSION_SELECTION_COMBO_CAPTION"));
        versionSelector.setTextInputAllowed(false);
        versionSelector.setNewItemsAllowed(false);
        versionSelector.setImmediate(true);
        versionSelector.setItemCaptionPropertyId("branch"); //$NON-NLS-1$
        BeanItemContainer<ProjectVersion> container = new BeanItemContainer<ProjectVersion>(ProjectVersion.class);

        for (ProjectVersion version : project.getChildren()) {
            container.addItem(version);
        }
        versionSelector.setContainerDataSource(container);
        versionSelector.select(version);
        versionSelector.setNullSelectionAllowed(false);
        versionSelector.setVisible(container.size() > 1);

        versionSelector.addListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (value instanceof ProjectVersion) {
                    version = (ProjectVersion) value;
                    MainDashboard.getCurrent().getBreadcrumbs().walkTo("?" + version.getName()); //$NON-NLS-1$
                }

            }
        });
        return versionSelector;
    }

    private ProjectVersion getProjectVersion(Project project, String version) {
        if(version==null)
            return project.getChildren().get(0);
        return project.getChild(version);
    }

    public ProjectDashboard(String projectName) {
        this(projectName, null);

    }

    private void createContents(VerticalLayout parent) {
        buildHeader(parent);

        table = new Table();
        table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
        table.setColumnWidth(ProjectLocaleTableContainer.LocaleProperty.PROGRESS, 110);
        table.setColumnExpandRatio(ProjectLocaleTableContainer.LocaleProperty.SUMMARY, 3f);
        table.setSizeFull();
        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);

        table.setContainerDataSource(new ProjectLocaleTableContainer(version));
        table.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE, LocaleProperty.SUMMARY, LocaleProperty.PROGRESS).toArray());
        table.setItemIconPropertyId(LocaleProperty.FLAG);
        parent.addComponent(table);
        parent.setExpandRatio(table, 1);


        Button editTemplate = new Button(Messages.getString("ProjectDashboard_EDIT_TEMPLATE_BUTTON"));
        editTemplate.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MainDashboard.getCurrent().getBreadcrumbs().walkTo("?master"); //$NON-NLS-1$

            }
        });
        editTemplate.setVisible(project.isTerminology());
        parent.addComponent(editTemplate);

    }

    private void buildHeader(Layout parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public CrumbTrail walkTo(String path) {
        if(path.equals("?master")) //$NON-NLS-1$
        {
            PropertyFileDescriptor descriptor = version.getTemplate().getDescriptors().get(0);
            return new PropertiesMasterEditor(descriptor);
        }
        if(path.startsWith(SearchResultPage.SEARCH_ADDRESS))
        {
            return new SearchResultPage(path.substring(SearchResultPage.SEARCH_ADDRESS.length()), version);
        }
        Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, path);
        if (path.startsWith("?")) { //$NON-NLS-1$
            version = getProjectVersion(project, path.substring(1));
            if(mainLayout!=null)
                mainLayout.removeAllComponents();
            return this;
        }
        //TODO: this is a hack

        ProjectLocale projectLocale = version.getProjectLocale(locale);
        return new ProjectLocaleDashboard(projectLocale);
    }

    @Override
    public String getTrailCaption() {
        return project.getName();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        ProjectLocale locale = (ProjectLocale) event.getButton().getData();
        MainDashboard.getCurrent().getBreadcrumbs().walkTo(locale.getLocale().toString());

    }

    @Override
    public boolean isDirty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public CDOObject getDomainObject() {
        return project;
    }

    @Override
    public Component createContents() {
        initialize();
        return mainLayout;

    }

}
