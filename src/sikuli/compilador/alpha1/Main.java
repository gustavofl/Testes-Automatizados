package sikuli.compilador.alpha1;

import java.io.File;

import javax.swing.JFileChooser;

public class Main {
	
	private static final int MODO_LEITURA = 1;
	private static final int MODO_ESCRITA = 2;

	public static void main(String[] args) {
		// File arquivoSikuli = chooseFile(MODO_LEITURA);
		// File arquivoJar = chooseFile(MODO_ESCRITA);
		File arquivoSikuli = new File("C:/Users/Gustavo/Desktop/sikuli/mysql_server.sikuli/mysql_server.py");
		File arquivoJar = new File("C:/Users/Gustavo/Desktop/sikuli/mysql_server.jar");
		
		if(arquivoJar == null) {
			System.err.println("arquivo de saida invalido.");
			return;
		} else if(arquivoSikuli == null) {
			System.err.println("arquivo de entrada invalido.");
			return;
		}
		
		Compilador comp = new Compilador(arquivoSikuli, "deep freeze");
		System.out.println(comp.compile());

		// String pathProjetoSikuli = arquivo.getPath() + ".sikuli";
		// String nomeProjeto = arquivo.getName();
	}
	
	private static File chooseFile(int operacao) {
		return chooseFile("", operacao);
	}

	private static File chooseFile(String currentDirectory, int operacao) {
		JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		file.setCurrentDirectory(new File(currentDirectory));
		
		int confirmed = 1;
		if(operacao == MODO_LEITURA)
			confirmed = file.showOpenDialog(null);
		else
			confirmed = file.showSaveDialog(null);
		
		if(confirmed == 1) {
			System.out.println("Cancelado.");
			return null;
		}
		
		return file.getSelectedFile();
	}
	
}
