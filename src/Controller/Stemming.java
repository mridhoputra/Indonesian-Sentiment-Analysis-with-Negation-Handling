/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author User-pc
 */
public class Stemming {
    public Stemming(){
        DocumentReader dr = new DocumentReader();
        dr.initKamusKDid();
    }
    
    //proses stem
    public String kataDasar(String kata){
        if(DocumentReader.getIndexAlphabet().contains(getFirstChart(kata))){
            //System.out.println(kata);
            if (!stringMatcher(kata, DocumentReader.getKamusKDid().get(getFirstChart(kata)))){
//                System.out.println(kata+" masuk ke proses stemming");
                return this.Hps_derivation_prefixes(this.Hps_derivation_suffixes(this.Hps_inflection_Suffixes(kata)));
            }
        }
//        System.out.println(kata+" tidak masuk ke proses stemming");
        return kata;
    }

    //untuk menghilangkan inflection suffixes( perubahan akhiran)
    private String Hps_inflection_Suffixes(String kata){
//        System.out.println(kata + " mampir ke Hps_inflection_Suffixes");
        String inflection = kata;
        String Hasil= "";
        String Hasil2= "";
        if (kata.endsWith("kah")||kata.endsWith("lah")||kata.endsWith("pun")||kata.endsWith("ku")||kata.endsWith("mu")||kata.endsWith("nya")){
            Hasil = kata.replaceAll("(kah|lah|pun|tah|ku|mu|nya)$", "");
            if(kata.endsWith("kah")||kata.endsWith("lah")||kata.endsWith("pun")||kata.endsWith("tah")){
                if(kata.endsWith("ku")||kata.endsWith("mu")||kata.endsWith("nya")){
                    Hasil2= kata.replaceAll("(ku|mu|nya)$", "");
//                    System.out.println("Hasil2: "+Hasil2);
                    return Hasil2;
                }
            }
//            System.out.println("Hasil: "+Hasil);
            return Hasil;
        }
//        System.out.println("inflection: "+inflection);
        return inflection;
    }
    
    //untuk menghilangkan derivation suffixes (akhiran yang pasti)
    private String Hps_derivation_suffixes(String kata){
//        System.out.println(kata + " mampir ke Hps_derivation_suffixes");
        String der_suffixes= kata;
        String Hasil="";
        String Hasil2="";
        if (kata.endsWith("i")||kata.endsWith("an")||kata.endsWith("kan")){
            Hasil= kata.replaceAll("(i|an|kan)$","");
            if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))){
//                System.out.println("Hasil: "+Hasil);
                return Hasil;
            }
            if (kata.endsWith("k")){
                Hasil2 = kata.replaceAll("(k)$", "");
                if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))){
//                    System.out.println("Hasil2: "+Hasil2);
                    return Hasil2;
                }
            }
//            System.out.println("der_suffixes: "+der_suffixes);
            return der_suffixes;
        }
