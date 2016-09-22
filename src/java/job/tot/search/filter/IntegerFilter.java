/*
 * IntegerFilter.java
 *
 * Created on 2006��12��7��, ����3:09
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.search.filter;
import java.io.IOException;
import java.util.BitSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.Filter;

/**
 * A Filter that restricts search results to a range of integer.
 *
 * <p>
 * For this to work, documents must have been indexed with a
 */
public class IntegerFilter extends Filter {
    String field;
    String start = intToString(0);
    String end = intToString(Integer.MAX_VALUE);

    private IntegerFilter(String f) {
        field = f;
    }

    /**
     * Constructs a filter for field <code>f</code> matching Integers between
     * <code>from</code> and <code>to</code> inclusively.
     */
    public IntegerFilter(String field, int from, int to) {
        this.field = field;
        start = intToString(from);
        end = intToString(to);
    }

    /**
     * Constructs a filter for field <code>f</code> matching Integers between
     * <code>from</code> and <code>to</code> inclusively.
     */
    public IntegerFilter(String field, Integer from, Integer to) {
        this.field = field;
        start = intToString(from.intValue());
        end = intToString(to.intValue());
    }

    /**
     * Constructs a filter for field <code>f</code> matching Integers on or
     * more than <code>Integer</code>.
     */
    public static IntegerFilter greaterThan(String field, Integer i) {
        IntegerFilter result = new IntegerFilter(field);
        result.start = intToString(i.intValue());
        return result;
    }

    /**
     * Constructs a filter for field <code>f</code> matching Integers on or
     * more than <code>Integer</code>.
     */
    public static IntegerFilter greaterThan(String field, int i) {
        IntegerFilter result = new IntegerFilter(field);
        result.start = intToString(i);
        return result;
    }

    /**
     * Constructs a filter for field <code>f</code> matching integers on or
     * less than <code>Integer</code>.
     */
    public static IntegerFilter lessThan(String field, Integer i) {
        IntegerFilter result = new IntegerFilter(field);
        result.end = intToString(i.intValue());
        return result;
    }

    /**
     * Constructs a filter for field <code>f</code> matching Integers on or
     * more than <code>Integer</code>.
     */
    public static IntegerFilter lessThan(String field, int i) {
        IntegerFilter result = new IntegerFilter(field);
        result.end = intToString(i);
        return result;
    }

    public static String intToString(int i) {
        return Integer.toString(i, Character.MAX_RADIX);
    }

    public static int stringToInt(String i) {
        return Integer.parseInt(i, Character.MAX_RADIX);
    }

    /**
     * Returns a BitSet with true for documents which should be permitted in
     * search results, and false for those that should not.
     */
    public BitSet bits(IndexReader reader) throws IOException {
        BitSet bits = new BitSet(reader.maxDoc());
        TermEnum enumerator = reader.terms(new Term(field, start));
        TermDocs termDocs = reader.termDocs();
        if (enumerator.term() == null) {
            return bits;
        }
        try {
            Term stop = new Term(field, end);
            while (enumerator.term().compareTo(stop) <= 0) {
                termDocs.seek(enumerator.term());
                while (termDocs.next()) {
                    bits.set(termDocs.doc());
                }
                if (!enumerator.next()) {
                    break;
                }
            }
        } finally {
            enumerator.close();
            termDocs.close();
        }
        return bits;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(field);
        buffer.append(":");
        buffer.append(start);
        buffer.append("-");
        buffer.append(end);
        return buffer.toString();
    }
}
