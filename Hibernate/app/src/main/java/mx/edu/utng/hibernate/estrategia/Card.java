package mx.edu.utng.hibernate.estrategia;

import android.widget.Button;


public class Card{

	public int x;
	public int y;
	public Button button;
	
	public Card(Button button, int x,int y) {
		this.x = x;
		this.y=y;
		this.button=button;
	}
	

}
