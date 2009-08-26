/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ada95_compilador_10611066;


import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 *
 * @author lfborjas
 */
class AdaFilter extends FileFilter {

    public AdaFilter() {
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
	return true;
    }

    String extension = Utils.getExtension(f);
    if (extension != null) {
	if (extension.equals(Utils.ada) ) {
	        return true;
	} else {
	    return false;
	}
    }

    return false;

    }

    public String getDescription() {
        return "Archivos de Ada";
    }


}
