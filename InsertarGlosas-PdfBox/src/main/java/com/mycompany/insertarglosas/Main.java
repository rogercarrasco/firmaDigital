
package com.mycompany.insertarglosas;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        
        String[] argss = {"Carta.pdf", "CartaConGlosa.pdf"};
        File srcFile = new File(argss[0]);
        File dstFile = new File(argss[1]);
        
        System.err.println("srcFile: " + srcFile.getAbsolutePath());
        
        String institucion = "Presidencia del Consejo de Ministros";
        String inicio = "04/01/2022";
        String fin = "04/04/2022";
        String urlVerica = "https://www.pcm.gob.pe/verifica-cvd";

        Glosas cvd = new Glosas();
        cvd.genCVD(srcFile, dstFile, institucion, inicio, fin, urlVerica);
    }
}
