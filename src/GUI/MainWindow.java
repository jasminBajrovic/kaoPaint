package GUI;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import geometrija.Duz;
import geometrija.Figura;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;

public class MainWindow {

	private JFrame frmKaoPaint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		MainWindow window = new MainWindow();
		window.frmKaoPaint.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKaoPaint = new JFrame();
		frmKaoPaint.setTitle("kao Paint :)");
		frmKaoPaint.setBounds(100, 100, 450, 300);
		frmKaoPaint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmKaoPaint.getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblStatus = new JLabel("New label");
		frmKaoPaint.getContentPane().add(lblStatus, BorderLayout.SOUTH);

		JPanel pnlZaAlate = new JPanel();
		frmKaoPaint.getContentPane().add(pnlZaAlate, BorderLayout.WEST);


		JToggleButton tglbtnCrtajDuz = new JToggleButton("Crtanje duzi");
		tglbtnCrtajDuz.setSelected(true);
		tglbtnCrtajDuz.setActionCommand("duz");
		pnlZaAlate.add(tglbtnCrtajDuz);

		JToggleButton tglbtnCrtajKrug = new JToggleButton("Crtanje kruga");
		tglbtnCrtajKrug.setActionCommand("krug");
		pnlZaAlate.add(tglbtnCrtajKrug);

		JToggleButton tglbtnCrtanjeKvadrata = new JToggleButton("Crtanje kvadrata");
		tglbtnCrtanjeKvadrata.setActionCommand("kvadrat");
		pnlZaAlate.add(tglbtnCrtanjeKvadrata);

		PanelZaCrtanje pnlZaCrtanje = new PanelZaCrtanje();
		

		pnlZaCrtanje.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmKaoPaint.getContentPane().add(pnlZaCrtanje, BorderLayout.CENTER);
		pnlZaAlate.setLayout(new BoxLayout(pnlZaAlate, BoxLayout.Y_AXIS));



		ButtonGroup grupaZaCrtanje = new ButtonGroup();
		grupaZaCrtanje.add(tglbtnCrtajDuz);
		grupaZaCrtanje.add(tglbtnCrtajKrug);
		grupaZaCrtanje.add(tglbtnCrtanjeKvadrata);

		pnlZaCrtanje.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent arg0) 
			{
				switch (grupaZaCrtanje.getSelection().getActionCommand())
				{
					case "duz":
						lblStatus.setText(pnlZaCrtanje.crtajDuz(arg0.getX(), arg0.getY()));
						break;
					case "krug":
						lblStatus.setText(pnlZaCrtanje.crtajKrug(arg0.getX(), arg0.getY()));
						break;
					case "kvadrat":
						lblStatus.setText(pnlZaCrtanje.crtajKvad(arg0.getX(), arg0.getY()));
						break;
				}
			}
		});


		JMenuBar menuBar = new JMenuBar();
		frmKaoPaint.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					JFileChooser odabir = new JFileChooser();
					odabir.showOpenDialog(frmKaoPaint);

					FileInputStream inFajl = new FileInputStream(odabir.getSelectedFile());
					ObjectInputStream inObj = new ObjectInputStream(inFajl);

					while (true)
					{
						Figura fig = (Figura)inObj.readObject();
						pnlZaCrtanje.figure.add(fig);
					}

				} catch (IOException | ClassNotFoundException e) 
				{
					pnlZaCrtanje.repaint();
				}
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					JFileChooser snimanje = new JFileChooser();
					snimanje.showSaveDialog(frmKaoPaint);
					FileOutputStream fajl = new FileOutputStream(snimanje.getSelectedFile());
					ObjectOutputStream oOut = new ObjectOutputStream(fajl);
					for (Figura fig: pnlZaCrtanje.figure)
					{
						oOut.writeObject(fig);
					}
					oOut.close();
					fajl.close();
				} catch (IOException e) {}
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmOcistiCrtez = new JMenuItem("Ocisti crtez");
		mntmOcistiCrtez.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				pnlZaCrtanje.figure.clear();
				pnlZaCrtanje.repaint();
			}
		});
		menuBar.add(mntmOcistiCrtez);
		
		pnlZaCrtanje.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) 
			{
				switch (grupaZaCrtanje.getSelection().getActionCommand())
				{
					case "duz":
						pnlZaCrtanje.crtamoLiniju = true;
						pnlZaCrtanje.iscrtavanje(arg0.getX(), arg0.getY());
						break;
					case "krug":
						pnlZaCrtanje.crtamoLiniju = false;
						pnlZaCrtanje.iscrtavanje(arg0.getX(), arg0.getY());
						break;
					case "kvadrat":
						break;
				}
			}
		});
	}
}
