package com.cyberway.core.utils.jdom;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author Marc Huang
 * 
 */
public class DataFormatFilter extends XMLFilterBase {

	/**
	 * Create a new filter.
	 */
	public DataFormatFilter() {
	}

	/**
	 * Create a new filter.
	 * 
	 * <p>
	 * Use the XMLReader provided as the source of events.
	 * </p>
	 * 
	 * @param xmlreader
	 *            The parent in the filter chain.
	 */
	public DataFormatFilter(XMLReader xmlreader) {
		super(xmlreader);
	}

	/**
	 * Return the current indent step.
	 * 
	 * <p>
	 * Return the current indent step: each start tag will be indented by this
	 * number of spaces times the number of ancestors that the element has.
	 * </p>
	 * 
	 * @return The number of spaces in each indentation step, or 0 or less for
	 *         no indentation.
	 * @see #setIndentStep
	 */
	public int getIndentStep() {
		return indentStep;
	}

	/**
	 * Set the current indent step.
	 * 
	 * @param indentStep
	 *            The new indent step (0 or less for no indentation).
	 * @see #getIndentStep
	 */
	public void setIndentStep(int indentStep) {
		this.indentStep = indentStep;
	}

	/**
	 * Reset the filter so that it can be reused.
	 * 
	 * <p>
	 * This method is especially useful if the filter failed with an exception
	 * the last time through.
	 * </p>
	 */
	public void reset() {
		state = SEEN_NOTHING;
		stateStack = new Stack();
	}

	/**
	 * Filter a start document event.
	 * 
	 * <p>
	 * Reset state and pass the event on for further processing.
	 * </p>
	 * 
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#startDocument
	 */
	public void startDocument() throws SAXException {
		reset();
		super.startDocument();
	}

	/**
	 * Add newline and indentation prior to start tag.
	 * 
	 * <p>
	 * Each tag will begin on a new line, and will be indented by the current
	 * indent step times the number of ancestors that the element has.
	 * </p>
	 * 
	 * <p>
	 * The newline and indentation will be passed on down the filter chain
	 * through regular characters events.
	 * </p>
	 * 
	 * @param uri
	 *            The element's Namespace URI.
	 * @param localName
	 *            The element's local name.
	 * @param qName
	 *            The element's qualified (prefixed) name.
	 * @param atts
	 *            The element's attribute list.
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#startElement
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (!stateStack.empty()) {
			doNewline();
			doIndent();
		}
		stateStack.push(SEEN_ELEMENT);
		state = SEEN_NOTHING;
		super.startElement(uri, localName, qName, atts);
	}

	/**
	 * Add newline and indentation prior to end tag.
	 * 
	 * <p>
	 * If the element has contained other elements, the tag will appear indented
	 * on a new line; otherwise, it will appear immediately following whatever
	 * came before.
	 * </p>
	 * 
	 * <p>
	 * The newline and indentation will be passed on down the filter chain
	 * through regular characters events.
	 * </p>
	 * 
	 * @param uri
	 *            The element's Namespace URI.
	 * @param localName
	 *            The element's local name.
	 * @param qName
	 *            The element's qualified (prefixed) name.
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#endElement
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		boolean seenElement = (state == SEEN_ELEMENT);
		state = stateStack.pop();
		if (seenElement) {
			doNewline();
			doIndent();
		}
		super.endElement(uri, localName, qName);
	}

	/**
	 * Filter a character data event.
	 * 
	 * @param ch
	 *            The characters to write.
	 * @param start
	 *            The starting position in the array.
	 * @param length
	 *            The number of characters to use.
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#characters
	 */
	public void characters(char ch[], int start, int length)
			throws SAXException {
		state = SEEN_DATA;
		super.characters(ch, start, length);
	}

	/**
	 * Add newline.
	 * 
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 */
	private void doNewline() throws SAXException {
		super.characters(NEWLINE, 0, NEWLINE.length);
	}

	/**
	 * Add indentation for the current level.
	 * 
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 */
	private void doIndent() throws SAXException {
		int n = indentStep * stateStack.size();
		if (n > 0) {
			char ch[] = new char[n];
			for (int i = 0; i < n; i++) {
				ch[i] = INDENT_CHAR;
			}
			super.characters(ch, 0, n);
		}
	}

	private static final Object SEEN_NOTHING = new Object();

	private static final Object SEEN_ELEMENT = new Object();

	private static final Object SEEN_DATA = new Object();

	private static final char[] NEWLINE = new char[] { '\n' };

	private static final char INDENT_CHAR = ' ';

	private Object state = SEEN_NOTHING;

	private Stack stateStack = new Stack();

	private int indentStep = 0;

}
