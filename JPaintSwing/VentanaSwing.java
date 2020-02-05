import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import java.io.*;
class AlzadaSwing extends JPanel implements MouseMotionListener,MouseListener
{
	//opcion==1 Mano Alzada
	//opcion==2 Texto
	//opcion==3 Cuadrado
	//opcion==4 Circulo
	private int opcion;
	private int x;
	private int y;
	private int xR;
	private int yR;
	private int xR2;
	private int yR2;
	private int xA;
	private int yA;
	private int ancho;
	private int alto;
	private Image img;
	private Image abrir;
	private Graphics w;
	private String text;
	private String abrirArch;
	private Font miFont;
	private boolean nuevo;
	public AlzadaSwing()
	{
		super();
		opcion=1;
		nuevo=false;
		this.setSize(200,200);
		this.setOpaque(true);
		this.setBackground(Color.white);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(img==null)
		{
			img=this.createImage(this.getWidth(),this.getHeight());
			w=img.getGraphics();
		}
		if(nuevo)
		{
			abrir=this.getToolkit().getImage(abrirArch);
			w.drawImage(abrir,0,0,this);
			g.drawImage(img,0,0,this);
			nuevo=false;
		}
		g.drawImage(img,1,1,this);
	}
	public void update(Graphics g)
	{
		System.out.println(abrir);
	}
	public void setOpcion(int i)
	{
		opcion=i;
	}
	public void actualizaPanel()
	{
		Graphics g=this.getGraphics();
		g.drawImage(img,1,1,this);
		g.dispose();
	}
	public void setColores(Color c)
	{
		w.setColor(c);
	}
	public void cambiaColor(Color col)
	{
		img=null;
		this.setBackground(col);
		repaint();
	}
	public void setTexto(String texto,String tamano)
	{
		int tam;
		try
		{
			tam=Integer.parseInt(tamano);
		}
		catch(Exception ex)
		{
			tam=10;
		}
		miFont=new Font("Arial",0,tam);
		text=texto;
	}
	public void nuevo()
	{
		img=null;
		repaint();
	}
	public void openImage(String imagen)
	{
		abrirArch=imagen;
		img=null;
		nuevo=true;
	}
	public void writeJPG(String archivo)
	{
		try
		{
			BufferedImage image=(BufferedImage)img;
			OutputStream bos =new FileOutputStream(archivo);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( bos );
			JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam( image );
			jep.setQuality( 1.0f, false );
			encoder.setJPEGEncodeParam( jep );
			encoder.encode( image );
			bos.close();
		}
		catch ( Exception e )
		{
			
		}
    }
	public void mouseDragged(MouseEvent e)
	{
		if(opcion==1)
		{
			xA=e.getX();
			yA=e.getY();
			w.drawLine(x,y,xA,yA);
			this.actualizaPanel();
			x=xA;
			y=yA;
		}
		if(opcion==3)
		{
			int ancho1=e.getX();
			int alto1=e.getY();
			if(ancho1>xR2)
			{
				xR=xR2;
				ancho=ancho1-xR;
			}
			else if(ancho1<xR2)
			{
				xR=ancho1;
				ancho=xR2-xR;
			}
			if(alto1>yR2)
			{
				yR=yR2;
				alto=alto1-yR;
			}
			else if(alto1<yR2)
			{
				yR=alto1;
				alto=yR2-yR;
			}
		}
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e)
	{
		if(opcion==3)
		{
			w.drawRect(xR,yR,ancho,alto);
			this.actualizaPanel();
		}
	}
	public void mousePressed(MouseEvent e)
	{
		x=e.getX();
		y=e.getY();
		xR=e.getX();
		yR=e.getY();
		xR2=e.getX();
		yR2=e.getY();
	}
	public void mouseClicked(MouseEvent e)
	{
		if(opcion==4)
		{
			w.fillOval(x,y,30,30);
			this.actualizaPanel();
		}
		else if(opcion==2)
		{
			w.setFont(miFont);
			w.drawString(text,x,y);
			this.actualizaPanel();
		}
	}
	public void mouseMoved(MouseEvent e){}
}
public class VentanaSwing extends JFrame implements ItemListener,ActionListener
{
	private AlzadaSwing panel;
	private Dialogo dialogo;
	private JLabel etiqueta=new JLabel("JPaint...");
	private Container contentpane;
	private Label label=new Label("Barra de Estado...");
	private MenuBar menubar=new MenuBar();
	private Menu menu=new Menu("Archivo");
	private Menu menu2=new Menu("Edición");
	private MenuItem item1=new MenuItem("Guardar");
	private Menu item2=new Menu("Tipo de dibujo...");
	private MenuItem item3=new MenuItem("Abrir");
	private MenuItem item4=new MenuItem("Opciones...");
	private MenuItem item5=new MenuItem("Nuevo");
	private CheckboxMenuItem item21=new CheckboxMenuItem("Circulo");
	private CheckboxMenuItem item22=new CheckboxMenuItem("Texto");
	private CheckboxMenuItem item23=new CheckboxMenuItem("Cuadrado");
	private CheckboxMenuItem item24=new CheckboxMenuItem("Mano Alzada");
	private JFileChooser fileChus1=new JFileChooser();
	public VentanaSwing()
	{
		super("JPaint");
		panel=new AlzadaSwing();
		dialogo=new Dialogo(this,"Opciones",panel);
		this.setBackground(Color.white);
		this.contentpane=this.getContentPane();
		this.setSize(450,400);
		this.initMenu();
		this.initVentana();
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(0);}});
		item4.addActionListener(this);
		item1.addActionListener(this);
		item3.addActionListener(this);
		item5.addActionListener(this);
		item21.addItemListener(this);
		item22.addItemListener(this);
		item23.addItemListener(this);
		item24.addItemListener(this);
	}
	public void initMenu()
	{
		setMenuBar(menubar);
		menu.add(item5);
		menu.add(item3);
		menu.add(item1);
		item2.add(item21);
		item2.add(item22);
		item2.add(item23);
		item2.add(item24);
		item24.setState(true);
		menu2.add(item2);
		menu2.add(item4);
		menubar.add(menu);
		menubar.add(menu2);
	}
	public void initVentana()
	{
		contentpane.setLayout(new BorderLayout());
		contentpane.add(panel);
		contentpane.add("South",etiqueta);
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object culpable=ev.getSource();
		if(culpable.equals(item4))
		{
			dialogo.show();
		}
		else if(culpable.equals(item5))
		{
			panel.nuevo();
		}
		else if(culpable.equals(item1))
		{
			fileChus1.showSaveDialog(this);
			File arch=fileChus1.getSelectedFile();
			String archivo=arch.getAbsolutePath();
			panel.writeJPG(archivo);
		}
		else if(culpable.equals(item3))
		{
			fileChus1.showOpenDialog(this);
			File arch=fileChus1.getSelectedFile();
			String archivo=arch.getAbsolutePath();
			panel.openImage(archivo);
		}
	}
	public void itemStateChanged(ItemEvent e)
	{
		Object source=e.getSource();
		if(source.equals(item21))
		{
			setCirculo();
		}
		else if(source.equals(item22))
		{
			setWriteTexto();
		}
		else if(source.equals(item23))
		{
			setCuadrado();
		}
		else if(source.equals(item24))
		{
			setManoAlzada();
		}
	}
	public void setCirculo()
	{
		item22.setState(false);
		item23.setState(false);
		item24.setState(false);
		item21.setState(true);
		panel.setOpcion(4);
		etiqueta.setText("Circulo...");
	}
	public void setWriteTexto()
	{
		item21.setState(false);
		item23.setState(false);
		item24.setState(false);
		item22.setState(true);
		panel.setOpcion(2);
		etiqueta.setText("Texto...");
	}
	public void setCuadrado()
	{
		item22.setState(false);
		item21.setState(false);
		item24.setState(false);
		item23.setState(true);
		panel.setOpcion(3);
		etiqueta.setText("Cuadrado...");
	}
	public void setManoAlzada()
	{
		item22.setState(false);
		item23.setState(false);
		item21.setState(false);
		item24.setState(true);
		panel.setOpcion(1);
		etiqueta.setText("Mano alzada...");
	}
	public static void main(String[]args)
	{
		VentanaSwing ventana=new VentanaSwing();
		ventana.show();
	}
}
class Dialogo extends JDialog implements ChangeListener,DocumentListener,ActionListener
{
	private JPanel panel=new JPanel(new FlowLayout());
	private JPanel panel2=new JPanel(new FlowLayout());
	private JPanel panel3=new JPanel(new FlowLayout());
	private JLabel label1=new JLabel("Texto : ");
	private JLabel label2=new JLabel("Tamaño : ");
	private JTextField campo=new JTextField(20);
	private JTextField campo2=new JTextField("10",3);
	private JColorChooser colorchus=new JColorChooser();
	private JColorChooser colorchus2=new JColorChooser();
	private JTabbedPane tabbedPane=new JTabbedPane();
	private JRadioButton mano=new JRadioButton("Alzada",true);
	private JRadioButton texto=new JRadioButton("Texto");
	private JRadioButton cuadrado=new JRadioButton("Cuadrado");
	private JRadioButton circulo=new JRadioButton("Circulo");
	private ButtonGroup grupo=new ButtonGroup();
	private AlzadaSwing alzada;
	private VentanaSwing ventana;
	private ColorSelectionModel model;
	private ColorSelectionModel model2;
	public Dialogo(VentanaSwing vent,String titulo,AlzadaSwing alt)
	{
		super(vent,titulo);
		ventana=vent;
		alzada=alt;
		this.setSize(450,450);
		this.getContentPane().setLayout(new BorderLayout());
		this.initDialog();
		mano.addActionListener(this);
		texto.addActionListener(this);
		cuadrado.addActionListener(this);
		circulo.addActionListener(this);
		model=colorchus.getSelectionModel();
		model.addChangeListener(this);
		model2=colorchus2.getSelectionModel();
		model2.addChangeListener(this);
		campo.getDocument().addDocumentListener(this);
		campo2.getDocument().addDocumentListener(this);
	}
	public void initDialog()
	{
		grupo.add(mano);
		grupo.add(texto);
		grupo.add(cuadrado);
		grupo.add(circulo);
		panel3.add(mano);
		panel3.add(texto);
		panel3.add(cuadrado);
		panel3.add(circulo);
		panel.add(label1);
		panel.add(campo);
		panel.add(label2);
		panel.add(campo2);
		panel.add(colorchus);
		panel2.add(colorchus2);
		tabbedPane.add("Dibujo",panel);
		tabbedPane.add("Canvas",panel2);
		this.getContentPane().add("North",panel3);
		this.getContentPane().add(tabbedPane);
	}
	public void stateChanged(ChangeEvent ev)
	{
		Object culpable=ev.getSource();
		if(culpable.equals(model))
		{
			Color miColor=colorchus.getColor();
			alzada.setColores(miColor);
		}
		else if(culpable.equals(model2))
		{
			Color miColor2=colorchus2.getColor();
			alzada.cambiaColor(miColor2);
			ventana.setBackground(miColor2);
		}
	}
	public void changedUpdate(DocumentEvent ev)
	{
		
	}
	public void insertUpdate(DocumentEvent ev)
	{
		alzada.setTexto(campo.getText(),campo2.getText());
	}
	public void removeUpdate(DocumentEvent ev)
	{
		alzada.setTexto(campo.getText(),campo2.getText());
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object culpable=ev.getSource();
		if(culpable.equals(mano))
		{
			ventana.setManoAlzada();
		}
		else if(culpable.equals(texto))
		{
			ventana.setWriteTexto();
		}
		else if(culpable.equals(cuadrado))
		{
			ventana.setCuadrado();
		}
		else if(culpable.equals(circulo))
		{
			ventana.setCirculo();
		}
	}
}