package Test_GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class TestUtil {
	private static int delay = 70;

    public static void setDelay(int delay) {
        TestUtil.delay = delay;
    }

    public static int getDelay() {
        return delay;
    }

    public static Component getComponentForName(Component parent, String name) {
        Component retorno = null;
        if (parent.getName() != null && parent.getName().equals(name))
            retorno = parent;
        else
        {
            if (parent instanceof Container)
            {
                Component[] hijos = ((Container) parent).getComponents();
                int i = 0;
                while (i < hijos.length && retorno == null)
                {
                    retorno = getComponentForName(hijos[i], name);
                    i++;
                }
            }
        }
        return retorno;
    }
    
    
    public static Point getCentro(Component componente) {
        Point retorno = null;
        if (componente != null) {
            retorno = componente.getLocationOnScreen();
	        retorno.x += componente.getWidth() / 2;
	        retorno.y += componente.getHeight() / 2;
        }
        return retorno;
    }

    
    public static void clickComponent(Component component, Robot robot) {
        Point punto = TestUtil.getCentro(component);
        robot.mouseMove(punto.x, punto.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getDelay());
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(getDelay());
    }

    
    /*public static void tipeaTexto(String texto, Robot robot) {
        String mayusculas = texto.toUpperCase();
        char letras[] = mayusculas.toCharArray();
        for (int i = 0; i < letras.length; i++)
        {
            robot.delay(getDelay());
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(letras[i]);
            robot.delay(getDelay());
            robot.keyRelease(letras[i]);
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyRelease(KeyEvent.VK_SHIFT);

        }
    }*/
    
    public static int getKeyCode(char c) {
        switch (c) {
            // letras minusculas y mayusculas
            case 'a': case 'A': return KeyEvent.VK_A;
            case 'b': case 'B': return KeyEvent.VK_B;
            case 'c': case 'C': return KeyEvent.VK_C;
            case 'd': case 'D': return KeyEvent.VK_D;
            case 'e': case 'E': return KeyEvent.VK_E;
            case 'f': case 'F': return KeyEvent.VK_F;
            case 'g': case 'G': return KeyEvent.VK_G;
            case 'h': case 'H': return KeyEvent.VK_H;
            case 'i': case 'I': return KeyEvent.VK_I;
            case 'j': case 'J': return KeyEvent.VK_J;
            case 'k': case 'K': return KeyEvent.VK_K;
            case 'l': case 'L': return KeyEvent.VK_L;
            case 'm': case 'M': return KeyEvent.VK_M;
            case 'n': case 'N': return KeyEvent.VK_N;
            case 'o': case 'O': return KeyEvent.VK_O;
            case 'p': case 'P': return KeyEvent.VK_P;
            case 'q': case 'Q': return KeyEvent.VK_Q;
            case 'r': case 'R': return KeyEvent.VK_R;
            case 's': case 'S': return KeyEvent.VK_S;
            case 't': case 'T': return KeyEvent.VK_T;
            case 'u': case 'U': return KeyEvent.VK_U;
            case 'v': case 'V': return KeyEvent.VK_V;
            case 'w': case 'W': return KeyEvent.VK_W;
            case 'x': case 'X': return KeyEvent.VK_X;
            case 'y': case 'Y': return KeyEvent.VK_Y;
            case 'z': case 'Z': return KeyEvent.VK_Z;

            // numeros
            case '0': return KeyEvent.VK_0;
            case '1': return KeyEvent.VK_1;
            case '2': return KeyEvent.VK_2;
            case '3': return KeyEvent.VK_3;
            case '4': return KeyEvent.VK_4;
            case '5': return KeyEvent.VK_5;
            case '6': return KeyEvent.VK_6;
            case '7': return KeyEvent.VK_7;
            case '8': return KeyEvent.VK_8;
            case '9': return KeyEvent.VK_9;

            // algunos especiales
            case ' ': return KeyEvent.VK_SPACE;
            case '.': return KeyEvent.VK_PERIOD;
            case ',': return KeyEvent.VK_COMMA; 
            
            default: return -1;

        }
    }

    
    /*public static void tipeaTexto(String texto, Robot robot) {
        char letras[] = texto.toCharArray();
        for (int i = 0; i < letras.length; i++)
        {
            robot.delay(getDelay());
            char c = letras[i];
            int keyCode = getKeyCode(c);

            if (c >= 'A' && c <= 'Z')
                robot.keyPress(KeyEvent.VK_SHIFT);
            else if (c == '@') {
                robot.keyPress(KeyEvent.VK_SHIFT);    
                robot.keyPress(KeyEvent.VK_2);     
            }
            else if (c == '_') {
            	robot.keyPress(KeyEvent.VK_SHIFT);
            	robot.keyPress(KeyEvent.VK_MINUS);
            }
            
            robot.keyPress(letras[i]);
            robot.delay(getDelay());
            robot.keyRelease(letras[i]);
            
            if (c >= 'A' && c <= 'Z')
                robot.keyRelease(KeyEvent.VK_SHIFT);
            else if (c == '@') {        
                robot.keyRelease(KeyEvent.VK_2);       
                robot.keyRelease(KeyEvent.VK_SHIFT);  
                }
            else if (c == '_') {
            	robot.keyRelease(KeyEvent.VK_SHIFT);
            	robot.keyRelease(KeyEvent.VK_MINUS);
            }
        }
    }*/
    
    public static void tipeaTexto(String texto, Robot robot) {
        String mayusculas = texto.toUpperCase();
        char letras[] = mayusculas.toCharArray();
        for (int i = 0; i < letras.length; i++)
        {
            robot.delay(getDelay());
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(letras[i]);
            robot.delay(getDelay());
            robot.keyRelease(letras[i]);
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyRelease(KeyEvent.VK_SHIFT);

        }
    }
    
    public static void borraJTextField(JTextField jtextfield, Robot robot) {
        Point punto = null;
        if (jtextfield != null)
        {
            robot.delay(getDelay());
            punto = jtextfield.getLocationOnScreen();
            robot.mouseMove(punto.x, punto.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(getDelay());
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.delay(getDelay());
            while (!jtextfield.getText().isEmpty())
            {
                robot.delay(getDelay());
                robot.keyPress(KeyEvent.VK_DELETE);
                robot.delay(getDelay());
                robot.keyRelease(KeyEvent.VK_DELETE);
            }
        }
    }


}
