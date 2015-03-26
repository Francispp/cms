package com.cyberway.core.utils.jdom;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author Marc Huang
 * 
 */
public class DataUnformatFilter extends XMLFilterBase {

	/**
	 * Create a new filter.
	 */
	public DataUnformatFilter() {
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
	public DataUnformatFilter(XMLReader xmlreader) {
		super(xmlreader);
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
		whitespace = new StringBuffer();
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
	 * Filter a start element event.
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
		clearWhitespace();
		stateStack.push(SEEN_ELEMENT);
		state = SEEN_NOTHING;
		super.startElement(uri, localName, qName, atts);
	}

	/**
	 * Filter an end element event.
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
		if (state == SEEN_ELEMENT) {
			clearWhitespace();
		} else {
			emitWhitespace();
		}
		state = stateStack.pop();
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
		if (state != SEEN_DATA) {

			/* Look for non-whitespace. */

			int end = start + length;
			while (end-- > start) {
				if (!isXMLWhitespace(ch[end]))
					break;
			}

			/*
			 * If all the characters are whitespace, save them for later. If
			 * we've got some data, emit any saved whitespace and update our
			 * state to show we've seen data.
			 */

			if (end < start) {
				saveWhitespace(ch, start, length);
			} else {
				state = SEEN_DATA;
				emitWhitespace();
			}
		}

		/* Pass on everything inside a data field. */

		if (state == SEEN_DATA) {
			super.characters(ch, start, length);
		}
	}

	/**
	 * Filter an ignorable whitespace event.
	 * 
	 * @param ch
	 *            The array of characters to write.
	 * @param start
	 *            The starting position in the array.
	 * @param length
	 *            The number of characters to write.
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace
	 */
	public void ignorableWhitespace(char ch[], int start, int length)
			throws SAXException {
		emitWhitespace();
		// ignore
	}

	/**
	 * Filter a processing instruction event.
	 * 
	 * @param target
	 *            The PI target.
	 * @param data
	 *            The PI data.
	 * @exception org.xml.sax.SAXException
	 *                If a filter further down the chain raises an exception.
	 * @see org.xml.sax.ContentHandler#processingInstruction
	 */
	public void processingInstruction(String target, String data)
			throws SAXException {
		emitWhitespace();
		super.processingInstruction(target, data);
	}

	/**
	 * Saves trailing whitespace.
	 */
	protected void saveWhitespace(char[] ch, int start, int length) {
		whitespace.append(ch, start, length);
	}

	/**
	 * Passes saved whitespace down the filter chain.
	 */
	protected void emitWhitespace() throws SAXException {
		char[] data = new char[whitespace.length()];
		whitespace.getChars(0, data.length, data, 0);
		whitespace.setLength(0);
		super.characters(data, 0, data.length);
	}

	/**
	 * Discards saved whitespace.
	 */
	protected void clearWhitespace() {
		whitespace.setLength(0);
	}

	/**
	 * Returns <var>true</var> if character is XML whitespace.
	 */
	private boolean isXMLWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	private static final Object SEEN_NOTHING = new Object();

	private static final Object SEEN_ELEMENT = new Object();

	private static final Object SEEN_DATA = new Object();

	private Object state = SEEN_NOTHING;

	private Stack stateStack = new Stack();

	private StringBuffer whitespace = new StringBuffer();

}
