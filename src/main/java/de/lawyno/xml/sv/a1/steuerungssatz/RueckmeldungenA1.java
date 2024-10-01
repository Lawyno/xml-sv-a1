package de.lawyno.xml.sv.a1.steuerungssatz;

import de.lawyno.xml.IXmlType;
import de.lawyno.xml.sv.a1.bescheid.ablehnung.A1Ablehnung;
import de.lawyno.xml.sv.a1.bescheid.bewilligung.A1Bewilligung;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Beim klassischen {@link de.lawyno.xml.sv.ag.svtoag.Svtoag} wird noch das Element {@code <a1:Rueckmeldungen>}
 * benötigt. Da diese verfahrensspezifischen Inhalt hat, wird sie hier speziell für A1 definiert.
 *
 * @deprecated wird in zukünfigen Headern nicht mehr benötigt, da dieses Element wegfällt.
 */
@Deprecated
@XmlRootElement(name = "Rueckmeldungen")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "a1BewilligungList",
        "a1AblehnungList"
})
public class RueckmeldungenA1 implements IXmlType {

    @XmlElement(name = "A1_Bewilligung", namespace = "http://www.gkv-datenaustausch.de/XMLSchema/A1_Bewilligung/1.3")
    protected List<A1Bewilligung> a1BewilligungList;
    @XmlElement(name = "A1_Ablehnung", namespace = "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3")
    protected List<A1Ablehnung> a1AblehnungList;


    /**
     * Gets the value of the a1BewilligungList property.
     *
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the a1BewilligungList property.</p>
     *
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getA1BewilligungList().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link A1Bewilligung }
     * </p>
     *
     *
     * @return
     *     The value of the a1BewilligungList property.
     */
    public List<A1Bewilligung> getA1BewilligungList() {
        if (a1BewilligungList == null) {
            a1BewilligungList = new ArrayList<>();
        }
        return this.a1BewilligungList;
    }

    /**
     * Gets the value of the a1AblehnungList property.
     *
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the a1AblehnungList property.</p>
     *
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getA1AblehnungList().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link A1Ablehnung }
     * </p>
     *
     *
     * @return
     *     The value of the a1AblehnungList property.
     */
    public List<A1Ablehnung> getA1AblehnungList() {
        if (a1AblehnungList == null) {
            a1AblehnungList = new ArrayList<>();
        }
        return this.a1AblehnungList;
    }

    /**
     * Adds objects to the list of A1BewilligungList using add method
     *
     * @param values
     *     objects to add to the list A1BewilligungList
     * @return
     *     The class instance
     */
    public RueckmeldungenA1 withA1BewilligungList(A1Bewilligung... values) {
        if (values!= null) {
            for (A1Bewilligung value: values) {
                getA1BewilligungList().add(value);
            }
        }
        return this;
    }

    /**
     * Adds objects to the list of A1BewilligungList using addAll method
     *
     * @param values
     *     objects to add to the list A1BewilligungList
     * @return
     *     The class instance
     */
    public RueckmeldungenA1 withA1BewilligungList(Collection<A1Bewilligung> values) {
        if (values!= null) {
            getA1BewilligungList().addAll(values);
        }
        return this;
    }

    /**
     * Adds objects to the list of A1AblehnungList using add method
     *
     * @param values
     *     objects to add to the list A1AblehnungList
     * @return
     *     The class instance
     */
    public RueckmeldungenA1 withA1AblehnungList(A1Ablehnung... values) {
        if (values!= null) {
            for (A1Ablehnung value: values) {
                getA1AblehnungList().add(value);
            }
        }
        return this;
    }

    /**
     * Adds objects to the list of A1AblehnungList using addAll method
     *
     * @param values
     *     objects to add to the list A1AblehnungList
     * @return
     *     The class instance
     */
    public RueckmeldungenA1 withA1AblehnungList(Collection<A1Ablehnung> values) {
        if (values!= null) {
            getA1AblehnungList().addAll(values);
        }
        return this;
    }

}
