package de.lawyno.xml.sv.a1.test;


import de.lawyno.xml.IXmlRoot;
import de.lawyno.xml.validation.XmlValidator;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatNoException;


public abstract class AbstractXmlTest {

    protected static final String XML_VERSION_HEADER = "3.0.0";
    protected static final String XML_VERSION_HEADER_LEGACY = "1.0.0";

    protected static final String XML_VERSION_ANTRAG = "3.0.0";
    protected static final String XML_VERSION_ANTRAG_LEGACY = "2.0.0";

    protected static final String XML_VERSION_BESCHEID = "2.0.0";
    protected static final String XML_VERSION_BESCHEID_LEGACY = "1.3.0";


    protected static void assertThatXmlIsValid(final String xsdPath, final String xml) {
        assertThatNoException().isThrownBy(() -> new XmlValidator(xsdPath, xml).validate());
    }


    private Marshaller createMarshaller(final JAXBContext jaxbContext)
    throws JAXBException {
        final Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.ISO_8859_1.name());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }


    protected Marshaller createDefaultMarshaller() throws JAXBException {
        return createMarshaller(createDefaultJaxbContext());
    }


    protected Marshaller createLegacyMarshallerWithSvtoagA1() throws JAXBException {
        return createMarshaller(createLegacyJaxbContextWithSvtoagA1());
    }


    protected Marshaller createLegacyMarshallerWithClassicSvtoag() throws JAXBException {
        return createMarshaller(createLegacyJaxbContextWithClassicSvtoag());
    }


    private Unmarshaller createUnmarshaller(final JAXBContext jaxbContext)
    throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }


    protected Unmarshaller createDefaultUnmarshaller() throws JAXBException {
        return createUnmarshaller(createDefaultJaxbContext());
    }


    protected Unmarshaller createLegacyUnmarshallerWithSvtoagA1() throws JAXBException {
        return createUnmarshaller(createLegacyJaxbContextWithSvtoagA1());
    }


    protected Unmarshaller createLegacyUnmarshallerWithClassicSvtoag() throws JAXBException {
        return createUnmarshaller(createLegacyJaxbContextWithClassicSvtoag());
    }


    @SafeVarargs
    protected final JAXBContext createJaxbContext(final Class<? extends IXmlRoot>... classes)
    throws JAXBException {
        return createJaxbContextWithMap(null, classes);
    }


    protected JAXBContext createJaxbContextWithBindingSourceList(final List<?> bindingSourceList, final Class<?>... classes)
    throws JAXBException {
        final Map<String, Object> contextMap = new HashMap<>();
        contextMap.put(JAXBContextProperties.OXM_METADATA_SOURCE, bindingSourceList);
        return createJaxbContextWithMap(contextMap, classes);
    }


    protected JAXBContext createJaxbContextWithMap(final Map<String, Object> contextMap, final Class<?>... classes)
    throws JAXBException {
        return JAXBContextFactory.createContext(classes, contextMap);
    }


    protected abstract JAXBContext createDefaultJaxbContext() throws JAXBException;

    protected abstract JAXBContext createLegacyJaxbContextWithSvtoagA1() throws JAXBException;

    protected abstract JAXBContext createLegacyJaxbContextWithClassicSvtoag() throws JAXBException;

}
