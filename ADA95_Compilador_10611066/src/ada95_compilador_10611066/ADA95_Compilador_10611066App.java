/*
 * ADA95_Compilador_10611066App.java
 */

package ada95_compilador_10611066;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ADA95_Compilador_10611066App extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ADA95_Compilador_10611066View(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ADA95_Compilador_10611066App
     */
    public static ADA95_Compilador_10611066App getApplication() {
        return Application.getInstance(ADA95_Compilador_10611066App.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ADA95_Compilador_10611066App.class, args);
    }
}
