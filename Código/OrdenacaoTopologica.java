import java.util.*;

public class OrdenacaoTopologica
{
	private class Elo
	{
		public int chave;
		public int contador;
		public Elo prox;
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
		public Elo id;
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

	private Elo prim;
	private int n;

	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
/*
	public void montarGrafo(int numeroVertices){

		Random random = new Random();

		List<Integer> vertices = new ArrayList<Integer>();

		for(int i = 0; i < numeroVertices; i++){
			vertices.add(i);
		}

		for(int i = 0; i <numeroVertices*2; i++){
			int predecessor = vertices.get(random.nextInt(numeroVertices));
			int sucessor = vertices.get(random.nextInt(numeroVertices));
			lerLinhaSemScanner(predecessor, sucessor);
		}

		montarListaFinal();

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

*/

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
		long[] tempos = new long[4];
		for (int i = 0; i < 4; i++) {
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
				if (i != eloAtual.chave && valorAleatorio < probabilidade) {
					adicionarAresta(eloAtual.chave, i);
				}
			}
		}

		listaSemPredecessores();
		imprimirElementos();
	}
	//O(1)
	public void adicionarVertice(int chave) {
		Elo novoElo = new Elo(chave, 0, prim, null);
		prim = novoElo;
		n++;
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

	public EloSucessor encontrarEloComPredecessor() {
		for (Elo eloAtual = prim; eloAtual != null; eloAtual = eloAtual.prox) {
			if(eloAtual.listaSuc != null){
				return eloAtual.listaSuc;
			}
		}
		return null;
	}

	//O(n)
	public void listaSemPredecessores(){
		Elo p = prim;
		Elo antigoPrim;
		prim = null;
		Elo elementoCorrente;

		while(p!= null){
			elementoCorrente = p;
			p = elementoCorrente.prox;
			if(elementoCorrente.contador == 0){
				if(elementoCorrente.listaSuc != null){
					Elo novoElo = new Elo(elementoCorrente.chave, 0, elementoCorrente, elementoCorrente.listaSuc);
					antigoPrim = prim;
					prim = novoElo;
					prim.prox = antigoPrim;
				}else{
					Elo novoElo = new Elo(elementoCorrente.chave, 0, elementoCorrente, encontrarEloComPredecessor());
					antigoPrim = prim;
					prim = novoElo;
					prim.prox = antigoPrim;
				}
			}
		}
	}

	//O(n*m)
	public void imprimirElementos(){

		Elo aux;
		for(aux = prim; aux != null; aux=aux.prox){
			n--;
			for (EloSucessor sucessor = aux.listaSuc; sucessor != null; sucessor = sucessor.prox) {
				sucessor.id.contador--;

				if(sucessor.id.contador== 0){
					Elo ult = percorrerLista();
					ult.prox = sucessor.id;
					sucessor.id.prox = null;

					aux.listaSuc = sucessor.prox;
				}
			}
		prim = aux.prox;
		}
	}

	public Elo percorrerLista() {
		Elo eloAtual;

		for (eloAtual = prim; eloAtual.prox != null; eloAtual = eloAtual.prox);

		return eloAtual;
	}


/*
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
*/

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