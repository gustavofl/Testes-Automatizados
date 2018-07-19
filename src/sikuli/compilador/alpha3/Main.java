package sikuli.compilador.alpha3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		File arquivoSikuli = new File("C:/Users/Gustavo/Desktop/sikuli/mysql_server.sikuli/mysql_server.py");

		String codigoFonte = "";
		
		/// LEITURA DO CODIGO FONTE
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(arquivoSikuli));

			while (br.ready()) {
				String linha = br.readLine();
				codigoFonte += linha + '\n';
			}

			br.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.err.println("Falha ao abrir o arquivo sikuli.");
		} catch (IOException e) {
			// e.printStackTrace();
			System.err.println("Falha ao ler o arquivo sikuli.");
		}
		
		/// PREPROCESSAMENTO
		
		codigoFonte = Preprocessador.scan(codigoFonte);
		
		System.out.println(codigoFonte);
		
		if(true) return;
		
		/// ANALISADOR LEXICO
		
		ArrayList<String> tokens = AnalisadorLexico.scan(codigoFonte);
		
		// DEBUG
		for (String token : tokens) {
			System.out.print(token + ' ');
		}
	}
}
