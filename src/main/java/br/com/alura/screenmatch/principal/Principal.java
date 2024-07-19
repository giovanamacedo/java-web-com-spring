package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        System.out.println("Digite o nome da série desejada: ");
        var nomeSerie = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> listaDadosTemporada = new ArrayList<>();
        for (int i = 1; i <= dados.totalTemporadas(); i++){
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            listaDadosTemporada.add(dadosTemporada);
        }
        listaDadosTemporada.forEach(System.out::println);

        /*for(int i = 0; i < dados.totalTemporadas(); i++){
            List<DadosEpisodio> episodiosTemporada = listaDadosTemporada.get(i).episodiosLista();
            for(int j = 0; j < episodiosTemporada.size(); j++){
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/

        //essa linha de código representa uma versão refatorada do for acima
        listaDadosTemporada.forEach(t -> t.episodiosLista().forEach(e -> System.out.println(e.titulo())));

        List<String> nomes = Arrays.asList("Giovana", "Mateus", "Léo", "Kiara");

        //Exemplo de como pode-se utilizar uma stream
        /*nomes
                .stream()
                .sorted()
                .limit(3)
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);*/

    }

}
