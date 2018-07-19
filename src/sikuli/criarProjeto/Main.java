package sikuli.criarProjeto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

public class Main {
	
	private static String localProjetoSikuli = "C:/Users/Gustavo/Desktop/sikuli";
	private static final String localIDESikuli = "C:/Users/Gustavo/Desktop/sikuli/IDE";

	public static void main(String[] args) {
		JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		file.setCurrentDirectory(new File(localProjetoSikuli));
		int confirmed = file.showSaveDialog(null);
		if(confirmed == 1) {
			System.out.println("Cancelado.");
			return;
		}
		File arquivo = file.getSelectedFile();
		String pathProjetoSikuli = arquivo.getPath() + ".sikuli";
		String nomeProjeto = arquivo.getName();
		
		try {
			File dir = new File(pathProjetoSikuli);
			dir.mkdirs();
			
			File arq = new File(pathProjetoSikuli + File.separator + nomeProjeto + ".py");
			arq.createNewFile();
			
			FileWriter fw = new FileWriter(arq);
		    PrintWriter gravarArq = new PrintWriter(fw);
		 
		    gravarArq.printf("def waitAndClick(button):\n" + 
		    				 "\twait(button,600)\n" + 
		    				 "\tclick(button)\n\n");
		 
		    gravarArq.printf("def clickOpcoes(button1, button2):\n" + 
				    		 "\tif(exists(button1)):\n" + 
				    		 "\t\tclick(button1)\n" + 
				    		 "\telif(exists(button2)):\n" + 
				    		 "\t\tclick(button2)\n\n");
		    
		    gravarArq.printf("### Escreva o seu codigo aqui");
		    
		    gravarArq.close();
		    
		    Runtime.getRuntime().exec("cmd.exe /c " + localIDESikuli + "/runsikulix.cmd");
		    
		    System.out.println("FIM.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
