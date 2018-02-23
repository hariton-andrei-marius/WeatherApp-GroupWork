package start.classes;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.web.client.RestClientException;

public class ForecastWrapper {
	private Lista[] list;
	private String dataGiorno, formato;
	private LinkedList<LinkedList> listaGiorni;
	private LinkedList<Double> mediaMin, mediaMax;
	private LinkedList<Icone> icone;
	
	public void setLista (Lista [] list) {
		this.list = list;
	}
	
	public Lista[] getList() {
		return list;
	}
	
	public LinkedList<Double> getMediaMin() {
		return mediaMin;
	}
	
	public LinkedList<Double> getMediaMax() {
		return mediaMax;
	}
	
	public String getDataGiorno() {
		return dataGiorno;
	}
	

	public LinkedList<LinkedList> getListaGiorni() {
		return listaGiorni;
	}
	
	public void getGiorno() throws ParseException {
		listaGiorni = new LinkedList<LinkedList>();
		String temp;
		System.out.println("prova");
		for(int i = 0; i < list.length; i++) {
			dataGiorno = list[i].getDt_txt();
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataGiorno);
			temp = new SimpleDateFormat("dd").format(date);
			LinkedList<Lista> listaPerGiorno = new LinkedList<Lista>();
			formato = temp;
			while((i< list.length) && (formato.equals(temp)) ) {
				listaPerGiorno.add(list[i]);
				dataGiorno = list[i].getDt_txt();
				Date datenew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataGiorno);
				formato = new SimpleDateFormat("dd").format(datenew);
				i++;
				
			}
			listaGiorni.add(listaPerGiorno);
			if(i< list.length) {
				i--;
			}		
		}
	}
	
	public void trovaTemp() {
		Iterator<LinkedList> iterator = listaGiorni.iterator();
		mediaMin = new LinkedList<Double>();
		mediaMax = new LinkedList<Double>();
		while(iterator.hasNext()) {
			LinkedList corrente = iterator.next();
			Iterator<Lista> it = corrente.iterator();
			LinkedList<Double> listaTempMax = new LinkedList<Double>();
			LinkedList<Double> listaTempMin = new LinkedList<Double>();
			int cont = 0;
			while(it.hasNext()) {
				Lista corr = it.next();
				listaTempMin.add(corr.getMain().getTemp_min());
				listaTempMax.add(corr.getMain().getTemp_max());
//				String prova = corr.getWeatherForecast()[0].getIcon();
//				System.out.println("prova stampa icon : " + prova );
				cont++;
			}
			mediaMin.add(mediaTemp(listaTempMin, cont));
			mediaMax.add(mediaTemp(listaTempMax, cont));
			
		}
	}
	
	public double mediaTemp(LinkedList<Double> temperature, int cont) {
		Iterator<Double> iterTemp = temperature.iterator();
		double somma = 0;
		while(iterTemp.hasNext()) {
			Double corrente = iterTemp.next();
			somma += corrente; 
		}
		System.out.println("temperatura " + somma/cont);
		return (somma/cont);
		
	}
	
	
//	public void cercaIcona() {
//		Iterator<LinkedList> iterator = listaGiorni.iterator();
//		icone = new LinkedList<Icone>();
//		while(iterator.hasNext()) {
//			LinkedList corrente = iterator.next();
//			Iterator<Lista> it = corrente.iterator();
//			String [] listaIcone = new String[list.length];
//			int cont = 0;
//			System.out.println("lunghezza lista icone :" + listaIcone.length);
//			while(it.hasNext()) {
//				System.out.println("sono nel while");
//				Lista corr = it.next();
//				ForecastWeather weather = corr.getWeatherForecast();
//				System.out.println("weather ");
//				listaIcone[cont] = weather.getIcon();//<----
//				System.out.println("lista icone: " + listaIcone[cont] + "cont : " + cont );
//				cont++;
//			}
//			System.out.println("sono uscito dal while");
//			icone.add(iconaRipetuta(listaIcone));
//		
//		}
//	}
//	
//	public Icone iconaRipetuta(String [] listaIcone) {
//		int[] conto = new int[listaIcone.length];
//		for(int i = 0; i<listaIcone.length; i++ ) {
//			String temp = listaIcone[i];
//			conto[i] = 0;
//			for(int j = 0; j<listaIcone.length; j++) {
//				if(temp.equals(listaIcone[j])) {
//					conto[i]++; 
//					System.out.println("icone : " + listaIcone[i] + "  "+  "conteggio icone : " + conto[i] + "sto leggendo l'icona: " + listaIcone[j] );
//				}
//			}
//		}
//		Icone objIcon = new Icone();
//		objIcon.setArray(listaIcone, conto);
//		return objIcon;
//	}
	
}