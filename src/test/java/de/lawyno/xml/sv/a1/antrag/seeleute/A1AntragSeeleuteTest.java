package de.lawyno.xml.sv.a1.antrag.seeleute;


import de.lawyno.xml.sv.a1.basis.KennzeichenAlphanumerischJStp;
import de.lawyno.xml.sv.a1.basis.StornokennzeichenStp;
import de.lawyno.xml.sv.a1.test.AbstractXmlTest;
import de.lawyno.xml.sv.ag300.svtoag.SvtoagA1;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class A1AntragSeeleuteTest extends AbstractXmlTest {

    private static final String XML_FILE_MAX = "/xml/single/antrag/seeleute/v3.0.0/seeleute-max.xml";
    private static final String XML_FILE_MAX_LEGACY = "/xml/single/antrag/seeleute/v2.0.0/seeleute-max.xml";

    private static final String EXPECTED_ABSENDER = "A0123456";
    private static final String EXPECTED_EMPFAENGER = "A9876543";
    private static final LocalDateTime EXPECTED_DATUM_ERSTELLUNG = LocalDateTime.of(2024, 9, 27, 8, 15, 42);
    private static final String EXPECTED_PROD_ID = "prod123";
    private static final String EXPECTED_MOD_ID = "mod12345";
    private static final String EXPECTED_DATENSATZ_ID = "datensatz-id-0000000000000000002";
    private static final String EXPECTED_VORGANGS_ID = "vorgang-id-000000000000000000001";
    private static final String EXPECTED_AZVU = "Seeleute-Max-3.0.0";
    private static final String EXPECTED_AZVU_LEGACY = "Seeleute-Max-2.0.0";
    private static final String EXPECTED_DATENSATZ_ID_URSPRUNGSMELDUNG = "datensatz-id-0000000000000000001";
    private static final String EXPECTED_VORNAME = "Maximilian";
    private static final String EXPECTED_FAMILIENNAME = "Mustermann";


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_MAX })
    void xmlToJava(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createDefaultUnmarshaller();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSeeleute.class);
        assertA1AntragSeeleute((A1AntragSeeleute) o, XML_VERSION_ANTRAG, EXPECTED_AZVU);

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
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_2/3.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Seeleute/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0"
                );

        assertThatXmlIsValid("/xsd/v3.0.0/A1A-106_Anlage_2_Seeleute-3.0.0.xsd", xml);
    }


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_MAX_LEGACY })
    void xmlToJava_legacy(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithSvtoagA1();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSeeleute.class);
        assertA1AntragSeeleute((A1AntragSeeleute) o, XML_VERSION_ANTRAG_LEGACY, EXPECTED_AZVU_LEGACY);

        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Seeleute/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_2/3.0"
                );

        assertThatXmlIsValid("/xsd/v2.0.0/A1_Seeleute_V2_0_0.xsd", xml);
    }


    private void assertA1AntragSeeleute(final A1AntragSeeleute a1AntragSeeleute,
                                        final String expectedVersion,
                                        final String expectedAzvu) {
        assertThat(a1AntragSeeleute).isNotNull();
        assertThat(a1AntragSeeleute.getVersionsnummer()).isEqualTo(expectedVersion);
        assertThat(a1AntragSeeleute.getSteuerungsdaten()).satisfies(steuerungsdaten -> {
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
        assertThat(a1AntragSeeleute.getAngabenZurPersonA1()).satisfies(angabenZurPerson -> {
            assertThat(angabenZurPerson).isNotNull();
            assertThat(angabenZurPerson.getName()).isNotNull();
            assertThat(angabenZurPerson.getName().getGrundangabenName()).satisfies(angabenName -> {
                assertThat(angabenName).isNotNull();
                assertThat(angabenName.getVorname()).isEqualTo(EXPECTED_VORNAME);
                assertThat(angabenName.getFamilienname()).isEqualTo(EXPECTED_FAMILIENNAME);
            });
        });
        assertThat(a1AntragSeeleute.getAngabenAuslandseinsatz()).satisfies(angabenAuslandseinsatz -> {
            assertThat(angabenAuslandseinsatz).isNotNull();
            assertThat(angabenAuslandseinsatz.getGrunddatenAuslandseinsatz()).isNotNull();
            assertThat(angabenAuslandseinsatz.getBeschaeftigungAufEinemHochseeschiffList()).hasSize(11);
        });
        assertThat(a1AntragSeeleute.getErklaerungArbeitgeber()).isNotNull();
        assertThat(a1AntragSeeleute.getErklaerungArbeitgeber().getAngaben()).isEqualByComparingTo(KennzeichenAlphanumerischJStp.J);
    }


    @Override
    protected JAXBContext createDefaultJaxbContext() throws JAXBException {
        return createJaxbContext(A1AntragSeeleute.class, SvtoagA1.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithSvtoagA1() throws JAXBException {
        final List<InputStream> list = new ArrayList<>();
        list.add(ClassLoader.getSystemResourceAsStream("oxm/106-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-seeleute-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-seeleute.xml"));
        return createJaxbContextWithBindingSourceList(list, A1AntragSeeleute.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithClassicSvtoag() {
        return null; // TODO Fälle mit SVTOAG aus xml-sv-ag implementieren
    }

}