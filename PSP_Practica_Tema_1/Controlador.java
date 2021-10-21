package es.studium.PSP_Practica_Tema_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements ActionListener, WindowListener,  MouseListener, KeyListener
{
	
	Vista vista = null;
	Modelo modelo = null;
	
	Proceso p = null;
	String splittedLine[] = {""};
	
	String c[] = {""};
	
	List<String> comandos = new ArrayList<String>();
	List<Proceso> procesos = new ArrayList<Proceso>();
	
	
	
	public Controlador (Vista vista, Modelo modelo)
	{
		this.vista = vista;
		this.modelo = modelo;
		
		vista.ventana.addWindowListener(this);
		vista.btnBloc.addActionListener(this);
		vista.btnPaint.addActionListener(this);
		vista.btnReloj.addActionListener(this);
		vista.btnComando.addActionListener(this);
		vista.btnTerminar.addActionListener(this);
		
		cargarProcesos();
		
	}
	
	
	public void cargarProcesos()
	{
		try 
		{
			vista.choProcesos.removeAll();
		    String line;
		    Process pr = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe /fo csv /nh");
		    BufferedReader input =  new BufferedReader(new InputStreamReader(pr.getInputStream()));
		    while ((line = input.readLine()) != null) 
		    {
		      splittedLine = line.split(",");		   
		      p = new Proceso(splittedLine[0], splittedLine[1]);
		      procesos.add(p);
		    }
		    input.close();
		    
		    
		    for(int i=0;i<procesos.size();i++)
		    {
		    	vista.choProcesos.add(procesos.get(i).getNombreProceso());
		    }
		 
		} 
		
		catch (Exception err) 
		{
		    err.printStackTrace();
		}
	}
	
	public void ejecutarProceso()
	{
		try 
		{
			
			
            Runtime rt = Runtime.getRuntime();
            Process p = Runtime.getRuntime().exec(vista.txfComando.getText());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=null;

            while((line=input.readLine()) != null)
            {
            	vista.txaComando.append(line+"\n");
            	comandos.add(line);
            }
            input.close();
            
            //VER POR CONSOLA
           // for(int i=0;i<comandos.size();i++)
          //  {
            	
            //	System.out.println(comandos.get(i));
          //  }
            
            int exitVal = p.waitFor();
            System.out.println("Exited with error code "+exitVal);
            vista.txfComando.setText("");
            vista.txaComando.setText("");
        } 
		
		catch(Exception e) 
		{
            System.out.println(e.toString());
            e.printStackTrace();
        }	
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent evento)
	{
		
	}

	@Override
	public void windowClosing(WindowEvent evento)
	{
		if(evento.getSource().equals(vista.ventana))
		{
			System.exit(0);
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(vista.btnBloc))
		{
			
			try
			{
			Process process = Runtime.getRuntime().exec("notepad.exe");
			cargarProcesos();
				while(process.isAlive())
				{
				vista.btnBloc.setEnabled(false);
				}		
				vista.btnBloc.setEnabled(true);
				
			}
			catch(IOException e)
			{
			System.out.println("Error");
			}
		}
		if(evento.getSource().equals(vista.btnPaint))
		{
			try
			{
			Process process = Runtime.getRuntime().exec("mspaint.exe");
			cargarProcesos();
			while(process.isAlive())
			{
			vista.btnPaint.setEnabled(false);
			}		
			vista.btnPaint.setEnabled(true);			
			}
			catch(IOException e)
			{
			System.out.println("Error");
			}
		}
		if(evento.getSource().equals(vista.btnReloj))
		{
			try
			{
				ProcessBuilder pb = new ProcessBuilder("java", "-jar", "C:\\Users\\Varo\\eclipse-workspace\\PSP_Practica_Tema_1\\Reloj.jar");
				Process p = pb.start();
				cargarProcesos();
				while(p.isAlive())
				{
				vista.btnReloj.setEnabled(false);
				}		
				vista.btnReloj.setEnabled(true);
			}
			catch(IOException e)
			{
			System.out.println("Error");
			}
		}
		if(evento.getSource().equals(vista.btnTerminar))
		{
			int index = vista.choProcesos.getSelectedIndex();
			Proceso selectedProcess = procesos.get(index);
			//System.out.println(selectedProcess.getidProceso());
			try
			{
				Process process = Runtime.getRuntime().exec("taskkill /F /PID " + selectedProcess.getidProceso());
				cargarProcesos();
			}
			catch(IOException e)
			{
			System.out.println("Error al matar");
			}
			
		}
		if(evento.getSource().equals(vista.btnComando))
		{
				ejecutarProceso();		
		}
	}


}
