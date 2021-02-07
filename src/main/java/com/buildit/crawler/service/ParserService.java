package com.buildit.crawler.service;

import com.buildit.crawler.exception.DomParseException;
import lombok.extern.slf4j.Slf4j;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

@Component
@Slf4j
public class ParserService {

    public Document getDocumentFromString(String docHtml) throws DomParseException {
        return prepareDocument(new InputSource(new StringReader(docHtml)));
    }

    private Document prepareDocument(InputSource is) throws DomParseException {
        try {
            DOMParser parser = new DOMParser();
            parser.parse(is);
            return parser.getDocument();
        } catch (SAXException | IOException ex) {
            log.error("Error parsing Dom document from response String");
            throw new DomParseException("Error parsing Dom document from response String", ex);
        }
    }
}
