package br.unirio;

import java.io.File;
import java.util.*;

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
		public EloSucessor listaSuc;

		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}

		public Elo(int chave, int contador, Elo prox, EloSucessor listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
	}

	private class EloSucessor
	{
		/* Aponta para o elo que � sucessor. */
		public Elo id;

		/* Aponta para o pr�ximo elemento. */
		public EloSucessor prox;

		public EloSucessor()
		{
			id = null;
			prox = null;
		}

		public EloSucessor(Elo id, EloSucessor prox)
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

	public void incializar() {
		// Vetor de inteiros fornecido como argumento
		int[] vetorParametro = {10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 30000, 50000, 100000};

		for (int valor : vetorParametro) {
			double tempoMedio = medirTempoMedio(valor);
			System.out.printf("Para o valor %d: Tempo medio = %.6f segundos %n", valor, tempoMedio);
		}
	}

	public double medirTempoMedio(int valor) {
		// Roda a função 10 vezes e calcula o tempo médio
		long[] tempos = new long[1];
		for (int i = 0; i < 1; i++) {
			long inicio = System.currentTimeMillis();
			geraGrafo(valor, 0.13);
			long fim = System.currentTimeMillis();
			tempos[i] = fim - inicio;
		}

		double tempoMedio = calcularMedia(tempos);
		return tempoMedio / 1000.0; // Converte para segundos
	}

	private static double calcularMedia(long[] tempos) {
		long soma = 0;
		for (long tempo : tempos) {
			soma += tempo;
		}
		return (double) soma / tempos.length;
	}

	//O(n^2)
	public void geraGrafo(int n, double probabilidade) {
		Random random = new Random();
		// Criar vértices
		for (int i = 0; i < n; i++) {
			adicionarVertice(i);
		}

		// Adicionar arestas com probabilidade p
		for (Elo eloAtual = prim; eloAtual != null; eloAtual = eloAtual.prox) {
			for (int i = 0; i < n; i++) {
				Double valorAleatorio = random.nextDouble();
				if (i != eloAtual.chave && (valorAleatorio < probabilidade) && i!= 0) {
					adicionarAresta(eloAtual.chave, i);
				}
			}
		}

	}
	//O(N)
	public Elo adicionarVertice(int chave) {
		Elo p, novoElo, ant;

		if(prim == null){
			novoElo = new Elo(chave, 0, null, null);
			prim = novoElo;
		}else{
			novoElo = new Elo(chave, 0, null, null);
			ant = percorrerLista();

			ant.prox = novoElo;
			n++;
		}
		return novoElo;
	}

	//O(N + M)
	public void adicionarAresta(int origem, int destino) {
		Elo eloOrigem = encontrarElo(origem);
		Elo eloDestino = encontrarElo(destino);

		if (eloOrigem != null && eloDestino != null && !verificarCiclo(eloDestino, eloOrigem)) {
			EloSucessor novoSucessor = new EloSucessor(eloDestino, eloOrigem.listaSuc);
			eloOrigem.listaSuc = novoSucessor;
			eloDestino.contador++;
		}
	}

	//O(S) onde S é o tamanho da lista de sucessores
	private boolean verificarCiclo(Elo origem, Elo destino) {
		// Verifica se há um ciclo ao percorrer os sucessores de origem
		for (EloSucessor sucessor = origem.listaSuc; sucessor != null; sucessor = sucessor.prox) {
			if (sucessor.id == destino) {
				return true; // Ciclo encontrado
			}
		}
		return false; // Não há ciclo
	}
	//O(n)
	public Elo encontrarElo(int chave) {
		for (Elo eloAtual = prim; eloAtual != null; eloAtual = eloAtual.prox) {
			if (eloAtual.chave == chave) {
				return eloAtual;
			}
		}
		return null;
	}

	//O(n)
	public void listaSemPredecessores(){
		Elo p = new Elo(prim.chave, 0, prim.prox, prim.listaSuc);
		Elo antigoPrim;
		prim = null;
		Elo elementoCorrente;

		while(p!= null){
			elementoCorrente = p;
			p = elementoCorrente.prox;
			if(elementoCorrente.contador == 0){
				Elo novoElo = new Elo(elementoCorrente.chave, 0, elementoCorrente, elementoCorrente.listaSuc);
				antigoPrim = prim;
				prim = novoElo;
				prim.prox = antigoPrim;
			}
		}
	}

	//O(n^2)
	public void imprimirElementos(){

		System.out.println("Elementos sem predecessores");
		Elo aux;
		for(aux = prim; aux != null; aux=aux.prox){
			n--;
			System.out.print(aux.chave + ", ");
			for (EloSucessor sucessor = aux.listaSuc; sucessor != null; sucessor = sucessor.prox) {
				sucessor.id.contador--;
				if(sucessor.id.contador== 0){
					if(aux.listaSuc == sucessor){
						Elo ult = percorrerLista();
						ult.prox = sucessor.id;
						aux.listaSuc = sucessor.prox;
						sucessor.id.prox = null;

					}else{
						EloSucessor antListSuc;
						antListSuc = percorrerListaSucessores(aux.listaSuc, sucessor.id.chave);
						antListSuc.prox = sucessor.prox;
						Elo ult = percorrerLista();
						ult.prox = sucessor.id;
						sucessor.id.prox = null;

					}
				}
			}
			prim = aux.prox;
		}
	}
	//O(n)
	public Elo percorrerLista() {
		Elo eloAtual;

		for (eloAtual = prim; eloAtual.prox != null; eloAtual = eloAtual.prox);

		return eloAtual;
	}

	//O(m) onde m é o tamanho da lista de sucessores
	public EloSucessor percorrerListaSucessores(EloSucessor inicio, int elem){
		EloSucessor ant = null;
		EloSucessor p;

		for(p = inicio; p.id.chave != elem; p=p.prox){
			ant = p;
		}

		return ant;
	}

	public void realizaLeitura(String nomeEntrada)
	{
		try {
			this.lerArquivo(nomeEntrada);
		} catch (Exception err) {
			System.out.println("Erro ao ler o arquivo.");
			err.printStackTrace();
		}
	}

	private void lerArquivo(String nomeArquivo) throws Exception {
		File file = new File(nomeArquivo);
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			this.lerLinha(scanner);
		}

		scanner.close();
	}

	private void lerLinha(Scanner scanner) {
		String data = scanner.nextLine();
		String[] elementos = data.split("\\s*<\\s*");

		int predecessorChave = Integer.parseInt(elementos[0]);
		int sucessorChave = Integer.parseInt(elementos[1]);

		Elo predecessor = this.encontrarElo(predecessorChave);
		if (predecessor == null) predecessor = this.adicionarVertice(predecessorChave);

		Elo sucessor = this.encontrarElo(sucessorChave);
		if (sucessor == null) sucessor = this.adicionarVertice(sucessorChave);

		this.adicionarAresta(predecessor.chave, sucessor.chave);
	}

	//O(n*m)
	private void debug()
	{
		for (Elo p = this.prim; p != null; p = p.prox) {
			System.out.print(p.chave + " predecessores: " + p.contador + ", sucessores: ");
			for (EloSucessor s = p.listaSuc; s != null; s = s.prox) {
				System.out.print(s.id.chave + " -> ");
			}
			System.out.println("NULL");
		}
	}

	public void executaEntrada(){
		this.debug();
		this.listaSemPredecessores();
		this.imprimirElementos();
	}

	public boolean executa(int tam, double prob)
	{
		geraGrafo(tam, prob);
		this.debug();
		listaSemPredecessores();
		imprimirElementos();
		return false;
	}

}