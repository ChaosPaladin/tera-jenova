package com.angelis.tera.login.utils.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;


public class XmlCharacterHandler implements CharacterEscapeHandler  {
    @Override
    public void escape(final char[] buf, final int start, final int len, final boolean isAttValue, final Writer out) throws IOException {
        final StringWriter buffer = new StringWriter();

        for (int i = start; i < start + len; i++) {
            buffer.write(buf[i]);
        }

        String st = buffer.toString();
        if (!st.contains("CDATA")) {
            st = buffer.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;").replace("\"", "&quot;");
        }

        out.write(st);
    }
}
