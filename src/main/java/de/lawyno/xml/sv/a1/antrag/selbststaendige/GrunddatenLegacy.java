package de.lawyno.xml.sv.a1.antrag.selbststaendige;


import de.lawyno.xml.sv.a1.basis.AnschriftPersonImAbkommensstaat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "", propOrder = {
        "name",
        "anschrift",
        "telefonnummer",
        "steuernummer"
})
public class GrunddatenLegacy extends A1AntragSelbststaendige.AngabenSelbststaendigeTaetigkeitDeutschland.Grunddaten {

    protected String steuernummer;


    @XmlElement(name = "Steuernummer", required = true)
    public String getSteuernummer() {
        return steuernummer;
    }


    public void setSteuernummer(final String steuernummer) {
        this.steuernummer = steuernummer;
    }


    @Override
    @XmlElement(name = "Name", required = true)
    public String getName() {
        return super.getName();
    }


    @Override
    @XmlElement(name = "Anschrift", required = true)
    public AnschriftPersonImAbkommensstaat getAnschrift() {
        return super.getAnschrift();
    }


    @Override
    @XmlElement(name = "Telefonnummer", required = true)
    public String getTelefonnummer() {
        return super.getTelefonnummer();
    }

}
