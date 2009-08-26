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
 * Clase para resaltar sintaxis de ada95
 * @author lfborjas
 * Copiado de <link>http://www.dreamincode.net/forums/blog/abgorn/index.php?showentry=1048</link>
 * Usado bajo la GPL
 * En base al manual de referencia de ADA95:
 *      abort          else           new            return
        abs            elsif          not            reverse
        abstract       end            null
        accept         entry                         select
        access         exception                     separate
        aliased        exit           of             subtype
        all                           or
        and            for            others         tagged
        array          function       out            task
        at                                           terminate
                       generic        package        then
        begin          goto           pragma         type
        body                          private
                       if             procedure
        case           in             protected      until
        constant       is                            use
                                      raise
        declare                       range          when
        delay          limited        record         while
        delta          loop           rem            with
        digits                        renames
        do             mod            requeue        xor

 */
public class HighlightDocumentAda95 extends DefaultStyledDocument{

                private Element rootElement;
                private HashMap<String,Color> keywords;
                private MutableAttributeSet style;

                private Color commentColor = Color.lightGray;
                private Color stringColor = Color.orange;
                private Pattern singleLineCommentDelimter = Pattern.compile("--");
                private Pattern stringLiteral = Pattern.compile("\"[^\"]*\"");
                private Pattern functionName=Pattern.compile("[a-zA-Z_]*[(]");

                //private Pattern multiLineCommentDelimiterStart = Pattern.compile("/\\*");
                //private Pattern multiLineCommentDelimiterEnd = Pattern.compile("\\*/");


                public HighlightDocumentAda95() {
                        putProperty( DefaultEditorKit.EndOfLineStringProperty, "\n" );

                        rootElement = getDefaultRootElement();

                        keywords = new HashMap<String, Color>();
                        keywords.put( "abstract", Color.blue);                                                                      
                        /*Visibilidad: */
                        keywords.put( "package", new Color(0,200,0));
                        keywords.put( "private", new Color(0,200,0));
                        keywords.put( "protected", new Color(0,200,0));
                        

                        keywords.put( "abort", Color.blue);
                        keywords.put( "abs", Color.blue);
                        keywords.put( "accept", Color.blue);
                        keywords.put( "access", Color.blue);
                        keywords.put( "aliased", Color.blue);
                        keywords.put( "all", Color.blue);
                        keywords.put( "and", Color.blue);
                        keywords.put( "array", Color.blue);
                        keywords.put( "at", Color.blue);

                        keywords.put( "begin", Color.blue);
                        keywords.put( "body", Color.blue);
                        keywords.put( "case", Color.blue);
                        keywords.put( "constant", Color.blue);
                        keywords.put( "declare", Color.blue);
                        keywords.put( "delay", Color.blue);
                        keywords.put( "delta", Color.blue);
                        keywords.put( "digits", Color.blue);
                        keywords.put( "do", Color.blue);

                        keywords.put( "else", Color.blue);
                        keywords.put( "elsif", Color.blue);
                        keywords.put( "end", Color.blue);
                        keywords.put( "endtry", Color.blue);
                        keywords.put( "exception", Color.blue);
                        keywords.put( "exit", Color.blue);

                        keywords.put( "for", Color.blue);
                        keywords.put( "function", Color.blue);

                        keywords.put( "generic", Color.blue);
                        keywords.put( "goto", Color.blue);

                        keywords.put( "if", Color.blue);
                        keywords.put( "in", Color.blue);
                        keywords.put( "is", Color.blue);

                        keywords.put( "limited", Color.blue);
                        keywords.put( "loop", Color.blue);

                        keywords.put( "mod", Color.blue);

                        keywords.put( "new", Color.blue);
                        keywords.put( "not", Color.blue);
                        keywords.put( "null", Color.red);

                        keywords.put( "of", Color.blue);
                        keywords.put( "or", Color.blue);
                        keywords.put( "others", Color.blue);
                        keywords.put( "out", Color.blue);

                        keywords.put( "pragma", Color.blue);
                        keywords.put( "procedure", Color.blue);

                        keywords.put( "raise", Color.blue);
                        keywords.put( "range", Color.blue);
                        keywords.put( "record", Color.blue);
                        keywords.put( "rem", Color.blue);
                        keywords.put( "renames", Color.blue);
                        keywords.put( "requeue", Color.blue);
                        keywords.put( "return", Color.blue);
                        keywords.put( "reverse", Color.blue);

                        keywords.put( "select", Color.blue);
                        keywords.put( "separate", Color.blue);
                        keywords.put( "subtype", Color.blue);

                        keywords.put( "tagged", Color.blue);
                        keywords.put( "task", Color.blue);
                        keywords.put( "terminate", Color.blue);
                        keywords.put( "then", Color.blue);
                        keywords.put( "type", Color.blue);

                        keywords.put( "until", Color.blue);
                        keywords.put( "use", Color.blue);

                        keywords.put( "when", Color.blue);
                        keywords.put( "while", Color.blue);
                        keywords.put( "with", Color.blue);

                        keywords.put( "xor", Color.blue);

                        //Tipos:
                        keywords.put( "Float", Color.blue);
                        keywords.put( "Integer", Color.blue);
                        keywords.put( "Boolean", Color.blue);

                        /*Tipos:
                         keywords.put( "char", Color.orange);
                        keywords.put( "byte", Color.orange);
                        keywords.put( "float", Color.orange);
                        keywords.put( "double", Color.orange);
                        keywords.put( "long", Color.orange);
                        keywords.put( "short", Color.orange);
                        keywords.put( "int", Color.orange);
                        keywords.put( "String", Color.orange);*/
                        /*Valores especiales:
                        keywords.put( "true", Color.red);
                        keywords.put( "false", Color.red);
                        keywords.put( "const", Color.red);
                         */
                        

                        

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
                        /*There are no multiline comments in ADA95: */
                       /* Matcher mlcStart = multiLineCommentDelimiterStart.matcher(text);
                        Matcher mlcEnd = multiLineCommentDelimiterEnd.matcher(text);*/
/*
                        while(mlcStart.find()) {
                                if(mlcEnd.find( mlcStart.end() ))
                                        highlightString(commentColor, mlcStart.start(), (mlcEnd.end()-mlcStart.start()), true, true);
                                else
                                        highlightString(commentColor, mlcStart.start(), getLength(), true, true);
                        }
     */
                        //process single comments:
                        Matcher slc = singleLineCommentDelimter.matcher(text);

                        while(slc.find()) {
                                int line = rootElement.getElementIndex(slc.start());
                                int endOffset = rootElement.getElement(line).getEndOffset() - 1;

                                highlightString(commentColor, slc.start(), (endOffset-slc.start()), true, true);
                        }
                        //process string literals
                        Matcher stringlit=stringLiteral.matcher(text);
                        
                        while(stringlit.find()) {
                                //int line = rootElement.getElementIndex(slc.start());
                                //int endOffset = rootElement.getElement(line).getEndOffset() - 1;

                                highlightString(stringColor, stringlit.start(), (stringlit.end()-stringlit.start()), true, true);
                        }

                        //process function names:
                        Matcher func=functionName.matcher(text);
                        while(func.find()){
                            StyleConstants.setBold(style, true);
                            StyleConstants.setForeground(style, Color.black);
                            setCharacterAttributes(func.start(),(func.end()-func.start())-1,style,true);
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
