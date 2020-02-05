import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class Cesar extends JFrame implements ActionListener
{
	private final int alto=230;
	private final int ancho=360;
	private String [] opciones={"     Indice de Cifrado     ","1","2","3","4","5","6","7","8","9","10","11","12","13"};
	private String [] opciones2={"    Accion    ","Cifrar","Descifrar"};
	private JComboBox combo=new JComboBox(opciones);
	private JComboBox combo2=new JComboBox(opciones2);
	private JTextField textoUsuario=new JTextField(30);
	private JTextField textoCifrado=new JTextField(30);
	private JButton boton=new JButton("Cifrar");
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	public Cesar()
	{
		super("Decoder");
		this.setSize(ancho,alto);
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(0);}});
		this.setResizable(false);
		boton.addActionListener(this);
		textoCifrado.setEditable(false);
		textoCifrado.setBackground(Color.white);
		initVentana();
	}
	public void actionPerformed(ActionEvent ev)
	{
		int indice1=combo.getSelectedIndex();
		String cual=opciones[indice1];
		int indice=Integer.parseInt(cual);
		char [] cadena=textoUsuario.getText().toCharArray();
		int indice2=combo2.getSelectedIndex();
		String cifODescif=opciones2[indice2];
		if(cifODescif.equals("Cifrar"))
		{
			cifrar(cadena,indice);
		}
		else if (cifODescif.equals("Descifrar"))
		{
			descifrar(cadena,indice);
		}
	}
	public void cifrar(char[] cadena,int indice)
	{
		for(int i=0;i<cadena.length;i++)
		{
			if(cadena[i]>='a' && cadena[i]<='z' || cadena[i]>='A' && cadena[i]<='Z')
			{
				cadena[i]+=indice;
				if(cadena[i]>'z' && cadena[i]-4<'z' || cadena[i]>'Z' && cadena[i]-4<'Z')
				{
					cadena[i]-=26;
				}
			}
		}
		String resultado=new String(cadena);
		textoCifrado.setText(resultado);
	}
	public void descifrar(char[] cadena,int indice)
	{
		for(int i=0;i<cadena.length;i++)
		{
			if(cadena[i]>='a' && cadena[i]<='z' || cadena[i]>='A' && cadena[i]<='Z')
			{
				cadena[i]-=indice;
				if(cadena[i]<'a' && cadena[i]+4>'a' || cadena[i]<'A' && cadena[i]+4>'A')
				{
					cadena[i]+=26;
				}
			}
		}
		String resultado=new String(cadena);
		textoCifrado.setText(resultado);
	}
	public void initVentana()
	{
		panel1.setBorder(BorderFactory.createTitledBorder("Opciones de Cifrado"));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(combo);
		panel1.add(combo2);
		
		panel2.setBorder(BorderFactory.createTitledBorder("Texto"));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.add(textoUsuario);
		panel2.add(textoCifrado);
		
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(panel1);
		this.getContentPane().add(panel2);
		this.getContentPane().add(boton);
	}
}


public class Decoder
{
	public static void main(String [] args)
	{
		Cesar ventana=new Cesar();
		ventana.show();
	}
}