/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ada95_compilador_10611066;

import java.awt.Color;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.*;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author lfborjas
 */
public class HighlightDocumentAda95 extends DefaultStyledDocument{

                private Element rootElement;
                private HashMap<String,Color> keywords;
                private MutableAttributeSet style;

                private Color commentColor = Color.red;
                private Pattern singleLineCommentDelimter = Pattern.compile("//");
                private Pattern multiLineCommentDelimiterStart = Pattern.compile("/\\*");
                private Pattern multiLineCommentDelimiterEnd = Pattern.compile("\\*/");

                public HighlightDocumentAda95() {
                        putProperty( DefaultEditorKit.EndOfLineStringProperty, "\n" );

                        rootElement = getDefaultRootElement();

                        keywords = new HashMap<String, Color>();
                        keywords.put( "abstract", Color.blue);
                        keywords.put( "interface", Color.blue);
                        keywords.put( "class", Color.blue);
                        keywords.put( "extends", Color.blue);
                        keywords.put( "implements", Color.blue);

                        keywords.put( "package", new Color(0,200,0));
                        keywords.put( "import", new Color(0,200,0));
                        keywords.put( "private", new Color(0,200,0));
                        keywords.put( "protected", new Color(0,200,0));
                        keywords.put( "public", new Color(0,200,0));

                        keywords.put( "void", Color.orange);
                        keywords.put( "boolean", Color.orange);
                        keywords.put( "char", Color.orange);
                        keywords.put( "byte", Color.orange);
                        keywords.put( "float", Color.orange);
                        keywords.put( "double", Color.orange);
                        keywords.put( "long", Color.orange);
                        keywords.put( "short", Color.orange);
                        keywords.put( "int", Color.orange);
                        keywords.put( "String", Color.orange);

                        keywords.put( "true", Color.red);
                        keywords.put( "false", Color.red);
                        keywords.put( "const", Color.red);
                        keywords.put( "null", Color.red);

                        keywords.put( "break", Color.blue);
                        keywords.put( "case", Color.blue);
                        keywords.put( "catch", Color.blue);
                        keywords.put( "operator", Color.blue);
                        keywords.put( "continue", Color.blue);
                        keywords.put( "default", Color.blue);
                        keywords.put( "do", Color.blue);
                        keywords.put( "else", Color.blue);
                        keywords.put( "final", Color.blue);
                        keywords.put( "finally", Color.blue);
                        keywords.put( "for", Color.blue);
                        keywords.put( "if", Color.blue);
                        keywords.put( "instanceof", Color.blue);
                        keywords.put( "native", Color.blue);
                        keywords.put( "new", Color.blue);
                        keywords.put( "return", Color.blue);
                        keywords.put( "static", Color.blue);
                        keywords.put( "super", Color.blue);
                        keywords.put( "switch", Color.blue);
                        keywords.put( "synchronized", Color.blue);
                        keywords.put( "this", Color.blue);
                        keywords.put( "Thread", Color.blue);
                        keywords.put( "throw", Color.blue);
                        keywords.put( "throws", Color.blue);
                        keywords.put( "transient", Color.blue);
                        keywords.put( "try", Color.blue);
                        keywords.put( "volatile", Color.blue);
                        keywords.put( "while", Color.blue);

                        style = new SimpleAttributeSet();
                }
                @Override
                public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
                {
                        super.insertString(offset, str, attr);
                        processChangedLines(offset, str.length());
                }
                @Override
                public void remove(int offset, int length) throws BadLocationException
                {
                        super.remove(offset, length);
                        processChangedLines(offset, length);
                }

                public void processChangedLines(int offset, int length) throws BadLocationException
                {
                        String text = getText(0, getLength());
                        highlightString(Color.black, 0, getLength(), true, false);

                        Set<String> keyw = keywords.keySet();

                        for (String keyword : keyw) {
                                Color col = keywords.get(keyword);

                                Pattern p = Pattern.compile("\\b" + keyword + "\\b");
                                Matcher m = p.matcher(text);

                                while(m.find()) {
                                        highlightString(col, m.start(), keyword.length(), true, true);
                                }
                        }

                        Matcher mlcStart = multiLineCommentDelimiterStart.matcher(text);
                        Matcher mlcEnd = multiLineCommentDelimiterEnd.matcher(text);

                        while(mlcStart.find()) {
                                if(mlcEnd.find( mlcStart.end() ))
                                        highlightString(commentColor, mlcStart.start(), (mlcEnd.end()-mlcStart.start()), true, true);
                                else
                                        highlightString(commentColor, mlcStart.start(), getLength(), true, true);
                        }

                         Matcher slc = singleLineCommentDelimter.matcher(text);

                        while(slc.find()) {
                                int line = rootElement.getElementIndex(slc.start());
                                int endOffset = rootElement.getElement(line).getEndOffset() - 1;

                                highlightString(commentColor, slc.start(), (endOffset-slc.start()), true, true);
                        }
                }

                public void highlightString(Color col, int begin, int length, boolean flag, boolean bold)
                {
                        StyleConstants.setForeground(style, col);

                        setCharacterAttributes(begin, length, style, flag);
                }
        }

        class highlightKit extends StyledEditorKit {
            @Override
                public Document createDefaultDocument()
                {
                        return new HighlightDocumentAda95();
                }


}
