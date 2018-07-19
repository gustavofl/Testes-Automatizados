package sikuli.compilador.alpha1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import sikuli.compilador.alpha3.AnalisadorLexico;

public class Compilador {
	
	private static ArrayList<Character> delimitadores = new ArrayList<Character>(Arrays.asList(' ','(',')','"','\'',','));
	private static ArrayList<Character> finaisJavaSemPontoEVirgula = new ArrayList<Character>(Arrays.asList('{','}')) ;
	private static ArrayList<String> comandosSikuli = new ArrayList<String>(Arrays.asList("click","doubleClick","wait","waitAndClick","type","hover","clickOpcoes","find","sleep","dragDrop",
																						  "exists","popup"));
	private File arquivoSikuli;
	private String nomeProjeto;
	private int qntBlocosAninhados;
	private ArrayList<Integer> indentacoes;
	private String metodoMain;
	private boolean codigoDeFuncao;
	
	public Compilador(File arquivoSikuli, String nomeProjeto) {
		this.arquivoSikuli = arquivoSikuli;
		this.nomeProjeto = verificarNomeProjeto(nomeProjeto);
		this.qntBlocosAninhados = 0;
		this.indentacoes = new ArrayList<>();
		indentacoes.add(0);
		this.metodoMain = "";
		this.codigoDeFuncao = false;
	}
	
	private String verificarNomeProjeto(String nome) {
		String[] partes = nome.split(" ");
		String nomeVerificado = "";
		
		if(!Character.isLetter(partes[0].charAt(0)))
			nomeVerificado += "Projeto";
		
		for (String palavra : partes) {
			nomeVerificado += Character.toUpperCase(palavra.charAt(0)) + palavra.substring(1);
		}
		
		return nomeVerificado;
	}

	public String compile() {
		String codigo = "";
		
		iniciarMetodoMain();

		try {
			BufferedReader br = new BufferedReader(new FileReader(arquivoSikuli));
			while(br.ready()){
			   String linha = br.readLine();
			   
			   // codigo += analisarLinha(linha);
			   ArrayList<String> tokens = AnalisadorLexico.scan(linha);
			   for (String token : tokens) {
				   System.out.println(token);
			   }
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.err.println("Falha ao abrir o arquivo sikuli.");
		} catch (IOException e) {
			// e.printStackTrace();
			System.err.println("Falha ao ler o arquivo sikuli.");
		}
		
		finalizarMetodoMain();
		
		if(!metodoMain.equals(""))
			codigo = metodoMain + "\n" + codigo;

		String classeJava = criarArquivoDeClasse(codigo);
		
		return classeJava;
	}

	private String criarArquivoDeClasse(String metodosImplementados) {
		String arquivo = "import org.sikuli.script.*;\n" +
						 "\n" +
						 "public class " + nomeProjeto + "{\n" +
						 "\tprivate ScreenAutomation screen;\n" + 
						 "\n" + 
						 "\tpublic "+nomeProjeto+"() {\n" + 
						 "\t\tscreen = ScreenAutomation.getInstance();\n" + 
						 "\t}\n" +
						 "\n";
		
		String[] linhas = metodosImplementados.split("\n");
		for (String linha : linhas)
			arquivo += "\t" + linha + "\n";
		
		arquivo += "}";
		
		return arquivo;
	}

	private void finalizarMetodoMain() {
		metodoMain += "}";
	}

	private void iniciarMetodoMain() {
		metodoMain += "public static void main(String[] args) {\n";
	}

	private String analisarLinha(String linha) {
		String palavra = "";
		String linhaCompilada = "";
		int indentacao = 0;
		boolean string = false;
		char aspasUsadas = ' ';
		String fechamentoDeBlocoAnterior = "";
		
		if(linha.length() == 0) {
			return "";
		}
		
		for (int i = 0; i < linha.length(); i++) {

			if(string == false) {
				// INICIO de uma string
				if(linha.charAt(i) == '\'' || linha.charAt(i) == '"') {
					string = true;
					aspasUsadas = linha.charAt(i);
				}
			} else {
				linhaCompilada += String.valueOf(linha.charAt(i));
				
				// FIM da string
				if(linha.charAt(i) == aspasUsadas) {
					string = false;
					continue;
				}
				
				// Se eh uma string, nao ha codigo a ser compilado
				if(string == true)
					continue;
			}
			
			if(!delimitadores.contains(linha.charAt(i)) && i != linha.length()-1){
				palavra += String.valueOf(linha.charAt(i));
			} else {
				if(linhaCompilada == "" && palavra == "") {
					if(linha.charAt(i) == ' ') {
						indentacao++;
						continue;
					}
				}
				
				if(linhaCompilada == "" && palavra != "") {	
					int indiceUltimaIndentacao = indentacoes.size() - 1;
					
					if(indentacoes.get(indiceUltimaIndentacao) == -1)
						indentacoes.set(indiceUltimaIndentacao, indentacao);
					else {
						while(indentacao < indentacoes.get(indiceUltimaIndentacao)){
							linhaCompilada += voltarIndentacao();
							indiceUltimaIndentacao = indentacoes.size() - 1;
							
							if(indentacao == 0)
								codigoDeFuncao = false;
						}
						
						fechamentoDeBlocoAnterior = linhaCompilada;
						linhaCompilada = "";
					}
					
					for (int j = 0; j < qntBlocosAninhados; j++)
						linhaCompilada += "\t";
					
				}
				
				linhaCompilada += traduzirPalavra(palavra);

				if(linha.charAt(i) == ':') {
					linhaCompilada = addIndentacao(linhaCompilada);
				}else
					linhaCompilada += String.valueOf(linha.charAt(i));
				
				palavra = "";
			}
		}
		
		linhaCompilada = addPontoEVirgula(linhaCompilada);
				
		linhaCompilada += "\n";
		
		if(codigoDeFuncao)
			linhaCompilada = fechamentoDeBlocoAnterior + linhaCompilada;
		else {
			metodoMain += "\t" + linhaCompilada;
			linhaCompilada = fechamentoDeBlocoAnterior;
		}
		
		return linhaCompilada;
	}
	
	private String voltarIndentacao() {
		String linha = "";
		
		int ultimoIndice = indentacoes.size() - 1;
		qntBlocosAninhados--;

		for (int j = 0; j < qntBlocosAninhados; j++)
			linha += "\t";
		linha += "}";
		
		indentacoes.remove(ultimoIndice);
		
		linha += "\n";
		return linha;
	}

	private String traduzirPalavra(String palavra) {
		if(comandosSikuli.contains(palavra))
			return "screen." + palavra;
		
		switch(palavra) {
			case "": case ":":
				return "";
			case "def":
				codigoDeFuncao = true;
				return "\npublic void";
			case "print":
				return "System.out.println";
			case "not":
				return "!";
			case "and":
				return "&&";
			case "or":
				return "||";
			case "Pattern":
				return "new Pattern";
			default:
				return palavra;
		}
	}

	private String addPontoEVirgula(String linha) {
		if(linha.length() != 0) {
			if(!finaisJavaSemPontoEVirgula.contains(linha.charAt(linha.length() - 1)))
				linha += ";";
		}
		
		return linha;
	}
	
	private String addIndentacao(String linha) {
		qntBlocosAninhados++;
		linha += " {";

		indentacoes.add(-1);
		
		return linha;
	}

}
