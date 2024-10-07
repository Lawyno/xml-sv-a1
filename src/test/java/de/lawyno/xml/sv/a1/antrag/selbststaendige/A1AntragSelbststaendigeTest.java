package de.lawyno.xml.sv.a1.antrag.selbststaendige;


import de.lawyno.xml.sv.a1.basis.KennzeichenAlphanumerischJStp;
import de.lawyno.xml.sv.a1.basis.KennzeichenAlphanumerischStp;
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


class A1AntragSelbststaendigeTest extends AbstractXmlTest {

    private static final String XML_FILE_MAX = "/xml/single/antrag/selbststaendige/v3.0.0/selbststaendige-max.xml";
    private static final String XML_FILE_SHIP = "/xml/single/antrag/selbststaendige/v3.0.0/selbststaendige-schiff.xml";
    private static final String XML_FILE_MAX_LEGACY = "/xml/single/antrag/selbststaendige/v2.0.0/selbststaendige-max.xml";
    private static final String XML_FILE_SHIP_LEGACY = "/xml/single/antrag/selbststaendige/v2.0.0/selbststaendige-schiff.xml";

    private static final String EXPECTED_ABSENDER = "A0123456";
    private static final String EXPECTED_EMPFAENGER = "A9876543";
    private static final LocalDateTime EXPECTED_DATUM_ERSTELLUNG = LocalDateTime.of(2024, 9, 27, 8, 15, 42);
    private static final String EXPECTED_PROD_ID = "prod123";
    private static final String EXPECTED_MOD_ID = "mod12345";
    private static final String EXPECTED_DATENSATZ_ID = "datensatz-id-0000000000000000002";
    private static final String EXPECTED_VORGANGS_ID = "vorgang-id-000000000000000000001";
    private static final String EXPECTED_AZVU_MAX = "106a-1-Max-3.0.0";
    private static final String EXPECTED_AZVU_SHIP = "106a-1-Schiff-3.0.0";
    private static final String EXPECTED_AZVU_MAX_LEGACY = "106a-1-Max-2.0.0";
    private static final String EXPECTED_AZVU_SHIP_LEGACY = "106a-1-Schiff-2.0.0";
    private static final String EXPECTED_DATENSATZ_ID_URSPRUNGSMELDUNG = "datensatz-id-0000000000000000001";
    private static final String EXPECTED_VORNAME = "Maximilian";
    private static final String EXPECTED_FAMILIENNAME = "Mustermann";
    private static final String EXPECTED_STEUERNUMMER = "DE1234567890123";


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_MAX })
    void xmlToJava_max(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createDefaultUnmarshaller();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSelbststaendige.class);
        assertA1AntragSelbststaendige((A1AntragSelbststaendige) o, XML_VERSION_ANTRAG, EXPECTED_AZVU_MAX);

        final Marshaller marshaller = createDefaultMarshaller();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v3.0.0/A1A-106a_Anlage_1_Selbststaendige-3.0.0.xsd", xml);
        assertXmlContent(xml);
    }


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_SHIP })
    void xmlToJava_ship(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createDefaultUnmarshaller();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSelbststaendige.class);
        assertA1AntragSelbststaendige((A1AntragSelbststaendige) o, XML_VERSION_ANTRAG, EXPECTED_AZVU_SHIP);

        final Marshaller marshaller = createDefaultMarshaller();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v3.0.0/A1A-106a_Anlage_1_Selbststaendige-3.0.0.xsd", xml);
        assertXmlContent(xml);
    }


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_MAX_LEGACY })
    void xmlToJava_max_legacy(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithSvtoagA1();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSelbststaendige.class);
        assertA1AntragSelbststaendige((A1AntragSelbststaendige) o, XML_VERSION_ANTRAG_LEGACY, EXPECTED_AZVU_MAX_LEGACY);

        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v2.0.0/A1_Selbststaendige_V2_0_0.xsd", xml);
        assertXmlContentLegacy(xml);
    }


    @ParameterizedTest
    @ValueSource(strings = { XML_FILE_SHIP_LEGACY })
    void xmlToJava_ship_legacy(final String file) throws JAXBException, IOException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithSvtoagA1();
        final Object o = unmarshaller.unmarshal(System.class.getResource(file));
        assertThat(o).isInstanceOf(A1AntragSelbststaendige.class);
        assertA1AntragSelbststaendige((A1AntragSelbststaendige) o, XML_VERSION_ANTRAG_LEGACY, EXPECTED_AZVU_SHIP_LEGACY);

        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(o, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v2.0.0/A1_Selbststaendige_V2_0_0.xsd", xml);
        assertXmlContentLegacy(xml);
    }


    private void assertA1AntragSelbststaendige(final A1AntragSelbststaendige a1AntragSelbststaendige,
                                               final String expectedVersion,
                                               final String expectedAzvu) {
        boolean isLegacy = XML_VERSION_ANTRAG_LEGACY.equals(expectedVersion);
        assertThat(a1AntragSelbststaendige).isNotNull();
        assertThat(a1AntragSelbststaendige.getVersionsnummer()).isEqualTo(expectedVersion);
        assertThat(a1AntragSelbststaendige.getSteuerungsdaten()).satisfies(steuerungsdaten -> {
            assertThat(steuerungsdaten).isNotNull();
            assertThat(steuerungsdaten.getAbsendernummer()).isEqualTo(EXPECTED_ABSENDER);
            assertThat(steuerungsdaten.getEmpfaengernummer()).isEqualTo(EXPECTED_EMPFAENGER);
            assertThat(steuerungsdaten.getDatumErstellung()).isEqualTo(EXPECTED_DATUM_ERSTELLUNG);
            assertThat(steuerungsdaten.getDatensatzId()).isEqualTo(EXPECTED_DATENSATZ_ID);
            if (isLegacy) {
                assertThat(steuerungsdaten.getProduktIdentifier()).isNull();
                assertThat(steuerungsdaten.getModifikationsIdentifier()).isNull();
                assertThat(steuerungsdaten.getVorgangsId()).isNull();
            } else {
                assertThat(steuerungsdaten.getProduktIdentifier()).isEqualTo(EXPECTED_PROD_ID);
                assertThat(steuerungsdaten.getModifikationsIdentifier()).isEqualTo(EXPECTED_MOD_ID);
                assertThat(steuerungsdaten.getVorgangsId()).isEqualTo(EXPECTED_VORGANGS_ID);
            }
            assertThat(steuerungsdaten.getAktenzeichenVerursacher()).isEqualTo(expectedAzvu);
            assertThat(steuerungsdaten.getStornierung()).satisfies(stornierung -> {
                assertThat(stornierung).isNotNull();
                assertThat(stornierung.getStornokennzeichen()).isEqualByComparingTo(StornokennzeichenStp.J);
                assertThat(stornierung.getDatensatzIdUrsprungsmeldung()).isEqualTo(EXPECTED_DATENSATZ_ID_URSPRUNGSMELDUNG);
            });
        });
        assertThat(a1AntragSelbststaendige.getAngabenZurPersonA1()).satisfies(angabenZurPerson -> {
            assertThat(angabenZurPerson).isNotNull();
            assertThat(angabenZurPerson.getName()).isNotNull();
            assertThat(angabenZurPerson.getName().getGrundangabenName()).satisfies(angabenName -> {
                assertThat(angabenName).isNotNull();
                assertThat(angabenName.getVorname()).isEqualTo(EXPECTED_VORNAME);
                assertThat(angabenName.getFamilienname()).isEqualTo(EXPECTED_FAMILIENNAME);
            });
        });
        assertThat(a1AntragSelbststaendige.getAngabenEntsendung()).satisfies(angabenEntsendung -> {
            assertThat(angabenEntsendung).isNotNull();
            assertThat(angabenEntsendung.getAngabenZurSelbststaendigenTaetigkeit()).satisfies(angabenZurTaetigkeit -> {
                if (expectedAzvu.contains("Schiff")) {
                    assertThat(angabenZurTaetigkeit.getSchiff()).satisfies(schiff -> {
                        assertThat(schiff).isNotNull();
                        assertThat(schiff.getNameSchiff()).isEqualTo("RV Enterprise");
                        assertThat(schiff.getImoNummer()).isEqualTo("IMO1234567");
                    });
                } else {
                    assertThat(angabenZurTaetigkeit.getAusuebungsortList()).hasSize(11);
                }
                assertThat(angabenZurTaetigkeit.getArtDerTaetigkeitImMitgliedsstaat())
                        .isEqualByComparingTo(KennzeichenAlphanumerischStp.J);
            });
            assertThat(angabenEntsendung.getZeitraumBisherigerEinsatzList()).hasSize(5);
        });
        assertThat(a1AntragSelbststaendige.getAngabenSelbststaendigeTaetigkeitDeutschland()).satisfies(angabenTaetigkeitDeutschland -> {
            assertThat(angabenTaetigkeitDeutschland).isNotNull();
            assertThat(angabenTaetigkeitDeutschland.getGrunddaten()).satisfies(grunddaten -> {
                assertThat(grunddaten).isNotNull();
                assertThat(grunddaten.getName()).isEqualTo("Häberle Test GmbH & Co KG");
                assertThat(grunddaten.getTelefonnummer()).isEqualTo("0123 4567890");
                if (isLegacy) {
                    assertThat(((GrunddatenLegacy) grunddaten).getSteuernummer()).isEqualTo(EXPECTED_STEUERNUMMER);
                }
            });
        });
        assertThat(a1AntragSelbststaendige.getAngabenSelbststaendigeTaetigkeitAusland()).isNotNull();
        assertThat(a1AntragSelbststaendige.getAngabenSelbststaendigeTaetigkeitAusland().getAngaben())
                .isEqualByComparingTo(KennzeichenAlphanumerischJStp.J);
    }


    private static void assertXmlContent(final String xml) {
        assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106a_Anlage_1/3.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Antrag/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Beamte/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Seeleute/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Selbststaendige/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0"
                );
    }


    private static void assertXmlContentLegacy(final String xml) {
        assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Selbststaendige/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_106a/1.0.0"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106_Anlage_",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1A-106a_Anlage_",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Antrag/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Beamte/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Seeleute/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/"
                );
    }


    @Override
    protected JAXBContext createDefaultJaxbContext() throws JAXBException {
        return createJaxbContext(A1AntragSelbststaendige.class, SvtoagA1.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithSvtoagA1() throws JAXBException {
        final List<InputStream> list = new ArrayList<>();
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-selbststaendige-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1a-antrag-selbststaendige.xml"));
        return createJaxbContextWithBindingSourceList(list, A1AntragSelbststaendige.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithClassicSvtoag() {
        return null; // TODO Fälle mit SVTOAG aus xml-sv-ag implementieren
    }

}