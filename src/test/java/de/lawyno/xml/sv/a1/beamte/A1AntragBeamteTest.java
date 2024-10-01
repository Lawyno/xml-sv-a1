package de.lawyno.xml.sv.a1.beamte;


import de.lawyno.xml.sv.a1.antrag.beamte.A1AntragBeamte;
import de.lawyno.xml.sv.a1.basis.StornokennzeichenStp;
import de.lawyno.xml.sv.a1.test.AbstractXmlTest;
import de.lawyno.xml.sv.ag300.svtoag.SvtoagA1;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class A1AntragBeamteTest extends AbstractXmlTest {

    private static final String XML_FILE_BEA_MAX = "/xml/single/antrag/beamte/v3.0.0/beamte-bea-max.xml";
    private static final String XML_FILE_ANG_MAX = "/xml/single/antrag/beamte/v3.0.0/beamte-ang-max.xml";
    private static final String XML_FILE_BEA_MAX_LEGACY = "/xml/single/antrag/beamte/v2.0.0/beamte-bea-max.xml";
    private static final String XML_FILE_ANG_MAX_LEGACY = "/xml/single/antrag/beamte/v2.0.0/beamte-ang-max.xml";

    private static final String EXPECTED_ABSENDER = "A0123456";
    private static final String EXPECTED_EMPFAENGER = "A9876543";
    private static final LocalDateTime EXPECTED_DATUM_ERSTELLUNG = LocalDateTime.of(2024, 9, 27, 8, 15, 42);
    private static final String EXPECTED_PROD_ID = "prod123";
    private static final String EXPECTED_MOD_ID = "mod12345";
    private static final String EXPECTED_DATENSATZ_ID = "datensatz-id-0000000000000000002";
    private static final String EXPECTED_VORGANGS_ID = "vorgang-id-000000000000000000001";
    private static final String EXPECTED_AZVU = "Beamte-Max-3.0.0";
    private static final String EXPECTED_AZVU_LEGACY = "Beamte-Max-2.0.0";
    private static final String EXPECTED_DATENSATZ_ID_URSPRUNGSMELDUNG = "datensatz-id-0000000000000000001";


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_BEA_MAX, XML_FILE_ANG_MAX })
    void xmlToJava(final String file) throws JAXBException, IOException, SAXException {
        final Unmarshaller unmarshaller = createDefaultUnmarshaller();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragBeamte.class);
        assertA1AntragBeamte((A1AntragBeamte) o, XML_VERSION_ANTRAG, EXPECTED_AZVU);

        final Marshaller marshaller = createDefaultMarshaller();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_1/3.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Beamte/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0"
                );

        assertThatXmlIsValid("/xsd/v3.0.0/A1A-106_Anlage_1_Beamte_Beschaeftigte-3.0.0.xsd", xml);
    }


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_BEA_MAX_LEGACY, XML_FILE_ANG_MAX_LEGACY })
    void xmlToJava_legacy(final String file) throws JAXBException, IOException, SAXException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithSvtoagA1();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragBeamte.class);
        assertA1AntragBeamte((A1AntragBeamte) o, XML_VERSION_ANTRAG_LEGACY, EXPECTED_AZVU_LEGACY);

        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Beamte/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_1/3.0"
                );

        assertThatXmlIsValid("/xsd/v2.0.0/A1_Beamte_V2_0_0.xsd", xml);
    }


    private void assertA1AntragBeamte(final A1AntragBeamte a1AntragBeamte, final String expectedVersion, final String expectedAzvu) {
        assertThat(a1AntragBeamte).isNotNull();
        assertThat(a1AntragBeamte.getVersionsnummer()).isEqualTo(expectedVersion);
        assertThat(a1AntragBeamte.getSteuerungsdaten()).satisfies(steuerungsdaten -> {
            assertThat(steuerungsdaten).isNotNull();
            assertThat(steuerungsdaten.getAbsendernummer()).isEqualTo(EXPECTED_ABSENDER);
            assertThat(steuerungsdaten.getEmpfaengernummer()).isEqualTo(EXPECTED_EMPFAENGER);
            assertThat(steuerungsdaten.getDatumErstellung()).isEqualTo(EXPECTED_DATUM_ERSTELLUNG);
            assertThat(steuerungsdaten.getProduktIdentifier()).isEqualTo(EXPECTED_PROD_ID);
            assertThat(steuerungsdaten.getModifikationsIdentifier()).isEqualTo(EXPECTED_MOD_ID);
            assertThat(steuerungsdaten.getDatensatzId()).isEqualTo(EXPECTED_DATENSATZ_ID);
            assertThat(steuerungsdaten.getVorgangsId()).isEqualTo(EXPECTED_VORGANGS_ID);
            assertThat(steuerungsdaten.getAktenzeichenVerursacher()).isEqualTo(expectedAzvu);
            assertThat(steuerungsdaten.getStornierung()).satisfies(stornierung -> {
                assertThat(stornierung).isNotNull();
                assertThat(stornierung.getStornokennzeichen()).isEqualByComparingTo(StornokennzeichenStp.J);
                assertThat(stornierung.getDatensatzIdUrsprungsmeldung()).isEqualTo(EXPECTED_DATENSATZ_ID_URSPRUNGSMELDUNG);
            });
        });
        assertThat(a1AntragBeamte.getErklaerungArbeitgeber()).isNotNull();
        assertThat(a1AntragBeamte.getErklaerungArbeitgeber().getAngaben()).isEqualTo("J");
    }


    @Override
    protected JAXBContext createDefaultJaxbContext() throws JAXBException {
        return createJaxbContext(A1AntragBeamte.class, SvtoagA1.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithSvtoagA1() throws JAXBException {
        final List<Object> list = new ArrayList<>();
        list.add(ClassLoader.getSystemResourceAsStream("oxm/106-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-beamte-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-beamte.xml"));

        // Abwärtskompatibilität durch MOXy-JAXB-Implementierung
        return createJaxbContextWithBindingSourceList(list, A1AntragBeamte.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithClassicSvtoag() throws JAXBException {
        return null; // TODO Fälle mit SVTOAG aus xml-sv-ag implementieren
    }

}