//        System.out.println("der_suffixes: "+der_suffixes);
        return der_suffixes;   
    }
    
    //Hapus Derivation prefixes
    private String Hps_derivation_prefixes(String kata){
        String der_prefixes = kata;
        String Hasil = "";
        String Hasil2 = "";
        if (kata.startsWith("di")||kata.startsWith("ke")||kata.startsWith("se")){
            Hasil = kata.replaceAll("^(di|ke|se)", "");
            if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil))))return Hasil;
            Hasil2 = Hps_derivation_suffixes(Hasil);
            if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
            if (kata.startsWith("diper")){
                Hasil = kata.replaceAll("^(diper)", "");
                if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                Hasil2 = Hps_derivation_suffixes(Hasil);
                if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                Hasil = kata.replaceAll("^(diper)", "r"); //luluh "r"
                if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                Hasil2 = Hps_derivation_suffixes(Hasil);
                if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
            }
        }
        if (kata.startsWith("te")||kata.startsWith("me")||kata.startsWith("be")||kata.startsWith("pe")){
            if(kata.startsWith("te")){
                if (kata.startsWith("terr")) return der_prefixes;
                if (kata.startsWith("ter")){
                     Hasil = kata.replaceAll("^(ter)", "");
                     if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                     Hasil2 = Hps_derivation_suffixes(Hasil);
                     if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("te")){
                    Hasil = kata.replaceAll("^(te)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
            }
            if (kata.startsWith("me")){
                if (kata.startsWith("menga")||kata.startsWith("mengi")||kata.startsWith("mengu")||
                        kata.startsWith("menge")||kata.startsWith("mengo")||kata.startsWith("mengk")
                        ||kata.startsWith("mengg")||kata.startsWith("mengh")||kata.startsWith("mengq")){
                    Hasil = kata.replaceAll("^(meng)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    Hasil = kata.replaceAll("^(meng)", "k");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("meny")){
                    Hasil = kata.replaceAll("^(meny)", "s");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("mem")){
                    if (kata.startsWith("memb")||kata.startsWith("memf")||kata.startsWith("memp")
                            ||kata.startsWith("memv")){
                        Hasil = kata.replaceAll("^(mem)", "");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                        Hasil = kata.replaceAll("^(mem)", "p");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                        if (kata.startsWith("memper")){
                            Hasil = kata.replaceAll("^(memper)", "");
                            if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                            Hasil2 = Hps_derivation_suffixes(Hasil);
                            if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                            Hasil = kata.replaceAll("^(memper)", "r");
                            if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                            Hasil2 = Hps_derivation_suffixes(Hasil);
                            if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                        }
                    }
                    if (kata.startsWith("mema")||kata.startsWith("memi")||kata.startsWith("memu")
                            ||kata.startsWith("meme")||kata.startsWith("memo")){
                        Hasil = kata.replaceAll("^(mem)", "p");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    }
		}
                if (kata.startsWith("men")){
                    if (kata.startsWith("menc")||kata.startsWith("mend")||kata.startsWith("menj")
                            ||kata.startsWith("mens")||kata.startsWith("menz")){
                        Hasil = kata.replaceAll("^(men)", "");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    }
                    if (kata.startsWith("mena")||kata.startsWith("meni")||kata.startsWith("menu")
                            ||kata.startsWith("mene")||kata.startsWith("meno")){
                        Hasil = kata.replaceAll("^(men)", "t");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    }
                }
                if (kata.startsWith("me")){
                    Hasil = kata.replaceAll("^(me)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
            }
            if (kata.startsWith("be")){
                if (kata.startsWith("ber")){
                    if (kata.startsWith("bera")||kata.startsWith("beri")||kata.startsWith("beru")
                            ||kata.startsWith("bere")||kata.startsWith("bero")){
                        Hasil = kata.replaceAll("^(ber)", "");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                        Hasil = kata.replaceAll("^(ber)", "r");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    }
                    if (!(kata.startsWith("bera")||kata.startsWith("beri")||kata.startsWith("beru")
                            ||kata.startsWith("bere")||kata.startsWith("bero"))){
                        Hasil = kata.replaceAll("^(ber)", "");
                        if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                        Hasil2 = Hps_derivation_suffixes(Hasil);
                        if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    }
                    return der_prefixes;
                }
                if (kata.startsWith("bek")){
                    Hasil = kata.replaceAll("^(be)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
            }
            if (kata.startsWith("pe")){
                if (kata.startsWith("penga")||kata.startsWith("pengi")||kata.startsWith("pengu")
                        ||kata.startsWith("penge")||kata.startsWith("pengo")||kata.startsWith("pengk")
                        ||kata.startsWith("pengg")||kata.startsWith("pengh")||kata.startsWith("pengq")){
                    Hasil = kata.replaceAll("^(peng)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    Hasil2 = Hps_derivation_suffixes(Hasil.substring(1));
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    Hasil = "k" + Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("peny")){
                    Hasil = kata.replaceAll("^(peny)", "s");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("pemb")||kata.startsWith("pemf")||kata.startsWith("pemp")||
                        kata.startsWith("pemv")||kata.startsWith("pemr")||kata.startsWith("pem")){
                    Hasil = kata.replaceAll("^(pem)", "");
                    if(Hasil.startsWith("belajar"))Hasil="ajar";

                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    Hasil = kata.replaceAll("^(pem)", "p");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("penc")||kata.startsWith("pend")||kata.startsWith("penj")
                        ||kata.startsWith("pens")||kata.startsWith("penz")){
                    Hasil = kata.replaceAll("^(pen)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("pena")||kata.startsWith("peni")||kata.startsWith("penu")
                        ||kata.startsWith("pene")||kata.startsWith("peno")){
                    Hasil = kata.replaceAll("^(pen)", "t");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
		}
                if (kata.startsWith("per")){
                    Hasil = kata.replaceAll("^(per)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                    Hasil = kata.replaceAll("^(per)", "r");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
                if (kata.startsWith("pe")){
                    Hasil = kata.replaceAll("^(pe)", "");
                    if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                    Hasil2 = Hps_derivation_suffixes(Hasil);
                    if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                }
            }
            if (kata.startsWith("memper")){
                Hasil = kata.replaceAll("^(memper)", "");
                if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                Hasil2 = Hps_derivation_suffixes(Hasil);
                if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
                Hasil = kata.replaceAll("^(memper)", "r");
                if (stringMatcher(Hasil, DocumentReader.getKamusKDid().get(getFirstChart(Hasil)))) return Hasil;
                Hasil2 = Hps_derivation_suffixes(Hasil);
                if (stringMatcher(Hasil2, DocumentReader.getKamusKDid().get(getFirstChart(Hasil2)))) return Hasil2;
            }
		}
        if (!(kata.startsWith("di")||kata.startsWith("ke")||kata.startsWith("se")||
                kata.startsWith("te")||kata.startsWith("me")||kata.startsWith("be")||kata.startsWith("pe"))){
            return der_prefixes;
        }
        return der_prefixes;
    }
    
    private Character getFirstChart(String teks){
        try {
            //System.out.println(teks + " : " + teks.charAt(0));
            return teks.charAt(0);
        } catch (Exception e) {
            return '`';
        }
    }
    
    // method check apakah isi stringnya sama
    private static boolean stringMatcher(String temp, String[] temp2){
        for (int j=0; j<temp2.length; j++){
            if(temp.equals(temp2[j])){
                return true;
            }
        }
        return false;
    }
    
}
