/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ada95_compilador_10611066;

import java.io.File;

/**
 *
 * @author lfborjas
 */
public class Utils {


    public final static String ada = "adb";


    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}


