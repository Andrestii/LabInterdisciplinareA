/* DE PAOLI LORENZO 753577
 * ONESTI ANDREA 754771
 * RIZZO MATTIA 755403
 * WU WEILI 752602 
 * SEDE: VARESE */

package climatemonitoring;

import java.io.BufferedReader;
import java.io.FileReader;

public class Comune {
    public AreaInteresse CercaAreaGeograficaLuogo(String citta, String stato) {
        // Ricerco nella lista quale area ha nome e stato uguali a quelli inseriti
        if(!ClimateMonitor.areeInteresse.isEmpty()) {  // Controllo se la lista non è vuota
            for (AreaInteresse a : ClimateMonitor.areeInteresse) {
                if(a != null && a.nome != null && a.stato != null)
                {
                    if((a.nome.toUpperCase().equals(citta.toUpperCase())) && (a.stato.toUpperCase().equals(stato.toUpperCase()))) {   // Abbiamo trovato l'area che volevamo
                        return a;
                    }
                }
                
            }
        }
        return null;
    }

    public AreaInteresse CercaAreaGeograficaCoordinate(String lat, String lon) {
        // Ricerco nella lista quale area ha latitudine e longitudine uguali a quelle inserite
        if(!ClimateMonitor.areeInteresse.isEmpty()) {  // Controllo se la lista non è vuota
            for (AreaInteresse a : ClimateMonitor.areeInteresse) { 
                if(a != null && a.latitudine != null && a.longitudine != null)
                {

                    if((a.latitudine.equals(lat)) && (a.longitudine.equals(lon))) {   // Abbiamo trovato l'area che volevamo
                        return a;
                    }
                } 
            }
            AreaInteresse areaPiuVicina = ClimateMonitor.areeInteresse.get(0);  // Prendo la prima della lista

            for (AreaInteresse a : ClimateMonitor.areeInteresse) { // Allora cerchiamo per le coordinate più vicine
                double latDouble = Double.parseDouble(lat), lonDouble = Double.parseDouble(lon);    // Che inserisce l'utente
                double aLat = Double.parseDouble(a.latitudine), aLon = Double.parseDouble(a.longitudine);   // Dalla lista
                double latVicina = Double.parseDouble(areaPiuVicina.latitudine), lonVicina = Double.parseDouble(areaPiuVicina.longitudine);
                if(Math.abs(latDouble - aLat) + Math.abs(lonDouble - aLon) < Math.abs(latDouble - latVicina) + Math.abs(lonDouble - lonVicina)) {
                    areaPiuVicina = a;  // Se è più vicina l'areaInteresse "a" allora aggiorno "areaPiuVicina"
                }
            }
            return areaPiuVicina;
        }
        return null;
    }

    public void VisualizzaAreaGeografica(AreaInteresse a) {
        if(a != null) {
            String[] parametri = CreaListaParametri(a);
            System.out.println(a.toString());
            if(parametri[14]!=null) {
                System.out.println("\nParametri climatici rilevati il " + parametri[14] + " alle " + parametri[15] + "\n\nVento: " + parametri[0]);
                if(!parametri[1].equals(""));
                    System.out.println("> Note: " + parametri[1]);

                System.out.println("\nUmidità: " + parametri[2]);
                if(!parametri[3].equals(""))
                    System.out.println("> Note: " + parametri[3]);

                System.out.println("\nPressione: " + parametri[4]);
                if(!parametri[5].equals(""))
                    System.out.println("> Note: " + parametri[5]);

                System.out.println("\nTemperatura: " + parametri[6]);
                if(!parametri[7].equals(""))
                    System.out.println("> Note: " + parametri[7]);

                System.out.println("\nPrecipitazioni: " + parametri[8]);
                if(!parametri[9].equals(""))
                    System.out.println("> Note: " + parametri[9]);

                System.out.println("\nAltitudine dei ghiacciai: " + parametri[10]);
                if(!parametri[11].equals(""))
                    System.out.println("> Note: " + parametri[11]);

                System.out.println("\nMassa dei ghiacciai: " + parametri[12]);
                if(!parametri[13].equals(""))
                    System.out.println("> Note: " + parametri[13]);
            }
        }
        else {
            System.out.println("Quest'area di interesse non esiste!");
        }
    }

    public static String[] CreaListaParametri(AreaInteresse a) {   // Dal file .csv prende tutti i parametri e li mette in una array di stringhe

        String riga = "";
        String[] parametri = new String[16];    // parametri (senza il nome dell'area di interesse)

        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/ParametriClimatici.csv"));
            String[] p = new String[17];
            while ((riga = br.readLine()) != null) {
                p = riga.split(";");
                if(p[0].equals(a.nome)) {
                    for(int i=0;i<16;i++) {
                        parametri[i]=p[i+1];
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return parametri;
    }
}