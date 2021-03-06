/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getTeamProvider <em>Team Provider</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#isTerminology <em>Terminology</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends Resolvable<Workspace, ProjectVersion> {
    /**
     * Returns the value of the '<em><b>Repository URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository URI</em>' attribute.
     * @see #setRepositoryURI(URI)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_RepositoryURI()
     * @model dataType="de.jutzig.jabylon.properties.URI"
     * @generated
     */
    URI getRepositoryURI();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repository URI</em>' attribute.
     * @see #getRepositoryURI()
     * @generated
     */
    void setRepositoryURI(URI value);

    /**
     * Returns the value of the '<em><b>Property Type</b></em>' attribute.
     * The literals are from the enumeration {@link de.jutzig.jabylon.properties.PropertyType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Property Type</em>' attribute.
     * @see de.jutzig.jabylon.properties.PropertyType
     * @see #setPropertyType(PropertyType)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_PropertyType()
     * @model
     * @generated
     */
    PropertyType getPropertyType();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getPropertyType <em>Property Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Property Type</em>' attribute.
     * @see de.jutzig.jabylon.properties.PropertyType
     * @see #getPropertyType()
     * @generated
     */
    void setPropertyType(PropertyType value);

    /**
     * Returns the value of the '<em><b>Team Provider</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Team Provider</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Team Provider</em>' attribute.
     * @see #setTeamProvider(String)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_TeamProvider()
     * @model
     * @generated
     */
    String getTeamProvider();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getTeamProvider <em>Team Provider</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Team Provider</em>' attribute.
     * @see #getTeamProvider()
     * @generated
     */
    void setTeamProvider(String value);

    /**
     * Returns the value of the '<em><b>Terminology</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Terminology</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Terminology</em>' attribute.
     * @see #setTerminology(boolean)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Terminology()
     * @model
     * @generated
     */
    boolean isTerminology();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#isTerminology <em>Terminology</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Terminology</em>' attribute.
     * @see #isTerminology()
     * @generated
     */
    void setTerminology(boolean value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    void fullScan(ScanConfiguration configuration);


} // Project
