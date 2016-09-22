/*
 * CombineFilter.java
 *
 * Created on 2006��12��7��, ����3:08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.search.filter;
import java.io.IOException;
import java.util.BitSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
/**
 *
 * @author Administrator
 */
public class CombineFilter extends Filter {

    private Filter leftFilter;

    private Filter rightFilter;

    public CombineFilter(Filter f1, Filter f2) {
        if ((f1 == null) && (f2 == null)) {
            throw new IllegalArgumentException("At least f1 or f2 must not be null.");
        }
        leftFilter = f1;
        rightFilter = f2;
    }

    public BitSet bits(IndexReader reader) throws IOException {

        BitSet resultBits = null;
        if ((leftFilter != null) && (rightFilter != null)) {
            BitSet leftBits = leftFilter.bits(reader);
            BitSet rightBits = rightFilter.bits(reader);
            resultBits = (BitSet) leftBits.clone();
            resultBits.and(rightBits);
        } else if (leftFilter != null) {
            resultBits = leftFilter.bits(reader);
        } else if (rightFilter != null) {
            resultBits = rightFilter.bits(reader);
        } else {
            // should never happen since we have checked it in the constructor
            throw new IllegalArgumentException("At least f1 or f2 must not be null.");
        }
        return resultBits;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(leftFilter);
        buffer.append("\n");
        buffer.append(rightFilter);
        return buffer.toString();
    }
}
