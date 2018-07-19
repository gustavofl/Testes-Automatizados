package sikuli.iniciarTeste;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class AcessoWeb {
	
	public static File baixar(String destino, String origem, int tamBufferKB) {
		try {
			URL url = new URL(origem);
			InputStream is = url.openStream();
			FileOutputStream fos = new FileOutputStream(destino);
			
			int count = 0;
			
			int tamBuffer = tamBufferKB * 1024;
			byte[] buffer = new byte[tamBuffer];
			
			System.out.print("iniciando transferencia: " + destino);
			long inicio = System.currentTimeMillis();
			
			while (is.read(buffer) != -1){
				fos.write(buffer);
				count++;
			}
			
			System.out.print("    ...    transfer�ncia concluida ("+count*tamBuffer+" Bytes)");
			long fim = System.currentTimeMillis();
			
			System.out.println(" - Dura��o: " + (fim - inicio)/1000 + " seg.");
			
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void baixar(String enderecoLocalConfiguracao, String enderecoRemotoconfiguracao) {
		baixar(enderecoLocalConfiguracao, enderecoRemotoconfiguracao, 1);
	}

}
