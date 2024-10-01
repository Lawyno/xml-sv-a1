package de.lawyno.xml.sv.a1.bescheid.ablehnung;


import de.lawyno.xml.sv.a1.basis.*;
import de.lawyno.xml.sv.a1.steuerungssatz.RueckmeldungenA1;
import de.lawyno.xml.sv.a1.test.AbstractXmlTest;
import de.lawyno.xml.sv.ag.svtoag.Svtoag;
import de.lawyno.xml.sv.ag300.basis.NamensvorsatzStp;
import de.lawyno.xml.sv.ag300.basis.NamenszusatzStp;
import de.lawyno.xml.sv.ag300.svtoag.SvtoagA1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

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


class A1AblehnungTest extends AbstractXmlTest {

    private static final String XML_FILE_MAX_SVTOAG_LEGACY = "/xml/svtoag/v2.0.0/ablehnung/v1.3.0/ablehnung-max.xml";
    private static final String XML_FILE_MAX_SVTOAG_A1 = "/xml/svtoag/v3.0.0/ablehnung/v2.0.0/ablehnung-max.xml";

    private static final String XML_ABSENDER = "66667777";
    private static final String XML_EMPFAENGER = "11223344";
    private static final String XML_DATENSATZ_ID = "datensatz-2000";
    private static final String XML_VORGANGS_ID = "vorgang-1000";
    private static final LocalDateTime XML_DATUM_ERSTELLUNG = LocalDateTime.of(2024, 9, 15, 8, 15, 30);
    private static final String XML_VSNR = "12345678X910";
    private static final byte[] XML_BESCHEINIGUNG_PDF = "Pseudo-PDF-Datei".getBytes();

    private static final String XML_HEADER_VFMM = "A1S";
    private static final String XML_HEADER_ABSENDER = "66667777";
    private static final String XML_HEADER_EMPFAENGER = "11223344";
    private static final LocalDateTime XML_HEADER_DATUM_ERSTELLUNG = LocalDateTime.of(2024, 9, 15, 8, 15, 30);
    private static final String XML_HEADER_DATEIFOLGENUMMER = "123456";
    private static final int XML_ABLEHNUNGSGRUND = 42;


    @Test
    void javaToXml() throws JAXBException, IOException, SAXException {
        final A1Ablehnung a1Ablehnung = createA1Ablehnung(XML_VERSION_BESCHEID);
        final Marshaller marshaller = createDefaultMarshaller();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(a1Ablehnung, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v3.0.0/A1S-Ablehnung-2.0.0.xsd", xml);

        Assertions.assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Ablehnung/2.0",
                        "Versionsnummer=\"2.0.0\""
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Simple_A1/1.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Bewilligung/1.3",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Bewilligung/2.0",
                        "Versionsnummer=\"1.3.0\"",
                        "Versionsnummer=\"1.0.0\""
                );
    }


    @Test
    void javaToXml_inSvtoagA1() throws JAXBException, IOException, SAXException {
        final A1Ablehnung a1Ablehnung = createA1Ablehnung(XML_VERSION_BESCHEID);
        final SvtoagA1 svtoagA1 = createSvtoagA1(XML_VERSION_HEADER, a1Ablehnung);
        final Marshaller marshaller = createDefaultMarshaller();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(svtoagA1, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v3.0.0/SV_Header_SVTOAG_A1_3.0.0.xsd", xml);

        Assertions.assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/SV_Header_SVTOAG/3.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Ablehnung/2.0",
                        "Versionsnummer=\"3.0.0\"",
                        "Versionsnummer=\"2.0.0\""
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Simple_A1/1.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Bewilligung/1.3",
                        "Versionsnummer=\"1.3.0\"",
                        "Versionsnummer=\"1.0.0\""
                );
    }


    @Test
    void javaToXml_legacy() throws JAXBException, IOException, SAXException {
        final A1Ablehnung a1Ablehnung = createA1Ablehnung(XML_VERSION_BESCHEID_LEGACY);
        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(a1Ablehnung, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v2.0.0/A1_Ablehnung_V1_3_0.xsd", xml);

        Assertions.assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3",
                        "Versionsnummer=\"1.3.0\""
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Ablehnung/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Bewilligung/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Simple_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Bewilligung/1.3",
                        "Versionsnummer=\"2.0.0\""
                );
    }


    @Test
    void javaToXml_inSvtoagA1_legacy() throws JAXBException, IOException, SAXException {
        final A1Ablehnung a1Ablehnung = createA1Ablehnung(XML_VERSION_BESCHEID_LEGACY);
        final SvtoagA1 svtoagA1 = createSvtoagA1(XML_VERSION_HEADER_LEGACY, a1Ablehnung);
        final Marshaller marshaller = createLegacyMarshallerWithSvtoagA1();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(svtoagA1, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v2.0.0/SV_Header_SVTOAG.xsd", xml);

        Assertions.assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/SV_Header_SVTOAG/1.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Ablehnung/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Bewilligung/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Simple_A1/1.0"
                );
    }


