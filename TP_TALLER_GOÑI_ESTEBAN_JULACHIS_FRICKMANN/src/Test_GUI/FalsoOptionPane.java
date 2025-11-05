package Test_GUI;

import java.awt.Component;

import vista.IOptionPane;

public class FalsoOptionPane implements IOptionPane{
	private String mensaje = null;

	@Override
	public void ShowMessage(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

}
