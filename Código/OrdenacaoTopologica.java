package br.unirio;

import com.sun.source.tree.EnhancedForLoopTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class OrdenacaoTopologica
{
	private class Elo
	{
		/* Identifica��o do elemento. */
		public int chave;
		
		/* N�mero de predecessores. */
		public int contador;
		
		/* Aponta para o pr�ximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
	}
	
	private class EloSuc
	{
		/* Aponta para o elo que � sucessor. */
		public Elo id;
		
		/* Aponta para o pr�ximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
	}


	/* Ponteiro (refer�ncia) para primeiro elemento da lista. */
	private Elo prim;
	
	/* N�mero de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}

	public void montarLista(){

	}

	/* M�todo respons�vel pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		int predecessorCorrente;
		int sucessorCorrente;


		try {
			FileReader fileReader = new FileReader(nomeEntrada);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(nomeEntrada))) {
			String line;
			while ((line = br.readLine()) != null) {
				int cont = 0;
				String stringNumber = "";
				while(true){
					if(!Character.isDigit(line.charAt(cont)))
						break;
					stringNumber += line.charAt(cont);
					cont ++;
				}
				predecessorCorrente = Integer.parseInt(stringNumber);

				stringNumber = "";
				while(cont < line.length()){
					if(Character.isDigit(line.charAt(cont))) {
						stringNumber += line.charAt(cont);
						cont++;
					} else if(stringNumber.length() != 0){
						break;
					}
					if(stringNumber.length() == 0)
						cont++;
				}
				sucessorCorrente = Integer.parseInt(stringNumber);
				alocaNumeros(predecessorCorrente, sucessorCorrente);
			}
		} catch (IOException e) {
			System.out.println("Arquivo não encontrado");
		}

	}
	
	/* M�todo para impress�o do estado atual da estrutura de dados. */
	private void debug()
	{

	}

	private void alocaNumeros(int predecessorCorrente, int sucessorCorrente){
		if(prim == null){
			Elo sucessor = new Elo(sucessorCorrente, 1, null, null);
			Elo predecessor = new Elo(predecessorCorrente, 0, sucessor, new EloSuc(sucessor, null));
			predecessor.prox = sucessor;
			prim = predecessor;
		}else{
			Elo p = prim;
			int contador = 0;
			Elo sucessor = new Elo();
			while(p != null){
				while(p.listaSuc != null){
					if(p.chave == predecessorCorrente){
						Elo predecessor = new Elo(sucessorCorrente, contador, null, null);
						p.listaSuc.prox = new EloSuc(predecessor, null);
					}
					p.listaSuc = p.listaSuc.prox;
				}

				p = p.prox;
			}
		}

	}

	/* M�todo respons�vel por executar o algoritmo. */
	public boolean executa()
	{
		/* Preencher. */
		
		return false;
	}
}