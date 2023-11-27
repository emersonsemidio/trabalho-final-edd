package br.unirio;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.random.RandomGenerator;

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

	public void montarGrafo(int numeroVertices){

		Random random = new Random();

		List<Integer> vertices = new ArrayList<Integer>();

		for(int i = 0; i < numeroVertices; i++){
			vertices.add(i);
		}

		for(int i = 0; i <numeroVertices; i++){
			int predecessor = vertices.get(random.nextInt(numeroVertices));
			int sucessor = vertices.get(random.nextInt(numeroVertices));
			lerLinhaSemScanner(predecessor, sucessor);
		}

	}

	/* M�todo respons�vel pela leitura do arquivo de entrada. */
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

	private void lerLinhaSemScanner(int chavePredecessor, int chaveSucessor) {

		Elo predecessor = this.buscar(chavePredecessor);
		if (predecessor == null) predecessor = this.adicionarVertice(chavePredecessor);

		Elo sucessor = this.buscar(chaveSucessor);
		if (sucessor == null) sucessor = this.adicionarVertice(chaveSucessor);

		this.adicionarSucessor(predecessor, sucessor);
	}

	private void lerLinha(Scanner scanner) {
		String data = scanner.nextLine();
		String[] elementos = data.split("\\s*<\\s*");

		int predecessorChave = Integer.parseInt(elementos[0]);
		int sucessorChave = Integer.parseInt(elementos[1]);

		Elo predecessor = this.buscar(predecessorChave);
		if (predecessor == null) predecessor = this.adicionarVertice(predecessorChave);

		Elo sucessor = this.buscar(sucessorChave);
		if (sucessor == null) sucessor = this.adicionarVertice(sucessorChave);

		this.adicionarSucessor(predecessor, sucessor);
	}

	private Elo adicionarVertice(int chave) {
		this.n++;

		if (this.prim == null) {
			this.prim = new Elo();
			this.prim.chave = chave;
			return this.prim;
		}

		Elo corrente = this.prim;
		while (corrente.prox != null) { corrente = corrente.prox; }

		Elo elo = new Elo();
		elo.chave = chave;
		corrente.prox = elo;

		return elo;
	}

	private void adicionarSucessor(Elo predecessor, Elo sucessor) {
		sucessor.contador++;

		if (predecessor.listaSuc == null) {
			predecessor.listaSuc = new EloSucessor();
			predecessor.listaSuc.id = sucessor;
			return;
		};

		EloSucessor sucessorCorrente = predecessor.listaSuc;

		EloSucessor novoSucessor = new EloSucessor();
		novoSucessor.id = sucessor;
		novoSucessor.prox = sucessorCorrente;
		predecessor.listaSuc = novoSucessor;
	}


	// O(n)
	private Elo buscar(int chave) {
		return this.prim != null ? this.buscarRecursivo(chave, this.prim) : null;
	}

	// O(n)
	private Elo buscarRecursivo(int chave, Elo eloCorrente) {
		if (eloCorrente == null) return null;
		if (chave == eloCorrente.chave) return eloCorrente;

		return this.buscarRecursivo(chave, eloCorrente.prox);
	}

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

	public boolean executa()
	{
		this.debug();
		return false;
	}
}