    @Test
    void javaToXml_inClassicSvtoag_legacy() throws JAXBException, IOException, SAXException {
        final A1Ablehnung a1Ablehnung = createA1Ablehnung(XML_VERSION_BESCHEID_LEGACY);
        final Svtoag svtoag = createSvtoag(XML_VERSION_HEADER_LEGACY, a1Ablehnung);
        final Marshaller marshaller = createLegacyMarshallerWithClassicSvtoag();
        final String xml;

        try (final StringWriter writer = new StringWriter()) {
            marshaller.marshal(svtoag, writer);
            xml = writer.toString();
        }
        System.out.println(xml);

        assertThatXmlIsValid("/xsd/v2.0.0/SV_Header_SVTOAG.xsd", xml);

        Assertions.assertThat(xml)
                .contains(
                        "http://www.gkv-datenaustausch.de/XMLSchema/SV_Header_SVTOAG/1.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Complex_A1/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1_Ablehnung/1.3"
                )
                .doesNotContain(
                        "http://www.gkv-datenaustausch.de/XMLSchema/106-Basis/1.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Ablehnung/2.0",
                        "http://www.gkv-datenaustausch.de/XMLSchema/A1S-Bewilligung/2.0",
                        "http://www.gkv-datenaustausch.de/Grundsaetze_Technik/XML/Basistypen_Simple_A1/1.0"
                );
    }


    @Test
    void xmlToJava_inSvtoagA1() throws JAXBException {
        final Unmarshaller unmarshaller = createDefaultUnmarshaller();
        final Object o = unmarshaller.unmarshal(System.class.getResource(XML_FILE_MAX_SVTOAG_A1));
        assertThat(o).isInstanceOf(SvtoagA1.class);
        assertSvtoagA1WithAblehnung((SvtoagA1) o, XML_VERSION_HEADER, XML_VERSION_BESCHEID);
    }


    @Test
    void xmlToJava_inSvtoagA1_legacy() throws JAXBException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithSvtoagA1();
        final Object o = unmarshaller.unmarshal(System.class.getResource(XML_FILE_MAX_SVTOAG_LEGACY));
        assertThat(o).isInstanceOf(SvtoagA1.class);
        assertSvtoagA1WithAblehnung((SvtoagA1) o, XML_VERSION_HEADER_LEGACY, XML_VERSION_BESCHEID_LEGACY);
    }


    @Test
    void xmlToJava_inClassicSvtoag_legacy() throws JAXBException {
        final Unmarshaller unmarshaller = createLegacyUnmarshallerWithClassicSvtoag();
        final Object o = unmarshaller.unmarshal(System.class.getResource(XML_FILE_MAX_SVTOAG_LEGACY));
        assertThat(o).isInstanceOf(Svtoag.class);
        assertClassicSvtoagWithAblehnung((Svtoag) o, XML_VERSION_HEADER_LEGACY, XML_VERSION_BESCHEID_LEGACY);
    }


    private void assertSvtoagA1WithAblehnung(final SvtoagA1 svtoagA1,
                                             final String expectedHeaderVersion,
                                             final String expectedAblehnungVersion) {
        assertThat(svtoagA1).isNotNull();
        assertThat(svtoagA1.getVersionsnummer()).isEqualTo(expectedHeaderVersion);

        assertThat(svtoagA1.getHeader()).isNotNull();
        assertThat(svtoagA1.getHeader().getVersionsnummer()).isEqualTo(expectedHeaderVersion);
        assertThat(svtoagA1.getHeader().getVorlaufsatz()).isNotNull();
        assertThat(svtoagA1.getHeader().getVorlaufsatz().getVerfahrensmerkmal()).isEqualTo(XML_HEADER_VFMM);
        assertThat(svtoagA1.getHeader().getVorlaufsatz().getDateifolgenummer()).isEqualTo(XML_HEADER_DATEIFOLGENUMMER);

        assertThat(svtoagA1.getBody()).isNotNull();
        assertThat(svtoagA1.getBody().getVerarbeitungsergebnis()).isNull();
        assertThat(svtoagA1.getBody().getVerfahren()).isNotNull();
        assertThat(svtoagA1.getBody().getVerfahren().getA1BewilligungList()).isEmpty();
        assertThat(svtoagA1.getBody().getVerfahren().getA1AblehnungList())
                .hasSize(1)
                .satisfiesExactly(a1Ablehnung -> assertAblehnung(a1Ablehnung, expectedAblehnungVersion));
    }


    private void assertClassicSvtoagWithAblehnung(final Svtoag svtoag,
                                                  final String expectedHeaderVersion,
                                                  final String expectedAblehnungVersion) {
        assertThat(svtoag).isNotNull();
        assertThat(svtoag.getVersionsnummer()).isEqualTo(expectedHeaderVersion);

        assertThat(svtoag.getHeader()).satisfies(header -> {
            assertThat(header).isNotNull();
            assertThat(header.getVersionsnummer()).isEqualTo(expectedHeaderVersion);
            assertThat(header.getVorlaufsatz()).isNotNull();
            assertThat(header.getVorlaufsatz().getVerfahrensmerkmal()).isEqualTo(XML_HEADER_VFMM);
            assertThat(header.getVorlaufsatz().getDateifolgenummer()).isEqualTo(XML_HEADER_DATEIFOLGENUMMER);
        });

        assertThat(svtoag.getBody()).isNotNull();
        assertThat(svtoag.getBody().getVerarbeitungsergebnis()).isNull();
        assertThat(svtoag.getBody().getVerfahren()).isNotNull();
        assertThat(svtoag.getBody().getVerfahren().getAny()).satisfies(any -> {
            assertThat(any).isInstanceOf(RueckmeldungenA1.class);
            final RueckmeldungenA1 rueckmeldungenA1 = (RueckmeldungenA1) any;
            assertThat(rueckmeldungenA1.getA1BewilligungList()).isEmpty();
            assertThat(rueckmeldungenA1.getA1AblehnungList())
                    .hasSize(1)
                    .satisfiesExactly(a1Ablehnung -> assertAblehnung(a1Ablehnung, expectedAblehnungVersion));
        });
    }


    private void assertAblehnung(final A1Ablehnung a1Ablehnung, final String expectedVersion) {
        assertThat(a1Ablehnung).isNotNull();
        assertThat(a1Ablehnung.getVersionsnummer()).isEqualTo(expectedVersion);
        assertThat(a1Ablehnung.getAblehnungsgrund()).isEqualTo(XML_ABLEHNUNGSGRUND);
        assertThat(a1Ablehnung.getBescheinigungPdfList()).containsExactly(XML_BESCHEINIGUNG_PDF);
        assertThat(a1Ablehnung.getSteuerungsdaten()).satisfies(steuerungsdaten -> {
            assertThat(steuerungsdaten).isNotNull();
            assertThat(steuerungsdaten.getAbsendernummer()).isEqualTo(XML_ABSENDER);
            assertThat(steuerungsdaten.getEmpfaengernummer()).isEqualTo(XML_EMPFAENGER);
            assertThat(steuerungsdaten.getDatensatzId()).isEqualTo(XML_DATENSATZ_ID);
            assertThat(steuerungsdaten.getVorgangsId()).isEqualTo(XML_VORGANGS_ID);
        });
        assertThat(a1Ablehnung.getAngabenZurPerson()).satisfies(angabenPerson -> {
            assertThat(angabenPerson).isNotNull();
            assertThat(angabenPerson.getVersicherungsnummer()).isEqualTo(XML_VSNR);
            assertThat(angabenPerson.getGrundangabeName()).satisfies(angabenName -> {
                assertThat(angabenName).isNotNull();
                assertThat(angabenName.getTitel()).isEqualTo("Dr. Prof.");
                assertThat(angabenName.getVorname()).isEqualTo("Max");
                assertThat(angabenName.getFamilienname()).isEqualTo("Mustermann");
                assertThat(angabenName.getNamenszusatz()).isEqualByComparingTo(NamenszusatzStp.fromValue("Landgraf"));
                assertThat(angabenName.getVorsatzwort()).isEqualByComparingTo(NamensvorsatzStp.fromValue("von und zu"));
                assertThat(angabenName.getGeschlecht()).isEqualByComparingTo(KennzeichenMwxStp.M);
            });
        });
        assertThat(a1Ablehnung.getAngabenAntragsteller()).isNotNull();
    }


    private A1Ablehnung createA1Ablehnung(final String version) {
        return new A1Ablehnung()
                .withVersionsnummer(version)
                .withSteuerungsdaten(new A1Ablehnung.Steuerungsdaten()
                        .withAbsendernummer(XML_ABSENDER)
                        .withEmpfaengernummer(XML_EMPFAENGER)
                        .withDatensatzId(XML_DATENSATZ_ID)
                        .withVorgangsId(XML_VORGANGS_ID)
                        .withDatumErstellung(XML_DATUM_ERSTELLUNG)
                        .withStornokennzeichen(StornokennzeichenStp.J)
                )
                .withAngabenZurPerson(new AngabenPersonRueckmeldungCtp()
                        .withGrundangabeName(new AngabenNameCtp()
                                .withGeschlecht(KennzeichenMwxStp.M)
                                .withVorname("Max")
                                .withFamilienname("Mustermann")
                                .withTitel("Dr. Prof.")
                                .withNamenszusatz(NamenszusatzStp.fromValue("Landgraf"))
                                .withVorsatzwort(NamensvorsatzStp.fromValue("von und zu"))
                        )
                        .withVersicherungsnummer(XML_VSNR)
                )
                .withAngabenAntragsteller(new AngabenAntragstellerRueckmeldungCtp()
                        .withAzvuUrsprungsmeldung("azvu-999")
                        .withBbnrVu("12345678")
                        .withDatensatzIdUrsprungsmeldung("datensatz-1000")
                )
                .withBescheinigungPdfList(XML_BESCHEINIGUNG_PDF)
                .withAblehnungsgrund(XML_ABLEHNUNGSGRUND);
    }


    private SvtoagA1 createSvtoagA1(final String version, final A1Ablehnung a1Ablehnung) {
        return new SvtoagA1()
                .withHeader(new SvtoagA1.SendungsHeaderSvtoag()
                        .withVersionsnummer(version)
                        .withVorlaufsatz(new SvtoagA1.SendungsHeaderSvtoag.Vorlaufsatz()
                                .withVerfahrensmerkmal(XML_HEADER_VFMM)
                                .withAbsendernummer(XML_HEADER_ABSENDER)
                                .withEmpfaengernummer(XML_HEADER_EMPFAENGER)
                                .withDatumErstellung(XML_HEADER_DATUM_ERSTELLUNG)
                                .withDateifolgenummer(XML_HEADER_DATEIFOLGENUMMER)
                        )
                )
                .withBody(new SvtoagA1.SendungsBody()
                        .withVerfahren(new SvtoagA1.SendungsBody.Verfahren()
                                .withA1AblehnungList(a1Ablehnung)
                        )
                )
                .withVersionsnummer(version);
    }


    private Svtoag createSvtoag(final String version, final A1Ablehnung a1Ablehnung) {
        return new Svtoag()
                .withHeader(new Svtoag.SendungsHeaderSvtoag()
                        .withVersionsnummer(version)
                        .withVorlaufsatz(new Svtoag.SendungsHeaderSvtoag.Vorlaufsatz()
                                .withVerfahrensmerkmal(XML_HEADER_VFMM)
                                .withAbsendernummer(XML_HEADER_ABSENDER)
                                .withEmpfaengernummer(XML_HEADER_EMPFAENGER)
                                .withDatumErstellung(XML_HEADER_DATUM_ERSTELLUNG)
                                .withDateifolgenummer(XML_HEADER_DATEIFOLGENUMMER)
                        )
                )
                .withBody(new Svtoag.SendungsBody()
                        .withVerfahren(new Svtoag.SendungsBody.Verfahren()
                                .withAny(new RueckmeldungenA1()
                                        .withA1AblehnungList(a1Ablehnung)
                                )
                        )
                )
                .withVersionsnummer(version);
    }


    @Override
    protected JAXBContext createDefaultJaxbContext() throws JAXBException {
        return createJaxbContext(A1Ablehnung.class, SvtoagA1.class);
        // Hier genügt die Default-JAXB-Implementierung (...leider nicht!)
//        return JAXBContext.newInstance(A1Ablehnung.class, SvtoagA1.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithSvtoagA1() throws JAXBException {
        final List<InputStream> list = new ArrayList<>();
        list.add(ClassLoader.getSystemResourceAsStream("oxm/106-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-ablehnung-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-ablehnung.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-bewilligung.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-svtoag.xml"));

        // Abwärtskompatibilität durch MOXy-JAXB-Implementierung
        return createJaxbContextWithBindingSourceList(list, A1Ablehnung.class, SvtoagA1.class);
    }


    @Override
    protected JAXBContext createLegacyJaxbContextWithClassicSvtoag() throws JAXBException {
        final List<Object> list = new ArrayList<>();
        list.add(ClassLoader.getSystemResourceAsStream("oxm/106-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-ablehnung-basis.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-ablehnung.xml"));
        list.add(ClassLoader.getSystemResourceAsStream("oxm/a1s-bewilligung.xml"));

        // Abwärtskompatibilität durch MOXy-JAXB-Implementierung
        return createJaxbContextWithBindingSourceList(
                list, A1Ablehnung.class, RueckmeldungenA1.class, Svtoag.class);
    }

}